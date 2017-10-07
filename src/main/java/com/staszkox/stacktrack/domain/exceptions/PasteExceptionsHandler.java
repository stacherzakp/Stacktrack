package com.staszkox.stacktrack.domain.exceptions;

import java.util.function.Supplier;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.staszkox.stacktrack.domain.exceptions.custom.PasteIllegalStateException;
import com.staszkox.stacktrack.domain.exceptions.custom.PasteNotAuthorizedException;

@RestControllerAdvice
public class PasteExceptionsHandler
{
	private final Logger logger = Logger.getLogger(this.getClass());
	
	private Supplier<PasteErrorResponse> badRequestResponse = () -> new PasteErrorResponse("Invalid request");
	private Supplier<PasteErrorResponse> unauthorizedResponse = () -> new PasteErrorResponse("Authentication problem");
	private Supplier<PasteErrorResponse> internalServerError = () -> new PasteErrorResponse("Internal server error");
	
	@ExceptionHandler(value = { ConstraintViolationException.class })
    protected PasteErrorResponse handleValidation(ConstraintViolationException ex, HttpServletResponse response) 
	{
		logger.error(ex);
		
		response.setStatus(HttpStatus.BAD_REQUEST.value());
        return badRequestResponse.get();
    }
	
	@ExceptionHandler(value = { PasteIllegalStateException.class })
    protected PasteErrorResponse handleIllegalState(PasteIllegalStateException ex, HttpServletResponse response) 
	{
		logger.error(ex);
		
		response.setStatus(HttpStatus.BAD_REQUEST.value());
        return badRequestResponse.get();
    }
	
	@ExceptionHandler(value = { PasteNotAuthorizedException.class })
    protected PasteErrorResponse handleNotAuthorized(PasteNotAuthorizedException ex, HttpServletResponse response) 
	{
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
        return unauthorizedResponse.get();
    }
	
	@ExceptionHandler(value = { Exception.class })
    protected PasteErrorResponse handleInternalError(Exception ex, HttpServletResponse response) 
	{
		logger.error(ex);
		
		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return internalServerError.get();
    }
}
