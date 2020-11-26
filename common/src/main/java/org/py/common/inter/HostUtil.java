package org.py.common.inter;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class HostUtil {

    public static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

}
