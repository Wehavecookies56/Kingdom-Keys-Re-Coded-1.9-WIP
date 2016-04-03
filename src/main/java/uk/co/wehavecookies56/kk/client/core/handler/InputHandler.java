package uk.co.wehavecookies56.kk.client.core.handler;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;
import uk.co.wehavecookies56.kk.common.capability.DriveStateCapability.IDriveState;
import uk.co.wehavecookies56.kk.common.capability.ModCapabilities;
import uk.co.wehavecookies56.kk.client.sound.ModSounds;
import uk.co.wehavecookies56.kk.client.gui.GuiCommandMenu;
import uk.co.wehavecookies56.kk.common.driveform.ModDriveForms;
import uk.co.wehavecookies56.kk.common.item.ItemDriveForm;
import uk.co.wehavecookies56.kk.common.item.ItemKKPotion;
import uk.co.wehavecookies56.kk.common.item.ItemKeyblade;
import uk.co.wehavecookies56.kk.common.item.ItemKeychain;
import uk.co.wehavecookies56.kk.common.item.ItemSpellOrb;
import uk.co.wehavecookies56.kk.common.lib.Constants;
import uk.co.wehavecookies56.kk.common.lib.Strings;
import uk.co.wehavecookies56.kk.common.magic.Magic;
import uk.co.wehavecookies56.kk.common.network.packet.PacketDispatcher;
import uk.co.wehavecookies56.kk.common.network.packet.server.AntiPoints;
import uk.co.wehavecookies56.kk.common.network.packet.server.DeSummonKeyblade;
import uk.co.wehavecookies56.kk.common.network.packet.server.DriveFormPacket;
import uk.co.wehavecookies56.kk.common.network.packet.server.OpenMenu;
import uk.co.wehavecookies56.kk.common.network.packet.server.SummonKeyblade;
import uk.co.wehavecookies56.kk.client.core.helper.GuiHelper;
import uk.co.wehavecookies56.kk.client.core.helper.KeyboardHelper;
import uk.co.wehavecookies56.kk.common.capability.PlayerStatsCapability;
import uk.co.wehavecookies56.kk.common.capability.SummonKeybladeCapability;

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
		Minecraft mc = Minecraft.getMinecraft();
		EntityPlayer player = mc.thePlayer;
		World world = mc.theWorld;
		double random = Math.random();
		int ap = player.getCapability(ModCapabilities.DRIVE_STATE, null).getAntiPoints();
		int prob = 0;
		if (ap > 0 && ap <= 4)
			prob = 10;
		else if (ap > 4 && ap <= 9)
			prob = 25;
		else if (ap >= 10)
			prob = 40;

		if (random * 100 < prob) {
			PacketDispatcher.sendToServer(new DriveFormPacket(Strings.Form_Anti));
			GuiCommandMenu.selected = GuiCommandMenu.ATTACK;
			GuiCommandMenu.submenu = GuiCommandMenu.SUB_MAIN;
			PacketDispatcher.sendToServer(new AntiPoints(4, "-"));
			world.playSound(player, player.getPosition(), ModSounds.select, SoundCategory.MASTER, 1.0f, 1.0f);
			return true;
		} else
			return false;
	}

	public void commandUp () {
		Minecraft mc = Minecraft.getMinecraft();
		EntityPlayer player = mc.thePlayer;
		PlayerStatsCapability.IPlayerStats STATS = player.getCapability(ModCapabilities.PLAYER_STATS, null);
		IDriveState DS = player.getCapability(ModCapabilities.DRIVE_STATE, null);
		this.magicCommands = new ArrayList<String>();
		this.itemsCommands = new ArrayList<String>();
		this.driveCommands = new ArrayList<String>();
		this.magicCommands.clear();
		for (int i = 0; i < Minecraft.getMinecraft().thePlayer.getCapability(ModCapabilities.MAGIC_STATE, null).getInventorySpells().getSizeInventory(); i++)
			if (Minecraft.getMinecraft().thePlayer.getCapability(ModCapabilities.MAGIC_STATE, null).getInventorySpells().getStackInSlot(i) != null) this.magicCommands.add(((ItemSpellOrb) Minecraft.getMinecraft().thePlayer.getCapability(ModCapabilities.MAGIC_STATE, null).getInventorySpells().getStackInSlot(i).getItem()).getMagicName());
		this.itemsCommands.clear();
		for (int i = 0; i < Minecraft.getMinecraft().thePlayer.getCapability(ModCapabilities.PLAYER_STATS, null).getInventoryPotionsMenu().getSizeInventory(); i++)
			if (Minecraft.getMinecraft().thePlayer.getCapability(ModCapabilities.PLAYER_STATS, null).getInventoryPotionsMenu().getStackInSlot(i) != null) this.itemsCommands.add(((ItemKKPotion) Minecraft.getMinecraft().thePlayer.getCapability(ModCapabilities.PLAYER_STATS, null).getInventoryPotionsMenu().getStackInSlot(i).getItem()).getItemName());
		this.driveCommands.clear();
		for (int i = 0; i < Minecraft.getMinecraft().thePlayer.getCapability(ModCapabilities.DRIVE_STATE, null).getInventoryDriveForms().getSizeInventory(); i++)
			if (Minecraft.getMinecraft().thePlayer.getCapability(ModCapabilities.DRIVE_STATE, null).getInventoryDriveForms().getStackInSlot(i) != null) this.driveCommands.add(((ItemDriveForm) Minecraft.getMinecraft().thePlayer.getCapability(ModCapabilities.DRIVE_STATE, null).getInventoryDriveForms().getStackInSlot(i).getItem()).getDriveFormName());
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
		PlayerStatsCapability.IPlayerStats STATS = player.getCapability(ModCapabilities.PLAYER_STATS, null);
		IDriveState DS = player.getCapability(ModCapabilities.DRIVE_STATE, null);
		this.magicCommands = new ArrayList<String>();
		this.itemsCommands = new ArrayList<String>();
		this.driveCommands = new ArrayList<String>();
		
		this.magicCommands.clear();
		for (int i = 0; i < Minecraft.getMinecraft().thePlayer.getCapability(ModCapabilities.MAGIC_STATE, null).getInventorySpells().getSizeInventory(); i++)
			if (Minecraft.getMinecraft().thePlayer.getCapability(ModCapabilities.MAGIC_STATE, null).getInventorySpells().getStackInSlot(i) != null) this.magicCommands.add(((ItemSpellOrb) Minecraft.getMinecraft().thePlayer.getCapability(ModCapabilities.MAGIC_STATE, null).getInventorySpells().getStackInSlot(i).getItem()).getMagicName());
		this.itemsCommands.clear();
		for (int i = 0; i < Minecraft.getMinecraft().thePlayer.getCapability(ModCapabilities.PLAYER_STATS, null).getInventoryPotionsMenu().getSizeInventory(); i++)
			if (Minecraft.getMinecraft().thePlayer.getCapability(ModCapabilities.PLAYER_STATS, null).getInventoryPotionsMenu().getStackInSlot(i) != null) this.itemsCommands.add(((ItemKKPotion) Minecraft.getMinecraft().thePlayer.getCapability(ModCapabilities.PLAYER_STATS, null).getInventoryPotionsMenu().getStackInSlot(i).getItem()).getItemName());
		this.driveCommands.clear();
		for (int i = 0; i < Minecraft.getMinecraft().thePlayer.getCapability(ModCapabilities.DRIVE_STATE, null).getInventoryDriveForms().getSizeInventory(); i++)
			if (Minecraft.getMinecraft().thePlayer.getCapability(ModCapabilities.DRIVE_STATE, null).getInventoryDriveForms().getStackInSlot(i) != null) this.driveCommands.add(((ItemDriveForm) Minecraft.getMinecraft().thePlayer.getCapability(ModCapabilities.DRIVE_STATE, null).getInventoryDriveForms().getStackInSlot(i).getItem()).getDriveFormName());
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
		PlayerStatsCapability.IPlayerStats STATS = player.getCapability(ModCapabilities.PLAYER_STATS, null);
		IDriveState DS = player.getCapability(ModCapabilities.DRIVE_STATE, null);
		this.magicCommands = new ArrayList<String>();
		this.magicCommands.clear();
		this.itemsCommands = new ArrayList<String>();
		this.itemsCommands.clear();
		this.driveCommands = new ArrayList<String>();
		this.driveCommands.clear();
		for (int i = 0; i < Minecraft.getMinecraft().thePlayer.getCapability(ModCapabilities.MAGIC_STATE, null).getInventorySpells().getSizeInventory(); i++)
			if (Minecraft.getMinecraft().thePlayer.getCapability(ModCapabilities.MAGIC_STATE, null).getInventorySpells().getStackInSlot(i) != null) this.magicCommands.add(((ItemSpellOrb) Minecraft.getMinecraft().thePlayer.getCapability(ModCapabilities.MAGIC_STATE, null).getInventorySpells().getStackInSlot(i).getItem()).getMagicName());
		for (int i = 0; i < Minecraft.getMinecraft().thePlayer.getCapability(ModCapabilities.PLAYER_STATS, null).getInventoryPotionsMenu().getSizeInventory(); i++)
			if (Minecraft.getMinecraft().thePlayer.getCapability(ModCapabilities.PLAYER_STATS, null).getInventoryPotionsMenu().getStackInSlot(i) != null) this.itemsCommands.add(((ItemKKPotion) Minecraft.getMinecraft().thePlayer.getCapability(ModCapabilities.PLAYER_STATS, null).getInventoryPotionsMenu().getStackInSlot(i).getItem()).getItemName());
		for (int i = 0; i < Minecraft.getMinecraft().thePlayer.getCapability(ModCapabilities.DRIVE_STATE, null).getInventoryDriveForms().getSizeInventory(); i++)
			if (Minecraft.getMinecraft().thePlayer.getCapability(ModCapabilities.DRIVE_STATE, null).getInventoryDriveForms().getStackInSlot(i) != null) this.driveCommands.add(((ItemDriveForm) Minecraft.getMinecraft().thePlayer.getCapability(ModCapabilities.DRIVE_STATE, null).getInventoryDriveForms().getStackInSlot(i).getItem()).getDriveFormName());
		
		switch (GuiCommandMenu.selected) {
			case GuiCommandMenu.MAGIC:
				if (GuiCommandMenu.submenu == GuiCommandMenu.SUB_MAIN) {
					if (!STATS.getRecharge() && (!this.magicCommands.isEmpty() && !DS.getActiveDriveName().equals(Strings.Form_Valor))) {
						GuiCommandMenu.magicselected = 0;
						GuiCommandMenu.submenu = GuiCommandMenu.SUB_MAGIC;
						world.playSound(player, player.getPosition(), ModSounds.select, SoundCategory.MASTER, 1.0f, 1.0f);
						return;
					} else {
						GuiCommandMenu.selected = GuiCommandMenu.ATTACK;
						world.playSound(player, player.getPosition(), ModSounds.error, SoundCategory.MASTER, 1.0f, 1.0f);
					}
				}
				break;

			case GuiCommandMenu.ITEMS:
				if (GuiCommandMenu.submenu == GuiCommandMenu.SUB_MAIN) {
					if (!this.itemsCommands.isEmpty()) {
						GuiCommandMenu.submenu = GuiCommandMenu.SUB_ITEMS;
						GuiCommandMenu.potionselected = 0;
						world.playSound(player, player.getPosition(), ModSounds.select, SoundCategory.MASTER, 1.0f, 1.0f);
					} else {
						GuiCommandMenu.selected = GuiCommandMenu.ATTACK;
						world.playSound(player, player.getPosition(), ModSounds.error, SoundCategory.MASTER, 1.0f, 1.0f);
					}
					return;
				}
				break;

			case GuiCommandMenu.DRIVE:
				if (GuiCommandMenu.submenu == GuiCommandMenu.SUB_MAIN) {
					if (DS.getInDrive()) {// Revert
						System.out.println(player.getCapability(ModCapabilities.CHEAT_MODE, null).getCheatMode());
						if (DS.getActiveDriveName().equals(Strings.Form_Anti) && !player.getCapability(ModCapabilities.CHEAT_MODE, null).getCheatMode()) {
							GuiCommandMenu.selected = GuiCommandMenu.ATTACK;
							world.playSound(player, player.getPosition(), ModSounds.error, SoundCategory.MASTER, 1.0f, 1.0f);
						} else {
							PacketDispatcher.sendToServer(new DriveFormPacket(true));
							GuiCommandMenu.submenu = GuiCommandMenu.SUB_MAIN;
							GuiCommandMenu.selected = GuiCommandMenu.ATTACK;
							world.playSound(player, player.getPosition(), ModSounds.select, SoundCategory.MASTER, 1.0f, 1.0f);
						}
					} else if (this.driveCommands.isEmpty() || STATS.getDP() <= 0) {
						world.playSound(player, player.getPosition(), ModSounds.error, SoundCategory.MASTER, 1.0f, 1.0f);
						GuiCommandMenu.selected = GuiCommandMenu.ATTACK;
					} else {
						GuiCommandMenu.driveselected = 0;
						GuiCommandMenu.submenu = GuiCommandMenu.SUB_DRIVE;
						world.playSound(player, player.getPosition(), ModSounds.select, SoundCategory.MASTER, 1.0f, 1.0f);
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
				world.playSound(player, player.getPosition(), ModSounds.select, SoundCategory.MASTER, 1.0f, 1.0f);
			}
		}

		if (GuiCommandMenu.selected == GuiCommandMenu.ITEMS && GuiCommandMenu.submenu == GuiCommandMenu.SUB_ITEMS) {
			if (this.itemsCommands.isEmpty()) {} else if (!this.itemsCommands.isEmpty()) {
				ItemKKPotion.getItem(player, world, (String) this.itemsCommands.get(GuiCommandMenu.potionselected), GuiCommandMenu.potionselected);

				GuiCommandMenu.selected = GuiCommandMenu.ATTACK;
				GuiCommandMenu.submenu = GuiCommandMenu.SUB_MAIN;
				world.playSound(player, player.getPosition(), ModSounds.select, SoundCategory.MASTER, 1.0f, 1.0f);
			}
		}

		if (GuiCommandMenu.selected == GuiCommandMenu.DRIVE && GuiCommandMenu.submenu == GuiCommandMenu.SUB_DRIVE) {
			if (this.driveCommands.isEmpty()) {} else if ((STATS.getDP() >= Constants.getCost((String) this.driveCommands.get(GuiCommandMenu.driveselected)))) {

				if(!antiFormCheck()){
					ModDriveForms.getDriveForm(player, world, (String) this.driveCommands.get(GuiCommandMenu.driveselected));
				}
				GuiCommandMenu.selected = GuiCommandMenu.ATTACK;
				GuiCommandMenu.submenu = GuiCommandMenu.SUB_MAIN;
				world.playSound(player, player.getPosition(), ModSounds.select, SoundCategory.MASTER, 1.0f, 1.0f);
			}
		}
	}

	public void commandBack () {
		Minecraft mc = Minecraft.getMinecraft();
		EntityPlayer player = mc.thePlayer;
		World world = mc.theWorld;
		if (GuiCommandMenu.submenu == GuiCommandMenu.SUB_MAIN)
			GuiCommandMenu.submenu = GuiCommandMenu.SUB_MAIN;
		else if (GuiCommandMenu.submenu == GuiCommandMenu.SUB_MAGIC) {
			GuiCommandMenu.submenu = GuiCommandMenu.SUB_MAIN;
			world.playSound(player, player.getPosition(), ModSounds.cancel, SoundCategory.MASTER, 1.0f, 1.0f);
		} else if (GuiCommandMenu.submenu == GuiCommandMenu.SUB_ITEMS) {
			GuiCommandMenu.submenu = GuiCommandMenu.SUB_MAIN;
			world.playSound(player, player.getPosition(), ModSounds.cancel, SoundCategory.MASTER, 1.0f, 1.0f);
		} else if (GuiCommandMenu.submenu == GuiCommandMenu.SUB_DRIVE) {
			GuiCommandMenu.submenu = GuiCommandMenu.SUB_MAIN;
			world.playSound(player, player.getPosition(), ModSounds.cancel, SoundCategory.MASTER, 1.0f, 1.0f);
		}
		GuiCommandMenu.magicselected = 0;
		GuiCommandMenu.driveselected = 0;
	}

	@SubscribeEvent
	public void handleKeyInputEvent (InputEvent.KeyInputEvent event) {
		Minecraft mc = Minecraft.getMinecraft();
		EntityPlayer player = mc.thePlayer;
		World world = mc.theWorld;
		SummonKeybladeCapability.ISummonKeyblade SUMMON = player.getCapability(ModCapabilities.SUMMON_KEYBLADE, null);

		Keybinds key = getPressedKey();
		if (key != null) switch (key) {
			case OPENMENU:
				GuiHelper.openMenu();
				PacketDispatcher.sendToServer(new OpenMenu());
				break;
			case SCROLL_UP:
				commandUp();
				world.playSound(player, player.getPosition(), ModSounds.move, SoundCategory.MASTER, 1.0f, 1.0f);
				break;

			case SCROLL_DOWN:
				commandDown();
				world.playSound(player, player.getPosition(), ModSounds.move, SoundCategory.MASTER, 1.0f, 1.0f);
				break;

			case ENTER:
				commandEnter();
				break;

			case BACK:
				commandBack();
				break;
			case SUMMON_KEYBLADE:
				if (mc.thePlayer.getCapability(ModCapabilities.SUMMON_KEYBLADE, null).getInventoryKeychain().getStackInSlot(0) == null) {
					world.playSound(player, player.getPosition(), ModSounds.error, SoundCategory.MASTER, 1.0f, 1.0f);
					System.out.println("Empty keychain inventory");
					break;
				}
				if (SUMMON.getIsKeybladeSummoned() == false && player.getHeldItem(EnumHand.MAIN_HAND) == null && mc.thePlayer.getCapability(ModCapabilities.SUMMON_KEYBLADE, null).getInventoryKeychain().getStackInSlot(0).getItem() instanceof ItemKeychain) {
					PacketDispatcher.sendToServer(new SummonKeyblade(((ItemKeychain) mc.thePlayer.getCapability(ModCapabilities.SUMMON_KEYBLADE, null).getInventoryKeychain().getStackInSlot(0).getItem()).getKeyblade()));
				} else if (player.getHeldItem(EnumHand.MAIN_HAND) != null && player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemKeyblade && SUMMON.getIsKeybladeSummoned() == true) {
					PacketDispatcher.sendToServer(new DeSummonKeyblade(player.inventory.getCurrentItem()));
				} else {
					break;
				}
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

		if (event.getButton() == Constants.LEFT_MOUSE && KeyboardHelper.isScrollActivatorDown() && event.isButtonstate()) {
			commandEnter();
			event.setCanceled(true);
		}

		if (event.getButton() == Constants.RIGHT_MOUSE && KeyboardHelper.isScrollActivatorDown() && event.isButtonstate()) {
			commandBack();
			event.setCanceled(true);
		}

		if (event.getDwheel() <= Constants.WHEEL_DOWN && KeyboardHelper.isScrollActivatorDown() && event.getDwheel() != 0) {
			commandDown();
			event.setCanceled(true);
			world.playSound(player, player.getPosition(), ModSounds.move, SoundCategory.MASTER, 1.0f, 1.0f);
		}
		if (event.getDwheel() >= Constants.WHEEL_UP && KeyboardHelper.isScrollActivatorDown() && event.getDwheel() != 0) {
			commandUp();
			event.setCanceled(true);
			world.playSound(player, player.getPosition(), ModSounds.move, SoundCategory.MASTER, 1.0f, 1.0f);
		}

	}

	public static enum Keybinds {

        OPENMENU ("key.kingdomkeys.openmenu", Keyboard.KEY_M), SCROLL_UP ("key.kingdomkeys.scrollup", Keyboard.KEY_UP), SCROLL_DOWN ("key.kingdomkeys.scrolldown", Keyboard.KEY_DOWN), ENTER ("key.kingdomkeys.enter", Keyboard.KEY_RIGHT), BACK ("key.kingdomkeys.back", Keyboard.KEY_LEFT), SCROLL_ACTIVATOR ("key.kingdomkeys.scrollactivator", Keyboard.KEY_LMENU), SUMMON_KEYBLADE ("key.kingdomkeys.summonkeyblade", Keyboard.KEY_G);

        private final KeyBinding keybinding;

        private Keybinds (String name, int defaultKey) {
            keybinding = new KeyBinding(name, defaultKey, "key.categories.kingdomkeys");
        }

        public KeyBinding getKeybind () {
            return keybinding;
        }

        public boolean isPressed () {
            return keybinding.isPressed();
        }

    }
}
