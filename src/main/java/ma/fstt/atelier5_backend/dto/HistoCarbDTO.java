package ma.fstt.atelier5_backend.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class HistoCarbDTO {
    private Long id;
    private String date;
    private Double prix;
    private Long stationId;
    private Long carburantId;
}
