package com.example;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/validar")
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.APPLICATION_JSON)
public class ValidadorResource {

    @Inject ValidadorCTD validador;

    @POST
    public Response hello(DadosDTO dto) {
        String resposta = validador.iniciar(dto.url(), dto.quantidadeDias());
        if (resposta.contains("Não")) {
            return Response.status(500).entity(resposta).build();
        }
        return Response.ok(resposta).build();
    }
}