package py.com.demo.authorizer.exceptions;

public class AlfaException extends RuntimeException{
	
    public AlfaException(String message) {
        super(message);
    }

    public AlfaException(String message, Exception e) {
        super(message,e);
    }
	
}
