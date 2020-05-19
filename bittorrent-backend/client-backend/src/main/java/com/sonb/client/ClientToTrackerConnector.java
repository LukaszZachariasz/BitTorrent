package com.sonb.client;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import util.RegisterTorrentRq;
import util.Torrent;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Łukasz Zachariasz
 */

public class ClientToTrackerConnector {

    private final List<String> trackerList;

    private final RestTemplate restTemplate;


    public ClientToTrackerConnector(RestTemplateBuilder restTemplateBuilder, List<String> trackersIps) {
        this.restTemplate = restTemplateBuilder.build();
        this.trackerList = trackersIps;
    }

    public void registerTorrent(Torrent torrent) {
        RegisterTorrentRq registerTorrentRq = new RegisterTorrentRq();
        registerTorrentRq.setFileId(torrent.getFileId());
        HttpEntity<RegisterTorrentRq> httpEntity = new HttpEntity<>(registerTorrentRq);
        trackerList.forEach(s -> restTemplate.exchange(s + "/register", HttpMethod.POST, httpEntity, String.class));
    }

    public List<String> clientOwnersForFileId(String fileId) {
        return trackerList
                .stream()
                .map(trackerUrl -> createGetClientsUrl(trackerUrl, fileId))
                .flatMap(clientsUrl -> internalClientOwnersForFileId(clientsUrl).stream())
                .distinct()
                .collect(Collectors.toList());
    }

    private String createGetClientsUrl(String trackerUrl, String fileId) {
        return null;
    }

    @SuppressWarnings("unchecked")
    private List<String> internalClientOwnersForFileId(String url) {
        return (List<String>) restTemplate.getForObject(url, List.class);
    }

    public void removeFileFromClient(String fileId, String trackerId, String myClientIp) {
        String s = prepareRemoveFileFromClientUrl(fileId, trackerId, myClientIp);
        restTemplate.exchange(s, HttpMethod.DELETE, HttpEntity.EMPTY, String.class);
    }

    private String prepareRemoveFileFromClientUrl(String fileId, String trackerId, String myClientIp) {
        return "NOT YET IMPLEMENTED";
    }

    public List<String> getTrackerList() {
        return trackerList;
    }
}
