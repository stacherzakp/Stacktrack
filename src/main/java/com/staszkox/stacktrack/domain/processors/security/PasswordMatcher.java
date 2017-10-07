package com.staszkox.stacktrack.domain.processors.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PasswordMatcher
{
	@Autowired
	private PasswordEncrypter passwordEncrypter;
	
	@Autowired
	public PasswordMatcher()
	{
	}
	
	public boolean isValidPassword(String pastePassword, String passwordToMatch)
	{
		String hash = passwordEncrypter.sha1(passwordToMatch);
		return pastePassword.equals(hash);
	}
}
