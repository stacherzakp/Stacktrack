package com.staszkox.stacktrack.domain.processors;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.staszkox.stacktrack.domain.exceptions.custom.PasteIllegalStateException;

@Component
public class FileReader
{
	private static final List<String> allowedExtensions = Lists.newArrayList("xml","txt");
	private static final Long maxFileSize = 5242880L;
	
	@Autowired
	public FileReader()
	{
	}
	
	public String readFileContent(MultipartFile file) throws IOException
	{
		String result = null;
		
		if (file != null && !file.isEmpty())
		{
			boolean validated = validateFile(file);
			
			if (validated)
			{
				result = IOUtils.toString(file.getInputStream(), StandardCharsets.UTF_8.name());
			}
			else
			{
				throw new PasteIllegalStateException();
			}
		}
		
		return result;
	}
	
	private boolean validateFile(MultipartFile file)
	{
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		long fileSize = file.getSize();
		
		boolean validated = allowedExtensions.contains(extension.toLowerCase()) && maxFileSize >= fileSize;
		
		return validated;
	}
}
