package bot;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;

public class MainRunner {

	// Change this if the token is regenerated, or overwrite it with argument from
	// command line.
	private static String token = "NTU1OTYyNjI1OTY0Mzc2MDY0.D2y6lQ.dUX0f7QZhSvAhclv1qubmqfdlu8";

	public static void main(String[] args) {
		
		CommandHandler.loadNSFWChannelList();
		ImageHandler.indexSFW();
		ImageHandler.indexNSFW();

		if (args.length == 1) {
			token = args[0];
		}

		IDiscordClient cli = getBuiltDiscordClient(token);

		// Register a listener via the EventSubscriber annotation which allows for
		// organization and delegation of events
		cli.getDispatcher().registerListener(new CommandHandler());

		// Only login after all events are registered otherwise some may be missed.
		cli.login();

	}

	// Handles the creation and getting of a IDiscordClient object for a token
	static IDiscordClient getBuiltDiscordClient(String token) {

		// The ClientBuilder object is where you attach parameters for configuring this
		// instance of this Discord4J bot, such as withToken, setDaemon etc
		ClientBuilder Monika = new ClientBuilder();
		Monika.withToken(token);
		Monika.set5xxRetryCount(Integer.MAX_VALUE);
		Monika.setMaxReconnectAttempts(Integer.MAX_VALUE);
		return Monika.build();
	}
}
