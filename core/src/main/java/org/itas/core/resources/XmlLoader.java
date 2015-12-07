package org.itas.core.resources;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Modifier;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import org.itas.common.Utils.ClassUtils;
import org.itas.core.Builder;
import org.itas.core.util.Constructors;
import org.itas.core.util.ItPaths;

abstract class XmlLoader implements Constructors {
	
	public abstract List<XmlBean> loadBean() throws Exception;
	
	public static XmlLoaderImpl.XmlLoaderBuilder newBuilder() {
		return new XmlLoaderImpl.XmlLoaderBuilder();
	}
	
	static class XmlLoaderImpl extends XmlLoader implements ItPaths{
		
		private final String homeDir;
		private final String[] javapack;
		private final String[] resPack;
		private final Class<?> parentClass;
		
		private XmlLoaderImpl(String homeDir, String[] javapack, 
				String[] resPack, Class<?> parentClass) {
			this.homeDir = homeDir;
			this.javapack = javapack;
			this.resPack = resPack;
			this.parentClass = parentClass;
		}
		
		@Override
		public List<XmlBean> loadBean() throws Exception {
			List<XmlBean> beans = new LinkedList<>();
			
			Path xmlRoot = Paths.get(homeDir, resPack);
			Map<String, Path> xmlPaths = loadMap(xmlRoot, "xml");
			
			Path javaRoot = Paths.get(homeDir, javapack);
			Map<String, Path> javaPaths = loadMap(javaRoot, "class");
			
			AtomicReference<Exception> ref = new AtomicReference<>();
			javaPaths.forEach((name, path)->{
				Path fullClassPath = Paths.get(javapack[1], name);
				final String className = fullClassPath.toString().replace(File.separatorChar, '.');
				Class<?> clazz = ClassUtils.forName(className);
				
				if (Modifier.isAbstract(clazz.getModifiers()) ||
					!parentClass.isAssignableFrom(clazz)) {
					return;
				}
				
				final Path xmlPath = xmlPaths.get(name);
				if (xmlPath == null && ref.get() == null) {
					ref.set(new FileNotFoundException(name));
				}
				
				beans.add(XmlBean.newBuilder()
					.setClazz(clazz)
					.setFile(xmlPath)
					.builder());
			});
			
			if (ref.get() != null) {
				throw ref.get();
			}
			
			return beans;
		}
		
		public static class XmlLoaderBuilder implements Builder {
			
			protected String workDir;
			protected List<String> javaPackList;
			protected List<String> resPackList;
			protected Class<?> parentClass;
			
			private XmlLoaderBuilder() {
			}
			
			public XmlLoaderBuilder setWorkDir(String workDir) {
				this.workDir = workDir;
				return this;
			}
			
			public XmlLoaderBuilder addJavaPack(String...packs) {
				if (javaPackList == null) {
					javaPackList = new ArrayList<>(4);
				}
				
				for (String pack : packs) {
					this.javaPackList.add(pack);
				}
				return this;
			}

			public XmlLoaderBuilder addResPack(String...packs) {
				if (resPackList == null) {
					resPackList = new ArrayList<>(4);
				}
				
				for (String pack : packs) {
					this.resPackList.add(pack);
				}
				return this;
			}
			
			public XmlLoaderBuilder setParentClass(Class<?> parentClass) {
				this.parentClass = parentClass;
				return this;
			}
			
			@Override
			public XmlLoader builder() {
				String[] javapack = new String[javaPackList.size()];
				javaPackList.toArray(javapack);
				
				String[] respack = new String[resPackList.size()];
				resPackList.toArray(respack);
				return new XmlLoaderImpl(workDir, javapack, respack, parentClass);
			}
			
		}
	}
	
}
