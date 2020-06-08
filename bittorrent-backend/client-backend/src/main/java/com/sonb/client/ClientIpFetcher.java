package com.sonb.client;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Mateusz Zakrzewski
 */
public class ClientIpFetcher {

    private final Integer serverPort;

    public ClientIpFetcher(Integer serverPort) {
        this.serverPort = serverPort;
    }

    public String getClientIp() {
        InetAddress ip;
        try {
            ip = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }

        String hostname = "localhost";

        if (ip.getHostName().contains("tracker") || ip.getHostName().contains("client")) {
            hostname = ip.getHostName();
        }

        String parsedIp = "http://" + hostname + ":" + serverPort;
        System.out.println("My IP: " + parsedIp);

        return parsedIp;
    }

}
