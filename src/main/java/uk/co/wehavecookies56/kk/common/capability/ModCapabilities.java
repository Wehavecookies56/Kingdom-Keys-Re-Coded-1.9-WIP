package uk.co.wehavecookies56.kk.common.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import uk.co.wehavecookies56.kk.common.capability.CheatModeCapability.ICheatMode;
import uk.co.wehavecookies56.kk.common.capability.DriveStateCapability.IDriveState;
import uk.co.wehavecookies56.kk.common.capability.FirstTimeJoinCapability.IFirstTimeJoin;
import uk.co.wehavecookies56.kk.common.capability.MagicStateCapability.IMagicState;
import uk.co.wehavecookies56.kk.common.capability.MunnyCapability.IMunny;
import uk.co.wehavecookies56.kk.common.capability.PlayerStatsCapability.IPlayerStats;
import uk.co.wehavecookies56.kk.common.capability.SummonKeybladeCapability.ISummonKeyblade;
import uk.co.wehavecookies56.kk.common.capability.SynthesisMaterialCapability.ISynthesisMaterial;
import uk.co.wehavecookies56.kk.common.capability.SynthesisRecipeCapability.ISynthesisRecipe;

public class ModCapabilities {

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

    public static void registerCapabilities() {
        CapabilityManager.INSTANCE.register(IMunny.class, new MunnyCapability.Storage(), MunnyCapability.Default.class);
        CapabilityManager.INSTANCE.register(IPlayerStats.class, new PlayerStatsCapability.Storage(), PlayerStatsCapability.Default.class);
        CapabilityManager.INSTANCE.register(ISummonKeyblade.class, new SummonKeybladeCapability.Storage(), SummonKeybladeCapability.Default.class);
        CapabilityManager.INSTANCE.register(IDriveState.class, new DriveStateCapability.Storage(), DriveStateCapability.Default.class);
        CapabilityManager.INSTANCE.register(IMagicState.class, new MagicStateCapability.Storage(), MagicStateCapability.Default.class);
        CapabilityManager.INSTANCE.register(IFirstTimeJoin.class, new FirstTimeJoinCapability.Storage(), FirstTimeJoinCapability.Default.class);
        CapabilityManager.INSTANCE.register(ISynthesisRecipe.class, new SynthesisRecipeCapability.Storage(), SynthesisRecipeCapability.Default.class);
        CapabilityManager.INSTANCE.register(ISynthesisMaterial.class, new SynthesisMaterialCapability.Storage(), SynthesisMaterialCapability.Default.class);
        CapabilityManager.INSTANCE.register(ICheatMode.class, new CheatModeCapability.Storage(), CheatModeCapability.Default.class);
	}
	
}
