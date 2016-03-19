package wehavecookies56.kk.entities.projectiles;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IThrowableEntity;
import wehavecookies56.kk.KingdomKeys;
import wehavecookies56.kk.entities.ExtendedPlayer;
import wehavecookies56.kk.item.ModItems;
import wehavecookies56.kk.network.packet.PacketDispatcher;
import wehavecookies56.kk.network.packet.server.GiveItemInSlot;

public class EntityIfrit extends EntityThrowable implements IThrowableEntity{
	EntityPlayer player;

	public EntityIfrit (World world) {
		super(world);
	}

	public EntityIfrit (World world, EntityLivingBase entity) {
		super(world, entity);
		this.player = (EntityPlayer) entity;
	}

	public EntityIfrit (World world, double x, double y, double z) {
		super(world, x, y, z);
	}

	@Override
	protected float getGravityVelocity () {
		return 0.0F;
	}

	@Override
	public void onUpdate () 
	{
		int rotation = 0;
		this.worldObj.spawnParticle(EnumParticleTypes.FLAME, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
		this.rotationYaw = (rotation + 1) % 360;
		boolean returning = false;
			
		if (ticksExisted > 15) {
			returning = true;
			setThrowableHeading(this.getThrower().posX - this.posX, this.getThrower().posY - this.posY + 1.25, this.getThrower().posZ - this.posZ, 1.5f, 0);
		}
		
		if (ticksExisted > 60) setDead();
		if (this.getThrower() == null) setDead();
		
		if (returning) {
			this.rotationYaw = (rotation + 1) % 360;
			ItemStack item = new ItemStack(ModItems.BlazeofGlory);
			List entityTagetList = this.worldObj.getEntitiesWithinAABB(
			Entity.class, this.getEntityBoundingBox().expand(1.0D, 1.0D, 1.0D));
			for (int i = 0; i < entityTagetList.size(); i++) {
				Entity entityTarget = (Entity) entityTagetList.get(i);
				if (entityTarget != null && entityTarget instanceof EntityPlayer) {
					EntityPlayer owner = (EntityPlayer) entityTarget;
					if (owner == this.getThrower()) {
						if (item != null) {
							int slot = owner.inventory.getFirstEmptyStack();
							PacketDispatcher.sendToServer(new GiveItemInSlot(item, slot, this.posX, this.posY, this.posZ, false));
						}
						this.setDead();
					}
				}
			}
		}
		
		super.onUpdate();
	}

	@Override
	protected void onImpact (RayTraceResult mop) {
		if (mop.entityHit != null) {
			if (mop.entityHit == this.getThrower()) {
				this.setDead();
				return;
			}
			mop.entityHit.setFire(8);
			float shotDamage;
			if (player.getCapability(KingdomKeys.PLAYER_STATS, null).getStrength() / 2 < 8)
				shotDamage = 8;
			else
				shotDamage = player.getCapability(KingdomKeys.PLAYER_STATS, null).getStrength() / 2;

			mop.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), shotDamage);
		}

		this.worldObj.spawnParticle(EnumParticleTypes.FLAME, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);

	}
	
	@Override
	public void setThrower (Entity entity) {
		
	}

}
