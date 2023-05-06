package py.com.demo.authorizer.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class UPError {

    @Schema(description = "Código de error", example = "2422")
    private Integer errorCode;

    @Schema(description = "", example = "The account balance is insufficient for the operation.")
    private String errorSource;

    @Schema(description = "", example = "INSUFFICIENT_ACCOUNT_BALANCE")
    private String errorReason;

}
