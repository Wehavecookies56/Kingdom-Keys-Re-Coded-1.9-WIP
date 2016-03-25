package wehavecookies56.kk.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockDangerBlox extends BlockBlox {

	protected BlockDangerBlox (Material material, String toolClass, int level, float hardness, float resistance) {
		super(material, toolClass, level, hardness, resistance);
	}

	@Override
	public void onBlockClicked (World par1World, BlockPos pos, EntityPlayer par5EntityPlayer) {
		par5EntityPlayer.attackEntityFrom(DamageSource.magic, 3);
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox (IBlockState worldIn, World pos, BlockPos state) {
		return new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 1.0D, 0.9375D).offset(state);
	}
	
	@Override
	public AxisAlignedBB getSelectedBoundingBox(IBlockState blockState, World worldIn, BlockPos pos) {
		return new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.9375D, 0.9375D);
	}

	@Override
	public void onEntityCollidedWithBlock (World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		if (entityIn instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entityIn;
			if (player.inventory.armorItemInSlot(0) != null) {
				ItemStack itemstack = player.inventory.armorItemInSlot(0);
			} else
				entityIn.attackEntityFrom(DamageSource.magic, 3.0F);
		} else
			entityIn.attackEntityFrom(DamageSource.magic, 3);
	}
}
