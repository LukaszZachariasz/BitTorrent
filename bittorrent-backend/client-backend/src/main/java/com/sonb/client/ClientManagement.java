package com.sonb.client;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Łukasz Zachariasz
 */

public class ClientManagement {

    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    public int getAvailableClientNumber(List<String> newClientIps) {
        return random.nextInt(0, newClientIps.size());
    }
}
