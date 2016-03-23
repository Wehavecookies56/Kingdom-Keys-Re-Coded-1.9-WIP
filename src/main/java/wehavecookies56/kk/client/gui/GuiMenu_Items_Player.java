package wehavecookies56.kk.client.gui;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import wehavecookies56.kk.KingdomKeys;
import wehavecookies56.kk.lib.Strings;
import wehavecookies56.kk.util.GuiHelper;
import wehavecookies56.kk.util.TextHelper;

public class GuiMenu_Items_Player extends GuiMenu_Bars {

	final int KEYCHAIN = 1, POTIONS = 2, SPELLS = 3, DRIVE = 4, BACK = 5; 
	GuiButton keychain, potions, spells, driveforms, back;

	public GuiMenu_Items_Player (String name) {
		super(Minecraft.getMinecraft().thePlayer.getDisplayName().getFormattedText());
	}

	@Override
	protected void actionPerformed (GuiButton button) throws IOException {
		switch (button.id) {
			case KEYCHAIN:
				GuiHelper.openInv(KingdomKeys.GUI_KEYCHAIN_INV);
				break;
			case POTIONS:
				GuiHelper.openInv(KingdomKeys.GUI_POTIONS_INV);
				break;
			case SPELLS:
				GuiHelper.openInv(KingdomKeys.GUI_SPELLS_INV);
				break;
			case DRIVE:
				GuiHelper.openInv(KingdomKeys.GUI_DRIVE_INV);
				break;
			case BACK:
				GuiHelper.openMenu_Items();
				break;
		}
		updateButtons();
	}

	private void updateButtons () {
		keychain.enabled = mc.thePlayer.getCapability(KingdomKeys.SUMMON_KEYBLADE, null).getIsKeybladeSummoned() == false;
		updateScreen();
	}

	@Override
	public void initGui () {
		super.initGui();
		int button_itemsY = (-140 / 16) + 75;

		int button_items_keybladeY = button_itemsY;
		int button_items_potionsY = button_items_keybladeY + 22; 
		int button_items_spellsY = button_items_potionsY + 22;
		int button_items_driveY = button_items_spellsY + 22;
		int button_items_backY = button_items_driveY + 22;

		buttonList.add(keychain = new GuiButton(KEYCHAIN, 5, button_items_keybladeY, 100, 20, TextHelper.localize(Strings.Gui_Menu_Items_Button_Keychain))); 		
		buttonList.add(potions = new GuiButton(POTIONS, 5, button_items_potionsY, 100, 20, TextHelper.localize(Strings.Gui_Menu_Items_Button_Potions)));
		buttonList.add(spells = new GuiButton(SPELLS, 5, button_items_spellsY, 100, 20, TextHelper.localize(Strings.Gui_Menu_Items_Button_Spells)));
		buttonList.add(driveforms = new GuiButton(DRIVE, 5, button_items_driveY, 100, 20, TextHelper.localize(Strings.Gui_Menu_Items_Button_Drive)));
		buttonList.add(back = new GuiButton(BACK, 5, button_items_backY, 100, 20, TextHelper.localize(Strings.Gui_Menu_Items_Button_Back)));
		updateButtons();
	}

}
