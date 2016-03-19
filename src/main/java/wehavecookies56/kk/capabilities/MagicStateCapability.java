package wehavecookies56.kk.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import wehavecookies56.kk.lib.Strings;

public class MagicStateCapability {
	
	public interface IMagicState {
		int getMagicLevel(String magic);
		void setMagicLevel(String magic, int level);
	}

	public static class Storage implements IStorage<IMagicState> {

		@Override
		public NBTBase writeNBT(Capability<IMagicState> capability, IMagicState instance, EnumFacing side) {
			NBTTagCompound properties = new NBTTagCompound();
			
			properties.setInteger("MagicLevelFire", instance.getMagicLevel(Strings.Spell_Fire));
			properties.setInteger("MagicLevelBlizzard", instance.getMagicLevel(Strings.Spell_Blizzard));
			properties.setInteger("MagicLevelThunder", instance.getMagicLevel(Strings.Spell_Thunder));
			properties.setInteger("MagicLevelCure", instance.getMagicLevel(Strings.Spell_Cure));
			properties.setInteger("MagicLevelAero", instance.getMagicLevel(Strings.Spell_Aero));
			properties.setInteger("MagicLevelStop", instance.getMagicLevel(Strings.Spell_Stop));

			return properties;
		}

		@Override
		public void readNBT(Capability<IMagicState> capability, IMagicState instance, EnumFacing side, NBTBase nbt) {
			NBTTagCompound properties = (NBTTagCompound) nbt;
			instance.setMagicLevel(Strings.Spell_Fire, properties.getInteger("MagicLevelFire"));
			instance.setMagicLevel(Strings.Spell_Blizzard, properties.getInteger("MagicLevelBlizzard"));
			instance.setMagicLevel(Strings.Spell_Thunder, properties.getInteger("MagicLevelThunder"));
			instance.setMagicLevel(Strings.Spell_Cure, properties.getInteger("MagicLevelCure"));
			instance.setMagicLevel(Strings.Spell_Aero, properties.getInteger("MagicLevelAero"));
			instance.setMagicLevel(Strings.Spell_Stop, properties.getInteger("MagicLevelStop"));
		}
	}
	
	public static class Default implements IMagicState {
        private int fireLevel = 1, blizzardLevel = 1, thunderLevel = 1, cureLevel = 1, aeroLevel = 1, stopLevel = 1;
		@Override
		public int getMagicLevel(String magic) {
			switch(magic)
			{
			case Strings.Spell_Fire:
				return fireLevel;
			case Strings.Spell_Blizzard:
				return blizzardLevel;
			case Strings.Spell_Thunder:
				return thunderLevel;
			case Strings.Spell_Cure:
				return cureLevel;
			case Strings.Spell_Aero:
				return aeroLevel;
			case Strings.Spell_Stop:
				return stopLevel;
			}
			return 0;
		}
		@Override
		public void setMagicLevel(String magic, int level) {
			switch(magic)
			{
			case Strings.Spell_Fire:
				fireLevel = level;
			case Strings.Spell_Blizzard:
				blizzardLevel = level;
			case Strings.Spell_Thunder:
				thunderLevel = level;
			case Strings.Spell_Cure:
				cureLevel = level;
			case Strings.Spell_Aero:
				aeroLevel = level;
			case Strings.Spell_Stop:
				stopLevel = level;
			}			
		}
        
    }
}


