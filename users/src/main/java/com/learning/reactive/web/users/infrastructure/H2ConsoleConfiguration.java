package com.learning.reactive.web.users.infrastructure;

import org.h2.tools.Server;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;

import java.sql.SQLException;

@Profile({"!prod"})
@Configuration
public class H2ConsoleConfiguration {
    private Server server;

    @EventListener(ApplicationStartedEvent.class)
    public void start() throws SQLException {
        final String PORT = "8082";
        this.server = Server.createWebServer("-webPort", PORT).start();
    }

    @EventListener(ContextClosedEvent.class)
    public void stop() {
        this.server.stop();
    }
}
