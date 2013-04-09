package jqw.metacraft;

import jqw.util.Nullable;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler {

	@Override
	public void onPacketData(@Nullable INetworkManager manager,
			@Nullable Packet250CustomPayload packet, @Nullable Player player) {
		// TODO Auto-generated method stub

	}

}
