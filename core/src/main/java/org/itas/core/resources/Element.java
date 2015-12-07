package org.itas.core.resources;

import java.util.Collections;
import java.util.List;

import org.itas.core.Builder;

import com.google.common.collect.Lists;

public final class Element {

	private final String name;
	
	private final String content;
	
	private final Attribute attribute;
	
	private final List<Element> elements;
	
	private Element(String name, String content, 
		Attribute attribute, List<Element> elements) {
		this.name = name;
		this.content = content;
		this.attribute = attribute;
		this.elements = Collections.unmodifiableList(elements);
	}

	public String getName() {
		return name;
	}

	public String getContent() {
		return content;
	}

	public Attribute getAttribute() {
		return attribute;
	}
	
	public int getChildCount() {
		return (elements == null) ? 0 : elements.size();
	}

	public List<Element> getElements() {
		return elements;
	}
	
	public Element getElement(String name) {
		for (Element el : elements) {
			if (name == el.name || name.equals(el.name)) {
				return el;
			}
		}
		
		throw new NullPointerException("["  + name + "] element not exists");
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append("<").append(name);
		if (attribute != null) {
			buffer.append(attribute.toString());
		}
		buffer.append(">");
		
		if (elements != null) {
			for (Element el : elements) {
				buffer.append("\n\t").append(el);
			}
		}
		
		if (content != null) {
			buffer.append(content);
		}
		
		buffer.append("</").append(name).append(">");
		
		return buffer.toString();
	}
	
	public static ElementBuilder newBuilder() {
		return new ElementBuilder();
	}
	
	public static class ElementBuilder implements Builder {

		private String name;
		
		private String content;
		
		private Attribute.AttributeBuilder attributes;
		
		private List<Element> elements;
		
		private ElementBuilder prev;
		
		private ElementBuilder() {
		}
		
		public String getName() {
			return name;
		}
		
		public ElementBuilder setName(String name) {
			this.name = name;
			return this;
		}

		public ElementBuilder getPrev() {
			return prev;
		}

		public ElementBuilder setPrev(ElementBuilder prev) {
			this.prev = prev;
			return this;
		}

		public ElementBuilder setContent(String content) {
			this.content = content;
			return this;
		}

		public ElementBuilder addAttribute(String name, String value) {
			if (attributes == null) {
				attributes = Attribute.newBuilder();
			}
			
			this.attributes.addAttribute(name, value);
			return this;
		}
		
		public ElementBuilder addElement(Element element) {
			if (elements == null) {
				elements = Lists.newArrayList();
			}
			
			elements.add(element);
			return this;
		}

		@Override
		public Element builder() {
			final Attribute attr = 
					(attributes == null) ? null : attributes.builder(); 
			final List<Element> els = 
					(elements == null) ? Collections.emptyList() : elements;
			return new Element(name, content, attr, els);
		}
		
	}
	
}
