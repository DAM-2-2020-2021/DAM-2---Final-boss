package eu.cifpfbmoll.net.node;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Basic Socket IO operations.
 */
public class NodeSocket {
    private final Socket socket;
    private final InputStream inputStream;
    private final OutputStream outputStream;

    /**
     * Create a new NodeSocket using the constructor factory method.
     *
     * @param host Host name
     * @param port Port number
     * @return NodeSocket
     * @throws IOException If the assignment of InputStream/OutputStream fails
     */
    public static NodeSocket create(String host, int port) throws IOException {
        return NodeSocket.create(new Socket(host, port));
    }

    /**
     * Create a new NodeSocket using the constructor factory method.
     *
     * @param socket Socket to create NodeSocket from
     * @return NodeSocket
     * @throws IOException If the assignment of InputStream/OutputStream fails
     */
    public static NodeSocket create(Socket socket) throws IOException {
        return new NodeSocket(socket);
    }

    /**
     * Create a new NodeSocket from an existing standard Socket.
     *
     * @param socket Socket to create NodeSocket from
     * @throws IOException If the assignment of InputStream/OutputStream fails
     */
    protected NodeSocket(Socket socket) throws IOException {
        this.socket = socket;
        this.inputStream = socket.getInputStream();
        this.outputStream = socket.getOutputStream();
    }

    /**
     * Write data to the current connection using OutputStream.
     *
     * @param data Data to send
     * @throws IOException If an IO error occurs
     */
    public void write(byte[] data) throws IOException {
        this.outputStream.write(data);
        this.outputStream.flush();
    }

    /**
     * Read from current connection using InputStream and
     * store the data in a buffer.
     *
     * @param data Data buffer used to store read data
     * @return Length of read data, or -1 if there is no more to read
     * @throws IOException If an IO error occurs
     */
    public int read(byte[] data) throws IOException {
        return this.inputStream.read(data);
    }

    /**
     * Read from current connection using InputStream and
     * store the data in a buffer.
     *
     * @param data Data buffer used to store read data
     * @param offset Offset to read from
     * @param length Maximum length of read data
     * @return Length of read data, or -1 if there is no more to read
     * @throws IOException If an IO error occurs
     */
    public int read(byte[] data, int offset, int length) throws IOException {
        return this.inputStream.read(data, offset, length);
    }

    /**
     * Terminate connection and close socket and InputStream/OutputStream.
     *
     * @throws IOException If an IO error occurs
     */
    public void close() throws IOException {
        if (this.inputStream != null) this.inputStream.close();
        if (this.outputStream != null) this.outputStream.close();
        if (this.socket != null) this.socket.close();
    }
}
