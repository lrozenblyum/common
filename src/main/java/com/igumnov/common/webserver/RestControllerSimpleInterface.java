package com.igumnov.common.webserver;

import javax.servlet.http.HttpServletRequest;

public interface RestControllerSimpleInterface {

    public Object response(HttpServletRequest request) throws WebServerException;


}

