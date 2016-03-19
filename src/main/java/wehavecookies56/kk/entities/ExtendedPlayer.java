package wehavecookies56.kk.entities;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import wehavecookies56.kk.api.driveforms.DriveForm;
import wehavecookies56.kk.inventory.InventoryDriveForms;
import wehavecookies56.kk.inventory.InventoryKeychain;
import wehavecookies56.kk.inventory.InventoryPotionsMenu;
import wehavecookies56.kk.inventory.InventorySpells;
import wehavecookies56.kk.item.ItemDriveForm;
import wehavecookies56.kk.item.ItemKKPotion;
import wehavecookies56.kk.item.ItemSpellOrb;
import wehavecookies56.kk.lib.Strings;
import wehavecookies56.kk.network.CommonProxy;
import wehavecookies56.kk.network.packet.PacketDispatcher;
import wehavecookies56.kk.network.packet.client.SyncExtendedPlayer;

public class ExtendedPlayer implements IExtendedEntityProperties {

	public final static String EXT_PROP_NAME = "KKExtendedPlayer";

	private final EntityPlayer player;

	public final InventoryKeychain inventoryKeychain = new InventoryKeychain();
	public final InventoryPotionsMenu inventoryPotions = new InventoryPotionsMenu();
	public final InventorySpells inventorySpells = new InventorySpells();
	public final InventoryDriveForms inventoryDrive = new InventoryDriveForms();

	public static List<String> driveForms = new ArrayList<String>();
	public static List<String> spells = new ArrayList<String>();
	public static List<String> items = new ArrayList<String>();

	//public boolean cheatMode;
	
	public boolean isKH1Fire;

	public ExtendedPlayer (EntityPlayer player) {
		this.player = player;
		this.cheatMode = false;
		
		this.isKH1Fire = false;
	}

	@Override
	public void saveNBTData (NBTTagCompound compound) {
		NBTTagCompound properties = new NBTTagCompound();
		this.inventoryKeychain.writeToNBT(properties);
		this.inventoryPotions.writeToNBT(properties);
		this.inventorySpells.writeToNBT(properties);
		this.inventoryDrive.writeToNBT(properties);
		
		properties.setBoolean("CheatMode", this.cheatMode);
		
		properties.setBoolean("isKH1Fire", this.isKH1Fire);

		compound.setTag(EXT_PROP_NAME, properties);

	}

	@Override
	public void loadNBTData (NBTTagCompound compound) {
		NBTTagCompound properties = (NBTTagCompound) compound.getTag(EXT_PROP_NAME);
		this.inventoryKeychain.readFromNBT(properties);
		this.inventoryPotions.readFromNBT(properties);
		this.inventorySpells.readFromNBT(properties);
		this.inventoryDrive.readFromNBT(properties);

		ExtendedPlayer.spells.clear();
		for (int i = 0; i < this.inventorySpells.getSizeInventory(); i++) {
			if (this.inventorySpells.getStackInSlot(i) != null) {
				ExtendedPlayer.spells.add(((ItemSpellOrb) this.inventorySpells.getStackInSlot(i).getItem()).getMagicName());
			}
		}
		ExtendedPlayer.driveForms.clear();
		for (int i = 0; i < this.inventoryDrive.getSizeInventory(); i++)
			if (this.inventoryDrive.getStackInSlot(i) != null) ExtendedPlayer.driveForms.add(((ItemDriveForm) this.inventoryDrive.getStackInSlot(i).getItem()).getDriveFormName());
		ExtendedPlayer.items.clear();
		for (int i = 0; i < this.inventoryPotions.getSizeInventory(); i++)
			if (this.inventoryPotions.getStackInSlot(i) != null) ExtendedPlayer.items.add(((ItemKKPotion) this.inventoryPotions.getStackInSlot(i).getItem()).getItemName());
		
		this.cheatMode = properties.getBoolean("CheatMode");
		
		//player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.hp);
		
		this.isKH1Fire = properties.getBoolean("isKH1Fire");
	}

	@Override
	public void init (Entity entity, World world) {}

	public void setKH1Fire(boolean kh1)
	{
		this.isKH1Fire = kh1;
		sync();
	}
	
	public boolean getKH1Fire()
	{
		return this.isKH1Fire;
	}


	/*
	public void levelUp () {
		this.level++;
		sync();
		if (!player.worldObj.isRemote) {
			PacketDispatcher.sendTo(new ShowOverlayPacket("levelup"), (EntityPlayerMP) player);
			PacketDispatcher.sendTo(new SyncStatMessagesPacket("clr"), (EntityPlayerMP) player);
		}
	}

	public void levelUp (int level) {
		this.level = level;
		sync();
		if (!player.worldObj.isRemote) {
			PacketDispatcher.sendTo(new ShowOverlayPacket("levelup"), (EntityPlayerMP) player);
			PacketDispatcher.sendTo(new SyncStatMessagesPacket("clr"), (EntityPlayerMP) player);
		}
	}

	public void levelUp (int level, boolean command) {
		// STUFF FOR COMMAND
		this.level = level;
		sync();
		if (!player.worldObj.isRemote) {
			PacketDispatcher.sendTo(new ShowOverlayPacket("levelup"), (EntityPlayerMP) player);
			PacketDispatcher.sendTo(new SyncStatMessagesPacket("clr"), (EntityPlayerMP) player);
		}
	}

	public int getMaxLevel () {
		return maxLevel;
	}

	/*
	 * public void setMaxLevel(int maxLevel) { this.maxLevel = maxLevel;
	 * this.sync(); }
	 */

	/*
	public boolean addXP (int amount) {
		boolean sufficient = true;
		setMaxExperience(999999);
		if (amount + this.experience > this.maxExperience || amount > this.maxExperience) {
			this.experience = this.maxExperience;
			sync();
			sufficient = false;
		}

		if (sufficient) {
			this.experience += amount;
			sync();
			PlayerLevel.LevelUp(player);

		} else
			return false;
		if (!player.worldObj.isRemote) PacketDispatcher.sendTo(new ShowOverlayPacket("exp"), (EntityPlayerMP) player);

		return true;
	}
	*/


	/*
	public void setXP (int experience) {
		this.experience = experience;
		sync();
		PlayerLevel.LevelUp(player);
	}
	*/

	public void setCheatMode (boolean bool) {
		this.cheatMode = bool;
		sync();
	}

	public final void sync () {
		SyncExtendedPlayer packet = new SyncExtendedPlayer(player);
		if (player.worldObj.isRemote) PacketDispatcher.sendToServer(packet);

		if (!player.worldObj.isRemote) {
			EntityPlayerMP player1 = (EntityPlayerMP) player;
			PacketDispatcher.sendTo(packet, player1);
		}
	}

	private static String getSaveKey (EntityPlayer player) {
		return player.getDisplayName() + ":" + EXT_PROP_NAME;
	}

	public static void saveProxyData (EntityPlayer player) {
		ExtendedPlayer playerData = ExtendedPlayer.get(player);
		NBTTagCompound SavedData = new NBTTagCompound();

		playerData.saveNBTData(SavedData);
		CommonProxy.storeEntityData(getSaveKey(player), SavedData);
	}

	public static void loadProxyData (EntityPlayer player) {
		ExtendedPlayer playerData = ExtendedPlayer.get(player);
		NBTTagCompound savedData = CommonProxy.getEntityData(getSaveKey(player));

		if (savedData != null) playerData.loadNBTData(savedData);
		playerData.sync();
	}

	public static final void register (EntityPlayer player) {
		//player.registerExtendedProperties(EXT_PROP_NAME, new ExtendedPlayer(player));
	}

	public static final ExtendedPlayer get (EntityPlayer player) {
		return null;
		//return (ExtendedPlayer) player.getExtendedProperties(EXT_PROP_NAME);
	}

	public void learnDriveForm (DriveForm driveForm) {
		driveForms.add(driveForm.getName());
		if (player instanceof EntityPlayerMP) sync();
	}
}
