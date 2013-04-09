package jqw.metacraft;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public interface IContainer {

	Object getContainer(int iD, EntityPlayer player, World world, int x, int y,
			int z);

}
