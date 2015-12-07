package org.itas.core.resources;

import java.nio.file.Path;

import org.itas.core.Parser;

public class XmlParser implements Parser {
	
	private XmlParser() {
	}
	
	public static Parser newInstance() {
		return new XmlParser();
	}

  @Override
  public Element parse(Path file) throws Exception {
		return new XmlHandlerImpl().parse(file);
  }
	
}
