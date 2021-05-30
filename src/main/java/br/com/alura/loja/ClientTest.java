package br.com.alura.loja;

import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Produto;
import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ClientTest {

    private HttpServer server;
    private Client client;

    @Before
    public void startServerForTest(){
        server = Server.startServer();
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.register(new LoggingFilter());
        this.client = ClientBuilder.newClient();
    }

    @After
    public void stopServer(){
        server.stop();
    }

    @Test
    public void testaQueAConexaoComOServidorFunciona() {
        WebTarget target = client.target("http://www.mocky.io");
        String conteudo = target.path("/v2/52aaf5deee7ba8c70329fb7d").request().get(String.class);
        Assert.assertTrue(conteudo.contains("<rua>Rua Vergueiro 3185"));
    }

    @Test
    public void testaQueAoBuscarCarrinhoTrazOCarrinhoEsperado(){
        String conteudo = createWebTarget().path("/carrinhos/1").request().get(String.class);
        Carrinho carrinho =  new Gson().fromJson(conteudo, Carrinho.class);
        Assert.assertEquals("Rua Vergueiro 3185, 8 andar", carrinho.getRua());
    }

    @Test
    public void testaAdicaoDeNovoCarrinhoDeveRetornarMensagemDeSucesso(){
        Entity<String> entity = Entity.entity(jsonToTest(), MediaType.APPLICATION_JSON);
        Response response = createWebTarget().path("/carrinhos").request().post(entity);
        Assert.assertEquals(201, response.getStatus());
        String uriInResponse = response.getHeaderString("Location");
        String responseGet = client.target(uriInResponse).request().get(String.class);
        Assert.assertTrue(responseGet.contains("Tablet"));
    }

    private WebTarget createWebTarget() {
        Client client = ClientBuilder.newClient();
        return client.target("http://localhost:8080");
    }

    private String jsonToTest(){
        Carrinho carrinho = new Carrinho();
        carrinho.adiciona(new Produto(314L, "Tablet", 999, 1));
        carrinho.setRua("Rua Vergueiro");
        carrinho.setCidade("Sao Paulo");
        return carrinho.toJSON();
    }
}
