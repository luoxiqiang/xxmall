package per.xxmall.common.service;

public interface Function<T, E> {

	public T callback(E e);
}
