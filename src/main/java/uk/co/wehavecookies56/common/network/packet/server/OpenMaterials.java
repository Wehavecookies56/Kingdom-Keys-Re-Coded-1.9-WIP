package uk.co.wehavecookies56.common.network.packet.server;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;
import uk.co.wehavecookies56.api.materials.MaterialRegistry;
import uk.co.wehavecookies56.common.capability.ModCapabilities;
import uk.co.wehavecookies56.common.item.ItemSynthesisMaterial;
import uk.co.wehavecookies56.common.network.packet.AbstractMessage.AbstractServerMessage;
import uk.co.wehavecookies56.common.network.packet.client.SyncMaterialData;
import uk.co.wehavecookies56.common.network.packet.PacketDispatcher;

public class OpenMaterials extends AbstractServerMessage<OpenMaterials> {

	public OpenMaterials () {}

	@Override
	protected void read (PacketBuffer buffer) throws IOException {

	}

	@Override
	protected void write (PacketBuffer buffer) throws IOException {

	}

	@Override
	public void process (EntityPlayer player, Side side) {
		for (int i = 0; i < 36; i++)
			if (player.inventory.mainInventory[i] != null) if (player.inventory.mainInventory[i].getItem() instanceof ItemSynthesisMaterial) {
				if (player.inventory.mainInventory[i].hasTagCompound()) {
					String s = player.inventory.mainInventory[i].getTagCompound().getString("material");
					if (MaterialRegistry.isMaterialRegistered(s)) player.getCapability(ModCapabilities.SYNTHESIS_MATERIALS, null).addMaterial(MaterialRegistry.get(s), player.inventory.mainInventory[i].stackSize);
					player.inventory.setInventorySlotContents(i, null);
				}

			} else if (MaterialRegistry.isMaterialRegistered(player.inventory.mainInventory[i].getItem().getUnlocalizedName().toString())) {
				player.getCapability(ModCapabilities.SYNTHESIS_MATERIALS, null).addMaterial(MaterialRegistry.get(player.inventory.mainInventory[i].getItem().getUnlocalizedName()), player.inventory.mainInventory[i].stackSize);
				player.inventory.setInventorySlotContents(i, null);
			}
		PacketDispatcher.sendTo(new SyncMaterialData(player.getCapability(ModCapabilities.SYNTHESIS_MATERIALS, null)), (EntityPlayerMP) player);
	}

}
