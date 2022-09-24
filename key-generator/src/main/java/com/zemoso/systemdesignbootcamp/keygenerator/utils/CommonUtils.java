package com.zemoso.systemdesignbootcamp.keygenerator.utils;

import lombok.NonNull;

import java.net.NetworkInterface;
import java.security.SecureRandom;
import java.util.Enumeration;

public class CommonUtils {

    public static int generateNodeIdFromHardwareAddress(@NonNull Integer maxNodeId) {
        int nodeId;
        try {
            StringBuilder sb = new StringBuilder();
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                byte[] mac = networkInterface.getHardwareAddress();
                if (mac != null) {
                    for (byte b : mac) {
                        sb.append(String.format("%02X", b));
                    }
                }
            }
            nodeId = sb.toString().hashCode();
        } catch (Exception ex) {
            nodeId = (new SecureRandom().nextInt());
        }
        nodeId = nodeId & maxNodeId;
        return nodeId;
    }
}
