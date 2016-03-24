package wehavecookies56.kk;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import wehavecookies56.kk.api.driveforms.DriveFormRegistry;
import wehavecookies56.kk.api.materials.MaterialRegistry;
import wehavecookies56.kk.api.recipes.RecipeRegistry;
import wehavecookies56.kk.block.ModBlocks;
import wehavecookies56.kk.block.ModBlocksRecipes;
import wehavecookies56.kk.capabilities.CheatModeCapability.ICheatMode;
import wehavecookies56.kk.capabilities.DriveStateCapability.IDriveState;
import wehavecookies56.kk.capabilities.FirstTimeJoinCapability.IFirstTimeJoin;
import wehavecookies56.kk.capabilities.MagicStateCapability.IMagicState;
import wehavecookies56.kk.capabilities.ModCapabilities;
import wehavecookies56.kk.capabilities.MunnyCapability.IMunny;
import wehavecookies56.kk.capabilities.PlayerStatsCapability.IPlayerStats;
import wehavecookies56.kk.capabilities.SummonKeybladeCapability.ISummonKeyblade;
import wehavecookies56.kk.capabilities.SynthesisMaterialCapability.ISynthesisMaterial;
import wehavecookies56.kk.capabilities.SynthesisRecipeCapability.ISynthesisRecipe;
import wehavecookies56.kk.client.audio.ModSounds;
import wehavecookies56.kk.driveforms.ModDriveForms;
import wehavecookies56.kk.entities.PlayerLevel;
import wehavecookies56.kk.entities.TileEntityKKChest;
import wehavecookies56.kk.entities.TileEntitySynthesisTable;
import wehavecookies56.kk.entities.block.EntityBlastBlox;
import wehavecookies56.kk.entities.magic.EntityFire;
import wehavecookies56.kk.entities.magic.EntityThunder;
import wehavecookies56.kk.item.ModItems;
import wehavecookies56.kk.item.ModItemsRecipes;
import wehavecookies56.kk.lib.Config;
import wehavecookies56.kk.lib.Constants;
import wehavecookies56.kk.lib.Lists;
import wehavecookies56.kk.lib.Reference;
import wehavecookies56.kk.materials.ModMaterials;
import wehavecookies56.kk.network.ClientProxy;
import wehavecookies56.kk.network.CommonProxy;
import wehavecookies56.kk.network.UpdateChecker;
import wehavecookies56.kk.network.packet.PacketDispatcher;
import wehavecookies56.kk.recipes.ModRecipes;
import wehavecookies56.kk.server.command.CommandCheatMode;
import wehavecookies56.kk.server.command.CommandDriveLevel;
import wehavecookies56.kk.server.command.CommandGiveMunny;
import wehavecookies56.kk.server.command.CommandLearnRecipe;
import wehavecookies56.kk.server.command.CommandLevelUp;
import wehavecookies56.kk.server.command.CommandRemoveKeychain;
import wehavecookies56.kk.server.command.CommandResetLevel;
import wehavecookies56.kk.util.FuelHandler;
import wehavecookies56.kk.util.KKOreDictionary;
import wehavecookies56.kk.util.LogHelper;
import wehavecookies56.kk.worldgen.ChestGen;
import wehavecookies56.kk.worldgen.WorldGenBlox;

@Mod (name = Reference.MODNAME, modid = Reference.MODID, version = Reference.MODVER, guiFactory = Reference.GUIFACTORY, modLanguage = "java")
public class KingdomKeys {

	@SidedProxy (clientSide = Reference.CLIENTPROXY, serverSide = Reference.COMMONPROXY)
	public static CommonProxy proxy;
	public static ClientProxy cproxy;

	public static Configuration config;

	private static int modGuiIndex = 0;
	public static final int GUI_KEYCHAIN_INV = modGuiIndex++;
	public static final int GUI_KKCHEST_INV = modGuiIndex++;
	public static final int GUI_ITEMS_PLAYER = modGuiIndex++;
	public static final int GUI_POTIONS_INV = modGuiIndex++;
	public static final int GUI_SPELLS_INV = modGuiIndex++;
	public static final int GUI_DRIVE_INV = modGuiIndex++;
	public static final int GUI_SYNTHESISBAGS_INV = modGuiIndex++;
	public static final int GUI_SYNTHESISBAGM_INV = modGuiIndex++;
	public static final int GUI_SYNTHESISBAGL_INV = modGuiIndex++;
	public static final int GUI_SYNTHESISTABLE = modGuiIndex++;

	@Mod.Instance (Reference.MODID)
	public static KingdomKeys instance;
	
	@CapabilityInject(IMunny.class)
    public static final Capability<IMunny> MUNNY = null;
	@CapabilityInject(IPlayerStats.class)
    public static final Capability<IPlayerStats> PLAYER_STATS = null;
	@CapabilityInject(ISummonKeyblade.class)
    public static final Capability<ISummonKeyblade> SUMMON_KEYBLADE = null;
	@CapabilityInject(IMagicState.class)
    public static final Capability<IMagicState> MAGIC_STATE = null;
	@CapabilityInject(IDriveState.class)
    public static final Capability<IDriveState> DRIVE_STATE = null;
	@CapabilityInject(IFirstTimeJoin.class)
    public static final Capability<IFirstTimeJoin> FIRST_TIME_JOIN = null;
	@CapabilityInject(ISynthesisRecipe.class)
    public static final Capability<ISynthesisRecipe> SYNTHESIS_RECIPES = null;
	@CapabilityInject(ISynthesisMaterial.class)
	public static final Capability<ISynthesisMaterial> SYNTHESIS_MATERIALS = null;
	@CapabilityInject(ICheatMode.class)
	public static final Capability<ICheatMode> CHEAT_MODE = null;

	@EventHandler
	public void preInit (FMLPreInitializationEvent e) {
		// Display mod info in console
		LogHelper.info("You are running " + Reference.MODNAME + " version " + Reference.MODVER + " for Minecraft " + Reference.MCVER);

		
		// Config
		config = new Configuration(e.getSuggestedConfigurationFile());
		Config.syncConfig();
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
		
		proxy.preInit();

	}

	// Sync config when changed
	@SubscribeEvent
	public void OnConfigChanged (ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID().equals(Reference.MODID)) Config.syncConfig();
	}

	@EventHandler
	public void init (FMLInitializationEvent e) {
		// Instance
		MinecraftForge.EVENT_BUS.register(instance);

		//	ModSounds.init();
		//LogHelper.info("Sounds loaded");
		
		// Update checker
		MinecraftForge.EVENT_BUS.register(new UpdateChecker());
		LogHelper.info("Update checker loaded");
		
		// Crafting recipes
		ModItemsRecipes.init();
		ModBlocksRecipes.init();
		LogHelper.info("Crafting recipes loaded");

		// Fuel Handler
		GameRegistry.registerFuelHandler(new FuelHandler());
		LogHelper.info("Fuel handler loaded");
		proxy.registerAchievements();

		// Register renders
		if(Loader.isModLoaded("RenderPlayerAPI")){
			LogHelper.info("RenderPlayerAPI installed everything will be fine");
		}else{
			FMLLog.bigWarning("RENDERPLAYERAPI NOT INSTALLED THIS IS GOING TO CRASH", Reference.MODID);
		}
		proxy.init();
		LogHelper.info("Renders loaded");

		// Tile entity registry
		GameRegistry.registerTileEntity(TileEntitySynthesisTable.class, "synthesistable");
		GameRegistry.registerTileEntity(TileEntityKKChest.class, "kkchest");
		LogHelper.info("Tile entities loaded");

		// Proxy used as Gui handler
		NetworkRegistry.INSTANCE.registerGuiHandler(this, proxy);

		EntityRegistry.registerModEntity(EntityBlastBlox.class, "blastblox", 0, instance, 16, 1, false);
		EntityRegistry.registerModEntity(EntityFire.class, "fire", 1, instance, 16, 1, false);
		EntityRegistry.registerModEntity(EntityThunder.class, "thunder", 2, instance, 16, 1, false);

		Lists.init();

		// Drive forms init
		ModDriveForms.init();
		LogHelper.info(DriveFormRegistry.getDriveFormMap().size() + " Drive form(s) loaded");

		// Synthesis Recipes init
		ModRecipes.init();
		LogHelper.info(RecipeRegistry.getRecipeMap().size() + " Synthesis recipe(s) loaded");

		ModMaterials.init();
		LogHelper.info(MaterialRegistry.getMaterialMap().size() + " Material(s) loaded");
		
		Constants.registerCosts();
		Constants.registerMagicLevels();

		// Chest loot init
		ChestGen.init();
		LogHelper.info("Chest loot loaded");

	}

	@EventHandler
	public void postInit (FMLPostInitializationEvent e) {

		new PlayerLevel();

		// Event handler
		MinecraftForge.EVENT_BUS.register(new wehavecookies56.kk.util.EventHandler());
		LogHelper.info("Events loaded");
		
		//Ore Dictionary registry
		KKOreDictionary.registerOres();
		LogHelper.info("Registered Ores");
	}

	@EventHandler
	public void serverStart (FMLServerStartingEvent e) {
		e.registerServerCommand(new CommandLearnRecipe());
		e.registerServerCommand(new CommandGiveMunny());
		e.registerServerCommand(new CommandCheatMode());
		e.registerServerCommand(new CommandRemoveKeychain());
		e.registerServerCommand(new CommandResetLevel());
		e.registerServerCommand(new CommandLevelUp());
		e.registerServerCommand(new CommandDriveLevel());

		LogHelper.info("Commands loaded");
	}

}
