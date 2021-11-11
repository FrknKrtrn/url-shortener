package urlShortener.url;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import urlShortener.exception.UrlExistsException;

@RestController
public class UrlController {
	
	private UrlService urlService;
	
	@Autowired
	public UrlController(UrlService urlService) {
		this.urlService = urlService;
	}

	@GetMapping("/urls")
	public ResponseEntity<List<Url>> getAllUrls() {
		List<Url> urls = urlService.getAllUrls();

		return new ResponseEntity<List<Url>>(urls, HttpStatus.OK);
	}
	
	@PostMapping("/users/{appUserId}/urls")
	public ResponseEntity<?> addUrl(@RequestBody UrlDto urlDto, @PathVariable(name = "appUserId") long appUserId) throws UrlExistsException {
		Url url = urlService.generateShortenedUrl(appUserId, urlDto);
		
		return new ResponseEntity<Url>(url, HttpStatus.CREATED);
	}

	
	@GetMapping("/users/{appUserId}/urls")
	public ResponseEntity<?> getUserUrls(@PathVariable(name = "appUserId") long appUserId) {
		List<Url> urls = urlService.getUrlsByUserId(appUserId);
		
		return new ResponseEntity<List<Url>>(urls, HttpStatus.OK);
	}
	
	@GetMapping("/users/{appUserId}/{shortenedUrl}")
	public ResponseEntity<?> redirectToOriginalUrl(@PathVariable("appUserId") long appUserId, @PathVariable("shortenedUrl") String shortenedUrl, HttpServletResponse response) throws IOException {
		Url url = urlService.getShortenedUrl(appUserId, shortenedUrl);
		response.sendRedirect(url.getOriginalUrl());
		return null;
	}
	
	@DeleteMapping("/{urlId}")
	public ResponseEntity<?> deleteUserUrl(@PathVariable(name = "urlId") long urlId) {
		urlService.deleteUserUrl(urlId);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
