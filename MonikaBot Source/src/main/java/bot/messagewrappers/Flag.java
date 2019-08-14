package bot.messagewrappers;

/**
 * A wrapper class necessary for {@link BotUtils#sendMessage(IChannel, String)}.
 * The use of this wrapper prevents a race condition. If .notify() executes
 * before .wait(), then that could be bad. An exception could be thrown, or the
 * method may have to wait an entire minute before returning null. Both of these
 * effects are undesirable.
 * 
 * @author Aaron Pazdera
 */
public class Flag {
	public boolean on;
}
