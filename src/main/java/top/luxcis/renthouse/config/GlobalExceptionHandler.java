package top.luxcis.renthouse.config;

import cn.dev33.satoken.exception.NotPermissionException;
import cn.hutool.core.exceptions.ValidateException;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.CryptoException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import top.luxcis.renthouse.enums.RespEnum;
import top.luxcis.renthouse.exception.BusinessException;
import top.luxcis.renthouse.model.Resp;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static org.springframework.util.CollectionUtils.toIterator;

/**
 * @author zhuang
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = BusinessException.class)
    public Resp<Object> BusinessException(BusinessException ex) {
        log.error("{} | {} at {}", ex.getCode(), ex.getMessage(), ArrayUtil.isNotEmpty(ex.getStackTrace()) ? ex.getStackTrace()[0] : "No Stack Trace");
        return Resp.ERROR(ex.getCode(), ex.getMessage(), ex.getData());
    }

    @ExceptionHandler(value = CryptoException.class)
    public Resp<Object> CryptoException(CryptoException ex) {
        log.error("{} at {}", ex.getMessage(), ArrayUtil.isNotEmpty(ex.getStackTrace()) ? ex.getStackTrace()[0] : "No Stack Trace");
        return Resp.ERROR(RespEnum.BAD_REQUEST, "加密参数无法解析", ex.getMessage());
    }

    @ExceptionHandler(value = NotPermissionException.class)
    public Resp<Object> NotPermissionException(HttpServletRequest request, NotPermissionException ex) {
        logError(request, ex);
        return Resp.ERROR(ex.getCode(), ex.getMessage(), null);
    }

    @ExceptionHandler(value = ValidateException.class)
    public Resp<String> ValidateException(ValidateException ex) {
        log.error(ex.getMessage());
        return Resp.ERROR(RespEnum.BAD_REQUEST, ex.getMessage(), null);
    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public Resp<String> MissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        String errorMessage = StrUtil.format("{} 不能为空", ex.getParameterName());
        log.error(errorMessage);
        return Resp.ERROR(RespEnum.BAD_REQUEST, errorMessage, null);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public Resp<Object> ConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errorMessage = ex.getConstraintViolations()
                .stream()
                .collect(Collectors.toMap(constraintViolation -> constraintViolation.getPropertyPath().toString(), ConstraintViolation::getMessage));
        return Resp.ERROR(RespEnum.BAD_REQUEST, null, errorMessage);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Resp<Object> MethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        Map<String, String> errorMessage = result.getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, fieldError -> StrUtil.blankToDefault(fieldError.getDefaultMessage(), "参数错误")));
        return Resp.ERROR(RespEnum.BAD_REQUEST, null, errorMessage);
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public Resp<Object> HttpMessageNotReadableException(HttpServletRequest request, HttpMessageNotReadableException ex) {
        logError(request, ex);
        return Resp.ERROR(RespEnum.BAD_REQUEST, ex.getMessage(), null);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public Resp<Object> IllegalArgumentException(IllegalArgumentException ex) {
        log.error("{} at {}", ex.getMessage(), ArrayUtil.isNotEmpty(ex.getStackTrace()) ? ex.getStackTrace()[0] : "No Stack Trace");
        return Resp.ERROR(RespEnum.BAD_REQUEST, ex.getMessage(), null);
    }

    @ExceptionHandler(value = BindException.class)
    public Resp<Object> BindException(BindException ex) {
        Map<String, String> errorMessage = ex.getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, fieldError -> StrUtil.blankToDefault(fieldError.getDefaultMessage(), "参数错误")));
        return Resp.ERROR(RespEnum.BAD_REQUEST, null, errorMessage);
    }

    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    public Resp<Object> MaxUploadSizeExceededException(HttpServletRequest request, MaxUploadSizeExceededException ex) {
        logError(request, ex);
        return Resp.ERROR(RespEnum.PAYLOAD_TOO_LARGE, ex.getMessage(), null);
    }

    @ExceptionHandler(value = MultipartException.class)
    public Resp<Object> MultipartException(HttpServletRequest request, MultipartException ex) {
        logError(request, ex, false);
        return Resp.ERROR(RespEnum.BAD_REQUEST, ex.getMessage(), null);
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public Resp<Object> HttpRequestMethodNotSupportedException(HttpServletRequest request, HttpRequestMethodNotSupportedException ex) {
        log.error("{} -> {}", request.getRequestURI(), ex.getMessage());
        return Resp.ERROR(RespEnum.METHOD_NOT_ALLOWED, ex.getMessage(), null);
    }

    @ExceptionHandler(value = SQLException.class)
    public Resp<Object> sqlException(HttpServletRequest request, SQLException ex) {
        logError(request, ex);
        return Resp.ERROR(RespEnum.SQL_EXCEPTION, ex.getMessage(), null);
    }

    @ExceptionHandler(value = ServletException.class)
    public Resp<Object> nestedServletException(HttpServletRequest request, ServletException ex) {
        logError(request, ex);
        return Resp.ERROR(RespEnum.INTERNAL_SERVER_ERROR, ex.getMessage(), null);
    }

    @ExceptionHandler(value = Exception.class)
    public Resp<Object> exception(HttpServletRequest request, Exception ex) {
        logError(request, ex);
        return Resp.ERROR(RespEnum.INTERNAL_SERVER_ERROR, ex.getMessage(), null);
    }

    private void logError(HttpServletRequest request, Exception e) {
        logError(request, e, true);
    }

    private void logError(HttpServletRequest request, Exception e, boolean withTrace) {
        StringBuilder messageBuilder = new StringBuilder("捕获到异常:\n")
                .append("****************************************")
                .append("\n*    ")
                .append(request.getMethod())
                .append("->")
                .append(request.getRequestURI())
                .append("\n*    ")
                .append("Header:");
        toIterable(request::getHeaderNames).forEach(name -> messageBuilder.append("\n*       ")
                .append(name)
                .append(": ")
                .append(request.getHeader(name)));
        if (!request.getParameterMap().isEmpty()) {
            StringBuilder parameters = getRequestParameters(request);
            messageBuilder.append(parameters);
        }
        messageBuilder.append("\n****************************************");
        if (withTrace) {
            log.error(messageBuilder.toString(), e);
        } else {
            log.error(messageBuilder.toString());
        }
    }

    private StringBuilder getRequestParameters(HttpServletRequest request) {
        StringBuilder stringBuilder = new StringBuilder()
                .append("\n*    ")
                .append("Request parameters:");
        final Map<String, String[]> parameterMap = request.getParameterMap();
        parameterMap.forEach((k, v) -> stringBuilder.append("\n*       ")
                .append(k)
                .append(": ")
                .append(Arrays.toString(v)));
        return stringBuilder;
    }

    private <T> Iterable<T> toIterable(Supplier<Enumeration<T>> enumerationSupplier) {
        return () -> toIterator(enumerationSupplier.get());
    }
}
