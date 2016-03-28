package uk.co.wehavecookies56.kk.common.core.proxy;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import uk.co.wehavecookies56.kk.api.driveforms.DriveFormRegistry;
import uk.co.wehavecookies56.kk.api.materials.MaterialRegistry;
import uk.co.wehavecookies56.kk.api.recipes.RecipeRegistry;
import uk.co.wehavecookies56.kk.common.KingdomKeys;
import uk.co.wehavecookies56.kk.common.core.handler.ConfigHandler;
import uk.co.wehavecookies56.kk.common.core.handler.EventHandler;
import uk.co.wehavecookies56.kk.common.core.handler.GuiHandler;
import uk.co.wehavecookies56.kk.common.achievement.ModAchievements;
import uk.co.wehavecookies56.kk.common.block.ModBlocks;
import uk.co.wehavecookies56.kk.common.crafting.ModBlocksRecipes;
import uk.co.wehavecookies56.kk.common.capability.ModCapabilities;
import uk.co.wehavecookies56.kk.common.driveform.ModDriveForms;
import uk.co.wehavecookies56.kk.common.entity.PlayerLevel;
import uk.co.wehavecookies56.kk.common.block.tile.TileEntityKKChest;
import uk.co.wehavecookies56.kk.common.block.tile.TileEntitySynthesisTable;
import uk.co.wehavecookies56.kk.common.entity.block.EntityBlastBlox;
import uk.co.wehavecookies56.kk.common.entity.magic.EntityFire;
import uk.co.wehavecookies56.kk.common.entity.magic.EntityThunder;
import uk.co.wehavecookies56.kk.common.item.ModItems;
import uk.co.wehavecookies56.kk.common.crafting.ModItemsRecipes;
import uk.co.wehavecookies56.kk.common.lib.Constants;
import uk.co.wehavecookies56.kk.common.lib.Lists;
import uk.co.wehavecookies56.kk.common.lib.Reference;
import uk.co.wehavecookies56.kk.common.synthesis.ModSynthesisMaterials;
import uk.co.wehavecookies56.kk.common.network.UpdateChecker;
import uk.co.wehavecookies56.kk.common.network.packet.PacketDispatcher;
import uk.co.wehavecookies56.kk.common.synthesis.ModSynthesisRecipes;
import uk.co.wehavecookies56.kk.common.core.handler.FuelHandler;
import uk.co.wehavecookies56.kk.common.crafting.KKOreDictionary;
import uk.co.wehavecookies56.kk.common.core.helper.LogHelper;
import uk.co.wehavecookies56.kk.common.world.ChestGen;
import uk.co.wehavecookies56.kk.common.world.WorldGenBlox;

public class CommonProxy {

	public void preInit (FMLPreInitializationEvent event) {
		// Display mod info in console
		LogHelper.info("You are running " + Reference.MODNAME + " version " + Reference.MODVER + " for Minecraft " + Reference.MCVER);

		// ConfigHandler
		ConfigHandler.init(new File(event.getModConfigurationDirectory().getPath() + File.separator + Reference.MODID + File.separator + "MainConfig.cfg"));
		LogHelper.info("Configuration loaded");

		// World generation
		GameRegistry.registerWorldGenerator(new WorldGenBlox(), 2);
		LogHelper.info("World generation loaded");

		// Packets
		PacketDispatcher.registerPackets();
		LogHelper.info("Packets loaded");

		// Items
		ModItems.init();
		ModItems.register();
		LogHelper.info("Items loaded");

		// Blocks
		ModBlocks.init();
		ModBlocks.register();
		LogHelper.info("Blocks loaded");

		ModCapabilities.registerCapabilities();

	}

	public void init (FMLInitializationEvent event) {
		// Instance
		MinecraftForge.EVENT_BUS.register(KingdomKeys.instance);

		//	ModSounds.init();
		//LogHelper.info("Sounds loaded");

		// Update checker
		MinecraftForge.EVENT_BUS.register(new UpdateChecker());
		LogHelper.info("Update checker loaded");

		// Crafting recipe
		ModItemsRecipes.init();
		ModBlocksRecipes.init();
		LogHelper.info("Crafting recipe loaded");

		// Fuel Handler
		GameRegistry.registerFuelHandler(new FuelHandler());
		LogHelper.info("Fuel handler loaded");
		registerAchievements();

		// Register renders
		if(Loader.isModLoaded("RenderPlayerAPI")){
			LogHelper.info("RenderPlayerAPI installed everything will be fine");
		}else{
			//FMLLog.bigWarning("RENDERPLAYERAPI NOT INSTALLED THIS IS GOING TO CRASH", Reference.MODID);
		}
		LogHelper.info("Renders loaded");

		// Tile entity registry
		GameRegistry.registerTileEntity(TileEntitySynthesisTable.class, "synthesistable");
		GameRegistry.registerTileEntity(TileEntityKKChest.class, "kkchest");
		LogHelper.info("Tile entity loaded");

		// Proxy used as Gui handler
		NetworkRegistry.INSTANCE.registerGuiHandler(KingdomKeys.instance, new GuiHandler());

		EntityRegistry.registerModEntity(EntityBlastBlox.class, "blastblox", 1001, KingdomKeys.instance, 16, 1, false);
		EntityRegistry.registerModEntity(EntityFire.class, "fire", 1002, KingdomKeys.instance, 16, 1, false);
		EntityRegistry.registerModEntity(EntityThunder.class, "thunder", 1003, KingdomKeys.instance, 16, 1, false);

		Lists.init();

		// Drive forms init
		ModDriveForms.init();
		LogHelper.info(DriveFormRegistry.getDriveFormMap().size() + " Drive form(s) loaded");

		// Synthesis Recipes init
		ModSynthesisRecipes.init();
		LogHelper.info(RecipeRegistry.getRecipeMap().size() + " Synthesis recipe(s) loaded");

		ModSynthesisMaterials.init();
		LogHelper.info(MaterialRegistry.getMaterialMap().size() + " Material(s) loaded");

		Constants.registerCosts();
		Constants.registerMagicLevels();

		// Chest loot init
		ChestGen.init();
		LogHelper.info("Chest loot loaded");
	}

	public void postInit (FMLPostInitializationEvent event) {
		new PlayerLevel();

		// Event handler
		MinecraftForge.EVENT_BUS.register(new EventHandler());
		LogHelper.info("Events loaded");

		//Ore Dictionary registry
		KKOreDictionary.registerOres();
		LogHelper.info("Registered Ores");
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

	public void registerAchievements () {
		// Achievements
		ModAchievements.init();
		ModAchievements.register();
		LogHelper.info("Achievements loaded");
	}

}
