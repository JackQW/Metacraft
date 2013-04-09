package jqw.metacraft;

import jqw.util.NonNullable;
import jqw.util.Nullable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
/*
import net.minecraft.inventory.Container;
*/
public class CommonProxy implements IGuiHandler {
    public void registerRenderInformation() {

    }
    
	@Override
	public @Nullable Object getServerGuiElement(int ID, @Nullable EntityPlayer player, @Nullable World world,
			int x, int y, int z) {
		if ( world != null ) {
	        TileEntity te = world.getBlockTileEntity(x, y, z);
	        @NonNullable EntityPlayer aplayer;
	        if ( player == null ) {
				if ( world.playerEntities.size() == 0 )
	        		return null;
				player = (EntityPlayer)world.playerEntities.get(0);
			}
	        if ( player != null )
	        	aplayer = player;
	        else
	        	return null;
	        if (te != null) {
	        	if ( te instanceof IContainer ) {
	        		return ((IContainer)te).getContainer(ID, aplayer, world, x, y, z);
	        	} else if ( te instanceof IGuiHandler ) {
	        		return ((IGuiHandler)te).getServerGuiElement(ID, player, world, x, y, z);
	        	} else {
	        		return null;
	        	}
	        }
		}
        return null;
	}

	@Override
	public @Nullable Object getClientGuiElement(int ID, @Nullable EntityPlayer player, @Nullable World world,
			int x, int y, int z) {
        return null;
	}

    @SuppressWarnings("static-method")
	public @Nullable World getClientWorld() {
        return null;
    }
}
