package com.github.pterolatypus.sourcers.server.model.players.packets;

import com.github.pterolatypus.sourcers.server.Config;
import com.github.pterolatypus.sourcers.server.model.players.Client;
import com.github.pterolatypus.sourcers.server.model.players.PacketType;

/**
 * Trading
 */
public class Trade implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int tradeId = c.getInStream().readSignedWordBigEndian();
		c.getPA().resetFollow();
		if (c.disconnected) {
			c.tradeStatus = 0;
		}
		if (c.arenas()) {
			c.sendMessage("You can't trade inside the arena!");
			return;
		}

		if (c.playerRights == 2 && !Config.ADMIN_CAN_TRADE) {
			c.sendMessage("Trading as an admin has been disabled.");
			return;
		}
		if (tradeId != c.playerId)
			c.getTradeAndDuel().requestTrade(tradeId);
	}

}
