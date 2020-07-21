package bot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import discord4j.common.util.Snowflake;
import discord4j.core.event.domain.message.MessageCreateEvent;
import reactor.core.publisher.Mono;

public class ImageHandler {

	// NSFW info
	private static final String nsfwImageFolderPath = System.getProperty("user.dir") + File.separator
			+ "NSFW Monika Pictures";
	private static final File nsfwImageFolder = new File(nsfwImageFolderPath);
	private static ArrayList<String> nsfwImageIndex = new ArrayList<String>();
	// Used to save the list of NSFW channels
	private static final String nsfwChannelListFileName = "NSFW Channel List.txt";
	private static final String nsfwChannelListFilePath = System.getProperty("user.dir") + File.separator
			+ nsfwChannelListFileName;
	static final File nsfwChannelListFile = new File(nsfwChannelListFilePath);
	public static ArrayList<String> nsfwChannelList = new ArrayList<>();

	// SFW info
	private static final String sfwImageFolderPath = System.getProperty("user.dir") + File.separator
			+ "Monika Pictures";
	private static final File sfwImageFolder = new File(sfwImageFolderPath);
	private static ArrayList<String> sfwImageIndex = new ArrayList<String>();

	// Twitter adds _large onto things. This catches that.
	private static final String[] acceptedFormats = { ".jpg", ".jpeg", ".jpg_large", ".jpeg_large", ".png",
			".png_large", ".gif", ".gif_large", ".webm", ".mp4" };

	public static void saveNSFWChannelList() {
		PrintWriter out = null;
		try {
			out = new PrintWriter(ImageHandler.nsfwChannelListFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.err.println(
					"Could not save the NSFW channel list file. Make sure that java has read/write permission at this file location.");
			return;
		}
		for (int i = 0; i < ImageHandler.nsfwChannelList.size(); i++) {
			out.println(ImageHandler.nsfwChannelList.get(i));
		}
		out.close();
	}

	public static void loadNSFWChannelList() {
		Scanner channelScanner = null;
		try {
			channelScanner = new Scanner(ImageHandler.nsfwChannelListFile);
		} catch (FileNotFoundException e) {
			System.err.println("Could not find the NSFW channel list file. Load failed.");
			return;
		}
		if (channelScanner.hasNext()) {
			ImageHandler.nsfwChannelList.clear();
			while (channelScanner.hasNext()) {
				String c = channelScanner.next();
				ImageHandler.nsfwChannelList.add(c);
				System.out.println("Loaded " + c + " into the NSFW channel list.");
			}
		} else {
			System.out.println("The NSFW channel list file is empty. Not modifying working list.");
		}
		channelScanner.close();
	}

	public static int indexSFW() {
		int total = 0;
		// Dump all the files from the folder into the index.
		if (sfwImageFolder.exists()) {
			sfwImageIndex.clear();
			File[] iflist = sfwImageFolder.listFiles();
			// For each file in the folder, determine if it looks like an image.
			for (int i = 0; i < iflist.length; i++) {
				if (iflist[i].isFile() && verifyFileFormat(iflist[i]) && iflist[i].length() / (1024 * 1024) < 8) {
					sfwImageIndex.add(iflist[i].getPath());
					total++;
				}
			}
			System.out.println("Loaded " + total + " SFW images.");
		} else {
			System.err.println("The image folder could not be found. Please create a folder at: " + sfwImageFolderPath
					+ " and fill it with images.");
		}
		return total;
	}

	public static int indexNSFW() {
		int total = 0;
		// Dump all the files from the folder into the index.
		if (nsfwImageFolder.exists()) {
			nsfwImageIndex.clear();
			File[] iflist = nsfwImageFolder.listFiles();
			// For each file in the folder, determine if it looks like an image with size
			// less than 8 MB.

			for (int i = 0; i < iflist.length; i++) {
				if (iflist[i].isFile() && verifyFileFormat(iflist[i]) && iflist[i].length() / (1024 * 1024) < 8) {
					nsfwImageIndex.add(iflist[i].getPath());
					total++;
				}
			}
			System.out.println("Loaded " + total + " NSFW images.");
		} else {
			System.err.println("The image folder could not be found. Please create a folder at: " + nsfwImageFolderPath
					+ " and fill it with images.");
		}
		return total;
	}

	public static File getRandomSFWImage() {
		if (sfwImageIndex.size() == 0) { return null; }
		String randomImageName = sfwImageIndex.get(Math.abs(new Random().nextInt(sfwImageIndex.size())));
		File randomImage = new File(randomImageName);
		if (randomImage.exists()) {
			return randomImage;
		} else {
			indexSFW();
		}
		randomImageName = sfwImageIndex.get(new Random().nextInt(sfwImageIndex.size()));
		randomImage = new File(randomImageName);
		if (!randomImage.exists()) { return null; }
		return randomImage;
	}

	public static File getRandomNSFWImage() {
		if (nsfwImageIndex.size() == 0) { return null; }
		String randomImageName = nsfwImageIndex.get(Math.abs(new Random().nextInt(nsfwImageIndex.size())));
		File randomImage = new File(randomImageName);
		if (randomImage.exists()) {
			return randomImage;
		} else {
			indexNSFW();
		}
		randomImageName = nsfwImageIndex.get(new Random().nextInt(nsfwImageIndex.size()));
		randomImage = new File(randomImageName);
		if (!randomImage.exists()) { return null; }
		return randomImage;
	}

	private static boolean verifyFileFormat(File f) {
		String fname = f.getName().toLowerCase();
		boolean accepted = false;
		for (int i = 0; i < acceptedFormats.length; i++) {
			if (fname.endsWith(acceptedFormats[i])) {
				accepted = true;
			}
		}
		return accepted;
	}

	public static Mono<Boolean> isChannelNSFW(MessageCreateEvent e) {
		return e.getMessage().getChannel().map(c -> isChannelNSFW(c.getId()));

	}

	public static boolean isChannelNSFW(Snowflake snowflake) {
		return nsfwChannelList.contains(Long.toString(snowflake.asLong()));
	}

	public static boolean isChannelNSFW(String channelID) {
		return nsfwChannelList.contains(channelID);
	}

	public static Mono<Void> enableNSFW(MessageCreateEvent event) {
		return event.getMessage().getChannel().flatMap(channel -> {
			if (!ImageHandler.nsfwChannelList.contains(channel.getId().toString())) {
				ImageHandler.nsfwChannelList.add(channel.getId().asString());
				ImageHandler.saveNSFWChannelList();
			}
			return channel.createMessage("NSFW enabled in " + channel.getMention());
		}).then();
	}

	public static Mono<Void> disableNSFW(MessageCreateEvent event) {
		return event.getMessage().getChannel().flatMap(channel -> {
			ImageHandler.nsfwChannelList.remove(channel.getId().asString());
			ImageHandler.saveNSFWChannelList();
			return channel.createMessage("NSFW disabled in " + channel.getMention());
		}).then();
	}
}
