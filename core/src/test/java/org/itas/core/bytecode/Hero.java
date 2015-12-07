package org.itas.core.bytecode;

import java.util.LinkedHashSet;
import java.util.Set;

import org.itas.core.GameObjectAotuID;
import org.itas.core.Simple;
import org.itas.core.annotation.Clazz;
import org.itas.core.annotation.Size;
import org.itas.core.bytecode.Model.Effect;
import org.itas.core.bytecode.Model.HeroType;
import org.itas.core.bytecode.Model.SexType;
import org.itas.core.bytecode.Model.SkillType;

public class Hero extends GameObjectAotuID {

	protected Hero(String Id) {
		super(Id);
	}
	
	@Clazz(LinkedHashSet.class)
	private Set<Integer> points;
	private Set<Simple<Hero>> depotS;
	@Size(16)
	private Set<HeroRes> heroResList;
	private Set<HeroType> heroTypeList;
	private Set<SexType> sexTypeList;
	private Set<Effect> effectTypeList;
	private Set<SkillType> skillTypeList;
	
	private Item item;
	
	private int[] pointArray;
	private Simple<Hero>[] heroArray;
	private HeroRes[] heroResArray;
	private HeroType[] heroTypeArray;
	private Item[] itemArray;
	
	private int[][] pointDArray;
	private Simple<Hero>[][] heroDArray;
	private HeroRes[][] heroResDArray;
	private HeroType[][] heroTypeDArray;
	private Item[][] itemDArray;
	
	
	public int[][] getPointDArray() {
		return pointDArray;
	}

	public void setPointDArray(int[][] pointDArray) {
		this.pointDArray = pointDArray;
	}

	public Simple<Hero>[][] getHeroDArray() {
		return heroDArray;
	}

	public void setHeroDArray(Simple<Hero>[][] heroDArray) {
		this.heroDArray = heroDArray;
	}

	public HeroRes[][] getHeroResDArray() {
		return heroResDArray;
	}

	public void setHeroResDArray(HeroRes[][] heroResDArray) {
		this.heroResDArray = heroResDArray;
	}

	public HeroType[][] getHeroTypeDArray() {
		return heroTypeDArray;
	}

	public void setHeroTypeDArray(HeroType[][] heroTypeDArray) {
		this.heroTypeDArray = heroTypeDArray;
	}

	public Item[][] getItemDArray() {
		return itemDArray;
	}

	public void setItemDArray(Item[][] itemDArray) {
		this.itemDArray = itemDArray;
	}

	public int[] getPointArray() {
		return pointArray;
	}

	public void setPointArray(int[] pointArray) {
		this.pointArray = pointArray;
	}

	public Simple<Hero>[] getHeroArray() {
		return heroArray;
	}

	public void setHeroArray(Simple<Hero>[] heroArray) {
		this.heroArray = heroArray;
	}

	public HeroRes[] getHeroResArray() {
		return heroResArray;
	}

	public void setHeroResArray(HeroRes[] heroResArray) {
		this.heroResArray = heroResArray;
	}

	public HeroType[] getHeroTypeArray() {
		return heroTypeArray;
	}

	public void setHeroTypeArray(HeroType[] heroTypeArray) {
		this.heroTypeArray = heroTypeArray;
	}

	public Item[] getItemArray() {
		return itemArray;
	}

	public void setItemArray(Item[] itemArray) {
		this.itemArray = itemArray;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
	
	public Set<Integer> getPoints() {
		return points;
	}

	public void setPoints(Set<Integer> points) {
		this.points = points;
	}

	public Set<Simple<Hero>> getDepotS() {
		return depotS;
	}

	public void setDepotS(Set<Simple<Hero>> depotS) {
		this.depotS = depotS;
	}

	public Set<HeroRes> getHeroResList() {
		return heroResList;
	}

	public void setHeroResList(Set<HeroRes> heroResList) {
		this.heroResList = heroResList;
	}

	public Set<HeroType> getHeroTypeList() {
		return heroTypeList;
	}

	public void setHeroTypeList(Set<HeroType> heroTypeList) {
		this.heroTypeList = heroTypeList;
	}

	public Set<SexType> getSexTypeList() {
		return sexTypeList;
	}

	public void setSexTypeList(Set<SexType> sexTypeList) {
		this.sexTypeList = sexTypeList;
	}

	public Set<Effect> getEffectTypeList() {
		return effectTypeList;
	}

	public void setEffectTypeList(Set<Effect> effectTypeList) {
		this.effectTypeList = effectTypeList;
	}

	public Set<SkillType> getSkillTypeList() {
		return skillTypeList;
	}

	public void setSkillTypeList(Set<SkillType> skillTypeList) {
		this.skillTypeList = skillTypeList;
	}

	@Override
	public String prefix() {
		return null;
	}

}
