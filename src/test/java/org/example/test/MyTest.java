package org.example.test;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.client.ContentResponse;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.ee10.servlet.ServletContextHandler;
import org.eclipse.jetty.ee10.webapp.WebAppContext;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.BufferUtil;
import org.eclipse.jetty.util.Callback;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class MyTest
{
    private Server server;
    private ServerConnector connector;
    private HttpClient client;

    public void start(String mode) throws Exception
    {
        // Create a new Server.
        server = new Server();

        // Configure a connector to accept HTTP requests TCP/IP on port 8080.
        connector = new ServerConnector(server);
        server.addConnector(connector);
        connector.setPort(8080);

        // Choose the handler to add to Jetty.
        switch (mode)
        {
            case "Handler":
                configureJettyCoreHandler();
                break;
            case "ServletContextHandler":
                configureServletContextHandler();
                break;
            case "WebAppContext":
                configureWebAppContext();
                break;
            default:
                throw new IllegalStateException(mode);
        }

        // Start the server.
        server.start();

        // Jetty HTTP Client used for testing.
        client = new HttpClient();
        client.start();
    }

    @AfterEach
    public void stop() throws Exception
    {
        client.stop();
        server.stop();
    }

    private void configureJettyCoreHandler()
    {
        // Jetty Core Handler Setup.
        server.setHandler(new Handler.Abstract() {
            @Override
            public boolean handle(Request request, Response response, Callback callback) throws Exception
            {
                response.getHeaders().put("Content-Type", "text/plain");
                response.setStatus(HttpStatus.OK_200);
                response.write(true, BufferUtil.toBuffer("hello world from jetty-core"), callback);
                return true;
            }
        });
    }

    private void configureServletContextHandler()
    {
        // Embedded Servlet Usage.
        ServletContextHandler contextHandler = new ServletContextHandler();
        contextHandler.setContextPath("/");
        contextHandler.addServlet(new HttpServlet()
        {
            @Override
            protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
            {
                resp.setContentType("text/plain");
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().print("hello world from servlet");
            }
        }, "/");
        server.setHandler(contextHandler);
    }

    private void configureWebAppContext()
    {
        // This reads the war file and uses it for the WebAppContext.
        // This is what is used in the GAE code, where the war file contains the application to deploy.
        WebAppContext webAppContext = new WebAppContext();
        webAppContext.setContextPath("/");
        webAppContext.setWar("target/basic-webapp.war");
        server.setHandler(webAppContext);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Handler", "ServletContextHandler", "WebAppContext"})
    public void test(String mode) throws Exception
    {
        start(mode);
        ContentResponse response = client.GET("http://localhost:" + connector.getLocalPort());
        System.err.println(response);
        System.err.println(response.getContentAsString());
    }
}
