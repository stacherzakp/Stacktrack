package com.staszkox.stacktrack.domain.tos;

import java.io.Serializable;


@SuppressWarnings("serial")
public class PasteAuthorizationRequestTO implements Serializable
{
	private String password;
	
	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("PasteAuthorizationRequestTO [password=");
		builder.append(password);
		builder.append("]");
		return builder.toString();
	}
}
