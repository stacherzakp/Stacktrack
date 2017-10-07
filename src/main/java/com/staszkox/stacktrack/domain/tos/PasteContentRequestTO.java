package com.staszkox.stacktrack.domain.tos;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import com.staszkox.stacktrack.model.enums.Privacy;

@SuppressWarnings("serial")
public class PasteContentRequestTO implements Serializable
{
	private String title;
	private String author;
	private String content;
	private MultipartFile file;

	private Privacy privacy;
	private String password;

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getAuthor()
	{
		return author;
	}

	public void setAuthor(String author)
	{
		this.author = author;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public MultipartFile getFile()
	{
		return file;
	}

	public void setFile(MultipartFile file)
	{
		this.file = file;
	}

	public Privacy getPrivacy()
	{
		return privacy;
	}

	public void setPrivacy(Privacy privacy)
	{
		this.privacy = privacy;
	}

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
		builder.append("PasteContentRequestTO [title=");
		builder.append(title);
		builder.append(", author=");
		builder.append(author);
		builder.append(", content=");
		builder.append(content);
		builder.append(", file=");
		builder.append(file);
		builder.append(", privacy=");
		builder.append(privacy);
		builder.append(", password=");
		builder.append(password);
		builder.append("]");
		return builder.toString();
	}
}
