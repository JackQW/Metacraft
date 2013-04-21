package jqw.util;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Map;
import java.util.logging.Logger;

public class HookedSystemClassLoader extends URLClassLoader {
	@Nullable
	final Logger logger;
	
	@NonNullable
	final Map<String,Class<?>> overrides;
	
	@NonNullable
	static final URL[] emptyUrls = new URL[] {};

	/**
	 * Instantiates a hooked system class loader that overloads classes based on a given map.
	 * @param map The name-to-class override map.
	 */
	public HookedSystemClassLoader(@NonNullable Map<String,Class<?>> map) {
		this(null, map);
	}
	/**
	 * Instantiates a hooked system class loader that overloads classes based on a given map.
	 * @param map The name-to-class override map.
	 * @param urls The additional {@link URL}s from which to load.
	 */
	public HookedSystemClassLoader(@NonNullable Map<String,Class<?>> map, URL ... urls) {
		this(null, map, urls);
	}

	/**
	 * Instantiates a hooked system class loader that overloads classes based on a given map.
	 * @param map The name-to-class override map.
	 * @param log A logger for reporting hooking activity on.
	 */
	public HookedSystemClassLoader(@NonNullable Map<String,Class<?>> map, @Nullable Logger log) {
		this(log, map);
	}

	/**
	 * Instantiates a hooked system class loader that overloads classes based on a given map.
	 * @param map The name-to-class override map.
	 * @param log A logger for reporting hooking activity on.
	 * @param urls The additional {@link URL}s from which to load.
	 */
	public HookedSystemClassLoader(@NonNullable Map<String,Class<?>> map, @Nullable Logger log, URL ... urls) {
		this(log, map, urls);
	}

	/**
	 * Instantiates a hooked system class loader that overloads classes based on a given map.
	 * @param log A logger for reporting hooking activity on.
	 * @param map The name-to-class override map.
	 */
	public HookedSystemClassLoader(@Nullable Logger log, @NonNullable Map<String,Class<?>> map) {
		super(emptyUrls, ClassLoader.getSystemClassLoader());
		overrides = map;
		logger = log;
		if ( logger != null )
			logger.info("HookedSystemClassLoader hooking "+
				Arrays.deepToString( map.keySet().toArray() ) );
	}
	/**
	 * Instantiates a hooked system class loader that overloads classes based on a given map.
	 * @param log A logger for reporting hooking activity on.
	 * @param map The name-to-class override map.
	 * @param urls The additional {@link URL}s from which to load.
	 */
	public HookedSystemClassLoader(@Nullable Logger log, @NonNullable Map<String,Class<?>> map, URL ... urls) {
		super(urls, ClassLoader.getSystemClassLoader());
		overrides = map;
		logger = log;
		if ( logger != null )
			logger.info("HookedSystemClassLoader hooking "+
				Arrays.deepToString( map.keySet().toArray() ) );
	}
	
	/**
	 * Hooked version of ClassLoader.loadClass
	 * @see java.lang.ClassLoader#loadClass(String name)
	 */
	@Override @Nullable
	public Class<?> loadClass(@Nullable String name) throws ClassNotFoundException {
		Class<?> override = overrides.get(name);
		if ( override != null ) {
			if ( logger != null )
				logger.info("Hooked loading of "+name+" to override "+override.getName());
			return override;
		}
		return super.loadClass(name);
	}

	/**
	 * Hooked version of ClassLoader.loadClass (resolve path)
	 * @see java.lang.ClassLoader#loadClass(String name, boolean resolve)
	 */
	@Override @Nullable
	protected Class<?> loadClass(@Nullable String name, boolean resolve)
			throws ClassNotFoundException {
		Class<?> override = overrides.get(name);
		if ( override != null ) {
			if ( logger != null )
				logger.info("Hooked resolving of "+name+" to override "+override.getName());
			return override;
		}
		return super.loadClass(name, resolve);
	}

	/**
	 * Hooked version of ClassLoader.findClass
	 * @see java.lang.ClassLoader#findClass(String name)
	 */
	@Override @Nullable
	protected Class<?> findClass(@Nullable String name) throws ClassNotFoundException {
		Class<?> override = overrides.get(name);
		if ( override != null ) {
			if ( logger != null )
				logger.info("Hooked finding of "+name+" to override "+override.getName());
			return override;
		}
		return super.findClass(name);
	}
}