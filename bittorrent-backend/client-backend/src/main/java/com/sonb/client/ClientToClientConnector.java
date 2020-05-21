package com.sonb.client;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author Łukasz Zachariasz
 */

public class ClientToClientConnector {


    private final List<String> trackerList;

    private final RestTemplate restTemplate;

    public ClientToClientConnector(RestTemplateBuilder restTemplateBuilder, List<String> trackersIps) {
        this.restTemplate = restTemplateBuilder.build();
        this.trackerList = trackersIps;
    }

    public String downloadPart(String clientIp, String fileId, Integer partId) {
        String url = clientIp + "/" + fileId + "/" + partId;
        return restTemplate.getForObject(url, String.class);
    }
}
