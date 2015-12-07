package org.itas.core.util;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import org.itas.common.ItasException;
import org.itas.common.Utils.Objects;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;

public interface ClassLoaders {

  default List<Path> loadFile(String packageName, String suffix) throws Exception {
	return CtClassImpl.instance.loadFile(packageName, suffix);
  }
  
  default Class<?>[] loadClass(String packageName) {
    return CtClassImpl.instance.loadClass(packageName); 
  }
  
  default CtClass[] loadCtClass(String packageName) throws Exception {
    return CtClassImpl.instance.loadCtClass(packageName); 
  }
  
  default List<Class<?>> getAllClass(Class<?> clazz) {
	return CtClassImpl.instance.getAllClass(clazz);  
  }
  
  default List<CtClass> getAllClass(CtClass clazz) throws Exception {
    return CtClassImpl.instance.getAllClass(clazz); 
  }
  
  default List<CtField> getAllField(CtClass clazz) throws Exception {
    return CtClassImpl.instance.getAllField(clazz); 
  }
	
  static class CtClassImpl {
	
	private static final CtClassImpl instance = new CtClassImpl();
	  
    public List<Path> loadFile(String packageName, String suffix) throws Exception {
	  final Path rootPath = Paths.get(ClassLoader.class.getResource("/").toURI());
	  final PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:*" + suffix);
	  final Path path = Paths.get(rootPath.toString(), packageName.replace(".", File.separator));
		
	  final List<Path> pathList = new ArrayList<Path>(path.getNameCount());
	  Files.walkFileTree(path, new SimpleFileVisitor<Path>(){
	    public FileVisitResult visitFile(Path file, 
	        BasicFileAttributes attrs) throws java.io.IOException {
	      if (matcher.matches(file.getFileName())) {
	    	pathList.add(file);
		  }
	    
	      return super.visitFile(path,attrs);
	    };
	  });
	
	  return pathList;
    }
	
    public CtClass[] loadCtClass(String packageName) throws Exception {
	  final List<Path> fileList =  loadFile(packageName, ".class");
	  assert fileList != null;
	  
	  final CtClass[] ctClasss = new CtClass[fileList.size()];
	
	  final ClassPool classPool = ClassPool.getDefault();
	  String fileName;
	  for (int i = 0; i < fileList.size(); i ++) {
	    fileName = fileList.get(i).getFileName().toString();
	    ctClasss[i] = classPool.get(String.format("%s.%s", packageName, 
		    fileName.substring(0, fileName.length() - 6)));
	  }
	
	  return ctClasss;
    }
  
    public Class<?>[] loadClass(String packageName) {
      try {
    	final List<Path> fileList =  loadFile(packageName, ".class"); 
    	assert fileList != null;

    	final Class<?>[] clazzArray = new Class<?>[fileList.size()];
			
		  String fileName;
		  for (int i = 0; i < fileList.size(); i ++) {
		    fileName = fileList.get(i).getFileName().toString();
		    clazzArray[i] = Class.forName(String.format("%s.%s", packageName, 
			    fileName.substring(0, fileName.length() - 6)));
		  }
			
		  return clazzArray;
      } catch (Exception e) {
    	throw new ItasException(e);
      }
    }

    public List<Class<?>> getAllClass(Class<?> clazz) {
      List<Class<?>> clazzList = new ArrayList<>();
      getAllSupperClass0(clazzList, clazz);
        
      return clazzList;
    }
    
    public List<CtClass> getAllClass(CtClass clazz) throws Exception {
      List<CtClass> clazzList = new ArrayList<CtClass>();
      
      CtClass objectClass = ClassPool.getDefault().get("java.lang.Object");
      getSupperClass(clazzList, clazz, objectClass);
      
      return clazzList;
    }

  
    public List<CtField> getAllField(CtClass childClass) throws Exception {
      List<CtField> fieldList = new ArrayList<CtField>();
      
      List<CtClass> supperClazzs = getAllClass(childClass);
      for (CtClass cls : supperClazzs) {
      	Objects.addAll(fieldList, cls.getDeclaredFields());
      }

      return fieldList;
    }
    
 

    private void getAllSupperClass0(List<Class<?>> clazzList, Class<?> clazz) {
      if (clazz.equals(Object.class)) {
	    return;
      }
        
      clazzList.add(clazz);
      getAllSupperClass0(clazzList, clazz.getSuperclass());
    }

    private void getSupperClass(List<CtClass> clazzList, 
        CtClass clazz, CtClass parentClass) throws Exception {
	  if (clazz.equals(parentClass)) {
		return;
	  }
    	
	  clazzList.add(clazz);
	  getSupperClass(clazzList, clazz.getSuperclass(), parentClass);
    }
  
    private CtClassImpl() {
    }
    
  }
}
