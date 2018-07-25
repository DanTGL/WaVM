package werewolvesAndVampires.potions;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import werewolvesAndVampires.werewolves.WerewolfHelpers;
import werewolvesAndVampires.werewolves.capability.IWerewolf;
import werewolvesAndVampires.werewolves.capability.WerewolfProvider;
import werewolvesAndVampires.werewolves.capability.WerewolfType;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PotionWWFever extends BasePotion {

	public PotionWWFever() {
		super(true, Color.WHITE.getRGB(), "ww_fever");
		registerPotionAttributeModifier(
				SharedMonsterAttributes.MOVEMENT_SPEED,
				"7107DE5E-7CE8-4030-940E-514C1F160890",
				-0.15000000596046448D,
				2
		);
		registerPotionAttributeModifier(
				SharedMonsterAttributes.ATTACK_DAMAGE,
				"22653B89-116E-49DC-9B6B-9971489B5BE5",
				-4.0D,
				0
		);
	}

	@Override
	public void onPotionStarted(EntityLivingBase entity, int amplifier) {
		entity.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 30 * 20, 0, false, false));
	}

	@Override
	public boolean isReady(int duration, int amplifier) {
		return true;
	}

	@Override
	public void performEffect(EntityLivingBase entity, int amplifier) {
		if (entity instanceof EntityPlayer)
			((EntityPlayer)entity).addExhaustion(0.005F * (float)(amplifier + 1));
	}

	@Override
	public void onPotionEnded(EntityLivingBase entity, int amplifier) {
		IWerewolf were = entity.getCapability(WerewolfProvider.WEREWOLF_CAP, null);

		if (were != null && entity.world.getMoonPhase() == 0 && WerewolfHelpers.timeUntilFullMoon(entity.world) == 0) {
			were.setWerewolfType(WerewolfType.FULL);
		}
	}

	@Override
	public List<ItemStack> getCurativeItems() {
		return new ArrayList<>();
	}

}
