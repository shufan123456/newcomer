 /*
  * Copyright 2020 tuhu.cn All right reserved. This software is the
  * confidential and proprietary information of tuhu.cn ("Confidential
  * Information"). You shall not disclose such Confidential Information and shall
  * use it only in accordance with the terms of the license agreement you entered
  * into with Tuhu.cn
  */
 package com.tuhu.sf.newcomer.task.service.handler;

 import com.tuhu.sf.newcomer.task.common.entiy.Result;
 import com.tuhu.sf.newcomer.task.common.entiy.StatusCode;
 import lombok.Getter;
 import lombok.Setter;
 import org.springframework.http.converter.HttpMessageNotReadableException;
 import org.springframework.web.bind.annotation.ControllerAdvice;
 import org.springframework.web.bind.annotation.ExceptionHandler;

 /**
  * @author 舒凡
  * @date 2020/12/9 16:57
  */
 @ControllerAdvice
 public class GlobalExceptionHandler extends Exception {

  @ExceptionHandler(Exception.class)
  public Result GlobalException(Exception ex){
   //ex.printStackTrace();
   return new Result(false, StatusCode.ERROR,"出现异常了:" + ex.getMessage());
  }


 }
