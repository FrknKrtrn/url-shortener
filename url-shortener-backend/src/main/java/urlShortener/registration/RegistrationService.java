package urlShortener.registration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import urlShortener.appuser.AppUser;
import urlShortener.appuser.AppUserRepository;
import urlShortener.appuser.AppUserRole;
import urlShortener.appuser.AppUserService;
 
@Service
public class RegistrationService {
	
	private AppUserService appUserService;
	
	@Autowired
	public RegistrationService(AppUserService appUserService) {
		this.appUserService = appUserService;
	}

	public void register(RegistrationRequest request) {
		
		appUserService.signUpUser(new AppUser(
										request.getFirstName(),
										request.getLastName(),
										request.getEmail(),
										request.getPassword(),
										AppUserRole.USER 
										)
		);
	}

}
