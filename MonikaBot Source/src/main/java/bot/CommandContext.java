package bot;

import discord4j.core.event.domain.message.MessageCreateEvent;

public class CommandContext {

	private MessageCreateEvent event;
	private String command;
	private String args;

	public CommandContext(MessageCreateEvent event, String command, String args) {
		this.event = event;
		this.command = command;
		this.args = args;
	}

	public MessageCreateEvent getEvent() { return this.event; }

	public String getCommand() { return command; }

	public String getArgs() { return args; }

}
