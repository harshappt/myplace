package com.harsha.myplace.controllers;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerExceptionHandler implements MessageSourceAware {

	private MessageSource messageSource;

	@Autowired
	public ControllerExceptionHandler(MessageSource messageSource) {

		this.messageSource = messageSource;

	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public Map<String, String> processValidationError(
			MethodArgumentNotValidException ex) {

		BindingResult result = ex.getBindingResult();

		List<FieldError> fieldErrors = result.getFieldErrors();

		return processFieldErrors(fieldErrors);

	}

	@ExceptionHandler(DuplicateKeyException.class)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.CONFLICT)
	public Map<String, String> handleDuplicateKey(DuplicateKeyException ex) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("error", ex.getLocalizedMessage());
		return map;
	}

	private Map<String, String> processFieldErrors(List<FieldError> fieldErrors) {

		Map<String, String> map = new LinkedHashMap<String, String>();

		for (FieldError fieldError : fieldErrors) {

			// String localizedErrorMessage =
			// resolveLocalizedErrorMessage(fieldError);
			map.put(fieldError.getField(), fieldError.getDefaultMessage());
		}
		return map;

	}

	private String resolveLocalizedErrorMessage(FieldError fieldError) {

		Locale currentLocale = LocaleContextHolder.getLocale();

		String localizedErrorMessage = messageSource.getMessage(fieldError,
				currentLocale);

		// If the message was not found, return the most accurate field error
		// code instead.

		// You can remove this check if you prefer to get the default error
		// message.

		if (localizedErrorMessage.equals(fieldError.getDefaultMessage())) {

			String[] fieldErrorCodes = fieldError.getCodes();

			localizedErrorMessage = fieldErrorCodes[0];

		}

		return localizedErrorMessage;

	}

	@Override
	public void setMessageSource(MessageSource messageSource) {
		// TODO Auto-generated method stub

	}
}
