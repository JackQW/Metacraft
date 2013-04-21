package jqw.util;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.logging.Logger;

public class HookedSystemClassLoader extends URLClassLoader {
	@Nullable
	final Logger logger;
	
	@NonNullable
	static final Class<?> c = InstrumentationExposer.getStaticClass();
	
	@NonNullable
	static final String cn = c.getName();
	
	@NonNullable
	static final URL[] urls = new URL[] {};
	
	public HookedSystemClassLoader() {
		this(null);
	}
	
	public HookedSystemClassLoader(@Nullable Logger l) {
		super(urls, ClassLoader.getSystemClassLoader());
		logger = l;
		if ( logger != null )
			logger.info("HookedSystemClassLoader hooking "+cn);
	}
	
	@Override @Nullable
	public Class<?> loadClass(@Nullable String name) throws ClassNotFoundException {
		if ( logger != null )
			logger.info("HookedSystemClassLoader loading "+name);
		
		if ( cn.equals(name) ) {
			if ( logger != null )
				logger.info("Hooked loading of "+cn);
			return c;
		}
		if ( logger != null )
			logger.info(name+" didn't equal "+cn);
		
		return super.loadClass(name);
	}
	
	@Override @Nullable
	protected Class<?> loadClass(@Nullable String name, boolean resolve)
			throws ClassNotFoundException {
		if ( logger != null ) logger.info("HookedSystemClassLoader resolving "+name);

		if ( cn.equals(name) ) {
			if ( logger != null )
				logger.info("Hooked resolving of "+cn);
			return c;
		}
		if ( logger != null )
			logger.info(name+" didn't equal "+cn);
		
		return super.loadClass(name, resolve);
	}
	
	@Override @Nullable
	protected Class<?> findClass(@Nullable String name) throws ClassNotFoundException {
		if ( logger != null ) logger.info("HookedSystemClassLoader finding "+name);

		if ( cn.equals(name) ) {
			if ( logger != null )
				logger.info("Hooked finding of "+cn);
			return c;
		}
		if ( logger != null )
			logger.info(name+" didn't equal "+cn);
		
		return super.findClass(name);
	}
}