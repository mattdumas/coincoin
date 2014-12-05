package fr.coincoin.resources;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("alerts")
public class AlertResource {



    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Coincoin!";
    }


}
