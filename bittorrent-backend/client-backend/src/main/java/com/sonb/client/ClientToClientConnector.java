package com.sonb.client;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

/**
 * @author Łukasz Zachariasz
 */

public class ClientToClientConnector {

    private final RestTemplate restTemplate;

    public ClientToClientConnector(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public String downloadPart(String clientIp, String fileId, Integer partId) {
        String url = clientIp + "/" + fileId + "/" + partId;
        return restTemplate.getForObject(url, String.class);
    }
}
