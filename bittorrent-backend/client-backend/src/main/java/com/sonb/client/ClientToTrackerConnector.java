package com.sonb.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import util.RegisterTorrentRq;
import util.Torrent;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Łukasz Zachariasz
 */

public class ClientToTrackerConnector {

    @Value("${server.port}")
    private String serverPort;

    private final List<String> trackerList;

    private final RestTemplate restTemplate;


    public ClientToTrackerConnector(RestTemplateBuilder restTemplateBuilder, List<String> trackersIps) {
        this.restTemplate = restTemplateBuilder.build();
        this.trackerList = trackersIps;
    }

    public void registerTorrent(Torrent torrent) {
        RegisterTorrentRq registerTorrentRq = new RegisterTorrentRq();
        registerTorrentRq.setFileId(torrent.getFileId());
        registerTorrentRq.setClientIp(fetchClientIp());
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

    public void removeFileFromClient(String fileId, String trackerId, String myClientIp) {
        String s = prepareRemoveFileFromClientUrl(fileId, trackerId, myClientIp);
        restTemplate.exchange(s, HttpMethod.DELETE, HttpEntity.EMPTY, String.class);
    }

    private String prepareRemoveFileFromClientUrl(String fileId, String trackerId, String myClientIp) {
        return "NOT YET IMPLEMENTED";
    }

    private String fetchClientIp() {
        InetAddress ip;
        try {
            ip = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        String parsedIp = "http://" + ip.getHostName() + ":" + serverPort;
        System.out.println("My IP: " + parsedIp);

        return parsedIp;
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
                .queryParam("clientIp", fetchClientIp())
                .build()
                .toUriString();
    }

    private void internalRegisteredClientInTrackerAsOwnerOfFileById(String trackerUrl) {
        String tracker = restTemplate.getForObject(trackerUrl, String.class);
        System.out.println("Client registered in tracker: " + tracker);
    }
}
