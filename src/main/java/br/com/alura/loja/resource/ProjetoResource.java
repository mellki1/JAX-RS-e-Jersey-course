package br.com.alura.loja.resource;

import br.com.alura.loja.dao.ProjetoDAO;
import br.com.alura.loja.modelo.Projeto;
import com.google.gson.Gson;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("projetos")
public class ProjetoResource {

    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String busca(@PathParam("id") long id){
        ProjetoDAO projetoDAO = new ProjetoDAO();
        Projeto projeto = projetoDAO.busca(id);
        return projeto.toJson();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response adiciona(String projeto){
        Projeto novoProjeto = new Gson().fromJson(projeto,Projeto.class);
        new ProjetoDAO().adiciona(novoProjeto);
        URI uri = URI.create("/projetos/"+ novoProjeto.getId());
        return Response.created(uri).build();
    }

    @Path("/{id}")
    @DELETE
    public Response deleta(@PathParam("id") long id){
        new ProjetoDAO().remove(id);
        return Response.ok().build();
    }
}
