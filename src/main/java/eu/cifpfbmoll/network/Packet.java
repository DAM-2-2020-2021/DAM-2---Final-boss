package eu.cifpfbmoll.network;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * Structure of a packet/message.
 */
public class Packet {
    private static final Charset CHARSET_ENCODING = StandardCharsets.UTF_8;
    private static final byte DEFAULT_TYPE_VALUE = 0;
    private static final int PACKET_TYPE_SIZE = 4;
    private static final int PACKET_TTL_SIZE = 1;
    private static final int PACKET_ID_SIZE = 1;

    public String type = "";
    public byte ttl = 32;
    public byte src = 0;
    public byte dst = 0;
    public byte resender = 0;
    public byte[] data;

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
        return new Packet(type, src, dst, resender, data);
    }

    public Packet(String type, byte src, byte dst, byte resender, byte[] data) {
        this.type = type;
        this.src = src;
        this.dst = dst;
        this.resender = resender;
        this.data = data;
    }

    public Packet(byte[] data) {
    }

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
        return String.format("[%s][%d][%d][%d][%d]", this.type, this.ttl, this.src, this.dst, this.resender);
    }
}