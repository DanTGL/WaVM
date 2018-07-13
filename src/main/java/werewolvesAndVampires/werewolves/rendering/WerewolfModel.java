package werewolvesAndVampires.werewolves.rendering;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;

/**
 * Werewolf - Ben Created using Tabula 7.0.0
 */
public class WerewolfModel extends ModelBase {
	public ModelRenderer torso1;
    public ModelRenderer chest;
    public ModelRenderer tail;
    public ModelRenderer leg1;
    public ModelRenderer leg2;
    public ModelRenderer arm1;
    public ModelRenderer arm2;
    public ModelRenderer head;
    public ModelRenderer snout;
    public ModelRenderer ear1;
    public ModelRenderer ear2;

    public WerewolfModel() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.chest = new ModelRenderer(this, 0, 16);
        this.chest.setRotationPoint(0.0F, -3.0F, 0.2F);
        this.chest.addBox(-5.0F, -5.0F, -3.0F, 10, 10, 6, 0.0F);
        this.ear1 = new ModelRenderer(this, 0, 0);
        this.ear1.setRotationPoint(-3.0F, -8.0F, -1.0F);
        this.ear1.addBox(-1.0F, -2.0F, 0.0F, 2, 2, 1, 0.0F);
        this.leg1 = new ModelRenderer(this, 38, 24);
        this.leg1.setRotationPoint(-2.0F, 8.5F, 0.0F);
        this.leg1.addBox(-2.0F, -0.5F, -2.0F, 4, 12, 4, 0.0F);
        this.tail = new ModelRenderer(this, 54, 0);
        this.tail.setRotationPoint(-1.0F, 6.5F, 0.0F);
        this.tail.addBox(0.0F, 0.0F, 0.0F, 2, 8, 2, 0.0F);
        this.setRotateAngle(tail, 0.4363323129985824F, 0.0F, 0.0F);
        this.leg2 = new ModelRenderer(this, 38, 24);
        this.leg2.mirror = true;
        this.leg2.setRotationPoint(2.0F, 8.5F, 0.0F);
        this.leg2.addBox(-2.0F, -0.5F, -2.0F, 4, 12, 4, 0.0F);
        this.snout = new ModelRenderer(this, 24, 0);
        this.snout.setRotationPoint(0.0F, 0.0F, -4.0F);
        this.snout.addBox(-1.5F, -3.0F, -3.0F, 3, 3, 4, 0.0F);
        this.torso1 = new ModelRenderer(this, 0, 32);
        this.torso1.setRotationPoint(0.0F, 4.0F, -1.0F);
        this.torso1.addBox(-4.0F, -8.0F, -2.0F, 8, 16, 4, 0.0F);
        this.arm1 = new ModelRenderer(this, 38, 0);
        this.arm1.setRotationPoint(-5.0F, -3.0F, 0.0F);
        this.arm1.addBox(-4.0F, -2.0F, -2.0F, 4, 16, 4, 0.0F);
        this.arm2 = new ModelRenderer(this, 38, 0);
        this.arm2.setRotationPoint(5.0F, -3.0F, 0.0F);
        this.arm2.addBox(0.0F, -2.0F, -2.0F, 4, 16, 4, 0.0F);
        this.ear2 = new ModelRenderer(this, 0, 0);
        this.ear2.setRotationPoint(3.0F, -8.0F, -1.0F);
        this.ear2.addBox(-1.0F, -2.0F, 0.0F, 2, 2, 1, 0.0F);
        this.head = new ModelRenderer(this, 0, 0);
        this.head.setRotationPoint(0.0F, -5.0F, 0.0F);
        this.head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
        this.torso1.addChild(this.chest);
        this.head.addChild(this.ear1);
        this.torso1.addChild(this.leg1);
        this.torso1.addChild(this.tail);
        this.torso1.addChild(this.leg2);
        this.head.addChild(this.snout);
        this.chest.addChild(this.arm1);
        this.chest.addChild(this.arm2);
        this.head.addChild(this.ear2);
        this.chest.addChild(this.head);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.torso1.render(f5);
    }

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch, float scaleFactor, Entity entityIn) {
		this.torso1.rotateAngleX = 0F;
		this.head.rotateAngleY = netHeadYaw * 0.017453292F;
		this.head.rotateAngleX = headPitch * 0.017453292F;

		float f = 1.0F;

		this.arm1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.5F / f;
		this.arm2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F / f;
		this.arm1.rotateAngleZ = 0.0F;
		this.arm2.rotateAngleZ = 0.0F;
		this.leg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount / f;
		this.leg2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount / f;
		this.leg1.rotateAngleY = 0.0F;
		this.leg2.rotateAngleY = 0.0F;
		this.leg1.rotateAngleZ = 0.0F;
		this.leg2.rotateAngleZ = 0.0F;
		
		this.tail.rotateAngleY = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount / 8;
		this.tail.rotateAngleX = 0.560F;
		
		this.arm1.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.arm2.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.arm1.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
        this.arm2.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
        
        if (this.swingProgress > 0.0F)
        {
            float f1 = this.swingProgress;

            this.arm1.rotationPointZ = MathHelper.sin(this.torso1.rotateAngleY) * 5.0F;
            this.arm1.rotationPointX = -MathHelper.cos(this.torso1.rotateAngleY) * 5.0F;
            this.arm2.rotationPointZ = -MathHelper.sin(this.torso1.rotateAngleY) * 5.0F;
            this.arm2.rotationPointX = MathHelper.cos(this.torso1.rotateAngleY) * 5.0F;
            this.arm1.rotateAngleY += this.torso1.rotateAngleY;
            this.arm2.rotateAngleY += this.torso1.rotateAngleY;
            this.arm2.rotateAngleX += this.torso1.rotateAngleY;
            f1 = 1.0F - this.swingProgress;
            f1 = f1 * f1;
            f1 = f1 * f1;
            f1 = 1.0F - f1;
            float f2 = MathHelper.sin(f1 * (float)Math.PI);
            float f3 = MathHelper.sin(this.swingProgress * (float)Math.PI) * -(this.head.rotateAngleX - 0.7F) * 0.75F;
           
           // arm2.rotateAngleX = (float)((double)arm1.rotateAngleX - ((double)f2 * 1.4D + (double)f3));
           // arm2.rotateAngleY = this.torso1.rotateAngleY * 2.0F;
           // arm2.rotateAngleZ += -1 * MathHelper.sin(this.swingProgress * (float)Math.PI) * -0.3F;
            
            arm1.rotateAngleX = (float)((double)arm1.rotateAngleX - ((double)f2 * 1.4D + (double)f3));
            arm1.rotateAngleY += this.torso1.rotateAngleY * 2.0F;
            arm1.rotateAngleZ += MathHelper.sin(this.swingProgress * (float)Math.PI) * -0.3F;
           
        }
	}
}
