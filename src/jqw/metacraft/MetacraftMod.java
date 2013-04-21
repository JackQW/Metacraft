package jqw.metacraft;

//import net.minecraft.src.PlayerAPI;
import java.io.File;
import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;

import net.minecraft.block.Block;

import jqw.util.InstrumentationExposer;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
/*
import cpw.mods.fml.common.versioning.ArtifactVersion;
import cpw.mods.fml.common.versioning.DefaultArtifactVersion;
*/

@Mod(modid = MetacraftMod.modid, useMetadata = true)
@NetworkMod(clientSideRequired = true, serverSideRequired = true,
	channels={MetacraftMod.modid}, packetHandler = PacketHandler.class )
public class MetacraftMod {
	public static Logger logger;
	public static ModMetadata metadata;
	public static File configDir;
	public static final String modid = "Metacraft";
	public static final String version = "1.5.1";
	public static Instrumentation inst = null;
	
	public static Block b = null;

	@Instance(modid)
	public static MetacraftMod instance;

    @SuppressWarnings("static-method")
	@PreInit
	public void onPreInit(@SuppressWarnings("unused") FMLPreInitializationEvent event)
	{
    	logger = event.getModLog();
    	metadata = event.getModMetadata();
    	configDir = event.getModConfigurationDirectory();
    	logger.info("Pre-Initializing Metacraft...");
	}
    @SuppressWarnings("static-method")
	@Init
	public void onInit(@SuppressWarnings("unused") FMLInitializationEvent event)
	{
    	logger.info("Initializing Metacraft...");
	}
    @SuppressWarnings("static-method")
	@PostInit
    public void onPostInit(@SuppressWarnings("unused") FMLPostInitializationEvent event) throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException, NullPointerException, IOException
    {
    	logger.info("Post-Initializing Metacraft...");
    	inst = InstrumentationExposer.getInstrumentation(logger);
    	
    	logger.info("size of this:" + inst.getObjectSize(this) );
    }
}
