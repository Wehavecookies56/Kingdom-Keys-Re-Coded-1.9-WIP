package wehavecookies56.kk.api.driveforms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import wehavecookies56.kk.KingdomKeys;
import wehavecookies56.kk.item.ItemDriveForm;

public class DriveFormRegistry {

	private static Map<String, DriveForm> driveFormMap = new HashMap<String, DriveForm>();

	public static Map<String, DriveForm> getDriveFormMap () {
		return ImmutableMap.copyOf(driveFormMap);
	}

	public static boolean registerDriveForm (DriveForm driveForm) {
		if (isDriveFormRegistered(driveForm.getName())) return false;
		driveFormMap.put(driveForm.getName(), driveForm);
		return true;
	}

	public static boolean isDriveFormRegistered (DriveForm driveForm) {
		return isDriveFormRegistered(driveForm.getName());
	}

	public static boolean isDriveFormRegistered (String name) {
		return driveFormMap.containsKey(name);
	}

	public static DriveForm get (String name) {
		return driveFormMap.get(name);
	}

	public static boolean learnDriveForm (EntityPlayer player, String name) {
		if (player != null && !isDriveFormKnown(player, name)) {
			DriveForm driveForm = driveFormMap.get(name);
			player.getCapability(KingdomKeys.DRIVE_STATE, null).learnDriveForm(driveForm);
			return true;
		}
		return false;
	}

	public static boolean isDriveFormKnown (EntityPlayer player, String name) {
		List<String> driveCommands = new ArrayList<String>();
		driveCommands.clear();
		for (int i = 0; i < Minecraft.getMinecraft().thePlayer.getCapability(KingdomKeys.DRIVE_STATE, null).getInventoryDriveForms().getSizeInventory(); i++)
			if (Minecraft.getMinecraft().thePlayer.getCapability(KingdomKeys.DRIVE_STATE, null).getInventoryDriveForms().getStackInSlot(i) != null) driveCommands.add(((ItemDriveForm) Minecraft.getMinecraft().thePlayer.getCapability(KingdomKeys.DRIVE_STATE, null).getInventoryDriveForms().getStackInSlot(i).getItem()).getDriveFormName());	
		return driveCommands.contains(name);
	}

}
