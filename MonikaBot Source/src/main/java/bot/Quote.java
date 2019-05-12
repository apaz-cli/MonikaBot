package bot;

public class Quote {

	private String title;
	private String quote;
	
	Quote(String title, String quote) {
		this.title = title;
		this.quote = quote;
	}

	public String getQuote() {
		return quote;
	}

	public void setQuote(String quote) {
		this.quote = quote;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
