package tracker;

import model.Peer;

import java.net.ServerSocket;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Łukasz Zachariasz
 */

public class Tracker implements Runnable {

    private ServerSocket trackerServerSocket;

    private ConcurrentHashMap<String, Set<Peer>> listOfPeers;

    @Override
    public void run() {

    }
}
