package bot;

import discord4j.core.event.domain.message.MessageCreateEvent;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface Command {
	abstract Mono<Void> execute(MessageCreateEvent event, String args);
}
