package characters;

import common.Functions;
import common.EnemyType;


public class Enemy{
	
	Functions f = new Functions();
	EnemyType[] et = new EnemyType[] 
	{
		new EnemyType("Bandit", "Human"),
		new EnemyType("Skeleton", "Undead"),
		new EnemyType("Wolf", "Animal"),
		new EnemyType("Werewolf", "Animal")
	};



	
	private String enemyType;
	private String enemyRace;
	private int hp;
	private int dmg;
	private int xpValue;

	public Enemy(int rCleared) //rCleared necessery for increasing difficulty with in-game progress
	{
		int k = f.rand(0, et.length-1); // k to losowa liczba miedzy 0 a ostatnia pozycja w tablicy EnemyType[]
		this.enemyType = et[k].getType();
		this.enemyRace = et[k].getRace();
		this.hp = (int)(f.rand(20,30)+(1.5*rCleared));
		this.dmg = f.rand(5,10)+rCleared/6;
		this.xpValue = (f.rand(20, 30)+(rCleared/2));

		if(this.enemyType.equals("Werewolf")) // Zmienia lekko statystki jezeli jest to wilkolak
		{
			this.dmg += 4; //Werewolf jest silniejszy
			this.hp -= 10+(rCleared);
		}
	}

	public Enemy createCustomEnemy(String type, String race, int hp, int dmg, int xpValue)
	{
		this.enemyType = type;
		this.enemyRace = race;
		this.hp = hp;
		this.dmg = dmg;
		this.xpValue = xpValue;

		final Enemy enemy = this;
		return enemy;
	}
	
	public void show(){ System.out.println(this.enemyType+", "+this.enemyRace); } //ENCOUNTER ENEMY
	public void recDmg(int d){ this.hp -= d; } //RECIEVE DMG
	public int getHp(){ return this.hp; } //RETURN CURRENT HP
	public int getDmg(){ return this.dmg; } //RETURN DMG
	public int getXpValue(){ return this.xpValue; } //RETURN DMG
	public String getType(){ return this.enemyType; }
	public String getRace(){ return this.enemyRace; }
}
