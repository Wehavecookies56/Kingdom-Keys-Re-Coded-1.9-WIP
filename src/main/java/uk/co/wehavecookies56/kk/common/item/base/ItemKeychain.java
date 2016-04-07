package uk.co.wehavecookies56.kk.common.item.base;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemKeychain extends Item {

	ItemKeyblade blade;
    
	public ItemKeychain (ItemKeyblade blade) {
		this.blade = blade;
		setMaxStackSize(1);
	}

	public void setKeyblade (ItemKeyblade blade) {
		this.blade = blade;
	}

	public ItemKeyblade getKeyblade () {
		return this.blade;
	}
	@Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book){
		return true;
	}
	@Override
	public int getItemEnchantability()
    {
        return 30;
    }


}
