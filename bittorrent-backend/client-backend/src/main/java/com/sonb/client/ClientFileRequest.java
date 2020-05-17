package com.sonb.client;

import java.util.List;

/**
 * @author Łukasz Zachariasz
 */

public class ClientFileRequest {

    String humanName;

    List<String> value;


    public String getHumanName() {
        return humanName;
    }

    public void setHumanName(String humanName) {
        this.humanName = humanName;
    }

    public List<String> getValue() {
        return value;
    }

    public void setValue(List<String> value) {
        this.value = value;
    }
}
