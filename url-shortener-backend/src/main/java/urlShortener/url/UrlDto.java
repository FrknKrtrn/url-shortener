package urlShortener.url;

public class UrlDto {
	
	private long id;
	private String url;
	
	public UrlDto() {
			
	}

	public UrlDto(long id, String url) {
		this.id = id;
		this.url = url;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
