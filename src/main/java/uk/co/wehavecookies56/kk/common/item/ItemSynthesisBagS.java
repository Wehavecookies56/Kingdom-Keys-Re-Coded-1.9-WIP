package uk.co.wehavecookies56.kk.common.item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import uk.co.wehavecookies56.kk.common.core.helper.TextHelper;
import uk.co.wehavecookies56.kk.common.lib.GuiIDs;
import uk.co.wehavecookies56.kk.common.lib.Strings;
import uk.co.wehavecookies56.kk.common.network.packet.PacketDispatcher;
import uk.co.wehavecookies56.kk.common.network.packet.server.OpenGui;

public class ItemSynthesisBagS extends Item {

	public ItemSynthesisBagS () {
		setMaxStackSize(1);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
		if (world.isRemote) PacketDispatcher.sendToServer(new OpenGui(GuiIDs.GUI_SYNTHESISBAGS_INV));
		return super.onItemRightClick(stack, world, player, hand);
	}

	@Override
	public void addInformation (ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
		int x = 30;
		String s = TextHelper.localize(Strings.SynthesisBagDesc);
		s = s.replaceAll("(.{" + x + ",}?)\\s+", "$1\n");
		String[] splitS = s.split("\n");
		for (String element : splitS)
			tooltip.add(element);
	}
}
