package bot;

import discord4j.common.util.Snowflake;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.User;
import bot.CommandContext;

public class MainRunner {

	// Change this if the token is regenerated, or overwrite it with argument from
	// command line.
	private static String token = "TOKEN HERE";

	public static Snowflake OURID;
	
	static {
		ImageHandler.loadNSFWChannelList();
		ImageHandler.indexSFW();
		ImageHandler.indexNSFW();
	}

	// Handles the creation and getting of a IDiscordClient object for a token
	private static GatewayDiscordClient getBuiltDiscordClient(String token) {

		GatewayDiscordClient Monika = DiscordClientBuilder.create(token).build().login().block();

		Monika.getEventDispatcher().on(ReadyEvent.class).subscribe(event -> {
			User self = event.getSelf();
			OURID = self.getId();
			System.out.println(String.format("Logged in as %s#%s", self.getUsername(), self.getDiscriminator()));
		});

		return Monika;
	}

	public static void main(String[] args) {

		if (args.length == 1) {
			token = args[0];
		}

		GatewayDiscordClient Monika = getBuiltDiscordClient(token);

		// @nof
		Monika.on(MessageCreateEvent.class).filter(event -> {
			return event.getMessage().getAuthor().map(author -> !author.isBot()).orElse(false)
					&& event.getMessage().getContent().trim().startsWith(BotUtils.BOT_PREFIX);
		}).map(event -> {
			String msg = event.getMessage().getContent().trim();
			String[] messageSplit = msg.split(" ");
			String commandStr = messageSplit[0].substring(BotUtils.BOT_PREFIX.length()).toLowerCase().trim();

			String argStr = "";
			if (messageSplit.length >= 1) {
				for (int i = 1; i < messageSplit.length; i++) {
					argStr += messageSplit[i].trim() + " ";
				}
				argStr = argStr == "" ? "" : argStr.substring(0, argStr.length() - 1);
			}
			return new CommandContext(event, commandStr, argStr);
		}).filter(context -> CommandHandler.commandMap.containsKey(context.getCommand()))
		.flatMap((context) -> {
			System.out.println("Command entered: " + context.getCommand());
			Command command = CommandHandler.commandMap.get(context.getCommand());
			return command.execute(context.getEvent(), context.getArgs());
		}).subscribe(result->{}, error->{ for (int i = 0; i < 3; i++) {System.err.println();} error.printStackTrace(); });
		// @dof

		Monika.onDisconnect().block();
	}

}
