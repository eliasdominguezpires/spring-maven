package py.com.demo.authorizer.entity.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorizationRequest {

    @Schema(description = "Identificador ", example = "CRUZ VERDE")
    private String name;

    @Schema(description = "Direccion de .....", example = "OHIGGINS 195, LOCAL 1")
    private String direction;

    @Schema(description = "Numero de telefono", example = "+5633332269467")
    private String phoneNumber;

    @Schema(description = "En caso de ser necesario", example = "token")
    private String token;
}
