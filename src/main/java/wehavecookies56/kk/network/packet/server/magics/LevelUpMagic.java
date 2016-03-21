package wehavecookies56.kk.network.packet.server.magics;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import wehavecookies56.kk.KingdomKeys;
import wehavecookies56.kk.inventory.InventorySpells;
import wehavecookies56.kk.lib.Constants;
import wehavecookies56.kk.network.packet.AbstractMessage.AbstractServerMessage;
import wehavecookies56.kk.util.TextHelper;

public class LevelUpMagic extends AbstractServerMessage<LevelUpMagic> {

	String magic;

	public LevelUpMagic () {}

	public LevelUpMagic (String magic) {
		this.magic = magic;
	}

	@Override
	protected void read (PacketBuffer buffer) throws IOException {
		magic = buffer.readStringFromBuffer(40);
	}

	@Override
	protected void write (PacketBuffer buffer) throws IOException {
		buffer.writeString(magic);
	}

	@Override
	public void process (EntityPlayer player, Side side) {

		int firstEmptySlot = -1;

		boolean hasMagicInSlot = false;
				
		for (int i = 0; i < InventorySpells.INV_SIZE; i++) {
			if (player.getCapability(KingdomKeys.PLAYER_STATS, null).getInventorySpells().getStackInSlot(i) != null) {
				if (player.getCapability(KingdomKeys.PLAYER_STATS, null).getInventorySpells().getStackInSlot(i).getItem() == player.getHeldItem(EnumHand.MAIN_HAND).getItem()) {
					hasMagicInSlot = true;
				}
			} else {
				firstEmptySlot = i;
				break;
			}
		}

		if (!hasMagicInSlot) {
			player.getCapability(KingdomKeys.PLAYER_STATS, null).getInventorySpells().setInventorySlotContents(firstEmptySlot, player.getHeldItem(EnumHand.MAIN_HAND));
			player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
			String magicName = TextHelper.localize(Constants.getMagicName(magic, player.getCapability(KingdomKeys.MAGIC_STATE, null).getMagicLevel(magic)));
			TextHelper.sendFormattedChatMessage("Succesfully learnt " + magicName + "!", TextFormatting.YELLOW, player);
		} else {
			if (player.getCapability(KingdomKeys.MAGIC_STATE, null).getMagicLevel(magic) < Constants.MAX_MAGIC_LEVEL) {
				player.getCapability(KingdomKeys.MAGIC_STATE, null).setMagicLevel(magic, player.getCapability(KingdomKeys.MAGIC_STATE, null).getMagicLevel(magic) + 1);
				player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
				TextHelper.sendFormattedChatMessage("Leveled up " + TextHelper.localize(Constants.getMagicName(magic, player.getCapability(KingdomKeys.MAGIC_STATE, null).getMagicLevel(magic) - 1)) + ", it is now " + TextHelper.localize(Constants.getMagicName(magic, player.getCapability(KingdomKeys.MAGIC_STATE, null).getMagicLevel(magic))) + "!", TextFormatting.YELLOW, player);

			} else {
				TextHelper.sendFormattedChatMessage("Can't level up " + TextHelper.localize(Constants.getMagicName(magic, player.getCapability(KingdomKeys.MAGIC_STATE, null).getMagicLevel(magic))) + ", it is already at the max level!", TextFormatting.YELLOW, player);
			}
		}		
	}
}
