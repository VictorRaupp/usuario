package com.victor.usuario.infrastructure.exceptions;

import javax.naming.AuthenticationException;

public class UnathorizedExcepetion extends AuthenticationException {

    public UnathorizedExcepetion(String mensagem) {
        super(mensagem);
    }
    public UnathorizedExcepetion(String mensagem, Throwable throwable){
        super(mensagem);
    }
}
