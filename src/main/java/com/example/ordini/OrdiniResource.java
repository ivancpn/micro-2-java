package com.example.ordini;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;

@Path("/ordini")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrdiniResource {

    @Inject
    OrdiniService service;

    @GET
    public List<Ordine> elenco() {
        return service.tutti();
    }

    @GET
    @Path("/{id}")
    public Response dettaglio(@PathParam("id") Long id) {
        return service.perId(id)
                .map(o -> Response.ok(o).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    public Response crea(NuovoOrdine richiesta) {
        if (richiesta == null || !richiesta.valido()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new Errore("prodottoId e quantita (> 0) sono obbligatori"))
                    .build();
        }
        Ordine ordine = service.crea(richiesta);
        return Response.created(URI.create("/ordini/" + ordine.id())).entity(ordine).build();
    }

    @POST
    @Path("/{id}/annulla")
    public Response annulla(@PathParam("id") Long id) {
        return service.annulla(id)
                .map(o -> Response.ok(o).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    public record Errore(String messaggio) {
    }
}
