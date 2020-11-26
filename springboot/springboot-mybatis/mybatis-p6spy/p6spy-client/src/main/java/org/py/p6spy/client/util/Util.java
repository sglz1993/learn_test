package org.py.p6spy.client.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Util {

    public static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

}
