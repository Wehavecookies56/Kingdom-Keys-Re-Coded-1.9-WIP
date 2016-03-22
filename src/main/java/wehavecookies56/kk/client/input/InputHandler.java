package wehavecookies56.kk.client.input;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import wehavecookies56.kk.KingdomKeys;
import wehavecookies56.kk.capabilities.DriveStateCapability.IDriveState;
import wehavecookies56.kk.capabilities.PlayerStatsCapability.IPlayerStats;
import wehavecookies56.kk.capabilities.SummonKeybladeCapability.ISummonKeyblade;
import wehavecookies56.kk.client.gui.GuiCommandMenu;
import wehavecookies56.kk.driveforms.ModDriveForms;
import wehavecookies56.kk.item.ItemDriveForm;
import wehavecookies56.kk.item.ItemKKPotion;
import wehavecookies56.kk.item.ItemKeyblade;
import wehavecookies56.kk.item.ItemKeychain;
import wehavecookies56.kk.item.ItemSpellOrb;
import wehavecookies56.kk.lib.Constants;
import wehavecookies56.kk.lib.Strings;
import wehavecookies56.kk.magic.Magic;
import wehavecookies56.kk.network.packet.PacketDispatcher;
import wehavecookies56.kk.network.packet.server.AntiPoints;
import wehavecookies56.kk.network.packet.server.DeSummonKeyblade;
import wehavecookies56.kk.network.packet.server.DriveFormPacket;
import wehavecookies56.kk.network.packet.server.OpenMenu;
import wehavecookies56.kk.network.packet.server.SummonKeyblade;
import wehavecookies56.kk.util.GuiHelper;
import wehavecookies56.kk.util.KeyboardHelper;

public class InputHandler {

	List<String> magicCommands;
	List<String> itemsCommands;
	List<String> driveCommands;
	
	private Keybinds getPressedKey () {
		for (Keybinds key : Keybinds.values())
			if (key.isPressed()) return key;
		return null;
	}

	public boolean antiFormCheck () {
		double random = Math.random();
		int ap = Minecraft.getMinecraft().thePlayer.getCapability(KingdomKeys.DRIVE_STATE, null).getAntiPoints();
		int prob = 0;
		if (ap > 0 && ap <= 4)
			prob = 10;
		else if (ap > 4 && ap <= 9)
			prob = 25;
		else if (ap >= 10) prob = 40;

		if (random * 100 < prob) {
			PacketDispatcher.sendToServer(new DriveFormPacket(Strings.Form_Anti));
			GuiCommandMenu.selected = GuiCommandMenu.ATTACK;
			GuiCommandMenu.submenu = GuiCommandMenu.SUB_MAIN;
			PacketDispatcher.sendToServer(new AntiPoints(4, "-"));
			//TODO Minecraft.getMinecraft().theWorld.playSound(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ, SoundHelper.Select, 1f, 1f, false);

			return true;
		} else
			return false;
	}

	public void commandUp () {
		Minecraft mc = Minecraft.getMinecraft();
		EntityPlayer player = mc.thePlayer;
		IPlayerStats STATS = player.getCapability(KingdomKeys.PLAYER_STATS, null);
		IDriveState DS = player.getCapability(KingdomKeys.DRIVE_STATE, null);
		this.magicCommands = new ArrayList<String>();
		this.itemsCommands = new ArrayList<String>();
		this.driveCommands = new ArrayList<String>();
		this.magicCommands.clear();
		for (int i = 0; i < Minecraft.getMinecraft().thePlayer.getCapability(KingdomKeys.MAGIC_STATE, null).getInventorySpells().getSizeInventory(); i++)
			if (Minecraft.getMinecraft().thePlayer.getCapability(KingdomKeys.MAGIC_STATE, null).getInventorySpells().getStackInSlot(i) != null) this.magicCommands.add(((ItemSpellOrb) Minecraft.getMinecraft().thePlayer.getCapability(KingdomKeys.MAGIC_STATE, null).getInventorySpells().getStackInSlot(i).getItem()).getMagicName());
		this.itemsCommands.clear();
		for (int i = 0; i < Minecraft.getMinecraft().thePlayer.getCapability(KingdomKeys.PLAYER_STATS, null).getInventoryPotionsMenu().getSizeInventory(); i++)
			if (Minecraft.getMinecraft().thePlayer.getCapability(KingdomKeys.PLAYER_STATS, null).getInventoryPotionsMenu().getStackInSlot(i) != null) this.itemsCommands.add(((ItemKKPotion) Minecraft.getMinecraft().thePlayer.getCapability(KingdomKeys.PLAYER_STATS, null).getInventoryPotionsMenu().getStackInSlot(i).getItem()).getItemName());
		this.driveCommands.clear();
		for (int i = 0; i < Minecraft.getMinecraft().thePlayer.getCapability(KingdomKeys.DRIVE_STATE, null).getInventoryDriveForms().getSizeInventory(); i++)
			if (Minecraft.getMinecraft().thePlayer.getCapability(KingdomKeys.DRIVE_STATE, null).getInventoryDriveForms().getStackInSlot(i) != null) this.driveCommands.add(((ItemDriveForm) Minecraft.getMinecraft().thePlayer.getCapability(KingdomKeys.DRIVE_STATE, null).getInventoryDriveForms().getStackInSlot(i).getItem()).getDriveFormName());	
		// Mainmenu
		if (GuiCommandMenu.submenu == GuiCommandMenu.SUB_MAIN) {
			if (GuiCommandMenu.selected == GuiCommandMenu.ATTACK)
				GuiCommandMenu.selected = GuiCommandMenu.DRIVE;
			else
				GuiCommandMenu.selected++;
		}
		// InsideMagic
		else if (GuiCommandMenu.submenu == GuiCommandMenu.SUB_MAGIC) {
			if (GuiCommandMenu.magicselected > 0) {
				GuiCommandMenu.magicselected--;
				GuiCommandMenu.submenu = GuiCommandMenu.SUB_MAGIC;
			} else if (GuiCommandMenu.magicselected <= 1) GuiCommandMenu.magicselected = this.magicCommands.size() - 1;
		}
		// InsideItems
		else if (GuiCommandMenu.submenu == GuiCommandMenu.SUB_ITEMS) {
			if (GuiCommandMenu.potionselected > 0) {
				GuiCommandMenu.potionselected--;
				GuiCommandMenu.submenu = GuiCommandMenu.SUB_ITEMS;
			} else if (GuiCommandMenu.potionselected <= 1) GuiCommandMenu.potionselected = this.itemsCommands.size() - 1;
		}
		// InsideDrive
		else if (GuiCommandMenu.submenu == GuiCommandMenu.SUB_DRIVE) if (GuiCommandMenu.driveselected > 0) {
			GuiCommandMenu.driveselected--;
			GuiCommandMenu.submenu = GuiCommandMenu.SUB_DRIVE;
		} else if (GuiCommandMenu.driveselected <= 1) GuiCommandMenu.driveselected = this.driveCommands.size() - 1;
	}

	public void commandDown () {
		Minecraft mc = Minecraft.getMinecraft();
		EntityPlayer player = mc.thePlayer;
		IPlayerStats STATS = player.getCapability(KingdomKeys.PLAYER_STATS, null);
		IDriveState DS = player.getCapability(KingdomKeys.DRIVE_STATE, null);
		this.magicCommands = new ArrayList<String>();
		this.itemsCommands = new ArrayList<String>();
		this.driveCommands = new ArrayList<String>();
		
		this.magicCommands.clear();
		for (int i = 0; i < Minecraft.getMinecraft().thePlayer.getCapability(KingdomKeys.MAGIC_STATE, null).getInventorySpells().getSizeInventory(); i++)
			if (Minecraft.getMinecraft().thePlayer.getCapability(KingdomKeys.MAGIC_STATE, null).getInventorySpells().getStackInSlot(i) != null) this.magicCommands.add(((ItemSpellOrb) Minecraft.getMinecraft().thePlayer.getCapability(KingdomKeys.MAGIC_STATE, null).getInventorySpells().getStackInSlot(i).getItem()).getMagicName());
		this.itemsCommands.clear();
		for (int i = 0; i < Minecraft.getMinecraft().thePlayer.getCapability(KingdomKeys.PLAYER_STATS, null).getInventoryPotionsMenu().getSizeInventory(); i++)
			if (Minecraft.getMinecraft().thePlayer.getCapability(KingdomKeys.PLAYER_STATS, null).getInventoryPotionsMenu().getStackInSlot(i) != null) this.itemsCommands.add(((ItemKKPotion) Minecraft.getMinecraft().thePlayer.getCapability(KingdomKeys.PLAYER_STATS, null).getInventoryPotionsMenu().getStackInSlot(i).getItem()).getItemName());
		this.driveCommands.clear();
		for (int i = 0; i < Minecraft.getMinecraft().thePlayer.getCapability(KingdomKeys.DRIVE_STATE, null).getInventoryDriveForms().getSizeInventory(); i++)
			if (Minecraft.getMinecraft().thePlayer.getCapability(KingdomKeys.DRIVE_STATE, null).getInventoryDriveForms().getStackInSlot(i) != null) this.driveCommands.add(((ItemDriveForm) Minecraft.getMinecraft().thePlayer.getCapability(KingdomKeys.DRIVE_STATE, null).getInventoryDriveForms().getStackInSlot(i).getItem()).getDriveFormName());	
		// Mainmenu
		if (GuiCommandMenu.submenu == GuiCommandMenu.SUB_MAIN) {
			if (GuiCommandMenu.selected == GuiCommandMenu.DRIVE)
				GuiCommandMenu.selected = GuiCommandMenu.ATTACK;
			else
				GuiCommandMenu.selected--;
		}
		// InsideMagic
		else if (GuiCommandMenu.submenu == GuiCommandMenu.SUB_MAGIC) {
			if (GuiCommandMenu.magicselected < this.magicCommands.size() - 1) {
				GuiCommandMenu.magicselected++;
				GuiCommandMenu.submenu = GuiCommandMenu.SUB_MAGIC;
			} else if (GuiCommandMenu.magicselected >= this.magicCommands.size() - 1) GuiCommandMenu.magicselected = 0;
		}
		// InsideItems
		else if (GuiCommandMenu.submenu == GuiCommandMenu.SUB_ITEMS) {
			if (GuiCommandMenu.potionselected < this.itemsCommands.size() - 1) {
				GuiCommandMenu.potionselected++;
				GuiCommandMenu.submenu = GuiCommandMenu.SUB_ITEMS;
			} else {
				if (GuiCommandMenu.potionselected >= this.itemsCommands.size() - 1) GuiCommandMenu.potionselected = 0;
			}
		}
		// InsideDrive
		else if (GuiCommandMenu.submenu == GuiCommandMenu.SUB_DRIVE) {
			if (GuiCommandMenu.driveselected < this.driveCommands.size() - 1) {
				GuiCommandMenu.driveselected++;
				GuiCommandMenu.submenu = GuiCommandMenu.SUB_DRIVE;
			} else {
				if (GuiCommandMenu.driveselected >= this.driveCommands.size() - 1) GuiCommandMenu.driveselected = 0;
			}
		}
	}

	public void commandEnter () {
		Minecraft mc = Minecraft.getMinecraft();
		EntityPlayer player = mc.thePlayer;
		World world = mc.theWorld;
		IPlayerStats STATS = player.getCapability(KingdomKeys.PLAYER_STATS, null);
		IDriveState DS = player.getCapability(KingdomKeys.DRIVE_STATE, null);
		this.magicCommands = new ArrayList<String>();
		this.magicCommands.clear();
		this.itemsCommands = new ArrayList<String>();
		this.itemsCommands.clear();
		this.driveCommands = new ArrayList<String>();
		this.driveCommands.clear();
		for (int i = 0; i < Minecraft.getMinecraft().thePlayer.getCapability(KingdomKeys.MAGIC_STATE, null).getInventorySpells().getSizeInventory(); i++)
			if (Minecraft.getMinecraft().thePlayer.getCapability(KingdomKeys.MAGIC_STATE, null).getInventorySpells().getStackInSlot(i) != null) this.magicCommands.add(((ItemSpellOrb) Minecraft.getMinecraft().thePlayer.getCapability(KingdomKeys.MAGIC_STATE, null).getInventorySpells().getStackInSlot(i).getItem()).getMagicName());
		for (int i = 0; i < Minecraft.getMinecraft().thePlayer.getCapability(KingdomKeys.PLAYER_STATS, null).getInventoryPotionsMenu().getSizeInventory(); i++)
			if (Minecraft.getMinecraft().thePlayer.getCapability(KingdomKeys.PLAYER_STATS, null).getInventoryPotionsMenu().getStackInSlot(i) != null) this.itemsCommands.add(((ItemKKPotion) Minecraft.getMinecraft().thePlayer.getCapability(KingdomKeys.PLAYER_STATS, null).getInventoryPotionsMenu().getStackInSlot(i).getItem()).getItemName());
		for (int i = 0; i < Minecraft.getMinecraft().thePlayer.getCapability(KingdomKeys.DRIVE_STATE, null).getInventoryDriveForms().getSizeInventory(); i++)
			if (Minecraft.getMinecraft().thePlayer.getCapability(KingdomKeys.DRIVE_STATE, null).getInventoryDriveForms().getStackInSlot(i) != null) this.driveCommands.add(((ItemDriveForm) Minecraft.getMinecraft().thePlayer.getCapability(KingdomKeys.DRIVE_STATE, null).getInventoryDriveForms().getStackInSlot(i).getItem()).getDriveFormName());	
		
		switch (GuiCommandMenu.selected) {
			case GuiCommandMenu.MAGIC:
				if (GuiCommandMenu.submenu == GuiCommandMenu.SUB_MAIN) {
					if (!STATS.getRecharge() && (!this.magicCommands.isEmpty() && !DS.getActiveDriveName().equals(Strings.Form_Valor))) {
						GuiCommandMenu.magicselected = 0;
						GuiCommandMenu.submenu = GuiCommandMenu.SUB_MAGIC;
						//TODO world.playSound(player.posX, player.posY, player.posZ, SoundHelper.Select, 1f, 1f, false);
						return;
					} else {
						GuiCommandMenu.selected = GuiCommandMenu.ATTACK;
						//TODO world.playSound(player.posX, player.posY, player.posZ, SoundHelper.Error, 2f, 1f, false);
					}
				}
				break;

			case GuiCommandMenu.ITEMS:
				if (GuiCommandMenu.submenu == GuiCommandMenu.SUB_MAIN) {
					if (!this.itemsCommands.isEmpty()) {
						GuiCommandMenu.submenu = GuiCommandMenu.SUB_ITEMS;
						GuiCommandMenu.potionselected = 0;
						//TODO world.playSound(player.posX, player.posY, player.posZ, SoundHelper.Select, 1f, 1f, false);
					} else {
						GuiCommandMenu.selected = GuiCommandMenu.ATTACK;
						//TODO world.playSound(player.posX, player.posY, player.posZ, SoundHelper.Error, 2f, 1f, false);
					}
					return;
				}
				break;

			case GuiCommandMenu.DRIVE:
				if (GuiCommandMenu.submenu == GuiCommandMenu.SUB_MAIN) {
					if (DS.getInDrive()) {// Revert
						if (DS.getActiveDriveName().equals(Strings.Form_Anti) && player.getCapability(KingdomKeys.CHEAT_MODE, null).getCheatMode() == false) {
							GuiCommandMenu.selected = GuiCommandMenu.ATTACK;
							//TODO world.playSound(player.posX, player.posY, player.posZ, SoundHelper.Error, 2f, 1f, false);
						} else {
							PacketDispatcher.sendToServer(new DriveFormPacket(true));
							GuiCommandMenu.submenu = GuiCommandMenu.SUB_MAIN;
							GuiCommandMenu.selected = GuiCommandMenu.ATTACK;
							//TODO world.playSound(player.posX, player.posY, player.posZ, SoundHelper.Select, 1f, 1f, false);
						}
					} else if (this.driveCommands.isEmpty() || STATS.getDP() <= 0) {
						//TODO world.playSound(player.posX, player.posY, player.posZ, SoundHelper.Error, 1f, 1f, false);
						GuiCommandMenu.selected = GuiCommandMenu.ATTACK;
					} else {
						GuiCommandMenu.driveselected = 0;
						GuiCommandMenu.submenu = GuiCommandMenu.SUB_DRIVE;
						//TODO world.playSound(player.posX, player.posY, player.posZ, SoundHelper.Select, 1f, 1f, false);
						return;
					}
				}
				break;
		}
		if (GuiCommandMenu.selected == GuiCommandMenu.MAGIC && GuiCommandMenu.submenu == GuiCommandMenu.SUB_MAGIC) {
			if (this.magicCommands.isEmpty()) 
			{} 
			else if (!STATS.getRecharge() || Constants.getCost((String) this.magicCommands.get(GuiCommandMenu.magicselected)) == -1 && STATS.getMP() > 0) {
				Magic.getMagic(player, world, (String) this.magicCommands.get(GuiCommandMenu.magicselected));
				GuiCommandMenu.selected = GuiCommandMenu.ATTACK;
				GuiCommandMenu.submenu = GuiCommandMenu.SUB_MAIN;
				//TODO world.playSound(player.posX, player.posY, player.posZ, SoundHelper.Select, 1f, 1f, false);
			}
		}

		if (GuiCommandMenu.selected == GuiCommandMenu.ITEMS && GuiCommandMenu.submenu == GuiCommandMenu.SUB_ITEMS) {
			if (this.itemsCommands.isEmpty()) {} else if (!this.itemsCommands.isEmpty()) {
				ItemKKPotion.getItem(player, world, (String) this.itemsCommands.get(GuiCommandMenu.potionselected), GuiCommandMenu.potionselected);

				GuiCommandMenu.selected = GuiCommandMenu.ATTACK;
				GuiCommandMenu.submenu = GuiCommandMenu.SUB_MAIN;
				//TODO world.playSound(player.posX, player.posY, player.posZ, SoundHelper.Select, 1f, 1f, false);
			}
		}

		if (GuiCommandMenu.selected == GuiCommandMenu.DRIVE && GuiCommandMenu.submenu == GuiCommandMenu.SUB_DRIVE) {
			if (this.driveCommands.isEmpty()) {} else if ((STATS.getDP() >= Constants.getCost((String) this.driveCommands.get(GuiCommandMenu.driveselected)))) {

				if(!antiFormCheck()){
					ModDriveForms.getDriveForm(player, world, (String) this.driveCommands.get(GuiCommandMenu.driveselected));
				}
				GuiCommandMenu.selected = GuiCommandMenu.ATTACK;
				GuiCommandMenu.submenu = GuiCommandMenu.SUB_MAIN;
				//TODO world.playSound(player.posX, player.posY, player.posZ, SoundHelper.Select, 1f, 1f, false);
			}
		}
	}

	public void commandBack () {
		if (GuiCommandMenu.submenu == GuiCommandMenu.SUB_MAIN)
			GuiCommandMenu.submenu = GuiCommandMenu.SUB_MAIN;
		else if (GuiCommandMenu.submenu == GuiCommandMenu.SUB_MAGIC) {
			GuiCommandMenu.submenu = GuiCommandMenu.SUB_MAIN;
			//TODO Minecraft.getMinecraft().theWorld.playSound(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ, SoundHelper.Cancel, 1f, 1f, false);
		} else if (GuiCommandMenu.submenu == GuiCommandMenu.SUB_ITEMS) {
			GuiCommandMenu.submenu = GuiCommandMenu.SUB_MAIN;
			//TODO Minecraft.getMinecraft().theWorld.playSound(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ, SoundHelper.Cancel, 1f, 1f, false);
		} else if (GuiCommandMenu.submenu == GuiCommandMenu.SUB_DRIVE) {
			GuiCommandMenu.submenu = GuiCommandMenu.SUB_MAIN;
			//TODO Minecraft.getMinecraft().theWorld.playSound(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ, SoundHelper.Cancel, 1f, 1f, false);
		}
		GuiCommandMenu.magicselected = 0;
		GuiCommandMenu.driveselected = 0;
	}

	@SubscribeEvent
	public void handleKeyInputEvent (InputEvent.KeyInputEvent event) {
		Minecraft mc = Minecraft.getMinecraft();
		EntityPlayer player = mc.thePlayer;
		World world = mc.theWorld;
		ISummonKeyblade SUMMON = player.getCapability(KingdomKeys.SUMMON_KEYBLADE, null);

		Keybinds key = getPressedKey();
		if (key != null) switch (key) {
			case OPENMENU:
				GuiHelper.openMenu();
				PacketDispatcher.sendToServer(new OpenMenu());
				break;
			case SCROLL_UP:
				commandUp();
				//TODO Minecraft.getMinecraft().theWorld.playSound(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ, SoundHelper.Move, 1f, 1f, false);
				break;

			case SCROLL_DOWN:
				commandDown();
				//TODO Minecraft.getMinecraft().theWorld.playSound(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ, SoundHelper.Move, 1f, 1f, false);
				break;

			case ENTER:
				commandEnter();
				break;

			case BACK:
				commandBack();
				break;
			case SUMMON_KEYBLADE:
				if (mc.thePlayer.getCapability(KingdomKeys.PLAYER_STATS, null).getInventoryKeychain().getStackInSlot(0) == null) {
					//TODO Minecraft.getMinecraft().theWorld.playSound(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ, SoundHelper.Error, 2f, 1f, false);
					break;
				}
				if (SUMMON.getKeybladeSummoned() == false && player.inventory.getCurrentItem() == null && mc.thePlayer.getCapability(KingdomKeys.PLAYER_STATS, null).getInventoryKeychain().getStackInSlot(0).getItem() instanceof ItemKeychain) {
					PacketDispatcher.sendToServer(new SummonKeyblade(((ItemKeychain) mc.thePlayer.getCapability(KingdomKeys.PLAYER_STATS, null).getInventoryKeychain().getStackInSlot(0).getItem()).getKeyblade()));
				} else if (player.inventory.getCurrentItem() != null && player.inventory.getCurrentItem().getItem() instanceof ItemKeyblade && SUMMON.getKeybladeSummoned() == true) {
					PacketDispatcher.sendToServer(new DeSummonKeyblade(player.inventory.getCurrentItem()));
				} else
					break;
				break;
			case SCROLL_ACTIVATOR:
				break;
			default:
				break;
		}
	}

	@SubscribeEvent
	public void OnMouseWheelScroll (MouseEvent event) {
		Minecraft mc = Minecraft.getMinecraft();
		EntityPlayer player = mc.thePlayer;
		World world = mc.theWorld;
		if (!mc.inGameHasFocus && !KeyboardHelper.isScrollActivatorDown()) {
			event.setCanceled(false);
			return;
		}

		if (event.button == Constants.LEFT_MOUSE && KeyboardHelper.isScrollActivatorDown() && event.buttonstate) {
			commandEnter();
			event.setCanceled(true);
		}

		if (event.button == Constants.RIGHT_MOUSE && KeyboardHelper.isScrollActivatorDown() && event.buttonstate) {
			commandBack();
			event.setCanceled(true);
		}

		if (event.dwheel <= Constants.WHEEL_DOWN && KeyboardHelper.isScrollActivatorDown() && event.dwheel != 0) {
			commandDown();
			event.setCanceled(true);
			//TODO Minecraft.getMinecraft().theWorld.playSound(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ, SoundHelper.Move, 1f, 1f, false);
		}
		if (event.dwheel >= Constants.WHEEL_UP && KeyboardHelper.isScrollActivatorDown() && event.dwheel != 0) {
			commandUp();
			event.setCanceled(true);
			//TODO Minecraft.getMinecraft().theWorld.playSound(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ, SoundHelper.Move, 1f, 1f, false);
		}

	}
}
