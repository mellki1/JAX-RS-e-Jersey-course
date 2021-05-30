package br.com.alura.loja.resource;

import br.com.alura.loja.dao.CarrinhoDAO;
import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Produto;
import com.google.gson.Gson;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("carrinhos")
public class CarrinhoResource {

    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String busca(@PathParam("id") long id){
        CarrinhoDAO carrinhoDAO = new CarrinhoDAO();
        Carrinho carrinho = carrinhoDAO.busca(id);
        return carrinho.toJSON();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response adiciona(String carrinho){
        Carrinho novoCarrinho = new Gson().fromJson(carrinho,Carrinho.class);
        new CarrinhoDAO().adiciona(novoCarrinho);
        URI uri = URI.create("/carrinhos/"+ novoCarrinho.getId());
        return Response.created(uri).build();
    }

    @Path("/{id}/produtos/{produtoId}")
    @DELETE
    public Response removeProdutoDoCarrinho(@PathParam("id") long id, @PathParam("produtoId") long produtoId){
        Carrinho carrinho = new CarrinhoDAO().busca(id);
        carrinho.remove(produtoId);
        return Response.ok().build();
    }

    @Path("/{id}/produtos/{produtoId}/quantidade")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response alteraProdutoNoCarrinho(@PathParam("id") long id, String conteudo){
        Carrinho carrinho = new CarrinhoDAO().busca(id);
        Produto produto = new Gson().fromJson(conteudo, Produto.class);
        carrinho.trocaQuantidade(produto);
        return Response.ok().build();
    }
}