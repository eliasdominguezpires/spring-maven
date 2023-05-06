package py.com.demo.authorizer.exceptions;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Log4j2
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class MsBaseExceptionHandler extends ResponseEntityExceptionHandler {


    /**
     * Maneja todas las excepciones del tipo AlfaMsException qye fueron lanzadas desde los micro-servicios
     *
     * @param aex Excepción lanzada por un método
     * @return ResponseEntity con el objeto ErrorBody en su body
     */
    @ExceptionHandler(AlfaMsException.class)
    public ResponseEntity<Object> handleAlfaMsException(AlfaMsException aex) {

        HttpStatus httpStatus;
        switch (aex.getType()) {
            case NOT_FOUND:
                httpStatus = HttpStatus.NOT_FOUND;
                break;
            case APPLICATION:
                httpStatus = HttpStatus.BAD_REQUEST;
                break;
            case SECURITY:
                httpStatus = HttpStatus.UNAUTHORIZED;
                break;
            case COMMUNICATION_UNAVAILABLE:
                httpStatus = HttpStatus.SERVICE_UNAVAILABLE;
                break;
            case COMMUNICATION:
            case DATABASE:
            case INTERNAL:
            default:
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                break;
        }

        return getResponseEntity(aex, httpStatus, aex.getType(), aex.getErrorCode(), aex.getErrorMessage());
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " +
                    violation.getPropertyPath() + ": " + violation.getMessage());
        }
        String errorMessage = String.join("; ", errors);
        logger.error(errorMessage);
        return getResponseEntity(ex, HttpStatus.BAD_REQUEST, AlfaMsTypeException.APPLICATION, null, errorMessage);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request) {
        String error = "Parámetro " + ex.getName() + " debe ser del tipo " + Objects.requireNonNull(ex.getRequiredType()).getSimpleName();
        return getResponseEntity(ex, HttpStatus.BAD_REQUEST, AlfaMsTypeException.APPLICATION, null, error);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<String> errors = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        return getResponseEntity(ex, HttpStatus.OK, AlfaMsTypeException.APPLICATION, "500", String.join("; ", errors));
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return getResponseEntity(ex, HttpStatus.BAD_REQUEST, AlfaMsTypeException.APPLICATION, null, String.format("Parámetro [%s] de tipo [%s] no encontrado en el request", ex.getParameterName(), ex.getParameterType()));
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return getResponseEntity(ex, HttpStatus.BAD_REQUEST, AlfaMsTypeException.APPLICATION, null, "Parámetro del body inválido o inexistente.");
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return getResponseEntity(ex, HttpStatus.NOT_FOUND, AlfaMsTypeException.APPLICATION, null, String.format("handler no encontrado para url [%s] y metodo [%s]", ex.getRequestURL(), ex.getHttpMethod()));
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return getResponseEntity(ex, HttpStatus.INTERNAL_SERVER_ERROR, AlfaMsTypeException.INTERNAL, null, "Ocurrió un error interno con el servidor");
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return getResponseEntity(ex, HttpStatus.METHOD_NOT_ALLOWED, AlfaMsTypeException.INTERNAL, null, String.format("Método [%s] no es soportado para este servicio", ex.getMethod()));
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return getResponseEntity(ex, HttpStatus.UNSUPPORTED_MEDIA_TYPE, AlfaMsTypeException.APPLICATION, null, "El formato del [Content-Type] o [Content-Encodig] no esta admitido");
    }

    private ResponseEntity<Object> getResponseEntity(Throwable e, HttpStatus status, AlfaMsTypeException alfaMsTypeException, String errorCode, String errorMessage) {
        log.error(e);
        List<UPError> errorList = new ArrayList<>();
        UPError errorBody = new UPError();
        errorBody.setErrorCode(Integer.parseInt(errorCode));
        errorBody.setErrorSource(errorMessage);
        errorBody.setErrorReason(alfaMsTypeException.name());
        errorList.add(errorBody);
        return new ResponseEntity<>(errorList, status);
    }
}
