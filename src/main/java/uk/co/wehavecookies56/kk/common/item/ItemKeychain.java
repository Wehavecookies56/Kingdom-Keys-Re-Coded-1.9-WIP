package uk.co.wehavecookies56.kk.common.item;

import net.minecraft.item.Item;

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
}