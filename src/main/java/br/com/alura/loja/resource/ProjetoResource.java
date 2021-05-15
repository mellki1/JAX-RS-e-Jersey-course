package br.com.alura.loja.resource;

import br.com.alura.loja.dao.ProjetoDAO;
import br.com.alura.loja.modelo.Projeto;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("projeto")
public class ProjetoResource {

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String busca(){
        ProjetoDAO projetoDAO = new ProjetoDAO();
        Projeto projeto = projetoDAO.busca(2l);
        return projeto.toJson();
    }
}
