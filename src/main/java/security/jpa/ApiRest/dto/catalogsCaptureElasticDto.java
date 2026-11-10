package security.jpa.ApiRest.dto;

import lombok.*;


@Data
@NoArgsConstructor // <-- ¡VITAL PARA QUE EL CONSTRUCTOR DE ELASTIC NO REVIENTE!
@AllArgsConstructor
public class catalogsCaptureElasticDto {
    private int id;
    private String identifier;  // Muévelo a la segunda posición
    private String description; // Muévelo a la tercera posición
}
