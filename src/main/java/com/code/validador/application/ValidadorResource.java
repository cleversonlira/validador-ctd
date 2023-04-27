package com.code.validador.application;

import com.code.validador.domain.StatusCTD;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

@Path("/validar")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ValidadorResource {

    @Inject ValidadorCTD validador;
    Queue<StatusCTD> fila = new LinkedBlockingQueue<>();

    @GET
    @Transactional
    public Response validar(@QueryParam("github") String github, @QueryParam("qtd_dias") short qtd_dias) {
        StatusCTD statusCTD = validador.iniciar(github, qtd_dias);
        fila.add(statusCTD); // TODO thread para persistir?
        return Response.ok(statusCTD).build();
    }
}