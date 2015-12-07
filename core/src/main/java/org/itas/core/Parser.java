package org.itas.core;

import java.nio.file.Path;

import org.itas.core.resources.Element;

public interface Parser {
  
  abstract Element parse(Path file) throws Exception;
  
}
