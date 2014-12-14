package org.wpattern.learning.object.ga.utils;

import java.util.List;

public interface ISortedList<T> extends List<T> {

	public T peekFirst();

	public T peekLast();

	public T pollFirst();

	public T pollLast();

}
