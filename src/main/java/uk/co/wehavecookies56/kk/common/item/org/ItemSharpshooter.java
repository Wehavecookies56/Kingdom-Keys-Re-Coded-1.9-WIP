package uk.co.wehavecookies56.kk.common.item.org;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import uk.co.wehavecookies56.kk.common.capability.ModCapabilities;
import uk.co.wehavecookies56.kk.common.entity.projectiles.EntitySharpshooterBullet;

public class ItemSharpshooter extends ItemSword {
	int strength = 30;

	public ItemSharpshooter (ToolMaterial material) {
		super(material);
		setMaxStackSize(1);
	}

	@Override
	@SideOnly (Side.CLIENT)
	public EnumRarity getRarity (ItemStack par1ItemStack) {
		return EnumRarity.UNCOMMON;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
		strength = -(strength) + 71999;
		if (!player.isSneaking()) {
			// TODO set strength

			if (player.getCapability(ModCapabilities.PLAYER_STATS, null).getMP() > 0 && !player.getCapability(ModCapabilities.PLAYER_STATS, null).getRecharge() || player.getCapability(ModCapabilities.CHEAT_MODE, null).getCheatMode()) {
				world.playSound(player.posX, player.posY, player.posZ, SoundEvents.entity_ghast_shoot, SoundCategory.PLAYERS, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F), false);
				world.spawnEntityInWorld(new EntitySharpshooterBullet(world, player, strength));
				if (!player.getCapability(ModCapabilities.CHEAT_MODE, null).getCheatMode()) player.getCapability(ModCapabilities.PLAYER_STATS, null).remMP(10);
				player.swingArm(EnumHand.MAIN_HAND);
			}
		} else {
			//player.setItemInUse(stack, getMaxItemUseDuration(stack));
		}
		return super.onItemRightClick(stack, world, player, hand);
	}

	@Override
	@SideOnly (Side.CLIENT)
	public void addInformation (ItemStack itemStack, EntityPlayer player, List<String> dataList, boolean bool) {
		dataList.add("II Xigbar");
	}
}
