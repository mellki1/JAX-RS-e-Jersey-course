package br.com.alura.loja;

import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.modelo.Projeto;
import com.google.gson.Gson;
import org.glassfish.grizzly.http.server.HttpServer;
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
        String content = createTargetForTest().path("/projeto/2").request().get(String.class);
        Projeto project = new Gson().fromJson(content, Projeto.class);
        Assert.assertEquals("Alura",project.getNome());
    }

    @Test
    public void testaAdicaoDeNovoProjetoDeveRetornarMensagemDeSucesso(){
        Entity<String> entity = Entity.entity(jsonToTest(), MediaType.APPLICATION_JSON);
        Response response = createWebTarget().path("/projetos").request().post(entity);
        Assert.assertEquals(201, response.getStatus());
        String uriInResponse = response.getHeaderString("Location");
        String responseGet = getServerClient().target(uriInResponse).request().get(String.class);
        Assert.assertTrue(responseGet.contains("Projeto teste"));
    }

    private WebTarget createWebTarget(){
        Client client = ClientBuilder.newClient();
        return client.target("http://localhost:8080");
    }

    private Client getServerClient(){
        return ClientBuilder.newClient();
    }

    private String jsonToTest(){
        Projeto projeto = new Projeto();
        projeto.setAnoDeInicio(2021);
        projeto.setId(5355L);
        projeto.setNome("Projeto teste");
        return projeto.toJson();
    }
}
