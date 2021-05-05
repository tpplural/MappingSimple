package pl.tpatola.rck.web;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Logger;

/**
 * Entry point for the app - uses {@link HttpServer} to serve requests.
 */
public class WebInterface {
    static Logger LOG = Logger.getLogger("DemoAppLogger");

    public static void main(String[] args) {

        HttpServer server;
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);

        try {
            server = HttpServer.create(new InetSocketAddress("localhost", 8001), 0);
            server.createContext("/test", new CustomHttpHandler());
            server.setExecutor(threadPoolExecutor);
            server.start();

        } catch (IOException e) {
            LOG.severe("Unable to start server, exiting");
            e.printStackTrace();
            System.exit(1);
        }

        LOG.info(" Server started on port 8001");

    }

}
