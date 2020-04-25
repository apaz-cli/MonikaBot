package bot;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.MessageHistory;

import java.io.File;
import java.net.UnknownHostException;
import java.util.*;

public class CommandHandler {

	// Completely useless for one command, but very useful for expanding so that
	// there isn't this massive if/elif block.
	private static Map<String, Command> commandMap = new HashMap<>();

	// Register commands
	static {
		System.out.println();
		System.out.println("Loading the Command Map.");

		// @nof
		commandMap.put("help", (event, args) -> {
			String baseCommandList = "```Prolog\nCommand List\n```\n" + "**/monika**\n" + "*Posts a random quote*\n"
					+ "**/monika #**\n" + "*Posts the quote specified*\n" + "**/index**\n"
					+ "*Updates the image list and posts the totals*\n" + "**/sfwMonika**\n"
					+ "*Posts a random sfw Monika pic*\n" + "**/cleanup**\n"
					+ "*Deletes all posts by this bot in this channel*";
			String nsfwCommands = "```Prolog\nNSFW Commands\n```\n" + "**/nsfwMonika**\n"
					+ "*Posts a random nsfw Monika pic*\n" + "**/enableNSFW**\n"
					+ "*Enables /nsfwMonika in the current channel (Disabled by default)*\n" + "**/disableNSFW**\n"
					+ "*Disables /nsfwMonika in the current channel (Disabled by default)*\n";
			String interserverCommands = 
					  "```Prolog\nInterserver Commands\n```\n" + "**/listServers**\n"
					+ "*Arguments: none*\n" + "*Gets a list of servers that this bot is on.*\n" + "**/listChannels**\n"
					+ "*Arguments: ServerKeyword post|print*\n"
					+ "*Gets a list of channels in the server specified by the keyword. This is case-sensitive, so be careful with that. Posts in discord when you type posts afterward.*\n"
					+ "**/rip**\n" + "*Arguments: ServerKeyword ChannelKeyword file|post|print*\n"
					+ "*Collects all the links to all the attachments in the specified channel on the specified server. This is (usually) a truly massive number of images. Be careful with this command, especially when posting. Saving to a file allows you to move those images around with /transplant.*\n"
					+ "**/transplant**\n" + "*Arguments: none, handled by /rip and /rc*\n"
					+ "*Effectively creates a GUI inside discord with which to move around the links which have been dumped into a file by /rip. Register channels first with /registerchannel or it's alias /rc, then use this command and react to the resulting message with an emoji to move the image into that channel, or press the green X to skip that image. Type /reset to stop.*\n"
					+ "**/registerChannel OR /rc**\n" + "*Arguments: none*\n" + "*Registers this channel an emoji to transplant with. See /transplant.*\n" 
					+ "**/reset**\n" + "*Arguments: none*\n" + "*Clears the global state of the interserver commands. Call this when you're done transplanting.*\n"
					+ "**/delete**\n"+ "*Arguments: A list of urls*\n" + "*Deletes the messages that contains these urls in the calling channel, then reposts the rest of the links and attachments in the same channel.*\n"
					+ "**/rebase**\n" +"*Arguments: none*\n" + "*Reposts all of the image links from this channel, along with any attachments on those messages, then deletes those messages if no errors occurred while downloading.*\n";
			String helpMessage = baseCommandList;
			if (ImageHandler.isChannelNSFW(event.getChannel())) {
				helpMessage += nsfwCommands;
			}
			if (InterserverCommands.isAuthorized(event.getAuthor(), event.getGuild())) {
				helpMessage += interserverCommands;
			}

			BotUtils.sendMessage(event.getChannel(), helpMessage);
		});
		// @dof

		commandMap.put("monika", (event, args) -> {
			String quoteString = QuoteHandler.getQuote(event, args);
			if (quoteString != null) {
				BotUtils.sendQuote(event.getChannel(), quoteString);
			}
		});

		commandMap.put("index", (event, args) -> {
			String sfwMessage = "Loaded " + ImageHandler.indexSFW() + " SFW Images.";
			String nsfwMessage = "Loaded " + ImageHandler.indexNSFW() + " NSFW images.";
			String message = ImageHandler.isChannelNSFW(event.getChannel()) ? sfwMessage + "\n" + nsfwMessage
					: sfwMessage;
			BotUtils.sendMessage(event.getChannel(), message);
		});

		commandMap.put("sfwmonika", (event, args) -> {
			File image = ImageHandler.getRandomSFWImage();
			if (image == null) {
				System.err.println("Please fill the sfw image folder with images.");
			} else {
				BotUtils.sendFile(event.getChannel(), image);
			}
		});

		commandMap.put("nsfwmonika", (event, args) -> {
			if (!ImageHandler.nsfwChannelList.contains(event.getChannel().getStringID())) {
				BotUtils.sendMessage(event.getChannel(), "NSFW Commands are not enabled in this channel.");
			} else {
				File image = ImageHandler.getRandomNSFWImage();
				if (image == null) {
					System.err.println("Please fill the nsfw image folder with images.");
				} else {
					BotUtils.sendFile(event.getChannel(), image);
				}
			}
		});

		commandMap.put("enablensfw", (event, args) -> {
			if (!event.getAuthor().getPermissionsForGuild(event.getGuild()).contains(Permissions.ADMINISTRATOR)) {
				return;
			}

			// As to avoid duplicate entries
			if (!ImageHandler.nsfwChannelList.contains(event.getChannel().getStringID())) {
				ImageHandler.nsfwChannelList.add(event.getChannel().getStringID());
				BotUtils.sendMessage(event.getChannel(), "NSFW enabled in <#" + event.getChannel().getStringID() + ">");
				System.out.println("NSFW enabled in " + event.getChannel().getName() + ".");
				ImageHandler.saveNSFWChannelList();
			}

		});

		commandMap.put("disablensfw", (event, args) -> {
			if (!event.getAuthor().getPermissionsForGuild(event.getGuild()).contains(Permissions.ADMINISTRATOR)) {
				return;
			}

			ImageHandler.nsfwChannelList.remove(event.getChannel().getStringID());
			BotUtils.sendMessage(event.getChannel(), "NSFW disabled in <#" + event.getChannel().getStringID() + ">");
			System.out.println("NSFW disabled in " + event.getChannel().getName() + ".");
			ImageHandler.saveNSFWChannelList();
		});

		commandMap.put("cleanup", (event, args) -> {
			if (!event.getAuthor().getPermissionsForGuild(event.getGuild()).contains(Permissions.ADMINISTRATOR)) {
				return;
			}

			event.getMessage().delete();
			MessageHistory history = event.getChannel().getFullMessageHistory();
			long ourID = MainRunner.getClient().getOurUser().getLongID();
			for (IMessage m : history.asArray()) {
				if (m.getAuthor().getLongID() == ourID) {
					if (m.getAttachments().isEmpty()) {
						m.delete();

						// Courtesy to Discord to avoid RateLimitExceptions
						try {
							Thread.sleep(250);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		});

		commandMap.put("ship", (event, args) -> {

			if (!event.getAuthor().getPermissionsForGuild(event.getGuild()).contains(Permissions.ADMINISTRATOR)) {
				return;
			}

			String ip;
			try {
				ip = BotUtils.getIP();
			} catch (UnknownHostException e) {
				BotUtils.sendMessage(event.getChannel(), "Failed to Fetch IP.");
				e.printStackTrace();
				return;
			}

			IMessage message = BotUtils.sendMessage(event.getChannel(), ip);
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
			}
			if (!message.isDeleted()) {
				message.delete();
			}

			message = event.getMessage();
			if (!message.isDeleted()) {
				message.delete();
			}
		});

		if (InterserverCommands.ENABLED) {
			commandMap.put("listservers", InterserverCommands.listServers);
			// Keyword for server file|post|print
			commandMap.put("listchannels", InterserverCommands.listServerChannels);
			// ServerKeyword ChannelKeyword file|post|print
			commandMap.put("rip", InterserverCommands.rip);
			// No arguments
			commandMap.put("registerchannel", InterserverCommands.registerChannel);
			// No arguments, shorthand for the above
			commandMap.put("rc", InterserverCommands.registerChannel);
			// No arguments
			commandMap.put("reset", InterserverCommands.resetGlobalState);
			// No arguments, information is gathered from the placement of registerChannel
			// commands.
			commandMap.put("transplant", InterserverCommands.transplant);
			// List of urls to delete
			commandMap.put("delete", InterserverCommands.deleteAttachments);
			commandMap.put("rebase", InterserverCommands.rebase);
		}

	}

	// This bot will, by design, respond to messages from other bots, like mee6's
	// timed messages.
	@EventSubscriber
	public void onMessageReceived(MessageReceivedEvent event) {

		// Don't process messages that aren't commands.
		String message = event.getMessage().getContent().trim();
		if (!message.startsWith(BotUtils.BOT_PREFIX) || message == null) {
			return;
		}

		// Process the command.

		// Splits up the line after every space.
		String[] messageSplit = message.split(" ");

		// Strip the bot prefix off the 0th entry, leaving
		// only the command name in the 0th element of the array.
		String commandStr = messageSplit[0].substring(BotUtils.BOT_PREFIX.length()).toLowerCase().trim();

		// Put the rest of the string back together (but not the 0th entry, which
		// contains the command name and no arguments.)
		String argStr = "";

		// If the user entered a command with no arguments, messageSplit[1] would not
		// exist, and argStr should remain the empty string. Otherwise, put the
		// arguments back together, with the original spaces between.
		if (messageSplit.length >= 1) {
			for (int i = 1; i < messageSplit.length; i++) {
				argStr += messageSplit[i].trim() + " ";
			}
			argStr = argStr.trim();
		}
		// Strip off the leading space.

		// Run the command specified, and alert the user if there's an error.
		if (commandMap.containsKey(commandStr)) {
			try {
				System.out.println("Command entered: " + commandStr);
				commandMap.get(commandStr).runCommand(event, argStr);
			} catch (Exception e) {
				// If there's a legitimate bug or something,
				// print it to command line instead. No need to tell the
				// user if there's nothing they can do about it.
				System.out.println("");
				System.out.println("");
				System.out.println("");
				e.printStackTrace();
			}

		}

	}

}