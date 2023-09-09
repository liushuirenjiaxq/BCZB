package com.bczb.exceptions;

import com.bczb.pojo.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public Result handleException(Exception e) {
        return Result.error(e.getMessage(),400);
    }

    @ExceptionHandler(value = SqlException.class)
    public Result handleSqlException(SqlException e) {
        return Result.error("数据库查询错误",401);
    }

    @ExceptionHandler(value = BusinessException.class)
    public Result handleBusinessException(BusinessException e) {
        return Result.error(e.getMessage(),402);
    }
    @ExceptionHandler(value = AuthException.class)
    public Result handleAuthException(AuthException e) {
        return Result.error(e.getMessage(),403);
    }
}
