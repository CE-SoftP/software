package com.app;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.time.Duration;
import java.util.logging.Logger;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

@Component
public class SignupTest {

    @Value("${app.url}")
    private String appUrl;

    private WebDriver webDriver;
    Logger logger = Logger.getLogger(getClass().getName());

    public void runTest() {
        HttpServer server = null;
        try {
            // Use a local server to serve the HTML file
            server = HttpServer.create(new InetSocketAddress(8080), 0);
            server.createContext("/form", new MyHandler());
            server.setExecutor(null); // creates a default executor
            server.start();

            // Open the HTML page in the browser
            webDriver = new ChromeDriver();
            webDriver.navigate().to(appUrl);

            // Wait for the page to load
            WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("user_name")));

            // Optionally, you can perform assertions or other actions on the loaded page

        } catch (IOException e) {
            logger.severe("An error occurred: " + e.getMessage());
        } finally {
            // Close the local server and the WebDriver when done
            if (server != null) {
                server.stop(0);
            }
            if (webDriver != null) {
                webDriver.quit();
            }
        }
    }

    class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            try (InputStream htmlStream = getClass().getResourceAsStream("/templates/signup.html")) {
                byte[] htmlBytes = htmlStream.readAllBytes();
                t.sendResponseHeaders(200, htmlBytes.length);
                OutputStream os = t.getResponseBody();
                os.write(htmlBytes);
                os.close();
            }
        }
    }
}
