package com.github.pterolatypus.sourcers.server.model.players.packets;

import com.github.pterolatypus.sourcers.server.model.items.GameItem;
import com.github.pterolatypus.sourcers.server.model.items.ItemO;
import com.github.pterolatypus.sourcers.server.model.players.Client;
import com.github.pterolatypus.sourcers.server.model.players.PacketType;

/**
 * Bank All Items
 **/
public class BankAll implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int removeSlot = c.getInStream().readUnsignedWordA();
		int interfaceId = c.getInStream().readUnsignedWord();
		int removeId = c.getInStream().readUnsignedWordA();

		switch (interfaceId) {
		case 3900:
			c.getShops().buyItem(removeId, removeSlot, 10);
			break;

		case 3823:
			c.getShops().sellItem(removeId, removeSlot, 10);
			break;

		case 5064:
			if (c.inTrade) {
				c.sendMessage("You can't store items while trading!");
				return;
			}
			if (ItemO.itemStackable[removeId]) {
				c.getItems().bankItem(c.playerItems[removeSlot], removeSlot,
						c.playerItemsN[removeSlot]);
			} else {
				c.getItems().bankItem(c.playerItems[removeSlot], removeSlot,
						c.getItems().itemAmount(c.playerItems[removeSlot]));
			}
			break;

		case 5382:
			c.getItems().fromBank(c.bankItems[removeSlot], removeSlot,
					c.bankItemsN[removeSlot]);
			break;

		case 3322:
			if (c.duelStatus <= 0) {
				if (ItemO.itemStackable[removeId]) {
					c.getTradeAndDuel().tradeItem(removeId, removeSlot,
							c.playerItemsN[removeSlot]);
				} else {
					c.getTradeAndDuel().tradeItem(removeId, removeSlot, 28);
				}
			} else {
				if (ItemO.itemStackable[removeId] || ItemO.itemIsNote[removeId]) {
					c.getTradeAndDuel().stakeItem(removeId, removeSlot,
							c.playerItemsN[removeSlot]);
				} else {
					c.getTradeAndDuel().stakeItem(removeId, removeSlot, 28);
				}
			}
			break;

		case 3415:
			if (c.duelStatus <= 0) {
				if (ItemO.itemStackable[removeId]) {
					for (GameItem item : c.getTradeAndDuel().offeredItems) {
						if (item.id == removeId) {
							c.getTradeAndDuel().fromTrade(
									removeId,
									removeSlot,
									c.getTradeAndDuel().offeredItems
											.get(removeSlot).amount);
						}
					}
				} else {
					for (GameItem item : c.getTradeAndDuel().offeredItems) {
						if (item.id == removeId) {
							c.getTradeAndDuel().fromTrade(removeId, removeSlot,
									28);
						}
					}
				}
			}
			break;

		case 6669:
			if (ItemO.itemStackable[removeId] || ItemO.itemIsNote[removeId]) {
				for (GameItem item : c.getTradeAndDuel().stakedItems) {
					if (item.id == removeId) {
						c.getTradeAndDuel()
								.fromDuel(
										removeId,
										removeSlot,
										c.getTradeAndDuel().stakedItems
												.get(removeSlot).amount);
					}
				}

			} else {
				c.getTradeAndDuel().fromDuel(removeId, removeSlot, 28);
			}
			break;

		}
	}

}
