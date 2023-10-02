package app;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import io.javalin.Javalin;

public class QuteQuotes {
    private final Javalin server;

    public QuteQuotes () {
        server = Javalin.create(config -> {
            config.defaultContentType = "application/json";
        });

        this.server.get("/quotes", context -> QuoteApiHandler.getAll(context));
        this.server.get("/quote/{id}", context -> QuoteApiHandler.getOne(context));
        this.server.post("/quotes", context -> QuoteApiHandler.create(context));
        //this.serv
    }

    private static InetAddress getInetAddress(InetAddress ip, NetworkInterface networkInterface) {
        for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
            int npf = interfaceAddress.getNetworkPrefixLength();
            InetAddress address = interfaceAddress.getAddress();
            InetAddress broadcast = interfaceAddress.getBroadcast();
            if (broadcast == null && npf != 8) {
                System.out.print("");
            } else if (!address.equals(InetAddress.getLoopbackAddress())) {
                ip = address;
            }
        }
        return ip;
    }
    
    public static void displayServerOutput() throws UnknownHostException, SocketException {
        InetAddress ip = InetAddress.getLocalHost();
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
    
        while (interfaces.hasMoreElements()) {
            NetworkInterface networkInterface = interfaces.nextElement();
            if (!networkInterface.isUp()) {
                continue;
            }
            ip = getInetAddress(ip, networkInterface);
        }
    
        System.out.println("Server running & waiting for client connections.\n" +
                "My IP Address: " + ip );
    }

    public static void main(String[] args) throws UnknownHostException, SocketException {
        QuteQuotes server = new QuteQuotes ();
        displayServerOutput();
        server.start(5000);
    }

    public void start(int port) {
        this.server.start(port);
    }

    public void stop() {
        this.server.stop();
    }

    
}