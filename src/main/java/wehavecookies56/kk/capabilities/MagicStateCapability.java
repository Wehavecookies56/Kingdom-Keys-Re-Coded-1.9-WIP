package wehavecookies56.kk.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import wehavecookies56.kk.inventory.InventoryDriveForms;
import wehavecookies56.kk.inventory.InventorySpells;
import wehavecookies56.kk.item.ItemSpellOrb;
import wehavecookies56.kk.lib.Strings;

public class MagicStateCapability {
	
	public interface IMagicState {
		boolean getKH1Fire();
		int getMagicLevel(String magic);
		void setKH1Fire(boolean kh1fire);
		void setMagicLevel(String magic, int level);
		
		InventorySpells getInventorySpells();

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
			
			properties.setBoolean("KH1Fire", instance.getKH1Fire());
			
			instance.getInventorySpells().writeToNBT(properties);


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
			
			instance.setKH1Fire(properties.getBoolean("KH1Fire"));
			
			instance.getInventorySpells().readFromNBT(properties);
			
			

		}
	}
	
	public static class Default implements IMagicState {
        private int fireLevel = 1, blizzardLevel = 1, thunderLevel = 1, cureLevel = 1, aeroLevel = 1, stopLevel = 1;
		private boolean kh1fire = false;
		private final InventorySpells inventorySpells = new InventorySpells();


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
        @Override
        public void setKH1Fire(boolean kh1fire)
        {
        	this.kh1fire = kh1fire;
        }
        @Override
        public boolean getKH1Fire()
        {
        	return this.kh1fire;
        }
        
		@Override public InventorySpells getInventorySpells(){return this.inventorySpells;}

    }
}


