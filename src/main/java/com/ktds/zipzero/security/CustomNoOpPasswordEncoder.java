package com.ktds.zipzero.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class CustomNoOpPasswordEncoder implements PasswordEncoder {

	public String encode(CharSequence rawPassword) {

		log.warn("before encode :" + rawPassword);
		
		return encode(rawPassword.toString());
	}

	public boolean matches(CharSequence rawPassword, String encodedPassword) {

		log.warn("matches: " + rawPassword + ":" + encodedPassword);
        log.info("pw=================== " + rawPassword.toString().equals(encodedPassword));

		return matches(rawPassword, encodedPassword);
		// return rawPassword.toString().equals(encodedPassword);
	}

}
