package com.igumnov.common.webserver;

import java.util.Map;

public interface RestControllerInterface {
    public Object response(String method, Map<String, Object> params) throws WebServerException;

}
