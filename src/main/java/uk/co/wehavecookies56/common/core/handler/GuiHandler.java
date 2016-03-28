package uk.co.wehavecookies56.common.core.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import uk.co.wehavecookies56.client.gui.*;
import uk.co.wehavecookies56.common.capability.ModCapabilities;
import uk.co.wehavecookies56.common.container.*;
import uk.co.wehavecookies56.common.container.inventory.InventorySynthesisBagL;
import uk.co.wehavecookies56.common.container.inventory.InventorySynthesisBagM;
import uk.co.wehavecookies56.common.container.inventory.InventorySynthesisBagS;
import uk.co.wehavecookies56.common.lib.GuiIDs;
import uk.co.wehavecookies56.common.block.tile.TileEntityKKChest;

/**
 * Created by Toby on 28/03/2016.
 */
public class GuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement (int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos xyz = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(xyz);

        if (ID == GuiIDs.GUI_KEYCHAIN_INV)
            return new ContainerKeychain(player, player.inventory, player.getCapability(ModCapabilities.SUMMON_KEYBLADE, null).getInventoryKeychain());
        else if (ID == GuiIDs.GUI_POTIONS_INV)
            return new ContainerPotionsMenu(player, player.inventory, player.getCapability(ModCapabilities.PLAYER_STATS, null).getInventoryPotionsMenu());
        else if (ID == GuiIDs.GUI_SPELLS_INV)
            return new ContainerSpells(player, player.inventory, player.getCapability(ModCapabilities.MAGIC_STATE, null).getInventorySpells());
        else if (ID == GuiIDs.GUI_DRIVE_INV)
            return new ContainerDriveForms(player, player.inventory, player.getCapability(ModCapabilities.DRIVE_STATE, null).getInventoryDriveForms());
        else if (ID == GuiIDs.GUI_KKCHEST_INV) {
            if (te instanceof TileEntityKKChest)
                return new ContainerKKChest(player.inventory, (TileEntityKKChest) world.getTileEntity(new BlockPos(x, y, z)));
            else
                return null;
        }
        else if (ID == GuiIDs.GUI_SPELLS_INV) {
            return null;
        }
        else if (ID == GuiIDs.GUI_SYNTHESISBAGS_INV)
            return new ContainerSynthesisBagS(player, player.inventory, new InventorySynthesisBagS(player.getHeldItem(EnumHand.MAIN_HAND)));
        else if (ID == GuiIDs.GUI_SYNTHESISBAGM_INV)
            return new ContainerSynthesisBagM(player, player.inventory, new InventorySynthesisBagM(player.getHeldItem(EnumHand.MAIN_HAND)));
        else if (ID == GuiIDs.GUI_SYNTHESISBAGL_INV) return new ContainerSynthesisBagL(player, player.inventory, new InventorySynthesisBagL(player.getHeldItem(EnumHand.MAIN_HAND)));
        return null;
    }

    @Override
    public Object getClientGuiElement (int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos xyz = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(xyz);

        if (ID == GuiIDs.GUI_KEYCHAIN_INV)
            return new GuiKeychains(player, player.inventory, player.getCapability(ModCapabilities.SUMMON_KEYBLADE, null).getInventoryKeychain());
        else if (ID == GuiIDs.GUI_POTIONS_INV)
            return new GuiPotions(player, player.inventory, player.getCapability(ModCapabilities.PLAYER_STATS, null).getInventoryPotionsMenu());
        else if (ID == GuiIDs.GUI_SPELLS_INV)
            return new GuiSpells(player, player.inventory, player.getCapability(ModCapabilities.MAGIC_STATE, null).getInventorySpells());
        else if (ID == GuiIDs.GUI_DRIVE_INV)
            return new GuiDriveForms(player, player.inventory, player.getCapability(ModCapabilities.DRIVE_STATE, null).getInventoryDriveForms());
        else if (ID == GuiIDs.GUI_KKCHEST_INV) {

            if (te instanceof TileEntityKKChest)
                return new GuiKKChest(player.inventory, (TileEntityKKChest) world.getTileEntity(new BlockPos(x, y, z)));
            else
                return null;
        }
        else if (ID == GuiIDs.GUI_SYNTHESISTABLE)
            return new GuiSynthesis(null);
        else if (ID == GuiIDs.GUI_SYNTHESISBAGS_INV)
            return new GuiSynthesisBagS(player, player.inventory, new InventorySynthesisBagS(player.getHeldItem(EnumHand.MAIN_HAND)));
        else if (ID == GuiIDs.GUI_SYNTHESISBAGM_INV)
            return new GuiSynthesisBagM(player, player.inventory, new InventorySynthesisBagM(player.getHeldItem(EnumHand.MAIN_HAND)));
        else if (ID == GuiIDs.GUI_SYNTHESISBAGL_INV) return new GuiSynthesisBagL(player, player.inventory, new InventorySynthesisBagL(player.getHeldItem(EnumHand.MAIN_HAND)));
        return null;

    }
}
