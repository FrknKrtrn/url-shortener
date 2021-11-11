package urlShortener.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import urlShortener.appuser.AppUserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	private AppUserService appUserService;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	public WebSecurityConfig(AppUserService appUserService, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.appUserService = appUserService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(daoAuthenticationProvider());
			
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors();
		http.csrf().disable();
		http
			.authorizeRequests()
			.antMatchers(HttpMethod.OPTIONS, "/**")
				.permitAll()
			.antMatchers("/sign-up", "/login")
				.permitAll()
			.anyRequest()
				.authenticated()
				.and()
			.httpBasic(); 
	}
	
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(bCryptPasswordEncoder);
		provider.setUserDetailsService(appUserService);
		
		return provider;
	}
	
}
