package ma.fstt.atelier5_backend.web;


import ma.fstt.atelier5_backend.dto.StationDTO;
import ma.fstt.atelier5_backend.services.StationService;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/stations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StationController {

    @EJB
    private StationService stationService;

    @GET
    public List<StationDTO> getAll() {
        return stationService.getAllStations();
    }

    @GET
    @Path("/{id}")
    public StationDTO getOne(@PathParam("id") Long id) {
        return stationService.getStation(id);
    }

    @POST
    public void add(StationDTO s) {
        stationService.addStation(s);
    }

    @PUT
    public void update(StationDTO s) {
        stationService.updateStation(s);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) {
        stationService.deleteStation(id);
    }
}
