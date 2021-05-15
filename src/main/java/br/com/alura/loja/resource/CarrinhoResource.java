package br.com.alura.loja.resource;

import br.com.alura.loja.dao.CarrinhoDAO;
import br.com.alura.loja.modelo.Carrinho;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.awt.*;

@Path("carrinhos")
public class CarrinhoResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String busca(){
        CarrinhoDAO carrinhoDAO = new CarrinhoDAO();
        Carrinho carrinho = carrinhoDAO.busca(1l);
        return carrinho.toJSON();
    }
}