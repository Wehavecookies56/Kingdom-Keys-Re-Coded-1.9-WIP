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
import wehavecookies56.kk.network.packet.client.SyncDriveData;
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
		int valorLevel = player.getCapability(KingdomKeys.DRIVE_STATE, null).getDriveLevel(Strings.Form_Valor);
		int wisdomLevel = player.getCapability(KingdomKeys.DRIVE_STATE, null).getDriveLevel(Strings.Form_Wisdom);
		int limitLevel = player.getCapability(KingdomKeys.DRIVE_STATE, null).getDriveLevel(Strings.Form_Limit);
		int masterLevel = player.getCapability(KingdomKeys.DRIVE_STATE, null).getDriveLevel(Strings.Form_Master);
		int finalLevel = player.getCapability(KingdomKeys.DRIVE_STATE, null).getDriveLevel(Strings.Form_Final);

		int hasDriveInSlot = -1, nullSlot = -1;
		
		int formLevel = 0;
		switch(form)
		{
			case Strings.Form_Valor:
				formLevel = valorLevel;
				break;
			case Strings.Form_Wisdom:
				formLevel = wisdomLevel;
				break;
			case Strings.Form_Limit:
				formLevel = limitLevel;
				break;
			case Strings.Form_Master:
				formLevel = masterLevel;
				break;
			case Strings.Form_Final:
				formLevel = finalLevel;
				break;
			default:
			break;
		}
		PacketDispatcher.sendTo(new SyncDriveData(player.getCapability(KingdomKeys.DRIVE_STATE, null), player.getCapability(KingdomKeys.PLAYER_STATS, null)), (EntityPlayerMP) player);
		if(isLevelUp)
		{//TODO
			player.getCapability(KingdomKeys.DRIVE_STATE, null).setDriveLevel(form, formLevel+levels);
			System.out.println(form+" level: "+(formLevel+1));
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
				TextHelper.sendFormattedChatMessage("Succesfully learnt " + form + " Form!", TextFormatting.YELLOW, player);
			} else {
				TextHelper.sendFormattedChatMessage("Already learnt " + form + " Form!", TextFormatting.YELLOW, player);
	
			}
		}
	}
}