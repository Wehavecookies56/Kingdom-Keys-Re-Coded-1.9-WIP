package uk.co.wehavecookies56.common.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemDriveOrb extends Item {

	public ItemDriveOrb () {
		setMaxStackSize(1);
	}

	@Override
	public void addInformation (ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
		if (stack.hasTagCompound()) {
			int amount = stack.getTagCompound().getInteger("amount");
			tooltip.add("" + amount);
		}
	}

	@Override
	public void getSubItems (Item itemIn, CreativeTabs tab, List subItems) {}
}
