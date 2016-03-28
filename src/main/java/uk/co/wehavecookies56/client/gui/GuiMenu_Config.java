package uk.co.wehavecookies56.client.gui;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import uk.co.wehavecookies56.common.capability.ModCapabilities;
import uk.co.wehavecookies56.common.core.handler.ConfigHandler;
import uk.co.wehavecookies56.common.lib.Strings;
import uk.co.wehavecookies56.common.network.packet.PacketDispatcher;
import uk.co.wehavecookies56.common.network.packet.server.magics.SetKH1Fire;
import uk.co.wehavecookies56.client.core.helper.GuiHelper;
import uk.co.wehavecookies56.common.core.helper.TextHelper;

public class GuiMenu_Config extends GuiMenu_Bars {

	GuiColourTextField r, g, b;
	GuiButton back, showHearts, musicToggle, fire;

	final int R = 0, G = 1, B = 2;
	final int BACK = 0, HEARTS = 1, MUSIC = 2, FIRE = 3;

	public GuiMenu_Config (String name) {
		super(Strings.Gui_Menu_Config_Title);
	}

	@Override
	protected void actionPerformed (GuiButton button) throws IOException {
		switch (button.id) {
			case BACK: 
				GuiHelper.openMenu();
				break;
			case HEARTS:
				ConfigHandler.EnableHeartsOnHUD = ConfigHandler.EnableHeartsOnHUD ? false : true ;
				ConfigHandler.EnableHeartsOnHUDProperty.set(ConfigHandler.EnableHeartsOnHUD);
				ConfigHandler.config.save();
				showHearts.displayString = String.valueOf(ConfigHandler.EnableHeartsOnHUD);
				break;
			case MUSIC:
				ConfigHandler.EnableCustomMusic = ConfigHandler.EnableCustomMusic ? false : true ;
				ConfigHandler.EnableCustomMusicProperty.set(ConfigHandler.EnableCustomMusic);
				ConfigHandler.config.save();
				musicToggle.displayString = String.valueOf(ConfigHandler.EnableCustomMusic);
				break;
			case FIRE:
				if(Minecraft.getMinecraft().thePlayer.getCapability(ModCapabilities.MAGIC_STATE, null).getKH1Fire() == true)
					PacketDispatcher.sendToServer(new SetKH1Fire(false));
				else
					PacketDispatcher.sendToServer(new SetKH1Fire(true));
				fire.displayString = String.valueOf(Minecraft.getMinecraft().thePlayer.getCapability(ModCapabilities.MAGIC_STATE, null).getKH1Fire());
				break;
		}
		//System.out.println(ExtendedPlayer.get(Minecraft.getMinecraft().thePlayer).isKH1Fire);
		updateButtons();
	}

	private void updateButtons () {
		updateScreen();
	}

	@Override
	public void initGui () {
		super.initGui();
		this.drawPlayerInfo = false;
		int boxWidth = 30;
		int rPosX = 15;
		int gPosX = rPosX + boxWidth + 15;
		int bPosX = gPosX + boxWidth + 15;
		r = new GuiColourTextField(R, mc.fontRendererObj, rPosX, 100, boxWidth, 10);
		g = new GuiColourTextField(G, mc.fontRendererObj, gPosX, 100, boxWidth, 10);
		b = new GuiColourTextField(B, mc.fontRendererObj, bPosX, 100, boxWidth, 10);
		buttonList.add(showHearts = new GuiButton(HEARTS, mc.fontRendererObj.getStringWidth(TextHelper.localize(Strings.Gui_Menu_Config_Hearts)) + 15, 115, 100, 20, String.valueOf(ConfigHandler.EnableHeartsOnHUD)));
		buttonList.add(musicToggle = new GuiButton(MUSIC, mc.fontRendererObj.getStringWidth(TextHelper.localize(Strings.Gui_Menu_Config_Music)) + 15, 135, 100, 20, String.valueOf(ConfigHandler.EnableCustomMusic)));
		buttonList.add(fire = new GuiButton(FIRE, mc.fontRendererObj.getStringWidth(TextHelper.localize(Strings.Gui_Menu_Config_Fire)) + 15, 155, 100, 20, String.valueOf(Minecraft.getMinecraft().thePlayer.getCapability(ModCapabilities.MAGIC_STATE, null).getKH1Fire())));
		//back = new GuiButton(BACK, 10, 0, 100, 20, TextHelper.localize(Strings.Gui_Menu_Items_Button_Back));
		buttonList.add(back = new GuiButton(BACK, 5, (mc.displayHeight / 2) - (mc.displayHeight / 8), 100, 20, TextHelper.localize(Strings.Gui_Menu_Items_Button_Back)));
		this.r.setText(String.valueOf(ConfigHandler.interfaceColour[0]));
		this.g.setText(String.valueOf(ConfigHandler.interfaceColour[1]));
		this.b.setText(String.valueOf(ConfigHandler.interfaceColour[2]));
		updateButtons();
	}

	@Override
	protected void keyTyped (char typedChar, int keyCode) throws IOException {
		this.r.textboxKeyTyped(typedChar, keyCode);
		this.g.textboxKeyTyped(typedChar, keyCode);
		this.b.textboxKeyTyped(typedChar, keyCode);
		try {
			int[] colour = { Integer.parseInt(this.r.getText()), Integer.parseInt(this.g.getText()), Integer.parseInt(this.b.getText()) };
			ConfigHandler.interfaceColour = colour;
			ConfigHandler.interfaceColourProperty.set(ConfigHandler.interfaceColour);
			ConfigHandler.config.save();
		} catch (NumberFormatException e) {

		}
		super.keyTyped(typedChar, keyCode);
	}

	@Override
	public void onGuiClosed () {
		ConfigHandler.config.save();
		super.onGuiClosed();
	}

	@Override
	protected void mouseClicked (int mouseX, int mouseY, int mouseButton) throws IOException {
		this.r.mouseClicked(mouseX, mouseY, mouseButton);
		this.g.mouseClicked(mouseX, mouseY, mouseButton);
		this.b.mouseClicked(mouseX, mouseY, mouseButton);
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	@Override
	public void drawScreen (int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.drawString(mc.fontRendererObj, TextHelper.localize(Strings.Gui_Menu_Config_Hearts), 5, 120, 0xFFFFFF);
		this.drawString(mc.fontRendererObj, TextHelper.localize(Strings.Gui_Menu_Config_Music), 5, 140, 0xFFFFFF);
		this.drawString(mc.fontRendererObj, TextHelper.localize(Strings.Gui_Menu_Config_Fire), 5, 160, 0xFFFFFF);

		this.drawString(mc.fontRendererObj, TextHelper.localize(Strings.Gui_Menu_Config_Colour_Desc), 5, 80, 0xFFFFFF);
		this.r.drawTextBox();
		this.drawString(mc.fontRendererObj, "R:", 5, 101, 0xFFFFFF);
		this.g.drawTextBox();
		this.drawString(mc.fontRendererObj, "G:", 50, 101, 0xFFFFFF);
		this.b.drawTextBox();
		this.drawString(mc.fontRendererObj, "B:", 95, 101, 0xFFFFFF);

	}

	@Override
	public void updateScreen () {
		this.r.updateCursorCounter();
		this.g.updateCursorCounter();
		this.b.updateCursorCounter();
		super.updateScreen();
	}

	public class GuiColourTextField extends GuiTextField {

		public GuiColourTextField (int componentId, FontRenderer fontrendererObj, int x, int y, int par5Width, int par6Height) {
			super(componentId, fontrendererObj, x, y, par5Width, par6Height);
			this.setMaxStringLength(3);
		}

		public boolean isNumber (char c) {
			try {
				Integer.parseInt(String.valueOf(c));
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		}

		@Override
		public boolean textboxKeyTyped (char c, int id) {
			switch (id) {
				case Keyboard.KEY_BACK:
					this.deleteFromCursor(0);
					break;
				case Keyboard.KEY_LEFT:
					this.moveCursorBy(0);
					break;
				case Keyboard.KEY_RIGHT:
					this.moveCursorBy(0);
					break;
				default:
					if (isNumber(c)) {
						String text = new StringBuilder(this.getText()).insert(this.getCursorPosition(), c).toString();
						if (Integer.parseInt(text) > 255) {
							return false;
						}
					} else {
						return false;
					}
					break;
			}
			return super.textboxKeyTyped(c, id);
		}
	}

}
