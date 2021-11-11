package urlShortener.appuser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import urlShortener.exception.EmailAlreadyTakenException;


@Service
public class AppUserService implements UserDetailsService {

	private AppUserRepository appUserRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	public AppUserService(AppUserRepository appUserRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.appUserRepository = appUserRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return appUserRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " not found"));
	}
	
	public void signUpUser(AppUser appUser) {
		
		boolean userExists = appUserRepository.findByEmail(appUser.getEmail()).isPresent();
		
		if(userExists)
			throw new EmailAlreadyTakenException("This email is already taken");
		
		String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
		 
		appUser.setPassword(encodedPassword);
		
		appUserRepository.save(appUser);
		
		System.out.println(appUser.getEmail() + " is saved");
	}

}
