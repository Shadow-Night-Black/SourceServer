package com.github.pterolatypus.sourcers.server.model.players.packets;

import com.github.pterolatypus.sourcers.server.model.players.Client;
import com.github.pterolatypus.sourcers.server.model.players.PacketType;

/**
 * Slient Packet
 **/
public class SilentPacket implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {

	}
}
