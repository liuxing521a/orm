package org.itas.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.itas.common.Utils.Objects;

public class GenericInfo {

	/** 泛型名*/
	private StringBuffer name;
	
	/** 所属泛型*/
	private GenericInfo parent;
	
	/** 子泛型*/
	private List<GenericInfo> childs;
	
	public GenericInfo() {
		this.name = new StringBuffer();
		this.childs = new ArrayList<>(2);
	}

	public String getName() {
		return name.toString();
	}

	public void addName(String str) {
		this.name.append(str);
	}

	public void addName(char ch) {
		this.name.append(ch);
	}

	public GenericInfo getParent() {
		return parent;
	}

	public void setParent(GenericInfo parent) {
		this.parent = parent;
	}

	public List<GenericInfo> getChilds() {
		if (Objects.isNull(childs)) {
			return Collections.emptyList();
		}
		
		return childs;
	}
	
	public void addChild(GenericInfo info, boolean isByteCode) {
		if (Objects.isNull(childs)) {
			childs = new ArrayList<>();
		}
		
		if (isByteCode) {
			info.deleteChildFirst();
		}
		this.childs.add(info);
	}
	
	void deleteChildFirst() {
		this.name.deleteCharAt(0);
	}
	
	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append(name);
		
		if (Objects.nonEmpty(childs)) {
			buf.append("<");
			for (GenericInfo info :childs ) {
				buf.append(info).append(',');
			}
			buf.deleteCharAt(buf.length() - 1);
			buf.append(">");
		}
		return buf.toString();
//		return name.toString();
	}
	
}
