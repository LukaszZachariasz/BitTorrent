package com.sonb.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author Łukasz Zachariasz
 */

public class ClientToClientConnector {


    @Value("${server.port}")
    String serverPort;

    private final List<String> trackerList;

    private final RestTemplate restTemplate;

    public ClientToClientConnector(RestTemplateBuilder restTemplateBuilder, List<String> trackersIps) {
        this.restTemplate = restTemplateBuilder.build();
        this.trackerList = trackersIps;
    }
}
