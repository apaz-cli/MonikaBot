package bot;

import java.io.File;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.Channel;
import reactor.core.publisher.Mono;

class BotUtils {

	static String BOT_PREFIX = "/";
	

	public static String codeBlock(String message) {
		return "```" + message + "\n```";
	}

	public static String codeBlock(String message, String language) {
		return "```" + language + "\n" + message + "```";
	}

	static Mono<Message> sendMessage(Channel channel, String message) {
		return null;
	}

	static Mono<Message> sendFile(Channel channel, File file) {
		return null;
	}

	static Mono<Message> sendFileMessage(Channel channel, String message, File file) {
		return null;
	}

	static String getIP() {
		String addresses = "";
		try {
			Enumeration<?> e = NetworkInterface.getNetworkInterfaces();
			while (e.hasMoreElements()) {
				NetworkInterface n = (NetworkInterface) e.nextElement();
				Enumeration<?> ee = n.getInetAddresses();
				while (ee.hasMoreElements()) {
					InetAddress i = (InetAddress) ee.nextElement();

					String address = i.getHostAddress();
					addresses += address.contains(":") || address.startsWith("127") ? "" : address + "\n";
				}
			}
		} catch (Exception e) {
		}
		return addresses.trim();
	}

}
