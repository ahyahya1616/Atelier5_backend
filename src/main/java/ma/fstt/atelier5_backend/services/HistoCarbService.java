package ma.fstt.atelier5_backend.services;

import ma.fstt.atelier5_backend.dto.HistoCarbDTO;
import ma.fstt.atelier5_backend.entities.HistoCarb;
import ma.fstt.atelier5_backend.entities.Station;
import ma.fstt.atelier5_backend.entities.Carburant;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class HistoCarbService {

    @PersistenceContext(unitName = "pu")
    private EntityManager em;

    public void addHistoCarb(HistoCarbDTO dto) {
        HistoCarb h = new HistoCarb();
        h.setDate(dto.getDate());
        h.setPrix(dto.getPrix());
        h.setStation(em.find(Station.class, dto.getStationId()));
        h.setCarburant(em.find(Carburant.class, dto.getCarburantId()));
        em.persist(h);
    }

    public List<HistoCarbDTO> getHistoByStation(Long idStation) {
        return em.createQuery(
                        "SELECT h FROM HistoCarb h WHERE h.station.id = :id",
                        HistoCarb.class
                ).setParameter("id", idStation)
                .getResultList()
                .stream()
                .map(h -> new HistoCarbDTO(h.getId(), h.getDate(), h.getPrix(),
                        h.getStation().getId(), h.getCarburant().getId()))
                .collect(Collectors.toList());
    }

    public List<HistoCarbDTO> getHistoByStationAndCarburant(Long idStation, Long idCarburant) {
        return em.createQuery(
                        "SELECT h FROM HistoCarb h WHERE h.station.id = :idS AND h.carburant.id = :idC",
                        HistoCarb.class
                ).setParameter("idS", idStation)
                .setParameter("idC", idCarburant)
                .getResultList()
                .stream()
                .map(h -> new HistoCarbDTO(h.getId(), h.getDate(), h.getPrix(),
                        h.getStation().getId(), h.getCarburant().getId()))
                .collect(Collectors.toList());
    }

    public void deleteHisto(Long id) {
        HistoCarb h = em.find(HistoCarb.class, id);
        if (h != null) em.remove(h);
    }
}
