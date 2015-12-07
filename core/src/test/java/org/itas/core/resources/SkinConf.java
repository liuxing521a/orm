package org.itas.core.resources;

import java.util.List;
import java.util.Set;

import org.itas.core.Config;

public class SkinConf extends Config {

	private static SkinConf instance = new SkinConf();

	private SkinConf() {
	}

	public static SkinConf getInstance() {
		return instance;
	}

	/** 默认地板 */
	private List<String> skinCellList;
	/** 默认墙 */
	private List<String> skinWallList;
	/** 默认柱子 */
	private List<String> skinPillarList;
	/** 默认地图边背景缘 */
	private List<String> skinBackList;
	/** 不能换列表*/
	private Set<String> noChangeSkins;
	
	public List<String> getSkinCellList() {
		return skinCellList;
	}

	public List<String> getSkinWallList() {
		return skinWallList;
	}

	public List<String> getSkinPillarList() {
		return skinPillarList;
	}

	public List<String> getSkinBackList() {
		return skinBackList;
	}
	
	public Set<String> getNoChangeSkins() {
		return noChangeSkins;
	}

	@Override
	public String toString() {
		return String.format("%s", skinCellList);
	}
}
