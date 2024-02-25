package com.devsuperior.movieflix.services.exceptions;
//Retorna o erro 401
public class UnauthorizedException extends RuntimeException {
    
	private static final long serialVersionUID = 1L;
    
	public UnauthorizedException(String msg) {
		super(msg);
	}    
    
}
