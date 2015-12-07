package org.itas.core.resources;

import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import junit.framework.Assert;

import org.itas.core.Config;
import org.itas.core.Resource;
import org.itas.core.resources.XmlLoader.XmlLoaderImpl;
import org.itas.core.util.ItPaths;
import org.junit.Test;

public class TestResourcesParser implements ItPaths {

	@Test
	public void parseConfTest() throws Exception {
		Path file = Paths.get(this.getClass().getResource("/SkinConf.xml").toURI());
		XmlHandlerImpl pareser = new XmlHandlerImpl();
		Element root = pareser.parse(file);
		
		Element el = root.getElement("skinCellList");
		Assert.assertEquals("skin-10000|skin-10600|skin-10700", el.getContent());
		
		el = root.getElement("skinWallList");
		Assert.assertEquals("skin-10100|skin-10101|skin-10200|skin-10300|skin-10400", el.getContent());
		
		el = root.getElement("skinPillarList");
		Assert.assertEquals("skin-10500", el.getContent());
		
		el = root.getElement("skinBackList");
		Assert.assertEquals("skin-40000", el.getContent());
		
		el = root.getElement("noChangeSkins");
		Assert.assertEquals("skin-10100|skin-10700|skin-40000", el.getContent());
	}
	
	@Test
	public void parseResTest() throws Exception {
		Path file = Paths.get(this.getClass().getResource("/HeroRes.xml").toURI());
		XmlHandlerImpl pareser = new XmlHandlerImpl();
		Element root = pareser.parse(file);
		
		Field field1 = HeroRes.class.getDeclaredField("name");
		Field field2 = HeroRes.class.getDeclaredField("effects");
//		Field field3 = SkinConf.class.getDeclaredField("weapon");
//		Field field4 = SkinConf.class.getDeclaredField("head");
		Field field5 = HeroRes.class.getDeclaredField("body");
//		Field field6 = SkinConf.class.getDeclaredField("adorn");
		Element el = root.getElements().get(7);
		Assert.assertEquals("红孩儿", el.getAttribute().getString(field1));
		Assert.assertEquals("effect-hero-061", el.getAttribute().getList(field2).get(0));
		Assert.assertEquals("skin-28301", el.getAttribute().getString(field5));

	}
	
	@Test
	public void parseDBTest() throws Exception {
//		Path file = Paths.get(this.getClass().getResource("/Main.xml").toURI());
//		XmlHandlerImpl pareser = new XmlHandlerImpl();
//		Element root = pareser.parse(file);
		
		XmlLoaderImpl.XmlLoaderBuilder builder = XmlLoader.newBuilder();
		
		builder.setWorkDir(System.getProperty("user.dir"));
		builder.setParentClass(Resource.class);
		builder.addJavaPack("bin", "org\\itas\\core\\resources");
		builder.addResPack("bin");
		
		List<XmlBean> xmlBeans = builder.builder().loadBean();
		for (XmlBean bean : xmlBeans) {
			bean.newInstance();
			bean.load();
			System.out.println(bean.getXmlList());
		}
		
		builder = XmlLoader.newBuilder();
		
		builder.setWorkDir(System.getProperty("user.dir"));
		builder.setParentClass(Config.class);
		builder.addJavaPack("bin", "org\\itas\\core\\resources");
		builder.addResPack("bin");
		
		xmlBeans = builder.builder().loadBean();
		for (XmlBean bean : xmlBeans) {
			bean.newInstance();
			bean.load();
			System.out.println(bean.getXmlList());
		}
		  
//		Path path = Paths.get("E:\\source\\world\\server\\logic\\src\\main\\resources\\res");
//		loadMap(path, "xml").forEach((k, v)->{
//			System.out.println("key->" + k + ", value->" + v);
//		});
//	
//		path = Paths.get("E:\\source\\world\\server\\logic\\src\\main\\java\\com\\aojia\\world\\res");
//		loadMap(path, "java").forEach((k, v)->{
//			System.out.println("key->" + k + ", value->" + v);
//		 });

	}

}
