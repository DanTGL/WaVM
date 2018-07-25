package werewolvesAndVampires.items;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSeedFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import werewolvesAndVampires.core.WVBlocks;
import werewolvesAndVampires.core.WVCore;

public class ItemGarlic extends ItemSeedFood {

	public ItemGarlic() {
		super(1, 0.3F, WVBlocks.garlic_crop, Blocks.FARMLAND);
		setRegistryName(new ResourceLocation(WVCore.MODID, "garlic"));
		setUnlocalizedName(WVCore.MODID + ".garlic");
	}

	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {

	}

	@SideOnly(Side.CLIENT)
	public void regModel() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	}

}
