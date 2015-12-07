/*
 * Copyright 2015 xcnet
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.itas.common.xml;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * xml元素<p>
 * 
 * @author liuzhen<liuxing521a@163.com>
 * @calendar 2015年5月29日
 */
public final class Element {

	/** xml element name*/
	private final String name;
	/** xml element content*/
	private String text;

	private Element parent;
	private List<Element> children;
	private Map<String, String> attributes;

	public Element (String name, Element parent) {
		this.name = name;
		this.parent = parent;
	}

	public String getName () {
		return name;
	}

	public Map<String, String> getAttributes () {
		return attributes;
	}

	public String getAttribute (String name) {
		if (attributes == null) 
			throw new RuntimeException("Element " + name + " doesn't have attribute: " + name);
		
		String value = attributes.get(name);
		if (value == null) 
			throw new RuntimeException("Element " + name + " doesn't have attribute: " + name);
		
		return value;
	}

	public String getAttribute (String name, String defaultValue) {
		if (attributes == null) 
			return defaultValue;
		
		String value = attributes.get(name);
		if (value == null) 
			return defaultValue;
		
		return value;
	}

	public void setAttribute (String name, String value) {
		if (attributes == null) 
			attributes = new LinkedHashMap<String, String>(8);
		
		attributes.put(name, value);
	}

	public int getChildCount () {
		if (children == null) 
			return 0;
		
		return children.size();
	}

	/** @throws GdxRuntimeException if the element has no children. */
	public Element getChild (int i) {
		if (children == null) 
			throw new RuntimeException("Element has no children: " + name);
		
		return children.get(i);
	}

	public void addChild (Element element) {
		if (children == null) 
			children = new ArrayList<>();
		
		children.add(element);
	}

	public String getText () {
		return text;
	}

	public void setText (String text) {
		this.text = text;
	}

	public void removeChild (int index) {
		if (children != null) children.remove(index);
	}

	public void removeChild (Element child) {
		if (children != null) 
			children.remove(child);
	}

	public void remove () {
		parent.removeChild(this);
	}

	public Element getParent () {
		return parent;
	}

	public String toString () {
		return toString("");
	}

	public String toString (String indent) {
		StringBuilder buffer = new StringBuilder(128);
		buffer.append(indent);
		buffer.append('<');
		buffer.append(name);
		if (attributes != null) {
			for (Map.Entry<String, String> entry : attributes.entrySet()) {
				buffer.append(' ');
				buffer.append(entry.getKey());
				buffer.append("=\"");
				buffer.append(entry.getValue());
				buffer.append('\"');
			}
		}
		if (children == null && (text == null || text.length() == 0))
			buffer.append("/>");
		else {
			buffer.append(">\n");
			String childIndent = indent + '\t';
			if (text != null && text.length() > 0) {
				buffer.append(childIndent);
				buffer.append(text);
				buffer.append('\n');
			}
			if (children != null) {
				for (Element child : children) {
					buffer.append(child.toString(childIndent));
					buffer.append('\n');
				}
			}
			buffer.append(indent);
			buffer.append("</");
			buffer.append(name);
			buffer.append('>');
		}
		return buffer.toString();
	}

	/** @param name the name of the child {@link Element}
	 * @return the first child having the given name or null, does not recurse */
	public Element getChildByName (String name) {
		if (children == null) 
			return null;
		
		for (Element element : children) {
			if (element.name.equals(name)) 
				return element;
		}
		
		return null;
	}

	/** @param name the name of the child {@link Element}
	 * @return the first child having the given name or null, recurses */
	public Element getChildByNameRecursive (String name) {
		if (children == null) 
			return null;
		
		for (Element element : children) {
			if (element.name.equals(name)) 
				return element;
			
			Element found = element.getChildByNameRecursive(name);
			if (found != null) 
				return found;
		}
		
		return null;
	}

	/** @param name the name of the children
	 * @return the children with the given name or an empty {@link Array} */
	public List<Element> getChildrenByName (String name) {
		ArrayList<Element> result = new ArrayList<Element>(children.size());
		if (children == null) 
			return result;
		
		for (Element child : children) {
			if (child.name.equals(name)) 
				result.add(child);
		}
		
		return result;
	}

	/** @param name the name of the children
	 * @return the children with the given name or an empty {@link Array} */
	public List<Element> getChildrenByNameRecursively (String name) {
		ArrayList<Element> result = new ArrayList<Element>();
		getChildrenByNameRecursively(name, result);
		return result;
	}

	private void getChildrenByNameRecursively (String name, List<Element> result) {
		if (children == null) return;
		for (int i = 0; i < children.size(); i++) {
			Element child = children.get(i);
			if (child.name.equals(name)) result.add(child);
			child.getChildrenByNameRecursively(name, result);
		}
	}

	/** @throws GdxRuntimeException if the attribute was not found. */
	public float getFloatAttribute (String name) {
		return Float.parseFloat(getAttribute(name));
	}

	public float getFloatAttribute (String name, float defaultValue) {
		String value = getAttribute(name, null);
		if (value == null) return defaultValue;
		return Float.parseFloat(value);
	}

	/** @throws GdxRuntimeException if the attribute was not found. */
	public int getIntAttribute (String name) {
		return Integer.parseInt(getAttribute(name));
	}

	public int getIntAttribute (String name, int defaultValue) {
		String value = getAttribute(name, null);
		if (value == null) return defaultValue;
		return Integer.parseInt(value);
	}

	/** @throws GdxRuntimeException if the attribute was not found. */
	public boolean getBooleanAttribute (String name) {
		return Boolean.parseBoolean(getAttribute(name));
	}

	public boolean getBooleanAttribute (String name, boolean defaultValue) {
		String value = getAttribute(name, null);
		if (value == null) return defaultValue;
		return Boolean.parseBoolean(value);
	}

	/** Returns the attribute value with the specified name, or if no attribute is found, the text of a child with the name.
	 * @throws GdxRuntimeException if no attribute or child was not found. */
	public String get (String name) {
		String value = get(name, null);
		if (value == null) throw new RuntimeException("Element " + this.name + " doesn't have attribute or child: " + name);
		return value;
	}

	/** Returns the attribute value with the specified name, or if no attribute is found, the text of a child with the name.
	 * @throws GdxRuntimeException if no attribute or child was not found. */
	public String get (String name, String defaultValue) {
		if (attributes != null) {
			String value = attributes.get(name);
			if (value != null) return value;
		}
		Element child = getChildByName(name);
		if (child == null) return defaultValue;
		String value = child.getText();
		if (value == null) return defaultValue;
		return value;
	}

	/** Returns the attribute value with the specified name, or if no attribute is found, the text of a child with the name.
	 * @throws GdxRuntimeException if no attribute or child was not found. */
	public int getInt (String name) {
		String value = get(name, null);
		if (value == null) throw new RuntimeException("Element " + this.name + " doesn't have attribute or child: " + name);
		return Integer.parseInt(value);
	}

	/** Returns the attribute value with the specified name, or if no attribute is found, the text of a child with the name.
	 * @throws GdxRuntimeException if no attribute or child was not found. */
	public int getInt (String name, int defaultValue) {
		String value = get(name, null);
		if (value == null) return defaultValue;
		return Integer.parseInt(value);
	}

	/** Returns the attribute value with the specified name, or if no attribute is found, the text of a child with the name.
	 * @throws GdxRuntimeException if no attribute or child was not found. */
	public float getFloat (String name) {
		String value = get(name, null);
		if (value == null) throw new RuntimeException("Element " + this.name + " doesn't have attribute or child: " + name);
		return Float.parseFloat(value);
	}

	/** Returns the attribute value with the specified name, or if no attribute is found, the text of a child with the name.
	 * @throws GdxRuntimeException if no attribute or child was not found. */
	public float getFloat (String name, float defaultValue) {
		String value = get(name, null);
		if (value == null) return defaultValue;
		return Float.parseFloat(value);
	}

	/** Returns the attribute value with the specified name, or if no attribute is found, the text of a child with the name.
	 * @throws GdxRuntimeException if no attribute or child was not found. */
	public boolean getBoolean (String name) {
		String value = get(name, null);
		if (value == null) throw new RuntimeException("Element " + this.name + " doesn't have attribute or child: " + name);
		return Boolean.parseBoolean(value);
	}

	/** Returns the attribute value with the specified name, or if no attribute is found, the text of a child with the name.
	 * @throws GdxRuntimeException if no attribute or child was not found. */
	public boolean getBoolean (String name, boolean defaultValue) {
		String value = get(name, null);
		if (value == null) return defaultValue;
		return Boolean.parseBoolean(value);
	}

}
