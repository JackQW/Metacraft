package jqw.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


@SuppressWarnings("rawtypes")
public class Key implements List<Comparable>, Comparable<Key> {

	@SuppressWarnings("rawtypes")
	final List<Comparable> view;
	
	@SuppressWarnings({ "rawtypes", "null" }) 
	public Key(@NonNullable Comparable ... components) {
		if ( components.length == 0 )
			throw new ArrayIndexOutOfBoundsException(0);
		view = Collections.unmodifiableList(Arrays.asList(components));
	}

	@Override
	public final int hashCode() {
		return view.hashCode();
	}
	
	@SuppressWarnings("null")
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getName()).append(" [").append(view.get(0));
		for ( int i = 1, l = size(); i < l; ++i )
			builder.append(',').append(view.get(i));
		builder.append(']');
		return builder.toString();
	}

	@Override
	public final boolean equals(@Nullable Object o) {
		return ( o == null
			? false
			: o == this
				? true
				: o instanceof Key
					? view.equals(((Key)o).view)
					: false );
	}
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public final int compareTo(@Nullable Key o) {
		if ( o == null ) return -1;
		if ( o == this ) return 0;
		int r;
		r = o.size() - size();
		if ( r == 0 ) return 0;
		for ( int i = 0, l = size(); i < l; ++i ) {
			r = view.get(i).compareTo(o.view.get(i));
			if ( r != 0 ) return r;
		}
		return 0;
	}

	@Override
	public int size() {
		return view.size();
	}
	
	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public boolean contains(@Nullable Object o) {
		return view.contains(o);
	}

	@Override
	public Iterator<Comparable> iterator() {
		return NonNull.value(view.iterator());
	}

	@SuppressWarnings("null")
	@Override
	public @NonNullable Object[] toArray() {
		return view.toArray();
	}

	@Override
	public <T> T[] toArray(@Nullable T[] a) {
		return NonNull.value(view.toArray(a));
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean containsAll(@Nullable Collection<?> c) {
		return view.containsAll(c);
	}
	@Override
	public @Nullable Comparable get(int index) {
		return view.get(index);
	}
	@Override
	public int indexOf(@Nullable Object o) {
		return view.indexOf(o);
	}

	@Override
	public int lastIndexOf(@Nullable Object o) {
		return view.lastIndexOf(o);
	}

	@Override
	public ListIterator<Comparable> listIterator() {
		return NonNull.value(view.listIterator());
	}

	@Override
	public ListIterator<Comparable> listIterator(int index) {
		return NonNull.value(view.listIterator(index));
	}

	@Override
	public List<Comparable> subList(int fromIndex, int toIndex) {
		return NonNull.value(view.subList(fromIndex, toIndex));
	}

	/*** @forbidden */
	@Override
	public Comparable set(int index, @Nullable Comparable element) {
		throw new UnsupportedOperationException();
	}

	/*** @forbidden */
	@Override
	public boolean add(@Nullable Comparable e) {
		throw new UnsupportedOperationException();
	}

	/*** @forbidden */
	@Override
	public void add(int index, @Nullable Comparable element) {
		throw new UnsupportedOperationException();
	}

	/*** @forbidden */
	@Override
	public boolean addAll(@Nullable Collection<? extends Comparable> c) {
		throw new UnsupportedOperationException();
	}

	/*** @forbidden */
	@Override
	public boolean addAll(int index,
			@Nullable Collection<? extends Comparable> c) {
		throw new UnsupportedOperationException();
	}

	/*** @forbidden */
	@Override
	public Comparable remove(int index) {
		throw new UnsupportedOperationException();
	}

	/*** @forbidden */
	@Override
	public boolean remove(@Nullable Object o) {
		throw new UnsupportedOperationException();
	}

	/*** @forbidden */
	@Override
	public boolean removeAll(@Nullable Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	/*** @forbidden */
	@Override
	public boolean retainAll(@Nullable Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	/*** @forbidden */
	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}
}
