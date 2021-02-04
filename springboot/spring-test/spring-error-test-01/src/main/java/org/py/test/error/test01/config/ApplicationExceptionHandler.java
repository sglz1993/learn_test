package org.py.test.error.test01.config;

import lombok.extern.slf4j.Slf4j;
import org.py.common.thread.ExceptionUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class ApplicationExceptionHandler {

    public static class MyTestStatusException extends RuntimeException {

        public MyTestStatusException(String message) {
            super(message);
        }

    }

    public static class MyTestStatusExceptionV2 extends RuntimeException {

        public MyTestStatusExceptionV2(String message) {
            super(message);
        }

    }

    @ExceptionHandler(MyTestStatusException.class)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "老哥这是测试，你的请求没权限~~")
    public String dealMyTestStatusException(MyTestStatusException e) {
        return "aryIndexOutOfBounds: " + ExceptionUtil.getThrowableStackInfo(e);
    }

    @ExceptionHandler(MyTestStatusExceptionV2.class)
    @ResponseStatus(code = HttpStatus.PAYMENT_REQUIRED)
    @ResponseBody
    public String dealMyTestStatusExceptionV2(MyTestStatusExceptionV2 e) {
        return "aryIndexOutOfBounds: " + ExceptionUtil.getThrowableStackInfo(e);
    }

}
