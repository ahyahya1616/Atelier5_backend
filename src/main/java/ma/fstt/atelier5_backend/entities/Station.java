package ma.fstt.atelier5_backend.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@AllArgsConstructor
@Getter    @Setter
@NoArgsConstructor
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String ville;
    private String adresse;
    @OneToMany(mappedBy = "station", cascade = CascadeType.ALL)
    private List<HistoCarb> histoCarburants;

}
