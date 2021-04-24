package eu.cifpfbmoll.net.packet;

@FunctionalInterface
public interface PacketHandler {
    void handle(Packet packet);
}