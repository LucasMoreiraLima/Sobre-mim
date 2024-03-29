package com.lucasm.testenuclear.exceptionhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class TesteNuclearExceptionHandler extends ResponseEntityExceptionHandler{

	@Autowired
	private MessageSource messagesource;
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		String mensagemUsuario = messagesource.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
		String mensagemDev = ex.getCause().toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDev));
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}
	public static class Erro {
		
		private String mensagemUsuario;
		private String mensagemDev;
		public Erro(String mensagemUsuario, String mensagemDev) {
			super();
			this.mensagemUsuario = mensagemUsuario;
			this.mensagemDev = mensagemDev;
		}
		public String getMensagemUsuario() {
			return mensagemUsuario;
		}
		public String getMensagemDev() {
			return mensagemDev;
		} 
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		List<Erro> erros = criarListaDeErros(ex.getBindingResult());
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler({org.springframework.dao.EmptyResultDataAccessException.class})
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<Object> EmptyResultDataAccessException(org.springframework.dao.EmptyResultDataAccessException ex,
			WebRequest request) {
		String mensagemUsuario = messagesource.getMessage("recurso.nao-encontrado", null, LocaleContextHolder.getLocale());
		String mensagemDev = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDev));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
	
	private List<Erro> criarListaDeErros(BindingResult bindingresult) {
		List<Erro> erros = new ArrayList<>();
		
		for (FieldError fielderror : bindingresult.getFieldErrors()) {
		String mensagemUsuario = messagesource.getMessage(fielderror, LocaleContextHolder.getLocale());
		String mensagemDev = fielderror.toString();
		erros.add(new Erro(mensagemUsuario, mensagemDev));
		}
		return erros;
	}
}
