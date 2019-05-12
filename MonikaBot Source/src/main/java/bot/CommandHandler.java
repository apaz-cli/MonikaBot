package bot;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.Permissions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class CommandHandler {

	// Completely useless for one command, but very useful for expanding so that
	// there isn't this massive if/elif block.
	private static Map<String, Command> commandMap = new HashMap<>();

	private static String nsfwChannelListFileName = "NSFW Channel List.txt";
	private static String nsfwChannelListFilePath = System.getProperty("user.dir") + File.separator
			+ nsfwChannelListFileName;
	private static File nsfwChannelListFile = new File(nsfwChannelListFilePath);
	private static ArrayList<String> nsfwChannelList = new ArrayList<>();

	public static void saveNSFWChannelList() {
		PrintWriter out = null;
		try {
			out = new PrintWriter(nsfwChannelListFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.err.println(
					"Could not save the NSFW channel list file. Make sure that java has read/write permission at this file location.");
		}
		for (int i = 0; i < nsfwChannelList.size(); i++) {
			out.println(nsfwChannelList.get(i));
		}
		out.close();
	}

	public static void loadNSFWChannelList() {
		Scanner channelScanner = null;
		try {
			channelScanner = new Scanner(nsfwChannelListFile);
		} catch (FileNotFoundException e) {
			System.err.println("Could not find the NSFW channel list file. Load failed.");
			return;
		}
		if (channelScanner.hasNext()) {
			nsfwChannelList.clear();
			while (channelScanner.hasNext()) {
				String c = channelScanner.next();
				nsfwChannelList.add(c);
				System.out.println("Loaded " + c + " into the NSFW channel list.");
			}
		} else {
			System.out.println("The NSFW channel list file is empty. Not modifying working list.");
		}
		channelScanner.close();
	}

	// Register commands
	static {
		System.out.println("");
		System.out.println("Loading the Command Map.");

		commandMap.put("help", (event, args) -> {
			BotUtils.sendMessage(event.getChannel(), 
					"```Prolog\nCommand List\n```\n" + 
					"**/monika**\n" + "*Posts a random quote*\n" + 
					"**/monika #**\n" + "*Posts the quote specified*\n" + 
					"**/index**\n" + "*Updates the image list and posts the totals*\n" + 
					"**/sfwMonika**\n" + "*Posts a random sfw Monika pic*\n" + 
					"**/nsfwMonika**\n" + "*Posts a random nsfw Monika pic*\n" + 
					"**/enableNSFW**\n" + "*Enables /nsfwMonika in the current channel (Disabled by default)*\n" + 
					"**/disableNSFW**\n" + "*Disables /nsfwMonika in the current channel (Disabled by default)*\n"
					);
		});

		commandMap.put("monika", (event, args) -> {
			// Get the appropriate string from the map
			String quoteString = QuoteHandler.getQuote(event, args);
			if (quoteString != null) {
				BotUtils.sendQuote(event.getChannel(), quoteString);
			}
		});

		commandMap.put("index", (event, args) -> {
			BotUtils.sendMessage(event.getChannel(), "Loaded " + ImageHandler.indexSFW() + " SFW Images.\n" + "Loaded "
					+ ImageHandler.indexNSFW() + " NSFW images.");
		});

		commandMap.put("sfwmonika", (event, args) -> {
			File image = ImageHandler.getRandomSFWImage();
			if (image == null) {
				System.err.println("getImage returned null. Is the SFW image folder empty?");
			} else {
				BotUtils.sendFile(event.getChannel(), image);
			}
		});

		commandMap.put("nsfwmonika", (event, args) -> {
			if (!nsfwChannelList.contains(event.getChannel().getStringID())) {
				BotUtils.sendMessage(event.getChannel(), "NSFW Commands are not enabled in this channel.");
			} else {
				File image = ImageHandler.getRandomNSFWImage();
				if (image == null) {
					System.err.println("getImage returned null. Is the NSFW image folder empty?");
				} else {
					BotUtils.sendFile(event.getChannel(), image);
				}
			}
		});

		commandMap.put("enablensfw", (event, args) -> {
			if (event.getAuthor().getPermissionsForGuild(event.getGuild()).contains(Permissions.ADMINISTRATOR)
			/* || event.getAuthor().getLongID() == 222363567192670219L */) {
				// As to avoid duplicate entries
				if (!nsfwChannelList.contains(event.getChannel().getStringID())) {
					nsfwChannelList.add(event.getChannel().getStringID());
					BotUtils.sendMessage(event.getChannel(),
							"NSFW enabled in <#" + event.getChannel().getStringID() + ">");
					System.out.println("NSFW enabled in " + event.getChannel().getName() + ".");
					saveNSFWChannelList();
				}
			} else {
				BotUtils.sendMessage(event.getChannel(), event.getAuthor().getDisplayName(event.getGuild())
						+ ", you don't have permission to use that command on this server.");
				System.out.println(event.getAuthor().getDisplayName(event.getGuild())
						+ " does not have permission to use this command on this server.");
			}
		});

		commandMap.put("disablensfw", (event, args) -> {
			if (event.getAuthor().getPermissionsForGuild(event.getGuild()).contains(Permissions.ADMINISTRATOR)
			/* || event.getAuthor().getLongID() == 222363567192670219L */) {
				nsfwChannelList.remove(event.getChannel().getStringID());
				BotUtils.sendMessage(event.getChannel(),
						"NSFW disabled in <#" + event.getChannel().getStringID() + ">");
				System.out.println("NSFW disabled in " + event.getChannel().getName() + ".");
				saveNSFWChannelList();
			} else {
				BotUtils.sendMessage(event.getChannel(), event.getAuthor().getDisplayName(event.getGuild())
						+ ", you don't have permission to use that command on this server.");
				System.out.println(event.getAuthor().getDisplayName(event.getGuild())
						+ " does not have permission to use this command on this server.");
			}
		});

		System.out.println("Done.");

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
			argStr.trim();
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