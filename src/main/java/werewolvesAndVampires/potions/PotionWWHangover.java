package werewolvesAndVampires.potions;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;

import java.awt.*;

public class PotionWWHangover extends BasePotion {

	public PotionWWHangover() {
		super(true, Color.WHITE.getRGB(), "ww_hangover");
	}

	@Override
	public void performEffect(EntityLivingBase entity, int amplifier) {
		entity.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 30*20, 0, false, false));
		entity.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 30*20, 0, false, false));
		entity.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 30*20, 0, false, false));
		entity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 30*20, 0, false, false));
	}

	@Override
	public boolean isReady(int duration, int amplifier) {
		return true;
	}

	@Override
	public boolean isInstant() {
		return true;
	}
}
