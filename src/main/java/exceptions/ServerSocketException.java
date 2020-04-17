package exceptions;

import java.io.IOException;

/**
 * @author Łukasz Zachariasz
 */

public class ServerSocketException extends IOException {

    public ServerSocketException(String message) {
        super(message);
    }
}
