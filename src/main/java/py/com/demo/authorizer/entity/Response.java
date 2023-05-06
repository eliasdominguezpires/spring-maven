package py.com.demo.authorizer.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(name = "Response", description = "Respuesta base de los servicios")
public class Response {

    @Schema(description = "Código de la respuesta", example = "0")
    private String code;
    @Schema(description = "Mensaje de la respuesta", example = "OK")
    private String message;
}
