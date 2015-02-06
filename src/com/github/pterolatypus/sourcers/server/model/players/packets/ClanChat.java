package com.github.pterolatypus.sourcers.server.model.players.packets;

import com.github.pterolatypus.sourcers.server.Server;
import com.github.pterolatypus.sourcers.server.model.players.Client;
import com.github.pterolatypus.sourcers.server.model.players.PacketType;
import com.github.pterolatypus.sourcers.server.util.Misc;

/**
 * Chat
 **/
public class ClanChat implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		String textSent = Misc.longToPlayerName2(c.getInStream().readQWord());
		textSent = textSent.replaceAll("_", " ");
		// c.sendMessage(textSent);
		Server.clanChat.handleClanChat(c, textSent);
	}
}
