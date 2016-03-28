package uk.co.wehavecookies56.common.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import uk.co.wehavecookies56.common.network.packet.PacketDispatcher;
import uk.co.wehavecookies56.common.network.packet.server.ManifestKnowledgePacket;

public class ItemManifestKnowledge extends Item {

	public ItemManifestKnowledge () {
		setMaxStackSize(1);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn,
			EnumHand hand) {
		if (worldIn.isRemote) PacketDispatcher.sendToServer(new ManifestKnowledgePacket());
		return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
	}

}
