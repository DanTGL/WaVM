package werewolvesAndVampires.items;

import java.util.List;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import werewolvesAndVampires.core.WVCore;

public class BaseItem extends Item{

	public BaseItem(String name){
		setRegistryName(name);
        setUnlocalizedName(WVCore.MODID + "." + name);
        setCreativeTab(WVCore.ctab);
	}
	
	@SideOnly(Side.CLIENT)
    public void regModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}
