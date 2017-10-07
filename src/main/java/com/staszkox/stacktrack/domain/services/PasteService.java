package com.staszkox.stacktrack.domain.services;

import java.io.IOException;
import java.util.List;

import com.staszkox.stacktrack.domain.tos.PasteAuthorizationRequestTO;
import com.staszkox.stacktrack.domain.tos.PasteContentDisplay;
import com.staszkox.stacktrack.domain.tos.PasteContentRequestTO;
import com.staszkox.stacktrack.domain.tos.PasteHistoryDisplay;

public interface PasteService
{
	String createPasteContent(PasteContentRequestTO pasteContentTO) throws IOException;

	PasteContentDisplay obtainPasteContent(String uuid);
	
	PasteContentDisplay obtainPasteContent(String uuid, PasteAuthorizationRequestTO auth);

	List<PasteHistoryDisplay> obtainLatestPastes(Integer size);

	List<PasteHistoryDisplay> search(String title, String author);

}
