package ma.fstt.atelier5_backend.services;

import ma.fstt.atelier5_backend.dto.StationDTO;
import ma.fstt.atelier5_backend.entities.Station;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class StationService {

    @PersistenceContext(unitName = "pu")
    private EntityManager em;

    // Ajouter une station depuis un DTO
    public void addStation(StationDTO dto) {
        Station s = new Station();
        s.setNom(dto.getNom());
        s.setVille(dto.getVille());
        s.setAdresse(dto.getAdresse());
        em.persist(s);
    }

    // Récupérer une station par ID en DTO
    public StationDTO getStation(Long id) {
        Station s = em.find(Station.class, id);
        if (s == null) return null;
        return new StationDTO(s.getId(), s.getNom(), s.getVille(), s.getAdresse());
    }

    // Récupérer toutes les stations en DTO
    public List<StationDTO> getAllStations() {
        return em.createQuery(
                "SELECT new ma.fstt.atelier5_backend.dto.StationDTO(s.id, s.nom, s.ville, s.adresse) FROM Station s",
                StationDTO.class
        ).getResultList();
    }

    // Mettre à jour une station depuis un DTO
    public void updateStation(StationDTO dto) {
        Station s = em.find(Station.class, dto.getId());
        if (s != null) {
            s.setNom(dto.getNom());
            s.setVille(dto.getVille());
            s.setAdresse(dto.getAdresse());
            em.merge(s);
        }
    }

    // Supprimer une station
    public void deleteStation(Long id) {
        Station s = em.find(Station.class, id);
        if (s != null) em.remove(s);
    }
}
