package com.activenetwork.awo.common;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

//import com.google.common.base.Function;
//import com.google.common.base.Supplier;
//import com.google.common.collect.Iterables;
//import com.google.common.collect.Lists;
//import com.google.common.collect.Maps;
//import com.reserveamerica.common.TokenizedStringBuilder.Formatter;
//import com.reserveamerica.commons.Identifiable;

public final class ColUtil {

	private ColUtil() {
		// prevent construction
	}

	/**
	 * This method provides a way to obtain an object from a collection, given
	 * an equal object (logical equals). In the collection and set interfaces
	 * (even list) the assumption is that if you have an equal object why would
	 * you need the one out of the collection. Because logically equal objects
	 * can be physically different objects we provide this method to return out
	 * of a collection the actual object in the collection ( for partial object
	 * checking and untrusted object arguements that reference an existing item
	 * in a collection ).
	 * 
	 * @param p_oCol
	 * @param p_oObj
	 * @return
	 */
	public static <T> T get(Collection<T> p_oCol, T p_oObj) {
		if (p_oObj == null)
			return null;
		if (isEmpty(p_oCol))
			return null;
		if (p_oCol instanceof List)
			return get(p_oCol, ((List<T>) p_oCol).indexOf(p_oObj));
		T oObj = null;
		T oTempObj = null;

		for (Iterator<T> oIter = p_oCol.iterator(); oIter.hasNext();) {
			oTempObj = oIter.next();
			if (p_oObj.equals(oTempObj)) {
				oObj = oTempObj;
				break;
			}
		}

		return oObj;
	}

	/**
	 * Mostly provided for sets, Please keep in mind sets don't guarantee any
	 * specific order. And it could be that for any one instance of a set this
	 * method may return 2 different objects for the same index (dependent on
	 * the set implementation) This method is useful for getting a single item
	 * out of a collection when you don't know what type of collection you are
	 * dealing with.
	 * 
	 * @param p_oCol
	 * @param p_iIndex
	 * @return
	 */
	public static <T> T get(Collection<T> p_oCol, int p_iIndex) {
		if (isEmpty(p_oCol))
			return null;
		if (p_iIndex < 0 || p_iIndex >= p_oCol.size())
			return null;
		if (p_oCol instanceof List)
			return ((List<T>) p_oCol).get(p_iIndex);

		T oObj = null;
		T oTempObj = null;
		int i = 0;

		for (Iterator<T> oIter = p_oCol.iterator(); oIter.hasNext();) {
			oTempObj = oIter.next();
			if (i++ == p_iIndex) {
				oObj = oTempObj;
				break;
			}
		}

		return oObj;
	}

	/**
	 * Checks each of the given collections to ensure they are all empty
	 * 
	 * @param collections
	 *            Collections to check
	 * @return True if all collections are empty
	 */
	public static boolean areEmpty(Collection<?>... collections) {
		if (collections == null) { // If null reference is passed then there's
									// nothing there so technically these are
									// empty
			return true;
		}
		for (Collection<?> col : collections) {
			if (!isEmpty(col)) {
				return false;
			}
		}
		return true;
	}

	public static boolean isEmpty(Iterable<?> i) {
		if (i instanceof Collection)
			return ((Collection<?>) i).isEmpty();
		return i == null || !i.iterator().hasNext();
	}

	public static boolean isEmpty(Map<?, ?> p_oCol) {
		return p_oCol == null || p_oCol.isEmpty();
	}

	@SuppressWarnings("unchecked")
	public static <T> void sort(List<T> p_oCol) {
		Object oaObj[] = p_oCol.toArray();
		Arrays.sort(oaObj);
		for (int j = 0; j < oaObj.length; j++) {
			p_oCol.set(j, (T) oaObj[j]);
		}
	}

	public static <T> void sort(List<T> p_oCol, Comparator<? super T> p_oComp) {
		@SuppressWarnings("unchecked")
		T[] oaObj = (T[]) p_oCol.toArray();
		Arrays.sort(oaObj, p_oComp);
		for (int j = 0; j < oaObj.length; j++) {
			p_oCol.set(j, oaObj[j]);
		}
	}

	/**
	 * Adds an item to a list if it doesn't already exist, if it does exist, it
	 * will return the original ( instead of replacing like set does ).
	 * 
	 * @param col
	 * @param item
	 * @return
	 */
	public static <T> T softAdd(List<T> col, T item) {
		T actual = get(col, item);
		if (actual == null) {
			actual = item;
			col.add(item);
		}
		return actual;
	}

	public static <T> T getLast(List<T> items) {
		if (items == null || items.size() == 0)
			return null;
		return items.get(items.size() - 1);
	}

	/**
	 * Breaks a given List up into sub-lists of the given size.
	 * 
	 * @param fullList
	 *            - List to be broken up
	 * @param sizeLimit
	 *            - Maximum size of each sub-list.
	 * @return List containing each of the sub-lists.
	 */
//	public static <T> List<List<T>> breakIntoSubLists(List<T> fullList,
//			int sizeLimit) {
//		return Lists.partition(fullList, sizeLimit);
//	}

	/**
	 * Make sure list is not null... Warning empty list returned when the
	 * argument is null, is unmodifiable.
	 */
	public static <T> List<T> unNull(List<T> list) {
		if (list == null)
			return Collections.emptyList();
		return list;
	}

	/**
	 * Warning empty set returned when the argument is null, is unmodifiable.
	 */
	public static <T> Set<T> unNull(Set<T> set) {
		if (set == null)
			return Collections.emptySet();
		return set;
	}

	/**
	 * Get the first item from the collection. returns first item (item at index
	 * 0) or null if collection has no items.
	 */
	public static <T> T getFirst(Collection<T> col) {
		return get(col, 0);
	}

	public static <T> List<T> toList(T... objects) {
		if (objects == null || objects.length == 0)
			return Collections.emptyList();

		List<T> rtn = new ArrayList<T>();
		for (T element : objects) {
			rtn.add(element);
		}
		return rtn;
	}

	/**
	 * Returns the values of a map as a list. This list is modifiable with no
	 * side affects to the Map.
	 * 
	 * @param <T>
	 * @param map
	 * @return
	 */
	public static <T> List<T> getListFromMap(Map<? extends Object, T> map) {
		return new ArrayList<T>(map.values());
	}

	public static <T, K extends Object> void copyMap(Map<K, T> sourceMap,
			Map<K, T> targetMap) {
		targetMap.putAll(sourceMap);
	}

	public static <T> List<T> addIf(List<T> list, T e,
			Predicate<? super T> predicate) {
		if (predicate.test(e))
			list.add(e);
		return list;
	}

	//for PCR-3792 it is used for flattening a tree list.
	public static List<Object> flatten(List<?> treeList) {
		List<Object> tmp = new LinkedList<Object>();
		flattenTail(treeList, tmp);
		return tmp;

	}

	private static void flattenTail(List<?> treeList, List<Object> flatList) {
		treeList.forEach((Object each) -> {
			if (each instanceof List<?>) {
				flattenTail((List<?>) each, flatList);
			} else {
				flatList.add(each);
			}
		});
	}

	// Removed reference to configurable.... use uniqueIndex instead.
	// /**
	// * convert a configurable list to a HashMap with one loop, avoid loops
	// when searching.
	// * @param <T>
	// * @param list
	// * @return
	// */
	// public static <T extends ConfigurableBase> Map<Long, T>
	// getMapFromListKeyByConfigurableId(List<T> list){
	// Map <Long, T> retMap=new HashMap<Long, T> ();
	// for(T t:list){
	// retMap.put( t.getId(), t );
	// }
	//
	// return retMap;
	// }

	// REMOVED REFERENCE TO COMPONENT.... use uniqueIndex instead.
	// /**
	// * convert a Component list to a HashMap with one loop, avoid loops when
	// searching.
	// * @param <T>
	// * @param list
	// * @return
	// */
	// public static <T extends Component> Map<String, T>
	// getMapFromListKeyByComponentName(List<T> list){
	// Map <String, T> retMap=new HashMap<String, T> ();
	// for(T t:list){
	// retMap.put( t.getName(), t );
	// }
	//
	// return retMap;
	// }

	/**
	 * Creates a Map based on a list of values
	 */
//	public static <K, V> Map<K, V> uniqueIndex(Iterable<V> values,
//			Function<? super V, K> keyFunction) {
//		return new HashMap<K, V>(Maps.uniqueIndex(values, keyFunction));
//	}

	/**
	 * When you have a map containing values that are array lists of a specific
	 * type... use this method to optionally create the list if it's not there
	 * and add the item.
	 * 
	 * @param <K>
	 *            key of entry
	 * @param <V>
	 *            value to be put in the list
	 * @param map
	 *            contains key, List<values>
	 * @param key
	 * @param value
	 */
	public static <K, V> List<V> addMappedArrayListEntry(Map<K, List<V>> map,
			K key, V value) {
		List<V> list = map.get(key);
		if (list == null)
			list = new ArrayList<V>();
		list.add(value);
		map.put(key, list);
		return list;
	}

	/**
	 * When you have a map containing values that are array lists of a specific
	 * type... creates an empty list, mapped to the key.
	 * 
	 * @param <K>
	 *            key of entry
	 * @param map
	 *            contains key, List<values>
	 * @param key
	 */
	public static <K, V> List<V> addMappedArrayList(Map<K, List<V>> map, K key) {
		List<V> list = map.get(key);
		if (list == null)
			list = new ArrayList<V>();
		map.put(key, list);
		return list;
	}

	/**
	 * When you have a map containing values that are array lists of a specific
	 * type... use this method to optionally create the list if it's not there
	 * and add the item.
	 * 
	 * @param <K>
	 *            key of entry
	 * @param <V>
	 *            value to be put in the list
	 * @param map
	 *            contains key, List<values>
	 * @param key
	 * @param value
	 */
	public static <K, V> List<V> addMappedUnmodifiableListEntry(
			Map<K, List<V>> map, K key, V value) {
		List<V> list = map.get(key);
		if (list == null)
			list = new ArrayList<V>();
		else
			list = new ArrayList<V>(list);
		list.add(value);

		list = Collections.unmodifiableList(list);
		map.put(key, list);
		return list;
	}

	/**
	 * When you have a map containing values that are set of a specific type...
	 * use this method to optionally create the list if it's not there and add
	 * the item.
	 * 
	 * @param <K>
	 *            key of entry
	 * @param <V>
	 *            value to be put in the list
	 * @param map
	 *            contains key, List<values>
	 * @param key
	 * @param value
	 */
	public static <K, V> Set<V> addMappedSetEntry(Map<K, Set<V>> map, K key,
			V value) {
		Set<V> set = map.get(key);
		if (set == null) {
			set = new HashSet<V>();
			map.put(key, set);
		}
		set.add(value);
		return set;
	}

	/**
	 * When you have a map containing values that are set of a specific type...
	 * use this method to optionally create the list if it's not there and add
	 * the item.
	 * 
	 * @param <K>
	 *            key of entry
	 * @param <V>
	 *            value to be put in the list
	 * @param map
	 *            contains key, List<values>
	 * @param key
	 * @param value
	 */
	public static <T, K, V> Map<K, V> addMappedMapEntry(Map<T, Map<K, V>> map,
			T t, K key, V value) {
		Map<K, V> subMap = map.get(t);
		if (subMap == null) {
			subMap = new HashMap<K, V>();
			map.put(t, subMap);
		}
		subMap.put(key, value);
		return subMap;
	}

	/**
	 * Creates a string by concatenating the string values of each of the
	 * elements. The passed separator will be inserted Between each of the
	 * elements. If no separator is desired, null may be specified.
	 * 
	 * @param list
	 *            list of objects to be concatenated
	 * @param separator
	 *            the string to insert between elements of the list. It will not
	 *            be appended at the end of the list.
	 * @return a stringized version of the list
	 */
	public static String asConcatenatedString(List<?> list, String separator) {
		return new TokenizedStringBuilder(separator == null ? "" : separator)
				.add(list).toString();
	}

//	public static String asConcatenatedString(List<?> list, String separator,
//			Formatter formatter) {
//		return new TokenizedStringBuilder(separator == null ? "" : separator)
//				.addFormatter(formatter).add(list).toString();
//	}

	// public static

	public static void main(String[] args) {
		long[] apps = new long[] { 9l };
		System.out.println(in(9l, apps));
		System.out.println(Arrays.toString(testMerge("asdf")));
		System.out.println(Arrays.toString(testMerge("asdf", null)));
		System.out.println(Arrays.toString(testMerge("asdf", null, "asdff")));

		List<String> oList = new ArrayList<String>();
		oList.add("man");
		oList.add("aan");
		oList.add("fan");
		oList.add("zan");
		oList.add("can");

		List<String> oOldList = oList;
		oList = new ArrayList<String>(oList);
		sort(oList);

		for (Object element : oList) {
			String oStr = (String) element;
			System.out.println(oList.indexOf(oStr) + ":" + oStr + " was "
					+ oOldList.indexOf(oStr));
		}

		oOldList = new ArrayList<String>();
		oOldList.add("man");
		oOldList.add("aan");
		oOldList.add("fan");
		oOldList.add("zan");
		oOldList.add("can");

		for (String str : getLastXItems(2, oOldList)) {
			System.out.println(str + " " + oOldList.indexOf(str));
		}

	}

	public static String[] testMerge(String first, String... others) {
		return merge(String.class, first, others);
	}

	/**
	 * Similiar to get first, but since maps are not ordered, this method will
	 * return the first item from the unordered set of values.
	 * 
	 * @param
	 * @return The first item from the unordered set of values.
	 */
	public static <K, V> V getAny(Map<K, V> map) {
		if (map == null || map.size() == 0)
			return null;

		return getFirst(map.values());
	}

	public static int size(Collection<?> col) {
		return col == null ? 0 : col.size();
	}

	public static <T> List<T> getLastXItems(int lastXItems, List<T> list) {
		ArrayList<T> sizedList = new ArrayList<T>(list);

		if (lastXItems < 0)
			lastXItems = 0;

		sizedList.subList(0, Math.max(sizedList.size() - lastXItems, 0))
				.clear();
		return sizedList;
	}

	public static <T> boolean isLast(T object, List<T> list) {
		if (isEmpty(list))
			return false;
		Object last = getLast(list);
		return last == null ? object == null : last.equals(object);
	}

	public static <T> boolean overlap(Collection<T> collection1,
			Collection<T> collection2) {
		for (T item : collection1)
			if (collection2.contains(item))
				return true;
		return false;
	}

	/**
	 * Determines if the 2 collections are the same. This includes the number of
	 * occurrences of duplicate items. List { a, a, b, c, d } != List { a, b, c,
	 * c, c, d, d } List { a, a, b, c, d } != Set { a, b, c, d } List { a, a, b,
	 * c, d } == List { b, c, d, a, a }
	 * 
	 * @param <T>
	 * @param collection1
	 * @param collection2
	 * @return
	 */
	public static <T> boolean same(Collection<T> collection1,
			Collection<T> collection2) {
		if (collection1.size() != collection2.size())
			return false;
		List<T> items = new LinkedList<T>(collection2);
		for (T item : collection1)
			if (!items.remove(item))
				return false;
		// if( !collection2.contains( item ) )
		// return false;
		return items.isEmpty();
	}

	/**
	 * Determines if the 2 collections have the same items. This IGNORES
	 * duplicates of the same item.. so: List { a, a, b, c, d } = List { a, b,
	 * c, c, c, d, d } List { a, a, b, c, d } = Set { a, b, c, d }
	 * 
	 * @param <T>
	 * @param collection1
	 * @param collection2
	 * @return
	 */
	public static <T> boolean sameContents(Collection<T> collection1,
			Collection<T> collection2) {
		List<T> items = new LinkedList<T>(collection2);
		for (T item : collection1)
			if (!collection2.contains(item))
				return false;

		for (T item : collection2)
			if (!collection1.contains(item))
				return false;

		return !items.isEmpty();
	}

	/**
	 * Merges a number of collections of the same type... into one.
	 * 
	 * @param <T>
	 * @param collections
	 * @return
	 */
//	public static <T> List<T> merge(Collection<T>... collections) {
//		List<T> allcs = Lists.newArrayList();
//
//		for (Collection<T> c : collections)
//			allcs.addAll(c);
//
//		return allcs;
//	}

	/**
	 * Provide this method so that someone wanting to find a way to get the
	 * complement of a subset (or in this case sub-collection) can easily find
	 * method for doing so in this class.
	 * 
	 * @param first
	 * @param second
	 * @return
	 */
	public static <T> DifferenceResult<T> getComplement(
			final Collection<T> first, final Collection<T> second) {
		return getDifference(first, second);
	}

	public static <T> DifferenceResult<T> getDifference(
			final Collection<T> first, final Collection<T> second) {
		Set<T> f = new HashSet<T>();
		if (isEmpty(first)) {
			f = Collections.emptySet();
		} else {
			f = new HashSet<T>(first);
		}

		Set<T> s = new HashSet<T>();
		if (isEmpty(second)) {
			s = Collections.emptySet();
		} else {
			s = new HashSet<T>(second);
		}

		Set<T> onlyInFirst = new HashSet<T>(f);
		onlyInFirst.removeAll(s);

		Set<T> onlyInSecond = new HashSet<T>(s);
		onlyInSecond.removeAll(f);

		Set<T> inBoth = new HashSet<T>(f);
		inBoth.retainAll(s);

		return new DifferenceResult<T>(Collections.unmodifiableSet(inBoth),
				Collections.unmodifiableSet(onlyInFirst),
				Collections.unmodifiableSet(onlyInSecond));
	}

	public static class DifferenceResult<T> {

		private final Set<T> inBoth;
		private final Set<T> onlyInFirst;
		private final Set<T> onlyInSecond;

		public DifferenceResult(Set<T> inBoth, Set<T> inFirst, Set<T> inSecond) {
			this.inBoth = inBoth;
			this.onlyInFirst = inFirst;
			this.onlyInSecond = inSecond;
		}

		public Set<T> getInBoth() {
			return inBoth;
		}

		public Set<T> getOnlyInFirst() {
			return onlyInFirst;
		}

		public Set<T> getOnlyInSecond() {
			return onlyInSecond;
		}

	}

	/**
	 * Determines if item is in the candidates. Supports nulls.
	 * 
	 * @return true if item is equal to one of the candidates.
	 */
	public static <T> boolean in(T item, T... candidates) {
		return Arrays.asList(candidates).contains(item);
	}

	/**
	 * Determines if item is in the candidates. Supports nulls.
	 * 
	 * @return true if item is equal to one of the candidates.
	 */
	public static boolean in(long item, long[] candidates) {
		if (candidates == null)
			return false;

		for (long l : candidates)
			if (item == l)
				return true;
		return false;
	}

	/**
	 * Determines if item is in the candidates. Supports nulls.
	 * 
	 * @return true if item is equal to one of the candidates.
	 */
	public static boolean in(int item, int[] candidates) {
		if (candidates == null)
			return false;

		for (int l : candidates)
			if (item == l)
				return true;
		return false;
	}

	/**
	 * Returns the value mapped by key, or if no mapping exists, puts to map and
	 * returns the value supplied by valueSupplier.
	 */
//	public static <K, V> V getOrPut(Map<K, V> map, K key,
//			Supplier<V> valueSupplier) {
//		if (map.containsKey(key)) {
//			return map.get(key);
//		}
//		V val = valueSupplier.get();
//		map.put(key, val);
//		return val;
//	}
//
//	public static <K, V> boolean equal(Map<? extends K, ? extends V> left,
//			Map<? extends K, ? extends V> right) {
//		return Maps.difference(left, right).areEqual();
//	}

	public static <T> List<T> newArrayList(T... objs) {
		List<T> results = new ArrayList<T>();
		results.addAll(Arrays.asList(objs));
		return results;
	}

	public static <E> ArrayList<E> newArrayList() {
		return new ArrayList<E>();
	}

	/**
	 * Creates a new list that has all of the original entries, but no
	 * duplicates. Order stays exactly the same as collections iterator returns
	 * them, except that duplicates ( after first occurrence ) are removed.
	 * 
	 * @param <T>
	 * @param list
	 * @return
	 */
	public static <T> List<T> distinct(Collection<T> list) {
		return new ArrayList<T>(new LinkedHashSet<T>(list));
	}

	/**
	 * Removes an item from a list and returns the collections instance of this
	 * item, or null if not found or the collection is empty/null.
	 * 
	 * @param <T>
	 * @param list
	 * @param remove
	 * @return
	 */
	public static <T> T remove(List<T> list, T remove) {
		if (isEmpty(list))
			return null;
		if (!list.contains(remove))
			return null;

		T item = get(list, remove);
		list.remove(item);
		return item;
	}

	/*****
	 * Replace an item with another item for the list
	 * 
	 * @param list
	 * @param from
	 * @param to
	 * @return
	 */
	public static <T> T replace(List<T> list, T from, T to) {
		if (isEmpty(list))
			return null;
		if (!list.contains(from))
			return null;

		int index = list.indexOf(from);
		list.set(index, to);

		return to;
	}

	/**
	 * Removes items from a list and returns that same list. This will modify
	 * the list passed in!
	 * 
	 * @param <T>
	 * @param list
	 * @param remove
	 * @return
	 */
	public static <T> List<T> removeItems(List<T> list, T... remove) {
		if (isEmpty(list))
			return null;
		list.removeAll(Arrays.asList(remove));

		return list;
	}

	public static <T> HashSet<T> newHashSet(T... objs) {
		HashSet<T> results = new HashSet<T>();
		results.addAll(Arrays.asList(objs));
		return results;
	}

	public static <E> HashSet<E> newHashSet() {
		return new HashSet<E>();
	}

	public static <K, V> Map<K, V> newHashMap() {
		return new HashMap<K, V>();
	}

	/**
	 * Adds thingsToAdd to thingsToBeAddedTo.
	 * 
	 * @param thingsToBeAddedTo
	 *            , which must be non-null.
	 * @throws IllegalArgumentException
	 *             if thingsToBeAddedTo is null.
	 * 
	 */
	public static <E> void addAll(
			final Collection<? super E> thingsToBeAddedTo,
			final Collection<? extends E> thingsToAdd) {
		if (thingsToBeAddedTo == null) {
			throw new IllegalArgumentException("There's no thingsToBeAddedTo!");
		}

		if (!isEmpty(thingsToAdd)) {
			thingsToBeAddedTo.addAll(thingsToAdd);
		}
	}

	/**
	 * Returns the first item of iterable for which predicate returns true, or
	 * null.
	 */
//	public static <T> T findFirst(Iterable<T> iterable,
//			com.google.common.base.Predicate<? super T> predicate) {
//		if (iterable == null) {
//			return null;
//		}
//		for (T t : iterable) {
//			if (predicate.apply(t)) {
//				return t;
//			}
//		}
//		return null;
//	}
//
//	public static <T> List<T> filter(Iterable<T> iterable,
//			com.google.common.base.Predicate<? super T> predicate) {
//		if (iterable == null || Iterables.isEmpty(iterable)) {
//			return Collections.emptyList();
//		}
//		if (predicate == null) {
//			if (iterable instanceof List)
//				return (List<T>) iterable;
//			return Lists.newArrayList(iterable);
//		}
//		List<T> result = Lists.newArrayList();
//		for (T t : iterable) {
//			if (predicate.apply(t)) {
//				result.add(t);
//			}
//		}
//		return result;
//	}

	public static <T> T findListFirst(Collection<T> col,
			Predicate<? super T> predicate) {
		if (col == null) {
			return null;
		}
		return col.stream().filter(predicate).findFirst().get();
	}

//	public static <T> List<T> filterList(Collection<T> col,
//			Predicate<? super T> predicate) {
//		if (col == null || Iterables.isEmpty(col)) {
//			return newArrayList();
//		}
//		return col.stream().filter(predicate).collect(Collectors.toList());
//	}
//
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	public static <T> Iterable<T> uncheckedIterable(Enumeration type) {
//		return new IterableEnumeration<T>(type);
//	}
//
//	public static <T> Iterable<T> iterable(Enumeration<T> type) {
//		return new IterableEnumeration<T>(type);
//	}
//
//	public static <T> Iterator<T> iterator(Enumeration<T> type) {
//		return new IterableEnumeration<T>(type);
//	}
//
//	public static <K, V, K2> Map<K2, V> transform(Map<K, V> map,
//			Function<? super K, K2> keyFunction, boolean prohibitOverwrites) {
//		Map<K2, V> tranformed = Maps.newLinkedHashMap();
//		if (map == null || map.isEmpty()) {
//			return tranformed;
//		}
//		if (prohibitOverwrites) {
//			tranformed = new IrreplaceableKeyMap<K2, V>(tranformed);
//		}
//		for (Map.Entry<K, V> e : map.entrySet()) {
//			K2 newKey = keyFunction.apply(e.getKey());
//			if (newKey == null) {
//				continue;
//			}
//			tranformed.put(newKey, e.getValue());
//		}
//		return tranformed;
//	}

	/**
	 * chops a list into non-view sublists of length
	 */
	public static <T> List<List<T>> chopLargeList(List<T> list, final int length) {

		List<List<T>> parts = new ArrayList<List<T>>();

		if (!isEmpty(list)) {
			final int total = list.size();
			if (length < 1 || total <= length) {
				parts.add(list);
			} else {
				for (int i = 0; i < total; i += length) {
					parts.add(new ArrayList<T>(list.subList(i,
							Math.min(total, i + length))));
				}
			}
		}
		return parts;
	}

	/**
	 * Creates a new sorted list from the original.
	 * 
	 * @param p_oCol
	 * @param comparator
	 * @return
	 */
	public static <T> List<T> copySort(List<T> p_oCol,
			Comparator<? super T> comparator) {
		List<T> retList = newArrayList();
		retList.addAll(p_oCol);
		Collections.sort(retList, comparator);
		return retList;
	}

	private static final int[] emptyIntArray = new int[0];
	private static final Long[] emptyLoogArray = new Long[0];

	/**
	 * Create an int array from a list of Integers
	 * 
	 * @param ints
	 * @return
	 */
	public static int[] toArray(List<Integer> ints) {
		if (ColUtil.isEmpty(ints))
			return emptyIntArray;
		int[] array = new int[ints.size()];
		for (int i = 0; i < ints.size(); i++) {
			array[i] = NumUtil.iVal(ints.get(i));
		}
		return array;
	}

	public static Long[] toLongArray(List<Long> longs) {
		if (ColUtil.isEmpty(longs))
			return emptyLoogArray;
		Long[] array = new Long[longs.size()];
		for (int i = 0; i < longs.size(); i++) {
			array[i] = NumUtil.lVal(longs.get(i));
		}
		return array;
	}

	/**
	 * Filter a collection by using our standard object filter
	 * {@link I_ObjectFilter}, and return a {@link List} of the accepted items.
	 */
//	public static <T> List<T> filter(Iterable<T> c, I_ObjectFilter<T> f) {
//		if (isEmpty(c))
//			return Collections.emptyList();
//		return filter(c, ColUtil.<T> newArrayList(), f);
//	}
//
//	/**
//	 * Filter a collection by using our standard object filter
//	 * {@link I_ObjectFilter}, and return a {@link Set} of the accepted items.
//	 */
//	public static <T> Set<T> filterSet(Collection<T> c, I_ObjectFilter<T> f) {
//		if (isEmpty(c))
//			return Collections.emptySet();
//		return filter(c, ColUtil.<T> newHashSet(), f);
//	}
//
//	private static <D extends Collection<T>, T> D filter(Iterable<T> source,
//			D destination, I_ObjectFilter<T> f) {
//
//		for (T t : source)
//			if (f == null || f.accept(t))
//				destination.add(t);
//
//		return destination;
//	}
//
//	/**
//	 * Returns a list containing each item converted by the function, but does
//	 * not include those for which the function returns null (the returned list
//	 * will not contain null).
//	 */
//	public static <T, C> List<C> collect(Iterable<T> source, Function<T, C> f) {
//		if (f == null)
//			throw new IllegalArgumentException("A function is required.");
//
//		if (isEmpty(source))
//			return Collections.emptyList();
//
//		List<C> res = Lists.newArrayList();
//
//		for (T t : source) {
//			C c = f.apply(t);
//			if (c != null)
//				res.add(c);
//		}
//
//		return res.isEmpty() ? Collections.<C> emptyList() : res;
//	}
//
//	/**
//	 * Returns a list containing each item converted by the function, but does
//	 * not include those for which the function returns null (the returned list
//	 * will not contain null).
//	 */
//	public static <T, C> List<C> collectAll(Iterable<T> source,
//			Function<T, Collection<C>> f) {
//		if (f == null)
//			throw new IllegalArgumentException("A function is required.");
//
//		if (isEmpty(source))
//			return Collections.emptyList();
//
//		List<C> res = Lists.newArrayList();
//
//		for (T t : source) {
//			Collection<C> c = f.apply(t);
//			if (c != null)
//				res.addAll(c);
//		}
//
//		return res.isEmpty() ? Collections.<C> emptyList() : res;
//	}
//
//	/**
//	 * Extracts a list of IDs from a list of Identifiable Objects.
//	 * 
//	 * @parm objs a list of Identifiable objects
//	 * @return a list of Ids extracted from given list of Identifiable objects
//	 */
//	public static List<Long> extractIdList(List<? extends Identifiable<?>> objs) {
//		List<Long> ids = newArrayList();
//		for (int i = 0; i < objs.size(); i++) {
//			ids.add((Long) objs.get(i).getId());
//		}
//		return ids;
//	}

	@SuppressWarnings("unchecked")
	public static <T> T[] merge(Class<T> cls, T first, T[] others) {
		List<T> list = new ArrayList<T>();
		list.add(first);
		if (others != null)
			for (T o : others)
				list.add(o);
		return list.toArray((T[]) Array.newInstance(cls, list.size()));
	}

	public static <T> boolean containsAll(List<T> list, T... items) {
		if (isEmpty(list))
			return false;
		if (items == null || items.length < list.size())
			return false;

		// TODO This is not the most efficient search...
		for (T item : items)
			if (!list.contains(item))
				return false;

		return true;
	}

	public static String buildQLInBigListNumberCondition(String fieldName,
			List<? extends Number> bigNumberList) {

		StringBuilder qlCondition = new StringBuilder();

		if (bigNumberList.size() > 500) {
			int bigLen = bigNumberList.size();
			ArrayList<List<Number>> smallLists = new ArrayList<List<Number>>();
			int index = 0;
			while (index < bigLen) {
				List<Number> smallList = new ArrayList<Number>();
				int smallLen;
				if (bigLen - index >= 500) {
					smallLen = 500;
				} else {
					smallLen = bigLen - index;
				}
				for (int i = 0; i < smallLen; i++) {
					smallList.add(bigNumberList.get(index++));
				}
				smallLists.add(smallList);
			}

			qlCondition.append(" ( ");

			for (int i = 0; i < smallLists.size(); i++) {
				List<Number> smallList = smallLists.get(i);
				String cond = fieldName + " in ( "
						+ asConcatenatedString(smallList, ",") + " ) ";
				if (i != 0)
					qlCondition.append(" or ");
				qlCondition.append(cond);
			}

			qlCondition.append(" ) ");
		} else {
			qlCondition.append(fieldName + " in ( ")
					.append(asConcatenatedString(bigNumberList, ","))
					.append(")");
		}

		return qlCondition.toString();
	}

	public static <T> List<List<T>> splitListToSubLists(List<T> parentList,
			int subListSize) {
		List<List<T>> subLists = new ArrayList<List<T>>();
		if (subListSize > parentList.size()) {
			subLists.add(parentList);
		} else {
			int remainingElements = parentList.size();
			int startIndex = 0;
			int endIndex = subListSize;
			do {
				List<T> subList = parentList.subList(startIndex, endIndex);
				subLists.add(subList);
				startIndex = endIndex;
				if (remainingElements - subListSize >= subListSize) {
					endIndex = startIndex + subListSize;
				} else {
					endIndex = startIndex + remainingElements - subList.size();
				}
				remainingElements -= subList.size();
			} while (remainingElements > 0);

		}
		return subLists;
	}

	/**
	 * Returns true if the list is a non-empty list with single element being
	 * just the one element
	 * 
	 * @param list
	 * @param object
	 * @return
	 */
	public static <T> boolean only(List<T> list, T object) {
		return !isEmpty(list) && list.size() == 1 && list.get(0) == object;
	}

	/**
	 * Returns the intersection of collection 1 and collection 2 without
	 * modifying collection 1 and collection 2
	 * 
	 * @param collection1
	 * @param collection2
	 * @return intersection set of given collections
	 */
	public static <T> Set<T> intersect(Collection<T> collection1,
			Collection<T> collection2) {
		if (isEmpty(collection1) || isEmpty(collection2)) {
			return newHashSet();
		}
		Set<T> source1 = new HashSet<T>(collection1);
		source1.retainAll(collection2);
		return source1;
	}

}
