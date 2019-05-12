package bot;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class ImageHandler {

	private static String sfwImageFolderPath = System.getProperty("user.dir") + File.separator + "Monika Pictures";
	private static File sfwImageFolder = new File(sfwImageFolderPath);
	private static ArrayList<String> sfwImageIndex = new ArrayList<String>();

	private static String nsfwImageFolderPath = System.getProperty("user.dir") + File.separator
			+ "NSFW Monika Pictures";
	private static File nsfwImageFolder = new File(nsfwImageFolderPath);
	private static ArrayList<String> nsfwImageIndex = new ArrayList<String>();

	// Twitter adds _large onto things.
	private static String[] acceptedFormats = { ".jpg", ".jpeg", ".jpg_large", ".jpeg_large", ".png", ".png_large",
			".gif", ".gif_large", ".webm", ".mp4"};

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
			System.err.println("Loaded " + total + " SFW images.");
		} else {
			System.err.println("The image folder could not be found. Please create a folder at:\n" + sfwImageFolderPath
					+ "and fill it with images.");
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
			System.err.println("Loaded " + total + " NSFW images.");
		} else {
			System.err.println("The image folder could not be found. Please create a folder at:\n" + nsfwImageFolderPath
					+ "and fill it with images.");
		}
		return total;
	}

	public static File getRandomSFWImage() {
		String randomImageName = sfwImageIndex.get(new Random().nextInt(sfwImageIndex.size()));
		File randomImage = new File(randomImageName);
		if (randomImage.exists()) {
			return randomImage;
		} else {
			indexSFW();
		}
		randomImageName = sfwImageIndex.get(new Random().nextInt(sfwImageIndex.size()));
		randomImage = new File(randomImageName);
		if (!randomImage.exists()) {
			return null;
		}
		return randomImage;
	}

	public static File getRandomNSFWImage() {
		String randomImageName = nsfwImageIndex.get(new Random().nextInt(nsfwImageIndex.size()));
		File randomImage = new File(randomImageName);
		if (randomImage.exists()) {
			return randomImage;
		} else {
			indexNSFW();
		}
		randomImageName = nsfwImageIndex.get(new Random().nextInt(nsfwImageIndex.size()));
		randomImage = new File(randomImageName);
		if (!randomImage.exists()) {
			return null;
		}
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
}
