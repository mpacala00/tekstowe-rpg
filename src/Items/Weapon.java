package Items;

import java.util.Random;
import characters.Enemy;
import characters.Player;
import common.Functions;

import java.util.Random;

public class Weapon extends Item {
    Functions f = new Functions();

    private double dmg;
    private String bonus;

    //To get lifesteal/ doubleAttack - define String bonus as one of those
    //To get bonusDmg (for certain enemy races) - define bonus as race, e.g. Undead, Animal etc.

    public Weapon(int value, String name, int dmg, String bonus)
    {
        super(value, name);
        this.dmg = dmg;
        this.bonus = bonus;
    }

    public double getDmg() { return this.dmg; }
    public String getClassString() { return "Weapon"; }

    public void show()
    {
        String bonusText; // bonusText = good against humans // bonusText = special power: lifesteal
        if(bonus.equals("Human") || bonus.equals("Animal") || bonus.equals("Undead"))
        {
            bonusText = ", good against "+this.bonus+" (+20% DMG)";
        } else if(bonus.equals("lifesteal"))
        {
            bonusText = ", special power: "+this.bonus;
        } else if(bonus.equals("doubleAttack"))
        {
          bonusText =  ", 25% chance to deal 2x DMG";
        } else bonusText = "";
        System.out.print(getName()+", value: "+getValue());
        System.out.println(" | DMG: "+(int)dmg+bonusText);
    }



    public int checkBonus(Enemy e, Player p)
    {
        if(this.bonus.equals("lifesteal")) return lifesteal(p);
        if(this.bonus.equals("doubleAttack")) return doubleAttack(25);
        if(this.bonus.equals("Undead")) return bonusDmg(e, 0.2, "Undead");
        if(this.bonus.equals("Animal")) return bonusDmg(e, 0.2, "Animal");
        if(this.bonus.equals("Human")) return bonusDmg(e, 0.2, "Animal");
        return 0; //Ogolnie to te returny to jest bonus damage, ktory jest doliczany bezposrednio do normalnego dmg
    }

    private int lifesteal(Player p)
    {
        double dmgHeal = 0.2; //Jaka czesc dmg zostala przywrocna w postaaci hp
        int totalHeal = (int) Math.ceil(this.dmg*dmgHeal);

        if(p.getHp() + totalHeal <= p.getMaxHp())
        {
            p.setHp((int)(p.getHp()+totalHeal));
        } else {
            p.setHp(p.getMaxHp());
        }
        System.out.println("You healed yourself for "+(totalHeal)+" HP.");
        return 0;
    }

    private int doubleAttack(int chance)
    {
        Random rand = new Random();
        int n = f.rand(0,100);
        if(chance>=n) //chance to ogolnie szansa na doubleAttack
        {
            System.out.println("Double attack!");
            return (int)(this.dmg*2);
        }
        return 0;
    }

    private int bonusDmg(Enemy e, double percent, String race) //percent must be defined as 0.percentage, i.e. 0.2 = 20%
    {
        if(e.getRace()==race)
        {
            return (int)(dmg*percent);
        }
        return 0;
    }
}
