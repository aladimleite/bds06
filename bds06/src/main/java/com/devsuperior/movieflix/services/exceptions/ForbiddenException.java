package com.devsuperior.movieflix.services.exceptions;
//Retorna o erro 403
public class ForbiddenException extends RuntimeException {
    
	private static final long serialVersionUID = 1L;
    
	public ForbiddenException(String msg) {
		super(msg);
	}    
    
}
