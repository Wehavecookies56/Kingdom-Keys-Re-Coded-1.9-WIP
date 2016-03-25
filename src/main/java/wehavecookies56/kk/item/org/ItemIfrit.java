package wehavecookies56.kk.item.org;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wehavecookies56.kk.entities.projectiles.EntityEternalFlames;
import wehavecookies56.kk.entities.projectiles.EntityIfrit;

public class ItemIfrit extends ItemChakram {
	public ItemIfrit (ToolMaterial material) {
		super(material);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStack, World world, EntityPlayer player, EnumHand hand) {
		if (!player.isSneaking()) {
			world.playSound(player.posX, player.posY, player.posZ, SoundEvents.entity_ghast_shoot, SoundCategory.PLAYERS, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F), false);
			EntityIfrit entity = new EntityIfrit(world, player);
			world.spawnEntityInWorld(entity);
			entity.func_184538_a(player, player.rotationPitch, player.rotationYaw, 0, 1f, 1);
			player.swingArm(hand);
		}
		return ActionResult.newResult(EnumActionResult.SUCCESS, itemStack);
	}
}
