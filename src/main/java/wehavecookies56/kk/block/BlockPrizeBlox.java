package wehavecookies56.kk.block;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

public class BlockPrizeBlox extends BlockBlox {

	protected BlockPrizeBlox (Material material, String toolClass, int level, float hardness, float resistance) {
		super(material, toolClass, level, hardness, resistance);
	}

	@Override
	public Item getItemDropped (IBlockState state, Random r, int fortune) {
		return null;
	}
}
