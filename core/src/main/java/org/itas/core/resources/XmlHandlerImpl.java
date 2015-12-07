package org.itas.core.resources;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.SAXParserFactory;

import org.itas.core.resources.Element.ElementBuilder;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

class XmlHandlerImpl extends DefaultHandler {
	
  /** 正则表达式去除换行符等特殊符号*/
  private static final Pattern pattern = Pattern.compile("\\s*|\t|\r|\n");
  
  private ElementBuilder current;
  private LinkedList<ElementBuilder> steps;

  
  XmlHandlerImpl() {
  	steps = new LinkedList<>();
  }
  
  public Element parse(Path file) throws Exception {
  	final SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.newSAXParser().parse(Files.newInputStream(file), this);
		
		return steps.peek().builder();
  }
  
  
  @Override
  public void startDocument() throws SAXException {
  	
  }
	
  @Override
  public void endDocument() throws SAXException  {
  	
  }

  @Override
  public void startElement(String uri, 
		String localName, String qName, Attributes attributes) throws SAXException {
  	
  	if (current != null) {
  		steps.add(current);
  	}
  	
  	current = Element.newBuilder().setName(qName);
  	if (!steps.isEmpty()) {
  		current.setPrev(steps.peekLast());
  	}
  	
	  for (int i = 0; i < attributes.getLength(); i++) {
	  	current.addAttribute(attributes.getQName(i), attributes.getValue(i));
	  }
  }
	
  @Override
  public void endElement(String uri, String localName, String qName) throws SAXException {
  	if (current == null) {
  		current = (steps.size() > 1) ? steps.pollLast() : steps.peekLast();
  	} 
  	
		if (current.getPrev() != null) {
			current.getPrev().addElement(current.builder());
		}
		current = null;
  }

  @Override
  public void characters(char[] ch, int start, int length) throws SAXException {
  	if (current != null)  {
  		current.setContent(replace(new String(ch, start, length).trim()));
  	}

  }

  private String replace(String content) {
  	if (content == null || content.length() == 0) {
  		return "";
  	}
	
  	final Matcher m = pattern.matcher(content);
  	return m.replaceAll("");
  }

	
}
