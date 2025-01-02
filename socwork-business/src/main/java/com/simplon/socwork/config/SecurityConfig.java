package com.simplon.socwork.config;

import java.util.Optional;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.auth0.jwt.algorithms.Algorithm;

@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
public class SecurityConfig {
	

	@Value("${co.simplon.socwork.BCrypt.rounds}")
	private int rounds;
	
	@Value("${co.simplon.socwork.cors}")
	private String origins;

	@Value("${co.simplon.socwork.jwt.secret}")
	private String secret;
	
	@Value("${co.simplon.socwork.jwt.claims.exp}")
    private Optional<Long> exp;
	
	@Value("${co.simplon.socwork.jwt.claims.iss}")
    private String issuer;

	@Bean
	WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
					.allowedMethods("POST", "GET")
					.allowedOrigins(origins);
			}
		};
	}
	
	//authorization server config
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(rounds);
	}
	
	@Bean
    JwtProvider jwtProvider() {
		Algorithm algorithm = Algorithm.HMAC256(secret);
    	return new JwtProvider(algorithm, exp, issuer);
    }
	
	//Resources server config
	@Bean
	JwtDecoder jwtDecoder() { 
		SecretKey secretKey = new SecretKeySpec(secret.getBytes(), "HMACSHA256");
		NimbusJwtDecoder decoder = NimbusJwtDecoder.withSecretKey(secretKey)
			.macAlgorithm(MacAlgorithm.HS256)
			.build();

		OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuer);
	    decoder.setJwtValidator(withIssuer);

		return decoder;
	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
//		new JwtGrantedAuthoritiesConverter().convert();
		security
			.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(authorize -> 
				authorize
					.requestMatchers(HttpMethod.POST, "/accounts", "/accounts/login").anonymous()
					.requestMatchers(HttpMethod.GET, "/accounts/with-role").hasRole("mgr")
					.anyRequest().authenticated())
			.oauth2ResourceServer(oauth2 ->
				oauth2.jwt(Customizer.withDefaults()))
			.cors(Customizer.withDefaults());
		
		
		return security.build();
	}
}
