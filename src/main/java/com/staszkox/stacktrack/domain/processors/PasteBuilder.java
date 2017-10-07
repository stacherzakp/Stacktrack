package com.staszkox.stacktrack.domain.processors;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.staszkox.stacktrack.domain.common.TextUtils;
import com.staszkox.stacktrack.domain.processors.security.PasswordEncrypter;
import com.staszkox.stacktrack.domain.tos.PasteContentRequestTO;
import com.staszkox.stacktrack.model.Paste;
import com.staszkox.stacktrack.model.enums.Activity;
import com.staszkox.stacktrack.model.enums.Privacy;

@Component
public class PasteBuilder
{
	private static final String DEFAULT_TITLE = "Untitled";
	private static final String DEFAULT_AUTHOR = "Anonymous";
	
	@Autowired
	private PasswordEncrypter passwordEncrypter;
	
	@Autowired
	private FileReader fileReader;
	
	@Autowired
	public PasteBuilder()
	{
	}
	
	public Paste build(PasteContentRequestTO requestTO) throws IOException
	{
		Paste paste = new Paste();
		
		generateUUID(paste);
		buildMetadata(paste, requestTO);
		buildContent(paste, requestTO);
		buildPrivacySettings(paste, requestTO);

		return paste;
	}

	private void generateUUID(Paste paste)
	{
		paste.setUuid(UUID.randomUUID().toString().replace("-", ""));
	}

	private void buildMetadata(Paste paste, PasteContentRequestTO requestTO)
	{
		paste.setTitle(TextUtils.fallbackForEmpty(requestTO.getTitle(), DEFAULT_TITLE));
		paste.setAuthor(TextUtils.fallbackForEmpty(requestTO.getAuthor(), DEFAULT_AUTHOR));
		paste.setCreateDate(new Date());
		paste.setActivity(Activity.ACTIVE);
	}
	
	private void buildContent(Paste paste, PasteContentRequestTO requestTO) throws IOException
	{
		String content = extractContent(requestTO);
		paste.setContent(content);
		
		String shortContent = content.length() > 255 ? content.substring(0, 252).concat("...") : content;
		paste.setShortContent(shortContent);
	}

	private void buildPrivacySettings(Paste paste, PasteContentRequestTO requestTO)
	{
		Privacy privacy = requestTO.getPrivacy() == null ? Privacy.PUBLIC : requestTO.getPrivacy(); 
		paste.setPrivacy(privacy);
		
		paste.setPassword(passwordEncrypter.sha1(requestTO.getPassword()));
	}
	
	private String extractContent(PasteContentRequestTO requestTO) throws IOException
	{
		String content = "";
		
		if (TextUtils.isNotEmpty(requestTO.getContent()))
		{
			content = requestTO.getContent();
		}
		else if (requestTO.getFile() != null)
		{
			content = fileReader.readFileContent(requestTO.getFile());
		}
		
		return content;
	}
}
