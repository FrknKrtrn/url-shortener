package urlShortener.url;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long>{
	//method to be used to check if the url exists in the user's url
	public Url findByAppUserIdAndShortenedUrl(long appUserId, String shortenedUrl);
		
	//method to be used to get the urls belong to the specific user
	public List<Url> findByAppUserId(long appUserId);
}
