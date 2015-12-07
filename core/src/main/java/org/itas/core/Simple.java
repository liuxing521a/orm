package org.itas.core;


/**
 * <p>简单对象</p>
 * 
 * @author liuzhen<liuxing521a@163.com>
 * @date 2014年3月24日
 */
public final class Simple<T extends GameBase> {

	private final String Id;
	
	Simple(String Id) {
		this.Id = Id;
	}

	public String getId() {
		return Id;
	}

	public T enty() {
		return Pool.get(Id);
	}

	@Override
	public boolean equals(Object data) {
		if (data == this) {
			return true;
		}

		if (data instanceof Simple) {
			return Id.equals(((Simple<?>) data).Id);
		}

		return false;
	}

	@Override
	public int hashCode() {
		return 31 + Id.hashCode();
	}

	@Override
	public String toString() {
		return String.format("[clazz=%s]", Id);
	}

}
