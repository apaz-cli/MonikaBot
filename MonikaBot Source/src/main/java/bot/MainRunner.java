package bot;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;

public class MainRunner {

	// Change this if the token is regenerated, or overwrite it with argument from
	// command line.
	private static String token = "NTU1OTYyNjI1OTY0Mzc2MDY0.XNh6cg.SwMn1H9tSzFNxmbnuSlqPVLNS4E";

	// Handles the creation and getting of a IDiscordClient object for a token
	private static IDiscordClient getBuiltDiscordClient(String token) {
		ClientBuilder Monika = new ClientBuilder();
		Monika.withToken(token);
		Monika.set5xxRetryCount(Integer.MAX_VALUE);
		Monika.setMaxReconnectAttempts(Integer.MAX_VALUE);
		return Monika.build();
	}

	private static IDiscordClient cli = getBuiltDiscordClient(token);;

	public static void main(String[] args) {

		ImageHandler.loadNSFWChannelList();
		ImageHandler.indexSFW();
		ImageHandler.indexNSFW();

		if (args.length == 1) {
			token = args[0];
		}

		cli = getBuiltDiscordClient(token);

		// Register a listener via the EventSubscriber annotation which allows for
		// organization and delegation of events
		EventDispatcher dispatcher = cli.getDispatcher();
		dispatcher.registerListener(new CommandHandler());

		if (InterserverCommands.ENABLED) {
			dispatcher.registerListener(new InterserverCommands());
		}

		// Only login after all events are registered otherwise some may be missed.
		cli.login();
	}

	static IDiscordClient getClient() {
		return cli;
	}
	
}
