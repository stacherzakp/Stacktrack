package com.staszkox.stacktrack.domain;

import java.net.URI;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.staszkox.stacktrack.domain.services.PasteService;
import com.staszkox.stacktrack.domain.tos.PasteAuthorizationRequestTO;
import com.staszkox.stacktrack.domain.tos.PasteContentDisplay;
import com.staszkox.stacktrack.domain.tos.PasteContentRequestTO;
import com.staszkox.stacktrack.domain.tos.PasteHistoryDisplay;

@RestController
@RequestMapping(value="/pastes")
@Validated
public class PasteController
{
	@Autowired
	PasteService contentService;
	
	@PostMapping
	public ResponseEntity<?> createContent(@RequestBody PasteContentRequestTO pasteContentTO) throws Exception
	{
		String uuid = contentService.createPasteContent(pasteContentTO);
		URI location = new URI("/pastes/" + uuid);
		return ResponseEntity.created(location).build();
	}
	
	@PostMapping(consumes={MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<?> createContentWithFile(@ModelAttribute PasteContentRequestTO pasteContentTO) throws Exception
	{
		String uuid = contentService.createPasteContent(pasteContentTO);
		URI location = new URI("/pastes/" + uuid);
		return ResponseEntity.created(location).build();
	}
	
	@GetMapping(value="{uuid}")
	public ResponseEntity<?> getContent(@PathVariable String uuid)
	{
		PasteContentDisplay pasteContent = contentService.obtainPasteContent(uuid);
		return ResponseEntity.ok(pasteContent);
	}

	@PostMapping(value="{uuid}")
	public ResponseEntity<?> getContent(@PathVariable String uuid, @RequestBody PasteAuthorizationRequestTO auth)
	{
		PasteContentDisplay pasteContent = contentService.obtainPasteContent(uuid, auth);
		return ResponseEntity.ok(pasteContent);
	}
	
	@GetMapping
	public ResponseEntity<?> getLatestPastes(@Max(value=100L) @Min(value=5L) @RequestParam(defaultValue="10") Integer size)
	{
		List<PasteHistoryDisplay> latestPastes = contentService.obtainLatestPastes(size);
		return ResponseEntity.ok(latestPastes);
	}
	
	@GetMapping("/search")
	public ResponseEntity<?> searchPastes(@Size(min=3) @RequestParam(required=false) String title, 
										  @Size(min=3) @RequestParam(required=false) String author)
	{
		List<PasteHistoryDisplay> latestPastes = contentService.search(title, author);
		return ResponseEntity.ok(latestPastes);
	} 
	
	@InitBinder
	public void initBinder(WebDataBinder binder) 
	{
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
}
