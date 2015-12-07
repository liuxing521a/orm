package org.itas.core.resources;

import java.util.List;

import org.itas.core.Resource;


/**
 * @author liuzhen<liuxing521a@163.com
 * @createTime 2014年5月9日
 */
public class HeroRes extends Resource {

	protected HeroRes(String Id) {
		super(Id);
	}
	
	private String name;
	private List<String> effects;
	
	private String weapon;
	private String head;
	private String body;
	private String adorn;
	
	public String getName() {
		return name;
	}
	
	public List<String> getEffects() {
		return effects;
	}
	
	public String getWeapon() {
		return weapon;
	}
	
	public String getHead() {
		return head;
	}
	
	public String getBody() {
		return body;
	}
	
	public String getAdorn() {
		return adorn;
	}
	
	@Override
	public String toString() {
		return String.format("\nId=%s, name=%s", getId(), getName());
	}
	
}
