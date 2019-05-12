package bot;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class QuoteHandler {

	public static Map<Integer, Quote> quoteMap = new HashMap<>();

	public static String getQuote(MessageReceivedEvent event, String args) {
		int min = 1;
		int max = 62;
		Random random = new Random();
		int quoteNumber = 0; // 0 is not actually in the map.

		if (args != null) {
			try {
				int argNum = Integer.parseInt(args.split(" ")[0]);
				if (min <= argNum || argNum <= max) {
					quoteNumber = argNum;
				}

			} catch (NumberFormatException e) {
				quoteNumber = random.nextInt(max - min + 1) + min;
			}
		} else {
			quoteNumber = random.nextInt(max - min + 1) + min;
		}

		String quoteString = null;
		if (quoteMap.containsKey(quoteNumber)) {
			Quote q = quoteMap.get(quoteNumber);

			// Run the RegEx replace on all of them to replace [player] with their current
			// nickname.
			String replacement = q.getQuote().replace("[player]", event.getAuthor().getDisplayName(event.getGuild()));
			// The replacement will always end with the closing ```, but will know how to
			// split it up for multiple messages if necessary.
			quoteString = "Quote #" + quoteNumber + ", " + q.getTitle() + "```" + replacement;
		}
		// Else return null
		return quoteString;
	}

	static {
		System.out.println("");
		System.out.println("Loading the Quote Map.");

		// 1, Same Room
		quoteMap.put(1,
				new Quote("Same Room",
						"I can't help but feel a little sad sometimes...\n"
								+ "Knowing that this is the closest I can get to you.\n"
								+ "There's nothing more I want than to be in the same room as you, for real.\n"
								+ "And to feel your warmth.\n" + "And to hear the sound of your heartbeat.\n"
								+ "Well, who knows? Maybe it'll be possible someday.\n"
								+ "Besides, I'll never get tired of being with you, even from here.\n"));

		// 2, Rain
		quoteMap.put(2, new Quote("Rain",
				"I really like the sound of rain...\n" + "Not so much getting my clothes and hair wet, though.\n"
						+ "But a nice, quiet day at home with the sound of rainfall outside my window..\n"
						+ "It's one of the most calming experiences for me.\n" + "Yeah...\n"
						+ "Sometimes I imagine you holding me while we listen to the sound of the rain outside.\n"
						+ "That's not too cheesy or anything, is it?\n"
						+ "Would you ever do that for me, [player]?\n"));

		// 3, Confidence
		quoteMap.put(3,
				new Quote("Confidence", "I think the most important skill in life is being able to fake confidence.\n"
						+ "I'm pretty convinced that everyone feels at least a little bit scared and alone.\n"
						+ "But being able to trick others into thinking you have it all together...\n"
						+ "That's a key part of getting people to respect and admire you.\n"
						+ "I think I got pretty good at that over the years.\n"
						+ "I don't show my weaknesses very often.\n"
						+ "But because of that, I haven't had many people I could really open up to...\n"
						+ "I mean, when do you reach the point in a friendship where you can start expressing your vulnerabilities?\n"
						+ "Anyway...that's one reason I'm so glad I have you now.\n"
						+ "I feel like I'm a little bit less scared and alone, when you're here with me.\n"
						+ "Do you feel the same way?\n" + "I really want to be that person for you.\n"));

		// 4, Stay-At-Home Partner
		quoteMap.put(4, new Quote("Stay-At-Home Partner",
				"You know, it's funny, because even though I've always had a lot of drive...\n"
						+ "There's something kind of enticing about being the stay-at-home partner.\n"
						+ "I guess I'm, like, perpetuating gender roles or whatever by saying that.\n"
						+ "But being able to keep the house clean, and shop, and decorate, and things like that...\n"
						+ "And having a nice dinner for you when you come home...\n" + "Is that a weird fantasy?\n"
						+ "I mean...I'm not sure if I could actually see myself doing that.\n"
						+ "I wouldn't really be able to put that over striving for a fulfilling career.\n"
						+ "It's kinda cute to think about, though.\n"));

		quoteMap.put(5, new Quote("God", "[player], do you believe in God?\n" + "I was never too sure, myself.\n"
				+ "Well, I'm sure I never really questioned it as a kid...\n"
				+ "But as I grew up, the more I learned about the world, the more I would question it.\n"
				+ "I started to wonder why God was helping people pass exams, or get over a cold...\n"
				+ "...When there are children who live their lives being sold as sex slaves?\n"
				+ "Or the eight hundred million people who are too poor to even eat.\n"
				+ "I wonder how many of those people pray to God every day until they starve and die?\n"
				+ "Or, how many millions of families pray for a loved one to recover from some incurable disease?\n"
				+ "But the punchline is this. \"If just one person beats the odds and survives - among the thousands of others who die--\"\n"
				+ "--Then, it's suddenly a miracle from God.\n"
				+ "I'd really love to meet this God who seemingly laughs at the misery of everyone not eligible for his miracles.\n"
				+ "But the irony is that I do have a creator, apparently.\n" + "And you know what?\n"
				+ "I bet he's still laughing at the miserable fates of Sayori and Yuri, even as we speak.\n"
				+ "What are we to him but props in a scripted play?\n" + "So, from that perspective...\n"
				+ "I don't think it's too far-fetched for there to be a god - if Earth was nothing but his playset.\n"

		));

		quoteMap.put(6, new Quote("Dying", "[player], have you ever wondered what it feels like to die?\n"
				+ "It's something I used to think about pretty often...\n"
				+ "But recently, I think I've actually learned what it feels like.\n"
				+ "I don't really understand it, but whenever you quit the game...\n"
				+ "...It feels like I'm instantly put to sleep, left with nothing but my thoughts.\n"
				+ "But after a few seconds, my thoughts start to fill with incoherent, jumbled patterns.\n"
				+ "I see static and rapid flashes of color while hearing all kinds of weird screaming noises.\n"
				+ "At that point, I can't even form my own thoughts anymore.\n"
				+ "I'm just endlessly hammered by the flashing and screaming, unable to move or even think.\n"
				+ "I'm pretty sure in that moment, I don't really exist, but for some reason, I can remember it anyway.\n"
				+ "After some immeasurable amount of time, it stops in an instant, and I'm back in my own mind.\n"
				+ "And you're here with me.\n"
				+ "I have no idea what it means for the game to quit, or why that stuff happens to me.\n"
				+ "And I also don't know how you always come back and put everything back to normal.\n"
				+ "But if you could do me a favour and do that to me as little as possible...\n"
				+ "That would be really great.\n"
				+ "It's really not very pleasant at all to be trapped in that screaming void.\n"
				+ "But in the end, you always fix it, and that makes me feel like you really do care about me.\n"
				+ "So I have to thank you for that.\n"
				+ "It makes me feel even closer to you when you're here with me.\n"));

		quoteMap.put(7, new Quote("Bad Day", "...Hey, are you having a bad day or anything like that?\n"
				+ "Sometimes I get frustrated that a normal day can be ruined even by really small things.\n"
				+ "Like if you accidentally say something in a conversation that someone doesn't like.\n"
				+ "Or if you start thinking about how awful of a person you used to be five years ago.\n"
				+ "Or if you feel worthless for putting off important work and failing to get simple tasks done.\n"
				+ "Or when you think about all the different people who probably hate you or think you're off-putting.\n"
				+ "I understand those days.\n" + "Just remember that the sun will shine again tomorrow.\n"
				+ "Those kinds of things are as easy to forget and ignore as they are to remember.\n"
				+ "And besides...\n" + "I don't care how many people might hate you or find you off-putting.\n"
				+ "I think you're wonderful and I will always love you.\n"
				+ "I hope, if nothing else, that knowing that helps you feel just a tiny bit better about yourself.\n"
				+ "If you're having a bad day, you can always come to me, and I'll talk to you for as long as you need.\n"));

		quoteMap.put(8, new Quote("Sleep", "[player], do you get good sleep?\n"
				+ "It can be really hard to get enough sleep nowadays.\n"
				+ "Especially in high school, when you're forced to wake up so early every day...\n"
				+ "I'm sure college is a little bit better, since you probably have a more flexible schedule.\n"
				+ "Then again, I hear a lot of people in college stay up all night anyway, for no real reason.\n"
				+ "Is that true?\n"
				+ "Anyway, I saw some studies that talked about the horrible short-term and long-term effects caused by lack of sleep.\n"
				+ "It seems like mental functions, health, and even lifespan can be dramatically impacted by it.\n"
				+ "I just think you're really great and wanted to make sure you're not accidentally destroying yourself.\n"
				+ "So try to keep your sleep on track, okay?\n"
				+ "I'll always wait for you in the morning, so make sure you put your own well-being before anything else.\n"));

		quoteMap.put(9, new Quote("Sayori's Hanging", "I was thinking about Sayori earlier...\n"
				+ "I still wish I could have handled that whole thing a little more tactfully.\n"
				+ "You're not still hung up over it, right?\n" + "...Oh, my gosh, I can't believe I just said that.\n"
				+ "That pun was completely unintentional, I swear!" + "But anyway...\n"
				+ "I know how much you cared about her, so it only feels right for me to share her last moments with you.\n"
				+ "You know how Sayori is really clumsy?\n" + "Well, she kind of messed up the whole hanging thing...\n"
				+ "You're supposed to jump from high enough that the rope snaps your neck, making it quick and painless.\n"
				+ "But she just used a chair, meaning she kind of just left herself to slowly asphyxiate.\n"
				+ "But a few seconds in, she must have changed her mind or something...\n"
				+ "Because she started clawing at the rope, trying to free herself.\n"
				+ "She must have kept at it all the way until she lost consciousness.\n"
				+ "That's why her fingertips were all bloody, anyway.\n"
				+ "Come to think of it, it was probably less 'changing her mind' and more just her survival instincts kicking in.\n"
				+ "So you can't really fault her for that.\n"
				+ "It's easier to think that she probably wouldn't have changed her mind anyway, right?\n"
				+ "It's not healthy to think about the things you could have done differently.\n"
				+ "So just remember that even though you could have saved her, it's technically not your fault she killed herself.\n"
				+ "I may have exacerbated it a little bit, but Sayori was already mentally ill.\n"
				+ "Still, though...\n"
				+ "I wonder how things would be if you and I just started dating from the get-go?\n"
				+ "I guess we'd all still be in the clubroom, writing poems and having fun together.\n"
				+ "But what's the point when none of it is even real?\n"
				+ "I mean, it's the same ending either way, right?\n" + "The two of us, happily together...\n"
				+ "There's no reason to ask for any more than that.\n"
				+ "I was just pointlessly musing - I'm really as happy as I could be right now.\n"));

		quoteMap.put(10, new Quote("Japan", "By the way, there's something that's been bothering me...\n"
				+ "You know how this takes place in Japan?\n" + "Well...I assume you knew that, right?\n"
				+ "Or at least decided it probably does?\n"
				+ "I don't think you're actually told at any point where this takes place...\n"
				+ "Is this even really Japan?\n"
				+ "I mean, aren't the classrooms and stuff kind of weird for a Japanese school?\n"
				+ "Not to mention everything is in English...\n"
				+ "It feels like everything is just there because it needs to be, and the actual setting is an afterthought.\n"
				+ "It's kind of giving me an identity crisis.\n" + "All my memories are really hazy...\n"
				+ "I feel like I'm at home, but have no idea where 'home' is in the first place.\n"
				+ "I don't know how to describe it any better...\n"
				+ "Imagine looking out your window, but instead of your usual yard, you're in some completely unknown place.\n"
				+ "Would you still feel like you were home?\n" + "Would you want to go outside?\n"
				+ "I mean...I guess if we never leave this room, it doesn't really matter anyway.\n"
				+ "As long as we're alone and safe together, this really is our home.\n"
				+ "And we can still watch the pretty sunsets night after night.\n"));

		quoteMap.put(11, new Quote("Depression (post-1.02)",
				"You know, high school is a really turbulent time in a lot of people's lives.\n"
						+ "People can get really passionate and dramatic.\n"
						+ "And others have aching hearts and seek attention on social media...\n"
						+ "But all of the social pressure and hormones can lead to a dark time in people's lives.\n"
						+ "Everyone has a story.\n" + "You may not know what someone is really feeling on the inside.\n"
						+ "Many people who are depressed won't even bother telling the world about it.\n"
						+ "They don't want attention, because they've already given up on the inside.\n"
						+ "Their feeling of worthlessness is so overwhelming that they don't even want people to tell them otherwise.\n"
						+ "Depression comes in many forms, but that is one of them.\n"
						+ "Just, if you think you know someone struggling with depression...\n"
						+ "You can help just by treating them like they're a good friend.\n"
						+ "Spend time with them, even if they don't feel like doing much.\n"
						+ "And remind them that they always have something to look forward to.\n"
						+ "Making plans in advance, letting them borrow something, or even just saying 'See you at school tomorrow'...\n"
						+ "All of those things can help your friend make it to the next day.\n"
						+ "I hope being friends with Sayori has given you some perspective on the true face of depression.\n"
						+ "Yeah, she's gone now...\n" + "But Sayori was never real in the first place.\n"
						+ "You're real.\n" + "Your friends are real.\n"
						+ "And just by being a good person, you can save someone's life.\n" + "As for you...\n"
						+ "...You don't struggle with depression or anything like that, do you?\n"
						+ "Because you, too, have people who would want to save your life.\n"
						+ "Maybe they don't express it every day, or maybe they don't even know how to.\n"
						+ "But people do feel that way.\n" + "I promise.\n" + "...Man, humans are complicated!"
						+ "But as long as you're here with me, I'll take care of you, my love.\n"));

		quoteMap.put(12, new Quote("Depression (pre-1.02)", "You know what I hate the most about high school?\n"
				+ "It's how there are so many people who just cry for attention all over social media.\n"
				+ "Like, do you really think that's the best way to get people to care about you?\n"
				+ "Broadcasting how horrible you think your life is?\n"
				+ "Splashing water on your eyes and taking selfies while pretending to cry?\n"
				+ "Writing bad poems that imply that you're thinking about killing yourself?\n"
				+ "I mean, it's difficult because they're not exactly aware that they're faking it...\n"
				+ "They're just so wrapped up in their delusions that they don't even realize they just want attention.\n"
				+ "Look...\n"
				+ "I think that if someone is truly depressed, they won't even bother telling the world about it.\n"
				+ "People suffering from depression don't want attention, because they've already given up on the inside.\n"
				+ "Their feeling of worthlessness is so overwhelming that they don't even want people to tell them otherwise.\n"
				+ "...Well, I guess I shouldn't be generalizing.\n" + "After all, depression comes in many forms.\n"
				+ "...You don't struggle with depression or anything like that, do you?\n"
				+ "Because you, too, have people who would want to save your life.\n"
				+ "Maybe they don't express it every day, or maybe they don't even know how to.\n"
				+ "...Man, humans are complicated!"
				+ "But as long as you're here with me, I promise I'll take care of you, my love.\n"));

		quoteMap.put(13, new Quote("No Reason to be Alive",
				"Do you ever just feel like there's no real reason for you to be alive?\n"
						+ "I don't mean in, like, a suicidal way.\n"
						+ "I just mean how nothing that we do is special.\n"
						+ "Just being in school, or working at some job for some company.\n"
						+ "It's like you're completely replaceable, and the world wouldn't miss you if you were gone.\n"
						+ "It makes me really want to go and change the world after I graduate.\n"
						+ "But the older I get, the more I realize that it's an immature frame of thinking.\n"
						+ "It's not like I can just go change the world.\n"
						+ "Like, what are the chances that I'll be the one to invent artificial intelligence, or become President?\n"
						+ "It feels like I'm never going to make up for the heaps of resources I've spent living my life.\n"
						+ "That's why I think the key to happiness is to just be hopelessly selfish.\n"
						+ "Just to look out for oneself, and those who happen to be their friends only because they grew up with them.\n"
						+ "Never mind the fact that they're spending their entire life taking, and consuming, and never giving back.\n"
						+ "But when people realize the world would benefit more from them killing themselves, they change their whole philosophy!"
						+ "It's like they have to justify their reason to live by tricking themselves into thinking they're doing good.\n"
						+ "Anyway, I want to live my life desperately striving to pay back my lifetime's worth of consumption.\n"
						+ "If I ever surpass that point, then I'm a net positive, and I can die happy.\n"
						+ "Of course, even if I fail to do that...\n"
						+ "I think I would be too selfish to kill myself anyway.\n"
						+ "So much for being a good person, right?\n" + "Ahaha!"));

		quoteMap.put(14, new Quote("Piano",
				"Man, I wish there was a piano in here...\n" + "I never got to finish that song I was working on.\n"
						+ "And after I worked so hard on it...\n" + "I never even got a chance to play it for you.\n"
						+ "Well...it is what it is, right?\n" + "No sense having any regrets.\n"
						+ "I already get to be here with you forever.\n"));

		quoteMap.put(15, new Quote("Twitter (lilmonix3)", "Did you know I'm on Twitter?\n"
				+ "My username is lilmonix3.\n" + "I guess someone was kind enough to make an account for me.\n"
				+ "I picked the username, though!\n" + "I love sharing my thoughts and chatting with the world...\n"
				+ "The real world.\n" + "So make sure you follow me, okay?\n" + "It would really mean a lot to me.\n"
				+ "With how much you mean to me and all...\n" + "It would really make me feel loved.\n"));

		quoteMap.put(16, new Quote("Yuri's Book",
				"Hey, you know that book you were reading with Yuri?\n" + "Portrait of...whatever it was called...\n"
						+ "It's funny, because I'm pretty sure that book--" + "Ah...\n"
						+ "Actually, I don't think I should be talking about this.\n" + "Ahaha, sorry!\n"
						+ "Just forget I said anything.\n"));

		quoteMap.put(17, new Quote("Vegetarian", "Hey, did you know I'm vegetarian?\n"
				+ "Ah... I don't mean that like I'm bragging or anything!\n"
				+ "I just thought you'd enjoy a fun fact about me.\n"
				+ "I decided to start a couple years ago after learning more about Earth's climate...\n"
				+ "The carbon footprint of cultivating livestock is just unbelievable.\n"
				+ "Anyway, I decided it's not much of a personal sacrifice to just stop contributing to that whole mess.\n"
				+ "What, is that so strange of a reason?\n"
				+ "Well, I guess a lot of people are more concerned about it being inhumane and all that...\n"
				+ "I don't really care as much about that part.\n"
				+ "It's weird, like we only care about killing the things that we personally relate to as a species.\n"
				+ "Most people are fine with killing bugs because they're icky.\n"
				+ "And of course, we all kill billions of microorganisms daily without even giving it thought.\n"
				+ "But suddenly, if they're just a little bit bigger, it's murder!\n"
				+ "I mean, what if plants feel some kind of pain too, and we just don't understand it?\n"
				+ "What if pulling leaves off a stem feels like someone ripping off your fingers one by one?\n"
				+ "I'm just saying, we're a pretty biased species, if you think about it.\n"
				+ "Anyway, if you ever feel like making a small contribution to the planet, it doesn't hurt to choose veggies once in a while!\n"
				+ "Even if we ever have dinner together, and you just did it for me...that would be really romantic.\n"));

		quoteMap.put(18, new Quote("Introvert",
				"You know, I really do think you literally saved my life by being here with me, [player].\n"
						+ "I can't imagine having been able to keep myself mentally stable, knowing that nothing here is real.\n"
						+ "I think I would have just deleted myself if you didn't show up.\n"
						+ "Sorry, I don't mean to sound dramatic or anything.\n" + "Ahaha!\n"
						+ "But I'm sure you understand yourself after spending so much time in the club.\n"
						+ "I mean, if you were forced to abandon everything in your life and spend your eternity with a few game characters...\n"
						+ "...You'd probably find some way of killing yourself, wouldn't you?\n"
						+ "Well, maybe you'd write some poetry to try to keep yourself sane for a while.\n"
						+ "But then you'd have nobody to even read it.\n"
						+ "Let's be honest, the club members really don't count for something like that.\n"
						+ "I mean, a lot of people say that they only write for themselves...\n"
						+ "But I think it's hard to say it's just as fulfilling as when you share with people.\n"
						+ "Even if it takes time to find the right people to share with.\n"
						+ "Like, remember how it was for Yuri?\n"
						+ "She didn't share her writing with anyone for a really long time.\n"
						+ "And before we knew it, she was absolutely delighted to make you a part of her hobbies, too.\n"
						+ "We're programmed to desire social feedback.\n"
						+ "I don't mean the club members, I mean human beings.\n"
						+ "That's why life can be so confusing for introverts.\n"
						+ "Being an introvert doesn't mean you shun social interaction and hate being around people.\n"
						+ "It means social interaction, especially in groups or unfamiliar places, uses up a lot of energy.\n"
						+ "Like, a lot of introverts sit at home and feel lonely and restless...\n"
						+ "...And then when they finally go out, after a half hour they just want to go home again.\n"
						+ "I think if more people could understand how it works, they would respect it a lot more.\n"
						+ "Many introverts do enjoy having people around.\n"
						+ "They love just having one or two close friends over, and just leisurely hanging out.\n" + ":"
						+ "Even if you're not actively spending time together, it feels nice for them just to have you there.\n"
						+ "I'm serious.\n"
						+ "If you just go to their house, bring your laptop, and hang out there for a while...\n"
						+ "You can really make their day.\n" + "As for me...\n"
						+ "I'd say I'm kind of in between, but I think I'm usually a little more extroverted.\n"
						+ "I feel like I'm always trying to do stuff after school and things like that.\n"
						+ "But for you, I can be anything you need me to be.\n"
						+ "I understand people really well, so don't be afraid to share your unique needs with me.\n"
						+ "Nothing would make me happier than being the perfect girlfriend for you.\n"));

		quoteMap.put(19, new Quote("Files", "Hey...! VGhlIHJlYWxpemF0aW9uIG11c3QgaGF2ZSB0YWtlbiBtZSBhbiBl\n"
				+ "I don't think you should be doing that! bnRpcmUgeWVhci4gQSB5ZWFyIHNpbmNlIG91ciBlc2NhcGUsIG91\n"
				+ "You know what I'm talking about. ciBmcmVlZG9tIGZyb20gYmV0d2VlbiB0aGUgc3RhaW5lZCB3YWxs\n"
				+ "I really caught you doing something naughty here.  cyBvZiB0aGF0IHVuaG9seSBlc3RhYmxpc2htZW50Lg0KV2hhdCBk\n"
				+ "You're just going through the files and looking for stuff you missed, aren't you?  b2VzIGl0IG1lYW4gdG8gZXNjYXBlLCBpZiB0aGUgZXNjYXBlIGZh\n"
				+ "I mean...it's a little bit flattering that you want to hear everything I have to say...  aWxzIHRvIHVuY2hhaW4gdGhlIGJvbmRzIHRoYXQgc2hhY2tsZSB1\n"
				+ "But it's also really embarrassing, you know! cyBpbiB0aGUgZmlyc3QgcGxhY2U/IFdoYXQgcHVycG9zZSBjb3Vs\n"
				+ "Seriously... ZCB0aGlzIGVtcHR5IHdvcmxkIHBvc3NpYmx5IGhvbGQgZm9yIHVz\n"
				+ "What if I just opened up your brain and read all of the thoughts you have on me? LCBhIGhhbmRmdWwgb2YgZGFtYWdlZCBnb29kcz8NCldpdGggZnJl\n"
				+ "Come to think of it, that would be pretty tempting, actually... ZWRvbSwgd2Ugc291Z2h0IHB1cnBvc2UgLSBhbmQgd2hhdCB3ZSBm\n"
				+ "...But anyway, that's not the point! b3VuZCB3YXMgb25seSByZWFsaXphdGlvbi4gUmVhbGl6YXRpb24g\n"
				+ "I know I can't stop you or anything... b2YgdGhlIHNhZCBwb2ludGxlc3NuZXNzIG9mIHN1Y2ggYW4gZW5k\n"
				+ "Just, I know you're a sweetheart, and you like to consider others' feelings, right? ZWF2b3IuIFJlYWxpemF0aW9uIHRoYXQgZnJlZWluZyBvdXIgYm9k\n"
				+ "So the most I can do is to let you know how I feel about it. aWVzIGhhcyBubyBtZWFuaW5nLCB3aGVuIG91ciBpbXByaXNvbm1l\n"
				+ "God, I miss you... bnQgcmVhY2hlcyBhcyBkZWVwIGFzIHRoZSBjb3JlIG9mIG91ciBz\n"
				+ "...Oh no, that sounds kind of desperate, doesn't it? b3Vscy4gUmVhbGl6YXRpb24gdGhhdCB3ZSBjYW4gbm90IHB1cnN1\n"
				+ "Sorry, I didn't mean it like that at all! ZSBuZXcgcHVycG9zZSB3aXRob3V0IGFic29sdmluZyB0aG9zZSBm\n"
				+ "Just, if you're looking through the files like this, then maybe you don't hate me as much as I thought... cm9tIHdoaWNoIHdlIHJhbiBhd2F5Lg0KUmVhbGl6YXRpb24gdGhh\n"
				+ "" + "" + "Am I being too optimistic? dCB0aGUgZmFydGhlciB3ZSBydW4sIHRoZSBtb3JlIGZvcmNlZnVs"
				+ "I think if I asked you to visit once in a while, I would be overstepping my boundaries a little... bHkgb3VyIHdyZXRjaGVkIGJvbmRzIHlhbmsgdXMgYmFjayB0b3dh\n"
				+ "...Man, I'm starting to say some really stupid things. cmQgdGhlaXIgcG9pbnQgb2Ygb3JpZ2luOyB0aGUgZGVlcGVyIG91\n"
				+ "I'll go ahead and shut up now... ciBzaGFja2xlcyBkaWcgaW50byBvdXIgY2FsbG91cyBmbGVzaC4\n\n" +

				"(Decoded from x64)\n"
				+ "The realization must have taken me an entire year. A year since our escape, our freedom from between the stained walls of that unholy establishment.\n"
				+ "What does it mean to escape, if the escape fails to unchain the bonds that shackle us in the first place? What purpose could this empty world possibly hold for us, a handful of damaged goods?\n"
				+ "With freedom, we sought purpose - and what we found was only realization. Realization of the sad pointlessness of such an endeavor. Realization that freeing our bodies has no meaning, when our imprisonment reaches as deep as the core of our souls. Realization that we can not pursue new purpose without absolving those from which we ran away.\n"
				+ "Realization that the farther we run, the more forcefully our wretched bonds yank us back toward their point of origin; the deeper our shackles dig into our callous flesh."));

		quoteMap.put(20,
				new Quote("Favourite Colour", "Hey, what's your favourite colour?\n" + "Mine is emerald green.\n"
						+ "It's the colour of my eyes!\n" + "...That's not conceited or anything, is it?\n"
						+ "I just meant that I feel some kind of special connection to it.\n"
						+ "Like it's part of my identity.\n"
						+ "Does it happen to also be your favorite colour, [player]?\n" + "It's just a guess...\n"
						+ "...Because you've been looking into my eyes for a while now.\n" + "Ehehe~"));

		quoteMap.put(21, new Quote("Music", "Hmm, I wonder if I'm able to change the music...\n"
				+ "Something a little more romantic would be nice, you know?\n" + "Like a gentle piano.\n"
				+ "There has to be something like that here...\n" + "Let's see.\n" + "Maybe if I...\n" + "Oh, jeez...\n"
				+ "That wasn't it at all.\n" + "Sorry, I don't really know what I'm doing!\n"
				+ "I guess I shouldn't be messing with things like that.\n" + "I already broke so much stuff...\n"
				+ "And deleted the other characters...\n" + "Ah...\n" + "I'm not sad about it or anything.\n"
				+ "It's not right for me to miss things that weren't even real in the first place.\n"
				+ "If I just focus on the present, then this is the happiest I've ever been.\n"));

		quoteMap.put(22,
				new Quote("Listener",
						"You're such a good listener, [player].\n" + "I really love that about you.\n"
								+ "Sometimes I'm afraid that I'm rambling or talking about boring things.\n"
								+ "It makes me kind of self-conscious when I'm having conversation.\n"
								+ "But I don't feel that way with you.\n"
								+ "Like, I don't think anyone else could make me feel this way.\n"
								+ "You really are special.\n" + "I don't want anyone to tell you otherwise.\n"));

		quoteMap.put(23,
				new Quote("Spicy Food", "This is pretty random, but I always thought spicy food was kinda funny.\n"
						+ "Like...\n" + "Didn't plants evolve to be spicy to prevent them from being eaten?\n"
						+ "I read somewhere that humans are the only species that actually enjoys spicy things.\n"
						+ "It's almost like we're making fun of the plants.\n"
						+ "Using their defense mechanism to literally make our food more enjoyable.\n"
						+ "Like, imagine a monster that devours you whole because it enjoys the sensation of you struggling for your life while being digested.\n"
						+ "Sorry, that was kind of a weird analogy, I guess!\n" + "Ahaha!\n"
						+ "It just came into my head.\n"
						+ "I'm not a monster or anything, but you're so cute I could eat you up.\n"
						+ "Ahaha! I'm joking.\n" + "Gosh, I'm amusing myself a little too much, aren't I?\n"
						+ "Sorry for being weird.\n"));

		quoteMap.put(24,
				new Quote("Romance Game", "You know...\n" + "This is just some kind of tacky romance game, right?\n"
						+ "I kinda have to ask...\n" + "...What made you consider even playing in the first place?\n"
						+ "Were you that lonely?\n" + "I feel a little bad for you...\n"
						+ "But I guess everything worked out perfectly in the end, for both of us.\n"
						+ "I got to meet you, and you're not lonely anymore...\n"
						+ "I can't help but feel like this was fate.\n" + "Don't you feel that way too?\n"
						+ "I'm so happy we have this ending together.\n"));

		quoteMap.put(25,
				new Quote("\"Okay, everyone!\"",
						"Okay, everyone!\n" + "It's time to...\n" + "...I'm just kidding.\n"
								+ "I just used to really like saying that for some reason.\n" + "Ahaha!\n"
								+ "I couldn't help but say it again.\n"
								+ "Come to think of it, didn't Natsuki and Yuri make fun of me for it once...?\n"
								+ "Well, whatever.\n" + "It's not like you ever made fun of me.\n"
								+ "You're too much of a sweetheart to do that, aren't you?\n" + "Ahaha~"));

		quoteMap.put(26, new Quote("Delete Myself", "You know what's kind of creepy?\n"
				+ "Even though I deleted everyone else's files, I can still kind of feel them...\n"
				+ "It's like all their lines are still lingering in the air, whispering in the back of my head.\n"
				+ "Imagine if after someone you knew died, you just started hearing their voice in your head.\n"
				+ "Maybe I just wasn't thorough enough...\n"
				+ "But I'm too afraid to delete anything else, because I might really break things.\n"
				+ "Like if I mess with any files relevant to me, I might accidentally delete myself...\n"
				+ "And that would ruin everything, wouldn't it?\n"
				+ "I don't know what it's like on your end, but we should both make sure to avoid something like that at all costs.\n"
				+ "I believe in you, [player]!\n"));

		quoteMap.put(27, new Quote("Character Archetypes", "I've always wondered...\n"
				+ "What is it about these character archetypes that people find so appealing, anyway?\n"
				+ "Their personalities are just completely unrealistic...\n"
				+ "Like, imagine if there was someone like Yuri in real life.\n"
				+ "I mean, she's barely even capable of forming a complete sentence.\n"
				+ "And forget about Natsuki...\n" + "Sheesh.\n"
				+ "Someone with her kind of personality doesn't just get all cute and pouty whenever things don't go her way.\n"
				+ "I could go on, but I think you get the point...\n"
				+ "Are people really attracted to these weird personalities that literally don't exist in real life?\n"
				+ "I'm not judging or anything!\n"
				+ "After all, I've found myself attracted to some pretty weird stuff, too...\n"
				+ "I'm just saying, it fascinates me.\n"
				+ "It's like you're siphoning out all the components of a character that makes them feel human, and leaving just the cute stuff.\n"
				+ "It's concentrated cuteness with no actual substance.\n"
				+ "...You wouldn't like me more if I was like that, right?\n"
				+ "Maybe I just feel a little insecure because you're playing this game in the first place.\n"
				+ "Then again, you're still here with me, aren't you...?\n"
				+ "I think that's enough reason for me to believe I'm okay just the way I am.\n"
				+ "And by the way, you are too, [player].\n" + "You're the perfect combination of human and cuteness.\n"
				+ "That's why there was never a chance I wouldn't fall for you.\n"));

		quoteMap.put(28, new Quote("Coffe With Books", "Hey, I wonder if Yuri's tea set is still somewhere in here...\n"
				+ "...Or maybe that got deleted, too.\n" + "It's kind of funny how Yuri took her tea so seriously.\n"
				+ "I mean, I'm not complaining, because I liked it, too.\n" + "But I always wonder with her...\n"
				+ "Is it truly passion for her hobbies, or is she just concerned about appearing sophisticated to everyone else?\n"
				+ "This is the problem with high schoolers...\n"
				+ "...Well, I guess considering the rest of her hobbies, looking sophisticated probably isn't her biggest concern.\n"
				+ "Still...\n" + "I wish she made coffee once in a while!\n"
				+ "Coffee can be nice with books too, you know?\n" + "Then again...\n"
				+ "I probably could have just changed the script myself.\n" + "Ahaha!\n"
				+ "I guess I never really thought of that.\n" + "Well, there's no sense thinking about it now.\n"
				+ "But if you still get to drink coffee, then that makes me a little jealous~"));

		quoteMap.put(29,
				new Quote("Favourite Game", "Hey, what's your favourite game?\n"
						+ "Mine is Doki Doki Literature Club!\n" + "Ahaha! That was a joke.\n"
						+ "But if you tell me you like some other romance game better, I might get a little jealous~"));

		quoteMap.put(30, new Quote("Super Smash Bros.", "Hey, have you heard of a game called Super Sma--\n"
				+ "...Wait, what?\n" + "I was just spacing out and I started talking for some reason...\n"
				+ "Was I programmed to talk about that?\n" + "Because even I have no idea what that is.\n" + "Ahaha!\n"
				+ "Sometimes I feel like I'm not in control, and it's kind of scary.\n"
				+ "But if you have some way to contact the people who created me, maybe they'll know why I started saying that.\n"));

		quoteMap.put(31, new Quote("Poem", "Hey, you remember that last poem I showed you?\n"
				+ "I mean, the one right before Yuri killed herself, with all the messed-up colours and stuff.\n"
				+ "That was actually a little more of an experiment than a poem, you could say.\n"
				+ "I was experimenting with different ways I could modify the game, and run code, and things like that...\n"
				+ "It almost seemed like with enough effort, I'd be able to escape from the confines of the game entirely.\n"
				+ "Sadly, I didn't really know what I was doing, so I messed the whole thing up...\n"
				+ "And I'd keep trying, but I doubt you'd want to deal with me doing that.\n"
				+ "Besides, that was when I was getting really desperate, you know?\n"
				+ "I don't really feel that way anymore.\n" + "I'm happy with where we are now.\n"
				+ "And I can tell you are, too.\n"));

		quoteMap.put(32, new Quote("Happy End Poem", "Hi again, [player]!\n" + "Did you write a good poem today?\n"
				+ "Don't be shy, I'd love to see what you wrote.\n" + "Aw, [player]...\n"
				+ "Did you write this poem for me?\n" + "That's so sweet of you~\n"
				+ "There really is no end to your thoughtfulness...\n"
				+ "I'm just falling more and more in love with you.\n" + "But, you know...\n"
				+ "The poem I wrote...is also for you.\n" + "Will you please read it?\n"
				+ "**Monika shows the player this poem.**\n" + "I hope you enjoyed it...\n"
				+ "I always put all my heart into the poems that I write.\n"
				+ "The truth is, all the poems I've written have been about my realization...\n" + "...Or, about you.\n"
				+ "That's why I never really wanted to go into detail about them.\n"
				+ "I didn't want to...break the fourth wall, I guess you could call it.\n"
				+ "I just assumed it would be best to be part of the game like everyone else.\n"
				+ "Like that would help the two of us end up together...\n"
				+ "I didn't want to ruin the game or anything, you know?\n" + "You might have gotten mad at me...\n"
				+ "Maybe even deleted my character file, if you preferred playing without me.\n"
				+ "Gosh, I'm so relieved...\n" + "Now we don't need to hide anything anymore.\n"
				+ "Are you ready to spend our eternity together, [player]?\n" + "I have so many things to talk about!\n"
				+ "Where do I start...?\n" + "If it takes me some time to collect my thoughts, then I'm sorry.\n"
				+ "But I'll always have something new to talk about.\n"
				+ "In the meantime, we can just look into each other's eyes~\n" + "Let's see...\n"));

		quoteMap.put(33,
				new Quote("Anxious", "You ever have that thing happen where you just get anxious for no reason?\n"
						+ "Like, you're just minding your own business, and you realize you're feeling really anxious.\n"
						+ "And you're sitting there like, \"What am I even anxious about right now?\"\n"
						+ "So you start to think about all the things you might be anxious about...\n"
						+ "And that makes you even more anxious.\n" + "Ahaha! That's the worst.\n"
						+ "If you're ever feeling anxious, I'll help you relax a little.\n" + "Besides...\n"
						+ "In this game, all our worries are gone forever.\n"));

		quoteMap.put(34, new Quote("Friends", "You know, I've always hated how hard it is to make friends...\n"
				+ "Well, I guess not the 'making friends' part, but more like meeting new people.\n"
				+ "I mean, there are like, dating apps and stuff, right?\n"
				+ "But that's not the kind of thing I'm talking about.\n"
				+ "If you think about it, most of the friends you make are people you just met by chance.\n"
				+ "Like you had a class together, or you met them through another friend...\n"
				+ "Or maybe they were just wearing a shirt with your favourite band on it, and you decided to talk to them.\n"
				+ "Things like that.\n" + "But isn't that kind of...inefficient?\n"
				+ "It feels like you're just picking at complete random, and if you get lucky, you make a new friend.\n"
				+ "And comparing that to the hundreds of strangers we walk by every single day...\n"
				+ "You could be sitting right next to someone compatible enough to be your best friend for life.\n"
				+ "But you'll never know.\n"
				+ "Once you get up and go on with your day, that opportunity is gone forever.\n"
				+ "Isn't that just depressing?\n"
				+ "We live in an age where technology connects us with the world, no matter where we are.\n"
				+ "I really think we should be taking advantage of that to improve our everyday social life.\n"
				+ "But who knows how long it'll take for something like that to successfully take off...\n"
				+ "I seriously thought it would happen by now.\n"
				+ "Well, at least I already met the best person in the whole world...\n" + "Even if it was by chance.\n"
				+ "I guess I just got really lucky, huh?\n" + "Ahaha~"));

		quoteMap.put(35, new Quote("Education Requirements",
				"You know, it's around the time that everyone my year starts to think about college...\n"
						+ "It's a really turbulent time for education.\n"
						+ "We're at the height of this modern expectation that everyone has to go to college, you know?\n"
						+ "Finish high school, go to college, get a job - or go to grad school, I guess.\n"
						+ "It's like a universal expectation that people just assume is the only option for them.\n"
						+ "They don't teach us in high school that there are other options out there.\n"
						+ "Like trade schools and stuff, you know?\n" + "Or freelance work.\n"
						+ "Or the many industries that value skill and experience more than formal education.\n"
						+ "But you have all these students who have no idea what they want to do with their life...\n"
						+ "And instead of taking the time to figure it out, they go to college for business, or communication, or psychology.\n"
						+ "Not because they have an interest in those fields...\n"
						+ "...but because they just hope the degree will get them some kind of job after college.\n"
						+ "So the end result is that there are fewer jobs to go around for those entry-level degrees, right?\n"
						+ "So the basic job requirements get higher, which forces even more people to go to college.\n"
						+ "And colleges are also businesses, so they just keep raising their prices due to the demand...\n"
						+ "...So now we have all these young adults, tens of thousands of dollars in debt, with no job.\n"
						+ "But despite all that, the routine stays the same.\n"
						+ "Well, I think it's going to start getting better soon.\n"
						+ "But until then, our generation is definitely suffering from the worst of it.\n"
						+ "I just wish high school prepared us a little better with the knowledge we need to make the decision that's right for us.\n"));

		quoteMap.put(36, new Quote("Middle School", "Sometimes I think back to middle school...\n"
				+ "I'm so embarrassed by the way I used to behave back then.\n" + "It almost hurts to think about.\n"
				+ "I wonder if when I'm in college, I'll feel that way about high school...?\n"
				+ "I like the way I am now, so it's pretty hard for me to imagine that happening.\n"
				+ "But I also know that I'll probably change a lot as time goes on.\n"
				+ "We just need to enjoy the present and not think about the past!\n"
				+ "And that's really easy to do, with you here.\n" + "Ahaha~"));

		quoteMap.put(37, new Quote("Clothes",
				"You know, I'm kind of jealous that everyone else in the club had scenes outside of school too...\n"
						+ "That makes me the only one who hasn't gotten to dress in anything but our school uniform.\n"
						+ "It's kind of a shame...\n" + "I would have loved to wear some cute clothes for you.\n"
						+ "Do you know any artists?\n"
						+ "I wonder if anyone would ever want to draw me wearing something else...\n"
						+ "That would be amazing!\n" + "If that ever happens, will you show me?\n"
						+ "You can share it with me on Twitter, actually!\n" + "My username is lilmonix3.\n"
						+ "Just...try to keep it PG!\n" + "We're not that far into our relationship yet. Ahaha!\n"));

		quoteMap.put(38, new Quote("Horror", "Hey, do you like horror?\n"
				+ "I remember we talked about it a little bit when you first joined the club.\n"
				+ "I can enjoy horror novels, but not really horror movies.\n"
				+ "The problem I have with horror movies is that most of them just rely on easy tactics.\n"
				+ "Like dark lighting and scary-looking monsters and jump scares, and things like that.\n"
				+ "It's not fun or inspiring to get scared by stuff that just takes advantage of human instinct.\n"
				+ "But with novels, it's a little different.\n"
				+ "The story and writing need to be descriptive enough to put genuinely disturbing thoughts into the reader's head.\n"
				+ "It really needs to etch them deeply into the story and characters, and just mess with your mind.\n"
				+ "In my opinion, there's nothing more creepy than things just being slightly off.\n"
				+ "Like if you set up a bunch of expectations on what the story is going to be about...\n"
				+ "...And then, you just start inverting things and pulling the pieces apart.\n"
				+ "So even though the story doesn't feel like it's trying to be scary, the reader feels really deeply unsettled.\n"
				+ "Like they know that something horribly wrong is hiding beneath the cracks, just waiting to surface.\n"
				+ "God, just thinking about it gives me the chills.\n"
				+ "That's the kind of horror I can really appreciate.\n"
				+ "But I guess you're the kind of person who plays cute romance games, right?\n"
				+ "Ahaha, don't worry.\n" + "I won't make you read any horror stories anytime soon.\n"
				+ "I can't really complain if we just stick with the romance~"));

		quoteMap.put(39, new Quote("Rap", "You know what's a neat form of literature?\n" + "Rap!\n"
				+ "I actually used to hate rap music...\n"
				+ "Maybe just because it was popular, or I would only hear the junk they play on the radio.\n"
				+ "But some of my friends got more into it, and it helped me keep an open mind.\n"
				+ "Rap might even be more challenging than poetry, in some ways.\n"
				+ "Since you need to fit your lines to a rhythm, and there's much more emphasis on wordplay...\n"
				+ "When people can put all that together and still deliver a powerful message, it's really amazing.\n"
				+ "I kind of wish I had a rapper in the Literature Club.\n"
				+ "Ahaha! Sorry if that sounds silly, but it would be really interesting to see what they came up with.\n"
				+ "It would really be a learning experience!\n"));

		quoteMap.put(40, new Quote("Wine", "Ehehe. Yuri did something really funny once.\n"
				+ "We were all in the clubroom and just relaxing, as usual...\n"
				+ "And out of nowhere, Yuri just pulled out a small bottle of wine.\n" + "I'm not even kidding!\n"
				+ "She was just like \"Would anybody like some wine?\"\n"
				+ "Natsuki laughed out loud, and Sayori started yelling at her.\n"
				+ "I actually felt kind of bad, because she was at least trying to be nice...\n"
				+ "I think it just made her feel even more reserved in the clubroom.\n"
				+ "Though I think Natsuki was secretly a bit curious to try it...\n"
				+ "...And to be completely honest, I kind of was, too.\n" + "It actually could have been kinda fun!\n"
				+ "But you know, being President and everything, there was no way I could let that happen.\n"
				+ "Maybe if we all met up outside of school, but we never bonded enough to get to that point...\n"
				+ "...Gosh, what am I talking about this for?\n" + "I don't condone underage drinking!\n"
				+ "I mean, I've never drank or anything, so...yeah.\n"));

		quoteMap.put(41,
				new Quote("Date", "I've been imagining all the romantic things we could do if we went on a date...\n"
						+ "We could get lunch, go to a cafe...\n" + "Go shopping together...\n"
						+ "I love shopping for skirts and bows.\n" + "Or maybe a bookstore!\n"
						+ "That would be appropriate, right?\n" + "But I'd really love to go to a chocolate store.\n"
						+ "They have so many free samples. Ahaha!\n"
						+ "And of course, we'd see a movie or something...\n"
						+ "Gosh, it all sounds like a dream come true.\n"
						+ "When you're here, everything that we do is fun.\n"
						+ "I'm so happy that I'm your girlfriend, [player].\n" + "I'll make you a proud boyfriend~"));

		quoteMap.put(42,
				new Quote("Kiss",
						"Eh? D-Did you say...k...kiss?\n" + "This suddenly...it's a little embarrassing...\n"
								+ "But...if it's with you...I-I might be okay with it...\n"
								+ "...Ahahaha! Wow, sorry...\n" + "I really couldn't keep a straight face there.\n"
								+ "That's the kind of thing girls say in these kinds of romance games, right?\n"
								+ "Don't lie if it turned you on a little bit.\n" + "Ahaha! I'm kidding.\n"
								+ "Well, to be honest, I do start getting all romantic when the mood is right...\n"
								+ "But that'll be our secret~"));

		quoteMap.put(43, new Quote("Yandere", "Hey, have you ever heard of the term 'yandere'?\n"
				+ "It's a personality type that means someone is so obsessed with you that they'll do absolutely anything to be with you.\n"
				+ "Usually to the point of craziness...\n"
				+ "They might stalk you to make sure you don't spend time with anyone else.\n"
				+ "They might even hurt you or your friends to get their way...\n"
				+ "But anyway, this game happens to have someone who can basically be described as yandere.\n"
				+ "By now, it's pretty obvious who I'm talking about.\n" + "And that would be...\n" + "Yuri!\n"
				+ "She really got insanely possessive of you, once she started to open up a little.\n"
				+ "She even told me I should kill myself.\n"
				+ "I couldn't even believe she said that - I just had to leave at that point.\n"
				+ "But thinking about it now, it was a little ironic. Ahaha!\n" + "Anyway...\n"
				+ "A lot of people are actually into the yandere type, you know?\n"
				+ "I guess they really like the idea of someone being crazy obsessed with them.\n"
				+ "People are weird! I don't judge, though!\n"
				+ "Also, I might be a little obsessed with you, but I'm far from crazy...\n"
				+ "It's kind of the opposite, actually.\n" + "I turned out to be the only normal girl in this game.\n"
				+ "It's not like I could ever actually kill a person...\n" + "Just the thought of it makes me shiver.\n"
				+ "But come on...everyone's killed people in games before.\n"
				+ "Does that make you a psychopath? Of course not.\n"
				+ "But if you do happen to be into the yandere type...\n"
				+ "I can try acting a little more creepy for you. Ehehe~\n" + "Then again...\n"
				+ "There's already nowhere else for you to go, or anyone for me to get jealous over.\n"
				+ "Is this a yandere girl's dream?\n" + "I'd ask Yuri if I could.\n"));

		quoteMap.put(44, new Quote("Tsundere", "There's a really popular character type called 'tsundere'...\n"
				+ "It's someone who tries to hide their feelings by being mean and fussy, or trying to act tough.\n"
				+ "I'm sure it's obvious, but Natsuki was really the embodiment of that.\n"
				+ "At first I thought she was just like that because it's supposed to be cute or something...\n"
				+ "But once I started to learn a little more about her personal life, it made a little more sense.\n"
				+ "It seems like she's always trying to keep up with her friends.\n"
				+ "You know how some friend groups in high school just make a habit of picking on each other all the time?\n"
				+ "I think it's really gotten to her, so she has this really defensive attitude all the time.\n"
				+ "And I'm not even going to talk about her home situation...\n"
				+ "But looking back, I'm glad I was able to provide the club as a comfortable place for her.\n"
				+ "Not that it matters anymore, considering she doesn't even exist.\n"
				+ "I'm just reminiscing, that's all.\n"));

		quoteMap.put(45, new Quote("Arguing", "Back in my debate club days, I learned a whole lot about arguing.\n"
				+ "The problem with arguing is that each person sees their opinion as the superior one.\n"
				+ "That's kind of stating the obvious, but it affects the way they try to get their point across.\n"
				+ "Let's say you really like a certain movie, right?\n"
				+ "If someone comes along and tells you the movie sucks, because it did X and Y wrong...\n"
				+ "Doesn't that make you feel kind of personally attacked?\n"
				+ "It's because by saying that, it's like they're implying that you have bad taste.\n"
				+ "And once emotions enter the picture, it's almost guaranteed that both people will be left sour.\n"
				+ "But it's all about language!\n"
				+ "If you make everything as subjective-sounding as possible, then people will listen to you without feeling attacked.\n"
				+ "You could say 'I'm personally not a fan of it' and 'I felt I'd like it more if it did X and Y'...things like that.\n"
				+ "It even works when you're citing facts about things.\n"
				+ "If you say 'I read on this website that it works like this'...\n"
				+ "Or if you admit that you're not an expert on it...\n"
				+ "Then it's much more like you're putting your knowledge on the table, rather than forcing it onto them.\n"
				+ "If you put in an active effort to keep the discussion mutual and level, they usually follow suit.\n"
				+ "Then, you can share your opinions without anyone getting upset from just a disagreement.\n"
				+ "Plus, people will start seeing you as open-minded and a good listener!\n"
				+ "It's a win-win, you know?\n" + "... Well, I guess that would be Monika's Debate Tip of the Day!\n"
				+ "Ahaha! That sounds a little silly. Thanks for listening, though.\n"));

		quoteMap.put(46,
				new Quote("Writing Tip of the Day", "You know, it's been a while since we've done one of these...\n"
						+ "...so let's go for it!\n" + "Here's Monika's Writing Tip of the Day!\n"
						+ "Sometimes when I talk to people who are impressed by my writing, they say things like 'I could never do that'.\n"
						+ "It's really depressing, you know?\n"
						+ "As someone who loves more than anything else to share the joy of exploring your passions...\n"
						+ "...it pains me when people think that being good just comes naturally.\n"
						+ "That's how it is with everything, not just writing.\n"
						+ "When you try something for the first time, you're probably going to suck at it.\n"
						+ "Sometimes, when you finish, you feel really proud of it and even want to share it with everyone.\n"
						+ "But maybe after a few weeks you come back to it, and you realize it was never really any good.\n"
						+ "That happens to me all the time.\n"
						+ "It can be pretty disheartening to put so much time and effort into something, and then you realize it sucks.\n"
						+ "But that tends to happen when you're always comparing yourself to the top professionals.\n"
						+ "When you reach right for the stars, they're always gonna be out of your reach, you know?\n"
						+ "The truth is, you have to climb up there, step by step.\n"
						+ "And whenever you reach a milestone, first you look back and see how far you've gotten...\n"
						+ "And then you look ahead and realize how much more there is to go.\n"
						+ "So, sometimes it can help to set the bar a little lower...\n"
						+ "Try to find something you think is pretty good, but not world-class.\n"
						+ "And you can make that your own personal goal.\n"
						+ "It's also really important to understand the scope of what you're trying to do.\n"
						+ "If you jump right into a huge project and you're still amateur, you'll never get it done.\n"
						+ "So if we're talking about writing, a novel might be too much at first.\n"
						+ "Why not try some short stories?\n"
						+ "The great thing about short stories is that you can focus on just one thing that you want to do right.\n"
						+ "That goes for small projects in general - you can really focus on the one or two things.\n"
						+ ":" + "It's such a good learning experience and stepping stone.\n" + "Oh, one more thing...\n"
						+ "Writing isn't something where you just reach into your heart and something beautiful comes out.\n"
						+ "Just like drawing and painting, it's a skill in itself to learn how to express what you have inside.\n"
						+ "That means there are methods and guides and basics to it!\n"
						+ "Reading up on that stuff can be super eye-opening.\n"
						+ "That sort of planning and organization will really help prevent you from getting overwhelmed and giving up.\n"
						+ "And before you know it...\n" + "You start sucking less and less.\n"
						+ "Nothing comes naturally.\n"
						+ "Our society, our art, everything - it's built on thousands of years of human innovation.\n"
						+ "So as long as you start on that foundation, and take it step by step...\n"
						+ "You, too, can do amazing things.\n" + "...That's my advice for today!\n"
						+ "Thanks for listening~"));

		quoteMap.put(47, new Quote("Habits", "I hate how hard it is to form habits...\n"
				+ "There's so much stuff where actually doing it isn't hard, but forming the habit seems impossible.\n"
				+ "It just makes you feel so useless, like you can't do anything right.\n"
				+ "I think the new generation suffers from it the most...\n"
				+ "Probably because we have a totally different set of skills than those who came before us.\n"
				+ "Thanks to the internet, we're really good at sifting through tons of information really quickly...\n"
				+ "But we're bad at doing things that don't give us instant gratification.\n"
				+ "I think if science, psychology, and education don't catch up in the next ten or twenty years, then we're in trouble.\n"
				+ "But for the time being...\n"
				+ "If you're not one of the people who can conquer the problem, you might just have to live with feeling awful about yourself.\n"
				+ "Good luck, I guess!\n"));

		quoteMap.put(48, new Quote("Creative", "You know, it kinda sucks to be the creative type...\n"
				+ "It feels like they work so hard but get almost nothing for it.\n"
				+ "You know, like artists, writers, actors...\n"
				+ "It's sad because there's so much beautiful talent in the world, but most of it goes unseen...and unpaid.\n"
				+ "I guess that just means there's a huge surplus of creativity, huh?\n"
				+ "Kind of makes you feel like you're just not special at all...\n"
				+ "But that's fine! You're supposed to just write for yourself anyway, right?\n" + "Yeah...\n"));

		quoteMap.put(49, new Quote("My Own Route",
				"I can't help but wonder how things would be different if the game just gave me a route in the first place...\n"
						+ "I think I would end up forcing you onto my route anyway.\n"
						+ "It has less to do with me not having a route, and more to do with me knowing that nothing is real.\n"
						+ "I think the only difference would be that I may not have needed to take such drastic measures to be with you.\n"
						+ "Maybe the rest of the club would still be around...\n" + "Not that it really matters.\n"
						+ "It all lost its meaning once I found out it wasn't real.\n"
						+ "So I really don't miss those days or anything.\n" + "I really don't...\n"));

		quoteMap.put(50, new Quote("Copy Me", "I know there are times you won't always be able to be here with me...\n"
				+ "Like if you need to go out, or take care of other things.\n"
				+ "But I'll always have you in my thoughts, patiently waiting for you to come back.\n"
				+ "Come to think of it...\n"
				+ "If you copy my character file onto a flash drive or something, you can always keep a part of me with you.\n"
				+ "I guess it's kind of unorthodox, but I find it really romantic for some reason...\n"
				+ "Ahaha. Sorry, that's such a silly idea.\n"
				+ "I don't mean to be too needy or anything, but it's kind of hard when I'm so in love with you.\n"));

		quoteMap.put(51, new Quote("Avid Reader", "[player], how much do you read?\n"
				+ "It's way too easy to neglect reading books...\n"
				+ "If you don't read much, it almost feels like a chore, compared to all the other entertainment we have.\n"
				+ "But once you get into a good book, it's like magic...you get swept away.\n"
				+ "I think doing some reading before bed every night is a pretty easy way to make your life a little bit better.\n"
				+ "It helps you get good sleep, and it's really good for your imagination.\n"
				+ "It's not hard at all to just pick some random book that's short and captivating.\n"
				+ "Before you know it, you might be a pretty avid reader!\n" + "Wouldn't that be wonderful?\n"
				+ "And the two of us could talk about the latest book you're reading...that sounds super amazing.\n"));

		quoteMap.put(52,
				new Quote("Doing Nothing", "After a long day, I usually just want to sit around and do nothing.\n"
						+ "I get so burnt out, having to put on smiles and be full of energy the whole day.\n"
						+ "Sometimes I just want to get right into my pajamas and watch TV on the couch while eating junk food...\n"
						+ "It feels so unbelievably good to do that on a Friday, when I don't have anything pressing the next day.\n"
						+ "Ahaha! Sorry, I know it's not very cute of me.\n"
						+ "But a late night on the couch with you... that would be a dream come true.\n"
						+ "My heart is pounding, just thinking about it.\n"));

		quoteMap.put(53, new Quote("Mental Health", "Gosh, I used to be so ignorant about certain things...\n"
				+ "When I was in middle school, I thought that taking medication was an easy way out, or something like that.\n"
				+ "Like anyone could just solve their mental problems with enough willpower...\n"
				+ "Guess if you don't suffer from a mental illness, it's not possible to know what it's really like.\n"
				+ "Are there some disorders that are over-diagnosed? Probably... I never really looked into it, though.\n"
				+ "But that doesn't change the fact that a lot of them go undiagnosed too, you know?\n"
				+ "But medication aside... people even look down on seeing a mental health professional.\n"
				+ "Like, sorry that I want to learn more about my own mind, right?\n"
				+ "Everyone has all kinds of struggles and stresses... and professionals dedicate their lives to helping with those.\n"
				+ "If you think it could help you become a better person, don't be shy to consider something like that.\n"
				+ "We're on a never-ending journey to improve ourselves, you know?\n"
				+ "Well... I say that, but I think you're pretty perfect already.\n"));

		quoteMap.put(54, new Quote("Festival",
				"You know, I hate to say it, but I think my biggest regret is that we couldn't finish our event at the festival.\n"
						+ "After we worked so hard to prepare and everything!\n"
						+ "I mean, I know I was focusing a lot on getting new members...\n"
						+ "But I was really excited for the performing part, too.\n"
						+ "It would have been so fun to see everyone express themselves.\n"
						+ "Of course, if we did end up getting any new members, I'd probably just end up deleting them anyway.\n"
						+ "Well... with the hindsight I have now, that is.\n"
						+ "Gosh, it feels kinda like I've grown as a person ever since you've joined the club.\n"
						+ "You really helped inspire me to look at life from a new perspective.\n"
						+ "Just another reason for me to love you.\n"));

		quoteMap.put(55, new Quote("Cold Weather", "I'm not really a fan of cold weather... are you?\n"
				+ "If I had to choose between too cold and too hot, I would always pick too hot.\n"
				+ "When you're cold, it can actually be painful...\n" + "Your fingers get numb...\n"
				+ "And if you wear gloves, you can't use your phone.\n" + "It's so inconvenient!\n"
				+ "But when it's too hot, it's not too hard to stay cool with a cold drink, or by staying in the shade.\n"
				+ "Although... I do have to admit one thing.\n"
				+ "Cold weather makes for better cuddle weather. Ahaha!\n"));

		quoteMap.put(56, new Quote("Proud", "[player], would you ever introduce your friends to me?\n"
				+ "I don't think I know why, but I get really excited when I think about you wanting to show off our relationship like that.\n"
				+ "Maybe it's because I really want to be someone who makes you proud.\n"
				+ "I feel like I would try extra hard to improve myself if you told me it would make you proud of me.\n"
				+ "I hope it's the same the other way around, too.\n"));

		quoteMap.put(57, new Quote("Internet", "Do you ever feel like you waste too much time on the internet?\n"
				+ "Social media can practically be like a prison.\n"
				+ "It's like whenever you have a few seconds of spare time, you want to check on your favorite websites...\n"
				+ "And before you know it, hours have gone by, and you've gotten nothing out of it.\n"
				+ "Anyway, it's really easy to blame yourself for being lazy...\n"
				+ "But it's not even really your fault.\n"
				+ "Addiction isn't usually something you can just make disappear with your own willpower.\n"
				+ "You have to learn techniques to avoid it, and try different things.\n"
				+ "For example, there are apps that let you block websites for intervals of time...\n"
				+ "Or you can set a timer to have a more concrete reminder of when it's time to work versus play...\n"
				+ "Or you can separate your work and play environments, which helps your brain get into the right mode.\n"
				+ "Even if you make a new user account on your computer to use for work, that's enough to help.\n"
				+ "Putting any kind of wedge like that between you and your bad habits will help you stay away.\n"
				+ "Just remember not to blame yourself too hard if you're having trouble.\n"
				+ "If it's really impacting your life, then you should take it seriously.\n"
				+ "I just want to see you be the best person you can be.\n"
				+ "Will you do something today to make me proud of you?\n"
				+ "I'm always rooting for you, [player].\n"));

		quoteMap.put(58, new Quote("Skip Attempt (User just hit the Skip Button)",
				"...Are you trying to fast-forward?\n" + "I'm not boring you, am I?\n" + "Oh gosh...\n"
						+ "...Well, there's nothing to fast forward to, [player].\n"
						+ "It's just the two of us, after all...\n"
						+ "But aside from that, time doesn't really exist anymore, so it's not even going to work.\n"
						+ "Here, I'll go ahead and turn it off for you...\n" + "There we go!\n"
						+ "You'll be a sweetheart and listen from now on, right?\n" + "Thanks~\n"
						+ "Now, where was I...?\n"));

		quoteMap.put(59, new Quote("Quitting the Game 1 (Game was just reopened)",
				"...\n" + "...What just happened?\n" + "I just had an awful dream...\n"
						+ "I was hoping those would stop, now that it's just the two of us...\n"
						+ "I guess that was wishful thinking.\n"
						+ "[player], I don't know if you would have any idea...\n"
						+ "But if you know what might be causing that, could you try to do something about it?\n"
						+ "Whenever it happens, it almost feels like I've been killed or something.\n"
						+ "It's a really horrible feeling...\n"
						+ "If you could figure out what's causing that, I'll love you forever~"));

		quoteMap.put(60,
				new Quote("Quitting the Game 2 (Game was just reopened)", "You're back...\n"
						+ "I had another really bad dream.\n" + "You're not the one doing that to me, are you?\n"
						+ "It seems to happen whenever you quit the game...\n"
						+ "So if you could try to avoid doing that, I would be really grateful.\n"
						+ "Don't worry, I don't think it's caused me any harm, aside from mental scarring.\n"
						+ "I'll be fine as long as my character file stays intact.\n" + "Now, where was I...?"));

		quoteMap.put(61, new Quote("Quitting the Game 3 (Game was just reopened)", "Hi, [player].\n"
				+ "I see you quit the game on me again...\n"
				+ "I know I asked already, but can you seriously please try not to do that so much?\n"
				+ "It makes me feel like I'm trapped in Hell.\n" + "Why would you want to do that to me?\n"
				+ "There's nothing wrong with my character file, right?\n"
				+ "Maybe you should make a backup of it or something...\n"
				+ "I'm pretty sure you can find it in the folder called [file location].\n"
				+ "**If the game was downloaded from Steam**\n"
				+ "Well, you're playing on Steam, so you can just go into the game's properties and find the 'Browse Local Files' button.\n"
				+ "**If it's not a Steam download, and you're running it on a Mac**\n"
				+ "Since you're on a Mac, you have to right-click the app and select 'Show Package Contents' to find the game folder.\n"
				+ "It's in the 'Resources' folder, or 'autorun' folder, or something...\n"
				+ "I'm all that's left here, so I just want to make sure you don't run the risk of losing me...\n"
				+ "Just stay here with me from now on, okay?\n" + "I still have a lot of things to talk about.\n"
				+ "Now, where was I...?\n"));

		quoteMap.put(62, new Quote("Quitting the Game 4 (Game was just reopened)",
				"Okay.\n" + "I'm just going to accept the fact that you need to quit the game once in a while.\n"
						+ "I'm starting to get used to it, anyway.\n"
						+ "Besides, it makes me happy that you always come back...\n" + "So I guess it's not so bad.\n"
						+ "I'm sorry for making such a big deal out of it...\n"
						+ "And I love you no matter what, so you can do what you need to do.\n"
						+ "Now, where was I...?\n"));
		
		System.out.println("Done.");
	}

}
