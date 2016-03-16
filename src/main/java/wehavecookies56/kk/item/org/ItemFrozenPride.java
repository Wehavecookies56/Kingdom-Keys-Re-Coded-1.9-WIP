package wehavecookies56.kk.item.org;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wehavecookies56.kk.item.ModItems;

public class ItemFrozenPride extends ItemSword {

	public ItemFrozenPride (ToolMaterial material) {
		super(material);
		setMaxStackSize(1);
	}

	@Override
	public void onUpdate (ItemStack item, World world, Entity entity, int p_77663_4_, boolean p_77663_5_) {
		EntityPlayer player = (EntityPlayer) entity;
		/* TODO Blocking gone
		if (player.getHeldItem(EnumHand.MAIN_HAND) != null && player.getHeldItem(EnumHand.MAIN_HAND).getItem() == ModItems.FrozenPride) if (player.isBlocking())
			player.setInWeb();
		else {
			// player.capabilities.setPlayerWalkSpeed(0.1F);
		}
		*/
		super.onUpdate(item, world, entity, p_77663_4_, p_77663_5_);
	}

	@Override
	@SideOnly (Side.CLIENT)
	public EnumRarity getRarity (ItemStack par1ItemStack) {
		return EnumRarity.UNCOMMON;
	}

	@Override
	@SideOnly (Side.CLIENT)
	public void addInformation (ItemStack itemStack, EntityPlayer player, List dataList, boolean bool) {
		dataList.add("IV Vexen");
	}

}
