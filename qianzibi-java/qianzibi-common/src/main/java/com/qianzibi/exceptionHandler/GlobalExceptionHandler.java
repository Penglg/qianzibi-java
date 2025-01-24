package com.qianzibi.exceptionHandler;

import com.qianzibi.exception.BusinessException;
import com.qianzibi.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public R error(BusinessException e) {
        e.printStackTrace();
        return R.error_600().message(e.getMessage());
    }

    //指定出现什么异常执行这个方法
    //全局异常处理
    @ExceptionHandler(Exception.class)
    @ResponseBody//返回数据
    public R error(Exception e){
        e.printStackTrace();
        return R.error_other().message("执行了全局异常处理..");
    }
    //特定异常处理
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody//返回数据
    public R error(ArithmeticException e){
        e.printStackTrace();
        return R.error_other().message("执行了ArithmeticException异常处理..");
    }
}
