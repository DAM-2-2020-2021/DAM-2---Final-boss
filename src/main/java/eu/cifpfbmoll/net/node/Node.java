package eu.cifpfbmoll.net.node;

/**
 * Node information.
 */
public class Node {
    private int id;
    private String ip;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Node(int id, String ip) {
        this.id = id;
        this.ip = ip;
    }
}
