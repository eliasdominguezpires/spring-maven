package py.com.demo.authorizer.entity.reponse;

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
public class LocalidadResponse {

    @Schema(description = "date", example = "06-05-23")
    private String fecha;
    @Schema(description = "ID", example = "1")
    private String local_id;

    @Schema(description = "nombre", example = "CRUZ VERDE")
    private String local_nombre;

    @Schema(description = "Comuna", example = "QUILLOTA")
    private String comuna_nombre;

    @Schema(description = "Localidad", example = "QUILLOTA")
    private String localidad_nombre;

    @Schema(description = "direccion", example = "OHIGGINS 195, LOCAL 1")
    private String local_direccion;

    @Schema(description = "Horario Apertura", example = "8:30:00")
    private String funcionamiento_hora_apertura;

    @Schema(description = "horario de cierre", example = "18:30:00")
    private String funcionamiento_hora_cierre;

    @Schema(description = "Telefono Local", example = "5633332269467")
    private String local_telefono;

    @Schema(description = "Coordenadas latitud", example = "-32.8793428949969")
    private String local_lat;

    @Schema(description = "Coordenadas longitud", example = "-71.2467871500868")
    private String local_lng;

    @Schema(description = "Dia laboral", example = "sabado")
    private String funcionamiento_dia;

    @Schema(description = "Region", example = "6")
    private String fk_region;

    @Schema(description = "Comuna", example = "69")
    private String fk_comuna;

    @Schema(description = "Localidad", example = "32")
    private String fk_localidad;
}
