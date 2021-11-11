package urlShortener.url;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.hash.Hashing;

import urlShortener.appuser.AppUser;
import urlShortener.appuser.AppUserRepository;
import urlShortener.exception.UrlExistsException;
import urlShortener.exception.UserNotFoundException;

@Service
public class UrlService {
	
	private UrlRepository urlRepository;
	private AppUserRepository appUserRepository;

	@Autowired
	public UrlService(UrlRepository urlRepository, AppUserRepository appUserRepository) {
		this.urlRepository = urlRepository;
		this.appUserRepository = appUserRepository;
	}
	
	public List<Url> getAllUrls() {
		
		return urlRepository.findAll();
	}
	
	public Url generateShortenedUrl(long appUserId, UrlDto urlDto) throws UrlExistsException {
		String shortenedKey = generateShortenedKey(urlDto.getUrl());
		AppUser user = appUserRepository.findById(appUserId).
									orElseThrow(() -> new UserNotFoundException("User with id " + appUserId + " not found."));
		
		Url urlToSave = new Url();
		urlToSave.setOriginalUrl(urlDto.getUrl());
		urlToSave.setShortenedUrl(shortenedKey);
		urlToSave.setAppUser(user);
		
		//checking if the url exists in the database
		if(urlRepository.findByAppUserIdAndShortenedUrl(appUserId, urlToSave.getShortenedUrl()) == null) {			
			user.getUrls().add(urlToSave);
			
			return urlRepository.save(urlToSave);
		}
		else
			throw new UrlExistsException("This url already exists for this user");
		
	}
	
	private String generateShortenedKey(String originalUrl) {
		String shortenedKey = Hashing.murmur3_32_fixed().hashString(originalUrl, StandardCharsets.UTF_8).toString();
		return shortenedKey;
	}
	
	public List<Url> getUrlsByUserId(long appUserId) {
		return urlRepository.findByAppUserId(appUserId);
	}
	
	public void deleteUserUrl(long urlId) {
		urlRepository.deleteById(urlId);
	}
	
	public Url getShortenedUrl(long appUserId, String shortenedUrl) {
		
		return urlRepository.findByAppUserIdAndShortenedUrl(appUserId, shortenedUrl);
	}
	
}
