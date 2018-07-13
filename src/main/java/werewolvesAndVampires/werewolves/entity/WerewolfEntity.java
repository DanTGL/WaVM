package werewolvesAndVampires.werewolves.entity;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import werewolvesAndVampires.werewolves.WerewolfHelpers;
import werewolvesAndVampires.werewolves.capability.IWerewolf;
import werewolvesAndVampires.werewolves.capability.WerewolfProvider;
import werewolvesAndVampires.werewolves.capability.WerewolfType;

public class WerewolfEntity extends EntityVillager {

	public WerewolfEntity(World worldIn) {
		super(worldIn);
	}

	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand) {
		if (this.getCapability(WerewolfProvider.WEREWOLF_CAP, null).getIsTransformed()) {
			return false;
		}
		return super.processInteract(player, hand);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64);
		this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(4);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(7);
	}

	@Override
	protected void initEntityAI() {
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIAttackMelee(this, 1, false));
		this.tasks.addTask(1, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(2, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(2, new EntityAILookIdle(this));
		this.applyEntityAI();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void applyEntityAI() {
		this.tasks.addTask(6, new EntityAIMoveThroughVillage(this, 1.0D, false));
		this.targetTasks.addTask(2, new FindTargetWhenTransformed(this, EntityPlayer.class, true));
		this.targetTasks.addTask(3, new FindTargetWhenTransformed(this, EntityVillager.class, false));
		this.targetTasks.addTask(4, new FindTargetWhenTransformed(this, EntityAnimal.class, false));
		this.targetTasks.addTask(5, new FindTargetWhenTransformed(this, EntityCreature.class, false));
	}

	@Override
	public boolean attackEntityAsMob(Entity entityIn) {

		boolean attacked = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this),
				(float) this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());

		if (attacked) {
			
			((EntityLivingBase)entityIn).knockBack(this, 0.5F, (double)MathHelper.sin(this.rotationYaw * 0.017453292F), (double)(-MathHelper.cos(this.rotationYaw * 0.017453292F)));
            this.motionX *= 0.6D;
            this.motionZ *= 0.6D;
			
            if (entityIn instanceof EntityPlayer)
            {
                EntityPlayer entityplayer = (EntityPlayer)entityIn;
                ItemStack itemstack = this.getHeldItemMainhand();
                ItemStack itemstack1 = entityplayer.isHandActive() ? entityplayer.getActiveItemStack() : ItemStack.EMPTY;

                if (!itemstack.isEmpty() && !itemstack1.isEmpty() && itemstack.getItem() instanceof ItemAxe && itemstack1.getItem() == Items.SHIELD)
                {
                    float f1 = 0.25F + (float)EnchantmentHelper.getEfficiencyModifier(this) * 0.05F;

                    if (this.rand.nextFloat() < f1)
                    {
                        entityplayer.getCooldownTracker().setCooldown(Items.SHIELD, 100);
                        this.world.setEntityState(entityplayer, (byte)30);
                    }
                }
            }
            
			IWerewolf were = entityIn.getCapability(WerewolfProvider.WEREWOLF_CAP, null);
			if (entityIn instanceof EntityLivingBase && were != null && were.getWerewolfType() != WerewolfType.FULL
					&& this.getCapability(WerewolfProvider.WEREWOLF_CAP, null).getIsTransformed()) {
				were.setWerewolfType(WerewolfType.INFECTED);
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (this.isServerWorld() && !this.world.isDaytime() && this.world.getCurrentMoonPhaseFactor() == 1F) {
			IWerewolf were = this.getCapability(WerewolfProvider.WEREWOLF_CAP, null);
			if (!were.getIsTransformed()
					&& this.world.canBlockSeeSky(new BlockPos(this.posX, this.posY + 1, this.posZ))) {
				WerewolfHelpers.transformEntity(this, were, true);
			}
		} else if (this.isServerWorld()) {
			IWerewolf were = this.getCapability(WerewolfProvider.WEREWOLF_CAP, null);
			if (were.getIsTransformed())
				WerewolfHelpers.transformEntity(this, were, false);
		}
	}
	
	

	public class FindTargetWhenTransformed extends EntityAINearestAttackableTarget {

		public FindTargetWhenTransformed(EntityCreature creature, Class classTarget, boolean checkSight) {
			super(creature, classTarget, checkSight);
		}

		@Override
		public boolean shouldExecute() {
			if (this.taskOwner.getCapability(WerewolfProvider.WEREWOLF_CAP, null) != null
					&& this.taskOwner.getCapability(WerewolfProvider.WEREWOLF_CAP, null).getIsTransformed()) {
				return super.shouldExecute();
			}
			return false;
		}
		
		@Override
		protected boolean isSuitableTarget(EntityLivingBase target, boolean includeInvincibles) {
			if(target instanceof WerewolfEntity)
				return false;
			return super.isSuitableTarget(target, includeInvincibles);
		}

	}
}
