package uk.co.wehavecookies56.kk.client.model.entity;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import uk.co.wehavecookies56.kk.common.entity.EntityShadow;

@SideOnly(Side.CLIENT)
public class ModelShadow extends ModelBiped {

	public ModelShadow()
	{
		this(0.0F, false);
	}

	public ModelShadow(float par1, boolean par2)
	{
		super(par1, 0.0F, 64, 32);

		if(!par2){
			bipedRightArm = new ModelRenderer(this, 40, 16);
			bipedRightArm.addBox(-1.0F, -2.0F, -1.0F, 2, 12, 2, par1);
			bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
			bipedLeftArm = new ModelRenderer(this, 40, 16);
			bipedLeftArm.mirror = true;
			bipedLeftArm.addBox(-1.0F, -2.0F, -1.0F, 2, 12, 2, par1);
			bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
			bipedRightLeg = new ModelRenderer(this, 0, 16);
			bipedRightLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 12, 2, par1);
			bipedRightLeg.setRotationPoint(-2.0F, 12.0F, 0.0F);
			bipedLeftLeg = new ModelRenderer(this, 0, 16);
			bipedLeftLeg.mirror = true;
			bipedLeftLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 12, 2, par1);
			bipedLeftLeg.setRotationPoint(2.0F, 12.0F, 0.0F);
		}
	}

	@Override
	public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float p_78086_2_, float p_78086_3_, float partialTickTime)
	{
		rightArmPose = ModelBiped.ArmPose.EMPTY;
		leftArmPose = ModelBiped.ArmPose.EMPTY;
		ItemStack itemstack = entitylivingbaseIn.getHeldItem(EnumHand.MAIN_HAND);
		super.setLivingAnimations(entitylivingbaseIn, p_78086_2_, p_78086_3_, partialTickTime);
	}

	/**
	 * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
	 * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
	 * "far" arms and legs can swing at most.
	 */
	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
	{
		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
		ItemStack itemstack = ((EntityLivingBase)entityIn).getHeldItemMainhand();
		EntityShadow entityskeleton = (EntityShadow)entityIn;

		if (itemstack == null || itemstack.getItem() != Items.BOW)
		{
			float f = MathHelper.sin(swingProgress * (float)Math.PI);
			float f1 = MathHelper.sin((1.0F - (1.0F - swingProgress) * (1.0F - swingProgress)) * (float)Math.PI);
			bipedRightArm.rotateAngleZ = 0.0F;
			bipedLeftArm.rotateAngleZ = 0.0F;
			bipedRightArm.rotateAngleY = -(0.1F - f * 0.6F);
			bipedLeftArm.rotateAngleY = 0.1F - f * 0.6F;
			bipedRightArm.rotateAngleX = -((float)Math.PI / 2F);
			bipedLeftArm.rotateAngleX = -((float)Math.PI / 2F);
			bipedRightArm.rotateAngleX -= f * 1.2F - f1 * 0.4F;
			bipedLeftArm.rotateAngleX -= f * 1.2F - f1 * 0.4F;
			bipedRightArm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
			bipedLeftArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
			bipedRightArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
			bipedLeftArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
		}
	}

	@Override
	public void postRenderArm(float scale, EnumHandSide side)
	{
		float f = side == EnumHandSide.RIGHT ? 1.0F : -1.0F;
		ModelRenderer modelrenderer = getArmForSide(side);
		modelrenderer.rotationPointX += f;
		modelrenderer.postRender(scale);
		modelrenderer.rotationPointX -= f;
	}
}