package com.sonb.client;

/**
 * @author Łukasz Zachariasz
 */

public class ClientControlBehavior {

    private static int simulateDownloadDelay = 1000;

    private static int maxRetiresToDownloadPart = 3;

    public static void simulateDownloadDelay() {
        try {
            Thread.sleep(simulateDownloadDelay);
        } catch (InterruptedException e) {
            throw new RuntimeException("Thread Sleep Interrupted Exception: ", e);
        }
    }

    public static int getSimulateDownloadDelay() {
        return simulateDownloadDelay;
    }

    public static void setSimulateDownloadDelay(int simulateDownloadDelay) {
        ClientControlBehavior.simulateDownloadDelay = simulateDownloadDelay;
    }

    public static int getMaxRetiresToDownloadPart() {
        return maxRetiresToDownloadPart;
    }

    public static void setMaxRetiresToDownloadPart(int maxRetiresToDownloadPart) {
        ClientControlBehavior.maxRetiresToDownloadPart = maxRetiresToDownloadPart;
    }
}
