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
	
	public ExtendedPlayer (EntityPlayer player) {
		this.player = player;
	}

	@Override
	public void init (Entity entity, World world) {}

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

	@Override
	public void saveNBTData(NBTTagCompound compound) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		// TODO Auto-generated method stub
		
	}
}
