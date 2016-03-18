package wehavecookies56.kk.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class MagicStateCapability {
	
	public interface IMagicState {
		int getMagicLevel(String magic);
		void setMagicLevel(String magic, int level);
	}

	public static class Storage implements IStorage<IMagicState> {

		@Override
		public NBTBase writeNBT(Capability<IMagicState> capability, IMagicState instance, EnumFacing side) {
			NBTTagCompound properties = new NBTTagCompound();
			
			properties.setInteger("MagicLevelFire", instance.getMagicLevel("Fire"));
			properties.setInteger("MagicLevelBlizzard", instance.getMagicLevel("Blizzard"));
			properties.setInteger("MagicLevelThunder", instance.getMagicLevel("Thunder"));
			properties.setInteger("MagicLevelCure", instance.getMagicLevel("Cure"));
			properties.setInteger("MagicLevelAero", instance.getMagicLevel("Aero"));
			properties.setInteger("MagicLevelStop", instance.getMagicLevel("Stop"));

			return properties;
		}

		@Override
		public void readNBT(Capability<IMagicState> capability, IMagicState instance, EnumFacing side, NBTBase nbt) {
			NBTTagCompound properties = (NBTTagCompound) nbt;
			instance.setMagicLevel("Fire", properties.getInteger("MagicLevelFire"));
			instance.setMagicLevel("Blizzard", properties.getInteger("MagicLevelBlizzard"));
			instance.setMagicLevel("Thunder", properties.getInteger("MagicLevelThunder"));
			instance.setMagicLevel("Cure", properties.getInteger("MagicLevelCure"));
			instance.setMagicLevel("Aero", properties.getInteger("MagicLevelAero"));
			instance.setMagicLevel("Stop", properties.getInteger("MagicLevelStop"));
		}
	}
	
	public static class Default implements IMagicState {
        private int fireLevel, blizzardLevel, thunderLevel, cureLevel, aeroLevel, stopLevel;
		@Override
		public int getMagicLevel(String magic) {
			switch(magic)
			{
			case "Fire":
				return fireLevel;
			case "Blizzard":
				return blizzardLevel;
			case "Thunder":
				return thunderLevel;
			case "Cure":
				return cureLevel;
			case "Aero":
				return aeroLevel;
			case "Stop":
				return stopLevel;
			}
			return 0;
		}
		@Override
		public void setMagicLevel(String magic, int level) {
			switch(magic)
			{
			case "Fire":
				fireLevel = level;
			case "Blizzard":
				blizzardLevel = level;
			case "Thunder":
				thunderLevel = level;
			case "Cure":
				cureLevel = level;
			case "Aero":
				aeroLevel = level;
			case "Stop":
				stopLevel = level;
			}			
		}
        
    }
}


