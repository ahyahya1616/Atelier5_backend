package ma.fstt.atelier5_backend.web;

import ma.fstt.atelier5_backend.dto.HistoCarbDTO;
import ma.fstt.atelier5_backend.services.HistoCarbService;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/histocarb")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HistoCarbController {

    @EJB
    private HistoCarbService histoService;

    @POST
    public void add(HistoCarbDTO h) {
        histoService.addHistoCarb(h);
    }

    @GET
    @Path("/station/{id}")
    public List<HistoCarbDTO> getByStation(@PathParam("id") Long id) {
        return histoService.getHistoByStation(id);
    }

    @GET
    @Path("/station/{idS}/carburant/{idC}")
    public List<HistoCarbDTO> getByStationAndCarburant(@PathParam("idS") Long idS, @PathParam("idC") Long idC) {
        return histoService.getHistoByStationAndCarburant(idS, idC);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) {
        histoService.deleteHisto(id);
    }
}
