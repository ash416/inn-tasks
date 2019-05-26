package part1.lesson10.way02.server;

import java.net.InetAddress;

/**
 * The class consists client's info
 */

public class ClientInfo {
    InetAddress address;
    String name;
    Integer port;

    public ClientInfo(Integer port, InetAddress address) {
        this.port = port;
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public InetAddress getAddress() {
        return address;
    }

    public Integer getPort() {
        return port;
    }
}
