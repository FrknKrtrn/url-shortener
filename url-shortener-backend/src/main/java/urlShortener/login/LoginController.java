package urlShortener.login;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import urlShortener.appuser.AppUser;
import urlShortener.appuser.AppUserRepository;
import urlShortener.exception.BadCredentialsException;
import urlShortener.exception.UserNotFoundException;

@RestController
public class LoginController {
	
	private AppUserRepository appUserRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	public LoginController(AppUserRepository appUserRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.appUserRepository = appUserRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	@GetMapping("/")
	public String login() {
		return "Login works [backend]";
	}
	
	@GetMapping("/users")
	public List<AppUser> getAllUsers() {
		List<AppUser> users = appUserRepository.findAll();
		
		return users;
	}
	
	@PostMapping("/login")
	public AppUser login(@RequestBody LoginRequest request) throws Exception {
		String emailToCheck = request.getEmail();
		String passwordToCheck = request.getPassword();
			
		AppUser userToCheck = null;
		
		if(emailToCheck != null && passwordToCheck != null) {
			
			userToCheck = appUserRepository.findByEmail(emailToCheck)
						.orElseThrow(() -> new UserNotFoundException("User with email " + emailToCheck + " not found"));
			
			if(!bCryptPasswordEncoder.matches(passwordToCheck, userToCheck.getPassword()))
				throw new BadCredentialsException("Bad credentials!");
		}
				
		return userToCheck;
	}
}
