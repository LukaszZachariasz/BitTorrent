package tracker;

import exceptions.ServerSocketException;
import model.Peer;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Łukasz Zachariasz
 */

public class Tracker implements Runnable {

    private ServerSocket trackerServerSocket;

    private ConcurrentHashMap<String, Set<Peer>> listOfPeers;

    public Tracker(Integer port) throws ServerSocketException {
        createTrackerServerSocket(port);

        this.run();
    }

    @Override
    public void run() {

    }

    private void createTrackerServerSocket(Integer port) throws ServerSocketException {
        try {
            this.trackerServerSocket = new ServerSocket(port);
            this.trackerServerSocket.setSoTimeout(2000);
        } catch (IOException e) {
            throw new ServerSocketException("Error creating Tracker Server Socket");
        }
    }

}
