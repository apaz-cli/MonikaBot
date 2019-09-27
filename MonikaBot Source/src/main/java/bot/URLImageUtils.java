package bot;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;

public class URLImageUtils {

	private static Map<String, String> mimeMap = new HashMap<>();
	static {
		// Build mime map
		mimeMap.put("html", "text/html");
		mimeMap.put("htm", "text/html");
		mimeMap.put("shtml", "text/html");
		mimeMap.put("css", "text/css");
		mimeMap.put("xml", "text/xml");
		mimeMap.put("gif", "image/gif");
		mimeMap.put("jpeg", "image/jpeg");
		mimeMap.put("jpg", "image/jpeg");
		mimeMap.put("js", "application/javascript");
		mimeMap.put("atom", "application/atom+xml");
		mimeMap.put("rss", "application/rss+xml");
		mimeMap.put("mml", "text/mathml");
		mimeMap.put("txt", "text/plain");
		mimeMap.put("jad", "text/vnd.sun.j2me.app-descriptor");
		mimeMap.put("wml", "text/vnd.wap.wml");
		mimeMap.put("htc", "text/x-component");
		mimeMap.put("png", "image/png");
		mimeMap.put("tif", "image/tiff");
		mimeMap.put("tiff", "image/tiff");
		mimeMap.put("wbmp", "image/vnd.wap.wbmp");
		mimeMap.put("ico", "image/x-icon");
		mimeMap.put("jng", "image/x-jng");
		mimeMap.put("bmp", "image/x-ms-bmp");
		mimeMap.put("svg", "image/svg+xml");
		mimeMap.put("svgz", "image/svg+xml");
		mimeMap.put("webp", "image/webp");
		mimeMap.put("woff", "application/font-woff");
		mimeMap.put("jar", "application/java-archive");
		mimeMap.put("war", "application/java-archive");
		mimeMap.put("ear", "application/java-archive");
		mimeMap.put("json", "application/json");
		mimeMap.put("hqx", "application/mac-binh0:ex40");
		mimeMap.put("doc", "application/msword");
		mimeMap.put("pdf", "application/pdf");
		mimeMap.put("ps", "application/postscript");
		mimeMap.put("eps", "application/postscript");
		mimeMap.put("ai", "application/postscript");
		mimeMap.put("rtf", "application/rtf");
		mimeMap.put("m3u8", "application/vnd.apple.mpegurl");
		mimeMap.put("xls", "application/vnd.ms-excel");
		mimeMap.put("eot", "application/vnd.ms-fontobject");
		mimeMap.put("ppt", "application/vnd.ms-powerpoint");
		mimeMap.put("wmlc", "application/vnd.wap.wmlc");
		mimeMap.put("kml", "application/vnd.google-earth.kml+xml");
		mimeMap.put("kmz", "application/vnd.google-earth.kmz");
		mimeMap.put("7z", "application/x-7z-compressed");
		mimeMap.put("cco", "application/x-cocoa");
		mimeMap.put("jardiff", "application/x-java-archive-diff");
		mimeMap.put("jnlp", "application/x-java-jnlp-file");
		mimeMap.put("run", "application/x-makeself");
		mimeMap.put("pl", "application/x-perl");
		mimeMap.put("pm", "application/x-perl");
		mimeMap.put("prc", "application/x-pilot");
		mimeMap.put("pdb", "application/x-pilot");
		mimeMap.put("rar", "application/x-rar-compressed");
		mimeMap.put("rpm", "application/x-redhat-package-manager");
		mimeMap.put("sea", "application/x-sea");
		mimeMap.put("swf", "application/x-shockwave-flash");
		mimeMap.put("sit", "application/x-stuffit");
		mimeMap.put("tcl", "application/x-tcl");
		mimeMap.put("tk", "application/x-tcl");
		mimeMap.put("der", "application/x-x509-ca-cert");
		mimeMap.put("pem", "application/x-x509-ca-cert");
		mimeMap.put("crt", "application/x-x509-ca-cert");
		mimeMap.put("xpi", "application/x-xpinstall");
		mimeMap.put("xhtml", "application/xhtml+xml");
		mimeMap.put("xspf", "application/xspf+xml");
		mimeMap.put("zip", "application/zip");
		mimeMap.put("bin", "application/octet-stream");
		mimeMap.put("exe", "application/octet-stream");
		mimeMap.put("dll", "application/octet-stream");
		mimeMap.put("deb", "application/octet-stream");
		mimeMap.put("dmg", "application/octet-stream");
		mimeMap.put("iso", "application/octet-stream");
		mimeMap.put("img", "application/octet-stream");
		mimeMap.put("msi", "application/octet-stream");
		mimeMap.put("msp", "application/octet-stream");
		mimeMap.put("msm", "application/octet-stream");
		mimeMap.put("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
		mimeMap.put("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		mimeMap.put("pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation");
		mimeMap.put("mid", "audio/midi");
		mimeMap.put("midi", "audio/midi");
		mimeMap.put("kar", "audio/midi");
		mimeMap.put("mp3", "audio/mpeg");
		mimeMap.put("ogg", "audio/ogg");
		mimeMap.put("m4a", "audio/x-m4a");
		mimeMap.put("ra", "audio/x-realaudio");
		mimeMap.put("3gpp", "video/3gpp");
		mimeMap.put("3gp", "video/3gpp");
		mimeMap.put("ts", "video/mp2t");
		mimeMap.put("mp4", "video/mp4");
		mimeMap.put("mpeg", "video/mpeg");
		mimeMap.put("mpg", "video/mpeg");
		mimeMap.put("mov", "video/quicktime");
		mimeMap.put("webm", "video/webm");
		mimeMap.put("flv", "video/x-flv");
		mimeMap.put("m4v", "video/x-m4v");
		mimeMap.put("mng", "video/x-mng");
		mimeMap.put("asx", "video/x-ms-asf");
		mimeMap.put("asf", "video/x-ms-asf");
		mimeMap.put("wmv", "video/x-ms-wmv");
		mimeMap.put("avi", "video/x-msvideo");
	}

	public static Pattern URL_REGEX = Pattern
			.compile("" + "(https?:\\/\\/(?:www\\.|(?!www))" + "[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\\.[^\\s]{2,}|"
					+ "www\\.[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\\.[^\\s]{2,}|"
					+ "https?:\\/\\/(?:www\\.|(?!www))[a-zA-Z0-9]+\\.[^\\s]{2,}|" + "www\\.[a-zA-Z0-9]+\\.[^\\s]{2,})");

	public static List<String> matchAllStrURLs(String str) {
		List<String> matches = new ArrayList<>();
		Matcher m = URL_REGEX.matcher(str);
		
		while (m.find()) {
			matches.add(m.group());
		}
		return matches;
	}

	public static List<URL> matchAllURLs(String str) {
		List<URL> matches = new ArrayList<>();
		Matcher m = URL_REGEX.matcher(str);

		while (m.find()) {
			try {
				matches.add(new URL(m.group()));
			} catch (MalformedURLException e) {
				System.err.print(
						"There was an issue where a URL accepted by the URL_REGEX pattern was not accepted by java.net.URL's constructor.\n"
								+ "URL: " + m.group() + "\n" + "Please create an issue on Github at:\nhttps://github.com/Aaron-Pazdera/MonikaBot");
			}
		}
		return matches;
	}

	public static String extensionFromURL(String url) throws MalformedURLException {
		return extensionFromURL(new URL(url));
	}

	public static String extensionFromURL(URL url) {
		return FilenameUtils.getExtension(url.getPath());
	}

	public static String guessMimeFromURL(String url) throws MalformedURLException {
		return guessMimeFromURL(new URL(url));
	}

	public static String guessMimeFromURL(URL url) {
		// This deals with twitter
		return mimeMap.get(FilenameUtils.getExtension(url.getPath()).replace(":large.*", ""));
	}

	public static boolean isImage(String url) throws MalformedURLException {
		return isImage(new URL(url));
	}

	public static boolean isImage(URL url) {
		String mime = guessMimeFromURL(url);
		if (mime == null) {
			return false;
		}
		if (mime.contains("image")) {
			return true;
		}
		return false;
	}

}
