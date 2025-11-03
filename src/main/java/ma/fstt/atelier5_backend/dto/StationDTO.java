package ma.fstt.atelier5_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class StationDTO {
    private Long id;
    private String nom;
    private String ville;
    private String adresse;
}
