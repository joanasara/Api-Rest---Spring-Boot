package com.springboot.api.exceptionhandler;

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
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.Getter;
import lombok.Setter;

@ControllerAdvice
@Getter
@Setter
public class ExcepetionHedler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource message;

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		String mensagemUsuario = message.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
		String mensagemDensevlvedor = ex.getCause().toString();

		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDensevlvedor));
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);

	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<Erro> erros = criarListaDeErros(ex.getBindingResult());
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);

	}

	private List<Erro> criarListaDeErros(BindingResult bindingResult) {
		List<Erro> erros = new ArrayList<>();

		for(FieldError fieldError : bindingResult.getFieldErrors()) {
		String mensagemUsuario = message.getMessage(fieldError, LocaleContextHolder.getLocale());
		String  mensagemDensevlvedor = fieldError.toString();
		erros.add(new  Erro(mensagemUsuario, mensagemDensevlvedor));
		
		}
		return erros;
	}

	public static class Erro {
		public String mensagemUsuario;
		public String mensagemDesenvolvedor;

		public Erro(String mensagemUsuario, String mensagemDensevolvedor) {
			this.mensagemUsuario = mensagemUsuario;
			this.mensagemDesenvolvedor = mensagemDensevolvedor;

		}

	}

}
