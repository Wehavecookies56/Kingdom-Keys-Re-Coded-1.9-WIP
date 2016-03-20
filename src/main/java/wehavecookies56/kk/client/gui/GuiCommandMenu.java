package wehavecookies56.kk.client.gui;

import java.io.IOException;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wehavecookies56.kk.KingdomKeys;
import wehavecookies56.kk.capabilities.DriveStateCapability.IDriveState;
import wehavecookies56.kk.capabilities.PlayerStatsCapability.IPlayerStats;
import wehavecookies56.kk.entities.ExtendedPlayer;
import wehavecookies56.kk.lib.Constants;
import wehavecookies56.kk.lib.Reference;
import wehavecookies56.kk.lib.Strings;
import wehavecookies56.kk.util.EventHandler;
import wehavecookies56.kk.util.TextHelper;

@SideOnly (Side.CLIENT)
public class GuiCommandMenu extends GuiScreen {
	Minecraft mc = Minecraft.getMinecraft();

	public static final int TOP = 5, ATTACK = 4, MAGIC = 3, ITEMS = 2, DRIVE = 1;

	public static final int MAGIC_TOP = 8, FIRE = 7, BLIZZARD = 6, THUNDER = 5, CURE = 4, GRAVITY = 3, AERO = 2, STOP = 1;
	public static final int POTION_TOP = 6, POTION1 = 5, POTION2 = 4, POTION3 = 3, POTION4 = 2, POTION5 = 1;
	// int selected = ATTACK;

	int TOP_WIDTH = 70;
	int TOP_HEIGHT = 15;

	int MENU_WIDTH = 71;
	int MENU_HEIGHT = 15;

	int textX = 0;

	public static List<String> driveCommands;
	public static List<String> spells;
	public static List<String> items;

	public static final int SUB_MAIN = 0, SUB_MAGIC = 1, SUB_ITEMS = 2, SUB_DRIVE = 3;

	public static final int NONE = 0;
	public static int selected = ATTACK;
	public static int submenu = 0;
	public static int magicselected = 0;
	public static int potionselected = 0;
	public static int driveselected = 0;
	public static boolean FireUnlocked = true, BlizzardUnlocked, ThunderUnlocked, CureUnlocked, GravityUnlocked, AeroUnlocked, StopUnlocked, ValorUnlocked, WisdomUnlocked, LimitUnlocked, MasterUnlocked, FinalUnlocked;

	ResourceLocation texture = new ResourceLocation(Reference.MODID, "textures/gui/commandmenu.png");

	@SubscribeEvent (priority = EventPriority.NORMAL)
	public void onRenderOverlayPost (RenderGameOverlayEvent event) {
		if (event.type == RenderGameOverlayEvent.ElementType.TEXT && !mc.ingameGUI.getChatGUI().getChatOpen()) {
			GL11.glPushMatrix();
			{
				// drawTexturedModalRect(0, mc.displayHeight/2 - MENU_HEIGHT,
				// TOP_WIDTH, 0, TOP_WIDTH + MENU_WIDTH, MENU_HEIGHT);
				drawCommandMenu(event.resolution.getScaledWidth(), event.resolution.getScaledHeight());
			}
			GL11.glPopMatrix();
		}
	}

	@Override
	public void initGui () {
		super.initGui();
	}

	@Override
	public void handleMouseInput () throws IOException {
		int i = Mouse.getEventDWheel();

		super.handleMouseInput();
	}

	@Override
	public void updateScreen () {
		super.updateScreen();
	}

	public void drawCommandMenu (int width, int height) {
		IDriveState DS = mc.thePlayer.getCapability(KingdomKeys.DRIVE_STATE, null);
		IPlayerStats STATS = mc.thePlayer.getCapability(KingdomKeys.PLAYER_STATS, null);

		// Magic:"+magicselected+" Drive:"+driveselected);
		//System.out.println("Is KH1 Fire?: "+ExtendedPlayer.get(Minecraft.getMinecraft().thePlayer).getKH1Fire());
		float scale = 1.05f;
		int colour;
		// DRIVE
		GL11.glPushMatrix();
		{
			int u;
			int v = 0;
			int x = 0;

			mc.renderEngine.bindTexture(texture);
			GL11.glTranslatef(x, (height - MENU_HEIGHT * scale * DRIVE), 0);
			GL11.glScalef(scale, scale, scale);
			if (submenu != 0) GL11.glColor3ub((byte) 80, (byte) 80, (byte) 80);
			if (selected == DRIVE) { // Selected
				textX = 5;
				if (EventHandler.isHostiles)
					drawTexturedModalRect(5, 0, TOP_WIDTH, 30, TOP_WIDTH + MENU_WIDTH, v + MENU_HEIGHT);
				else
					drawTexturedModalRect(5, 0, TOP_WIDTH, MENU_HEIGHT, TOP_WIDTH + MENU_WIDTH, v + MENU_HEIGHT);

			} else { // Not selected
				textX = 0;

				if (EventHandler.isHostiles)
					drawTexturedModalRect(0, 0, 0, 30, TOP_WIDTH, v + MENU_HEIGHT);
				else
					drawTexturedModalRect(0, 0, TOP_WIDTH, 0, TOP_WIDTH + MENU_WIDTH, v + MENU_HEIGHT);
			}

			if (DS.getInDrive()) {
				if (DS.getActiveDriveName().equals(Strings.Form_Anti))
					drawString(mc.fontRendererObj, TextHelper.localize(Strings.Gui_CommandMenu_Drive_Revert), 6 + textX, 4, 0x888888);
				else
					drawString(mc.fontRendererObj, TextHelper.localize(Strings.Gui_CommandMenu_Drive_Revert), 6 + textX, 4, 0xFFFFFF);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			} else if (STATS.getDriveFormsList().isEmpty() || STATS.getDP() <= 0)
				drawString(mc.fontRendererObj, TextHelper.localize(Strings.Gui_CommandMenu_Drive), 6 + textX, 4, 0x888888);
			else
				drawString(mc.fontRendererObj, TextHelper.localize(Strings.Gui_CommandMenu_Drive), 6 + textX, 4, 0xFFFFFF);

		}
		GL11.glPopMatrix();
		// ITEMS
		GL11.glPushMatrix();
		{
			int u;
			int v = 0;
			int x = 0;

			mc.renderEngine.bindTexture(texture);
			GL11.glTranslatef(x, (height - MENU_HEIGHT * scale * ITEMS), 0);
			GL11.glScalef(scale, scale, scale);
			if (submenu != 0) GL11.glColor3ub((byte) 80, (byte) 80, (byte) 80);
			if (selected == ITEMS) { // Selected
				textX = 5;
				if (EventHandler.isHostiles)
					drawTexturedModalRect(5, 0, TOP_WIDTH, 30, TOP_WIDTH + MENU_WIDTH, v + MENU_HEIGHT);
				else
					drawTexturedModalRect(5, 0, TOP_WIDTH, MENU_HEIGHT, TOP_WIDTH + MENU_WIDTH, v + MENU_HEIGHT);

			} else { // Not selected
				textX = 0;

				if (EventHandler.isHostiles)
					drawTexturedModalRect(0, 0, 0, 30, TOP_WIDTH, v + MENU_HEIGHT);
				else
					drawTexturedModalRect(0, 0, TOP_WIDTH, 0, TOP_WIDTH + MENU_WIDTH, v + MENU_HEIGHT);
			}

			if (STATS.getItemsList().isEmpty())
				drawString(mc.fontRendererObj, TextHelper.localize(Strings.Gui_CommandMenu_Items), 6 + textX, 4, 0x888888);
			else
				drawString(mc.fontRendererObj, TextHelper.localize(Strings.Gui_CommandMenu_Items), 6 + textX, 4, 0xFFFFFF);

			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		}
		GL11.glPopMatrix();
		// MAGIC
		GL11.glPushMatrix();
		{
			int u;
			int v = 0;
			int x = 0;
			mc.renderEngine.bindTexture(texture);
			GL11.glTranslatef(x, (height - MENU_HEIGHT * scale * MAGIC), 0);
			GL11.glScalef(scale, scale, scale);
			if (submenu != 0) GL11.glColor3ub((byte) 80, (byte) 80, (byte) 80);
			if (selected == MAGIC) { // Selected
				textX = 5;
				if (EventHandler.isHostiles)
					drawTexturedModalRect(5, 0, TOP_WIDTH, 30, TOP_WIDTH + MENU_WIDTH, v + MENU_HEIGHT);
				else
					drawTexturedModalRect(5, 0, TOP_WIDTH, MENU_HEIGHT, TOP_WIDTH + MENU_WIDTH, v + MENU_HEIGHT);

			} else { // Not selected
				textX = 0;

				if (EventHandler.isHostiles)
					drawTexturedModalRect(0, 0, 0, 30, TOP_WIDTH, v + MENU_HEIGHT);
				else
					drawTexturedModalRect(0, 0, TOP_WIDTH, 0, TOP_WIDTH + MENU_WIDTH, v + MENU_HEIGHT);
			}

			if (spells == null) {
				drawString(mc.fontRendererObj, TextHelper.localize(Strings.Gui_CommandMenu_Magic), 6 + textX, 4, 0x888888);
			} else {
				//if (!ExtendedPlayer.get(mc.thePlayer).getRecharge() && !spells.isEmpty() && !ExtendedPlayer.get(mc.thePlayer).getDriveInUse().equals("Valor"))
				if (!STATS.getRecharge() && (!STATS.getSpellsList().isEmpty() && !DS.getActiveDriveName().equals(Strings.Form_Valor))) 

					drawString(mc.fontRendererObj, TextHelper.localize(Strings.Gui_CommandMenu_Magic), 6 + textX, 4, 0xFFFFFF);
				else
					drawString(mc.fontRendererObj, TextHelper.localize(Strings.Gui_CommandMenu_Magic), 6 + textX, 4, 0x888888);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			}
		}
		GL11.glPopMatrix();
		// ATTACK
		GL11.glPushMatrix();
		{
			int u;
			int v = 0;
			int x = 0;
			mc.renderEngine.bindTexture(texture);
			GL11.glTranslatef(x, (height - MENU_HEIGHT * scale * ATTACK), 0);
			GL11.glScalef(scale, scale, scale);

			if (submenu != 0) GL11.glColor3ub((byte) 80, (byte) 80, (byte) 80);

			if (selected == ATTACK) { // Selected

				textX = 5;
				if (EventHandler.isHostiles)
					drawTexturedModalRect(5, 0, TOP_WIDTH, 30, TOP_WIDTH + MENU_WIDTH, v + MENU_HEIGHT);
				else
					drawTexturedModalRect(5, 0, TOP_WIDTH, MENU_HEIGHT, TOP_WIDTH + MENU_WIDTH, v + MENU_HEIGHT);

			} else { // Not selected
				textX = 0;

				if (EventHandler.isHostiles)
					drawTexturedModalRect(0, 0, 0, 30, TOP_WIDTH, v + MENU_HEIGHT);
				else
					drawTexturedModalRect(0, 0, TOP_WIDTH, 0, TOP_WIDTH + MENU_WIDTH, v + MENU_HEIGHT);
			}
			drawString(mc.fontRendererObj, TextHelper.localize(Strings.Gui_CommandMenu_Attack), 6 + textX, 4, 0xFFFFFF);

		}
		GL11.glPopMatrix();
		// TOP
		GL11.glPushMatrix();
		{
			mc.renderEngine.bindTexture(texture);
			GL11.glTranslatef(0, (height - MENU_HEIGHT * scale * TOP), 0);
			GL11.glScalef(scale, scale, scale);
			if (submenu != 0) GL11.glColor3ub((byte) 80, (byte) 80, (byte) 80);
			if (EventHandler.isHostiles)
				drawTexturedModalRect(0, 0, 0, 15, TOP_WIDTH, TOP_HEIGHT);
			else
				drawTexturedModalRect(0, 0, 0, 0, TOP_WIDTH, TOP_HEIGHT);
			drawString(mc.fontRendererObj, TextHelper.localize(Strings.Gui_CommandMenu_Command), 6, 4, 0xFFFFFF);
		}
		GL11.glPopMatrix();
		// Magic submenu //
		spells = STATS.getSpellsList();
		if (spells == null) {} else if (!spells.isEmpty()) {
			// MAGIC TOP
			GL11.glPushMatrix();
			{
				mc.renderEngine.bindTexture(texture);
				GL11.glTranslatef(5, (height - MENU_HEIGHT * scale * (spells.size() + 1)), 0);
				GL11.glScalef(scale, scale, scale);
				if (submenu == SUB_MAGIC) {
					drawTexturedModalRect(0, 0, 0, 0, TOP_WIDTH, TOP_HEIGHT);
					drawString(mc.fontRendererObj, TextHelper.localize(Strings.Gui_CommandMenu_Magic_Title), 6, 4, 0xFFFFFF);
				}
			}
			GL11.glPopMatrix();
			for (int i = 0; i < spells.size(); i++) {
				GL11.glPushMatrix();
				{
					int u;
					int v;
					int x;
					x = (magicselected == i) ? 10 : 5;

					mc.renderEngine.bindTexture(texture);
					GL11.glTranslatef(x, (height - MENU_HEIGHT * scale * (spells.size() - i)), 0);
					GL11.glScalef(scale, scale, scale);
					if (submenu == SUB_MAGIC) {
						v = 0;
						if (magicselected == i)
							drawTexturedModalRect(0, 0, TOP_WIDTH, 15, TOP_WIDTH + MENU_WIDTH, v + MENU_HEIGHT);
						else
							drawTexturedModalRect(0, 0, TOP_WIDTH, 0, TOP_WIDTH + MENU_WIDTH, v + MENU_HEIGHT);
						colour = Constants.getCost(spells.get(i)) < STATS.getMP() ? 0xFFFFFF : 0xFF9900;
						if (spells.get(i).equals(Strings.Spell_Cure)) colour = 0xFF9900;
						colour = STATS.getMP() < 1 ? 0x888888 : colour;

						String magic = spells.get(i);
						int level = mc.thePlayer.getCapability(KingdomKeys.MAGIC_STATE, null).getMagicLevel(magic);
						String magicName = Constants.getMagicName(magic, level);
						drawString(mc.fontRendererObj, TextHelper.localize(magicName), 6, 4, colour);
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					}
				}
				GL11.glPopMatrix();
			}
		}
		// Items submenu //
		items = STATS.getItemsList();
		if (items == null) {} else if (!items.isEmpty()) {
			// Items TOP
			GL11.glPushMatrix();
			{
				mc.renderEngine.bindTexture(texture);
				GL11.glTranslatef(5, (height - MENU_HEIGHT * scale * (items.size() + 1)), 0);
				GL11.glScalef(scale, scale, scale);
				if (submenu == SUB_ITEMS) {
					drawTexturedModalRect(0, 0, 0, 0, TOP_WIDTH, TOP_HEIGHT);
					drawString(mc.fontRendererObj, TextHelper.localize("ITEMS"), 6, 4, 0xFFFFFF);
				}

			}
			GL11.glPopMatrix();
			for (int i = 0; i < items.size(); i++) {
				GL11.glPushMatrix();
				{
					int u;
					int v;
					int x;
					x = (potionselected == i) ? 10 : 5;

					mc.renderEngine.bindTexture(texture);
					GL11.glTranslatef(x, (height - MENU_HEIGHT * scale * (items.size() - i)), 0);
					GL11.glScalef(scale, scale, scale);
					if (submenu == SUB_ITEMS) {
						v = 0;
						if (potionselected == i)
							drawTexturedModalRect(0, 0, TOP_WIDTH, 15, TOP_WIDTH + MENU_WIDTH, v + MENU_HEIGHT);
						else
							drawTexturedModalRect(0, 0, TOP_WIDTH, 0, TOP_WIDTH + MENU_WIDTH, v + MENU_HEIGHT);
						drawString(mc.fontRendererObj, TextHelper.localize("item." + items.get(i) + ".name"), 6, 4, 0xFFFFFF);

						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					}
				}
				GL11.glPopMatrix();
			}
		}

		ExtendedPlayer.get(mc.thePlayer);
		// Drive form submenu //
		driveCommands = STATS.getDriveFormsList();
		if (driveCommands == null) {} else if (!driveCommands.isEmpty()) {
			// DRIVE TOP
			GL11.glPushMatrix();
			{
				mc.renderEngine.bindTexture(texture);
				GL11.glTranslatef(5, (height - MENU_HEIGHT * scale * (driveCommands.size() + 1)), 0);
				GL11.glScalef(1.25f, scale, scale);
				if (submenu == SUB_DRIVE) drawTexturedModalRect(0, 0, 0, 0, TOP_WIDTH, TOP_HEIGHT);
			}
			GL11.glPopMatrix();

			GL11.glPushMatrix();
			{
				GL11.glTranslatef(5, (height - MENU_HEIGHT * scale * (driveCommands.size() + 1)), 0);
				GL11.glScalef(scale, scale, scale);
				if (submenu == SUB_DRIVE) drawString(mc.fontRendererObj, TextHelper.localize(Strings.Gui_CommandMenu_Drive_Title), 6, 4, 0xFFFFFF);
			}
			GL11.glPopMatrix();
			for (int i = 0; i < driveCommands.size(); i++) {
				GL11.glPushMatrix();
				{
					int u;
					int v;
					int x;
					x = (driveselected == i) ? 10 : 5;
					v = (driveselected == i) ? MENU_HEIGHT : 0;

					mc.renderEngine.bindTexture(texture);
					GL11.glTranslatef(x, (height - MENU_HEIGHT * scale * (driveCommands.size() - i)), 0);
					GL11.glScalef(1.25f, scale, scale);
					if (submenu == SUB_DRIVE) {
						v = 0;
						if (driveselected == i)
							drawTexturedModalRect(0, 0, TOP_WIDTH, 15, TOP_WIDTH + MENU_WIDTH, v + MENU_HEIGHT);
						else
							drawTexturedModalRect(0, 0, TOP_WIDTH, 0, TOP_WIDTH + MENU_WIDTH, v + MENU_HEIGHT);
					}
				}
				GL11.glPopMatrix();

				GL11.glPushMatrix();
				{
					int x;
					x = (driveselected == i) ? 10 : 5;
					GL11.glTranslatef(x, (height - MENU_HEIGHT * scale * (driveCommands.size() - i)), 0);
					GL11.glScalef(scale, scale, scale);
					if (submenu == SUB_DRIVE) {
						if (STATS.getDP() >= Constants.getCost(driveCommands.get(i)) || mc.thePlayer.getCapability(KingdomKeys.PLAYER_STATS, null).getCheatMode())
							drawString(mc.fontRendererObj, TextHelper.localize(driveCommands.get(i)), 6, 4, 0xFFFFFF);
						else
							drawString(mc.fontRendererObj, TextHelper.localize(driveCommands.get(i)), 6, 4, 0x888888);
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					}
				}
				GL11.glPopMatrix();
			}
		}
	}

}
