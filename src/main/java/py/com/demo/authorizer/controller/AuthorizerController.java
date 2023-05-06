package py.com.demo.authorizer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import py.com.demo.authorizer.entity.reponse.LocalidadResponse;
import py.com.demo.authorizer.exceptions.AlfaMsException;
import py.com.demo.authorizer.exceptions.UPError;
import py.com.demo.authorizer.entity.ErrorBody;
import py.com.demo.authorizer.entity.Response;
import py.com.demo.authorizer.entity.SystemInfoResponse;
import py.com.demo.authorizer.entity.request.AuthorizationRequest;
import py.com.demo.authorizer.service.AuthorizerService;

import java.util.List;

@Tag(name = "Api/cards")
@RestController
@RequestMapping(value = "/Api/cards")
public class AuthorizerController {

    @Autowired
    private AuthorizerService authorizerService;

    public final static String SUCCESS = "Successful Operation";
    public final static String NO_CONTENT = "No content";
    public final static String BUSINESS_ENTITY_FAILURE = "Business Entity Failure";
    public final static String INTERNAL_SERVER_ERROR = "Internal Server Error";
    public final static String INVALID_PARAMS = "Invalid Params";

    @Operation(
            summary = "Recupera informacion del API",
            responses = {
                    @ApiResponse(
                            description = SUCCESS,
                            responseCode = "200",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Response.class)
                            )
                    ),
                    @ApiResponse(
                            description = BUSINESS_ENTITY_FAILURE,
                            responseCode = "422",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorBody.class)
                            )
                    ),
                    @ApiResponse(
                            description = INTERNAL_SERVER_ERROR,
                            responseCode = "500",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorBody.class)
                            )
                    )
            })
    @GetMapping(value = "api/{alphaAccountNumber}/system-info")
    public ResponseEntity<SystemInfoResponse> getSystemInfo(@Parameter(description = "Nro Cuenta Itgf", example = "160606")
                                                            @PathVariable String alphaAccountNumber) throws AlfaMsException {
        SystemInfoResponse response = authorizerService.getSystemInfo(alphaAccountNumber);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Create authorization for demo",
            responses = {
                    @ApiResponse(
                            description = SUCCESS,
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = INVALID_PARAMS,
                            responseCode = "400",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UPError.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Entity Failure",
                            responseCode = "422",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UPError.class)
                            )
                    ),
                    @ApiResponse(
                            description = INTERNAL_SERVER_ERROR,
                            responseCode = "500",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UPError.class)
                            )
                    ),

            })
    @PostMapping(value = "/authorization")
    public ResponseEntity<Response> createAuthorization( @RequestBody AuthorizationRequest request)
            throws AlfaMsException {
        return ResponseEntity.ok(authorizerService.createAuthorization(request));

    }

    @PostMapping(value = "Api/cards/{token}/transactions/{referenceId}:decrease")
        public ResponseEntity<Response> reverseTransaction(
                        @Parameter(description = "Token de la", example = "aaee-iioo") @PathVariable String cardReferenceToken,
                        @Parameter(description = "Id de referencia", example = "000001") @PathVariable String externalReferenceId) throws AlfaMsException{
                
                authorizerService.doReversion(cardReferenceToken, externalReferenceId);
                return null;
        }

    @Operation(
            summary = "Lista y filtra datos de  " +
                    "https://farmanet.minsal.cl/index.php/ws/getLocales",
            operationId = "getData",
            responses = {
                    @ApiResponse(
                            description = "Successful Operation",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = LocalidadResponse.class)
                            )),
                    @ApiResponse(
                            description = "Entity Failure",
                            responseCode = "422",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorBody.class)
                            )
                    )
            })
    @GetMapping(value = "/filter-data")
    public ResponseEntity<List<LocalidadResponse>> getData(
            @Parameter(description = "Nombre", example = "nombre a buscar")
            @RequestParam(name = "nombre") String nombre,
            @Parameter(description = "Direccion de .....", example = "OHIGGINS 195, LOCAL 1")
            @RequestParam(name = "direccion") String direccion,
            @Parameter(description = "Telefono de .....", example = "+5959711111")
            @RequestParam(name = "telefono") String telefono
    ) throws AlfaMsException {
        return ResponseEntity.ok(authorizerService.getLocalidad(nombre, direccion, telefono));
    }

    @Operation(
            summary = "Lista y filtra datos de  " +
                    "https://farmanet.minsal.cl/index.php/ws/getLocales",
            operationId = "getData",
            responses = {
                    @ApiResponse(
                            description = "Successful Operation",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = LocalidadResponse.class)
                            )),
                    @ApiResponse(
                            description = "Entity Failure",
                            responseCode = "422",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorBody.class)
                            )
                    )
            })
    @GetMapping(value = "/filter-data-name")
    public ResponseEntity<List<LocalidadResponse>> getDataName(
            @Parameter(description = "Nombre", example = "nombre a buscar")
            @RequestParam(name = "nombre") String nombre
    ) throws AlfaMsException {
        return ResponseEntity.ok(authorizerService.getLocalidadName(nombre));
    }

    @Operation(
            summary = "Lista y filtra datos de  " +
                    "https://farmanet.minsal.cl/index.php/ws/getLocales",
            operationId = "getData",
            responses = {
                    @ApiResponse(
                            description = "Successful Operation",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = LocalidadResponse.class)
                            )),
                    @ApiResponse(
                            description = "Entity Failure",
                            responseCode = "422",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorBody.class)
                            )
                    )
            })
    @GetMapping(value = "/filter-data-direction")
    public ResponseEntity<List<LocalidadResponse>> getDataDirection(
            @Parameter(description = "Direccion de .....", example = "OHIGGINS 195, LOCAL 1")
            @RequestParam(name = "direccion") String direccion
    ) throws AlfaMsException {
        return ResponseEntity.ok(authorizerService.getLocalidadDireccion(direccion));
    }

    @Operation(
            summary = "Lista y filtra datos de  " +
                    "https://farmanet.minsal.cl/index.php/ws/getLocales",
            operationId = "getData",
            responses = {
                    @ApiResponse(
                            description = "Successful Operation",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = LocalidadResponse.class)
                            )),
                    @ApiResponse(
                            description = "Entity Failure",
                            responseCode = "422",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorBody.class)
                            )
                    )
            })
    @GetMapping(value = "/filter-data-telefono")
    public ResponseEntity<List<LocalidadResponse>> getDataPhone(
            @Parameter(description = "Telefono de .....", example = "+5959711111")
            @RequestParam(name = "telefono") String telefono
    ) throws AlfaMsException {
        return ResponseEntity.ok(authorizerService.getLocalidadTelefono(telefono));
    }
}
