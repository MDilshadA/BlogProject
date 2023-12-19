package com.myblog.myblog.exceptionHandling;
import com.myblog.myblog.payload.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptioHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ErrorDetails> resourceNotFound(
            ResourceNotFound ex,
            WebRequest webRequest
    ){
        ErrorDetails err=new ErrorDetails(new Date(),ex.getMessage(),webRequest.getDescription(true));
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }
}


//ResponseEntityExceptionHandler
//A convenient base class for @ControllerAdvice classes that wish to provide centralized exception handling
// across all @RequestMapping methods through @ExceptionHandler methods.
//This base class provides an @ExceptionHandler method for handling internal Spring MVC exceptions.
// This method returns a ResponseEntity for writing to the response with a message converter,
// in contrast to DefaultHandlerExceptionResolver which returns a ModelAndView.
