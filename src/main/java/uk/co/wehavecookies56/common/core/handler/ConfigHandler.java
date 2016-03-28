package uk.co.wehavecookies56.common.core.handler;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import uk.co.wehavecookies56.common.core.helper.TextHelper;
import uk.co.wehavecookies56.common.lib.Reference;
import uk.co.wehavecookies56.common.lib.Strings;

import static uk.co.wehavecookies56.common.lib.Strings.*;

import java.io.File;

public class ConfigHandler {

	public static Configuration config;

	public static String[] items;

	public static boolean
			EnableWorldGen = true,
			EnableUpdateCheck = true,
			EnableHeartsOnHUD = false,
			EnableCustomMusic = true,
			ForceEnableCustomMusic = false;

	public static String TM_Interdiction, TM_Sharpshooter, TM_Lindworm, TM_FrozenPride, TM_Skysplitter, TM_BookofRetribution, TM_Lunatic, TM_EternalFlames, TM_Arpeggio, TM_FairGame, TM_GracefulDahlia, TM_Foudre, TM_BlazeofGlory, TM_Prometheus, TM_Ifrit;

	public static String TM_KingdomKey, TM_Oathkeeper, TM_Oblivion, TM_UltimaWeaponKH1, TM_KingdomKeyD, TM_KeybladeofPeoplesHearts, TM_LadyLuck, TM_Olympia, TM_JungleKing, TM_ThreeWishes, TM_Crabclaw, TM_FairyHarp, TM_DivineRose, TM_Spellbinder, TM_MetalChocobo, TM_Lionheart, TM_DiamondDust, TM_OneWingedAngel, TM_SoulEater, TM_Pumpkinhead, TM_WishingStar, TM_BondofFlame, TM_FollowtheWind, TM_HiddenDragon, TM_Monochrome, TM_PhotonDebugger, TM_StarSeeker, TM_MaverickFlare, TM_TotalEclipse, TM_MidnightRoar, TM_TwoBecomeOne, TM_UltimaWeaponKH2, TM_WaytotheDawn, TM_DestinysEmbrace, TM_Stormfall, TM_EndsoftheEarth, TM_RumblingRose, TM_HerosCrest, TM_MysteriousAbyss, TM_WishingLamp, TM_DecisivePumpkin, TM_CircleofLife, TM_SweetMemories, TM_GullWing, TM_GuardianSoul, TM_SleepingLion, TM_Fenrir, TM_FatalCrest, TM_WinnersProof, TM_MissingAche, TM_OminousBlight, TM_AbaddonPlasma, TM_PainofSolitude, TM_SignofInnocence, TM_CrownofGuilt, TM_AbyssalTide, TM_Leviathan, TM_TrueLightsFlight, TM_RejectionofFate, TM_GlimpseofDarkness, TM_SilentDirge, TM_LunarEclipse, TM_DarkerThanDark, TM_AstralBlast, TM_TwilightBlaze, TM_OmegaWeapon, TM_Umbrella, TM_Aubade, TM_WoodenStick, TM_DreamSword, TM_ZeroOne, TM_Earthshaker, TM_Darkgnaw, TM_ChaosRipper, TM_MasterXehanortsKeyblade, TM_TreasureTrove, TM_StrokeofMidnight, TM_FairyStars, TM_VictoryLine, TM_MarkofaHero, TM_Hyperdrive, TM_PixiePetal, TM_Sweetstack, TM_UltimaWeaponBBS, TM_VoidGear, TM_NoName, TM_RoyalRadiance, TM_Rainfell, TM_Brightcrest, TM_MastersDefender, TM_WaywardWind, TM_FrolicFlame, TM_LostMemory, TM_Kiblade, TM_IncompleteKiblade, TM_WoodenKeyblade, TM_SkullNoise, TM_GuardianBell, TM_DualDisc, TM_FerrisGear, TM_KnockoutPunch, TM_AllforOne, TM_Counterpoint, TM_Divewing, TM_SweetDreams, TM_UltimaWeaponDDD, TM_Unbound, TM_EndofPain, TM_OceansRage, TM_LeasKeyblade, TM_YoungXehanortsKeyblade, TM_MirageSplit, TM_NightmaresEnd, TM_NightmaresEndandMirageSplit, TM_Starlight, TM_AnguisForetellersKeyblade, TM_LeopardosForetellersKeyblade, TM_UnicornisForetellersKeyblade, TM_UrsusForetellersKeyblade, TM_VulpeusForetellersKeyblade;

	public static String[] A_TM_Interdiction, A_TM_Sharpshooter, A_TM_Lindworm, A_TM_FrozenPride, A_TM_Skysplitter, A_TM_BookofRetribution, A_TM_Lunatic, A_TM_EternalFlames, A_TM_Arpeggio, A_TM_FairGame, A_TM_GracefulDahlia, A_TM_Foudre, A_TM_BlazeofGlory, A_TM_Prometheus, A_TM_Ifrit;

	public static String[] A_TM_AbaddonPlasma, A_TM_AbyssalTide, A_TM_AllforOne, A_TM_AnguisForetellersKeyblade, A_TM_AstralBlast, A_TM_Aubade, A_TM_BondofFlame, A_TM_Brightcrest, A_TM_ChaosRipper, A_TM_CircleofLife, A_TM_Counterpoint, A_TM_Crabclaw, A_TM_CrownofGuilt, A_TM_DarkerThanDark, A_TM_Darkgnaw, A_TM_DecisivePumpkin, A_TM_DestinysEmbrace, A_TM_DiamondDust, A_TM_Divewing, A_TM_DivineRose, A_TM_DreamSword, A_TM_DualDisc, A_TM_Earthshaker, A_TM_EndofPain, A_TM_EndsoftheEarth, A_TM_FairyHarp, A_TM_FairyStars, A_TM_FatalCrest, A_TM_Fenrir, A_TM_FerrisGear, A_TM_FollowtheWind, A_TM_FrolicFlame, A_TM_GlimpseofDarkness, A_TM_GuardianBell, A_TM_GuardianSoul, A_TM_GullWing, A_TM_HerosCrest, A_TM_HiddenDragon, A_TM_Hyperdrive, A_TM_IncompleteKiblade, A_TM_JungleKing, A_TM_KeybladeofPeoplesHearts, A_TM_Kiblade, A_TM_KingdomKey, A_TM_KingdomKeyD, A_TM_KnockoutPunch, A_TM_LadyLuck, A_TM_LeasKeyblade, A_TM_LeopardosForetellersKeyblade, A_TM_Leviathan, A_TM_Lionheart, A_TM_LostMemory, A_TM_LunarEclipse, A_TM_MarkofaHero, A_TM_MasterXehanortsKeyblade, A_TM_MastersDefender, A_TM_MaverickFlare, A_TM_MetalChocobo, A_TM_MidnightRoar, A_TM_MirageSplit, A_TM_MissingAche, A_TM_Monochrome, A_TM_MysteriousAbyss, A_TM_NightmaresEnd, A_TM_NightmaresEndandMirageSplit, A_TM_NoName, A_TM_Oathkeeper, A_TM_Oblivion, A_TM_OceansRage, A_TM_Olympia, A_TM_OmegaWeapon, A_TM_OminousBlight, A_TM_OneWingedAngel, A_TM_PainofSolitude, A_TM_PhotonDebugger, A_TM_PixiePetal, A_TM_Pumpkinhead, A_TM_Rainfell, A_TM_RejectionofFate, A_TM_RoyalRadiance, A_TM_RumblingRose, A_TM_SignofInnocence, A_TM_SilentDirge, A_TM_SkullNoise, A_TM_SleepingLion, A_TM_SoulEater, A_TM_Spellbinder, A_TM_StarSeeker, A_TM_Starlight, A_TM_Stormfall, A_TM_StrokeofMidnight, A_TM_SweetDreams, A_TM_SweetMemories, A_TM_Sweetstack, A_TM_ThreeWishes, A_TM_TotalEclipse, A_TM_TreasureTrove, A_TM_TrueLightsFlight, A_TM_TwilightBlaze, A_TM_TwoBecomeOne, A_TM_UltimaWeaponKH1, A_TM_UltimaWeaponKH2, A_TM_UltimaWeaponBBS, A_TM_UltimaWeaponDDD, A_TM_Umbrella, A_TM_Unbound, A_TM_UnicornisForetellersKeyblade, A_TM_UrsusForetellersKeyblade, A_TM_VictoryLine, A_TM_VoidGear, A_TM_VulpeusForetellersKeyblade, A_TM_WaytotheDawn, A_TM_WaywardWind, A_TM_WinnersProof, A_TM_WishingLamp, A_TM_WishingStar, A_TM_WoodenKeyblade, A_TM_WoodenStick, A_TM_YoungXehanortsKeyblade, A_TM_ZeroOne;

	public static int[] interfaceColour = new int[] { 255, 0, 0 };
	public static Property interfaceColourProperty, EnableHeartsOnHUDProperty, EnableCustomMusicProperty;


	public static void init(File file) {
		config = new Configuration(file);
		config.load();
		load();

		MinecraftForge.EVENT_BUS.register(new ConfigHandler());
	}


	public static void load() {

		final String WORLDGEN = propertyCategory("worldgen", Configuration.CATEGORY_GENERAL);
		final String NETWORK = propertyCategory("network", Configuration.CATEGORY_GENERAL);
		final String INTERFACE = propertyCategory("interface", Configuration.CATEGORY_CLIENT);
		final String SOUND = propertyCategory("sound", Configuration.CATEGORY_CLIENT);
		final String ITEMS = propertyCategory("items", Configuration.CATEGORY_GENERAL);
		final String TOOLMATERIALS = propertyCategory("toolmaterials", ITEMS);

		//WORLDGEN
		EnableWorldGen = configProperty("Enable World Gen", "Toggles all world generation performed by this mod", EnableWorldGen, WORLDGEN);

		//NETWORK
		EnableUpdateCheck = configProperty("Enable Update Checking", "Toggles whether the update checker checks for updates", EnableUpdateCheck, NETWORK);

		//INTERFACE
		EnableHeartsOnHUD = configProperty("Enable hearts on HUD", "Toggles rendering of hearts on the HUD", EnableHeartsOnHUD, INTERFACE);
		interfaceColourProperty = configProperty("Interface colour", "Set the colour of the interface with RGB values", interfaceColour, INTERFACE);
		interfaceColour = interfaceColourProperty.getIntList();

		//SOUND
		EnableCustomMusic = configProperty("Enable custom music", "Toggles the custom music that plays, requires the music resource pack", EnableCustomMusic, SOUND);
		ForceEnableCustomMusic = configProperty("Force Enable custom music", "Force toggles the custom music that plays regardless of whether the resource pack is loaded", ForceEnableCustomMusic, SOUND);

		//ITEMS
		//TOOLMATERIALS
		config.addCustomCategoryComment(TOOLMATERIALS, "The tool material for items which use them, A space after commas is required. \nParameters {(String) NAME, (int) HARVESTLEVEL, (int) MAXUSES, (float) EFFICIENCY, (float) DAMAGE, (int) ENCHANTABILITY}");

		TM_Interdiction = configProperty(Strings.TM_Interdiction_NAME, "", TM_Interdiction_DEFAULT, TOOLMATERIALS);
		TM_Sharpshooter = configProperty(Strings.TM_Sharpshooter_NAME, "", TM_Sharpshooter_DEFAULT, TOOLMATERIALS);
		TM_Lindworm = configProperty(Strings.TM_Lindworm_NAME, "", TM_Lindworm_DEFAULT, TOOLMATERIALS);
		TM_FrozenPride = configProperty(Strings.TM_FrozenPride_NAME, "", TM_FrozenPride_DEFAULT, TOOLMATERIALS);
		TM_Skysplitter = configProperty(Strings.TM_Skysplitter_NAME, "", TM_Skysplitter_DEFAULT, TOOLMATERIALS);
		TM_BookofRetribution = configProperty(Strings.TM_BookofRetribution_NAME, "", TM_BookofRetribution_DEFAULT, TOOLMATERIALS);
		TM_Lunatic = configProperty(Strings.TM_Lunatic_NAME, "", TM_Lunatic_DEFAULT, TOOLMATERIALS);
		TM_EternalFlames = configProperty(Strings.TM_EternalFlames_NAME, "", TM_EternalFlames_DEFAULT, TOOLMATERIALS);
		TM_Arpeggio = configProperty(Strings.TM_Arpeggio_NAME, "", TM_Arpeggio_DEFAULT, TOOLMATERIALS);
		TM_FairGame = configProperty(Strings.TM_FairGame_NAME, "", TM_FairGame_DEFAULT, TOOLMATERIALS);
		TM_GracefulDahlia = configProperty(Strings.TM_GracefulDahlia_NAME, "", TM_GracefulDahlia_DEFAULT, TOOLMATERIALS);
		TM_Foudre = configProperty(Strings.TM_Foudre_NAME, "", TM_Foudre_DEFAULT, TOOLMATERIALS);
		TM_BlazeofGlory = configProperty(Strings.TM_BlazeofGlory_NAME, "", TM_BlazeofGlory_DEFAULT, TOOLMATERIALS);
		TM_Prometheus = configProperty(Strings.TM_Prometheus_NAME, "", TM_Prometheus_DEFAULT, TOOLMATERIALS);
		TM_Ifrit = configProperty(Strings.TM_Ifrit_NAME, "", TM_Ifrit_DEFAULT, TOOLMATERIALS);

		TM_AbaddonPlasma = configProperty(Strings.TM_AbaddonPlasma_NAME, "", TM_AbaddonPlasma_DEFAULT, TOOLMATERIALS);
		TM_AbyssalTide = configProperty(TM_AbyssalTide_NAME, "", TM_AbyssalTide_DEFAULT, TOOLMATERIALS);
		TM_AllforOne = configProperty(TM_AllforOne_NAME, "", TM_AllforOne_DEFAULT, TOOLMATERIALS);
		TM_AnguisForetellersKeyblade = configProperty(TM_AnguisForetellersKeyblade_NAME, "", TM_AnguisForetellersKeyblade_DEFAULT, TOOLMATERIALS);
		TM_AstralBlast = configProperty(TM_AstralBlast_NAME, "", TM_AstralBlast_DEFAULT, TOOLMATERIALS);
		TM_Aubade = configProperty(TM_Aubade_NAME, "", TM_Aubade_DEFAULT, TOOLMATERIALS);
		TM_BondofFlame = configProperty(TM_BondofFlame_NAME, "", TM_BondofFlame_DEFAULT, TOOLMATERIALS);
		TM_Brightcrest = configProperty(TM_Brightcrest_NAME, "", TM_Brightcrest_DEFAULT, TOOLMATERIALS);
		TM_ChaosRipper = configProperty(TM_ChaosRipper_NAME, "", TM_ChaosRipper_DEFAULT, TOOLMATERIALS);
		TM_CircleofLife = configProperty(TM_CircleofLife_NAME, "", TM_CircleofLife_DEFAULT, TOOLMATERIALS);
		TM_Counterpoint = configProperty(TM_Counterpoint_NAME, "", TM_Counterpoint_DEFAULT, TOOLMATERIALS);
		TM_Crabclaw = configProperty(TM_Crabclaw_NAME, "", TM_Crabclaw_DEFAULT, TOOLMATERIALS);
		TM_CrownofGuilt = configProperty(TM_CrownofGuilt_NAME, "", TM_CrownofGuilt_DEFAULT, TOOLMATERIALS);
		TM_DarkerThanDark = configProperty(TM_DarkerThanDark_NAME, "", TM_DarkerThanDark_DEFAULT, TOOLMATERIALS);
		TM_Darkgnaw = configProperty(TM_Darkgnaw_NAME, "", TM_Darkgnaw_DEFAULT, TOOLMATERIALS);
		TM_DecisivePumpkin = configProperty(TM_DecisivePumpkin_NAME, "", TM_DecisivePumpkin_DEFAULT, TOOLMATERIALS);
		TM_DestinysEmbrace = configProperty(TM_DestinysEmbrace_NAME, "", TM_DestinysEmbrace_DEFAULT, TOOLMATERIALS);
		TM_DiamondDust = configProperty(TM_DiamondDust_NAME, "", TM_DiamondDust_DEFAULT, TOOLMATERIALS);
		TM_Divewing = configProperty(TM_Divewing_NAME, "", TM_Divewing_DEFAULT, TOOLMATERIALS);
		TM_DivineRose = configProperty(TM_DivineRose_NAME, "", TM_DivineRose_DEFAULT, TOOLMATERIALS);
		TM_DreamSword = configProperty(TM_DreamSword_NAME, "", TM_DreamSword_DEFAULT, TOOLMATERIALS);
		TM_DualDisc = configProperty(TM_DualDisc_NAME, "", TM_DualDisc_DEFAULT, TOOLMATERIALS);
		TM_Earthshaker = configProperty(TM_Earthshaker_NAME, "", TM_Earthshaker_DEFAULT, TOOLMATERIALS);
		TM_EndofPain = configProperty(TM_EndofPain_NAME, "", TM_EndofPain_DEFAULT, TOOLMATERIALS);
		TM_EndsoftheEarth = configProperty(TM_EndsoftheEarth_NAME, "", TM_EndsoftheEarth_DEFAULT, TOOLMATERIALS);
		TM_FairyHarp = configProperty(TM_FairyHarp_NAME, "", TM_FairyHarp_DEFAULT, TOOLMATERIALS);
		TM_FairyStars = configProperty(TM_FairyStars_NAME, "", TM_FairyStars_DEFAULT, TOOLMATERIALS);
		TM_FatalCrest = configProperty(TM_FatalCrest_NAME, "", TM_FatalCrest_DEFAULT, TOOLMATERIALS);
		TM_Fenrir = configProperty(TM_Fenrir_NAME, "", TM_Fenrir_DEFAULT, TOOLMATERIALS);
		TM_FerrisGear = configProperty(TM_FerrisGear_NAME, "", TM_FerrisGear_DEFAULT, TOOLMATERIALS);
		TM_FollowtheWind = configProperty(TM_FollowtheWind_NAME, "", TM_FollowtheWind_DEFAULT, TOOLMATERIALS);
		TM_FrolicFlame = configProperty(TM_FrolicFlame_NAME, "", TM_FrolicFlame_DEFAULT, TOOLMATERIALS);
		TM_GlimpseofDarkness = configProperty(TM_GlimpseofDarkness_NAME, "", TM_GlimpseofDarkness_DEFAULT, TOOLMATERIALS);
		TM_GuardianBell = configProperty(TM_GuardianBell_NAME, "", TM_GuardianBell_DEFAULT, TOOLMATERIALS);
		TM_GuardianSoul = configProperty(TM_GuardianSoul_NAME, "", TM_GuardianSoul_DEFAULT, TOOLMATERIALS);
		TM_GullWing = configProperty(TM_GullWing_NAME, "", TM_GullWing_DEFAULT, TOOLMATERIALS);
		TM_HerosCrest = configProperty(TM_HerosCrest_NAME, "", TM_HerosCrest_DEFAULT, TOOLMATERIALS);
		TM_HiddenDragon = configProperty(TM_HiddenDragon_NAME, "", TM_HiddenDragon_DEFAULT, TOOLMATERIALS);
		TM_Hyperdrive = configProperty(TM_Hyperdrive_NAME, "", TM_Hyperdrive_DEFAULT, TOOLMATERIALS);
		TM_IncompleteKiblade = configProperty(TM_IncompleteKiblade_NAME, "", TM_IncompleteKiblade_DEFAULT, TOOLMATERIALS);
		TM_JungleKing = configProperty(TM_JungleKing_NAME, "", TM_JungleKing_DEFAULT, TOOLMATERIALS);
		TM_KeybladeofPeoplesHearts = configProperty(TM_KeybladeofPeoplesHearts_NAME, "", TM_KeybladeofPeoplesHearts_DEFAULT, TOOLMATERIALS);
		TM_Kiblade = configProperty(TM_Kiblade_NAME, "", TM_Kiblade_DEFAULT, TOOLMATERIALS);
		TM_KingdomKey = configProperty(TM_KingdomKey_NAME, "", TM_KingdomKey_DEFAULT, TOOLMATERIALS);
		TM_KingdomKeyD = configProperty(TM_KingdomKeyD_NAME, "", TM_KingdomKeyD_DEFAULT, TOOLMATERIALS);
		TM_KnockoutPunch = configProperty(TM_KnockoutPunch_NAME, "", TM_KnockoutPunch_DEFAULT, TOOLMATERIALS);
		TM_LadyLuck = configProperty(TM_LadyLuck_NAME, "", TM_LadyLuck_DEFAULT, TOOLMATERIALS);
		TM_LeasKeyblade = configProperty(TM_LeasKeyblade_NAME, "", TM_LeasKeyblade_DEFAULT, TOOLMATERIALS);
		TM_LeopardosForetellersKeyblade = configProperty(TM_LeopardosForetellersKeyblade_NAME, "", TM_LeopardosForetellersKeyblade_DEFAULT, TOOLMATERIALS);
		TM_Leviathan = configProperty(TM_Leviathan_NAME, "", TM_Leviathan_DEFAULT, TOOLMATERIALS);
		TM_Lionheart = configProperty(TM_Lionheart_NAME, "", TM_Lionheart_DEFAULT, TOOLMATERIALS);
		TM_LostMemory = configProperty(TM_LostMemory_NAME, "", TM_LostMemory_DEFAULT, TOOLMATERIALS);
		TM_LunarEclipse = configProperty(TM_LunarEclipse_NAME, "", TM_LunarEclipse_DEFAULT, TOOLMATERIALS);
		TM_MarkofaHero = configProperty(TM_MarkofaHero_NAME, "", TM_MarkofaHero_DEFAULT, TOOLMATERIALS);
		TM_MasterXehanortsKeyblade = configProperty(TM_MasterXehanortsKeyblade_NAME, "", TM_MasterXehanortsKeyblade_DEFAULT, TOOLMATERIALS);
		TM_MastersDefender = configProperty(TM_MastersDefender_NAME, "", TM_MastersDefender_DEFAULT, TOOLMATERIALS);
		TM_MaverickFlare = configProperty(TM_MaverickFlare_NAME, "", TM_MaverickFlare_DEFAULT, TOOLMATERIALS);
		TM_MetalChocobo = configProperty(TM_MetalChocobo_NAME, "", TM_MetalChocobo_DEFAULT, TOOLMATERIALS);
		TM_MidnightRoar = configProperty(TM_MidnightRoar_NAME, "", TM_MidnightRoar_DEFAULT, TOOLMATERIALS);
		TM_MirageSplit = configProperty(TM_MirageSplit_NAME, "", TM_MirageSplit_DEFAULT, TOOLMATERIALS);
		TM_MissingAche = configProperty(TM_MissingAche_NAME, "", TM_MissingAche_DEFAULT, TOOLMATERIALS);
		TM_Monochrome = configProperty(TM_Monochrome_NAME, "", TM_Monochrome_DEFAULT, TOOLMATERIALS);
		TM_MysteriousAbyss = configProperty(TM_MysteriousAbyss_NAME, "", TM_MysteriousAbyss_DEFAULT, TOOLMATERIALS);
		TM_NightmaresEnd = configProperty(TM_NightmaresEnd_NAME, "", TM_NightmaresEnd_DEFAULT, TOOLMATERIALS);
		TM_NightmaresEndandMirageSplit = configProperty(TM_NightmaresEndandMirageSplit_NAME, "", TM_NightmaresEndandMirageSplit_DEFAULT, TOOLMATERIALS);
		TM_NoName = configProperty(TM_NoName_NAME, "", TM_NoName_DEFAULT, TOOLMATERIALS);
		TM_Oathkeeper = configProperty(TM_Oathkeeper_NAME, "", TM_Oathkeeper_DEFAULT, TOOLMATERIALS);
		TM_Oblivion = configProperty(TM_Oblivion_NAME, "", TM_Oblivion_DEFAULT, TOOLMATERIALS);
		TM_OceansRage = configProperty(TM_OceansRage_NAME, "", TM_OceansRage_DEFAULT, TOOLMATERIALS);
		TM_Olympia = configProperty(TM_Olympia_NAME, "", TM_Olympia_DEFAULT, TOOLMATERIALS);
		TM_OmegaWeapon = configProperty(TM_OmegaWeapon_NAME, "", TM_OmegaWeapon_DEFAULT, TOOLMATERIALS);
		TM_OminousBlight = configProperty(TM_OminousBlight_NAME, "", TM_OminousBlight_DEFAULT, TOOLMATERIALS);
		TM_OneWingedAngel = configProperty(TM_OneWingedAngel_NAME, "", TM_OneWingedAngel_DEFAULT, TOOLMATERIALS);
		TM_PainofSolitude = configProperty(TM_PainofSolitude_NAME, "", TM_PainofSolitude_DEFAULT, TOOLMATERIALS);
		TM_PhotonDebugger = configProperty(TM_PhotonDebugger_NAME, "", TM_PhotonDebugger_DEFAULT, TOOLMATERIALS);
		TM_PixiePetal = configProperty(TM_PixiePetal_NAME, "", TM_PixiePetal_DEFAULT, TOOLMATERIALS);
		TM_Pumpkinhead = configProperty(TM_Pumpkinhead_NAME, "", TM_Pumpkinhead_DEFAULT, TOOLMATERIALS);
		TM_Rainfell = configProperty(TM_Rainfell_NAME, "", TM_Rainfell_DEFAULT, TOOLMATERIALS);
		TM_RejectionofFate = configProperty(TM_RejectionofFate_NAME, "", TM_RejectionofFate_DEFAULT, TOOLMATERIALS);
		TM_RoyalRadiance = configProperty(TM_RoyalRadiance_NAME, "", TM_RoyalRadiance_DEFAULT, TOOLMATERIALS);
		TM_RumblingRose = configProperty(TM_RumblingRose_NAME, "", TM_RumblingRose_DEFAULT, TOOLMATERIALS);
		TM_SignofInnocence = configProperty(TM_SignofInnocence_NAME, "", TM_SignofInnocence_DEFAULT, TOOLMATERIALS);
		TM_SilentDirge = configProperty(TM_SilentDirge_NAME, "", TM_SilentDirge_DEFAULT, TOOLMATERIALS);
		TM_SkullNoise = configProperty(TM_SkullNoise_NAME, "", TM_SkullNoise_DEFAULT, TOOLMATERIALS);
		TM_SleepingLion = configProperty(TM_SleepingLion_NAME, "", TM_SleepingLion_DEFAULT, TOOLMATERIALS);
		TM_SoulEater = configProperty(TM_SoulEater_NAME, "", TM_SoulEater_DEFAULT, TOOLMATERIALS);
		TM_Spellbinder = configProperty(TM_Spellbinder_NAME, "", TM_Spellbinder_DEFAULT, TOOLMATERIALS);
		TM_StarSeeker = configProperty(TM_StarSeeker_NAME, "", TM_StarSeeker_DEFAULT, TOOLMATERIALS);
		TM_Starlight = configProperty(TM_Starlight_NAME, "", TM_Starlight_DEFAULT, TOOLMATERIALS);
		TM_Stormfall = configProperty(TM_Stormfall_NAME, "", TM_Stormfall_DEFAULT, TOOLMATERIALS);
		TM_StrokeofMidnight = configProperty(TM_StrokeofMidnight_NAME, "", TM_StrokeofMidnight_DEFAULT, TOOLMATERIALS);
		TM_SweetDreams = configProperty(TM_SweetDreams_NAME, "", TM_SweetDreams_DEFAULT, TOOLMATERIALS);
		TM_SweetMemories = configProperty(TM_SweetMemories_NAME, "", TM_SweetMemories_DEFAULT, TOOLMATERIALS);
		TM_Sweetstack = configProperty(TM_Sweetstack_NAME, "", TM_Sweetstack_DEFAULT, TOOLMATERIALS);
		TM_ThreeWishes = configProperty(TM_ThreeWishes_NAME, "", TM_ThreeWishes_DEFAULT, TOOLMATERIALS);
		TM_TotalEclipse = configProperty(TM_TotalEclipse_NAME, "", TM_TotalEclipse_DEFAULT, TOOLMATERIALS);
		TM_TreasureTrove = configProperty(TM_TreasureTrove_NAME, "", TM_TreasureTrove_DEFAULT, TOOLMATERIALS);
		TM_TrueLightsFlight = configProperty(TM_TrueLightsFlight_NAME, "", TM_TrueLightsFlight_DEFAULT, TOOLMATERIALS);
		TM_TwilightBlaze = configProperty(TM_TwilightBlaze_NAME, "", TM_TwilightBlaze_DEFAULT, TOOLMATERIALS);
		TM_TwoBecomeOne = configProperty(TM_TwoBecomeOne_NAME, "", TM_TwoBecomeOne_DEFAULT, TOOLMATERIALS);
		TM_UltimaWeaponKH1 = configProperty(TM_UltimaWeaponKH1_NAME, "", TM_UltimaWeaponKH1_DEFAULT, TOOLMATERIALS);
		TM_UltimaWeaponKH2 = configProperty(TM_UltimaWeaponKH2_NAME, "", TM_UltimaWeaponKH2_DEFAULT, TOOLMATERIALS);
		TM_UltimaWeaponBBS = configProperty(TM_UltimaWeaponBBS_NAME, "", TM_UltimaWeaponBBS_DEFAULT, TOOLMATERIALS);
		TM_UltimaWeaponDDD = configProperty(TM_UltimaWeaponDDD_NAME, "", TM_UltimaWeaponDDD_DEFAULT, TOOLMATERIALS);
		TM_Umbrella = configProperty(TM_Umbrella_NAME, "", TM_Umbrella_DEFAULT, TOOLMATERIALS);
		TM_Unbound = configProperty(TM_Unbound_NAME, "", TM_Unbound_DEFAULT, TOOLMATERIALS);
		TM_UnicornisForetellersKeyblade = configProperty(TM_UnicornisForetellersKeyblade_NAME, "", TM_UnicornisForetellersKeyblade_DEFAULT, TOOLMATERIALS);
		TM_UrsusForetellersKeyblade = configProperty(TM_UrsusForetellersKeyblade_NAME, "", TM_UrsusForetellersKeyblade_DEFAULT, TOOLMATERIALS);
		TM_VictoryLine = configProperty(TM_VictoryLine_NAME, "", TM_VictoryLine_DEFAULT, TOOLMATERIALS);
		TM_VoidGear = configProperty(TM_VoidGear_NAME, "", TM_VoidGear_DEFAULT, TOOLMATERIALS);
		TM_VulpeusForetellersKeyblade = configProperty(TM_VulpeusForetellersKeyblade_NAME, "", TM_VulpeusForetellersKeyblade_DEFAULT, TOOLMATERIALS);
		TM_WaytotheDawn = configProperty(TM_WaytotheDawn_NAME, "", TM_WaytotheDawn_DEFAULT, TOOLMATERIALS);
		TM_WaywardWind = configProperty(TM_WaywardWind_NAME, "", TM_WaywardWind_DEFAULT, TOOLMATERIALS);
		TM_WinnersProof = configProperty(TM_WinnersProof_NAME, "", TM_WinnersProof_DEFAULT, TOOLMATERIALS);
		TM_WishingLamp = configProperty(TM_WishingLamp_NAME, "", TM_WishingLamp_DEFAULT, TOOLMATERIALS);
		TM_WishingStar = configProperty(TM_WishingStar_NAME, "", TM_WishingStar_DEFAULT, TOOLMATERIALS);
		TM_WoodenKeyblade = configProperty(TM_WoodenKeyblade_NAME, "", TM_WoodenKeyblade_DEFAULT, TOOLMATERIALS);
		TM_WoodenStick = configProperty(TM_WoodenStick_NAME, "", TM_WoodenStick_DEFAULT, TOOLMATERIALS);
		TM_YoungXehanortsKeyblade = configProperty(TM_YoungXehanortsKeyblade_NAME, "", TM_YoungXehanortsKeyblade_DEFAULT, TOOLMATERIALS);
		TM_ZeroOne = configProperty(TM_ZeroOne_NAME, "", TM_ZeroOne_DEFAULT, TOOLMATERIALS);

		A_TM_Interdiction = TM_Interdiction.split(", ");
		A_TM_Sharpshooter = TM_Sharpshooter.split(", ");
		A_TM_Lindworm = TM_Lindworm.split(", ");
		A_TM_FrozenPride = TM_FrozenPride.split(", ");
		A_TM_Skysplitter = TM_Skysplitter.split(", ");
		A_TM_BookofRetribution = TM_BookofRetribution.split(", ");
		A_TM_Lunatic = TM_Lunatic.split(", ");
		A_TM_EternalFlames = TM_EternalFlames.split(", ");
		A_TM_Arpeggio = TM_Arpeggio.split(", ");
		A_TM_FairGame = TM_FairGame.split(", ");
		A_TM_GracefulDahlia = TM_GracefulDahlia.split(", ");
		A_TM_Foudre = TM_Foudre.split(", ");
		A_TM_BlazeofGlory = TM_BlazeofGlory.split(", ");
		A_TM_Prometheus = TM_Prometheus.split(", ");
		A_TM_Ifrit = TM_Ifrit.split(", ");

		A_TM_AbaddonPlasma = TM_AbaddonPlasma.split(", ");
		A_TM_AbyssalTide = TM_AbyssalTide.split(", ");
		A_TM_AllforOne = TM_AllforOne.split(", ");
		A_TM_AnguisForetellersKeyblade = TM_AnguisForetellersKeyblade.split(", ");
		A_TM_AstralBlast = TM_AstralBlast.split(", ");
		A_TM_Aubade = TM_Aubade.split(", ");
		A_TM_BondofFlame = TM_BondofFlame.split(", ");
		A_TM_Brightcrest = TM_Brightcrest.split(", ");
		A_TM_ChaosRipper = TM_ChaosRipper.split(", ");
		A_TM_CircleofLife = TM_CircleofLife.split(", ");
		A_TM_Counterpoint = TM_Counterpoint.split(", ");
		A_TM_Crabclaw = TM_Crabclaw.split(", ");
		A_TM_CrownofGuilt = TM_CrownofGuilt.split(", ");
		A_TM_DarkerThanDark = TM_DarkerThanDark.split(", ");
		A_TM_Darkgnaw = TM_Darkgnaw.split(", ");
		A_TM_DecisivePumpkin = TM_DecisivePumpkin.split(", ");
		A_TM_DestinysEmbrace = TM_DestinysEmbrace.split(", ");
		A_TM_DiamondDust = TM_DiamondDust.split(", ");
		A_TM_Divewing = TM_Divewing.split(", ");
		A_TM_DivineRose = TM_DivineRose.split(", ");
		A_TM_DreamSword = TM_DreamSword.split(", ");
		A_TM_DualDisc = TM_DualDisc.split(", ");
		A_TM_Earthshaker = TM_Earthshaker.split(", ");
		A_TM_EndofPain = TM_EndofPain.split(", ");
		A_TM_EndsoftheEarth = TM_EndsoftheEarth.split(", ");
		A_TM_FairyHarp = TM_FairyHarp.split(", ");
		A_TM_FairyStars = TM_FairyStars.split(", ");
		A_TM_FatalCrest = TM_FatalCrest.split(", ");
		A_TM_Fenrir = TM_Fenrir.split(", ");
		A_TM_FerrisGear = TM_FerrisGear.split(", ");
		A_TM_FollowtheWind = TM_FollowtheWind.split(", ");
		A_TM_FrolicFlame = TM_FrolicFlame.split(", ");
		A_TM_GlimpseofDarkness = TM_GlimpseofDarkness.split(", ");
		A_TM_GuardianBell = TM_GuardianBell.split(", ");
		A_TM_GuardianSoul = TM_GuardianSoul.split(", ");
		A_TM_GullWing = TM_GullWing.split(", ");
		A_TM_HerosCrest = TM_HerosCrest.split(", ");
		A_TM_HiddenDragon = TM_HiddenDragon.split(", ");
		A_TM_Hyperdrive = TM_Hyperdrive.split(", ");
		A_TM_IncompleteKiblade = TM_IncompleteKiblade.split(", ");
		A_TM_JungleKing = TM_JungleKing.split(", ");
		A_TM_KeybladeofPeoplesHearts = TM_KeybladeofPeoplesHearts.split(", ");
		A_TM_Kiblade = TM_Kiblade.split(", ");
		A_TM_KingdomKey = TM_KingdomKey.split(", ");
		A_TM_KingdomKeyD = TM_KingdomKeyD.split(", ");
		A_TM_KnockoutPunch = TM_KnockoutPunch.split(", ");
		A_TM_LadyLuck = TM_LadyLuck.split(", ");
		A_TM_LeasKeyblade = TM_LeasKeyblade.split(", ");
		A_TM_LeopardosForetellersKeyblade = TM_LeopardosForetellersKeyblade.split(", ");
		A_TM_Leviathan = TM_Leviathan.split(", ");
		A_TM_Lionheart = TM_Lionheart.split(", ");
		A_TM_LostMemory = TM_LostMemory.split(", ");
		A_TM_LunarEclipse = TM_LunarEclipse.split(", ");
		A_TM_MarkofaHero = TM_MarkofaHero.split(", ");
		A_TM_MasterXehanortsKeyblade = TM_MasterXehanortsKeyblade.split(", ");
		A_TM_MastersDefender = TM_MastersDefender.split(", ");
		A_TM_MaverickFlare = TM_MaverickFlare.split(", ");
		A_TM_MetalChocobo = TM_MetalChocobo.split(", ");
		A_TM_MidnightRoar = TM_MidnightRoar.split(", ");
		A_TM_MirageSplit = TM_MirageSplit.split(", ");
		A_TM_MissingAche = TM_MissingAche.split(", ");
		A_TM_Monochrome = TM_Monochrome.split(", ");
		A_TM_MysteriousAbyss = TM_MysteriousAbyss.split(", ");
		A_TM_NightmaresEnd = TM_NightmaresEnd.split(", ");
		A_TM_NightmaresEndandMirageSplit = TM_NightmaresEndandMirageSplit.split(", ");
		A_TM_NoName = TM_NoName.split(", ");
		A_TM_Oathkeeper = TM_Oathkeeper.split(", ");
		A_TM_Oblivion = TM_Oblivion.split(", ");
		A_TM_OceansRage = TM_OceansRage.split(", ");
		A_TM_Olympia = TM_Olympia.split(", ");
		A_TM_OmegaWeapon = TM_OmegaWeapon.split(", ");
		A_TM_OminousBlight = TM_OminousBlight.split(", ");
		A_TM_OneWingedAngel = TM_OneWingedAngel.split(", ");
		A_TM_PainofSolitude = TM_PainofSolitude.split(", ");
		A_TM_PhotonDebugger = TM_PhotonDebugger.split(", ");
		A_TM_PixiePetal = TM_PixiePetal.split(", ");
		A_TM_Pumpkinhead = TM_Pumpkinhead.split(", ");
		A_TM_Rainfell = TM_Rainfell.split(", ");
		A_TM_RejectionofFate = TM_RejectionofFate.split(", ");
		A_TM_RoyalRadiance = TM_RoyalRadiance.split(", ");
		A_TM_RumblingRose = TM_RumblingRose.split(", ");
		A_TM_SignofInnocence = TM_SignofInnocence.split(", ");
		A_TM_SilentDirge = TM_SilentDirge.split(", ");
		A_TM_SkullNoise = TM_SkullNoise.split(", ");
		A_TM_SleepingLion = TM_SleepingLion.split(", ");
		A_TM_SoulEater = TM_SoulEater.split(", ");
		A_TM_Spellbinder = TM_Spellbinder.split(", ");
		A_TM_StarSeeker = TM_StarSeeker.split(", ");
		A_TM_Starlight = TM_Starlight.split(", ");
		A_TM_Stormfall = TM_Stormfall.split(", ");
		A_TM_StrokeofMidnight = TM_StrokeofMidnight.split(", ");
		A_TM_SweetDreams = TM_SweetDreams.split(", ");
		A_TM_SweetMemories = TM_SweetMemories.split(", ");
		A_TM_Sweetstack = TM_Sweetstack.split(", ");
		A_TM_ThreeWishes = TM_ThreeWishes.split(", ");
		A_TM_TotalEclipse = TM_TotalEclipse.split(", ");
		A_TM_TreasureTrove = TM_TreasureTrove.split(", ");
		A_TM_TrueLightsFlight = TM_TrueLightsFlight.split(", ");
		A_TM_TwilightBlaze = TM_TwilightBlaze.split(", ");
		A_TM_TwoBecomeOne = TM_TwoBecomeOne.split(", ");
		A_TM_UltimaWeaponKH1 = TM_UltimaWeaponKH1.split(", ");
		A_TM_UltimaWeaponKH2 = TM_UltimaWeaponKH2.split(", ");
		A_TM_UltimaWeaponBBS = TM_UltimaWeaponBBS.split(", ");
		A_TM_UltimaWeaponDDD = TM_UltimaWeaponDDD.split(", ");
		A_TM_Umbrella = TM_Umbrella.split(", ");
		A_TM_Unbound = TM_Unbound.split(", ");
		A_TM_UnicornisForetellersKeyblade = TM_UnicornisForetellersKeyblade.split(", ");
		A_TM_UrsusForetellersKeyblade = TM_UrsusForetellersKeyblade.split(", ");
		A_TM_VictoryLine = TM_VictoryLine.split(", ");
		A_TM_VoidGear = TM_VoidGear.split(", ");
		A_TM_VulpeusForetellersKeyblade = TM_VulpeusForetellersKeyblade.split(", ");
		A_TM_WaytotheDawn = TM_WaytotheDawn.split(", ");
		A_TM_WaywardWind = TM_WaywardWind.split(", ");
		A_TM_WinnersProof = TM_WinnersProof.split(", ");
		A_TM_WishingLamp = TM_WishingLamp.split(", ");
		A_TM_WishingStar = TM_WishingStar.split(", ");
		A_TM_WoodenKeyblade = TM_WoodenKeyblade.split(", ");
		A_TM_WoodenStick = TM_WoodenStick.split(", ");
		A_TM_YoungXehanortsKeyblade = TM_YoungXehanortsKeyblade.split(", ");
		A_TM_ZeroOne = TM_ZeroOne.split(", ");

		if (config.hasChanged()) config.save();
	}

	private static boolean configProperty(String name, String description, boolean defaultValue, String category) {
		Property property = config.get(category, name, defaultValue);
		property.setComment(description);
		return property.getBoolean(defaultValue);
	}

	public static int configProperty(String name, String description, int defaultValue, String category) {
		Property property = config.get(category, name, defaultValue);
		property.setComment(description);
		return property.getInt(defaultValue);
	}

	public static double configProperty(String name, String description, double defaultValue, String category) {
		Property property = config.get(category, name, defaultValue);
		property.setComment(description);
		return property.getDouble(defaultValue);
	}

	public static String configProperty(String name, String description, String defaultValue, String category) {
		Property property = config.get(category, name, defaultValue);
		property.setComment(description);
		return property.getString();
	}

	public static Property configProperty(String name, String description, int[] defaultValue, String category) {
		Property property = config.get(category, name, defaultValue);
		property.setComment(description);
		return property;
	}

	public static String propertyCategory(String name, String parent) {
		return parent + Configuration.CATEGORY_SPLITTER + name;
	}

	// Sync config when changed
	@SubscribeEvent
	public void OnConfigChanged (ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID().equals(Reference.MODID)) load();
	}
}
