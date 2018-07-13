package werewolvesAndVampires.werewolves.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import werewolvesAndVampires.werewolves.capability.WerewolfProvider;

public class EntityAngryPlayer extends EntityMob {

	private static final DataParameter<Integer> PLAYER_ID = EntityDataManager.createKey(EntityAngryPlayer.class, DataSerializers.VARINT);

	public EntityAngryPlayer(World worldIn) {
		super(worldIn);
	}

	@SuppressWarnings("static-access")
	public EntityAngryPlayer(World worldIn, EntityPlayer playerIn) {
		super(worldIn);
		this.dataManager.set(PLAYER_ID, playerIn.getEntityId());
		this.setPosition(playerIn.posX, playerIn.posY, playerIn.posZ);
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(PLAYER_ID, 0);
	}
	
	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
		return super.onInitialSpawn(difficulty, livingdata);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(9);
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
	public void onDeath(DamageSource cause) {
		if (this.world.getEntityByID(this.dataManager.get(PLAYER_ID)) != null)
			this.world.getEntityByID(this.dataManager.get(PLAYER_ID)).setDead();
		super.onDeath(cause);
	}

	@Override
	public void onLivingUpdate() {
		if (this.world.getEntityByID(this.dataManager.get(PLAYER_ID)) != null) {
			if (!this.isServerWorld()) {
				Minecraft.getMinecraft().setRenderViewEntity(this);
			}else {
				this.world.getEntityByID(this.dataManager.get(PLAYER_ID)).posY = 300;
				this.world.getEntityByID(this.dataManager.get(PLAYER_ID)).fallDistance = 0;
			}
		} else {
			this.setDead();
		}
		super.onLivingUpdate();
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
			if (target instanceof WerewolfEntity)
				return false;
			return super.isSuitableTarget(target, includeInvincibles);
		}

	}
}
