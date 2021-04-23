package eu.cifpfbmoll.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 */
public class BaseSocket implements SocketInterface {
    private final Socket socket;
    private final InputStream inputStream;
    private final OutputStream outputStream;

    /**
     * Create a new BaseSocket with a specified host and port.
     *
     * @param host Host name
     * @param port Port number
     * @throws IOException If the creation of the Socket fails
     */
    public BaseSocket(String host, int port) throws IOException {
        this(new Socket(host, port));
    }

    /**
     * Create a new BaseSocket from an existing standard Socket.
     *
     * @param socket Socket to create BaseSocket from
     * @throws IOException If the assignment of InputStream/OutputStream fails
     */
    public BaseSocket(Socket socket) throws IOException {
        this.socket = socket;
        this.inputStream = socket.getInputStream();
        this.outputStream = socket.getOutputStream();
    }

    @Override
    public void write(byte[] data) throws IOException {
        this.outputStream.write(data);
        this.outputStream.flush();
    }

    @Override
    public int read(byte[] data) throws IOException {
        return this.inputStream.read(data);
    }

    @Override
    public int read(byte[] data, int offset, int length) throws IOException {
        return this.inputStream.read(data, offset, length);
    }

    @Override
    public void close() throws IOException {
        if (this.inputStream != null) this.inputStream.close();
        if (this.outputStream != null) this.outputStream.close();
        if (this.socket != null) this.socket.close();
    }
}
