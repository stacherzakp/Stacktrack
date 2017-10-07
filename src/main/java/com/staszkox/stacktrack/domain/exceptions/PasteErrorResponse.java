package com.staszkox.stacktrack.domain.exceptions;

import java.io.Serializable;

@SuppressWarnings("serial")
public class PasteErrorResponse implements Serializable
{
	private String reason;

	public PasteErrorResponse(String reason)
	{
		this.reason = reason;
	}
	
	public String getReason()
	{
		return reason;
	}

	public void setReason(String reason)
	{
		this.reason = reason;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("PasteErrorResponse [reason=");
		builder.append(reason);
		builder.append("]");
		return builder.toString();
	}
}
