package eu.cifpfbmoll.net.node;

/**
 * Handle a connection with another Node.
 */
public class NodeConnection {
    private Node node;
    private NodeSocket socket;

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public NodeSocket getSocket() {
        return socket;
    }

    public void setSocket(NodeSocket socket) {
        this.socket = socket;
    }

    public NodeConnection(Node node, NodeSocket socket) {
        this.node = node;
        this.socket = socket;
    }
}
