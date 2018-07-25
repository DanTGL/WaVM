package werewolvesAndVampires.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import werewolvesAndVampires.core.WVCore;
import werewolvesAndVampires.werewolves.capability.IWerewolf;
import werewolvesAndVampires.werewolves.capability.WerewolfProvider;
import werewolvesAndVampires.werewolves.capability.WerewolfType;

public class ItemNetherApple extends BaseItemFood {

	public ItemNetherApple() {
		super(1, false, "nether_apple");
	}

	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
		super.onFoodEaten(stack, worldIn, player);

		IWerewolf were = player.getCapability(WerewolfProvider.WEREWOLF_CAP, null);
		were.setWerewolfType(WerewolfType.IMMUNE);
	}
}
