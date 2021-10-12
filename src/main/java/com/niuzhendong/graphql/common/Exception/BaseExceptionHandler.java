package com.niuzhendong.graphql.common.Exception;

import com.niuzhendong.graphql.common.dto.ResultDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class BaseExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(BaseExceptionHandler.class);
    private static final String ERROR_MES_SEPARATOR = "####";
    private final String message = "服务器开小差了";

    @ExceptionHandler({BaseException.class})
    @ResponseBody
    public ResponseEntity<ResultDTO> baseExceptionHandler(HttpServletRequest request, BaseException ex) {
        logger.error(ex.getMessage(), ex);
        return new ResponseEntity(new ResultDTO(ex.getCode(), ex.getMessage(), ""), HttpStatus.OK);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<ResultDTO> dataIntegrityViolationExceptionExceptionHandler(DataIntegrityViolationException ex) {
        logger.error(ex.getMessage(), ex);
        Throwable cause = ex.getCause();
        String errorMessage = "";
        if (cause instanceof SQLIntegrityConstraintViolationException) {
            errorMessage = cause.getMessage();
        }

        if (!(cause instanceof SQLIntegrityConstraintViolationException)) {
            errorMessage = cause.getCause().getMessage();
        }

        String reg = "[u4E00-u9FA5]";
        String rega = "[a-zA-Z_']";
        Pattern compile = Pattern.compile(reg);
        Matcher matcher = compile.matcher(errorMessage);
        String msg = "";
        if (matcher.find()) {
            msg = errorMessage.replaceAll(rega, "").trim() + "重复";
            return new ResponseEntity(new ResultDTO(40001, msg, ""), HttpStatus.OK);
        } else {
            return new ResponseEntity(new ResultDTO(40001, "服务器开小差了", ""), HttpStatus.OK);
        }
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ResultDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {
        logger.error(ex.getMessage(), ex);
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        return new ResponseEntity(new ResultDTO(400, ((ObjectError)allErrors.get(0)).getDefaultMessage(), ""), HttpStatus.OK);
    }

    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<ResultDTO> handleValidationExceptions(ValidationException ex) {
        logger.error(ex.getMessage(), ex);
        String message = ex.getMessage();
        String reg = "[u4E00-u9FA5]";
        Pattern compile = Pattern.compile(reg);
        Matcher matcher = compile.matcher(message);
        String rega = "[a-zA-Z_'_\\[_\\]_[0-8]_._:]";
        if (matcher.find()) {
            message = message.replaceAll(rega, "").trim();
            return new ResponseEntity(new ResultDTO(400, message, ""), HttpStatus.OK);
        } else {
            Integer var10004 = 400;
            this.getClass();
            return new ResponseEntity(new ResultDTO(var10004, "服务器开小差了", ""), HttpStatus.OK);
        }
    }

    @ExceptionHandler({Exception.class})
    @ResponseBody
    public ResponseEntity<ResultDTO> runtimeExceptionHandler(HttpServletRequest request, Exception ex) {
        HttpStatus status = this.getStatus(request);
        String messageDetail = ex.getMessage();
        String msg = "服务器开小差了";
        Matcher m = Pattern.compile("[一-龥]").matcher(messageDetail);
        if (m.find()) {
            msg = messageDetail;
        }

        logger.error(ex.getMessage(), ex);
        return new ResponseEntity(new ResultDTO(status.value(), msg, ""), status);
    }

    public HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer)request.getAttribute("javax.servlet.error.status_code");
        return statusCode == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.valueOf(statusCode);
    }
}
