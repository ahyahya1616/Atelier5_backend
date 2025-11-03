package ma.fstt.atelier5_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class CarburantDTO {
    private Long id;
    private String nom;
    private String description;
}
