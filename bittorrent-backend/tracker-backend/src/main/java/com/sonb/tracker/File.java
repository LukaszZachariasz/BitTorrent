package com.sonb.tracker;

import java.util.Map;

/**
 * @author Łukasz Zachariasz
 */

public class File {

    int fileSize;
    Map<String, PartContent> partIdToPartContent;

    String getFileContent() {
        return partIdToPartContent.entrySet()
                .stream()
                .map(Map.Entry::getValue)
                .map(partContent -> partContent.data)
                .reduce(String::concat)
                .orElseThrow(() -> new IllegalStateException("File is empty!"));
    }

}
