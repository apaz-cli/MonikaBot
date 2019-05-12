package bot;

import java.io.File;
import java.io.FileNotFoundException;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.RequestBuffer;

class BotUtils {

	// Constants for use throughout the bot
	static String BOT_PREFIX = "/";
	static String SPLIT_STR = ":";

	// Helper functions to make certain aspects of the bot easier to use.
	static void sendQuote(IChannel channel, String message) {

		// RequestBuffer is a utility class intended to deal with RateLimitExceptions by
		// queuing rate-limited operations until they can be completed.

		RequestBuffer.request(() -> {
			try {
				boolean sent = false;
				while (!sent) {
					if (message.length() > 1999) {
						String[] messages = message.split(SPLIT_STR);
						int i = 0;
						for (int z = 0; z < messages.length; z++) {

							// Insert ``` at end of first index, and at beginning and end
							// of all the rest.
							if (i == 0) {
								messages[i] = messages[i] + "```";
							} else {
								messages[i] = "```" + messages[i] + "```";
							}

							channel.sendMessage(messages[i]);
							i++;
							sent = true;
						}
					} else {
						channel.sendMessage(message + "```");
						sent = true;
					}
				}
			} catch (DiscordException e) {
				System.err.println("Message could not be sent. Returned the error: ");
				e.printStackTrace();
			}
		});
	}
	
	static void sendMessage(IChannel channel, String message) {
		RequestBuffer.request(() -> {
			try {
				channel.sendMessage(message);
			} catch (DiscordException e) {
				System.out.println("Message could not be sent. Returned the error: ");
				e.printStackTrace();
			}
		});
	}
	
	static void sendFile(IChannel channel, File file) {
		RequestBuffer.request(() -> {
			try {
				channel.sendFile(file);
			} catch (DiscordException | FileNotFoundException e) {
				System.out.println("Message could not be sent. Returned the error: ");
				e.printStackTrace();
			}
		});
	}

	static void sendFileMessage(IChannel channel, String message, File file) {
		RequestBuffer.request(() -> {
			try {
				channel.sendFile(message, file);
			} catch (DiscordException | FileNotFoundException e) {
				System.out.println("Message could not be sent. Returned the error: ");
				e.printStackTrace();
			}
		});
	}

}
