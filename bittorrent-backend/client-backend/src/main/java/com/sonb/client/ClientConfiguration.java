package com.sonb.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Mateusz Zakrzewski
 */

@Configuration
public class ClientConfiguration {

    @Value("${server.port}")
    Integer serverPort;

    @Bean
    ClientIpFetcher ipFetcher() {
        return new ClientIpFetcher(serverPort);
    }

    @Bean
    ClientToTrackerConnector clientToTrackerConnector(RestTemplateBuilder restTemplateBuilder,
                                                      @Value("${trackerUrlPrefix:http://localhost:700}") String urlPrefix,
                                                      @Value("${trackersNumber:3}") int trackerNumber) {

        List<String> trackersIps = IntStream.range(1, trackerNumber + 1)
                .mapToObj(value -> createTrackerUrl(value, urlPrefix))
                .collect(Collectors.toList());

        return new ClientToTrackerConnector(trackersIps, restTemplateBuilder, ipFetcher());
    }

    @Bean
    ClientToClientConnector clientToClientConnector(RestTemplateBuilder restTemplateBuilder) {
        return new ClientToClientConnector(restTemplateBuilder);
    }

    private String createTrackerUrl(int number, String trackerUrlPrefix) {
        if (trackerUrlPrefix.contains("localhost")) {
            return trackerUrlPrefix + number;
        } else {
            return trackerUrlPrefix + number + ":700" + number;
        }
    }

}
