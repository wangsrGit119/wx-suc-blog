package cn.wangsr.advice;

import exception.ExceptionResult;
import exception.GlobalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 *  全局异常响应
 * @author WJL
 */
@ControllerAdvice
@RestController
public class GlobalExceptionAdvice {

    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionAdvice.class);

    /**
     * 原始异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ExceptionResult  handleExceptionForErrorOne(Exception e, HttpServletRequest request){
        ExceptionResult exceptionResult = ExceptionResult.builder()
                .requestId((String)request.getAttribute("request_id"))
                .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .message(e.getMessage())
                .path(request.getRequestURI())
                .build();
        logger.info("Exception message  : {}",e.getMessage());
        logger.info("Exception from  : {}",e.getCause());
        logger.info("Exception print  : {}",e);
        return exceptionResult;
    }


    /**
     * 自定义全局异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(GlobalException.class)
    ExceptionResult  handleExceptionForErrorTwo(GlobalException e, HttpServletRequest request){
        ExceptionResult exceptionResult = ExceptionResult.builder()
                .requestId((String)request.getAttribute("request_id"))
                .code(String.valueOf(e.getCode()))
                .message(e.getMessage())
                .path(request.getRequestURI())
                .build();
        logger.info("MyException message  : {}",e.getMessage());
        logger.info("MyException from  : {}",e.getCause());
        logger.info("MyException print  : {}",e);
        return exceptionResult;
    }

}
