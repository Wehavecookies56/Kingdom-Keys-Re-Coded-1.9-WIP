package uk.co.wehavecookies56.kk.common.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import uk.co.wehavecookies56.kk.common.block.base.BlockBlox;

public class BlockBounceBlox extends BlockBlox {

	protected BlockBounceBlox (Material material, String toolClass, int level, float hardness, float resistance) {
		super(material, toolClass, level, hardness, resistance);
	}

	@Override
	public void onEntityCollidedWithBlock (World worldIn, BlockPos pos, Entity entityIn) {
		if (!entityIn.isSneaking()) {
			entityIn.playSound(SoundEvents.entity_slime_jump, 1, 1);
			double d0 = 0.4D + Math.abs(entityIn.motionY) * 0.2D;
			entityIn.motionX *= d0;
			entityIn.motionZ *= d0;
			entityIn.motionY++;
			entityIn.fallDistance = 0;
			super.onEntityCollidedWithBlock(worldIn, pos, entityIn);
		}
	}
}
