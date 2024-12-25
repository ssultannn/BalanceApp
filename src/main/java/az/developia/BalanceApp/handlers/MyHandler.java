package az.developia.BalanceApp.handlers;


import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import az.developia.BalanceApp.exception.MyException;
import lombok.Data;

@RestControllerAdvice
public class MyHandler {
    @ExceptionHandler
    public ErrorResponse handleException(MyException e) {
        BindingResult bResult = e.getBr();
        ErrorResponse resp = new ErrorResponse();
        
        if (bResult != null) {
            List<FieldError> errors = bResult.getFieldErrors();
            List<MyFieldError> fieldErrors = new ArrayList<>();
            for (FieldError err : errors) {
                MyFieldError mfe = new MyFieldError();
                mfe.setField(err.getField());
                mfe.setDefaultMessage(err.getDefaultMessage());
                fieldErrors.add(mfe);
            }
            resp.setFieldErrors(fieldErrors);
        }
        
        resp.setMessage(e.getMessage());
        return resp;
    }
}

@Data
class ErrorResponse {
	private String message;
	List<MyFieldError> fieldErrors;
	
}
@Data
class MyFieldError{
	private String field;
	private String defaultMessage;
}