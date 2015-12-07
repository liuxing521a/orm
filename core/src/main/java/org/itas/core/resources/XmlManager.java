package org.itas.core.resources;

import java.util.Collection;
import java.util.List;

import org.itas.core.Builder;
import org.itas.core.Config;
import org.itas.core.Ioc;
import org.itas.core.Pool.ResPool;
import org.itas.core.Resource;
import org.itas.core.Service.OnBinder;
import org.itas.core.Service.OnShutdown;
import org.itas.core.Service.OnStartUP;
import org.itas.core.resources.ResPoolImpl.ResPoolImplBuilder;

import com.google.inject.Binder;

public class XmlManager implements OnShutdown, OnStartUP, OnBinder {

	private final String workDir;
	
	private final String[] resJavaPack;
	private final String[] resResourcePack;
	
	private final String[] confJavaPack;
	private final String[] confResourcePack;
	
	private XmlManager(String workDir,
			String[] resJavaPack,
			String[] resResourcePack,
			String[] confJavaPack,
			String[] confResourcePack) {
		this.workDir = workDir;
		this.resJavaPack = resJavaPack;
		this.resResourcePack = resResourcePack;
		this.confJavaPack = confJavaPack;
		this.confResourcePack = confResourcePack;
	}
	
	@Override
	public void bind(Binder binder) throws Exception {
		XmlLoader resLoader = XmlLoader.newBuilder()
			.setWorkDir(workDir)
			.setParentClass(Resource.class)
			.addJavaPack(resJavaPack)
			.addResPack(resResourcePack)
			.builder();
		
		ResPoolImplBuilder builder = ResPoolImpl.newBuilder();
		List<XmlBean> beans = resLoader.loadBean();
		for (XmlBean bean : beans) {
			bean.newInstance();
			builder.addXmlBean(bean);
		}
		
		
		binder.bind(XmlLoader.class).toInstance(resLoader);
		binder.bind(ResPool.class).toInstance(builder.builder());
	}
	
	@Override
	public void onStartUP() throws Exception {
		ResPool respool = Ioc.getInstance(ResPool.class);
		
		Collection<XmlBean> beans = ((ResPoolImpl)respool).getXmlBeans();
		for (XmlBean bean : beans) {
			bean.load();
		}
		
		XmlLoader confLoader = XmlLoader.newBuilder()
				.setWorkDir(workDir)
				.setParentClass(Config.class)
				.addJavaPack(confJavaPack)
				.addResPack(confResourcePack)
				.builder();
		
		beans = confLoader.loadBean();
		for (XmlBean bean : beans) {
			bean.newInstance();
			bean.load();
		}
	}

	@Override
	public void onShutdown() throws Exception {
		// TODO Auto-generated method stub
		
	}

	
	
	public static class XmlManagerBuilder implements Builder {

		@Override
		public XmlManager builder() {
			return null;
		}
		
	}

}
