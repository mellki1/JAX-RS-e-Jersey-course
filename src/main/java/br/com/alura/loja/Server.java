package br.com.alura.loja;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

public class Server {
    public static void main(String[] args) throws IOException {
        HttpServer server = startServer();
        System.in.read();
        server.stop();

    }

    public static HttpServer startServer() {
        URI uri = URI.create("http://localhost:8080/");
        ResourceConfig config = new ResourceConfig().packages("br.com.alura.loja");
        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(uri, config);
        System.out.println("Servidor rodando");
        return server;
    }
}
