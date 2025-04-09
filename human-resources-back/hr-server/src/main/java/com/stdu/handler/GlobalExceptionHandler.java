package com.stdu.handler;

import com.stdu.constant.MessageConstant;
import com.stdu.exception.BaseException;
import com.stdu.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex){
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

    /**
     * 捕获SQLIntegrityConstraintViolationException异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(SQLIntegrityConstraintViolationException ex){
        log.error("异常信息：{}", ex.getMessage());
        //Duplicate entry 'ss' for key 'employee.idx_username'
        String err = ex.getMessage();
        if (err.contains("Duplicate entry")) {
            String[] split = err.split(" ");
            return Result.error(split[2] + MessageConstant.ACCOUNT_EXISTED);
        }
        else{
            return Result.error(MessageConstant.UNKNOWN_ERROR);
        }
    }

}
