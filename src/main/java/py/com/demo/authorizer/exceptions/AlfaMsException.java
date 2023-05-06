package py.com.demo.authorizer.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class AlfaMsException extends Exception {

    private AlfaMsTypeException type;
    private String errorCode;
    private String errorMessage;

    public AlfaMsException(String errorMessage){
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public AlfaMsException(String errorCode, String errorMessage){
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public AlfaMsException(String errorCode, Exception e) {
        super(errorCode,e);
    }
}
