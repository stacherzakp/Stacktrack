package com.staszkox.stacktrack.domain.services;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.staszkox.stacktrack.domain.common.TextUtils;
import com.staszkox.stacktrack.domain.exceptions.custom.PasteIllegalStateException;
import com.staszkox.stacktrack.domain.exceptions.custom.PasteNotAuthorizedException;
import com.staszkox.stacktrack.domain.processors.PasteBuilder;
import com.staszkox.stacktrack.domain.processors.PasteDisplayConverter;
import com.staszkox.stacktrack.domain.processors.security.PasswordMatcher;
import com.staszkox.stacktrack.domain.services.dao.PasteDao;
import com.staszkox.stacktrack.domain.tos.PasteAuthorizationRequestTO;
import com.staszkox.stacktrack.domain.tos.PasteContentDisplay;
import com.staszkox.stacktrack.domain.tos.PasteContentRequestTO;
import com.staszkox.stacktrack.domain.tos.PasteHistoryDTO;
import com.staszkox.stacktrack.domain.tos.PasteHistoryDisplay;
import com.staszkox.stacktrack.domain.tos.PasteSimpleDTO;
import com.staszkox.stacktrack.model.Paste;

@Service
public class PasteServiceImpl implements PasteService
{
	@Autowired
	private PasteDao contentDao;
	
	@Autowired 
	private PasteDisplayConverter displayConverter;
	
	@Autowired
	private PasteBuilder pasteBuilder;
	
	@Autowired
	private PasswordMatcher passwordMatcher;
	
	@Override
	@Transactional
	public String createPasteContent(PasteContentRequestTO pasteContentTO) throws IOException
	{
		Paste paste = pasteBuilder.build(pasteContentTO);
		boolean saved = contentDao.savePaste(paste);
		
		String uuid = saved ? paste.getUuid() : "";
		return uuid;
	}

	@Override
	@Transactional
	public PasteContentDisplay obtainPasteContent(String uuid)
	{
		PasteSimpleDTO pasteDTO = contentDao.getPaste(uuid);
		
		if (TextUtils.isNotEmpty(pasteDTO.getPassword()))
		{
			throw new PasteNotAuthorizedException();
		}
		
		PasteContentDisplay pasteContentDisplay = displayConverter.convertToDisplay(pasteDTO);
		return pasteContentDisplay;
	}

	@Override
	@Transactional
	public PasteContentDisplay obtainPasteContent(String uuid, PasteAuthorizationRequestTO auth)
	{
		PasteSimpleDTO pasteDTO = contentDao.getPaste(uuid);
		
		if (!passwordMatcher.isValidPassword(pasteDTO.getPassword(), auth.getPassword()))
		{
			throw new PasteNotAuthorizedException();
		}
		
		PasteContentDisplay pasteContentDisplay = displayConverter.convertToDisplay(pasteDTO);
		return pasteContentDisplay;
	}
	
	@Override
	@Transactional
	public List<PasteHistoryDisplay> obtainLatestPastes(Integer size)
	{
		List<PasteHistoryDTO> latestPastes = contentDao.getLatestPastes(size);
		List<PasteHistoryDisplay> latestDisplayPastes = displayConverter.convertToDisplay(latestPastes);
		return latestDisplayPastes;
	}

	@Override
	@Transactional
	public List<PasteHistoryDisplay> search(String title, String author)
	{
		if (TextUtils.isEmpty(title) && TextUtils.isEmpty(author))
		{
			throw new PasteIllegalStateException();
		}
		
		List<PasteHistoryDTO> searchedPastes = contentDao.searchPastes(title, author);
		List<PasteHistoryDisplay> searchedDisplayPastes = displayConverter.convertToDisplay(searchedPastes);
		return searchedDisplayPastes;
	}
}
