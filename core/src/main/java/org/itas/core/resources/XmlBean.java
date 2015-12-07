package org.itas.core.resources;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.itas.common.ItasException;
import org.itas.common.Utils.ClassUtils;
import org.itas.core.Builder;
import org.itas.core.Config;
import org.itas.core.Parser;
import org.itas.core.Resource;
import org.itas.core.resources.Attribute.AttributeBuilder;
import org.itas.core.util.Constructors;


final class XmlBean {
	
	private static Parser parser;
	
	static {
		parser = XmlParser.newInstance();
	}
	
	private final Path file;
	private final Class<?> clazz;
	private final List<Field> fields;
	private final InnerProcess process;
	
	private Element root;
	private List<AbstractXml> xmlList;
	
	private XmlBean(Path file, Class<?> clazz) {
		this.file = file;
		this.clazz = clazz;
		this.fields = ClassUtils.getAllField(clazz);
		this.process = getProcess(clazz);
	}
	
	public Class<?> getClazz() {
		return clazz;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends AbstractXml> List<T> getXmlList() {
		return (List<T>) xmlList;
	}

	public void newInstance() throws Exception {
		process.newInstance();
	}

	public void load() throws Exception {
		process.load();
	}

	public void reLoad() throws Exception {
		process.reLoad();
	}
	
	private InnerProcess getProcess(Class<?> clazz) {
		if (Config.class.isAssignableFrom(clazz)) {
			return new InnerConfigProcess(this);
		}
		
		if (Resource.class.isAssignableFrom(clazz)) {
			return new InnerResourceProcess(this);
		}
		
		throw new ItasException("");
		
	}
	
	public static XmlBeanBuilder newBuilder() {
		return new XmlBeanBuilder();
	}
	
	public static class XmlBeanBuilder implements Builder {
		
		private Path file;
		private Class<?> clazz;
		
		private XmlBeanBuilder() {
		}
		
		public XmlBeanBuilder setFile(Path file) {
			this.file = file;
			return this;
		}

		public XmlBeanBuilder setClazz(Class<?> clazz) {
			this.clazz = clazz;
			return this;
		}

		@Override
		public XmlBean builder() {
			return new XmlBean(file, clazz);
		}
	}

	
	static abstract class InnerProcess {
		
		protected final XmlBean xmlBean;
		
		private InnerProcess(XmlBean xmlBean) {
			this.xmlBean = xmlBean;
		}
		
		public abstract void newInstance() throws Exception;

		public abstract void load() throws Exception;

		public abstract void reLoad() throws Exception;
		
		protected void checkAndLoad(AbstractXml res, Attribute attr) throws Exception {
			if (!((Resource)res).getId().equals(attr.getValue("Id"))) {
				throw new ItasException(
						res.getClass().getSimpleName() + ".xml only change attribute value...");
			}
			
			res.load(xmlBean.fields, attr);
		}
		
	}
	
	private static class InnerConfigProcess extends InnerProcess implements Constructors {

		private InnerConfigProcess(XmlBean xmlBean) {
			super(xmlBean);
		}
		
		@Override
		public void newInstance() throws Exception {
			if (xmlBean.xmlList != null) {
				throw new ItasException("can not more once times...");
			}
			
			xmlBean.root = parser.parse(xmlBean.file);
			Method method = xmlBean.clazz.getMethod("getInstance");
			method.setAccessible(true);

			List<AbstractXml> confList = Arrays.asList((AbstractXml)method.invoke(null));
			xmlBean.xmlList = Collections.unmodifiableList(confList);
		}

		@Override
		public void load() throws Exception {
			if (xmlBean.root == null) {
				throw new ItasException("can not more once times...");
			}
			
			List<Element> elements = xmlBean.root.getElements();
			AttributeBuilder builder = Attribute.newBuilder();
			for (Element element : elements) {
				builder.addAttribute(element.getName(), element.getContent());
			}
			
			checkAndLoad(xmlBean.xmlList.get(0), builder.builder());
			
			xmlBean.root = null;
		}

		@Override
		public void reLoad() throws Exception {
			throw new ItasException("config unsupport reloading...");
		}
		
		@Override
		protected void checkAndLoad(AbstractXml res, Attribute attr) throws Exception {
			res.load(xmlBean.fields, attr);
		}
	}
	
	private static class InnerResourceProcess extends InnerProcess implements Constructors {

		private InnerResourceProcess(XmlBean xmlBean) {
			super(xmlBean);
		}
		
		public void newInstance() throws Exception {
			if (xmlBean.xmlList != null) {
				throw new ItasException("can not more once times...");
			}
			xmlBean.root = parser.parse(xmlBean.file);
			List<AbstractXml> reslList = new ArrayList<>(xmlBean.root.getChildCount());
			
			String rid;
			for (Element element : xmlBean.root.getElements()) {
				rid = element.getAttribute().getValue("Id");
				reslList.add(newInstance(xmlBean.clazz, new String[]{rid}));
			}
			
			xmlBean.xmlList = Collections.unmodifiableList(reslList);
		}

		public void load() throws Exception {
			if (xmlBean.root == null) {
				throw new ItasException("can not more once times...");
			}
			
			List<Element> elements = xmlBean.root.getElements();
			for (int i = 0; i < elements.size(); i++) {
				checkAndLoad(xmlBean.xmlList.get(i), elements.get(i).getAttribute());
			}
			
			xmlBean.root = null;
		}
		
		public void reLoad() throws Exception {
			if (xmlBean.xmlList == null) {
				throw new ItasException("must loading...");
			}
			
			xmlBean.root = parser.parse(xmlBean.file);
			List<Element> elements = xmlBean.root.getElements();
			for (int i = 0; i < elements.size(); i++) {
				checkAndLoad(xmlBean.xmlList.get(i), elements.get(i).getAttribute());
			}
			
			xmlBean.root = null;
		}
	}
}
