package bot;

import java.io.File;
import java.io.FileNotFoundException;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.RequestBuffer;

import bot.messagewrappers.*;

class BotUtils {

	static String BOT_PREFIX = "/";
	private static String MESSAGE_SPLIT_CHAR = ":";

	// Helper functions to make certain aspects of the bot easier to use.
	static void sendQuote(IChannel channel, String message) {

		// RequestBuffer is a utility class intended to deal with RateLimitExceptions by
		// queuing rate-limited operations until they can be completed.

		RequestBuffer.request(() -> {
			try {
				boolean sent = false;
				while (!sent) {
					if (message.length() > 1999) {
						String[] messages = message.split(MESSAGE_SPLIT_CHAR);
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

	public static String codeBlock(String message) {
		return "```\n" + message + "\n```";
	}

	public static String codeBlock(String message, String language) {
		return "```" + language + "\n" + message + "```";
	}
	
	static IMessage sendMessage(IChannel channel, String message) {
		Box box = new Box();
		Flag finished = new Flag();

		RequestBuffer.request(() -> {
		    synchronized (finished) {
		        try {
		            box.result = channel.sendMessage(message);
		        } catch (DiscordException e) {
		            System.out.println("Message could not be sent. Returned the error: ");
		            e.printStackTrace();
		        } finally {
		            finished.on = true;
		            finished.notify();
		        }
		    }
		});
		synchronized (finished) {
		    if (!finished.on)
				try {
					finished.wait(60000);
				} catch (InterruptedException e) {
				}
		}
		return box.result;
	}

	static IMessage sendFile(IChannel channel, File file) {
		class Box { IMessage result; }
		class Flag { boolean on; }
		Box box = new Box();
		Flag finished = new Flag();

		RequestBuffer.request(() -> {
		    synchronized (finished) {
		        try {
		            box.result = channel.sendFile(file);
		        } catch (DiscordException | FileNotFoundException e) {
		            System.out.println("Message could not be sent. Returned the error: ");
		            e.printStackTrace();
		        } finally {
		            finished.on = true;
		            finished.notify();
		        }
		    }
		});
		synchronized (finished) {
		    if (!finished.on)
				try {
					finished.wait(60000);
				} catch (InterruptedException e) {
				}
		}
		return box.result;
	}

	static IMessage sendFileMessage(IChannel channel, String message, File file) {
		class Box { IMessage result; }
		class Flag { boolean on; }
		Box box = new Box();
		Flag finished = new Flag();

		RequestBuffer.request(() -> {
		    synchronized (finished) {
		        try {
		            box.result = channel.sendFile(message, file);
		        } catch (DiscordException | FileNotFoundException e) {
		            System.out.println("Message could not be sent. Returned the error: ");
		            e.printStackTrace();
		        } finally {
		            finished.on = true;
		            finished.notify();
		        }
		    }
		});
		synchronized (finished) {
		    if (!finished.on)
				try {
					finished.wait(60000);
				} catch (InterruptedException e) {
				}
		}
		return box.result;
	}

}
