package com.staszkox.stacktrack.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.staszkox.stacktrack.model.enums.Activity;
import com.staszkox.stacktrack.model.enums.Privacy;

@SuppressWarnings("serial")
@Entity
@Table(name="paste")
public class Paste implements Serializable
{
	private Long id;
	private String uuid;
	private Date createDate;
	private Activity activity;
	private String title;
	private String author;
	private String content;
	private String shortContent;
	private String fileName;
	private Privacy privacy;
	private String password;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId()
	{
		return id;
	}
	
	public void setId(Long id)
	{
		this.id = id;
	}
	
	public String getUuid()
	{
		return uuid;
	}

	public void setUuid(String uuid)
	{
		this.uuid = uuid;
	}

	public Date getCreateDate()
	{
		return createDate;
	}
	
	public void setCreateDate(Date createDate)
	{
		this.createDate = createDate;
	}
	
	@Enumerated(EnumType.STRING)
	public Activity getActivity()
	{
		return activity;
	}
	
	public void setActivity(Activity activity)
	{
		this.activity = activity;
	}
	
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

	@Type(type="text")
	public String getContent()
	{
		return content;
	}
	
	public void setContent(String content)
	{
		this.content = content;
	}
	
	public String getShortContent()
	{
		return shortContent;
	}
	
	public void setShortContent(String shortContent)
	{
		this.shortContent = shortContent;
	}
	
	public String getFileName()
	{
		return fileName;
	}
	
	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}
	
	@Enumerated(EnumType.STRING)
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
		builder.append("Paste [id=");
		builder.append(id);
		builder.append(", uuid=");
		builder.append(uuid);
		builder.append(", createDate=");
		builder.append(createDate);
		builder.append(", activity=");
		builder.append(activity);
		builder.append(", title=");
		builder.append(title);
		builder.append(", author=");
		builder.append(author);
		builder.append(", content=");
		builder.append(content);
		builder.append(", shortContent=");
		builder.append(shortContent);
		builder.append(", fileName=");
		builder.append(fileName);
		builder.append(", privacy=");
		builder.append(privacy);
		builder.append(", password=");
		builder.append(password);
		builder.append("]");
		return builder.toString();
	}
}
