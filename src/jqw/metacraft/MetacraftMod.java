package jqw.metacraft;

import net.minecraft.src.PlayerAPI;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
/*
import java.util.logging.Level;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import cpw.mods.fml.common.FMLLog;
*/

@Mod(modid = MetacraftMod.modid, name = MetacraftMod.name, version = MetacraftMod.version)
@NetworkMod(clientSideRequired = true, serverSideRequired = true,
	channels={MetacraftMod.modid}, packetHandler = PacketHandler.class )
public class MetacraftMod {
	public static final String modid = "Metacraft";
	public static final String name = "Metacraft";
	public static final String version = "1.5.1";

	@Instance(modid)
	public static MetacraftMod instance;

    @SuppressWarnings("static-method")
	@PreInit
	public void onPreInit(@SuppressWarnings("unused") FMLPreInitializationEvent event)
	{
	        
	}
    @SuppressWarnings("static-method")
	@Init
	public void onInit(@SuppressWarnings("unused") FMLInitializationEvent event)
	{
	        
	}
    @SuppressWarnings("static-method")
	@PostInit
    public void onPostInit(@SuppressWarnings("unused") FMLPostInitializationEvent event)
    {
    	PlayerAPI.register("MyModId", MetacraftPlayerBase.class);
    }
}
