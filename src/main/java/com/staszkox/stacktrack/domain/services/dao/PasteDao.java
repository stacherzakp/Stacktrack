package com.staszkox.stacktrack.domain.services.dao;

import java.util.List;

import com.staszkox.stacktrack.domain.tos.PasteHistoryDTO;
import com.staszkox.stacktrack.domain.tos.PasteSimpleDTO;
import com.staszkox.stacktrack.model.Paste;

public interface PasteDao
{
	boolean savePaste(Paste pasteContent);

	PasteSimpleDTO getPaste(String uuid);

	List<PasteHistoryDTO> getLatestPastes(Integer size);

	List<PasteHistoryDTO> searchPastes(String title, String author);
}
