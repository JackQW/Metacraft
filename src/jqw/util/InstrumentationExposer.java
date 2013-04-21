/**
 * 
 */
package jqw.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.instrument.*;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.logging.Logger;



/**
 * @author Jack Q. Word
 *
 */
public class InstrumentationExposer {
	
	@Nullable
	static Logger logger = null;

	static final String os = System.getProperty("os.name").toLowerCase();

	static final int Win = 0;
	static final int Lnx = 1;
	static final int Mac = 2;
	static final int Sol = 3;
	static final int sysTypeId = os.contains("solaris")
			? Sol
			: os.contains("mac")
				? Mac
				: os.contains("linux") || os.contains("unix")
					? Lnx
					: os.contains("win")
						? Win
						: -1;

	static final String nativeAttachProviderClassName =
			sysTypeId == Lnx || sysTypeId == Mac
					? "sun.tools.attach.LinuxAttachProvider"
					: sysTypeId == Win
						? "sun.tools.attach.WindowsAttachProvider"
						: "sun.tools.attach.SolarisAttachProvider";
	
	static final String nativeVirtualMachineClassName =
			sysTypeId == Lnx || sysTypeId == Mac
			? "sun.tools.attach.LinuxVirtualMachine"
			: sysTypeId == Win
				? "sun.tools.attach.WindowsVirtualMachine"
				: "sun.tools.attach.SolarisVirtualMachine";

	static final String[] toolClassNames = new String[] {
		"com.sun.tools.attach.AttachNotSupportedException",
		"com.sun.tools.attach.AgentInitializationException",
		"com.sun.tools.attach.VirtualMachine",
		"com.sun.tools.attach.VirtualMachineDescriptor",
		"com.sun.tools.attach.AgentLoadException",
		"com.sun.tools.attach.AttachPermission",
		"com.sun.tools.attach.spi.AttachProvider",
		"sun.tools.attach.HotSpotAttachProvider",
		"sun.tools.attach.HotSpotVirtualMachine",
		nativeAttachProviderClassName,
		nativeVirtualMachineClassName
	};

	public static final Class<?>[] urlClassArg = new Class<?>[] { URL.class };
	public static final Class<?>[] classArg = new Class<?>[] { Class.class };
	
	static final Map<String,Class<?>> toolClassMap = new HashMap<>();

    static final Manifest manifest = new Manifest();
	@Nullable
	static Instrumentation ginst = null;
	
    

	static final Class<InstrumentationExposer> getStaticClass() {
		return InstrumentationExposer.class;
	}
	
	public static void Init(@Nullable Logger l) throws IOException {
		logger = l;
		
		String trueStr = Boolean.TRUE.toString();
        Attributes attrs = manifest.getMainAttributes();
        attrs.put(Attributes.Name.MANIFEST_VERSION, "1.0");
        attrs.putValue("Agent-Class", getStaticClass().getName());
        attrs.putValue("Can-Redefine-Classes", trueStr);
        attrs.putValue("Can-Retransform-Classes", trueStr);
        attrs.putValue("Can-Set-Native-Method-Prefix", trueStr);
        
        try ( ByteArrayOutputStream baos = new ByteArrayOutputStream() ) {
	        manifest.write(baos);
	        logger.info( baos.toString() );
        }
		
		ClassLoader cl = ClassLoader.getSystemClassLoader();

		Class<ClassLoader> clc = ClassLoader.class;
		Class<URLClassLoader> uclc = URLClassLoader.class;
		
		try {
			String javaHome = System.getProperty("java.home");
			if ( logger != null ) logger.info("java.home = "+javaHome);
			URL javaHomeLib = Paths.get(System.getProperty("java.home"), "..", "lib", "tools.jar").toUri().toURL();
			Method mAddUrl = uclc.getDeclaredMethod("addURL", urlClassArg);
			mAddUrl.setAccessible(true);
			mAddUrl.invoke(cl, new Object[] { javaHomeLib } );
			if ( logger != null ) logger.info("added to classpath (?): "+javaHomeLib.toString());
		} catch ( Throwable t ) {
			throw new RuntimeException( "Failed to set up class loader.", t );
		}


		for ( String toolClassName : toolClassNames ) {
			try {
				toolClassMap.put(toolClassName, cl.loadClass(toolClassName));
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
		}
		try {
			Field fscl = clc.getDeclaredField("scl");
			fscl.setAccessible(true);

			@NonNullable @SuppressWarnings("resource")
			final HookedSystemClassLoader hscl =
				new HookedSystemClassLoader(logger);
			
			fscl.set(null, hscl);
			Method mResolveClass = clc.getDeclaredMethod("resolveClass", classArg);
			mResolveClass.setAccessible(true);
			mResolveClass.invoke(cl, new Object[] { getStaticClass() } );
		} catch ( Throwable t ) {
			throw new RuntimeException( "Failed to resolve self in class loader.", t );
		}
	}
	
    static final @NonNullable File generateAgentJar() throws NullPointerException, IOException {
        // Prepare generated jar output file
    	@NonNullable
    	final String classSmplName = NonNull.value(getStaticClass().getSimpleName());
    	@NonNullable
        final File jarFile = NonNull.value(File.createTempFile(classSmplName, ".jar"));

    	try ( @NonNullable final FileOutputStream jarFos =
    			new FileOutputStream(jarFile) ) {
    	
	        // Prepare jar output stream
	        try ( @NonNullable final JarOutputStream jarOS =
	        		new JarOutputStream(jarFos, manifest) ) {
	
		        // Return generated jar file
		        return jarFile;
	        }
    	}
    }
    
	public static final String getPid() {
		@NonNullable
		final RuntimeMXBean bean = NonNull.value( ManagementFactory.getRuntimeMXBean() );
        @NonNullable
        final String beanName = NonNull.value( bean.getName() );
        @NonNullable
        final String pid = beanName.contains("@")
        	? NonNull.value(beanName.substring(0, beanName.indexOf("@")))
        	: beanName;

        return pid;
	}
	
	public static final Instrumentation getInstrumentation() throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException, NullPointerException, IOException {
		return getInstrumentation(null);
	}
	
	public static final Instrumentation getInstrumentation(@Nullable Logger l) throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException, NullPointerException, IOException {
		Instrumentation inst = ginst;
		if ( inst != null )
			return inst;
		
		Init(l);

        @NonNullable
        Class<?> apClass = NonNull.value(toolClassMap
        		.get( "com.sun.tools.attach.spi.AttachProvider" ));
        @NonNullable
        Class<?> vmClass = NonNull.value(toolClassMap
        		.get( "com.sun.tools.attach.VirtualMachine" ));
        @NonNullable
        Class<?> napClass = NonNull.value(toolClassMap
        		.get( nativeAttachProviderClassName ));
        @NonNullable
        Class<?> nvmClass = NonNull.value(toolClassMap
        		.get( nativeVirtualMachineClassName ));
        
        @NonNullable
        Object nattProv = NonNull.value(napClass.newInstance());
        
        @NonNullable
        Constructor<?> nvmCtor = NonNull.value(nvmClass
        		.getDeclaredConstructor( apClass, String.class )); 
        
        nvmCtor.setAccessible(true);
        
        @NonNullable
		Object vm = NonNull.value(nvmCtor.newInstance( nattProv, getPid() ));
        // Load agent
        vmClass.getMethod("loadAgent", String.class)
        	.invoke(vm, generateAgentJar().getPath());
        // Detach
        vmClass.getMethod("detach").invoke(vm);
        
        return NonNull.value( ginst );
	}

	/**
	 * Entry point for JVMTI Agent initialization.
	 * @param args
	 * @param inst
	 */
	public static final void agentmain(String args, Instrumentation inst) {
		ginst = inst;
	}
	
}
