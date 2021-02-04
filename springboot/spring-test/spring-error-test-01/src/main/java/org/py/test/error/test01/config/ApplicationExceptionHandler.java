package org.py.test.error.test01.config;

import lombok.extern.slf4j.Slf4j;
import org.py.common.thread.ExceptionUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
//@ControllerAdvice
public class ApplicationExceptionHandler {

    public static class MyTestStatusException extends RuntimeException {

        public MyTestStatusException(String message) {
            super(message);
        }

    }

    @ExceptionHandler(MyTestStatusException.class)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "老哥这是测试，你的请求没权限~~")
    public String dealMyTestStatusException(MyTestStatusException e) {
        return "aryIndexOutOfBounds: " + ExceptionUtil.getThrowableStackInfo(e);
    }

}
