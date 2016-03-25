package wehavecookies56.kk.item.org;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
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

public class ItemEternalFlames extends ItemSword {
	public ItemEternalFlames (ToolMaterial material) {
		super(material);
		setMaxStackSize(1);
	}

	@Override
	@SideOnly (Side.CLIENT)
	public EnumRarity getRarity (ItemStack par1ItemStack) {
		return EnumRarity.UNCOMMON;
	}

	@Override
	public boolean hitEntity (ItemStack item, EntityLivingBase entity, EntityLivingBase p_77644_3_) {
		entity.setFire(5);
		return super.hitEntity(item, entity, p_77644_3_);
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase player, int timeLeft) {
		
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase player) {
		return super.onItemUseFinish(stack, world, player);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStack, World world, EntityPlayer player, EnumHand hand) {
		if (!player.isSneaking()) {
			world.playSound(player.posX, player.posY, player.posZ, SoundEvents.entity_ghast_shoot, SoundCategory.PLAYERS, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F), false);
			EntityEternalFlames entity = new EntityEternalFlames(world, player);
			world.spawnEntityInWorld(entity);
			entity.func_184538_a(player, player.rotationPitch, player.rotationYaw, 0, 1f, 1);
			player.swingArm(EnumHand.MAIN_HAND);
		}
		return ActionResult.newResult(EnumActionResult.SUCCESS, itemStack);
	}

	@Override
	@SideOnly (Side.CLIENT)
	public void addInformation (ItemStack itemStack, EntityPlayer player, List<String> dataList, boolean bool) {
		dataList.add("VIII Axel");
	}
}