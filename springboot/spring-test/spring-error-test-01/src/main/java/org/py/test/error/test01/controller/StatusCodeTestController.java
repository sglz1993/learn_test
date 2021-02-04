package org.py.test.error.test01.controller;

import lombok.extern.slf4j.Slf4j;
import org.py.test.error.test01.config.ApplicationExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.annotation.ResponseStatusExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author pengyue.du
 * @see <a href="https://www.cnblogs.com/yihuihui/p/12193593.html">参考链接</a>
 */
@Slf4j
@RestController
public class StatusCodeTestController {


    /**
     * 注解方式，只支持标准http状态码
     * 也可以在异常处理的时候标注在方法上
     *
     * 底层调用了方法：org.springframework.web.servlet.mvc.annotation.ResponseStatusExceptionResolver#applyStatusAndReason(int, java.lang.String, javax.servlet.http.HttpServletResponse)
     *  该方法调用了：response.sendError(statusCode);
     *  会导致重定向到 ErrorController
     * @see ResponseStatusExceptionResolver#doResolveException(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
     * @see ServletInvocableHandlerMethod#setResponseStatus(org.springframework.web.context.request.ServletWebRequest)
     *  可以看出来，当通过 @ExceptionHandler + @ResponseStatus 处理时，reason不写，则只设置status，不为空则设置error
     * @return
     * 状态码 400
     * 信息：
     * {
     *     "timestamp": "2021-02-03T11:46:35.991+00:00",
     *     "status": 400,
     *     "error": "Bad Request",
     *     "message": "",
     *     "path": "/error400"
     * }
     *
     * 使用的姿势不对，正确的应该是：
     * 1. 配合@ExceptionHandler来根据异常返回对应的状态码， 写在异常处理类上
     * 2. 直接装饰在异常类上，然后当你的业务代码中，抛出特定的异常类，返回的 httpcode 就会设置为注解中的值
     *
     *
     * 不知道为什么 @ResponseStatus 注解方式使用的时候 reason 属性不起作用，使用的是标准状态码的reason
     *   原因：
     *      @see org.springframework.boot.autoconfigure.web.ErrorProperties#includeMessage
     *      默认为 IncludeAttribute.NEVER, 可以配置，如： server.error.include-message=ALWAYS
     *
     */
    @GetMapping("error400")
    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "400test!")
    public String ano() {
        return "{\"code\": 400, \"msg\": \"bad request!\"}";
    }

    @GetMapping("error401")
    public String error401() {
        throw  new ApplicationExceptionHandler.MyTestStatusException("lalalal");
    }

    @GetMapping("error402")
    public String error402() {
        throw  new ApplicationExceptionHandler.MyTestStatusExceptionV2("lalalal402");
    }

    /**
     * 异常类 + 注解方式，只支持标准http状态码
     *
     * @return
     */
    @GetMapping("exception/500")
    public String serverException() {
        throw new ServerException("内部异常哦");
    }

    @GetMapping("exception/400")
    public String clientException() {
        throw new ClientException("客户端异常哦");
    }

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public static class ServerException extends RuntimeException {
        public ServerException(String message) {
            super(message);
        }
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "老哥，你的请求有问题~~")
    public static class ClientException extends RuntimeException {
        public ClientException(String message) {
            super(message);
        }
    }

    /**********************************************************************************************************************************************************************************/
    /**
     * 推荐这种方法
     * 比较方便
     * @return
     */
    @GetMapping("401")
    public ResponseEntity<String> _401() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"code\": 401, \"msg\": \"未授权!\"}");
    }

    @GetMapping("451")
    public ResponseEntity<String> _451() {
        return ResponseEntity.status(451).body("{lalalala}");
    }

    /**********************************************************************************************************************************************************************************/

    /**
     * response.setStatus 支持自定义http code，并可以返回结果
     * 和 ResponseEntity 设置是一样的
     * @param response
     * @return
     */
    @GetMapping("525")
    public String _525(HttpServletResponse response) {
        response.setStatus(525);
        return "{\"code\": 525, \"msg\": \"自定义错误码 525!\"}";
    }

    /**********************************************************************************************************************************************************************************/

    /**
     * send error 方式，只支持标准http状态码; 且不会带上返回的结果
     * 原因：sendError 标记该响应异常，导致Tomcat重定向对应异常处理Controller，返回对应响应
     * @see org.apache.catalina.core.StandardHostValve#status(org.apache.catalina.connector.Request, org.apache.catalina.connector.Response)
     * @param response
     * @return
     * {
     *     "timestamp": "2021-02-04T08:11:59.079+00:00",
     *     "status": 410,
     *     "error": "Gone",
     *     "message": "",
     *     "path": "/410"
     * }
     * @throws IOException
     */
    @GetMapping("410")
    public String _410(HttpServletResponse response) throws IOException {
        response.sendError(410, "send 410");
        return "{\"code\": 410, \"msg\": \"Gone 410!\"}";
    }

    @GetMapping("460")
    public String _460(HttpServletResponse response) throws IOException {
        response.sendError(460, "send 460");
        return "{\"code\": 460, \"msg\": \"Gone 460!\"}";
    }

    /**********************************************************************************************************************************************************************************/

}
