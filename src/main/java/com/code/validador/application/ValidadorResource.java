package com.code.validador.application;

import com.code.validador.domain.StatusCTD;
import io.quarkus.logging.Log;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/validar")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ValidadorResource {

    @Inject ValidadorCTD validador;

    @GET
    @Transactional
    public Response validar(@QueryParam("github") String github, @QueryParam("qtd_dias") short qtd_dias) {
        StatusCTD statusCTD = null;
        try {
            statusCTD = validador.iniciar(github, qtd_dias);
            statusCTD.persist();
            return Response.ok(statusCTD).build();
        } catch (Exception e) {
            Log.error("Erro ao validar o mentorado:" + statusCTD);
            e.printStackTrace();
            return Response.status(500).build();
        }
    }
}