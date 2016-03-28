package uk.co.wehavecookies56.common.capability;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import uk.co.wehavecookies56.api.driveforms.DriveForm;
import uk.co.wehavecookies56.common.container.inventory.InventoryDriveForms;
import uk.co.wehavecookies56.common.lib.Strings;

public class DriveStateCapability {
	
	public interface IDriveState {
		boolean getInDrive();
		String getActiveDriveName();
		int getAntiPoints();
		int getDriveLevel(String drive);
		int getDriveExp(String drive);
		
		void setInDrive(boolean drive);
		void setActiveDriveName(String drive);
		void setAntiPoints(int points);
		void setDriveLevel(String drive, int level);
		void setDriveExp(String drive, int exp);

		void learnDriveForm(DriveForm form);
		
		InventoryDriveForms getInventoryDriveForms();
	}

	public static class Storage implements IStorage<IDriveState> {

		@Override
		public NBTBase writeNBT(Capability<IDriveState> capability, IDriveState instance, EnumFacing side) {
			NBTTagCompound properties = new NBTTagCompound();
			properties.setBoolean("InDrive", instance.getInDrive());
			properties.setString("ActiveDriveName", instance.getActiveDriveName());
			properties.setInteger("AntiPoints", instance.getAntiPoints());
			properties.setInteger("DriveLevelValor", instance.getDriveLevel(Strings.Form_Valor));
			properties.setInteger("DriveLevelWisdom", instance.getDriveLevel(Strings.Form_Wisdom));
			properties.setInteger("DriveLevelLimit", instance.getDriveLevel(Strings.Form_Limit));
			properties.setInteger("DriveLevelMaster", instance.getDriveLevel(Strings.Form_Master));
			properties.setInteger("DriveLevelFinal", instance.getDriveLevel(Strings.Form_Final));
			
			properties.setInteger("DriveExpValor", instance.getDriveExp(Strings.Form_Valor));
			properties.setInteger("DriveExpWisdom", instance.getDriveExp(Strings.Form_Wisdom));
			properties.setInteger("DriveExpLimit", instance.getDriveExp(Strings.Form_Limit));
			properties.setInteger("DriveExpMaster", instance.getDriveExp(Strings.Form_Master));
			properties.setInteger("DriveExpFinal", instance.getDriveExp(Strings.Form_Final));
			
			instance.getInventoryDriveForms().writeToNBT(properties);

			return properties;
		}

		@Override
		public void readNBT(Capability<IDriveState> capability, IDriveState instance, EnumFacing side, NBTBase nbt) {
			NBTTagCompound properties = (NBTTagCompound) nbt;
			instance.setInDrive(properties.getBoolean("InDrive"));
			instance.setActiveDriveName(properties.getString("ActiveDriveName"));
			instance.setAntiPoints(properties.getInteger("AntiPoints"));
			instance.setDriveLevel(Strings.Form_Valor, properties.getInteger("DriveLevelValor"));
			instance.setDriveLevel(Strings.Form_Wisdom, properties.getInteger("DriveLevelWisdom"));
			instance.setDriveLevel(Strings.Form_Limit, properties.getInteger("DriveLevelLimit"));
			instance.setDriveLevel(Strings.Form_Master, properties.getInteger("DriveLevelMaster"));
			instance.setDriveLevel(Strings.Form_Final, properties.getInteger("DriveLevelFinal"));
			
			instance.setDriveExp(Strings.Form_Valor, properties.getInteger("DriveExpValor"));
			instance.setDriveExp(Strings.Form_Wisdom, properties.getInteger("DriveExpWisdom"));
			instance.setDriveExp(Strings.Form_Limit, properties.getInteger("DriveExpLimit"));
			instance.setDriveExp(Strings.Form_Master, properties.getInteger("DriveExpMaster"));
			instance.setDriveExp(Strings.Form_Final, properties.getInteger("DriveExpFinal"));

			instance.getInventoryDriveForms().readFromNBT(properties);
		}
	}
	
	public static class Default implements IDriveState {
        private boolean inDrive = false;
        private String activeDrive = "none";
        int antiPoints = 0;
		private final InventoryDriveForms inventoryDrive = new InventoryDriveForms();
		private static List<String> driveForms = new ArrayList<String>();

		int valor, wisdom, limit, master, Final;
		int valorExp, wisdomExp, limitExp, masterExp, FinalExp; 
		@Override public boolean getInDrive() { return inDrive; }
		@Override public String getActiveDriveName() { return activeDrive; }
		@Override public int getAntiPoints() { return antiPoints; }
		@Override public int getDriveLevel(String drive) {
			switch(drive) {
				case Strings.Form_Valor:
					return valor;
				case Strings.Form_Wisdom:
					return wisdom;
				case Strings.Form_Limit:
					return limit;
				case Strings.Form_Master:
					return master;
				case Strings.Form_Final:
					return Final;
			}
			return 0;
		}

		@Override public void setInDrive(boolean drive) { this.inDrive = drive; }
		@Override public void setActiveDriveName(String drive) { this.activeDrive = drive; }
		@Override public void setAntiPoints(int points) { this.antiPoints = points; }
		@Override public void setDriveLevel(String drive, int level) {
			switch (drive) {
				case Strings.Form_Valor:
					valor = level;
					break;
				case Strings.Form_Wisdom:
					wisdom = level;
					break;
				case Strings.Form_Limit:
					limit = level;
					break;
				case Strings.Form_Master:
					master = level;
					break;
				case Strings.Form_Final:
					Final = level;
					break;
			}
		}
		@Override
		public void learnDriveForm(DriveForm form) {
			driveForms.add(form.getName());
		}
		@Override public InventoryDriveForms getInventoryDriveForms(){return this.inventoryDrive;}
		@Override public int getDriveExp(String drive) {
			switch(drive) {
			case Strings.Form_Valor:
				return valorExp;
			case Strings.Form_Wisdom:
				return wisdomExp;
			case Strings.Form_Limit:
				return limitExp;
			case Strings.Form_Master:
				return masterExp;
			case Strings.Form_Final:
				return FinalExp;
			}
			return 0;
		}
		@Override public void setDriveExp(String drive, int exp) {
			switch (drive) {
				case Strings.Form_Valor:
					valorExp = exp;
					break;
				case Strings.Form_Wisdom:
					wisdomExp = exp;
					break;
				case Strings.Form_Limit:
					limitExp = exp;
					break;
				case Strings.Form_Master:
					masterExp = exp;
					break;
				case Strings.Form_Final:
					FinalExp = exp;
					break;
			}			
		}

    }
}


