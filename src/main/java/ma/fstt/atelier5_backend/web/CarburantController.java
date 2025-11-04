package ma.fstt.atelier5_backend.web;

import ma.fstt.atelier5_backend.dto.CarburantDTO;
import ma.fstt.atelier5_backend.entities.Carburant;
import ma.fstt.atelier5_backend.services.CarburantService;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/carburants")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CarburantController {

    @EJB
    private CarburantService carburantService;

    @GET
    public List<CarburantDTO> getAll() {
        return carburantService.getAllCarburants();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public CarburantDTO getById(@PathParam("id") Long id) {
        return carburantService.getCarburant(id); // peut renvoyer null si pas trouvé
    }


    @POST
    public void add(CarburantDTO c) {
        carburantService.addCarburant(c);
    }

    @PUT
    public void update(CarburantDTO c) {
        carburantService.updateCarburant(c);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) {
        carburantService.deleteCarburant(id);
    }
}
