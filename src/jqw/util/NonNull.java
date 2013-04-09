package jqw.util;

public abstract class NonNull {
	public static final <T> T value( @Nullable T o ) throws NullPointerException {
		if ( o != null ) return o;
		throw new NullPointerException();
	}
	public static final <T> T value( @Nullable T o, T alt) {
		return o != null ? o : alt;
	}

	public static final <T> String string( @Nullable T o ) {
		if ( o == null )
			return "null";
		@Nullable
		String v = o.toString();
		if ( v != null )
			return v;
		Class<?> c = o.getClass();
		int h;
		try { h = o.hashCode(); }
		catch ( Throwable t ) { h = System.identityHashCode(o); }
		return c.getName() + '@' + h;
	}
	@SuppressWarnings("null") public static final <T> String string( byte o ) { return Byte.toString(o); }
	@SuppressWarnings("null") public static final <T> String string( char o ) { return Character.toString(o); }
	@SuppressWarnings("null") public static final <T> String string( int o ) { return Integer.toString(o); }
	@SuppressWarnings("null") public static final <T> String string( long o ) { return Long.toString(o); }
	@SuppressWarnings("null") public static final <T> String string( float o ) { return Float.toString(o); }
	@SuppressWarnings("null") public static final <T> String string( double o ) { return Double.toString(o); }
}
