package urlShortener.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/sign-up")
public class RegistrationController {
	
	private RegistrationService registrationService;

	@Autowired
	public RegistrationController(RegistrationService registrationService) {
		this.registrationService = registrationService;
	}

	@PostMapping
	public void register(@RequestBody RegistrationRequest request) {
		registrationService.register(request);
	}
}
