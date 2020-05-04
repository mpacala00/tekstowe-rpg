package Items;
import characters.Player;
import Items.Item;

import java.util.Scanner;

public class Potion extends Item{

    private String effect;
    private int howMuch;

    public Potion(int value, String name, String effect, int howMuch)
    {
        super(value, name);
    	this.effect = effect;
    	this.howMuch = howMuch;
    }
    
    public String getEffect() { return this.effect; }
    public int getHowMuch() { return this.howMuch; } // Ilosc odnawianego hp/ dodanej sily

    //Zapobiega odpalenia funkcji heal w przypadku potionow na sile itp
    public void drink(Player player, String input, Scanner skn, String ei)
    {
        if(this.effect.equals("heal"))
        {
            this.drinkHealing(player, input, skn, ei);
        }
    }
    private void drinkHealing(Player player, String input, Scanner skn, String ei)
    {
        boolean inAction = true;
        if(player.getPotionNum()>0)
        {
            System.out.println("You have "+player.getPotionNum()+" healing potions.");
            System.out.println("Do you want to drink one?");
            System.out.println("\t1. Yes");
            System.out.println("\t2. No");

            while(inAction)
            {
                input = skn.nextLine();
                if(input.equals("1"))
                {
                    player.setHp(player.getHp()+this.getHowMuch());
                    player.drinkPotion();
                    if(player.getHp()>player.getMaxHp()) player.setHp(player.getMaxHp());
                    System.out.println("You regained "+this.getHowMuch()+" HP.");
                    ei = skn.nextLine();
                    return;
                }
                else if(input.equals("2")) return;
            }
        } else {
            System.out.println("You don't have any healing potions!");
        }
    }

    public String getClassString() { return "Potion"; }

    public void show()
    {
        String bonusText;
        bonusText = "";
        if(this.effect.equals("heal")) bonusText = "heals for "+this.howMuch+" hp";
        if(this.effect.equals("incDmg")) bonusText = "Increases player's damage by "+this.howMuch+" DMG";
        if(this.effect.equals("incEng")) bonusText = "Increases max ENG by "+howMuch+", faster ENG regen.";
        System.out.print(getName()+", value: "+getValue());
        System.out.println(" | "+bonusText);
    }

}
