package com.staszkox.stacktrack.domain.processors.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.staszkox.stacktrack.domain.common.TextUtils;

@Component
public class PasswordEncrypter
{
	@Autowired
	public PasswordEncrypter()
	{
	}
	
	public String sha1(String plainPassword)
	{
		if (TextUtils.isNotEmpty(plainPassword))
		{
			return Hashing.sha1().hashString(plainPassword, Charsets.UTF_8).toString();
		}
		
		return null;
	}
}
