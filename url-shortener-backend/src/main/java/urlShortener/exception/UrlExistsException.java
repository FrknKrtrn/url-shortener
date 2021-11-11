package urlShortener.exception;

public class UrlExistsException extends RuntimeException {
	
	public UrlExistsException(String message) {
		super(message);
	}
}
