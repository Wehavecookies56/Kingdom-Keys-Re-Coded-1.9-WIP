package wehavecookies56.kk.network.packet.server;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import wehavecookies56.kk.KingdomKeys;
import wehavecookies56.kk.inventory.InventoryDriveForms;
import wehavecookies56.kk.lib.Strings;
import wehavecookies56.kk.network.packet.AbstractMessage.AbstractServerMessage;
import wehavecookies56.kk.network.packet.PacketDispatcher;
import wehavecookies56.kk.network.packet.client.ShowOverlayPacket;
import wehavecookies56.kk.network.packet.client.SyncDriveData;
import wehavecookies56.kk.network.packet.client.SyncDriveInventory;
import wehavecookies56.kk.util.TextHelper;

public class LevelUpDrive extends AbstractServerMessage<LevelUpDrive> {

	String form;
	boolean isLevelUp = false;
	int levels;

	public LevelUpDrive () {}

	public LevelUpDrive (String form) {
		this.form = form;
	}
	
	public LevelUpDrive (String form, boolean levelup, int level) {
		this.form = form;
		this.isLevelUp = levelup;
		this.levels = level;
	}
	
	@Override
	protected void read (PacketBuffer buffer) throws IOException {
		form = buffer.readStringFromBuffer(40);
		isLevelUp = buffer.readBoolean();
		levels = buffer.readInt();
	}

	@Override
	protected void write (PacketBuffer buffer) throws IOException {
		buffer.writeString(form);
		buffer.writeBoolean(isLevelUp);
		buffer.writeInt(levels);
	}

	@Override
	public void process (EntityPlayer player, Side side) {
		int hasDriveInSlot = -1, nullSlot = -1;
				
		PacketDispatcher.sendTo(new SyncDriveData(player.getCapability(KingdomKeys.DRIVE_STATE, null), player.getCapability(KingdomKeys.PLAYER_STATS, null)), (EntityPlayerMP) player);
		if(isLevelUp)
		{
			player.getCapability(KingdomKeys.DRIVE_STATE, null).setDriveLevel(form, levels);
			//System.out.println(form+" level: "+(player.getCapability(KingdomKeys.DRIVE_STATE, null).getDriveLevel(form)));
		}
		else
		{
			for (int i = 0; i < InventoryDriveForms.INV_SIZE; i++) {
				if (player.getCapability(KingdomKeys.DRIVE_STATE, null).getInventoryDriveForms().getStackInSlot(i) != null) {
					if (player.getCapability(KingdomKeys.DRIVE_STATE, null).getInventoryDriveForms().getStackInSlot(i).getItem() == player.getHeldItem(EnumHand.MAIN_HAND).getItem()) {
						hasDriveInSlot = i;
					}
				} else {
					nullSlot = i;
					break;
				}
			}
	
			if (hasDriveInSlot == -1) {
				player.getCapability(KingdomKeys.DRIVE_STATE, null).getInventoryDriveForms().setInventorySlotContents(nullSlot, player.getHeldItem(EnumHand.MAIN_HAND));
				player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
		    //	PacketDispatcher.sendTo(new ShowOverlayPacket("munny", event.getEntityItem().getEntityItem().getTagCompound().getInteger("amount")), (EntityPlayerMP) event.getPlayer());

				TextHelper.sendFormattedChatMessage("Succesfully learnt "+TextHelper.localize(form), TextFormatting.YELLOW, player);
			} else {
				TextHelper.sendFormattedChatMessage("Already learnt "+TextHelper.localize(form), TextFormatting.YELLOW, player);
	
			}
		}
		PacketDispatcher.sendTo(new SyncDriveInventory(player.getCapability(KingdomKeys.DRIVE_STATE, null)), (EntityPlayerMP) player);
	}
}