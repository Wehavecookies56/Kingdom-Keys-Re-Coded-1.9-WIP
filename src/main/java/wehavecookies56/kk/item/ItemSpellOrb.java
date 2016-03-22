package wehavecookies56.kk.item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import wehavecookies56.kk.KingdomKeys;
import wehavecookies56.kk.lib.Constants;
import wehavecookies56.kk.network.packet.PacketDispatcher;
import wehavecookies56.kk.network.packet.client.SyncMagicData;
import wehavecookies56.kk.network.packet.server.magics.LevelUpMagic;
import wehavecookies56.kk.util.TextHelper;

public abstract class ItemSpellOrb extends Item {

	String magic;

	public ItemSpellOrb (String magic) {
		this.magic = magic;
		setMaxStackSize(1);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World world, EntityPlayer player, EnumHand hand) {
		if (world.isRemote) {
			PacketDispatcher.sendToServer(new LevelUpMagic(this.magic));
		}
		return super.onItemRightClick(itemStackIn, world, player, hand);
	}
	
	public String getMagicLevelName(EntityPlayer player, String magic){
		String magicName;
		int magicLevel = player.getCapability(KingdomKeys.MAGIC_STATE, null).getMagicLevel(magic);
		return magic;
	}

	@Override
	public void addInformation (ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
		tooltip.add(TextHelper.localize(Constants.getMagicName(magic, player.getCapability(KingdomKeys.MAGIC_STATE, null).getMagicLevel(magic))));
		super.addInformation(stack, player, tooltip, advanced);
	}

	public String getMagicName () {
		return magic;
	}

	public void setMagicName (String magic) {
		this.magic = magic;
	}
}
