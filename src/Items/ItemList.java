package Items;

import common.Functions;
import characters.Player;
import Items.*;

import java.util.Scanner;

public class ItemList {

    Functions f = new Functions(); //f.rand(1, 100);
    Item[] it = new Item[]
            {
                    //new Item(10, "cos"),
                    //new Item(10, "cos"),
                    //new Potion(30, "Strength potion", "incDmg", 5),
                    new Item(0, "Item bought"), // NIE USUWAC!

                    new Weapon(20, "Iron dagger", 15, "Animal"),

                    new Weapon(28, "Iron sword", 25, ""),
                    new Weapon(28, "Iron sword", 25, ""),

                    new Weapon(33, "Assassin daggers", 22, "doubleAttack"),

                    new Weapon(40, "Iron spear", 35, ""),

                    new Weapon(50, "Vampire Dagger", 25, "lifesteal"),

                    new Weapon(60, "Steel mace", 42, "Human"),

                    new Weapon(80, "Holy Claymore", 47, "Undead"),

                    new Weapon(120, "Night's Edge", 55, ""),

                    new Weapon(150, "Poisoned Blade", 58, "doubleAttack"),

                    new Potion(15, "Healing potion", "heal", 25),
                    new Potion(15, "Healing potion", "heal", 25),
                    new Potion(15, "Healing potion", "heal", 25),

                    new Potion(50, "Energy potion", "incEng", 40),

                    new Potion(40, "Strength potion", "incDmg", 5),
                    new Potion(70, "Strength essence", "incDmg", 10)

            };

    private int count;
    int[] num = { 0, 0, 0, 0, 0 }; //Dodaj zero za kazdy nowy itemek (zmien itemAmnt)
    int itemAmnt = 5;

    public void generateList()
    {
        for(int i=0; i<itemAmnt; i++) //Prints 5 items
        {
            int k = f.rand(0, it.length-1);
            if(k==0) k += 1; //Na pozycji ZERO jest item sold
            num[i] = k;
            //talblica num przechowuje, ktory numerek z pozycji Item[] zostal wylosowany
        }


    }

    public void showList(Player p) //num[1] = 5; it[5]
    {
        count = 1;

        System.out.println("\"How can i help you?\" Merchant says.");
        for(int i=0; i<itemAmnt; i++) //za kazego inta w tablicy num wykonaj ponizsze polecenie:
        {
            System.out.print(count+". ");
            it[num[i]].show();
            count++;
        }
        System.out.print( (count) + ". Exit the shop \n\n" );
        System.out.println("You have "+p.getCoins()+" gold coins. \n");
    }

    public void askToBuy(int index, Scanner skn, Player p)
    {
        String input;
        Item chosenItem = it[num[index-1]]; // Item wybrany przez gracza na podstawie index

        if(chosenItem.getValue() == 0) return; //Jesli item zostal zakupiony to nic nie rob

        if(p.getCoins() < chosenItem.getValue()) // Sprawdza na start czy masz kase
        {
            System.out.println("You cannot afford it.");
            input = skn.nextLine();
            return;
        }

        //it[num[index-1]].show(); // o kurde
        chosenItem.show();

        System.out.println("Are you sure you want to buy it?\n\t1. Yes\n\t2. No, I changed my mind");
        input = skn.nextLine();

        if (input.equals("1")) {

            switch(chosenItem.getClassString())
            {
                case "Weapon":
                    Weapon i1 = (Weapon) chosenItem;
                    p.changeWeapon(i1);
                    break;

                case "Potion":

                    Potion i2 = (Potion) chosenItem;
                    //What kind of potion?

                    if ( (i2.getEffect().equals("heal")) ) {
                        p.foundPotion(1);
                    }
                    else if ( i2.getEffect().equals("incDmg") ) {
                        p.addStr(i2.getHowMuch());
                    }
                    else if ( i2.getEffect().equals("incEng") ) {
                        p.addEng(40);
                        p.engUpgrade();
                    }
                    break;
            }

            p.pay(it[num[index - 1]].getValue()); //Plac ze item
            num[index-1] = 0; //Itemek sprzedany - na pozycji zero jest ITEM SOLD na liscie
            //Ogolnie te rozwiazanie nie jest dobre

            System.out.println("Item bought");
            System.out.println("Remaining coins: " + p.getCoins());

            input = skn.nextLine(); // Enter aby kontynuowac
            return;
        }

        else if(input.equals("2")) return;
        else
        {
            System.out.println("Wrong input\n");
            this.askToBuy(index, skn, p);
        }


    }

    public int getCount() { return this.count; }

    public Item getItem(int index) { return it[index]; }
    //czyli jesli 1. Iron sword to oznacza, ze w tabeli ma numerek 0
    //dlatego index-1
}
