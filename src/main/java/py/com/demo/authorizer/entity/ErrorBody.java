package py.com.demo.authorizer.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Schema(name = "ErrorBody", description = "Respuesta en caso de error")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class ErrorBody {

    @Schema(description = "Mensaje de error", example = "Ocurrio un error con la BD")
    private String message;

    @Schema(description = "Mensaje útil para el desarrollador", example = "Could not find a property named 'Customer_id' on Object 'getaccountsview'.")
    private String debugMessage;

    @Schema(description = "Código de error", example = "DB1000")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String code; 
}
