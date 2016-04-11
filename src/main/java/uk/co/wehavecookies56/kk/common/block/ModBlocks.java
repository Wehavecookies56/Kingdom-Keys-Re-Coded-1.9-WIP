package uk.co.wehavecookies56.kk.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import uk.co.wehavecookies56.kk.common.lib.Reference;
import uk.co.wehavecookies56.kk.common.lib.Strings;

public class ModBlocks {

	public static Block NormalBlox, HardBlox, MetalBlox, DangerBlox, BounceBlox, BlastBlox, PrizeBlox, RarePrizeBlox, GhostBlox, BlazingOre, BrightOre, DarkOre, DarkOreE, DenseOre, EnergyOre, FrostOre, LightningOre, LucidOre, PowerOre, PowerOreE, RemembranceOre, SerenityOre, StormyOre, TranquilOre, TwilightOre, SynthesisTable, KKChest, SavePoint, MagnetBlox;

	public static CreativeTabs tabKingdomKeysBlocks;

	public static void init () {
		tabKingdomKeysBlocks = new TabKingdomKeysBlocks(CreativeTabs.getNextID(), Strings.tabKingdomKeysBlocks);
		NormalBlox = new BlockNormalBlox(Material.iron, "pickaxe", 0, 1f, 10f).setUnlocalizedName(Strings.NormalBlox).setCreativeTab(tabKingdomKeysBlocks);
		HardBlox = new BlockHardBlox(Material.iron, "pickaxe", 1, 5f, 20f).setUnlocalizedName(Strings.HardBlox).setCreativeTab(tabKingdomKeysBlocks);
		MetalBlox = new BlockMetalBlox(Material.iron, "pickaxe", 2, 10f, 60f).setUnlocalizedName(Strings.MetalBlox).setCreativeTab(tabKingdomKeysBlocks);
		DangerBlox = new BlockDangerBlox(Material.iron, "pickaxe", 1, 1f, 1f).setUnlocalizedName(Strings.DangerBlox).setCreativeTab(tabKingdomKeysBlocks);
		BounceBlox = new BlockBounceBlox(Material.iron, "pickaxe", 0, 1f, 1f).setUnlocalizedName(Strings.BounceBlox).setCreativeTab(tabKingdomKeysBlocks);
		BlastBlox = new BlockBlastBlox(Material.iron, "pickaxe", 0, 1f, 1f).setUnlocalizedName(Strings.BlastBlox).setCreativeTab(tabKingdomKeysBlocks);
		PrizeBlox = new BlockPrizeBlox(Material.iron, "pickaxe", 0, 1f, 1f).setUnlocalizedName(Strings.PrizeBlox).setCreativeTab(tabKingdomKeysBlocks);
		RarePrizeBlox = new BlockRarePrizeBlox(Material.iron, "pickaxe", 1, 1f, 1f).setUnlocalizedName(Strings.RarePrizeBlox).setCreativeTab(tabKingdomKeysBlocks);
		GhostBlox = new BlockGhostBlox(Material.circuits, "pickaxe", 1, 1f, 1f).setUnlocalizedName(Strings.GhostBlox).setCreativeTab(tabKingdomKeysBlocks);
		BlazingOre = new BlockBlazingOre(Material.iron, "pickaxe", 1, 1f, 1f).setUnlocalizedName(Strings.BlazingOre).setCreativeTab(tabKingdomKeysBlocks);
		BrightOre = new BlockBrightOre(Material.iron, "pickaxe", 1, 1f, 1f).setUnlocalizedName(Strings.BrightOre).setCreativeTab(tabKingdomKeysBlocks);
		DarkOre = new BlockDarkOre(Material.iron, "pickaxe", 2, 1f, 1f).setUnlocalizedName(Strings.DarkOre).setCreativeTab(tabKingdomKeysBlocks);
		DarkOreE = new BlockDarkOreE(Material.iron, "pickaxe", 2, 1f, 1f).setUnlocalizedName(Strings.DarkOreE).setCreativeTab(tabKingdomKeysBlocks);
		DenseOre = new BlockDenseOre(Material.iron, "pickaxe", 2, 1f, 1f).setUnlocalizedName(Strings.DenseOre).setCreativeTab(tabKingdomKeysBlocks);
		EnergyOre = new BlockEnergyOre(Material.iron, "pickaxe", 1, 1f, 1f).setUnlocalizedName(Strings.EnergyOre).setCreativeTab(tabKingdomKeysBlocks);
		FrostOre = new BlockFrostOre(Material.iron, "pickaxe", 1, 1f, 1f).setUnlocalizedName(Strings.FrostOre).setCreativeTab(tabKingdomKeysBlocks);
		LightningOre = new BlockLightningOre(Material.iron, "pickaxe", 1, 1f, 1f).setUnlocalizedName(Strings.LightningOre).setCreativeTab(tabKingdomKeysBlocks);
		LucidOre = new BlockLucidOre(Material.iron, "pickaxe", 1, 1f, 1f).setUnlocalizedName(Strings.LucidOre).setCreativeTab(tabKingdomKeysBlocks);
		PowerOre = new BlockPowerOre(Material.iron, "pickaxe", 2, 1f, 1f).setUnlocalizedName(Strings.PowerOre).setCreativeTab(tabKingdomKeysBlocks);
		PowerOreE = new BlockPowerOreE(Material.iron, "pickaxe", 2, 1f, 1f).setUnlocalizedName(Strings.PowerOreE).setCreativeTab(tabKingdomKeysBlocks);
		RemembranceOre = new BlockRemembranceOre(Material.iron, "pickaxe", 1, 1f, 1f).setUnlocalizedName(Strings.RemembranceOre).setCreativeTab(tabKingdomKeysBlocks);
		SerenityOre = new BlockSerenityOre(Material.iron, "pickaxe", 1, 1f, 1f).setUnlocalizedName(Strings.SerenityOre).setCreativeTab(tabKingdomKeysBlocks);
		TranquilOre = new BlockTranquilOre(Material.iron, "pickaxe", 1, 1f, 1f).setUnlocalizedName(Strings.TranquilOre).setCreativeTab(tabKingdomKeysBlocks);
		StormyOre = new BlockStormyOre(Material.iron, "pickaxe", 2, 1f, 1f).setUnlocalizedName(Strings.StormyOre).setCreativeTab(tabKingdomKeysBlocks);
		TwilightOre = new BlockTwilightOre(Material.iron, "pickaxe", 2, 1f, 1f).setUnlocalizedName(Strings.TwilightOre).setCreativeTab(tabKingdomKeysBlocks);
		SynthesisTable = new BlockSynthesisTable(Material.wood, "axe", 0, 1, 1).setUnlocalizedName(Strings.SynthesisTable).setCreativeTab(tabKingdomKeysBlocks);
		KKChest = new BlockKKChest(Material.rock, "pickaxe", 3, 20f, 5f).setUnlocalizedName(Strings.KKChest).setCreativeTab(tabKingdomKeysBlocks);
		SavePoint = new BlockSavePoint(Material.circuits, "pickaxe", 3, 20f, 5f).setUnlocalizedName(Strings.SavePoint).setCreativeTab(tabKingdomKeysBlocks);
		MagnetBlox = new BlockMagnetBlox(Material.iron, "pickaxe", 0, 1f, 10f).setUnlocalizedName(Strings.MagnetBlox).setCreativeTab(tabKingdomKeysBlocks);

	}

	public static void registerBlock (Block block, String name) {
		GameRegistry.register(block, new ResourceLocation(Reference.MODID, name));
		GameRegistry.register(new ItemBlock(block), new ResourceLocation(Reference.MODID, name));
	}

	public static void register () {
		registerBlock(NormalBlox, Strings.NormalBlox);
		registerBlock(HardBlox, Strings.HardBlox);
		registerBlock(MetalBlox, Strings.MetalBlox);
		registerBlock(DangerBlox, Strings.DangerBlox);
		registerBlock(BounceBlox, Strings.BounceBlox);
		registerBlock(BlastBlox, Strings.BlastBlox);
		registerBlock(PrizeBlox, Strings.PrizeBlox);
		registerBlock(RarePrizeBlox, Strings.RarePrizeBlox);
		registerBlock(GhostBlox, Strings.GhostBlox);
		registerBlock(BlazingOre, Strings.BlazingOre);
		registerBlock(BrightOre, Strings.BrightOre);
		registerBlock(DarkOre, Strings.DarkOre);
		registerBlock(DarkOreE, Strings.DarkOreE);
		registerBlock(DenseOre, Strings.DenseOre);
		registerBlock(EnergyOre, Strings.EnergyOre);
		registerBlock(FrostOre, Strings.FrostOre);
		registerBlock(LightningOre, Strings.LightningOre);
		registerBlock(LucidOre, Strings.LucidOre);
		registerBlock(PowerOre, Strings.PowerOre);
		registerBlock(PowerOreE, Strings.PowerOreE);
		registerBlock(RemembranceOre, Strings.RemembranceOre);
		registerBlock(SerenityOre, Strings.SerenityOre);
		registerBlock(StormyOre, Strings.StormyOre);
		registerBlock(TranquilOre, Strings.TranquilOre);
		registerBlock(TwilightOre, Strings.TwilightOre);
		registerBlock(SynthesisTable, Strings.SynthesisTable);
		registerBlock(KKChest, Strings.KKChest);
		registerBlock(SavePoint, Strings.SavePoint);
		registerBlock(MagnetBlox, Strings.MagnetBlox);

	}

	public static void registerRenders () {
		Item item = Item.getItemFromBlock(GhostBlox);
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(Reference.MODID + ":" + item.getUnlocalizedName().substring(5), "visible=0"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 1, new ModelResourceLocation(Reference.MODID + ":" + item.getUnlocalizedName().substring(5), "visible=1"));
		registerRender(NormalBlox);
		registerRender(HardBlox);
		registerRender(MetalBlox);
		registerRender(DangerBlox);
		registerRender(BounceBlox);
		registerRender(BlastBlox);
		registerRender(PrizeBlox);
		registerRender(RarePrizeBlox);
		registerRender(GhostBlox);
		registerRender(BlazingOre);
		registerRender(BrightOre);
		registerRender(DarkOre);
		registerRender(DarkOreE);
		registerRender(DenseOre);
		registerRender(EnergyOre);
		registerRender(FrostOre);
		registerRender(LightningOre);
		registerRender(LucidOre);
		registerRender(PowerOre);
		registerRender(PowerOreE);
		registerRender(RemembranceOre);
		registerRender(SerenityOre);
		registerRender(StormyOre);
		registerRender(TranquilOre);
		registerRender(TwilightOre);
		registerRender(SynthesisTable);
		registerRender(KKChest);
		registerRender(SavePoint);
		registerRender(MagnetBlox);

	}

	public static void registerRender (Block block) {
		Item item = Item.getItemFromBlock(block);
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(Reference.MODID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
}
