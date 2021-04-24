package eu.cifpfbmoll.net.packet;

import java.util.Hashtable;

/**
 * Manages incoming Packets and processes them using PacketHandler.
 * For every Packet type a PacketHandler must be specified in order to process it.
 */
public class PacketManager {
    private final Hashtable<String, PacketHandler> handlerTable = new Hashtable<>();

    /**
     * Add a new Packet Handler for Packet type.
     *
     * @param type Packet type to handle
     * @param packetHandler Packet Handler
     */
    public void addHandler(String type, PacketHandler packetHandler) {
        String packetType = Packet.formatType(type);
        if (!this.handlerTable.containsKey(packetType))
            this.handlerTable.put(packetType, packetHandler);
    }

    /**
     * Removed Packet Handler for Packet type.
     *
     * @param type Packet type to remove
     */
    public void removeHandler(String type) {
        String packetType = Packet.formatType(type);
        this.handlerTable.remove(packetType);
    }

    /**
     * Process a packet using its Packet type handler.
     *
     * @param packet Packet to process
     */
    public void process(Packet packet) {
        PacketHandler handler = this.handlerTable.get(packet.type);
        if (handler != null)
            handler.handle(packet);
    }
}