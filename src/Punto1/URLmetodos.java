package Punto1;

import java.net.MalformedURLException;
import java.net.URL;

public class URLmetodos {
    public static void URLmetodos() throws MalformedURLException {
        URL url = new URL("https://myaccount.microsoft.com/?ref=MeControl");

        System.out.println("getProtocol:" + url.getProtocol());
        System.out.println("getAuthority:" + url.getProtocol());
        System.out.println("getHost:" + url.getHost());
        System.out.println("getPort:" + url.getPort());
        System.out.println("getDefaultPort: " + url.getDefaultPort());
        System.out.println("getPath:" + url.getPath());
        System.out.println("getQuery:" + url.getQuery());
        System.out.println("getFile:" + url.getFile());
        System.out.println("getRef:" + url.getRef());
    }
    public static void main(String[] args) throws MalformedURLException {
        URLmetodos();
    }
}

