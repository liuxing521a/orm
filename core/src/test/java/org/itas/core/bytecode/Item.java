package org.itas.core.bytecode;

import org.itas.core.GameObjectAotuID;
import org.itas.core.annotation.SQLEntity;

@SQLEntity("item")
public class Item extends GameObjectAotuID {

	protected Item(String Id) {
		super(Id);
	}
	
	private String name;
	
	private int coinPrice;
	
	private int goldPrice;
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCoinPrice() {
		return coinPrice;
	}

	public void setCoinPrice(int coinPrice) {
		this.coinPrice = coinPrice;
	}

	public int getGoldPrice() {
		return goldPrice;
	}

	public void setGoldPrice(int goldPrice) {
		this.goldPrice = goldPrice;
	}

	@Override
	public String prefix() {
		return "it_";
	}

}
