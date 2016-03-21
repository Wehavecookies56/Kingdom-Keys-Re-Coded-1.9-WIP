package wehavecookies56.kk.capabilities;

import net.minecraftforge.common.capabilities.CapabilityManager;
import wehavecookies56.kk.capabilities.CheatModeCapability.ICheatMode;
import wehavecookies56.kk.capabilities.DriveStateCapability.IDriveState;
import wehavecookies56.kk.capabilities.FirstTimeJoinCapability.IFirstTimeJoin;
import wehavecookies56.kk.capabilities.MagicStateCapability.IMagicState;
import wehavecookies56.kk.capabilities.MunnyCapability.IMunny;
import wehavecookies56.kk.capabilities.PlayerStatsCapability.IPlayerStats;
import wehavecookies56.kk.capabilities.SummonKeybladeCapability.ISummonKeyblade;
import wehavecookies56.kk.capabilities.SynthesisMaterialCapability.ISynthesisMaterial;
import wehavecookies56.kk.capabilities.SynthesisRecipeCapability.ISynthesisRecipe;

public class ModCapabilities {

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
