package org.itas.core.util;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ItPaths {

	default List<Path> loadList(Path root, String suffix) throws IOException {
		final List<Path> paths = new ArrayList<>(root.getNameCount());

		final PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:*" + suffix);
		Files.walkFileTree(root, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) 
			throws java.io.IOException {
				if (matcher.matches(file.getFileName())) {
					paths.add(file);
				}

				return super.visitFile(root, attrs);
			};
		});

		return paths;
	}
	
	default Map<String, Path> loadMap(Path root, String suffix) throws IOException {
		final Map<String, Path> paths = new HashMap<>(root.getNameCount());

		final PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:*" + suffix);
		Files.walkFileTree(root, new SimpleFileVisitor<Path> () {
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) 
			throws java.io.IOException {
				if (matcher.matches(file.getFileName())) {
					Path path = root.relativize(file);
					paths.put(subSuffix(path, suffix), file);
				}

				return super.visitFile(root, attrs);
			};
		});

		return paths;
	}
	
	static String subSuffix(Path path, String suffix) {
		String name = path.toString();
		final int index = name.indexOf(suffix);
		
		return (index != -1) ? name.substring(0, index - 1) : name;
	}

}
