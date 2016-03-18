package wehavecookies56.kk.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class SummonKeybladeCapability {
	
	public interface ISummonKeyblade {
		boolean getKeybladeSummoned();
		String getKeybladeName();
		String getKeychainName();
		boolean setKeybladeSummoned(boolean summoned, String keychainName, String keybladeName);
	}

	public static class Storage implements IStorage<ISummonKeyblade> {

		@Override
		public NBTBase writeNBT(Capability<ISummonKeyblade> capability, ISummonKeyblade instance, EnumFacing side) {
			NBTTagCompound properties = new NBTTagCompound();
			properties.setBoolean("Keyblade Summoned", instance.getKeybladeSummoned());
			properties.setString("Keyblade Name", instance.getKeybladeName());
			properties.setString("Keychain Name", instance.getKeychainName());
			
			return properties;
		}

		@Override
		public void readNBT(Capability<ISummonKeyblade> capability, ISummonKeyblade instance, EnumFacing side, NBTBase nbt) {
			NBTTagCompound properties = (NBTTagCompound) nbt;
			instance.setKeybladeSummoned(properties.getBoolean("Keyblade Summoned"), properties.getString("Keyblade Name"), properties.getString("Keychain Name"));
			
		}
	}
	
	public static class Default implements ISummonKeyblade {
        private boolean summoned = false;
        private String keyblade = "";
        private String keychain = "";
        @Override public boolean getKeybladeSummoned() { return this.summoned; }
        @Override public String getKeybladeName() { return this.keyblade; }
        @Override public String getKeychainName() { return this.keychain; }
        @Override public boolean setKeybladeSummoned(boolean summoned, String keychain, String keyblade) { 
        	this.summoned = summoned; 
        	this.keyblade = keyblade; 
        	this.keychain = keychain; 
        	if (this.keyblade.isEmpty() || this.keychain.isEmpty()) {
        		this.summoned = false;
        		return this.summoned;
        	}
        	return this.summoned;
        }

    }
}


