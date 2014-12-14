package org.wpattern.learning.object.ga.utils;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.wpattern.learning.object.beans.BaseBean;

import com.google.common.collect.MinMaxPriorityQueue;

public class SortedList<T> extends BaseBean implements ISortedList<T> {

	private static final long serialVersionUID = 201406081634L;

	private MinMaxPriorityQueue<T> colletion;

	private Comparator<T> comparator;

	public SortedList(Comparator<T> comparator) {
		this.comparator = comparator;
		this.colletion = MinMaxPriorityQueue.orderedBy(this.comparator).create();
	}

	@Override
	public T peekFirst() {
		return this.colletion.peekFirst();
	}

	@Override
	public T peekLast() {
		return this.colletion.peekLast();
	}

	@Override
	public T pollLast() {
		return this.colletion.pollLast();
	}

	@Override
	public T pollFirst() {
		return this.colletion.pollFirst();
	}

	@Override
	public int size() {
		return this.colletion.size();
	}

	@Override
	public boolean isEmpty() {
		return this.colletion.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return this.colletion.contains(o);
	}

	@Override
	public Iterator<T> iterator() {
		return this.colletion.iterator();
	}

	@Override
	public Object[] toArray() {
		return this.colletion.toArray();
	}

	@SuppressWarnings("hiding")
	@Override
	public <T> T[] toArray(T[] a) {
		return this.colletion.toArray(a);
	}

	@Override
	public boolean add(T e) {
		return this.colletion.add(e);
	}

	@Override
	public boolean remove(Object o) {
		return this.colletion.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return this.colletion.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		return this.colletion.addAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return this.colletion.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return this.colletion.retainAll(c);
	}

	@Override
	public void clear() {
		this.colletion.clear();
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		// TODO: augusto.branquinho Develop a new method using index from "queue" object inside MinMaxPriorityQueue.
		throw new UnsupportedOperationException(ErrorMessages.SORTED_LIST_INDEX_ERROR);
	}

	@Override
	public T get(int index) {
		// TODO: augusto.branquinho Develop a new method using index from "queue" object inside MinMaxPriorityQueue.
		Iterator<T> iterator = this.colletion.iterator();
		int count = 0;

		while (iterator.hasNext()) {
			T t = iterator.next();

			if (count++ == index) {
				return t;
			}
		}

		throw new UnsupportedOperationException(ErrorMessages.SORTED_LIST_INDEX_ERROR);
	}

	@Override
	public T set(int index, T element) {
		// TODO: augusto.branquinho Develop a new method using index from "queue" object inside MinMaxPriorityQueue.
		throw new UnsupportedOperationException(ErrorMessages.SORTED_LIST_INDEX_ERROR);
	}

	@Override
	public void add(int index, T element) {
		// TODO: augusto.branquinho Develop a new method using index from "queue" object inside MinMaxPriorityQueue.
		throw new UnsupportedOperationException(ErrorMessages.SORTED_LIST_INDEX_ERROR);
	}

	@Override
	public T remove(int index) {
		// TODO: augusto.branquinho Develop a new method using index from "queue" object inside MinMaxPriorityQueue.
		throw new UnsupportedOperationException(ErrorMessages.SORTED_LIST_INDEX_ERROR);
	}

	@Override
	public int indexOf(Object o) {
		return -1;
	}

	@Override
	public int lastIndexOf(Object o) {
		// TODO: augusto.branquinho Develop a new method using index from "queue" object inside MinMaxPriorityQueue.
		throw new UnsupportedOperationException(ErrorMessages.SORTED_LIST_INDEX_ERROR);
	}

	@Override
	public ListIterator<T> listIterator() {
		// TODO: augusto.branquinho Develop a new method using index from "queue" object inside MinMaxPriorityQueue.
		throw new UnsupportedOperationException(ErrorMessages.SORTED_LIST_INDEX_ERROR);
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		// TODO: augusto.branquinho Develop a new method using index from "queue" object inside MinMaxPriorityQueue.
		throw new UnsupportedOperationException(ErrorMessages.SORTED_LIST_INDEX_ERROR);
	}

	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		// TODO: augusto.branquinho Develop a new method using index from "queue" object inside MinMaxPriorityQueue.
		throw new UnsupportedOperationException(ErrorMessages.SORTED_LIST_INDEX_ERROR);
	}

}
