package org.itas.core;



/**
 * <p>自动同步数据库基类</p>
 * 所有自动 从数据库加载、自动修改数据库、自动删除等同步对象都需要继承此类；</br>
 * 继承此类后需要不会自动生成Id，需要制定Id
 * @author liuzhen<liuxing521a@gmail.com>
 * @createTime 2014年12月15日下午4:25:47
 */
public abstract class GameObject extends GameBase {

	protected GameObject(String Id) {
		super(Id);
	}
	
	/**
	 * <p>自动生成对象</p>
	 * 注:需要指定id
	 * @param Id 新对象的id
	 * @return 生成的对象
	 */
	protected abstract <T extends GameObject> T autoInstance(String Id);
	
	
	public int getCachedSize() {
		return 86;
	}

}
