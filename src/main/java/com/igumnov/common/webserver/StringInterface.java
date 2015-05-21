package com.igumnov.common.webserver;

import javax.servlet.http.HttpServletRequest;

public interface StringInterface {
    public String response(HttpServletRequest request) throws WebServerException;
}
