package wehavecookies56.kk.network;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import wehavecookies56.kk.KingdomKeys;
import wehavecookies56.kk.achievements.ModAchievements;
import wehavecookies56.kk.client.gui.GuiDriveForms;
import wehavecookies56.kk.client.gui.GuiKKChest;
import wehavecookies56.kk.client.gui.GuiKeychains;
import wehavecookies56.kk.client.gui.GuiPotions;
import wehavecookies56.kk.client.gui.GuiSpells;
import wehavecookies56.kk.client.gui.GuiSynthesis;
import wehavecookies56.kk.client.gui.GuiSynthesisBagL;
import wehavecookies56.kk.client.gui.GuiSynthesisBagM;
import wehavecookies56.kk.client.gui.GuiSynthesisBagS;
import wehavecookies56.kk.entities.TileEntityKKChest;
import wehavecookies56.kk.inventory.ContainerDriveForms;
import wehavecookies56.kk.inventory.ContainerKKChest;
import wehavecookies56.kk.inventory.ContainerKeychain;
import wehavecookies56.kk.inventory.ContainerPotionsMenu;
import wehavecookies56.kk.inventory.ContainerSpells;
import wehavecookies56.kk.inventory.ContainerSynthesisBagL;
import wehavecookies56.kk.inventory.ContainerSynthesisBagM;
import wehavecookies56.kk.inventory.ContainerSynthesisBagS;
import wehavecookies56.kk.inventory.InventorySynthesisBagL;
import wehavecookies56.kk.inventory.InventorySynthesisBagM;
import wehavecookies56.kk.inventory.InventorySynthesisBagS;
import wehavecookies56.kk.util.LogHelper;

public class CommonProxy implements IGuiHandler {

	public void init () {
		// NetworkRegistry.INSTANCE.registerGuiHandler(this, new CommonProxy());
	}

	public EntityPlayer getPlayerEntity (MessageContext ctx) {
		return ctx.getServerHandler().playerEntity;
	}

	public IThreadListener getThreadFromContext (MessageContext ctx) {
		return ctx.getServerHandler().playerEntity.getServerForPlayer();
	}

	private static final Map<String, NBTTagCompound> extendedEntityData = new HashMap<String, NBTTagCompound>();

	public static void storeEntityData (String name, NBTTagCompound compound) {
		extendedEntityData.put(name, compound);
	}

	public void spawnTestParticle(World world, double posX, double posY, double posZ, double motionX, double motionY, double motionZ) {}
	
	public static NBTTagCompound getEntityData (String name) {
		return extendedEntityData.remove(name);
	}

	@Override
	public Object getServerGuiElement (int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos xyz = new BlockPos(x, y, z);
		TileEntity te = world.getTileEntity(xyz);

		if (ID == KingdomKeys.GUI_KEYCHAIN_INV)
			return new ContainerKeychain(player, player.inventory, player.getCapability(KingdomKeys.PLAYER_STATS, null).getInventoryKeychain());
		else if (ID == KingdomKeys.GUI_POTIONS_INV)
			return new ContainerPotionsMenu(player, player.inventory, player.getCapability(KingdomKeys.PLAYER_STATS, null).getInventoryPotionsMenu());
		else if (ID == KingdomKeys.GUI_SPELLS_INV)
			return new ContainerSpells(player, player.inventory, player.getCapability(KingdomKeys.MAGIC_STATE, null).getInventorySpells());
		else if (ID == KingdomKeys.GUI_DRIVE_INV)
			return new ContainerDriveForms(player, player.inventory, player.getCapability(KingdomKeys.DRIVE_STATE, null).getInventoryDriveForms());
		else if (ID == KingdomKeys.GUI_KKCHEST_INV) {
			if (te instanceof TileEntityKKChest)
				return new ContainerKKChest(player.inventory, (TileEntityKKChest) world.getTileEntity(new BlockPos(x, y, z)));
			else
				return null;
		}
		else if (ID == KingdomKeys.GUI_SPELLS_INV) {
			return null;
		}
		else if (ID == KingdomKeys.GUI_SYNTHESISBAGS_INV)
			return new ContainerSynthesisBagS(player, player.inventory, new InventorySynthesisBagS(player.getHeldItem(EnumHand.MAIN_HAND)));
		else if (ID == KingdomKeys.GUI_SYNTHESISBAGM_INV)
			return new ContainerSynthesisBagM(player, player.inventory, new InventorySynthesisBagM(player.getHeldItem(EnumHand.MAIN_HAND)));
		else if (ID == KingdomKeys.GUI_SYNTHESISBAGL_INV) return new ContainerSynthesisBagL(player, player.inventory, new InventorySynthesisBagL(player.getHeldItem(EnumHand.MAIN_HAND)));
		return null;
	}

	@Override
	public Object getClientGuiElement (int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos xyz = new BlockPos(x, y, z);
		TileEntity te = world.getTileEntity(xyz);

		if (ID == KingdomKeys.GUI_KEYCHAIN_INV)
			return new GuiKeychains(player, player.inventory, player.getCapability(KingdomKeys.PLAYER_STATS, null).getInventoryKeychain());
		else if (ID == KingdomKeys.GUI_POTIONS_INV)
			return new GuiPotions(player, player.inventory, player.getCapability(KingdomKeys.PLAYER_STATS, null).getInventoryPotionsMenu());
		else if (ID == KingdomKeys.GUI_SPELLS_INV)
			return new GuiSpells(player, player.inventory, player.getCapability(KingdomKeys.MAGIC_STATE, null).getInventorySpells());
		else if (ID == KingdomKeys.GUI_DRIVE_INV)
			return new GuiDriveForms(player, player.inventory, player.getCapability(KingdomKeys.DRIVE_STATE, null).getInventoryDriveForms());
		else if (ID == KingdomKeys.GUI_KKCHEST_INV) {

			if (te instanceof TileEntityKKChest)
				return new GuiKKChest(player.inventory, (TileEntityKKChest) world.getTileEntity(new BlockPos(x, y, z)));
			else
				return null;
		} 
		else if (ID == KingdomKeys.GUI_SYNTHESISTABLE)
			return new GuiSynthesis(null);
		else if (ID == KingdomKeys.GUI_SYNTHESISBAGS_INV)
			return new GuiSynthesisBagS(player, player.inventory, new InventorySynthesisBagS(player.getHeldItem(EnumHand.MAIN_HAND)));
		else if (ID == KingdomKeys.GUI_SYNTHESISBAGM_INV)
			return new GuiSynthesisBagM(player, player.inventory, new InventorySynthesisBagM(player.getHeldItem(EnumHand.MAIN_HAND)));
		else if (ID == KingdomKeys.GUI_SYNTHESISBAGL_INV) return new GuiSynthesisBagL(player, player.inventory, new InventorySynthesisBagL(player.getHeldItem(EnumHand.MAIN_HAND)));
		return null;

	}

	public void preInit () {

	}

	public void registerAchievements () {
		// Achievements
		ModAchievements.init();
		ModAchievements.register();
		LogHelper.info("Achievements loaded");
	}

}
