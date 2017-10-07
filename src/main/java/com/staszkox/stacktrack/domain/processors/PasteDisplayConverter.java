package com.staszkox.stacktrack.domain.processors;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.staszkox.stacktrack.domain.common.TextUtils;
import com.staszkox.stacktrack.domain.tos.PasteContentDisplay;
import com.staszkox.stacktrack.domain.tos.PasteHistoryDTO;
import com.staszkox.stacktrack.domain.tos.PasteHistoryDisplay;
import com.staszkox.stacktrack.domain.tos.PasteSimpleDTO;

@Component
public class PasteDisplayConverter
{
	private static final String dateFormat = "dd-MM-yyyy HH:mm:ss";
	
	public PasteContentDisplay convertToDisplay(PasteSimpleDTO pasteDTO)
	{
		PasteContentDisplay pasteDisplay = new PasteContentDisplay();
		
		pasteDisplay.setContent(pasteDTO.getContent());
		pasteDisplay.setAuthor(pasteDTO.getAuthor());
		pasteDisplay.setTitle(pasteDTO.getTitle());
		pasteDisplay.setCreateDate(convertDate(pasteDTO.getCreateDate()));
		
		return pasteDisplay;
	}

	public List<PasteHistoryDisplay> convertToDisplay(List<PasteHistoryDTO> latestPastes)
	{
		List<PasteHistoryDisplay> displayPastes = latestPastes.stream().map(this::convertHistoryDTO).collect(Collectors.toList());
		return displayPastes;
	}
	
	private PasteHistoryDisplay convertHistoryDTO(PasteHistoryDTO historyDTO)
	{
		PasteHistoryDisplay display = new PasteHistoryDisplay();
		
		display.setUuid(historyDTO.getUuid());
		display.setShortContent(historyDTO.getShortContent());
		display.setAuthor(historyDTO.getAuthor());
		display.setTitle(historyDTO.getTitle());
		display.setCreateDate(convertDate(historyDTO.getCreateDate()));
		
		return display;
	}
	
	private String convertDate(Date date)
	{
		return TextUtils.convertTimestampToDateString(date, dateFormat);
	}
}
