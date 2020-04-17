package model;

import lombok.Data;

import java.net.InetAddress;

/**
 * @author Łukasz Zachariasz
 */

@Data
public class Peer {

    private InetAddress ipAddress;

    private Integer portNumber;

}
