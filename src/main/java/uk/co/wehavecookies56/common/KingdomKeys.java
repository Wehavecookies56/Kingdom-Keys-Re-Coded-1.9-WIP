package uk.co.wehavecookies56.common;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import uk.co.wehavecookies56.common.core.handler.ConfigHandler;
import uk.co.wehavecookies56.common.capability.CheatModeCapability.ICheatMode;
import uk.co.wehavecookies56.common.capability.DriveStateCapability.IDriveState;
import uk.co.wehavecookies56.common.capability.FirstTimeJoinCapability.IFirstTimeJoin;
import uk.co.wehavecookies56.common.capability.MagicStateCapability.IMagicState;
import uk.co.wehavecookies56.common.capability.MunnyCapability.IMunny;
import uk.co.wehavecookies56.common.capability.PlayerStatsCapability.IPlayerStats;
import uk.co.wehavecookies56.common.capability.SummonKeybladeCapability.ISummonKeyblade;
import uk.co.wehavecookies56.common.capability.SynthesisMaterialCapability.ISynthesisMaterial;
import uk.co.wehavecookies56.common.capability.SynthesisRecipeCapability.ISynthesisRecipe;
import uk.co.wehavecookies56.common.lib.Reference;
import uk.co.wehavecookies56.client.core.proxy.ClientProxy;
import uk.co.wehavecookies56.common.core.proxy.CommonProxy;
import uk.co.wehavecookies56.common.core.command.CommandCheatMode;
import uk.co.wehavecookies56.common.core.command.CommandDriveLevel;
import uk.co.wehavecookies56.common.core.command.CommandGiveMunny;
import uk.co.wehavecookies56.common.core.command.CommandLearnRecipe;
import uk.co.wehavecookies56.common.core.command.CommandLevelUp;
import uk.co.wehavecookies56.common.core.command.CommandRemoveKeychain;
import uk.co.wehavecookies56.common.core.command.CommandResetLevel;
import uk.co.wehavecookies56.common.core.helper.LogHelper;

@Mod (name = Reference.MODNAME, modid = Reference.MODID, version = Reference.MODVER, guiFactory = Reference.GUIFACTORY, modLanguage = "java")
public class KingdomKeys {

	@SidedProxy (clientSide = Reference.CLIENTPROXY, serverSide = Reference.COMMONPROXY)
	public static CommonProxy proxy;

	@Mod.Instance (Reference.MODID)
	public static KingdomKeys instance;

	@EventHandler
	public void preInit (FMLPreInitializationEvent e) { proxy.preInit(e); }

	@EventHandler
	public void init (FMLInitializationEvent e) { proxy.init(e); }

	@EventHandler
	public void postInit (FMLPostInitializationEvent e) { proxy.postInit(e); }

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
