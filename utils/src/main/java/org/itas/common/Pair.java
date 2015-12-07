package org.itas.common;


public final class Pair<Key, Value> {

	private Key key;

	private Value value;

	public Pair() {

	}

	public Pair(Key key, Value value) {
		this.key = key;
		this.value = value;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public Value getValue() {
		return value;
	}

	public void setValue(Value value) {
		this.value = value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o instanceof Pair) {
			Pair<?, ?> pair = (Pair<?, ?>) o;
			if (key != null ? !key.equals(pair.key) : pair.key != null)
				return false;
			if (value != null ? !value.equals(pair.value) : pair.value != null)
				return false;
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return key.hashCode() * 13 + (value == null ? 0 : value.hashCode());
	}

	@Override
	public String toString() {
		return String.format("%s,%s", key, value);
	}
}
