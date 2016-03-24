package wehavecookies56.kk.util;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import com.mojang.authlib.GameProfile;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase.NBTPrimitive;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.RenderItemInFrameEvent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wehavecookies56.kk.KingdomKeys;
import wehavecookies56.kk.achievements.ModAchievements;
import wehavecookies56.kk.api.driveforms.DriveFormRegistry;
import wehavecookies56.kk.api.materials.MaterialRegistry;
import wehavecookies56.kk.api.recipes.RecipeRegistry;
import wehavecookies56.kk.block.ModBlocks;
import wehavecookies56.kk.capabilities.CheatModeCapability.ICheatMode;
import wehavecookies56.kk.capabilities.DriveStateCapability.IDriveState;
import wehavecookies56.kk.capabilities.FirstTimeJoinCapability.IFirstTimeJoin;
import wehavecookies56.kk.capabilities.MagicStateCapability.IMagicState;
import wehavecookies56.kk.capabilities.MunnyCapability.IMunny;
import wehavecookies56.kk.capabilities.PlayerStatsCapability.IPlayerStats;
import wehavecookies56.kk.capabilities.SummonKeybladeCapability.ISummonKeyblade;
import wehavecookies56.kk.capabilities.SynthesisMaterialCapability.ISynthesisMaterial;
import wehavecookies56.kk.capabilities.SynthesisRecipeCapability.ISynthesisRecipe;
import wehavecookies56.kk.entities.magic.EntityThunder;
import wehavecookies56.kk.inventory.InventorySynthesisBagL;
import wehavecookies56.kk.inventory.InventorySynthesisBagM;
import wehavecookies56.kk.inventory.InventorySynthesisBagS;
import wehavecookies56.kk.item.ItemHpOrb;
import wehavecookies56.kk.item.ItemKeyblade;
import wehavecookies56.kk.item.ItemMunny;
import wehavecookies56.kk.item.ItemSynthesisMaterial;
import wehavecookies56.kk.item.ModItems;
import wehavecookies56.kk.lib.Reference;
import wehavecookies56.kk.lib.Strings;
import wehavecookies56.kk.network.packet.PacketDispatcher;
import wehavecookies56.kk.network.packet.client.ShowOverlayPacket;
import wehavecookies56.kk.network.packet.client.SyncDriveData;
import wehavecookies56.kk.network.packet.client.SyncDriveInventory;
import wehavecookies56.kk.network.packet.client.SyncItemsInventory;
import wehavecookies56.kk.network.packet.client.SyncKeybladeData;
import wehavecookies56.kk.network.packet.client.SyncMagicData;
import wehavecookies56.kk.network.packet.client.SyncMagicInventory;
import wehavecookies56.kk.network.packet.client.SyncMunnyData;
import wehavecookies56.kk.network.packet.server.DeSummonKeyblade;
import wehavecookies56.kk.network.packet.server.DriveOrbPickup;
import wehavecookies56.kk.network.packet.server.HpOrbPickup;
import wehavecookies56.kk.network.packet.server.MagicOrbPickup;
import wehavecookies56.kk.network.packet.server.MunnyPickup;

public class EventHandler {
	
	@SubscribeEvent
	public void onEntityConstructing (AttachCapabilitiesEvent.Entity event) {
		event.addCapability(new ResourceLocation(Reference.MODID, "IMunny"), new ICapabilitySerializable<NBTPrimitive>()
        {
            IMunny inst = KingdomKeys.MUNNY.getDefaultInstance();
            @Override
            public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
                return capability == KingdomKeys.MUNNY;
            }

            @SuppressWarnings("unchecked")
            @Override
            public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
                return capability == KingdomKeys.MUNNY ? (T)inst : null;
            }

            @Override
            public NBTPrimitive serializeNBT() {
                return (NBTPrimitive)KingdomKeys.MUNNY.getStorage().writeNBT(KingdomKeys.MUNNY, inst, null);
            }

            @Override
            public void deserializeNBT(NBTPrimitive nbt) {
            	KingdomKeys.MUNNY.getStorage().readNBT(KingdomKeys.MUNNY, inst, null, nbt);
            }
        });
		
		event.addCapability(new ResourceLocation(Reference.MODID, "ICheatMode"), new ICapabilitySerializable<NBTPrimitive>()
        {
            ICheatMode inst = KingdomKeys.CHEAT_MODE.getDefaultInstance();
            @Override
            public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
                return capability == KingdomKeys.CHEAT_MODE;
            }

            @SuppressWarnings("unchecked")
            @Override
            public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
                return capability == KingdomKeys.CHEAT_MODE ? (T)inst : null;
            }

            @Override
            public NBTPrimitive serializeNBT() {
                return (NBTPrimitive)KingdomKeys.CHEAT_MODE.getStorage().writeNBT(KingdomKeys.CHEAT_MODE, inst, null);
            }

            @Override
            public void deserializeNBT(NBTPrimitive nbt) {
            	KingdomKeys.CHEAT_MODE.getStorage().readNBT(KingdomKeys.CHEAT_MODE, inst, null, nbt);
            }
        });
		
		event.addCapability(new ResourceLocation(Reference.MODID, "IPlayerStats"), new ICapabilitySerializable<NBTTagCompound>()
        {
            IPlayerStats inst = KingdomKeys.PLAYER_STATS.getDefaultInstance();
            @Override
            public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
                return capability == KingdomKeys.PLAYER_STATS;
            }

            @SuppressWarnings("unchecked")
            @Override
            public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
                return capability == KingdomKeys.PLAYER_STATS ? (T)inst : null;
            }

            @Override
            public NBTTagCompound serializeNBT() {
                return (NBTTagCompound)KingdomKeys.PLAYER_STATS.getStorage().writeNBT(KingdomKeys.PLAYER_STATS, inst, null);
            }

            @Override
            public void deserializeNBT(NBTTagCompound nbt) {
            	KingdomKeys.PLAYER_STATS.getStorage().readNBT(KingdomKeys.PLAYER_STATS, inst, null, nbt);
            }
        });
		
		event.addCapability(new ResourceLocation(Reference.MODID, "ISummonKeyblade"), new ICapabilitySerializable<NBTTagCompound>()
        {
            ISummonKeyblade inst = KingdomKeys.SUMMON_KEYBLADE.getDefaultInstance();
            @Override
            public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
                return capability == KingdomKeys.SUMMON_KEYBLADE;
            }

            @SuppressWarnings("unchecked")
            @Override
            public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
                return capability == KingdomKeys.SUMMON_KEYBLADE ? (T)inst : null;
            }

            @Override
            public NBTTagCompound serializeNBT() {
                return (NBTTagCompound)KingdomKeys.SUMMON_KEYBLADE.getStorage().writeNBT(KingdomKeys.SUMMON_KEYBLADE, inst, null);
            }

            @Override
            public void deserializeNBT(NBTTagCompound nbt) {
            	KingdomKeys.SUMMON_KEYBLADE.getStorage().readNBT(KingdomKeys.SUMMON_KEYBLADE, inst, null, nbt);
            }
        });
		
		event.addCapability(new ResourceLocation(Reference.MODID, "IMagicState"), new ICapabilitySerializable<NBTTagCompound>()
        {
			IMagicState inst = KingdomKeys.MAGIC_STATE.getDefaultInstance();
            @Override
            public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
                return capability == KingdomKeys.MAGIC_STATE;
            }

            @SuppressWarnings("unchecked")
            @Override
            public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
                return capability == KingdomKeys.MAGIC_STATE ? (T)inst : null;
            }

            @Override
            public NBTTagCompound serializeNBT() {
                return (NBTTagCompound)KingdomKeys.MAGIC_STATE.getStorage().writeNBT(KingdomKeys.MAGIC_STATE, inst, null);
            }

            @Override
            public void deserializeNBT(NBTTagCompound nbt) {
            	KingdomKeys.MAGIC_STATE.getStorage().readNBT(KingdomKeys.MAGIC_STATE, inst, null, nbt);
            }
        });
		
		event.addCapability(new ResourceLocation(Reference.MODID, "IDriveState"), new ICapabilitySerializable<NBTTagCompound>()
        {
			IDriveState inst = KingdomKeys.DRIVE_STATE.getDefaultInstance();
            @Override
            public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
                return capability == KingdomKeys.DRIVE_STATE;
            }

            @SuppressWarnings("unchecked")
            @Override
            public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
                return capability == KingdomKeys.DRIVE_STATE ? (T)inst : null;
            }

            @Override
            public NBTTagCompound serializeNBT() {
                return (NBTTagCompound)KingdomKeys.DRIVE_STATE.getStorage().writeNBT(KingdomKeys.DRIVE_STATE, inst, null);
            }

            @Override
            public void deserializeNBT(NBTTagCompound nbt) {
            	KingdomKeys.DRIVE_STATE.getStorage().readNBT(KingdomKeys.DRIVE_STATE, inst, null, nbt);
            }
        });
		
		event.addCapability(new ResourceLocation(Reference.MODID, "IFirstTimeJoin"), new ICapabilitySerializable<NBTTagCompound>()
        {
			IFirstTimeJoin inst = KingdomKeys.FIRST_TIME_JOIN.getDefaultInstance();
            @Override
            public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
                return capability == KingdomKeys.FIRST_TIME_JOIN;
            }

            @SuppressWarnings("unchecked")
            @Override
            public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
                return capability == KingdomKeys.FIRST_TIME_JOIN ? (T)inst : null;
            }

            @Override
            public NBTTagCompound serializeNBT() {
                return (NBTTagCompound)KingdomKeys.FIRST_TIME_JOIN.getStorage().writeNBT(KingdomKeys.FIRST_TIME_JOIN, inst, null);
            }

            @Override
            public void deserializeNBT(NBTTagCompound nbt) {
            	KingdomKeys.FIRST_TIME_JOIN.getStorage().readNBT(KingdomKeys.FIRST_TIME_JOIN, inst, null, nbt);
            }
        });
		
		event.addCapability(new ResourceLocation(Reference.MODID, "ISynthesisRecipe"), new ICapabilitySerializable<NBTTagCompound>()
        {
			ISynthesisRecipe inst = KingdomKeys.SYNTHESIS_RECIPES.getDefaultInstance();
            @Override
            public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
                return capability == KingdomKeys.SYNTHESIS_RECIPES;
            }

            @SuppressWarnings("unchecked")
            @Override
            public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
                return capability == KingdomKeys.SYNTHESIS_RECIPES ? (T)inst : null;
            }

            @Override
            public NBTTagCompound serializeNBT() {
                return (NBTTagCompound)KingdomKeys.SYNTHESIS_RECIPES.getStorage().writeNBT(KingdomKeys.SYNTHESIS_RECIPES, inst, null);
            }

            @Override
            public void deserializeNBT(NBTTagCompound nbt) {
            	KingdomKeys.SYNTHESIS_RECIPES.getStorage().readNBT(KingdomKeys.SYNTHESIS_RECIPES, inst, null, nbt);
            }
        });
		
		event.addCapability(new ResourceLocation(Reference.MODID, "ISynthesisMaterial"), new ICapabilitySerializable<NBTTagCompound>()
		        {
					ISynthesisMaterial inst = KingdomKeys.SYNTHESIS_MATERIALS.getDefaultInstance();
		            @Override
		            public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		                return capability == KingdomKeys.SYNTHESIS_MATERIALS;
		            }

		            @SuppressWarnings("unchecked")
		            @Override
		            public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		                return capability == KingdomKeys.SYNTHESIS_MATERIALS ? (T)inst : null;
		            }

		            @Override
		            public NBTTagCompound serializeNBT() {
		                return (NBTTagCompound)KingdomKeys.SYNTHESIS_MATERIALS.getStorage().writeNBT(KingdomKeys.SYNTHESIS_MATERIALS, inst, null);
		            }

		            @Override
		            public void deserializeNBT(NBTTagCompound nbt) {
		            	KingdomKeys.SYNTHESIS_MATERIALS.getStorage().readNBT(KingdomKeys.SYNTHESIS_MATERIALS, inst, null, nbt);
		            }
		        });

		//if (event.entity instanceof EntityPlayer && ExtendedPlayer.get((EntityPlayer) event.entity) == null) ExtendedPlayer.register((EntityPlayer) event.entity);
		//if (event.entity instanceof EntityPlayer && ExtendedPlayerRecipes.get((EntityPlayer) event.entity) == null) ExtendedPlayerRecipes.register((EntityPlayer) event.entity);
		//if (event.entity instanceof EntityPlayer && ExtendedPlayerMaterials.get((EntityPlayer) event.entity) == null) ExtendedPlayerMaterials.register((EntityPlayer) event.entity);
	}

	@SideOnly (Side.CLIENT)
	@SubscribeEvent
	public void onRenderItemInFrame (RenderItemInFrameEvent event) {
		if (event.getItem().getItem() != null) if (event.getItem().getItem() instanceof ItemKeyblade) GlStateManager.scale(0.02f, 0.02f, 0.02f);
	}

	@SideOnly (Side.CLIENT)
	@SubscribeEvent
	public void fovUpdate (FOVUpdateEvent event) {
		if (event.getEntity().getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue() == 0) event.setNewfov(1f);
	}

	@SubscribeEvent
	public void addTooltip (ItemTooltipEvent event) {
		Item ghostBlox = Item.getItemFromBlock(ModBlocks.GhostBlox);
		if (event.getItemStack().getItem() == ghostBlox) {
			if (!KeyboardHelper.isShiftDown()) {
				event.getToolTip().add(TextFormatting.ITALIC + TextHelper.localize(Strings.HoldForInfo));
			} else {
				int x = 30;
				String s = TextHelper.localize(Strings.GhostBloxDesc).replace("%s", TextHelper.localize(ModBlocks.GhostBlox.getUnlocalizedName() + ".name"));
				s = s.replaceAll("(.{" + x + ",}?)\\s+", "$1\n");
				String[] splitS = s.split("\n");
				for (String element : splitS)
					event.getToolTip().add(element);
			}
		}
		Item dangerBlox = Item.getItemFromBlock(ModBlocks.DangerBlox);
		if (event.getItemStack().getItem() == dangerBlox) {
			if (!KeyboardHelper.isShiftDown()) {
				event.getToolTip().add(TextFormatting.ITALIC + TextHelper.localize(Strings.HoldForInfo));
			} else {
				int x = 30;
				String s = TextHelper.localize(Strings.DangerBloxDesc).replace("%s", TextHelper.localize(ModBlocks.DangerBlox.getUnlocalizedName() + ".name"));
				s = s.replaceAll("(.{" + x + ",}?)\\s+", "$1\n");
				String[] splitS = s.split("\n");
				for (String element : splitS)
					event.getToolTip().add(element);
			}
		}
		Item bounceBlox = Item.getItemFromBlock(ModBlocks.BounceBlox);
		if (event.getItemStack().getItem() == bounceBlox) {
			if (!KeyboardHelper.isShiftDown()) {
				event.getToolTip().add(TextFormatting.ITALIC + TextHelper.localize(Strings.HoldForInfo));
			} else {
				int x = 30;
				String s = TextHelper.localize(Strings.BounceBloxDesc).replace("%s", TextHelper.localize(ModBlocks.BounceBlox.getUnlocalizedName() + ".name"));
				s = s.replaceAll("(.{" + x + ",}?)\\s+", "$1\n");
				String[] splitS = s.split("\n");
				for (String element : splitS)
					event.getToolTip().add(element);
			}
		}
		Item magnetBlox = Item.getItemFromBlock(ModBlocks.MagnetBlox);
		if (event.getItemStack().getItem() == magnetBlox) {
			if (!KeyboardHelper.isShiftDown()) {
				event.getToolTip().add(TextFormatting.ITALIC + TextHelper.localize(Strings.HoldForInfo));
			} else {
				event.getToolTip().add("This Block is WIP and doesn't work at all.");
				event.getToolTip().add("It won't crash your game though.");
			}
		}
		Item kkchest = Item.getItemFromBlock(ModBlocks.KKChest);
		if (event.getItemStack().getItem() == kkchest) {
			event.getToolTip().add(TextHelper.localize(Strings.KKChestDesc_1));
			if (!KeyboardHelper.isShiftDown())
				event.getToolTip().add(TextFormatting.ITALIC + TextHelper.localize(Strings.HoldForInfo));
			else
				event.getToolTip().add(TextHelper.localize(Strings.KKChestDesc_2));
		}

		Item savepoint = Item.getItemFromBlock(ModBlocks.SavePoint);
		if (event.getItemStack().getItem() == savepoint) if (!KeyboardHelper.isShiftDown())
			event.getToolTip().add(TextFormatting.ITALIC + TextHelper.localize(Strings.HoldForInfo));
		else
			event.getToolTip().add(TextHelper.localize(Strings.SavePointDesc));

	}

	@SubscribeEvent
	public void PlayerClone (PlayerEvent.Clone event) {
		if (event.isWasDeath()) {
			IFirstTimeJoin ftjBefore = event.getOriginal().getCapability(KingdomKeys.FIRST_TIME_JOIN, null);
			IFirstTimeJoin ftjAfter = event.getEntityPlayer().getCapability(KingdomKeys.FIRST_TIME_JOIN, null);
			ftjAfter.setFirstTimeJoin(ftjBefore.getFirstTimeJoin());
			IMunny munnyBefore = event.getOriginal().getCapability(KingdomKeys.MUNNY, null);
			IMunny munnyAfter = event.getEntityPlayer().getCapability(KingdomKeys.MUNNY, null);
			munnyAfter.setMunny(munnyBefore.getMunny());
			IDriveState dsBefore = event.getOriginal().getCapability(KingdomKeys.DRIVE_STATE, null);
			IDriveState dsAfter = event.getEntityPlayer().getCapability(KingdomKeys.DRIVE_STATE, null);
			dsAfter.setActiveDriveName(dsBefore.getActiveDriveName());
			dsAfter.setAntiPoints(dsBefore.getAntiPoints());
			dsAfter.setDriveExp(Strings.Form_Valor, dsBefore.getDriveExp(Strings.Form_Valor));
			dsAfter.setDriveExp(Strings.Form_Wisdom, dsBefore.getDriveExp(Strings.Form_Wisdom));
			dsAfter.setDriveExp(Strings.Form_Limit, dsBefore.getDriveExp(Strings.Form_Limit));
			dsAfter.setDriveExp(Strings.Form_Master, dsBefore.getDriveExp(Strings.Form_Master));
			dsAfter.setDriveExp(Strings.Form_Final, dsBefore.getDriveExp(Strings.Form_Final));
			dsAfter.setDriveLevel(Strings.Form_Valor, dsBefore.getDriveLevel(Strings.Form_Valor));
			dsAfter.setDriveLevel(Strings.Form_Wisdom, dsBefore.getDriveLevel(Strings.Form_Wisdom));
			dsAfter.setDriveLevel(Strings.Form_Limit, dsBefore.getDriveLevel(Strings.Form_Limit));
			dsAfter.setDriveLevel(Strings.Form_Master, dsBefore.getDriveLevel(Strings.Form_Master));
			dsAfter.setDriveLevel(Strings.Form_Final, dsBefore.getDriveLevel(Strings.Form_Final));
			dsAfter.setInDrive(dsBefore.getInDrive());
			for (int i = 0; i < dsBefore.getInventoryDriveForms().getSizeInventory(); i++) {
				dsAfter.getInventoryDriveForms().setInventorySlotContents(i, dsBefore.getInventoryDriveForms().getStackInSlot(i));
			}
			IMagicState magicBefore = event.getOriginal().getCapability(KingdomKeys.MAGIC_STATE, null);
			IMagicState magicAfter = event.getEntityPlayer().getCapability(KingdomKeys.MAGIC_STATE, null);
			magicAfter.setKH1Fire(magicBefore.getKH1Fire());
			magicAfter.setMagicLevel(Strings.Spell_Fire, magicBefore.getMagicLevel(Strings.Spell_Fire));
			magicAfter.setMagicLevel(Strings.Spell_Blizzard, magicBefore.getMagicLevel(Strings.Spell_Blizzard));
			magicAfter.setMagicLevel(Strings.Spell_Thunder, magicBefore.getMagicLevel(Strings.Spell_Thunder));
			magicAfter.setMagicLevel(Strings.Spell_Cure, magicBefore.getMagicLevel(Strings.Spell_Cure));
			magicAfter.setMagicLevel(Strings.Spell_Stop, magicBefore.getMagicLevel(Strings.Spell_Stop));
			magicAfter.setMagicLevel(Strings.Spell_Aero, magicBefore.getMagicLevel(Strings.Spell_Aero));
			for (int i = 0; i < magicBefore.getInventorySpells().getSizeInventory(); i++) {
				magicAfter.getInventorySpells().setInventorySlotContents(i, magicBefore.getInventorySpells().getStackInSlot(i));
			}
			ISummonKeyblade skBefore = event.getOriginal().getCapability(KingdomKeys.SUMMON_KEYBLADE, null);
			ISummonKeyblade skAfter = event.getEntityPlayer().getCapability(KingdomKeys.SUMMON_KEYBLADE, null);
			skAfter.setIsKeybladeSummoned(skBefore.getIsKeybladeSummoned());
			for (int i = 0; i < skBefore.getInventoryKeychain().getSizeInventory(); i++) {
				skAfter.getInventoryKeychain().setInventorySlotContents(i, skBefore.getInventoryKeychain().getStackInSlot(i));
			}
			IPlayerStats statsBefore = event.getOriginal().getCapability(KingdomKeys.PLAYER_STATS, null);
			IPlayerStats statsAfter = event.getEntityPlayer().getCapability(KingdomKeys.PLAYER_STATS, null);
			statsAfter.setDefense(statsBefore.getDefense());
			statsAfter.setDP(statsBefore.getDP());
			statsAfter.setExperience(statsBefore.getExperience());
			statsAfter.setHP(statsBefore.getHP());
			statsAfter.setLevel(statsBefore.getLevel());
			statsAfter.setMagic(statsBefore.getMagic());
			statsAfter.setMaxMP(statsBefore.getMaxMP());
			statsAfter.setMP(statsBefore.getMP());
			statsAfter.setRecharge(statsBefore.getRecharge());
			statsAfter.setStrength(statsBefore.getStrength());
			for (int i = 0; i < statsBefore.getInventoryPotionsMenu().getSizeInventory(); i++) {
				statsAfter.getInventoryPotionsMenu().setInventorySlotContents(i, statsBefore.getInventoryPotionsMenu().getStackInSlot(i));
			}
			ISynthesisRecipe recipesBefore = event.getOriginal().getCapability(KingdomKeys.SYNTHESIS_RECIPES, null);
			ISynthesisRecipe recipesAfter = event.getEntityPlayer().getCapability(KingdomKeys.SYNTHESIS_RECIPES, null);
			for (int i = 0; i < recipesBefore.getKnownRecipes().size(); i++) {
				recipesAfter.learnRecipe(RecipeRegistry.get(recipesBefore.getKnownRecipes().get(i)));
			}
			ISynthesisMaterial materialsBefore = event.getOriginal().getCapability(KingdomKeys.SYNTHESIS_MATERIALS, null);
			ISynthesisMaterial materialsAfter = event.getEntityPlayer().getCapability(KingdomKeys.SYNTHESIS_MATERIALS, null);
			Iterator<Entry<String, Integer>> it = materialsBefore.getKnownMaterialsMap().entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, Integer> pair = (Map.Entry<String, Integer>) it.next();
				materialsAfter.setMaterial(MaterialRegistry.get(pair.getKey().toString()), pair.getValue());
			}
		}
	}
	
	@SubscribeEvent
	public void OnEntityJoinWorld (EntityJoinWorldEvent event) {
		if (!event.getEntity().worldObj.isRemote && event.getEntity() instanceof EntityPlayer) {
			PacketDispatcher.sendTo(new SyncMagicInventory(event.getEntity().getCapability(KingdomKeys.MAGIC_STATE, null)), (EntityPlayerMP) event.getEntity());
			PacketDispatcher.sendTo(new SyncItemsInventory(event.getEntity().getCapability(KingdomKeys.PLAYER_STATS, null)), (EntityPlayerMP) event.getEntity());
			PacketDispatcher.sendTo(new SyncDriveInventory(event.getEntity().getCapability(KingdomKeys.DRIVE_STATE, null)), (EntityPlayerMP) event.getEntity());
			PacketDispatcher.sendTo(new SyncDriveData(event.getEntity().getCapability(KingdomKeys.DRIVE_STATE, null), event.getEntity().getCapability(KingdomKeys.PLAYER_STATS, null)), (EntityPlayerMP) event.getEntity());
			PacketDispatcher.sendTo(new SyncMagicData(event.getEntity().getCapability(KingdomKeys.MAGIC_STATE, null), event.getEntity().getCapability(KingdomKeys.PLAYER_STATS, null)), (EntityPlayerMP) event.getEntity());
			PacketDispatcher.sendTo(new SyncKeybladeData(event.getEntity().getCapability(KingdomKeys.SUMMON_KEYBLADE, null)), (EntityPlayerMP) event.getEntity());
			IFirstTimeJoin FTJ = event.getEntity().getCapability(KingdomKeys.FIRST_TIME_JOIN, null);
			if (!FTJ.getFirstTimeJoin()) {
				((EntityPlayer) event.getEntity()).inventory.addItemStackToInventory(new ItemStack(ModItems.WoodenKeyblade));
				FTJ.setFirstTimeJoin(true);
			}
			
			((EntityPlayer) event.getEntity()).getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(event.getEntity().getCapability(KingdomKeys.PLAYER_STATS, null).getHP());

			try {
				if (event.getEntity() instanceof EntityPlayerMP){
					EntityPlayerMP player = (EntityPlayerMP) event.getEntity();
					GameProfile profileWehavecookies56 = player.mcServer.getPlayerProfileCache().getGameProfileForUsername("Wehavecookies56");
					UUID uuidWehavecookies56 = profileWehavecookies56.getId();
					final IMunny munny = ((EntityPlayer) event.getEntity()).getCapability(KingdomKeys.MUNNY, null);
					if (event.getEntity().getUniqueID() == uuidWehavecookies56) {
						//ExtendedPlayer.get((EntityPlayer) event.entity).setMunny(ExtendedPlayer.get((EntityPlayer) event.entity).getMunny() + 10000);
						munny.setMunny(munny.getMunny() + 10000);
					}
					GameProfile profileAbelatox = player.mcServer.getPlayerProfileCache().getGameProfileForUsername("Abelatox");
					UUID uuidAbelatox = profileAbelatox.getId();
					if (event.getEntity().getUniqueID() == uuidAbelatox) {
						//ExtendedPlayer.get((EntityPlayer) event.entity).setMunny(ExtendedPlayer.get((EntityPlayer) event.entity).getMunny() + 10000);
						munny.setMunny(munny.getMunny() + 10000);
					}
				}
			} catch (Exception e) {

			}

		}

	}

	@SubscribeEvent
	public void onLivingDeathEvent (LivingDeathEvent event) {
		if (!event.getEntity().worldObj.isRemote && event.getEntity() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntity();
			ISummonKeyblade SUMMON = player.getCapability(KingdomKeys.SUMMON_KEYBLADE, null);
			if (SUMMON.getIsKeybladeSummoned()) {
				PacketDispatcher.sendToServer(new DeSummonKeyblade(player.inventory.getCurrentItem()));
				SUMMON.setIsKeybladeSummoned(false);
			}
		}

		if (!event.getEntity().worldObj.isRemote && event.getEntity() instanceof EntityMob) if (event.getSource().getSourceOfDamage() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getSource().getSourceOfDamage();

			EntityMob mob = (EntityMob) event.getEntity();
			player.getCapability(KingdomKeys.PLAYER_STATS, null).addExperience((int) (mob.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getAttributeValue() / 2));
		}
	}

	@SubscribeEvent
	public void onLivingDrops (LivingDropsEvent event) {
		if (event.getEntity() instanceof EntityPlayer) for (int i = 0; i < event.getDrops().size(); i++)
			if (event.getDrops().get(i).getEntityItem().getItem() instanceof ItemKeyblade && (event.getDrops().get(i).getEntityItem().getItem() != ModItems.WoodenKeyblade && event.getDrops().get(i).getEntityItem().getItem() != ModItems.WoodenStick)) {
				event.getDrops().remove(i);
				
				event.getEntity().getCapability(KingdomKeys.SUMMON_KEYBLADE, null).setIsKeybladeSummoned(false);
				i = 0;
			}
		if (event.getSource().getSourceOfDamage() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getSource().getSourceOfDamage();
			if (player.getHeldItem(EnumHand.MAIN_HAND) != null) if (player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemKeyblade) if (event.getEntity() instanceof EntityAnimal)
				event.getEntityLiving().entityDropItem(new ItemStack(ModItems.Heart), 2);
			else if (event.getEntity() instanceof EntityMob) {
				event.getEntityLiving().entityDropItem(new ItemStack(ModItems.DarkHeart), 2);
				if (event.getEntity() instanceof EntityWitch) {
					int rand;
					rand = randomWithRange(1, 30);
					if (rand == 1)
						event.getEntityLiving().entityDropItem(new ItemStack(ModItems.LevelUpMagicFire), 1);
					else if (rand == 5)
						event.getEntityLiving().entityDropItem(new ItemStack(ModItems.LevelUpMagicBlizzard), 1);
					else if (rand == 9)
						event.getEntityLiving().entityDropItem(new ItemStack(ModItems.LevelUpMagicThunder), 1);
					else if (rand == 13)
						event.getEntityLiving().entityDropItem(new ItemStack(ModItems.LevelUpMagicCure), 1);
					else if (rand == 17) {
						// event.getEntityLiving().entityDropItem(new
						// ItemStack(ModItems.LevelUpMagicGravity), 1);
					}

					else if (rand == 21)
						event.getEntityLiving().entityDropItem(new ItemStack(ModItems.LevelUpMagicAero), 1);
					else if (rand == 25) {
						// event.getEntityLiving().entityDropItem(new
						// ItemStack(ModItems.LevelUpMagicStop), 1);
					}
				}
			} else if (event.getEntity() instanceof EntityAgeable)
				event.getEntityLiving().entityDropItem(new ItemStack(ModItems.PureHeart), 2);
			else if (event.getEntity() instanceof EntityDragon || event.getEntity() instanceof EntityWither) event.getEntityLiving().entityDropItem(new ItemStack(ModItems.KingdomHearts), 2);
			ItemStack munny = new ItemStack(ModItems.Munny, 1);
			munny.setTagCompound(new NBTTagCompound());
			ItemStack driveOrb = new ItemStack(ModItems.DriveOrb, 1);
			driveOrb.setTagCompound(new NBTTagCompound());
			ItemStack magicOrb = new ItemStack(ModItems.MagicOrb, 1);
			magicOrb.setTagCompound(new NBTTagCompound());
			ItemStack HPOrb = new ItemStack(ModItems.HpOrb, 1);

			if (event.getEntity() instanceof EntityAnimal) {
				munny.getTagCompound().setInteger("amount", randomWithRange(1, 20));
				event.getEntityLiving().entityDropItem(munny, 1);
				driveOrb.getTagCompound().setInteger("amount", 1);
				event.getEntityLiving().entityDropItem(driveOrb, 1);
				magicOrb.getTagCompound().setInteger("amount", randomWithRange(2, 8));
				event.getEntityLiving().entityDropItem(magicOrb, 1);
				event.getEntityLiving().entityDropItem(HPOrb, 1);
			} else if (event.getEntity() instanceof EntityMob) {
				munny.getTagCompound().setInteger("amount", randomWithRange(5, 50));
				event.getEntityLiving().entityDropItem(munny, 1);
				driveOrb.getTagCompound().setInteger("amount", 5);
				event.getEntityLiving().entityDropItem(driveOrb, 1);
				magicOrb.getTagCompound().setInteger("amount", randomWithRange(5, 15));
				event.getEntityLiving().entityDropItem(magicOrb, 1);
				event.getEntityLiving().entityDropItem(HPOrb, 1);

			} else if (event.getEntity() instanceof EntityAgeable) {
				munny.getTagCompound().setInteger("amount", randomWithRange(50, 100));
				event.getEntityLiving().entityDropItem(munny, 1);
				driveOrb.getTagCompound().setInteger("amount", 5);
				event.getEntityLiving().entityDropItem(driveOrb, 1);
				magicOrb.getTagCompound().setInteger("amount", randomWithRange(10, 25));
				event.getEntityLiving().entityDropItem(magicOrb, 1);
			} else if (event.getEntity() instanceof EntityDragon || event.getEntity() instanceof EntityWither) {
				munny.getTagCompound().setInteger("amount", randomWithRange(500, 1000));
				event.getEntityLiving().entityDropItem(munny, 1);
				driveOrb.getTagCompound().setInteger("amount", randomWithRange(200, 250));
				event.getEntityLiving().entityDropItem(driveOrb, 1);
				magicOrb.getTagCompound().setInteger("amount", randomWithRange(100, 140));
				event.getEntityLiving().entityDropItem(magicOrb, 1);
			}
		}
	}

	@SubscribeEvent
	public void onEntityItemPickUp (EntityItemPickupEvent event) {
		if (event.getItem().getEntityItem().getItem() instanceof ItemMunny) {
            final IMunny munny = event.getEntityPlayer().getCapability(KingdomKeys.MUNNY, null);
			MunnyPickup packet = new MunnyPickup(event.getItem().getEntityItem());
			event.getItem().getEntityItem().stackSize--;
			munny.addMunny(event.getItem().getEntityItem().getTagCompound().getInteger("amount"));
			PacketDispatcher.sendTo(new SyncMunnyData(munny), (EntityPlayerMP) event.getEntityPlayer());
	    	PacketDispatcher.sendTo(new ShowOverlayPacket("munny", event.getItem().getEntityItem().getTagCompound().getInteger("amount")), (EntityPlayerMP) event.getEntityPlayer());
			
		} else if (event.getItem().getEntityItem().getItem() instanceof ItemHpOrb) {
			if (event.getEntityPlayer().getHeldItem(EnumHand.MAIN_HAND) != null) if (event.getEntityPlayer().getHeldItem(EnumHand.MAIN_HAND).getItem() == ModItems.EmptyBottle) return;
			IPlayerStats STATS = event.getEntityPlayer().getCapability(KingdomKeys.PLAYER_STATS, null);
			HpOrbPickup packet = new HpOrbPickup(event.getItem().getEntityItem());
			if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
				if (event.getEntityPlayer().getHealth() >= STATS.getHP()) {
					event.getItem().getEntityItem().stackSize--;
					return;
				}
				if (event.getEntityPlayer().getHealth() < STATS.getHP() - 1)
					event.getEntityPlayer().heal(2);
				else
					event.getEntityPlayer().heal(1);
				event.getItem().getEntityItem().stackSize--;
			}
		} else if (event.getItem().getEntityItem().getItem() == ModItems.DriveOrb) {
            final IPlayerStats STATS = event.getEntityPlayer().getCapability(KingdomKeys.PLAYER_STATS, null);
			double dp = STATS.getDP();
			// if(dp < 1000) //Not pickup orb when full
			{
				DriveOrbPickup packet = new DriveOrbPickup(event.getItem().getEntityItem());
				if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
					event.getItem().getEntityItem().stackSize--;
					STATS.addDP(event.getItem().getEntityItem().getTagCompound().getInteger("amount"));
					EntityPlayer player = event.getEntityPlayer();
					PacketDispatcher.sendTo(new SyncDriveData(player.getCapability(KingdomKeys.DRIVE_STATE, null), player.getCapability(KingdomKeys.PLAYER_STATS, null)), (EntityPlayerMP) player);
					PacketDispatcher.sendTo(new SyncDriveInventory(player.getCapability(KingdomKeys.DRIVE_STATE, null)), (EntityPlayerMP) event.getEntityPlayer());
				}
			}
		} else if (event.getItem().getEntityItem().getItem() == ModItems.MagicOrb) {
			final IPlayerStats STATS = event.getEntityPlayer().getCapability(KingdomKeys.PLAYER_STATS, null);
			double mp = STATS.getMP();
			if (event.getEntityPlayer().getHeldItem(EnumHand.MAIN_HAND) != null) if (event.getEntityPlayer().getHeldItem(EnumHand.MAIN_HAND).getItem() == ModItems.EmptyBottle) return;
			MagicOrbPickup packet = new MagicOrbPickup(event.getItem().getEntityItem());
			if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
				event.getItem().getEntityItem().stackSize--;
				STATS.addMP(event.getItem().getEntityItem().getTagCompound().getInteger("amount"));
				PacketDispatcher.sendTo(new SyncMagicData(event.getEntityPlayer().getCapability(KingdomKeys.MAGIC_STATE, null), STATS), (EntityPlayerMP) event.getEntityPlayer());
			}
		} else if (event.getItem().getEntityItem().getItem() == Item.getItemFromBlock(ModBlocks.NormalBlox) || event.getItem().getEntityItem().getItem() == Item.getItemFromBlock(ModBlocks.HardBlox) || event.getItem().getEntityItem().getItem() == Item.getItemFromBlock(ModBlocks.MetalBlox)) {
			AchievementHelper.addAchievement(event.getEntityPlayer(), ModAchievements.getBlox);
		} else if (event.getItem().getEntityItem().getItem() instanceof ItemSynthesisMaterial) {
			for(int i = 0; i < event.getEntityPlayer().inventory.getSizeInventory(); i++) {
				if (event.getEntityPlayer().inventory.getStackInSlot(i) != null) {
					if (event.getEntityPlayer().inventory.getStackInSlot(i).getItem() == ModItems.SynthesisBagL) {
						InventorySynthesisBagL inv = new InventorySynthesisBagL(event.getEntityPlayer().inventory.getStackInSlot(i));
						for (int j = 0; j < inv.getSizeInventory(); j++) {
							ItemStack bagItem = inv.getStackInSlot(j);
							ItemStack pickUp = event.getItem().getEntityItem();
							if (bagItem != null) {
								if (bagItem.getItem().equals(pickUp.getItem())) {
									if (bagItem.hasTagCompound() && pickUp.hasTagCompound()){
										if (bagItem.getTagCompound().hasKey("material") && pickUp.getTagCompound().hasKey("material")) {
											if (bagItem.getTagCompound().getString("material").equals(pickUp.getTagCompound().getString("material"))) {
												if (bagItem.stackSize < 64) {
													if (bagItem.stackSize + pickUp.stackSize <= 64) {
														ItemStack stack = new ItemStack(pickUp.getItem(), pickUp.stackSize + bagItem.stackSize);
														stack.setTagCompound(new NBTTagCompound());
														stack.getTagCompound().setString("material", bagItem.getTagCompound().getString("material"));
														stack.getTagCompound().setString("rank", bagItem.getTagCompound().getString("rank"));
														inv.setInventorySlotContents(j, stack);
														pickUp.stackSize = 0;
														return;
													}
												}
											}
										}
									}
								}
							} else if (bagItem == null) {
								inv.setInventorySlotContents(j, pickUp);
								pickUp.stackSize = 0;
								return;
							}
						}
					} else if (event.getEntityPlayer().inventory.getStackInSlot(i).getItem() == ModItems.SynthesisBagM) {
						InventorySynthesisBagM inv = new InventorySynthesisBagM(event.getEntityPlayer().inventory.getStackInSlot(i));
						for (int j = 0; j < inv.getSizeInventory(); j++) {
							ItemStack bagItem = inv.getStackInSlot(j);
							ItemStack pickUp = event.getItem().getEntityItem();
							if (bagItem != null) {
								if (bagItem.getItem().equals(pickUp.getItem())) {
									if (bagItem.hasTagCompound() && pickUp.hasTagCompound()) {
										if (bagItem.getTagCompound().hasKey("material") && pickUp.getTagCompound().hasKey("material")) {
											if (bagItem.getTagCompound().getString("material").equals(pickUp.getTagCompound().getString("material"))) {
												if (bagItem.stackSize < 64) {
													if (bagItem.stackSize + pickUp.stackSize <= 64) {
														ItemStack stack = new ItemStack(pickUp.getItem(), pickUp.stackSize + bagItem.stackSize);
														stack.setTagCompound(new NBTTagCompound());
														stack.getTagCompound().setString("material", bagItem.getTagCompound().getString("material"));
														stack.getTagCompound().setString("rank", bagItem.getTagCompound().getString("rank"));
														inv.setInventorySlotContents(j, stack);
														pickUp.stackSize = 0;
														return;
													}
												}
											}
										}
									}
								}
							} else if (bagItem == null) {
								inv.setInventorySlotContents(j, pickUp);
								pickUp.stackSize = 0;
								return;
							}
						}
					} if (event.getEntityPlayer().inventory.getStackInSlot(i).getItem() == ModItems.SynthesisBagS) {
						InventorySynthesisBagS inv = new InventorySynthesisBagS(event.getEntityPlayer().inventory.getStackInSlot(i));
						for (int j = 0; j < inv.getSizeInventory(); j++) {
							ItemStack bagItem = inv.getStackInSlot(j);
							ItemStack pickUp = event.getItem().getEntityItem();
							if (bagItem != null) {
								if (bagItem.getItem().equals(pickUp.getItem())) {
									if (bagItem.hasTagCompound() && pickUp.hasTagCompound()) {
										if (bagItem.getTagCompound().hasKey("material") && pickUp.getTagCompound().hasKey("material")) {
											if (bagItem.getTagCompound().getString("material").equals(pickUp.getTagCompound().getString("material"))) {
												if (bagItem.stackSize < 64) { 
													if (bagItem.stackSize + pickUp.stackSize <= 64) {
														ItemStack stack = new ItemStack(pickUp.getItem(), pickUp.stackSize + bagItem.stackSize);
														stack.setTagCompound(new NBTTagCompound());
														stack.getTagCompound().setString("material", bagItem.getTagCompound().getString("material"));
														stack.getTagCompound().setString("rank", bagItem.getTagCompound().getString("rank"));
														inv.setInventorySlotContents(j, stack);
														pickUp.stackSize = 0;
														return;
													}
												}
											}
										}
									}
								}
							} else if (bagItem == null) {
								inv.setInventorySlotContents(j, pickUp);
								pickUp.stackSize = 0;
								return;
							}
						}
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void onItemTossEvent (ItemTossEvent event) {
		if (event.getEntityItem().getEntityItem().getItem() instanceof ItemKeyblade && (event.getEntityItem().getEntityItem().getItem() != ModItems.WoodenKeyblade && event.getEntityItem().getEntityItem().getItem() != ModItems.WoodenStick)) {
			event.getEntityItem().isDead = true;
			ItemStack itemStack = event.getEntityItem().getEntityItem();
			
			event.getPlayer().getCapability(KingdomKeys.SUMMON_KEYBLADE, null).setIsKeybladeSummoned(false);
		} else if (event.getEntityItem().getEntityItem().getItem() instanceof ItemMunny) {
			event.setCanceled(true);
	    	PacketDispatcher.sendTo(new ShowOverlayPacket("munny", event.getEntityItem().getEntityItem().getTagCompound().getInteger("amount")), (EntityPlayerMP) event.getPlayer());

			event.getPlayer().getCapability(KingdomKeys.MUNNY, null).addMunny(event.getEntityItem().getEntityItem().getTagCompound().getInteger("amount"));
		}
	}

	@SubscribeEvent
	public void onItemCrafted (ItemCraftedEvent event) {
		ItemStack WHC56skull = new ItemStack(Items.skull, 1, 3);
		WHC56skull.setTagCompound(new NBTTagCompound());
		WHC56skull.getTagCompound().setTag("SkullOwner", new NBTTagString("Wehavecookies56"));

		ItemStack AAskull = new ItemStack(Items.skull, 1, 3);
		AAskull.setTagCompound(new NBTTagCompound());
		AAskull.getTagCompound().setTag("SkullOwner", new NBTTagString("Abelatox"));

		if (event.crafting.getItem() == Item.getItemFromBlock(ModBlocks.SynthesisTable)) AchievementHelper.addAchievement(event.player, ModAchievements.getSynthesisTable);

		if (event.crafting.getItem() == WHC56skull.getItem()) AchievementHelper.addAchievement(event.player, ModAchievements.getWehavecookies56Skull);

		if (event.crafting.getItem() == AAskull.getItem()) AchievementHelper.addAchievement(event.player, ModAchievements.getAbelatoxSkull);
	}

	public static boolean isBoss = false;

	public static boolean isHostiles = false;

	@SubscribeEvent
	public void onPlayerTick (PlayerTickEvent event) {
		IPlayerStats STATS = event.player.getCapability(KingdomKeys.PLAYER_STATS, null);
		IDriveState DS = event.player.getCapability(KingdomKeys.DRIVE_STATE, null);
		
		/*if(event.side.isClient())
		{
			System.out.println("Valor Client: "+event.player.getCapability(KingdomKeys.DRIVE_STATE, null).getDriveLevel(Strings.Form_Valor));
			System.out.println("Wisdom Client: "+event.player.getCapability(KingdomKeys.DRIVE_STATE, null).getDriveLevel(Strings.Form_Wisdom));
			System.out.println("Limit Client: "+event.player.getCapability(KingdomKeys.DRIVE_STATE, null).getDriveLevel(Strings.Form_Limit));
			System.out.println("Master Client: "+event.player.getCapability(KingdomKeys.DRIVE_STATE, null).getDriveLevel(Strings.Form_Master));
			System.out.println("Final Client: "+event.player.getCapability(KingdomKeys.DRIVE_STATE, null).getDriveLevel(Strings.Form_Final));
		}else{
			System.out.println("Valor Server: "+event.player.getCapability(KingdomKeys.DRIVE_STATE, null).getDriveLevel(Strings.Form_Valor));
			System.out.println("Wisdom Server: "+event.player.getCapability(KingdomKeys.DRIVE_STATE, null).getDriveLevel(Strings.Form_Wisdom));
			System.out.println("Limit Server: "+event.player.getCapability(KingdomKeys.DRIVE_STATE, null).getDriveLevel(Strings.Form_Limit));
			System.out.println("Master Server: "+event.player.getCapability(KingdomKeys.DRIVE_STATE, null).getDriveLevel(Strings.Form_Master));
			System.out.println("Final Server: "+event.player.getCapability(KingdomKeys.DRIVE_STATE, null).getDriveLevel(Strings.Form_Final));
		}*/
		if (!DS.getInDrive()) 
			if (STATS.getMP() <= 0 || STATS.getRecharge()) {
				STATS.setRecharge(true);
			if (STATS.getMP() != STATS.getMaxMP()) {
				STATS.addMP(0.1);
				if (STATS.getMP() > STATS.getMaxMP()) 
					STATS.setMP(STATS.getMaxMP());

			} else {
				STATS.setMP(STATS.getMaxMP());
				STATS.setRecharge(false);
				if(event.side.isServer())
				{
					PacketDispatcher.sendTo(new SyncMagicData(event.player.getCapability(KingdomKeys.MAGIC_STATE, null), event.player.getCapability(KingdomKeys.PLAYER_STATS, null)), (EntityPlayerMP)event.player);

					//PacketDispatcher.sendTo(new SyncMagicData(KingdomKeys.MAGIC_STATE, KingdomKeys.PLAYER_STATS), event.player);
				}
			}
		}
		if (!DS.getActiveDriveName().equals("none") && DriveFormRegistry.isDriveFormRegistered(DS.getActiveDriveName())) DriveFormRegistry.get(DS.getActiveDriveName()).update(event.player);
		if (event.player != null) {
			List<Entity> entities = event.player.worldObj.getEntitiesWithinAABBExcludingEntity(event.player, event.player.getEntityBoundingBox().expand(16.0D, 10.0D, 16.0D));
			List<Entity> bossEntities = event.player.worldObj.getEntitiesWithinAABBExcludingEntity(event.player, event.player.getEntityBoundingBox().expand(150.0D, 100.0D, 150.0D));
			if (!bossEntities.isEmpty()) {
				for (int i = 0; i < bossEntities.size(); i++) {
					if (bossEntities.get(i) instanceof EntityDragon || bossEntities.get(i) instanceof EntityWither) {
						isBoss = true;
						break;
					} else {
						isBoss = false;
					}
				}
			} else {
				isBoss = false;
			}
			if (!entities.isEmpty()) {
				for (int i = 0; i < entities.size(); i++) {
					if (entities.get(i) instanceof EntityMob) {
						isHostiles = true;
						break;
					} else {
						isHostiles = false;
					}
				}
			} else {
				isHostiles = false;
			}
		}

	}

	@SubscribeEvent
	public void onLivingUpdate (LivingUpdateEvent event) {

	}

	/**
	 * Method for generating random ints between the 2 parameters, The order of
	 * min and max do not matter.
	 *
	 * @param min
	 * @param max
	 * @return
	 */
	public static int randomWithRange (int min, int max) {
		int range = Math.abs(max - min) + 1;
		return (int) (Math.random() * range) + (min <= max ? min : max);
	}

	@SubscribeEvent
	public void onHurt (LivingHurtEvent event) {
		if (event.getEntityLiving() instanceof EntityPlayer) {
			IPlayerStats STATS = event.getEntity().getCapability(KingdomKeys.PLAYER_STATS, null);
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			if (event.getAmount() - STATS.getDefense() <= 0)
				event.setAmount(1);
			else
				event.setAmount((float)( event.getAmount() - (STATS.getDefense()*0.25)));

			if (player.getHeldItem(EnumHand.MAIN_HAND) != null && player.getHeldItem(EnumHand.MAIN_HAND).getItem() == ModItems.FrozenPride) 
			{
				/* Blocking is gone
				if (player.) 
				{
					event.ammount = 0.5f;
				}
				*/
			}
			if (event.getSource().getDamageType() == "lightningBolt") if (EntityThunder.summonLightning) event.setCanceled(true);
		}
		if (event.getSource().getSourceOfDamage() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getSource().getSourceOfDamage();
			IPlayerStats STATS = player.getCapability(KingdomKeys.PLAYER_STATS, null);
			IDriveState DS = player.getCapability(KingdomKeys.DRIVE_STATE, null);
			event.setAmount((float) (event.getAmount() + (STATS.getStrength() * 0.25)));
			if (player.getHeldItem(EnumHand.MAIN_HAND) != null) if (player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemKeyblade) {
				if (DS.getActiveDriveName().equals("Valor")) event.setAmount((float) (event.getAmount() * 1.5));
				STATS.addDP(1);
			} else
				return;
		}
	}

	@SubscribeEvent
	public void onFall (LivingFallEvent event) {
		if (event.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			IDriveState DS = player.getCapability(KingdomKeys.DRIVE_STATE, null);
			if (DS.getInDrive()) event.setDistance(0);
		}
	}

	@SubscribeEvent
	public void onBlockDestroyed (HarvestDropsEvent event) {
		int fortune;
		if (event.getState().getBlock() == ModBlocks.BlazingOre) {
			int drop = randomWithRange(1, 4);
			if (drop == 1) {
				ItemStack BlazingShard = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(BlazingShard, Strings.SM_BlazingShard, "C");
				for (int i = 0; i < event.getFortuneLevel(); i++) {
					fortune = randomWithRange(1, 15);
					if (fortune < 5) event.getDrops().add(BlazingShard);
				}
				event.getDrops().add(BlazingShard);

			} else if (drop == 2) {
				ItemStack BlazingStone = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(BlazingStone, Strings.SM_BlazingStone, "B");
				for (int i = 0; i < event.getFortuneLevel(); i++) {
					fortune = randomWithRange(1, 15);
					if (fortune < 5) event.getDrops().add(BlazingStone);
				}
				event.getDrops().add(BlazingStone);

			} else if (drop == 3) {
				ItemStack BlazingGem = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(BlazingGem, Strings.SM_BlazingGem, "A");
				for (int i = 0; i < event.getFortuneLevel(); i++) {
					fortune = randomWithRange(1, 15);
					if (fortune < 5) event.getDrops().add(BlazingGem);
				}
				event.getDrops().add(BlazingGem);

			} else if (drop == 4) {
				ItemStack BlazingCrystal = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(BlazingCrystal, Strings.SM_BlazingCrystal, "S");
				for (int i = 0; i < event.getFortuneLevel(); i++) {
					fortune = randomWithRange(1, 15);
					if (fortune < 5) event.getDrops().add(BlazingCrystal);
				}
				event.getDrops().add(BlazingCrystal);
			}
		} else if (event.getState().getBlock() == ModBlocks.BrightOre) {
			int drop = randomWithRange(1, 4);
			if (drop == 1) {
				ItemStack BrightShard = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(BrightShard, Strings.SM_BrightShard, "C");
				for (int i = 0; i < event.getFortuneLevel(); i++) {
					fortune = randomWithRange(1, 15);
					if (fortune < 5) event.getDrops().add(BrightShard);
				}
				event.getDrops().add(BrightShard);
			} else if (drop == 2) {
				ItemStack BrightStone = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(BrightStone, Strings.SM_BrightStone, "B");
				for (int i = 0; i < event.getFortuneLevel(); i++) {
					fortune = randomWithRange(1, 15);
					if (fortune < 5) event.getDrops().add(BrightStone);
				}
				event.getDrops().add(BrightStone);
			} else if (drop == 3) {
				ItemStack BrightGem = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(BrightGem, Strings.SM_BrightGem, "A");
				for (int i = 0; i < event.getFortuneLevel(); i++) {
					fortune = randomWithRange(1, 15);
					if (fortune < 5) event.getDrops().add(BrightGem);
				}
				event.getDrops().add(BrightGem);
			} else if (drop == 4) {
				ItemStack BrightCrystal = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(BrightCrystal, Strings.SM_BrightCrystal, "S");
				for (int i = 0; i < event.getFortuneLevel(); i++) {
					fortune = randomWithRange(1, 15);
					if (fortune < 5) event.getDrops().add(BrightCrystal);
				}
				event.getDrops().add(BrightCrystal);
			}
		} else if (event.getState().getBlock() == ModBlocks.DarkOre) {
			int drop = randomWithRange(1, 4);
			if (drop == 1) {
				ItemStack DarkShard = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(DarkShard, Strings.SM_DarkShard, "C");
				for (int i = 0; i < event.getFortuneLevel(); i++) {
					fortune = randomWithRange(1, 15);
					if (fortune < 5) event.getDrops().add(DarkShard);
				}
				event.getDrops().add(DarkShard);
			} else if (drop == 2) {
				ItemStack DarkStone = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(DarkStone, Strings.SM_DarkStone, "B");
				for (int i = 0; i < event.getFortuneLevel(); i++) {
					fortune = randomWithRange(1, 15);
					if (fortune < 5) event.getDrops().add(DarkStone);
				}
				event.getDrops().add(DarkStone);
			} else if (drop == 3) {
				ItemStack DarkGem = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(DarkGem, Strings.SM_DarkGem, "A");
				for (int i = 0; i < event.getFortuneLevel(); i++) {
					fortune = randomWithRange(1, 15);
					if (fortune < 5) event.getDrops().add(DarkGem);
				}
				event.getDrops().add(DarkGem);
			} else if (drop == 4) {
				ItemStack DarkCrystal = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(DarkCrystal, Strings.SM_DarkCrystal, "S");
				for (int i = 0; i < event.getFortuneLevel(); i++) {
					fortune = randomWithRange(1, 15);
					if (fortune < 5) event.getDrops().add(DarkCrystal);
				}
				event.getDrops().add(DarkCrystal);
			}
		} else if (event.getState().getBlock() == ModBlocks.DarkOreE) {
			int drop = randomWithRange(1, 4);
			if (drop == 1) {
				ItemStack DarkShard = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(DarkShard, Strings.SM_DarkShard, "C");
				for (int i = 0; i < event.getFortuneLevel(); i++) {
					fortune = randomWithRange(1, 15);
					if (fortune < 5) event.getDrops().add(DarkShard);
				}
				event.getDrops().add(DarkShard);
			} else if (drop == 2) {
				ItemStack DarkStone = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(DarkStone, Strings.SM_DarkStone, "B");
				for (int i = 0; i < event.getFortuneLevel(); i++) {
					fortune = randomWithRange(1, 15);
					if (fortune < 5) event.getDrops().add(DarkStone);
				}
				event.getDrops().add(DarkStone);
			} else if (drop == 3) {
				ItemStack DarkGem = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(DarkGem, Strings.SM_DarkGem, "A");
				for (int i = 0; i < event.getFortuneLevel(); i++) {
					fortune = randomWithRange(1, 15);
					if (fortune < 5) event.getDrops().add(DarkGem);
				}
				event.getDrops().add(DarkGem);
			} else if (drop == 4) {
				ItemStack DarkCrystal = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(DarkCrystal, Strings.SM_DarkCrystal, "S");
				for (int i = 0; i < event.getFortuneLevel(); i++) {
					fortune = randomWithRange(1, 15);
					if (fortune < 5) event.getDrops().add(DarkCrystal);
				}
				event.getDrops().add(DarkCrystal);
			}
		} else if (event.getState().getBlock() == ModBlocks.DenseOre) {
			int drop = randomWithRange(1, 4);
			if (drop == 1) {
				ItemStack DenseShard = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(DenseShard, Strings.SM_DenseShard, "C");
				for (int i = 0; i < event.getFortuneLevel(); i++) {
					fortune = randomWithRange(1, 15);
					if (fortune < 5) event.getDrops().add(DenseShard);
				}
				event.getDrops().add(DenseShard);
			} else if (drop == 2) {
				ItemStack DenseStone = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(DenseStone, Strings.SM_DenseStone, "B");
				for (int i = 0; i < event.getFortuneLevel(); i++) {
					fortune = randomWithRange(1, 15);
					if (fortune < 5) event.getDrops().add(DenseStone);
				}
				event.getDrops().add(DenseStone);
			} else if (drop == 3) {
				ItemStack DenseGem = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(DenseGem, Strings.SM_DenseGem, "A");
				for (int i = 0; i < event.getFortuneLevel(); i++) {
					fortune = randomWithRange(1, 15);
					if (fortune < 5) event.getDrops().add(DenseGem);
				}
				event.getDrops().add(DenseGem);
			} else if (drop == 4) {
				ItemStack DenseCrystal = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(DenseCrystal, Strings.SM_DenseCrystal, "S");
				for (int i = 0; i < event.getFortuneLevel(); i++) {
					fortune = randomWithRange(1, 15);
					if (fortune < 5) event.getDrops().add(DenseCrystal);
				}
				event.getDrops().add(DenseCrystal);
			}
		} else if (event.getState().getBlock() == ModBlocks.EnergyOre) {
			int drop = randomWithRange(1, 4);
			if (drop == 1) {
				ItemStack EnergyShard = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(EnergyShard, Strings.SM_EnergyShard, "C");
				for (int i = 0; i < event.getFortuneLevel(); i++) {
					fortune = randomWithRange(1, 15);
					if (fortune < 5) event.getDrops().add(EnergyShard);
				}
				event.getDrops().add(EnergyShard);
			} else if (drop == 2) {
				ItemStack EnergyStone = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(EnergyStone, Strings.SM_EnergyStone, "B");
				for (int i = 0; i < event.getFortuneLevel(); i++) {
					fortune = randomWithRange(1, 15);
					if (fortune < 5) event.getDrops().add(EnergyStone);
				}
				event.getDrops().add(EnergyStone);
			} else if (drop == 3) {
				ItemStack EnergyGem = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(EnergyGem, Strings.SM_EnergyGem, "A");
				for (int i = 0; i < event.getFortuneLevel(); i++) {
					fortune = randomWithRange(1, 15);
					if (fortune < 5) event.getDrops().add(EnergyGem);
				}
				event.getDrops().add(EnergyGem);
			} else if (drop == 4) {
				ItemStack EnergyCrystal = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(EnergyCrystal, Strings.SM_EnergyCrystal, "S");
				for (int i = 0; i < event.getFortuneLevel(); i++) {
					fortune = randomWithRange(1, 15);
					if (fortune < 5) event.getDrops().add(EnergyCrystal);
				}
				event.getDrops().add(EnergyCrystal);
			}
		} else if (event.getState().getBlock() == ModBlocks.FrostOre) {
			int drop = randomWithRange(1, 4);
			if (drop == 1) {
				ItemStack FrostShard = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(FrostShard, Strings.SM_FrostShard, "C");
				for (int i = 0; i < event.getFortuneLevel(); i++) {
					fortune = randomWithRange(1, 15);
					if (fortune < 5) event.getDrops().add(FrostShard);
				}
				event.getDrops().add(FrostShard);
			} else if (drop == 2) {
				ItemStack FrostStone = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(FrostStone, Strings.SM_FrostStone, "B");
				for (int i = 0; i < event.getFortuneLevel(); i++) {
					fortune = randomWithRange(1, 15);
					if (fortune < 5) event.getDrops().add(FrostStone);
				}
				event.getDrops().add(FrostStone);
			} else if (drop == 3) {
				ItemStack FrostGem = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(FrostGem, Strings.SM_FrostGem, "A");
				for (int i = 0; i < event.getFortuneLevel(); i++) {
					fortune = randomWithRange(1, 15);
					if (fortune < 5) event.getDrops().add(FrostGem);
				}
				event.getDrops().add(FrostGem);
			} else if (drop == 4) {
				ItemStack FrostCrystal = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(FrostCrystal, Strings.SM_FrostCrystal, "S");
				for (int i = 0; i < event.getFortuneLevel(); i++) {
					fortune = randomWithRange(1, 15);
					if (fortune < 5) event.getDrops().add(FrostCrystal);
				}
				event.getDrops().add(FrostCrystal);
			}
		} else if (event.getState().getBlock() == ModBlocks.LightningOre) {
			int drop = randomWithRange(1, 4);
			if (drop == 1) {
				ItemStack LightningShard = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(LightningShard, Strings.SM_LightningShard, "C");
				for (int i = 0; i < event.getFortuneLevel(); i++) {
					fortune = randomWithRange(1, 15);
					if (fortune < 5) event.getDrops().add(LightningShard);
				}
				event.getDrops().add(LightningShard);
			} else if (drop == 2) {
				ItemStack LightningStone = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(LightningStone, Strings.SM_LightningStone, "B");
				for (int i = 0; i < event.getFortuneLevel(); i++) {
					fortune = randomWithRange(1, 15);
					if (fortune < 5) event.getDrops().add(LightningStone);
				}
				event.getDrops().add(LightningStone);
			} else if (drop == 3) {
				ItemStack LightningGem = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(LightningGem, Strings.SM_LightningGem, "A");
				for (int i = 0; i < event.getFortuneLevel(); i++) {
					fortune = randomWithRange(1, 15);
					if (fortune < 5) event.getDrops().add(LightningGem);
				}
				event.getDrops().add(LightningGem);
			} else if (drop == 4) {
				ItemStack LightningCrystal = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(LightningCrystal, Strings.SM_LightningCrystal, "S");
				for (int i = 0; i < event.getFortuneLevel(); i++) {
					fortune = randomWithRange(1, 15);
					if (fortune < 5) event.getDrops().add(LightningCrystal);
				}
				event.getDrops().add(LightningCrystal);
			}
		} else if (event.getState().getBlock() == ModBlocks.LucidOre) {
			int drop = randomWithRange(1, 4);
			if (drop == 1) {
				ItemStack LucidShard = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(LucidShard, Strings.SM_LucidShard, "C");
				for (int i = 0; i < event.getFortuneLevel(); i++) {
					fortune = randomWithRange(1, 15);
					if (fortune < 5) event.getDrops().add(LucidShard);
				}
				event.getDrops().add(LucidShard);
			} else if (drop == 2) {
				ItemStack LucidStone = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(LucidStone, Strings.SM_LucidStone, "B");
				for (int i = 0; i < event.getFortuneLevel(); i++) {
					fortune = randomWithRange(1, 15);
					if (fortune < 5) event.getDrops().add(LucidStone);
				}
				event.getDrops().add(LucidStone);
			} else if (drop == 3) {
				ItemStack LucidGem = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(LucidGem, Strings.SM_LucidGem, "A");
				for (int i = 0; i < event.getFortuneLevel(); i++) {
					fortune = randomWithRange(1, 15);
					if (fortune < 5) event.getDrops().add(LucidGem);
				}
				event.getDrops().add(LucidGem);
			} else if (drop == 4) {
				ItemStack LucidCrystal = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(LucidCrystal, Strings.SM_LucidCrystal, "S");
				for (int i = 0; i < event.getFortuneLevel(); i++) {
					fortune = randomWithRange(1, 15);
					if (fortune < 5) event.getDrops().add(LucidCrystal);
				}
				event.getDrops().add(LucidCrystal);
			}
		} else if (event.getState().getBlock() == ModBlocks.PowerOre) {
			int drop = randomWithRange(1, 4);
			if (drop == 1) {
				ItemStack PowerShard = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(PowerShard, Strings.SM_PowerShard, "C");
				for (int i = 0; i < event.getFortuneLevel(); i++) {
					fortune = randomWithRange(1, 15);
					if (fortune < 5) event.getDrops().add(PowerShard);
				}
				event.getDrops().add(PowerShard);
			} else if (drop == 2) {
				ItemStack PowerStone = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(PowerStone, Strings.SM_PowerStone, "B");
				for (int i = 0; i < event.getFortuneLevel(); i++) {
					fortune = randomWithRange(1, 15);
					if (fortune < 5) event.getDrops().add(PowerStone);
				}
				event.getDrops().add(PowerStone);
			} else if (drop == 3) {
				ItemStack PowerGem = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(PowerGem, Strings.SM_PowerGem, "A");
				for (int i = 0; i < event.getFortuneLevel(); i++) {
					fortune = randomWithRange(1, 15);
					if (fortune < 5) event.getDrops().add(PowerGem);
				}
				event.getDrops().add(PowerGem);
			} else if (drop == 4) {
				ItemStack PowerCrystal = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(PowerCrystal, Strings.SM_PowerCrystal, "S");
				for (int i = 0; i < event.getFortuneLevel(); i++) {
					fortune = randomWithRange(1, 15);
					if (fortune < 5) event.getDrops().add(PowerCrystal);
				}
				event.getDrops().add(PowerCrystal);
			}
		} else if (event.getState().getBlock() == ModBlocks.PowerOreE) {
			int drop = randomWithRange(1, 4);
			if (drop == 1) {
				ItemStack PowerShard = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(PowerShard, Strings.SM_PowerShard, "C");
				for (int i = 0; i < event.getFortuneLevel(); i++) {
					fortune = randomWithRange(1, 15);
					if (fortune < 5) event.getDrops().add(PowerShard);
				}
				event.getDrops().add(PowerShard);
			} else if (drop == 2) {
				ItemStack PowerStone = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(PowerStone, Strings.SM_PowerStone, "B");
				for (int i = 0; i < event.getFortuneLevel(); i++) {
					fortune = randomWithRange(1, 15);
					if (fortune < 5) event.getDrops().add(PowerStone);
				}
				event.getDrops().add(PowerStone);
			} else if (drop == 3) {
				ItemStack PowerGem = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(PowerGem, Strings.SM_PowerGem, "A");
				for (int i = 0; i < event.getFortuneLevel(); i++) {
					fortune = randomWithRange(1, 15);
					if (fortune < 5) event.getDrops().add(PowerGem);
				}
				event.getDrops().add(PowerGem);
			} else if (drop == 4) {
				ItemStack PowerCrystal = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(PowerCrystal, Strings.SM_PowerCrystal, "S");
				for (int i = 0; i < event.getFortuneLevel(); i++) {
					fortune = randomWithRange(1, 15);
					if (fortune < 5) event.getDrops().add(PowerCrystal);
				}
				event.getDrops().add(PowerCrystal);
			}
		} else if (event.getState().getBlock() == ModBlocks.RemembranceOre) {
			int drop = randomWithRange(1, 4);
			if (drop == 1) {
				ItemStack RemembranceShard = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(RemembranceShard, Strings.SM_RemembranceShard, "C");
				for (int i = 0; i < event.getFortuneLevel(); i++) {
					fortune = randomWithRange(1, 15);
					if (fortune < 5) event.getDrops().add(RemembranceShard);
				}
				event.getDrops().add(RemembranceShard);
			} else if (drop == 2) {
				ItemStack RemembranceStone = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(RemembranceStone, Strings.SM_RemembranceStone, "B");
				for (int i = 0; i < event.getFortuneLevel(); i++) {
					fortune = randomWithRange(1, 15);
					if (fortune < 5) event.getDrops().add(RemembranceStone);
				}
				event.getDrops().add(RemembranceStone);
			} else if (drop == 3) {
				ItemStack RemembranceGem = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(RemembranceGem, Strings.SM_RemembranceGem, "A");
				for (int i = 0; i < event.getFortuneLevel(); i++) {
					fortune = randomWithRange(1, 15);
					if (fortune < 5) event.getDrops().add(RemembranceGem);
				}
				event.getDrops().add(RemembranceGem);
			} else if (drop == 4) {
				ItemStack RemembranceCrystal = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(RemembranceCrystal, Strings.SM_RemembranceCrystal, "S");
				for (int i = 0; i < event.getFortuneLevel(); i++) {
					fortune = randomWithRange(1, 15);
					if (fortune < 5) event.getDrops().add(RemembranceCrystal);
				}
				event.getDrops().add(RemembranceCrystal);
			}
		} else if (event.getState().getBlock() == ModBlocks.SerenityOre) {
			int drop = randomWithRange(1, 4);
			if (drop == 1) {
				ItemStack SerenityShard = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(SerenityShard, Strings.SM_SerenityShard, "C");
				for (int i = 0; i < event.getFortuneLevel(); i++) {
					fortune = randomWithRange(1, 15);
					if (fortune < 5) event.getDrops().add(SerenityShard);
				}
				event.getDrops().add(SerenityShard);
			} else if (drop == 2) {
				ItemStack SerenityStone = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(SerenityStone, Strings.SM_SerenityStone, "B");
				for (int i = 0; i < event.getFortuneLevel(); i++) {
					fortune = randomWithRange(1, 15);
					if (fortune < 5) event.getDrops().add(SerenityStone);
				}
				event.getDrops().add(SerenityStone);
			} else if (drop == 3) {
				ItemStack SerenityGem = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(SerenityGem, Strings.SM_SerenityGem, "A");
				for (int i = 0; i < event.getFortuneLevel(); i++) {
					fortune = randomWithRange(1, 15);
					if (fortune < 5) event.getDrops().add(SerenityGem);
				}
				event.getDrops().add(SerenityGem);
			} else if (drop == 4) {
				ItemStack SerenityCrystal = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(SerenityCrystal, Strings.SM_SerenityCrystal, "S");
				for (int i = 0; i < event.getFortuneLevel(); i++) {
					fortune = randomWithRange(1, 15);
					if (fortune < 5) event.getDrops().add(SerenityCrystal);
				}
				event.getDrops().add(SerenityCrystal);
			}
		} else if (event.getState().getBlock() == ModBlocks.TranquilOre) {
			int drop = randomWithRange(1, 4);
			if (drop == 1) {
				ItemStack TranquilShard = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(TranquilShard, Strings.SM_TranquilShard, "C");
				for (int i = 0; i < event.getFortuneLevel(); i++) {
					fortune = randomWithRange(1, 15);
					if (fortune < 5) event.getDrops().add(TranquilShard);
				}
				event.getDrops().add(TranquilShard);
			} else if (drop == 2) {
				ItemStack TranquilStone = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(TranquilStone, Strings.SM_TranquilStone, "B");
				for (int i = 0; i < event.getFortuneLevel(); i++) {
					fortune = randomWithRange(1, 15);
					if (fortune < 5) event.getDrops().add(TranquilStone);
				}
				event.getDrops().add(TranquilStone);
			} else if (drop == 3) {
				ItemStack TranquilGem = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(TranquilGem, Strings.SM_TranquilGem, "A");
				for (int i = 0; i < event.getFortuneLevel(); i++) {
					fortune = randomWithRange(1, 15);
					if (fortune < 5) event.getDrops().add(TranquilGem);
				}
				event.getDrops().add(TranquilGem);
			} else if (drop == 4) {
				ItemStack TranquilCrystal = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(TranquilCrystal, Strings.SM_TranquilCrystal, "S");
				for (int i = 0; i < event.getFortuneLevel(); i++) {
					fortune = randomWithRange(1, 15);
					if (fortune < 5) event.getDrops().add(TranquilCrystal);
				}
				event.getDrops().add(TranquilCrystal);
			}
		} else if (event.getState().getBlock() == ModBlocks.TwilightOre) {
			int drop = randomWithRange(1, 4);
			if (drop == 1) {
				ItemStack TwilightShard = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(TwilightShard, Strings.SM_TwilightShard, "C");
				for (int i = 0; i < event.getFortuneLevel(); i++) {
					fortune = randomWithRange(1, 15);
					if (fortune < 5) event.getDrops().add(TwilightShard);
				}
				event.getDrops().add(TwilightShard);
			} else if (drop == 2) {
				ItemStack TwilightStone = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(TwilightStone, Strings.SM_TwilightStone, "B");
				for (int i = 0; i < event.getFortuneLevel(); i++) {
					fortune = randomWithRange(1, 15);
					if (fortune < 5) event.getDrops().add(TwilightStone);
				}
				event.getDrops().add(TwilightStone);
			} else if (drop == 3) {
				ItemStack TwilightGem = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(TwilightGem, Strings.SM_TwilightGem, "A");
				for (int i = 0; i < event.getFortuneLevel(); i++) {
					fortune = randomWithRange(1, 15);
					if (fortune < 5) event.getDrops().add(TwilightGem);
				}
				event.getDrops().add(TwilightGem);
			} else if (drop == 4) {
				ItemStack TwilightCrystal = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(TwilightCrystal, Strings.SM_TwilightCrystal, "S");
				for (int i = 0; i < event.getFortuneLevel(); i++) {
					fortune = randomWithRange(1, 15);
					if (fortune < 5) event.getDrops().add(TwilightCrystal);
				}
				event.getDrops().add(TwilightCrystal);
			}
		} else if (event.getState().getBlock() == ModBlocks.StormyOre) {
			int drop = randomWithRange(1, 4);
			if (drop == 1) {
				ItemStack StormyShard = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(StormyShard, Strings.SM_StormyShard, "C");
				for (int i = 0; i < event.getFortuneLevel(); i++) {
					fortune = randomWithRange(1, 15);
					if (fortune < 5) event.getDrops().add(StormyShard);
				}
				event.getDrops().add(StormyShard);
			} else if (drop == 2) {
				ItemStack StormyStone = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(StormyStone, Strings.SM_StormyStone, "B");
				for (int i = 0; i < event.getFortuneLevel(); i++) {
					fortune = randomWithRange(1, 15);
					if (fortune < 5) event.getDrops().add(StormyStone);
				}
				event.getDrops().add(StormyStone);
			} else if (drop == 3) {
				ItemStack StormyGem = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(StormyGem, Strings.SM_StormyGem, "A");
				for (int i = 0; i < event.getFortuneLevel(); i++) {
					fortune = randomWithRange(1, 15);
					if (fortune < 5) event.getDrops().add(StormyGem);
				}
				event.getDrops().add(StormyGem);
			} else if (drop == 4) {
				ItemStack StormyCrystal = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(StormyCrystal, Strings.SM_StormyCrystal, "S");
				for (int i = 0; i < event.getFortuneLevel(); i++) {
					fortune = randomWithRange(1, 15);
					if (fortune < 5) event.getDrops().add(StormyCrystal);
				}
				event.getDrops().add(StormyCrystal);
			}
		} else if (event.getState().getBlock() == ModBlocks.PrizeBlox) {
			int drop = randomWithRange(1, 29);
			if (drop == 1) {
				ItemStack munny = new ItemStack(ModItems.Munny, 1);
				munny.setTagCompound(new NBTTagCompound());
				munny.getTagCompound().setInteger("amount", 50);
				event.getDrops().add(munny);
			} else if (drop == 2) {
				ItemStack munny = new ItemStack(ModItems.Munny, 1);
				munny.setTagCompound(new NBTTagCompound());
				munny.getTagCompound().setInteger("amount", 100);
				event.getDrops().add(munny);
			} else if (drop == 3) {
				ItemStack munny = new ItemStack(ModItems.Munny, 1);
				munny.setTagCompound(new NBTTagCompound());
				munny.getTagCompound().setInteger("amount", 500);
				event.getDrops().add(munny);
			} else if (drop == 4) {
				ItemStack munny = new ItemStack(ModItems.Munny, 1);
				munny.setTagCompound(new NBTTagCompound());
				munny.getTagCompound().setInteger("amount", 700);
				event.getDrops().add(munny);
			} else if (drop == 5) {
				ItemStack BlazingGem = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(BlazingGem, Strings.SM_BlazingShard, "C");
				event.getDrops().add(BlazingGem);
			} else if (drop == 6) {
				ItemStack BrightGem = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(BrightGem, Strings.SM_BrightShard, "C");
				event.getDrops().add(BrightGem);
			} else if (drop == 7) {
				ItemStack DarkGem = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(DarkGem, Strings.SM_DarkShard, "C");
				event.getDrops().add(DarkGem);
			} else if (drop == 8) {
				ItemStack DenseGem = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(DenseGem, Strings.SM_DenseShard, "C");
				event.getDrops().add(DenseGem);
			} else if (drop == 9) {
				ItemStack EnergyGem = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(EnergyGem, Strings.SM_EnergyShard, "C");
				event.getDrops().add(EnergyGem);
			} else if (drop == 10) {
				ItemStack FrostGem = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(FrostGem, Strings.SM_FrostShard, "C");
				event.getDrops().add(FrostGem);
			} else if (drop == 11) {
				ItemStack LucidGem = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(LucidGem, Strings.SM_LucidShard, "C");
				event.getDrops().add(LucidGem);
			} else if (drop == 12) {
				ItemStack PowerGem = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(PowerGem, Strings.SM_PowerShard, "C");
				event.getDrops().add(PowerGem);
			} else if (drop == 13) {
				ItemStack RemembranceGem = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(RemembranceGem, Strings.SM_RemembranceShard, "C");
				event.getDrops().add(RemembranceGem);
			} else if (drop == 14) {
				ItemStack SerenityGem = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(SerenityGem, Strings.SM_SerenityShard, "C");
				event.getDrops().add(SerenityGem);
			} else if (drop == 15) {
				ItemStack TranquilGem = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(TranquilGem, Strings.SM_TranquilShard, "C");
				event.getDrops().add(TranquilGem);
			} else if (drop == 16) {
				ItemStack TwilightGem = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(TwilightGem, Strings.SM_TwilightShard, "C");
				event.getDrops().add(TwilightGem);
			} else if (drop == 17) {
				ItemStack BlazingCrystal = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(BlazingCrystal, Strings.SM_BlazingStone, "B");
				event.getDrops().add(BlazingCrystal);
			} else if (drop == 18) {
				ItemStack BrightCrystal = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(BrightCrystal, Strings.SM_BrightStone, "B");
				event.getDrops().add(BrightCrystal);
			} else if (drop == 19) {
				ItemStack DarkCrystal = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(DarkCrystal, Strings.SM_DarkStone, "B");
				event.getDrops().add(DarkCrystal);
			} else if (drop == 20) {
				ItemStack DenseCrystal = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(DenseCrystal, Strings.SM_DenseStone, "B");
				event.getDrops().add(DenseCrystal);
			} else if (drop == 21) {
				ItemStack EnergyCrystal = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(EnergyCrystal, Strings.SM_EnergyStone, "B");
				event.getDrops().add(EnergyCrystal);
			} else if (drop == 22) {
				ItemStack FrostCrystal = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(FrostCrystal, Strings.SM_FrostStone, "B");
				event.getDrops().add(FrostCrystal);
			} else if (drop == 23) {
				ItemStack LucidCrystal = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(LucidCrystal, Strings.SM_LucidStone, "B");
				event.getDrops().add(LucidCrystal);
			} else if (drop == 24) {
				ItemStack PowerCrystal = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(PowerCrystal, Strings.SM_PowerStone, "B");
				event.getDrops().add(PowerCrystal);
			} else if (drop == 25) {
				ItemStack RemembranceCrystal = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(RemembranceCrystal, Strings.SM_RemembranceStone, "B");
				event.getDrops().add(RemembranceCrystal);
			} else if (drop == 26) {
				ItemStack SerenityCrystal = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(SerenityCrystal, Strings.SM_SerenityStone, "B");
				event.getDrops().add(SerenityCrystal);
			} else if (drop == 27) {
				ItemStack TranquilCrystal = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(TranquilCrystal, Strings.SM_TranquilStone, "B");
				event.getDrops().add(TranquilCrystal);
			} else if (drop == 28) {
				ItemStack TwilightCrystal = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(TwilightCrystal, Strings.SM_TwilightStone, "B");
				event.getDrops().add(TwilightCrystal);
			} else if (drop == 29) {
				ItemStack Orichalcum = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(Orichalcum, Strings.SM_Orichalcum, "A");
				event.getDrops().add(Orichalcum);
			}
		}

		else if (event.getState().getBlock() == ModBlocks.RarePrizeBlox) {
			int drop = randomWithRange(1, 28);
			if (drop == 1) {
				ItemStack munny = new ItemStack(ModItems.Munny, 1);
				munny.setTagCompound(new NBTTagCompound());
				munny.getTagCompound().setInteger("amount", 1000);
				event.getDrops().add(munny);
			} else if (drop == 2) {
				ItemStack munny = new ItemStack(ModItems.Munny, 1);
				munny.setTagCompound(new NBTTagCompound());
				munny.getTagCompound().setInteger("amount", 1500);
				event.getDrops().add(munny);
			} else if (drop == 3) {
				ItemStack munny = new ItemStack(ModItems.Munny, 1);
				munny.setTagCompound(new NBTTagCompound());
				munny.getTagCompound().setInteger("amount", 2000);
				event.getDrops().add(munny);
			} else if (drop == 4) {
				ItemStack munny = new ItemStack(ModItems.Munny, 1);
				munny.setTagCompound(new NBTTagCompound());
				munny.getTagCompound().setInteger("amount", 3000);
				event.getDrops().add(munny);
			} else if (drop == 5) {
				ItemStack BlazingGem = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(BlazingGem, Strings.SM_BlazingGem, "A");
				event.getDrops().add(BlazingGem);
			} else if (drop == 6) {
				ItemStack BrightGem = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(BrightGem, Strings.SM_BrightGem, "A");
				event.getDrops().add(BrightGem);
			} else if (drop == 7) {
				ItemStack DarkGem = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(DarkGem, Strings.SM_DarkGem, "A");
				event.getDrops().add(DarkGem);
			} else if (drop == 8) {
				ItemStack DenseGem = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(DenseGem, Strings.SM_DenseGem, "A");
				event.getDrops().add(DenseGem);
			} else if (drop == 9) {
				ItemStack EnergyGem = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(EnergyGem, Strings.SM_EnergyGem, "A");
				event.getDrops().add(EnergyGem);
			} else if (drop == 10) {
				ItemStack FrostGem = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(FrostGem, Strings.SM_FrostGem, "A");
				event.getDrops().add(FrostGem);
			} else if (drop == 11) {
				ItemStack LucidGem = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(LucidGem, Strings.SM_LucidGem, "A");
				event.getDrops().add(LucidGem);
			} else if (drop == 12) {
				ItemStack PowerGem = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(PowerGem, Strings.SM_PowerGem, "A");
				event.getDrops().add(PowerGem);
			} else if (drop == 13) {
				ItemStack RemembranceGem = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(RemembranceGem, Strings.SM_RemembranceGem, "A");
				event.getDrops().add(RemembranceGem);
			} else if (drop == 14) {
				ItemStack SerenityGem = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(SerenityGem, Strings.SM_SerenityGem, "A");
				event.getDrops().add(SerenityGem);
			} else if (drop == 15) {
				ItemStack TranquilGem = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(TranquilGem, Strings.SM_TranquilGem, "A");
				event.getDrops().add(TranquilGem);
			} else if (drop == 16) {
				ItemStack TwilightGem = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(TwilightGem, Strings.SM_TwilightGem, "A");
				event.getDrops().add(TwilightGem);
			} else if (drop == 17) {
				ItemStack BlazingCrystal = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(BlazingCrystal, Strings.SM_BlazingCrystal, "S");
				event.getDrops().add(BlazingCrystal);
			} else if (drop == 18) {
				ItemStack BrightCrystal = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(BrightCrystal, Strings.SM_BrightCrystal, "S");
				event.getDrops().add(BrightCrystal);
			} else if (drop == 19) {
				ItemStack DarkCrystal = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(DarkCrystal, Strings.SM_DarkCrystal, "S");
				event.getDrops().add(DarkCrystal);
			} else if (drop == 20) {
				ItemStack DenseCrystal = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(DenseCrystal, Strings.SM_DenseCrystal, "S");
				event.getDrops().add(DenseCrystal);
			} else if (drop == 21) {
				ItemStack EnergyCrystal = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(EnergyCrystal, Strings.SM_EnergyCrystal, "S");
				event.getDrops().add(EnergyCrystal);
			} else if (drop == 22) {
				ItemStack FrostCrystal = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(FrostCrystal, Strings.SM_FrostCrystal, "S");
				event.getDrops().add(FrostCrystal);
			} else if (drop == 23) {
				ItemStack LucidCrystal = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(LucidCrystal, Strings.SM_LucidCrystal, "S");
				event.getDrops().add(LucidCrystal);
			} else if (drop == 24) {
				ItemStack PowerCrystal = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(PowerCrystal, Strings.SM_PowerCrystal, "S");
				event.getDrops().add(PowerCrystal);
			} else if (drop == 25) {
				ItemStack RemembranceCrystal = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(RemembranceCrystal, Strings.SM_RemembranceCrystal, "S");
				event.getDrops().add(RemembranceCrystal);
			} else if (drop == 26) {
				ItemStack SerenityCrystal = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(SerenityCrystal, Strings.SM_SerenityCrystal, "S");
				event.getDrops().add(SerenityCrystal);
			} else if (drop == 27) {
				ItemStack TranquilCrystal = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(TranquilCrystal, Strings.SM_TranquilCrystal, "S");
				event.getDrops().add(TranquilCrystal);
			} else if (drop == 28) {
				ItemStack TwilightCrystal = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(TwilightCrystal, Strings.SM_TwilightCrystal, "S");
				event.getDrops().add(TwilightCrystal);
			} else if (drop == 29) {
				ItemStack Orichalcum = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(Orichalcum, Strings.SM_Orichalcum, "A");
				event.getDrops().add(Orichalcum);
			} else if (drop == 30) {
				ItemStack OrichalcumPlus = new ItemStack(ModItems.SynthesisMaterial, randomWithRange(1, 3));
				ItemStacks.createSynthesisItem(OrichalcumPlus, Strings.SM_OrichalcumPlus, "S");
				event.getDrops().add(OrichalcumPlus);
			}
		}
	}
}