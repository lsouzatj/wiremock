package com.br.wiremock;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static java.util.Objects.isNull;
import static org.eclipse.jetty.util.TypeUtil.isFalse;

@Log4j2
@Profile({"dev", "qa", "local"})
@Configuration
public class WireMockConfig {

    private WireMockServer wireMockServer;

    @Value("${wiremock.toggle}")
    private boolean wiremockToggle;
    @Value("${wiremock.port}")
    private int wiremockPort;
    @Value("${wiremock.path}")
    private String wiremockPath;

    @PostConstruct
    public void startWireMockServer() {
        if (isFalse(wiremockToggle)) return;
        wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig()
                .port(wiremockPort)
                .usingFilesUnderClasspath(wiremockPath)
                .extensions(new ResponseTemplateTransformer(true)));
        wireMockServer.start();
        log.info("===================================================");
        log.info("= WireMock server started on port(s): {} (http) =", wiremockPort);
        log.info("===================================================");
    }

    @PreDestroy
    public void stopWireMockServer() {
        if (isNull(wireMockServer)) return;
        wireMockServer.stop();
        log.info("===========================");
        log.info("= WireMock server stopped =");
        log.info("===========================");
    }
}
