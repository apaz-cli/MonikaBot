package bot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.Duration;
import java.util.*;

import discord4j.common.util.Snowflake;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.GuildChannel;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.object.entity.channel.TextChannel;
import discord4j.rest.util.Permission;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class CommandHandler {

	// Completely useless for one command, but very useful for expanding so that
	// there isn't this massive if/elif block.
	static Map<String, Command> commandMap = new HashMap<>();
	// ****************//
	// Normal Commands //
	// ****************//
	static {
		System.out.println();
		System.out.println("Loading the Command Map.");

		// @nof
		commandMap.put("help", (event, args) -> {
			String baseCommandList = 
					"```Prolog\nCommand List\n```\n" + 
					"**/monika**\n" + "*Posts a random quote*\n" + 
					"**/monika #**\n" + "*Posts the quote specified*\n" + 
					"**/index**\n" + "*Updates the image list and posts the totals*\n" + 
					"**/sfwMonika**\n" + "*Posts a random sfw Monika pic*\n" + 
					"**/cleanup**\n" + "*Deletes all posts by this bot in this channel*";
			String nsfwCommands = "```Prolog\nNSFW Commands\n```\n" + 
					"**/nsfwMonika**\n" + "*Posts a random nsfw Monika pic*\n" + 
					"**/enableNSFW**\n" + "*Enables /nsfwMonika in the current channel (Disabled by default)*\n" + 
					"**/disableNSFW**\n" + "*Disables /nsfwMonika in the current channel (Disabled by default)*\n";
			
			//String interserverCommands = "```Prolog\nInterserver Commands\n```\n" + 
			//		"**/listServers**\n" + "*Arguments: none*\n" + "*Gets a list of servers that this bot is on.*\n" + 
			//		"**/listChannels**\n" + "*Arguments: ServerKeyword post|print*\n" + "*Gets a list of channels in the server specified by the keyword. This is case-sensitive, so be careful with that. Posts in discord when you type posts afterward.*\n" + 
			//		"**/rip**\n" + "*Arguments: ServerKeyword ChannelKeyword file|post|print*\n" + "*Collects all the links to all the attachments in the specified channel on the specified server. This is (usually) a truly massive number of images. Be careful with this command, especially when posting. Saving to a file allows you to move those images around with /transplant.*\n" + 
			//		"**/transplant**\n" + "*Arguments: none, handled by /rip and /rc*\n" + "*Effectively creates a GUI inside discord with which to move around the links which have been dumped into a file by /rip. Register channels first with /registerchannel or it's alias /rc, then use this command and react to the resulting message with an emoji to move the image into that channel, or press the green X to skip that image. Type /reset to stop.*\n" + 
			//		"**/registerChannel OR /rc**\n" + "*Arguments: none*\n" + "*Registers this channel an emoji to transplant with. See /transplant.*\n" + 
			//		"**/reset**\n" + "*Arguments: none*\n" + "*Clears the global state of the interserver commands. Call this when you're done transplanting.*\n" + 
			//		"**/delete**\n" + "*Arguments: A list of urls*\n" + "*Deletes the messages that contains these urls in the calling channel, then reposts the rest of the links and attachments in the same channel.*\n" + 
			//		"**/rebase**\n" + "*Arguments: none*\n" + "*Reposts all of the image links from this channel, along with any attachments on those messages, then deletes those messages if no errors occurred while downloading.*\n";
			
		// @dof	
			Mono<Boolean> isNSFW = isChannelNSFW(event);

			final Mono<String> commandList = isNSFW.map(b -> b ? baseCommandList + nsfwCommands : baseCommandList);

			Mono<MessageChannel> dmc = Mono.justOrEmpty(event.getMember()).flatMap(m -> m.getPrivateChannel())
					.ofType(MessageChannel.class);

			Mono<Message> sentMessage = dmc.flatMap(c -> commandList.flatMap(message -> c.createMessage(message)));

			return sentMessage.then();
		});

		commandMap.put("monika", (event, args) -> {
			return Mono.justOrEmpty(event.getMember()).flatMapMany(member -> event.getMessage().getChannel()
					.flatMapMany(channel -> QuoteHandler.sendQuote(member, channel, args))).then();
		});

		commandMap.put("index", (event, args) -> {
			String sfwMessage = "Loaded " + ImageHandler.indexSFW() + " SFW Images.";
			String nsfwMessage = "Loaded " + ImageHandler.indexNSFW() + " NSFW images.";
			Mono<String> message = event.getMessage().getChannel()
					.map(c -> ImageHandler.isChannelNSFW(c.getId()) ? sfwMessage + "\n" + nsfwMessage : sfwMessage);
			Mono<Message> sentMessage = event.getMessage().getChannel().ofType(MessageChannel.class)
					.flatMap(c -> message.flatMap(m -> c.createMessage(m)));
			return sentMessage.then(deleteCallingMessage(event));
		});

		commandMap.put("sfwmonika", (event, args) -> {
			return event.getMessage().getChannel().flatMap(c -> {
				File image = ImageHandler.getRandomSFWImage();
				FileInputStream is = null;
				try {
					is = new FileInputStream(image);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				final FileInputStream fis = is;

				return image != null ? c.createMessage(m -> m.addFile(image.getName(), fis))
						: c.createMessage("Please tell the person running this bot to fill the sfw image folder.");
			}).then(deleteCallingMessage(event));
		});

		commandMap.put("nsfwmonika", (event, args) -> {
			Mono<MessageCreateEvent> authorizedEvent = Mono.just(event).filterWhen(e -> ImageHandler.isChannelNSFW(e));

			return authorizedEvent.flatMap(e -> e.getMessage().getChannel().flatMap(c -> {
				File image = ImageHandler.getRandomNSFWImage();
				FileInputStream is = null;
				try {
					is = new FileInputStream(image);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				final FileInputStream fis = is;

				return image != null ? c.createMessage(m -> m.addFile(image.getName(), fis))
						: c.createMessage(
								"Please tell the person running this bot to either fill the nsfw image folder or disable the functionality.");
			})).then(deleteCallingMessage(authorizedEvent));
		});

		commandMap.put("enablensfw", (event, args) -> {
			return Mono
					.just(event).filterWhen(e -> creatorIsAdmin(e)).flatMap(e -> e.getMessage().getChannel()
							.ofType(MessageChannel.class).flatMap(channel -> { return ImageHandler.enableNSFW(e); }))
					.then();

		});

		commandMap.put("disablensfw", (event, args) -> {
			return Mono
					.just(event).filterWhen(e -> creatorIsAdmin(e)).flatMap(e -> e.getMessage().getChannel()
							.ofType(MessageChannel.class).flatMap(channel -> { return ImageHandler.disableNSFW(e); }))
					.then();
		});

		commandMap.put("cleanup", (event, args) -> {
			Mono<MessageCreateEvent> authorizedEvent = Mono.just(event).filterWhen(e -> creatorIsAdmin(e))
					// Also don't fire if this is an nsfw channel, as so not to mess up archives
					.filterWhen(m -> isChannelNSFW(event).map(b -> !b));

			Flux<Message> messageHistory = authorizedEvent.flatMapMany(e -> fullMessageHistory(e));

			final Snowflake ourID = MainRunner.OURID;
			Flux<Message> ourMessageHistory = messageHistory
					.filterWhen(message -> message.getAuthorAsMember().map(member -> member.getId().equals(ourID)));

			Flux<Message> withoutNSFWImages = ourMessageHistory;

			Flux<Void> deleted = withoutNSFWImages.flatMap(m -> m.delete());

			return deleted.then(deleteCallingMessage(authorizedEvent));
		});

		commandMap.put("ship", (event, args) -> {
			Mono<MessageCreateEvent> authorizedEvent = Mono.just(event).filterWhen(e -> creatorIsAdmin(e));

			Mono<Message> ipMessage = authorizedEvent
					.flatMap(e -> getChannel(e).flatMap(c -> { return c.createMessage(BotUtils.getIP()); }));

			Mono<Void> deleteIPMessage = ipMessage.delayElement(Duration.ofSeconds(5)).flatMap(m -> m.delete());

			return deleteIPMessage.then(deleteCallingMessage(authorizedEvent));
		});

	}

	// *********************//
	// Interserver Commands //
	// *********************//
	static {
		commandMap.put("rip", (event, args) -> {

			Mono<MessageCreateEvent> authorizedEvent = Mono.just(event).filterWhen(e -> creatorIsAdmin(e));

			Flux<Message> messageHistory = authorizedEvent.flatMapMany(e -> fullMessageHistory(e));

			Flux<String> imageURLs = messageHistory.flatMap(message -> {
				Flux<String> attachmentURLs = Flux.fromIterable(message.getAttachments()).map(a -> a.getUrl());
				Flux<String> messageLinks = Flux.fromIterable(URLImageUtils.matchAllStrURLs(message.getContent()));
				return attachmentURLs.concatWith(messageLinks).filter(m -> URLImageUtils.isImage(m));
			});

			// These two have to be finalized so that they can be ued in the lambda chain at
			// the bottom.
			File imageLinkFile = new File("Attachment Links.txt");
			FileInputStream imageLinkFileStream = null;
			try {
				imageLinkFileStream = new FileInputStream(imageLinkFile);
			} catch (FileNotFoundException e2) {
				e2.printStackTrace();
			}
			final FileInputStream imageLinkFileStreamFinal = imageLinkFileStream;

			PrintWriter pw = null;
			try {
				pw = new PrintWriter(imageLinkFile);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			final PrintWriter pwf = pw;

			return imageURLs.map(url -> { pwf.println(url); return url; }).doOnComplete(() -> pwf.close())
					.then(authorizedEvent.flatMap(e -> 
						e.getMessage().getChannel()).flatMap(c -> 
							c.createMessage(s -> 
								s.addFile("Attachment Links.txt", imageLinkFileStreamFinal))))
					.then(deleteCallingMessage(authorizedEvent));
		});
	}

	public static Mono<Boolean> isChannelNSFW(Mono<MessageCreateEvent> event) {
		return event.flatMap(e -> isChannelNSFW(e));
	}

	public static Mono<Boolean> isChannelNSFW(MessageCreateEvent event) {
		return getChannel(event).ofType(TextChannel.class).map(c -> c.isNsfw());
	}

	public static Flux<Message> fullMessageHistory(Mono<MessageCreateEvent> event) {
		return event.flatMapMany(e -> fullMessageHistory(e));
	}

	public static Flux<Message> fullMessageHistory(MessageCreateEvent event) {
		return getChannel(event).flatMapMany(channel -> channel.getMessagesBefore(event.getMessage().getId()));
	}

	public static Mono<Boolean> creatorIsAdmin(Mono<MessageCreateEvent> event) {
		return event.flatMap(e -> creatorIsAdmin(e));
	}

	public static Mono<Boolean> creatorIsAdmin(MessageCreateEvent event) {
		return event.getMessage().getAuthorAsMember()
				.flatMap(creator -> event.getGuild()
						.flatMap(guild -> event.getMessage().getChannel().ofType(GuildChannel.class)
								.flatMap(channel -> channel.getEffectivePermissions(creator.getId())
										.map(perms -> perms.contains(Permission.ADMINISTRATOR)))));
	}

	public static Mono<MessageChannel> getChannel(Mono<MessageCreateEvent> event) {
		return event.flatMap(e -> getChannel(e));
	}

	public static Mono<MessageChannel> getChannel(MessageCreateEvent event) {
		return event.getMessage().getChannel().ofType(MessageChannel.class);
	}

	public static Mono<Member> getOurMember(Mono<MessageCreateEvent> event) {
		return event.flatMap(e -> getOurMember(e));
	}

	public static Mono<Member> getOurMember(MessageCreateEvent event) {
		return event.getClient().getSelf().flatMap(s -> event.getGuild().flatMap(g -> s.asMember(g.getId())));
	}

	public static Mono<Void> deleteCallingMessage(Mono<MessageCreateEvent> event) {
		return event.flatMap(e -> deleteCallingMessage(e));
	}

	public static Mono<Void> deleteCallingMessage(MessageCreateEvent event) {
		return Mono.just(event.getMessage())
				.filterWhen(m -> getOurMember(event).flatMap(us -> m.getChannel().ofType(GuildChannel.class)
						.flatMap(channel -> channel.getEffectivePermissions(us.getId())
								.map(perms -> perms.contains(Permission.MANAGE_MESSAGES)))))
				.flatMap(m -> m.delete()).then();
	}

}
