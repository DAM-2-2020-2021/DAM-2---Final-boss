package eu.cifpfbmoll.net.packet;

import org.apache.commons.lang3.StringUtils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * Structure of a packet/message.
 * Instances of this object must be created using the Constructor Factory Method.
 */
public class Packet {
    private static final Charset CHARSET_ENCODING = StandardCharsets.UTF_8;
    private static final byte DEFAULT_TYPE_VALUE = 0;
    private static final int DEFAULT_TTL_VALUE = 32;
    private static final int PACKET_TYPE_SIZE = 4;
    private static final int PACKET_TTL_SIZE = 1;
    private static final int PACKET_ID_SIZE = 1;

    public String type;
    public byte ttl;
    public byte src;
    public byte dst;
    public byte resender;
    public byte[] data;

    /**
     * Create a new Packet using the constructor factory method.
     *
     * @param type     Packet type
     * @param ttl      Time to live
     * @param src      Source node id
     * @param dst      Destination node id
     * @param resender Resender node id
     * @param data     Packet data
     * @return New Packet instance
     */
    public static Packet create(String type, Integer ttl, Integer src, Integer dst, Integer resender, byte[] data) {
        return new Packet(Packet.formatType(type), ttl.byteValue(), src.byteValue(), dst.byteValue(), resender.byteValue(), data);
    }

    /**
     * Create a new Packet using the constructor factory method.
     *
     * @param type     Packet type
     * @param src      Source node id
     * @param dst      Destination node id
     * @param resender Resender node id
     * @param data     Packet data
     * @return New Packet instance
     */
    public static Packet create(String type, Integer src, Integer dst, Integer resender, byte[] data) {
        return Packet.create(type, DEFAULT_TTL_VALUE, src, dst, resender, data);
    }

    /**
     * Create a new Packet using the constructor factory method.
     *
     * @param type Packet type
     * @param src  Source node id
     * @param dst  Destination node id
     * @param data Packet data
     * @return New Packet instance
     */
    public static Packet create(String type, Integer src, Integer dst, byte[] data) {
        return Packet.create(type, DEFAULT_TTL_VALUE, src, dst, src, data);
    }

    /**
     * Decode Packet from byte array.
     *
     * @param bytes Byte array
     * @return Decoded Packet object
     */
    public static Packet fromBytes(byte[] bytes) {
        byte[] ptype = Arrays.copyOfRange(bytes, 0, PACKET_TYPE_SIZE);
        String type = new String(ptype, CHARSET_ENCODING);
        int index = PACKET_TYPE_SIZE;
        byte ttl = bytes[index++];
        byte src = bytes[index++];
        byte dst = bytes[index++];
        byte resender = bytes[index++];
        byte[] data = Arrays.copyOfRange(bytes, index, bytes.length);
        return new Packet(type, ttl, src, dst, resender, data);
    }

    /**
     * Get the correct format of type header field.
     * Checks for correct length and appends default values if needed.
     *
     * @return Corrected Packet type format
     */
    public static String formatType(String type) {
        if (type == null) type = StringUtils.EMPTY;
        byte[] bytes = new byte[PACKET_TYPE_SIZE];
        byte[] str = type.getBytes(CHARSET_ENCODING);
        for (int i = 0; i < PACKET_TYPE_SIZE; i++)
            bytes[i] = (i < str.length) ? str[i] : DEFAULT_TYPE_VALUE;
        return new String(bytes, CHARSET_ENCODING);
    }

    /**
     * Packet constructor with all attributes. This is the only constructor available,
     * in order to create new Packets the constructor factory method must be used.
     *
     * @param type     Packet type
     * @param ttl      Time to live
     * @param src      Source node id
     * @param dst      Destination node id
     * @param resender Resender node id
     * @param data     Packet data
     */
    private Packet(String type, byte ttl, byte src, byte dst, byte resender, byte[] data) {
        this.type = type;
        this.ttl = ttl;
        this.src = src;
        this.dst = dst;
        this.resender = resender;
        this.data = data;
    }

    /**
     * Get packet size.
     *
     * @return Packet size
     */
    public int size() {
        return PACKET_TYPE_SIZE + PACKET_TTL_SIZE + PACKET_ID_SIZE * 3 + this.data.length;
    }

    /**
     * Convert Packet data to byte array.
     *
     * @return Byte array
     */
    public byte[] bytes() {
        byte[] bytes = new byte[this.size()];
        byte[] str = this.type.getBytes(CHARSET_ENCODING);
        int index = 0;
        for (int i = 0; i < PACKET_TYPE_SIZE; i++)
            bytes[index++] = (i < str.length) ? str[i] : DEFAULT_TYPE_VALUE;
        bytes[index++] = this.ttl;
        bytes[index++] = this.src;
        bytes[index++] = this.dst;
        bytes[index++] = this.resender;
        System.arraycopy(this.data, 0, bytes, index, this.data.length);
        return bytes;
    }

    @Override
    public String toString() {
        return String.format("type: [%s]; ttl: [%d]; src: [%d]; dst: [%d]; resender: [%d]", this.type, this.ttl, this.src, this.dst, this.resender);
    }
}