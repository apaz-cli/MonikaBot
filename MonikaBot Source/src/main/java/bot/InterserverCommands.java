package bot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.reaction.ReactionEvent;
import sx.blah.discord.handle.impl.obj.ReactionEmoji;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IMessage.Attachment;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;

public class InterserverCommands {

	// Configs
	public static final boolean ENABLED = true;
	private static final String interserverFolderPath = System.getProperty("user.dir") + File.separator + "Interserver Attachment Folder"; 
	private static final String attachmentLinksFilePath = interserverFolderPath + File.separator + "Attachment Links.txt";
	private static final String completedLinksFilePath = interserverFolderPath + File.separator + "Completed Attachment Links.txt";

	// Global State
	private static boolean transplanting = false;
	private static List<String> loadedAttachmentLinks = new ArrayList<>();

	// Integer -> Emoji -> Channel
	private static HashMap<ReactionEmoji, IChannel> emojiToChannel = new HashMap<>();
	private static HashMap<Integer, ReactionEmoji> intCharacterToEmoji = new HashMap<>();
	static { // Initialize int -> unicode emoji map
		intCharacterToEmoji.put(0, ReactionEmoji.of("\u0030\u20E3", 0, false));
		intCharacterToEmoji.put(1, ReactionEmoji.of("\u0031\u20E3", 0, false));
		intCharacterToEmoji.put(2, ReactionEmoji.of("\u0032\u20E3", 0, false));
		intCharacterToEmoji.put(3, ReactionEmoji.of("\u0033\u20E3", 0, false));
		intCharacterToEmoji.put(4, ReactionEmoji.of("\u0034\u20E3", 0, false));
		intCharacterToEmoji.put(5, ReactionEmoji.of("\u0035\u20E3", 0, false));
		intCharacterToEmoji.put(6, ReactionEmoji.of("\u0036\u20E3", 0, false));
		intCharacterToEmoji.put(7, ReactionEmoji.of("\u0037\u20E3", 0, false));
		intCharacterToEmoji.put(8, ReactionEmoji.of("\u0038\u20E3", 0, false));
		intCharacterToEmoji.put(9, ReactionEmoji.of("\u0039\u20E3", 0, false));
	}
	// Stored globally so that it doesn't have to be re-sorted every time that
	// transplantEmojiListener triggers.
	private static List<ReactionEmoji> sortedRegisteredEmoji = null;

	static Command listServers = (event, args) -> {
		if (!isAuthorized(event.getAuthor(), event.getGuild())) {
			return;
		}

		List<IGuild> guildList = MainRunner.getClient().getGuilds();
		for (IGuild g : guildList) {
			System.out.println(g.getName());
		}
		event.getMessage().delete();
	};

	// Lists all the channels for the server with the given keyword.
	static Command listServerChannels = (event, args) -> {
		if (!isAuthorized(event.getAuthor(), event.getGuild())) {
			return;
		}

		String[] tokens = args.trim().split(" ");
		if (!(tokens.length == 2 || tokens.length == 1)) {
			BotUtils.sendMessage(event.getChannel(), "The syntax for this command is ServerKeyword file|post|print");
			return;
		}

		List<IGuild> guildList = MainRunner.getClient().getGuilds();
		IGuild targetGuild = null;
		for (IGuild g : guildList) {
			if (g.getName().contains(tokens[0])) {
				targetGuild = g;
			}
		}
		if (targetGuild == null) {
			BotUtils.sendMessage(event.getChannel(),
					"I couldn't figure out what guild you were talking about. Maybe try some other keyword? Maybe it isn't capitalized right?");
			System.out.println("Failed find for Guild.");
			return;
		}


		// Change this to find out different things
		String indentedChannels = "";
		for (IChannel ch : targetGuild.getChannels()) {
			indentedChannels += "               " + ch.getName() + "\n";
		}

		String channelOutput = "Guild: " + targetGuild.getName() + "\n" + "     Owner:"
				+ targetGuild.getOwner().getDisplayName(targetGuild) + "\n" + "     Channels: \n" + indentedChannels;

		if (tokens.length == 1) {
			System.out.println(channelOutput);
			return;
		}
		
		if (tokens[1].contains("post")) {
			BotUtils.sendMessage(event.getChannel(), channelOutput);
		} else {
			System.out.println(channelOutput);
		}
	};

	// Gets a (usually) massive list of links to all the attachments in this
	// server's channel's message history.
	static Command rip = (event, args) -> {
		if (!isAuthorized(event.getAuthor(), event.getGuild())) {
			return;
		}

		String[] tokens = args.trim().split(" ");
		if (tokens.length != 3) {
			BotUtils.sendMessage(event.getChannel(),
					"The syntax for this command is ServerKeyword ChannelKeyword file|post|print");
		}

		List<IGuild> guildList = MainRunner.getClient().getGuilds();
		IGuild targetGuild = null;
		for (IGuild g : guildList) {
			if (g.getName().contains(tokens[0])) {
				targetGuild = g;
			}
		}
		if (targetGuild == null) {
			BotUtils.sendMessage(event.getChannel(),
					"I couldn't figure out what guild you were talking about. Maybe /listchannels and try again?");
			System.out.println("Failed find for Guild.");
			return;
		}

		IChannel targetChannel = null;
		for (IChannel ch : targetGuild.getChannels()) {
			if (ch.getName().contains(tokens[1])) {
				targetChannel = ch;
			}
		}
		if (targetChannel == null) {
			BotUtils.sendMessage(event.getChannel(), "I found the guild that you're looking for, it's "
					+ targetGuild.getName()
					+ ". But, I couldn't figure out the channel you were looking for. Maybe /listchannels and try again?");
			System.out.println("Failed find for Channel.");
			return;
		}

		List<String> allAttachmentURLs = new ArrayList<>();
		for (IMessage m : targetChannel.getFullMessageHistory()) {
			for (Attachment a : m.getAttachments()) {
				allAttachmentURLs.add(a.getUrl());
			}
		}

		if (tokens[2].contains("file")) {
			String filename = attachmentLinksFilePath;
			PrintWriter out = null;
			try {
				out = new PrintWriter(filename);
			} catch (Exception e) {
				e.printStackTrace();
			}
			for (String l : allAttachmentURLs) {
				out.println(l);
			}
			out.close();
		} else if (tokens[2].contains("post")) {
			for (int i = 0; i < allAttachmentURLs.size(); i += 5) {
				String attachmentMessage = "";
				for (int j = i; j < i + 5; j++) {
					if (j < allAttachmentURLs.size()) {
						attachmentMessage += allAttachmentURLs.get(j) + "\n";
					}
				}
				BotUtils.sendMessage(event.getChannel(), attachmentMessage);
			}
		} else if (tokens[2].contains("print")) {
			for (String i : allAttachmentURLs) {
				System.out.println(i);
			}
		} else {
			BotUtils.sendMessage(event.getChannel(),
					"Please specify in the third argument file, post, or print. Also, piano music is nice.");
		}
	};

	// Registers this channel an emoji to transplant to. See /transplant.
	static Command registerChannel = (event, args) -> {
		if (!isAuthorized(event.getAuthor(), event.getGuild())) {
			return;
		}
		if (emojiToChannel.size() >= 10) {
			BotUtils.sendMessage(event.getChannel(),
					"You have already registered ten channels baka~~. Why do you need so many anyway?");
			event.getMessage().delete();
			return;
		}
		
		if (emojiToChannel.containsValue(event.getChannel())) {
			BotUtils.sendMessage(event.getChannel(), "This channel has already been registered.");
			return;
		}
		
		int emojiNumber = emojiToChannel.size();
		
		emojiToChannel.put(intCharacterToEmoji.get(emojiNumber), event.getChannel());
		IMessage message = event.getChannel()
				.sendMessage("Channel registered to " + intCharacterToEmoji.get(emojiNumber) + ".");
		event.getMessage().delete();

		// Wait 5 seconds, then delete the message
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		message.delete();
	};

	// Clears the global state
	static Command resetGlobalState = (event, args) -> {
		if (!isAuthorized(event.getAuthor(), event.getGuild())) {
			return;
		}

		transplanting = false;
		sortedRegisteredEmoji = null;
		emojiToChannel.clear();
		loadedAttachmentLinks.clear();
		event.getMessage().delete();
	};

	// Effectively creates a GUI inside discord with which to move around the links
	// which have been dumped into a file by /rip. Register channels first with
	// /registerchannel or it's alias /rc, then use this command and react to the
	// resulting message with an emoji to move the image into that channel, or press
	// the green X to skip that image. Type /reset to stop.
	static Command transplant = (event, args) -> {
		if (!isAuthorized(event.getAuthor(), event.getGuild())) {
			return;
		}
		if (emojiToChannel.isEmpty()) {
			BotUtils.sendMessage(event.getChannel(), "Please register some emoji first.");
			return;
		}

		// Ensure no channel is registered twice
		Collection<IChannel> channelsRegistered = emojiToChannel.values();
		List<IChannel> channelsSeen = new ArrayList<>();
		for (IChannel c : channelsRegistered) {
			if (channelsSeen.contains(c)) {
				BotUtils.sendMessage(event.getChannel(),
						"The same channel was registered an emoji more than once. Clearing all registered emoji. Please re-register.");
				emojiToChannel.clear();
				return;
			}
		}

		// Load ALL links from file
		loadedAttachmentLinks.clear();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(attachmentLinksFilePath));
			String line = reader.readLine();
			while (line != null) {
				loadedAttachmentLinks.add(line.trim());
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			BotUtils.sendMessage(event.getChannel(),
					"The file attachment links could not be read. Possibly it does not exist?");
			e.printStackTrace();
			return;
		}

		// Subtract the links already completed from the ones just loaded
		List<String> completedAttachmentLinks = new ArrayList<>();
		try {
			// Create the file if it does not already exist
			File completedLinksFile = new File(completedLinksFilePath);
			completedLinksFile.createNewFile();

			reader = new BufferedReader(new FileReader(completedLinksFile));
			String line = reader.readLine();
			while (line != null) {
				completedAttachmentLinks.add(line.trim());
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			BotUtils.sendMessage(event.getChannel(), "The completed links file could not be read.");
			e.printStackTrace();
			return;
		}
		loadedAttachmentLinks.removeAll(completedAttachmentLinks);

		// Post a message describing which emoji go to which channel
		String message = "Move to channel:";
		// The sorted emoji keyset is stored globally so that it doesn't have to be
		// sorted
		// every time the event listener triggers.
		sortedRegisteredEmoji = emojiToChannel.keySet().stream().collect(Collectors.toList());
		Collections.sort(sortedRegisteredEmoji, (e1, e2) -> e1.getName().compareTo(e2.getName()));
		for (ReactionEmoji e : sortedRegisteredEmoji) {
			message += "\n" + e + ": " + emojiToChannel.get(e).getName();
		}
		BotUtils.sendMessage(event.getChannel(), message);

		// Activate the listener, which will do the actual transplanting.
		transplanting = true;

		// Post first message of transplant, then listen onEmojiMoveAttachment
		markWithEmoji(event.getChannel().sendMessage(loadedAttachmentLinks.remove(0)));

	};

	// Given a message, will react with the green X emoji, followed by all the emoji
	// in sortedRegisteredEmoji corresponding to channels to transplant to.
	private static void markWithEmoji(IMessage message) {
		message.addReaction(ReactionEmoji.of("\u274E", 0, false));

		for (ReactionEmoji e : sortedRegisteredEmoji) {
			// So that Discord doesn't get mad at us
			try {
				Thread.sleep(170);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
			message.addReaction(e);
		}
	}

	// A listener for the transplant command.
	@EventSubscriber
	public void transplantEmojiListener(ReactionEvent event) {
		if (!transplanting) {
			return;
		}
		// Move only messages from this bot
		if (event.getReaction().getMessage().getAuthor().getLongID() != MainRunner.getClient().getOurUser()
				.getLongID()) {
			return;
		}
		// Don't process reactions from bots. This also prevents a feedback loop from
		// occurring when the emoji to click are added to the transplant message.
		if (event.getUser().isBot()) {
			return;
		}

		// Note that this listener will accept input from users who are not authorized
		// once transplanting. This is a intended, not a flaw.

		IChannel moveToChannel = emojiToChannel.get(event.getReaction().getEmoji());
		String link = event.getReaction().getMessage().getContent();

		// Log to file
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(completedLinksFilePath, true));
			writer.append(link);
			writer.newLine();
			writer.flush();
			writer.close();
		} catch (IOException e) {
			BotUtils.sendMessage(event.getChannel(),
					"There was an issue creating, opening, writing to, or closing the logging file.");
			e.printStackTrace();
			return;
		}

		// Move or delete the message. This will trigger if the reaction that triggered
		// this event was the green X to delete, or they respond with some other
		// unrelated emoji
		if (moveToChannel != null) {
			BotUtils.sendMessage(moveToChannel, link);
		}

		// Post the next link, and mark it such that this action can be repeated.
		String nextLink = loadedAttachmentLinks.remove(0);
		markWithEmoji(BotUtils.sendMessage(event.getChannel(), nextLink));
		event.getMessage().delete();
	}

	public static boolean isAuthorized(IUser user, IGuild guild) {
		boolean authorized = user.getPermissionsForGuild(guild).contains(Permissions.ADMINISTRATOR);
		if (!authorized) {
			System.out.println(user.getName() + " is not authorized to use this command.");
		}
		return authorized;
	}
}