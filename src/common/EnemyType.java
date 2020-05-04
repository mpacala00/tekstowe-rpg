package common;

public class EnemyType {
	private String type;
	private String race;
	
	public EnemyType(String type, String race)
	{
		this.type = type;
		this.race = race;
	}
	
	public String getType()
	{
		return this.type;
	}
	
	public String getRace()
	{
		return this.race;
	}
}
