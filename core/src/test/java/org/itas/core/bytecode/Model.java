package org.itas.core.bytecode;

import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.itas.core.EnumByte;
import org.itas.core.EnumInt;
import org.itas.core.EnumString;
import org.itas.core.GameObject;
import org.itas.core.Simple;
import org.itas.core.annotation.Clazz;
import org.itas.core.annotation.SQLEntity;
import org.itas.core.annotation.Size;

@SQLEntity("model")
public class Model extends GameObject {

	public enum SexType implements EnumByte {
		man {
			@Override
			public byte key() {
				return 1;
			}
		},
	}
	
	public enum Effect implements EnumInt {
		effect1{
			@Override
			public int key() {
				return 2;
			}
		},
	}
	
	public enum SkillType implements EnumString {
		skill1{
			@Override
			public String key() {
				return "skill";
			}
		},
	}
	
	public enum HeroType {
		hero
	}
	
	protected Model(String Id) {
		super(Id);
	}

	private String name;
	private char sex;
	private boolean isMarry;
	private byte chirldCount;
	private short gemCount;
	private int age;
	private long hp;
	private float gameCoin;
	private double money;
	private SexType sexType;
	private Effect effect;
	private SkillType skillType;
	private HeroType heroType;
	private Simple<Hero> heroS;
	private Timestamp updateAt;
	private HeroRes heroRes;
	@Clazz(LinkedList.class)
	private List<Integer> points;
	private List<Simple<Hero>> depotS;
	@Size(16)
	private List<HeroRes> heroResList;
	private List<HeroType> heroTypeList;
	private List<SexType> sexTypeList;
	private List<Effect> effectTypeList;
	private List<SkillType> skillTypeList;
	
	private Map<Integer, Simple<Hero>> heroGroups;
	@Size(16)private Map<HeroRes, Integer> heroResMap;
	private Map<SexType, Float> sexCoinMap;
	@Clazz(LinkedHashMap.class) private Map<Simple<Hero>, String> cardGroupNames;
	
	
	private Hero hero;
	private Model model;
	
	
	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public Hero getHero() {
		return hero;
	}

	public void setHero(Hero hero) {
		this.hero = hero;
	}

	public Map<Integer, Simple<Hero>> getHeroGroups() {
		return heroGroups;
	}

	public void setHeroGroups(Map<Integer, Simple<Hero>> heroGroups) {
		this.heroGroups = heroGroups;
	}

	public Map<HeroRes, Integer> getHeroResMap() {
		return heroResMap;
	}

	public void setHeroResMap(Map<HeroRes, Integer> heroResMap) {
		this.heroResMap = heroResMap;
	}

	public Map<SexType, Float> getSexCoinMap() {
		return sexCoinMap;
	}

	public void setSexCoinMap(Map<SexType, Float> sexCoinMap) {
		this.sexCoinMap = sexCoinMap;
	}

	public Map<Simple<Hero>, String> getCardGroupNames() {
		return cardGroupNames;
	}

	public void setCardGroupNames(Map<Simple<Hero>, String> cardGroupNames) {
		this.cardGroupNames = cardGroupNames;
	}

	public List<HeroType> getHeroTypeList() {
		return heroTypeList;
	}

	public void setHeroTypeList(List<HeroType> heroTypeList) {
		this.heroTypeList = heroTypeList;
	}

	public List<SexType> getSexTypeList() {
		return sexTypeList;
	}

	public void setSexTypeList(List<SexType> sexTypeList) {
		this.sexTypeList = sexTypeList;
	}

	public List<Effect> getEffectTypeList() {
		return effectTypeList;
	}

	public void setEffectTypeList(List<Effect> effectTypeList) {
		this.effectTypeList = effectTypeList;
	}

	public List<SkillType> getSkillTypeList() {
		return skillTypeList;
	}

	public void setSkillTypeList(List<SkillType> skillTypeList) {
		this.skillTypeList = skillTypeList;
	}

	public List<Integer> getPoints() {
		return points;
	}

	public void setPoints(List<Integer> points) {
		this.points = points;
	}

	public List<Simple<Hero>> getDepotS() {
		return depotS;
	}

	public void setDepotS(List<Simple<Hero>> depotS) {
		this.depotS = depotS;
	}

	public List<HeroRes> getHeroResList() {
		return heroResList;
	}

	public void setHeroResList(List<HeroRes> heroResList) {
		this.heroResList = heroResList;
	}

	public HeroRes getHeroRes() {
		return heroRes;
	}

	public void setHeroRes(HeroRes heroRes) {
		this.heroRes = heroRes;
	}

	public Timestamp getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Timestamp updateAt) {
		this.updateAt = updateAt;
	}

	public Simple<Hero> getHeroS() {
		return heroS;
	}

	public void setHeroS(Simple<Hero> heroS) {
		this.heroS = heroS;
	}

	public HeroType getHeroType() {
		return heroType;
	}

	public void setHeroType(HeroType heroType) {
		this.heroType = heroType;
	}

	public SkillType getSkillType() {
		return skillType;
	}

	public void setSkillType(SkillType skillType) {
		this.skillType = skillType;
	}

	public Effect getEffect() {
		return effect;
	}

	public void setEffect(Effect effect) {
		this.effect = effect;
	}

	public SexType getSexType() {
		return sexType;
	}

	public void setSexType(SexType sexType) {
		this.sexType = sexType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public short getGemCount() {
		return gemCount;
	}

	public void setGemCount(short gemCount) {
		this.gemCount = gemCount;
	}

	public long getHp() {
		return hp;
	}

	public void setHp(long hp) {
		this.hp = hp;
	}

	public float getGameCoin() {
		return gameCoin;
	}

	public void setGameCoin(float gameCoin) {
		this.gameCoin = gameCoin;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public char getSex() {
		return sex;
	}

	public void setSex(char sex) {
		this.sex = sex;
	}

	public byte getChirldCount() {
		return chirldCount;
	}

	public void setChirldCount(byte chirldCount) {
		this.chirldCount = chirldCount;
	}

	public boolean isMarry() {
		return isMarry;
	}

	public void setMarry(boolean isMarry) {
		this.isMarry = isMarry;
	}

	@Override
	public String prefix() {
		return "mt_";
	}

	@Override
	protected <T extends GameObject> T autoInstance(String Id) {
		return null;
	}

}
