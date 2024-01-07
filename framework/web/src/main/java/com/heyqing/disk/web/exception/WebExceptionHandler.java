package com.heyqing.disk.web.exception;

import com.heyqing.disk.core.exception.HeyDiskBusinessException;
import com.heyqing.disk.core.response.ResponseCode;
import com.heyqing.disk.core.response.Result;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintDeclarationException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 * ClassName:WebExceptionHandler
 * Package:com.heyqing.disk.web.exception
 * Description:
 *          全局异常处理器
 * @Date:2024/1/7
 * @Author:Heyqing
 */
@RestControllerAdvice
public class WebExceptionHandler {

    @ExceptionHandler(value = HeyDiskBusinessException.class)
    public Result heyDiskBusinessExceptionHandler(HeyDiskBusinessException e){
        return Result.fail(e.getCode(),e.getMsg());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e){
        ObjectError objectError = e.getBindingResult().getAllErrors().stream().findFirst().get();
        return Result.fail(ResponseCode.ERROR_PARAM.getCode(),objectError.getDefaultMessage());
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public Result constraintViolationExceptionHandler(ConstraintViolationException e){
        ConstraintViolation<?> constraintViolation = e.getConstraintViolations().stream().findFirst().get();
        return Result.fail(ResponseCode.ERROR_PARAM.getCode(),constraintViolation.getMessage());
    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public Result missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException e){
        return Result.fail(ResponseCode.ERROR_PARAM);
    }

    @ExceptionHandler(value = IllegalStateException.class)
    public Result illegalStateExceptionHandler(IllegalStateException e){
        return Result.fail(ResponseCode.ERROR_PARAM);
    }

    @ExceptionHandler(value = BindException.class)
    public Result bindExceptionHandler(BindException e){
        FieldError fieldError = e.getBindingResult().getFieldErrors().stream().findFirst().get();
        return Result.fail(ResponseCode.ERROR_PARAM.getCode(),fieldError.getDefaultMessage());
    }

    @ExceptionHandler(value = RuntimeException.class)
    public Result runtimeExceptionHandler(RuntimeException e){
        return Result.fail(ResponseCode.ERROR.getCode(),e.getMessage());
    }
}
