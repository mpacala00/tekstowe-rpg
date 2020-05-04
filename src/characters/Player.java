package characters;

import java.util.Random;
import java.util.Scanner;
import Items.*;
import common.Functions;


public class Player {
	Functions f = new Functions();
	

		private int hp, level, xp;
		int xpToLevel;
		int maxHp;
		int eng, engUpgrade;
		int maxEng;
		int dmg;
		int attCost;
		int potionNum;

		int gCoins;

		public Boolean isNextRoomAnEvent = true;

	Weapon pWeapon;
	
	public Player()
	{
		level = 1;
		xp = 0;
		xpToLevel = ( 80 + (20*this.level) );

		hp = 100;
		maxHp = 100; //Maybe increasing with levels
		eng = 100;
		maxEng = 100;
		engUpgrade = 0;

		dmg = 10;

		gCoins = 0;

		pWeapon = new Weapon(0, "Rusty Sword", 10, "");
		
		attCost = 22;
		potionNum = 1;
	}

	// GETTERS -----------------------------------------
	public int getDmg(){ return dmg; }
	public double getWeaponDmg(){ return pWeapon.getDmg(); }
	public int checkWeaponBonus(Weapon w, Enemy e, Player p) { return w.checkBonus(e, p); }
	public int getAttCost(){ return attCost; }
	public int getXpToLevel() { return xpToLevel; }

	public int getXp(){ return xp; }
	public int getLevel(){ return level; }

	public int getHp(){ return hp; }
	public int getMaxHp(){ return maxHp; }
	public int getMaxEng(){ return maxEng; }
	public int getCoins(){ return gCoins; }
	public int getPotionNum(){ return potionNum; }

	public void engUpgrade() { this.engUpgrade+=10; } //Doliczana ilosc do regeneracji eng: 10 co poziom
	// --------------------------------------------------

	public void foundPotion(int howMany)
	{
		this.potionNum += howMany;
	}
	public void foundCoins(int howMany) { this.gCoins += howMany; }

	public void pay(int howMuch) { this.gCoins -= howMuch; }

	public void recXp(int xp) { this.xp += xp; }

	public int getEng(){ return eng; }
	public Weapon getWeapon() { return pWeapon; }
	public void changeWeapon(Weapon w) { this.pWeapon = w; }
	public void addStr(int howMuch) { this.dmg += howMuch; }
	public void addEng(int howMuch) { this.maxEng += howMuch; }

	public void addDmg(int amount){ this.dmg += amount; } //tylko do Weapon
	public void setHp(int hp){ this.hp = hp; }
	public void drinkPotion() { this.potionNum--; }
	public void setEng(int eng){ this.eng = eng; }

	public void regEng()
	{
		int n = f.rand(60, 80)+engUpgrade;
		if(n+this.eng>this.maxEng) setEng(this.maxEng);
		else setEng(this.eng+n);
		System.out.println("You regained "+n+" points of energy.");
	}
	
	public void recDmg(Enemy enemy)
	{ 
		int dmg = (enemy.getDmg()+f.rand(1, 5)); //damage taken from enemies
		this.hp -= dmg; 
		System.out.println("You recieved "+dmg+" damage.");
	}

	public int calcDmg()
	{
		Random rand = new Random();
		int n = rand.nextInt(5)+1;
		return (int)(getWeaponDmg()+getDmg()+n-5); //10*0.1*10+(from 1 to 5) = 10+(1 to 5)
	}

	public void healFull()
	{
		System.out.println("Your wounds are healed completly.");
		this.setHp(this.maxHp);
	}

	public void levelUp()
	{
		//Od aktualnego expa odejmujemy ilosc xp potrzebna do wbicia poziomu
		this.xp -= this.xpToLevel;

		this.level++;

		//Ustawia nowa ilosc xp wymagana do levela
		this.xpToLevel = ( 80 + (30*this.level) );
	}

	//choice bedzie od 1 do 3 i bedzie okreslal co gracz postanowil zwiekszyc: hp, dmg czy energy
	public boolean checkLevelUpgrade(String choice)
	{
			if (choice.equals("1")) //DMG
			{
				this.dmg += 5;
				System.out.println("You feel stronger now. Current DMG: "+this.dmg);
				return true;

			} else if (choice.equals("2")) //HP
			{
				this.maxHp += 20;
				this.hp += 20;
				System.out.println("Your thoughness has increased. Current HP: "+this.maxHp);
				return true;

			} else if (choice.equals("3")) //ENG
			{
				this.maxEng += 40;
				this.eng += 40;
				this.engUpgrade();
				System.out.println("Your stamina has increased. Current ENG: "+this.maxEng);
				return true;
			}

			else
			{
				System.out.println("Wrong input");
				return false;
			}
	}


}
