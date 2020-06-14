package com.sonb.client;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import util.RegisterTorrentRq;
import util.Torrent;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Łukasz Zachariasz
 */

public class ClientToTrackerConnector {

    private final List<String> trackerList;

    private final RestTemplate restTemplate;

    private final ClientIpFetcher clientIpFetcher;

    public ClientToTrackerConnector(List<String> trackerList, RestTemplateBuilder restTemplateBuilder, ClientIpFetcher clientIpFetcher) {
        this.trackerList = trackerList;
        this.restTemplate = restTemplateBuilder.build();
        this.clientIpFetcher = clientIpFetcher;
    }

    public void registerTorrent(Torrent torrent) {
        RegisterTorrentRq registerTorrentRq = new RegisterTorrentRq();
        registerTorrentRq.setFileId(torrent.getFileId());
        registerTorrentRq.setClientIp(clientIpFetcher.getClientIp());
        HttpEntity<RegisterTorrentRq> httpEntity = new HttpEntity<>(registerTorrentRq);
        trackerList.forEach(trackerUrl -> restTemplate.exchange(trackerUrl + "/registerTorrent", HttpMethod.POST, httpEntity, String.class));
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
        return trackerUrl + "/clients/" + fileId;
    }

    @SuppressWarnings("unchecked")
    private List<String> internalClientOwnersForFileId(String url) {
        return (List<String>) restTemplate.getForObject(url, List.class);
    }

    public void removeFileFromClient(String fileId, String myClientIp) {
        IntStream.range(0, trackerList.size())
                .boxed()
                .forEach(tracker -> {
                    try {
                        String s = prepareRemoveFileFromClientUrl(fileId, tracker, myClientIp);
                        restTemplate.exchange(s, HttpMethod.DELETE, HttpEntity.EMPTY, String.class);
                    } catch (Exception e) {
                        throw new RuntimeException("Tracker is not responding while removing client");
                    }
                });
    }

    private String prepareRemoveFileFromClientUrl(String fileId, Integer trackerId, String myClientIp) {
        return trackerList.get(trackerId) + "/remove/" + fileId + "?clientIp=" + myClientIp;
    }

    public List<String> getTrackerList() {
        return trackerList;
    }

    public void registerFileOwnerClient(String fileId) {
        trackerList
                .stream()
                .map(trackerUrl -> createRegisterClientUrl(trackerUrl, fileId))
                .forEach(this::internalRegisteredClientInTrackerAsOwnerOfFileById);
    }


    private String createRegisterClientUrl(String trackerUrl, String fileId) {
        return UriComponentsBuilder.fromHttpUrl(trackerUrl + "/registerFileOwner/" + fileId)
                .queryParam("clientIp", clientIpFetcher.getClientIp())
                .build()
                .toUriString();
    }

    private void internalRegisteredClientInTrackerAsOwnerOfFileById(String trackerUrl) {
        String tracker = restTemplate.getForObject(trackerUrl, String.class);
        System.out.println("Client registered in tracker: " + tracker);
    }
}
