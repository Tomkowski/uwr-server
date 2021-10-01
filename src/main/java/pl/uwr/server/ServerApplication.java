package pl.uwr.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * klasa posiadająca metodę main. Stąd uruchamiana jest aplikacja
 */
@SpringBootApplication
public class ServerApplication {

    /**
     * główna metoda serwera
     */
    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }
}
