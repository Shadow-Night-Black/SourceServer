package com.github.pterolatypus.sourcers.server.world;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom2.Element;

import com.github.pterolatypus.sourcers.server.Config;
import com.github.pterolatypus.sourcers.server.error.IDMismatchException;
import com.github.pterolatypus.sourcers.server.model.items.GroundItem;
import com.github.pterolatypus.sourcers.server.model.items.Item;
import com.github.pterolatypus.sourcers.server.model.players.Client;
import com.github.pterolatypus.sourcers.server.model.players.Player;
import com.github.pterolatypus.sourcers.server.model.players.PlayerHandler;
import com.github.pterolatypus.sourcers.server.util.xml.item.ItemConverter;
import com.github.pterolatypus.sourcers.server.util.xml.item.ItemElement;
import com.github.pterolatypus.sourcers.server.util.xml.item.ItemLoader;

/**
 * Handles ground items
 **/

public class ItemHandler {

	public Map<Integer, Item> itemList = new HashMap<Integer, Item>(Config.ITEM_LIMIT);
	public List<GroundItem> items = new ArrayList<GroundItem>();
	public static final int HIDE_TICKS = 100;

	public ItemHandler() {
		try {
			loadItemList();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IDMismatchException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds item to list
	 **/
	public void addItem(GroundItem item) {
		items.add(item);
	}

	/**
	 * Removes item from list
	 **/
	public void removeItem(GroundItem item) {
		items.remove(item);
	}

	/**
	 * Item amount
	 **/
	public int itemAmount(int itemId, int itemX, int itemY) {
		for (GroundItem i : items) {
			if (i.getItemId() == itemId && i.getItemX() == itemX
					&& i.getItemY() == itemY) {
				return i.getItemAmount();
			}
		}
		return 0;
	}

	/**
	 * Item exists
	 **/
	public boolean itemExists(int itemId, int itemX, int itemY) {
		for (GroundItem i : items) {
			if (i.getItemId() == itemId && i.getItemX() == itemX
					&& i.getItemY() == itemY) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Reloads any items if you enter a new region
	 **/
	public void reloadItems(Client c) {
		for (GroundItem i : items) {
			if (c != null) {
				if (c.getItems().tradeable(i.getItemId())
						|| i.getName().equalsIgnoreCase(c.playerName)) {
					if (c.distanceToPoint(i.getItemX(), i.getItemY()) <= 60) {
						if (i.hideTicks > 0
								&& i.getName().equalsIgnoreCase(c.playerName)) {
							c.getItems().removeGroundItem(i.getItemId(),
									i.getItemX(), i.getItemY(),
									i.getItemAmount());
							c.getItems().createGroundItem(i.getItemId(),
									i.getItemX(), i.getItemY(),
									i.getItemAmount());
						}
						if (i.hideTicks == 0) {
							c.getItems().removeGroundItem(i.getItemId(),
									i.getItemX(), i.getItemY(),
									i.getItemAmount());
							c.getItems().createGroundItem(i.getItemId(),
									i.getItemX(), i.getItemY(),
									i.getItemAmount());
						}
					}
				}
			}
		}
	}

	public void process() {
		ArrayList<GroundItem> toRemove = new ArrayList<GroundItem>();
		for (int j = 0; j < items.size(); j++) {
			if (items.get(j) != null) {
				GroundItem i = items.get(j);
				if (i.hideTicks > 0) {
					i.hideTicks--;
				}
				if (i.hideTicks == 1) { // item can now be seen by others
					i.hideTicks = 0;
					createGlobalItem(i);
					i.removeTicks = HIDE_TICKS;
				}
				if (i.removeTicks > 0) {
					i.removeTicks--;
				}
				if (i.removeTicks == 1) {
					i.removeTicks = 0;
					toRemove.add(i);
					// removeGlobalItem(i, i.getItemId(), i.getItemX(),
					// i.getItemY(), i.getItemAmount());
				}

			}

		}

		for (int j = 0; j < toRemove.size(); j++) {
			GroundItem i = toRemove.get(j);
			removeGlobalItem(i, i.getItemId(), i.getItemX(), i.getItemY(),
					i.getItemAmount());
		}
		/*
		 * for(GroundItem i : items) { if(i.hideTicks > 0) { i.hideTicks--; }
		 * if(i.hideTicks == 1) { // item can now be seen by others i.hideTicks
		 * = 0; createGlobalItem(i); i.removeTicks = HIDE_TICKS; }
		 * if(i.removeTicks > 0) { i.removeTicks--; } if(i.removeTicks == 1) {
		 * i.removeTicks = 0; removeGlobalItem(i, i.getItemId(), i.getItemX(),
		 * i.getItemY(), i.getItemAmount()); } }
		 */
	}

	/**
	 * Creates the ground item
	 **/
	public int[][] brokenBarrows = { { 4708, 4860 }, { 4710, 4866 },
			{ 4712, 4872 }, { 4714, 4878 }, { 4716, 4884 }, { 4720, 4896 },
			{ 4718, 4890 }, { 4720, 4896 }, { 4722, 4902 }, { 4732, 4932 },
			{ 4734, 4938 }, { 4736, 4944 }, { 4738, 4950 }, { 4724, 4908 },
			{ 4726, 4914 }, { 4728, 4920 }, { 4730, 4926 }, { 4745, 4956 },
			{ 4747, 4926 }, { 4749, 4968 }, { 4751, 4994 }, { 4753, 4980 },
			{ 4755, 4986 }, { 4757, 4992 }, { 4759, 4998 } };

	public void createGroundItem(Client c, int itemId, int itemX, int itemY,
			int itemAmount, int playerId) {
		if (itemId > 0) {
			if (itemId >= 2412 && itemId <= 2414) {
				c.sendMessage("The cape vanishes as it touches the ground.");
				return;
			}
			if (itemId > 4705 && itemId < 4760) {
				for (int j = 0; j < brokenBarrows.length; j++) {
					if (brokenBarrows[j][0] == itemId) {
						itemId = brokenBarrows[j][1];
						break;
					}
				}
			}
			if (getItemList(itemId).getStackSize() > 1
					&& itemAmount > 0) {
				for (int j = 0; j < itemAmount; j++) {
					c.getItems().createGroundItem(itemId, itemX, itemY, 1);
					GroundItem item = new GroundItem(itemId, itemX, itemY, 1,
							c.playerId, HIDE_TICKS,
							PlayerHandler.players[playerId].playerName);
					addItem(item);
				}
			} else {
				c.getItems().createGroundItem(itemId, itemX, itemY, itemAmount);
				GroundItem item = new GroundItem(itemId, itemX, itemY,
						itemAmount, c.playerId, HIDE_TICKS,
						PlayerHandler.players[playerId].playerName);
				addItem(item);
			}
		}
	}

	/**
	 * Shows items for everyone who is within 60 squares
	 **/
	public void createGlobalItem(GroundItem i) {
		for (Player p : PlayerHandler.players) {
			if (p != null) {
				Client person = (Client) p;
				if (person != null) {
					if (person.playerId != i.getItemController()) {
						if (!person.getItems().tradeable(i.getItemId())
								&& person.playerId != i.getItemController())
							continue;
						if (person.distanceToPoint(i.getItemX(), i.getItemY()) <= 60) {
							person.getItems().createGroundItem(i.getItemId(),
									i.getItemX(), i.getItemY(),
									i.getItemAmount());
						}
					}
				}
			}
		}
	}

	/**
	 * Removing the ground item
	 **/

	public void removeGroundItem(Client c, int itemId, int itemX, int itemY,
			boolean add) {
		for (GroundItem i : items) {
			if (i.getItemId() == itemId && i.getItemX() == itemX
					&& i.getItemY() == itemY) {
				if (i.hideTicks > 0
						&& i.getName().equalsIgnoreCase(c.playerName)) {
					if (add) {
						if (!c.getItems().specialCase(itemId)) {
							if (c.getItems().addItem(i.getItemId(),
									i.getItemAmount())) {
								removeControllersItem(i, c, i.getItemId(),
										i.getItemX(), i.getItemY(),
										i.getItemAmount());
								break;
							}
						} else {
							c.getItems().handleSpecialPickup(itemId);
							removeControllersItem(i, c, i.getItemId(),
									i.getItemX(), i.getItemY(),
									i.getItemAmount());
							break;
						}
					} else {
						removeControllersItem(i, c, i.getItemId(),
								i.getItemX(), i.getItemY(), i.getItemAmount());
						break;
					}
				} else if (i.hideTicks <= 0) {
					if (add) {
						if (c.getItems().addItem(i.getItemId(),
								i.getItemAmount())) {
							removeGlobalItem(i, i.getItemId(), i.getItemX(),
									i.getItemY(), i.getItemAmount());
							break;
						}
					} else {
						removeGlobalItem(i, i.getItemId(), i.getItemX(),
								i.getItemY(), i.getItemAmount());
						break;
					}
				}
			}
		}
	}

	/**
	 * Remove item for just the item controller (item not global yet)
	 **/

	public void removeControllersItem(GroundItem i, Client c, int itemId,
			int itemX, int itemY, int itemAmount) {
		c.getItems().removeGroundItem(itemId, itemX, itemY, itemAmount);
		removeItem(i);
	}

	/**
	 * Remove item for everyone within 60 squares
	 **/

	public void removeGlobalItem(GroundItem i, int itemId, int itemX,
			int itemY, int itemAmount) {
		for (Player p : PlayerHandler.players) {
			if (p != null) {
				Client person = (Client) p;
				if (person != null) {
					if (person.distanceToPoint(itemX, itemY) <= 60) {
						person.getItems().removeGroundItem(itemId, itemX,
								itemY, itemAmount);
					}
				}
			}
		}
		removeItem(i);
	}

	public Item getItemList(int i) {
		return (i >= 0 && i < itemList.size()) ? itemList.get(i) : new Item(
				true);
	}

	public boolean loadItemList() throws IOException, IDMismatchException {
		
		File file = new File(Config.PATH_CFG+"item.cfg");
		if (file.exists()) {
			ItemConverter converter = ItemConverter.getInstance();
			BufferedReader reader = new BufferedReader(new FileReader(file));
			while (reader.ready()) {
				String s = reader.readLine().replaceAll(" ", "");
				if (s.startsWith("/") || s.equals("[ENDOFITEMLIST]") || s.matches("\\s*")) {
					continue;
				}
				String[] token = s.split("\\t+");
				token[0] = token[0].replaceAll("item=", "");
				int id = Integer.parseInt(token[0]);
				String name = token[1];
				String desc = token[2];
				int shopVal = Integer.parseInt(token[3]);
				int lowAlch = Integer.parseInt(token[4]);
				int hiAlch = Integer.parseInt(token[5]);
				int[] bonuses = new int[12];
				for (int i = 0; i <= 11; i++) {
					bonuses[i] = Integer.parseInt(token[i+6]);
				}
				int stackSize = 1;
				converter.addItem(id, name, desc, shopVal, lowAlch, hiAlch, bonuses, stackSize);
			}
			converter.save();
			converter.abandon();
			reader.close();
		}
		
		ItemLoader loader = new ItemLoader(Config.PATH_ITEM_XML);
		for (int i = 0; i < loader.size(); i++) {
			Element e = loader.getItem(i);
			int id = Integer.parseInt(e.getChildText(ItemElement.PARAM_ID));
			
			if (id >= 0) {
				
				int newID = Integer.parseInt(e.getChildText(ItemElement.PARAM_ID));
				String newName = e.getChildText(ItemElement.PARAM_NAME);
				String newDesc = e.getChildText(ItemElement.PARAM_DESCRIPTION);
				String s = e.getChildText(ItemElement.PARAM_VALUE).replaceAll("\\.0", "");
				int newVal = Integer.parseInt(s.isEmpty()?"0":s);
				s = e.getChildText(ItemElement.PARAM_LO_ALCH).replaceAll("\\.0", "");
				int newLo = Integer.parseInt(s.isEmpty()?"0":s);
				s = e.getChildText(ItemElement.PARAM_LO_ALCH).replaceAll("\\.0", "");
				int newHi = Integer.parseInt(s.isEmpty()?"0":s);
				int[] newBonus = new int[12];
				for (int k = 1; k <= 12; k++) {
					newBonus[k-1] = Integer.parseInt(e.getChildText(ItemElement.PARAM_BONUS_ROOT+k));
				}
				int newStack;
				try {
					newStack = Integer.parseInt(e.getChildText(ItemElement.PARAM_STACK_SIZE));
				} catch (Exception e1) {
					e.addContent(new Element(ItemElement.PARAM_STACK_SIZE).addContent("1"));
					loader.save();
					newStack = Integer.parseInt(e.getChildText(ItemElement.PARAM_STACK_SIZE));
				}
				
				Item it =  new Item(newID, newName, newDesc, newVal, newLo, newHi, newBonus, newStack);
				
				if (itemList.containsKey(id)) {
					throw new IDMismatchException("item", it);
				}
				
				itemList.put(newID, it);
			}
		}
		return true;
	}
}
