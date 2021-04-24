package eu.cifpfbmoll.net.node;

import eu.cifpfbmoll.util.Threaded;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Manages a ServerSocket to allow incoming connections/packets.
 */
public class NodeServer extends Threaded {
    private static final Logger log = LoggerFactory.getLogger(NodeServer.class);
    private final ServerSocket socket;

    /**
     * Create a new NodeServer using the constructor factory method.
     *
     * @param port Port number to bound socket to
     * @return NodeServer
     * @throws IOException If port binding fails
     */
    public static NodeServer create(int port) throws IOException {
        return new NodeServer(new ServerSocket(port));
    }

    /**
     * Create a new NodeServer using the constructor factory method.
     *
     * @param socket ServerSocket to create NodeServer from
     * @return NodeServer
     */
    public static NodeServer create(ServerSocket socket) throws NullPointerException {
        return new NodeServer(socket);
    }

    /**
     * Create a new NodeServer from an existing ServerSocket.
     *
     * @param socket ServerSocket to create NodeServer from
     */
    private NodeServer(ServerSocket socket) throws NullPointerException {
        if (socket == null) throw new NullPointerException("ServerSocket must not be null");
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
        } catch (Exception e) {
            log.error("ServerSocket thread crashed: ", e);
        } finally {
            try {
                this.socket.close();
            } catch (Exception e) {
                log.error("failed to close ServerSocket: ", e);
            }
        }
    }
}
