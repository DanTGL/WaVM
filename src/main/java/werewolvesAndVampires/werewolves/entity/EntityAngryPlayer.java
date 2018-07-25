package werewolvesAndVampires.werewolves.entity;

import net.minecraft.entity.EntityCreature;
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
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import werewolvesAndVampires.werewolves.WerewolfEventhandler;

public class EntityAngryPlayer extends EntityMob {

	private static final DataParameter<Integer> PLAYER_ID = EntityDataManager.createKey(EntityAngryPlayer.class,
			DataSerializers.VARINT);

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

	public void setIdNull() {
		this.dataManager.set(PLAYER_ID, Integer.MIN_VALUE);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(9);
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
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
		this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityVillager.class, false));
		this.targetTasks.addTask(4, new EntityAINearestAttackableTarget(this, EntityAnimal.class, false));
		this.targetTasks.addTask(5, new EntityAINearestAttackableTarget(this, EntityCreature.class, false));
	}

	@Override
	public void onDeath(DamageSource cause) {
		if (this.world.getEntityByID(this.dataManager.get(PLAYER_ID)) != null)
			this.world.getEntityByID(this.dataManager.get(PLAYER_ID)).setDead();
		WerewolfEventhandler.cam = null;
		super.onDeath(cause);
	}

	@Override
	public void onLivingUpdate() {
		if (this.world.getEntityByID(this.dataManager.get(PLAYER_ID)) != null) {
			this.world.getEntityByID(this.dataManager.get(PLAYER_ID)).setPositionAndUpdate(posX, posY + 2, posZ);
		} else {
			this.setDead();
			WerewolfEventhandler.cam = null;
		}
		super.onLivingUpdate();
	}
}
