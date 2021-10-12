package com.niuzhendong.graphql.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class WebSiteUtil {
    private static final Logger logger = LoggerFactory.getLogger(WebSiteUtil.class);

    public WebSiteUtil() {
    }

    public static boolean isAjaxRequest(HttpServletRequest request) {
        String header = request.getHeader("X-Requested-With");
        return header != null && "XMLHttpRequest".equals(header);
    }

    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip) || ip.split(".").length != 4) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip) || ip.split(".").length != 4) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip) || ip.split(".").length != 4) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip) || ip.split(".").length != 4) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip) || ip.split(".").length != 4) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }

    public static String getLocalIP() throws UnknownHostException, SocketException {
        return isWindowsOS() ? InetAddress.getLocalHost().getHostAddress() : getLinuxLocalIp();
    }

    public static boolean isWindowsOS() {
        boolean isWindowsOS = false;
        String osName = System.getProperty("os.name");
        if (osName.toLowerCase().indexOf("windows") > -1) {
            isWindowsOS = true;
        }

        return isWindowsOS;
    }

    public static String getLocalHostName() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostName();
    }

    private static String getLinuxLocalIp() throws SocketException {
        String ip = "";

        try {
            Enumeration en = NetworkInterface.getNetworkInterfaces();

            label43:
            while(true) {
                NetworkInterface intf;
                String name;
                do {
                    do {
                        if (!en.hasMoreElements()) {
                            break label43;
                        }

                        intf = (NetworkInterface)en.nextElement();
                        name = intf.getName();
                    } while(name.contains("docker"));
                } while(name.contains("lo"));

                Enumeration enumIpAddr = intf.getInetAddresses();

                while(enumIpAddr.hasMoreElements()) {
                    InetAddress inetAddress = (InetAddress)enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        String ipaddress = inetAddress.getHostAddress();
                        if (!ipaddress.contains("::") && !ipaddress.contains("0:0:") && !ipaddress.contains("fe80")) {
                            ip = ipaddress;
                        }
                    }
                }
            }
        } catch (SocketException var7) {
            logger.error("获取ip地址异常");
            ip = "127.0.0.1";
            var7.printStackTrace();
        }

        System.out.println("IP:" + ip);
        return ip;
    }
}
