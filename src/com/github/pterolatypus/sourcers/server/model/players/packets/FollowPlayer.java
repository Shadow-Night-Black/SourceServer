package com.github.pterolatypus.sourcers.server.model.players.packets;

import com.github.pterolatypus.sourcers.server.model.players.Client;
import com.github.pterolatypus.sourcers.server.model.players.PacketType;
import com.github.pterolatypus.sourcers.server.model.players.PlayerHandler;

/**
 * Follow Player
 **/
public class FollowPlayer implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int followPlayer = c.getInStream().readUnsignedWordBigEndian();
		if (PlayerHandler.players[followPlayer] == null) {
			return;
		}
		c.playerIndex = 0;
		c.npcIndex = 0;
		c.mageFollow = false;
		c.usingBow = false;
		c.usingRangeWeapon = false;
		c.followDistance = 1;
		c.followId = followPlayer;
	}
}
