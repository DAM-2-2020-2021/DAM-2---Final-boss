package eu.cifpfbmoll.network;

import java.io.IOException;

/**
 * Socket interface which defines basic methods for reading and writing
 * to a socket using InputStream and OutputStream.
 */
public interface SocketInterface {
    /**
     * Write data to the current connection using OutputStream.
     *
     * @param data Data to send
     * @throws IOException If an IO error occurs
     */
    void write(byte[] data) throws IOException;

    /**
     * Read from current connection using InputStream and
     * store the data in a buffer.
     *
     * @param data Data buffer used to store read data
     * @return Length of read data, or -1 if there is no more to read
     * @throws IOException If an IO error occurs
     */
    int read(byte[] data) throws IOException;

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
    int read(byte[] data, int offset, int length) throws IOException;

    /**
     * Terminate connection and close socket and InputStream/OutputStream.
     *
     * @throws IOException If an IO error occurs
     */
    void close() throws IOException;
}
