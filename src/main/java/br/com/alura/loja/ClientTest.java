package br.com.alura.loja;

import br.com.alura.loja.modelo.Carrinho;
import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

public class ClientTest {

    private HttpServer server;

    @Before
    public void startServerForTest(){
        server = Server.startServer();
    }

    @After
    public void stopServer(){
        server.stop();
    }

    @Test
    public void testaQueAConexaoComOServidorFunciona() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://www.mocky.io");
        String conteudo = target.path("/v2/52aaf5deee7ba8c70329fb7d").request().get(String.class);
        Assert.assertTrue(conteudo.contains("<rua>Rua Vergueiro 3185"));
    }

    @Test
    public void testaQueAoBuscarCarrinhoTrazOCarrinhoEsperado(){
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080");
        String conteudo = target.path("/carrinhos").request().get(String.class);
        Carrinho carrinho =  new Gson().fromJson(conteudo, Carrinho.class);
        Assert.assertEquals("Rua Vergueiro 3185, 8 andar", carrinho.getRua());
    }
}
