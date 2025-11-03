package ma.fstt.atelier5_backend.services;

import ma.fstt.atelier5_backend.dto.CarburantDTO;
import ma.fstt.atelier5_backend.entities.Carburant;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class CarburantService {

    @PersistenceContext(unitName = "pu")
    private EntityManager em;

    public void addCarburant(CarburantDTO dto) {
        Carburant c = new Carburant();
        c.setNom(dto.getNom());
        c.setDescription(dto.getDescription());
        em.persist(c);
    }

    public CarburantDTO getCarburant(Long id) {
        Carburant c = em.find(Carburant.class, id);
        if (c == null) return null;
        return new CarburantDTO(c.getId(), c.getNom(), c.getDescription());
    }

    public List<CarburantDTO> getAllCarburants() {
        return em.createQuery(
                "SELECT new ma.fstt.atelier5_backend.dto.CarburantDTO(c.id, c.nom, c.description) FROM Carburant c",
                CarburantDTO.class
        ).getResultList();
    }

    public void updateCarburant(CarburantDTO dto) {
        Carburant c = em.find(Carburant.class, dto.getId());
        if (c != null) {
            c.setNom(dto.getNom());
            c.setDescription(dto.getDescription());
            em.merge(c);
        }
    }

    public void deleteCarburant(Long id) {
        Carburant c = em.find(Carburant.class, id);
        if (c != null) em.remove(c);
    }
}
