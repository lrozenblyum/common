package com.igumnov.common.webserver;

import javax.servlet.http.HttpServletRequest;

public interface RestControllerInterface {

    public Object response(HttpServletRequest request, Object postObject) throws WebServerException;
}
