package bot.messagewrappers;

import sx.blah.discord.handle.obj.IMessage;

/**
 * A wrapper class necessary for {@link BotUtils#sendMessage(IChannel, String)}.
 * It's necessary to extract the message from the scope of the lambda function.
 * 
 * @author Aaron Pazdera
 */
public class Box {
	public IMessage result;
}