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

    @Bean
    ClientToTrackerConnector clientToTrackerConnector(RestTemplateBuilder restTemplateBuilder,
                                                      @Value("${trackersNumber:http://localhost:808}") String urlPrefix,
                                                      @Value("${trackersNumber:1}") int trackerNumber) {

        List<String> trackersIps = IntStream.range(1, trackerNumber + 1)
                .mapToObj(value -> urlPrefix + value)
                .collect(Collectors.toList());

        return new ClientToTrackerConnector(restTemplateBuilder, trackersIps);
    }

}
