package jqw.util;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

	
public final class IniReader implements Iterable<Map.Entry<IniReader.IniKey,String>> {
	final static Pattern pComment = Pattern.compile( "^\\s*;" );
	final static Pattern pSection = Pattern.compile( "^\\s*\\[([^]]*)\\]" );
	final static Pattern pKeyValue = Pattern.compile( "^\\s*([^=]*)=(.*)" );
    final NavigableMap<IniKey, String> entries = new ConcurrentSkipListMap<>();
    
    public final class IniKey extends Key {
    	public IniKey( @Nullable String section, String key ) { super( section, key ); }
        public final @Nullable String getSection() { return (String) get(0); }
        public final String getKey() { return (String) NonNull.value( get(1) ); }
    }

    public IniReader( String path ) throws IOException {
        try ( BufferedReader br = new BufferedReader( new FileReader( path )) ) {
	        String line;
	        String section = null;
	        Matcher m;
	        while(( line = br.readLine()) != null ) {
	        	if ( pComment.matcher( line ).matches() )
	        		continue; // comment
	            if( (m = pSection.matcher( line )).matches() ) {
	                section = m.group( 1 ).trim();
	                continue; // section
	            }
	            if( !(m = pKeyValue.matcher( line )).matches() )
	            	continue; // junk
	            // entry
	            entries.put( new IniKey( section, NonNull.value( m.group( 1 ).trim() ) ),
	            		m.group( 2 ).trim() );
	        }
	        return;
        }
    }
    public final String get( String key, String defaultvalue ) {
        return get( null, key, defaultvalue );
    }

    public final String get( @Nullable String section, String key, String defaultvalue ) {
        String entry = entries.get( new IniKey(section, key) );
        return NonNull.value(entry, defaultvalue);
    }

    public final class IniEntryIterator implements Iterator<Map.Entry<IniKey,String>> {
        NavigableMap<IniKey, String> cursor;
        public IniEntryIterator( IniReader ifr ) { cursor = ifr.entries; }
        @Override
        public final boolean hasNext() { return !cursor.isEmpty(); }
        @Override
        public final @Nullable Map.Entry<IniKey,String> next() {
            Map.Entry<IniKey,String> entry = cursor.firstEntry();
            cursor = cursor.tailMap( entry.getKey(), false );
            return entry;
        }
        @Override
        public final void remove() { cursor.remove(cursor.firstKey()); }
    }

    @Override
    public final Iterator<Map.Entry<IniKey, String>> iterator() {
        return new IniEntryIterator( this );
    }
}
