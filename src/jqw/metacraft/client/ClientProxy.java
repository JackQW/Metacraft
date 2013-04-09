package jqw.metacraft.client;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import jqw.metacraft.CommonProxy;
import jqw.util.Nullable;

public class ClientProxy extends CommonProxy {
    @Override
    public void registerRenderInformation()
    {
    	
    }

	@Override
    public @Nullable World getClientWorld()
    {
        return FMLClientHandler.instance().getClient().theWorld;
    }

    @Override
    public @Nullable Object getClientGuiElement(int ID, @Nullable EntityPlayer player, @Nullable World world, int x, int y, int z)
    {
    	if (world != null ) {
	        TileEntity te = world.getBlockTileEntity(x, y, z);
	        if (te != null && te instanceof IGuiHandler) {
	        	return ((IGuiHandler)te).getClientGuiElement( ID, player, world, x, y, z );
	        }
    	}
        return null;
    }
}
