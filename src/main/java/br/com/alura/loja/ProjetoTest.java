package br.com.alura.loja;

import br.com.alura.loja.modelo.Projeto;
import com.google.gson.Gson;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

public class ProjetoTest {

    private HttpServer server;

    @Before
    public void startServer(){
        server = Server.startServer();
    }

    @After
    public void stopServer(){
        server.stop();
    }


    private WebTarget createTargetForTest(){
        Client client = ClientBuilder.newClient();
        return client.target("http://localhost:8080");
    }

    @Test
    public void testaQueAFazerBuscaNoBancoFunciona(){
        String conteudo = createTargetForTest().path("/projeto").request().get(String.class);
        Assert.assertTrue(conteudo.contains("Alura"));
    }

    @Test
    public void whenSearchProjectReturnProject_ok(){
        String content = createTargetForTest().path("/projeto").request().get(String.class);
        Projeto project = new Gson().fromJson(content, Projeto.class);
        Assert.assertEquals("Alura",project.getNome());
    }
}
