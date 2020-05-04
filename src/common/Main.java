package common;
import java.security.Key;
import java.util.Scanner;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Items.*;
import characters.Enemy;
import characters.Player;


public class Main //implements KeyListener
{

	public static void m(String msg) { System.out.println(msg); }


    public static void main(String args[]) {

        Scanner skn = new Scanner(System.in);
        String input, ei; //ei - empty input, for PRESS ENTER TO CONITNUE
        Functions f = new Functions();

        boolean running = true; //breaks if player.hp == 0;
        boolean fighting = true;
        
        int rCleared = 0; //Rooms cleared - used for checking whether
		// you s "\nWelcome to the Dungeon!\nYour task is to survive as long as possible.\nEvery 6 enemies there will be an"
		//        		+ " item shop, where you can buy new equipment for youhould enter item shop and for increasing the event pool
        
        String welcomeMessage = 
        		"alpha v0.72\n"
        		+"Welcome to the dungeon!"
        		+"\nPress ENTER to continue...\n";

        //0.73
		/*
		Zmieniono w jaki sposob obliczany jest dmg gracza - teraz jest to (dmg broni + dmg gracza - 5)+liczba od 1 do 5
		* */
        
//-------------------------------------------------------------------------------------------------------------------------
        m(welcomeMessage);
        Player p = new Player();
		ItemList it = new ItemList();

		//p.foundCoins(100);

        //Hp potion healing for 25 health points
        Potion hpPot = new Potion(15, "Healing potion", "heal", 30);

		ei = skn.nextLine();
        
        GAME:
        while(running)
        {
			if(p.getHp()<=0) { running = false; continue GAME; }
        	//Nizej kod na level upa
			if(p.getXp()>=p.getXpToLevel())
			{
				p.levelUp();
				m("You feel more experienced.");
				m("Current level: "+p.getLevel());

				ei = skn.nextLine();

				m("Choose your upgarde:");
				m("\t 1. +5 DMG");
				m("\t 2. +20 HP");
				m("\t 3. +40 ENG, faster regen");

				m("Current stats: DMG: "+p.getDmg()+", HP: "+p.getMaxHp()+", ENG: "+p.getMaxEng());

				boolean choosing = true;
				while(choosing)
				{
					String choice = skn.nextLine();
					if(p.checkLevelUpgrade(choice))
					{
						choosing = false;
						ei = skn.nextLine();
					}
				}
			}

			int eventChance = f.rand(0, 100);
			int eventPercent = 15; //Prawdziwa szansa na event, to wyżej to losowanko przy każdym nowym roomie

			Enemy e1 = new Enemy(rCleared); //generowanie losowego przeciwnika
        	//Nizej sprawdzanie czy ma odbyc sie walka lub inny event
        	if( (!(p.isNextRoomAnEvent) || (eventChance > eventPercent)) && (rCleared%6 != 0 || rCleared == 0) ) // 15% szansy, ze odbedzie sie event. Pozostale 85% to walka, chyba, ze gracz wchodzi do sklepu
        	{
	        	fighting = true;

				m("You encounter an enemy!");
	            e1.show();
	            //m(String.valueOf(rCleared));
        	}

        	if(rCleared%6 == 0 && rCleared != 0) //co 6 pokoi wejdz do item shopa
        	{ 		// Tu jakiegos loopa trzeba zrobic
        		fighting = false;
        		Boolean shopping = true;

        		m("You enter the item shop."); // SKLEPIK --------------------------------------------------------
        		ei = skn.nextLine();
				it.generateList();

				SHOPPING:
        		while(shopping)
				{
					it.showList(p);
					input = skn.nextLine();
					String exitNum = Integer.toString(it.getCount());

					if(input.equals(exitNum)) { shopping = false; break SHOPPING; }

					if(input.equals("1")) it.askToBuy(1, skn, p);
					if(input.equals("2")) it.askToBuy(2, skn, p);
					if(input.equals("3")) it.askToBuy(3, skn, p);
					if(input.equals("4")) it.askToBuy(4, skn, p);
					if(input.equals("5")) it.askToBuy(5, skn, p);


					//domyslnie 5.
					//exitNum to liczba ktora sie printuje przy X. Exit the shop -> ostatnia opcja w sklepie
				}

				//m("Error 404: itemShop.java not found. You require \"Item Shop DLC\" to enter this area. Press Enter to continue.");
				m("You leave the shop and head deeper into the dungeon. Additionaly,");
        		p.regEng();

        		rCleared++;
        		ei = skn.nextLine();
        	}

        	if(eventChance <= eventPercent && p.isNextRoomAnEvent)//EVENCIKI HERE MY PEOPLE!
			{
				//eventy
				fighting = false;
				p.isNextRoomAnEvent = false; // Zeby nie bylo 2 eventow pod rzad
				//To znaczy, ze MOZE byc eventem a nie, ze NA PEWNO BEDZIE eventem.

				int eventType = f.rand(1,5);
				switch (eventType)
				{
					case 1:
						// Empty room
						m("You enter the room but to your suprise there is nothing here");
						m("except an old bookcase. You search through it but find nothing");
						m("worthy of intrest.");
						break;

					case 2:
						// Bandit Leader fight
						m("You entered a strange room. You look around and realise it is a bandit camp.");
						m("One of the bandits notices you and starts running towards you.\nIt's their leader himself!");
						int genHp = (60+rCleared/2); // Zmienna wygenerowana na potrzeby zwiekszania poziomu trudnosci
						int genDmg = (15+rCleared/6);
						e1 = e1.createCustomEnemy("Bandit Leader","Human",genHp,genDmg, 60);
						fighting = true;
						break;

					case 3:
						m("You stumble upon an empty room with nothing but a chest inside. You approach it and inspect it.");
						m("It is quite old but looks solid. You open it and find some gold coins.");
						int howMany = f.rand(16,25);
						p.foundCoins(howMany);
						System.out.println("You found "+howMany+" gold coins.");
						break;

					case 4:
						m("You enter an old, dark chamber. After a moment you realise It is a traproom!");
						m("You ran as fast as you can but, unfortunetly, you got hit by a spike trap.");
						e1 = e1.createCustomEnemy("", "", 0, 15, 0); // Hard coding mocno
						p.recDmg(e1);
						break;
					case 5:
						m("You came across a fountain with pure water. When you put your hand in the water");
						m("you saw a small cut on your hand healing completely. It is a healing fountain.");
						p.healFull();
						break;
				}


				ei = skn.nextLine();
				rCleared++;
			} // KONIEC EVENTOW ----------------------------------
        	
        	FIGHTING:
            while(fighting)
            {
				if(p.getHp()<=0) { running = false; continue GAME; }
            	//Your HP: x, ENERGY: y, Rooms cleared: y
            	m( "Your HP: "+p.getHp()+", ENERGY: "+p.getEng());
            	
            	//ENEMY's HP: xyz
            	m(e1.getType()+"'s HP: "+e1.getHp()+" ATT: "+e1.getDmg());
            	
            	m("\t1. Attack");
            	m("\t2. Rest to regain energy");
            	m("\t3. Drink HP potion");
            	m("\t4. Stats");

	            input = skn.nextLine();

	            //if(input.equals("1"))
				if(input.contentEquals("1"))
	            {
	            	boolean stillFighting = playerAttack(skn, p, e1, ei, running, rCleared);
	            	if(stillFighting) continue FIGHTING;
	            	else // czyli jezeli playerAttack = false
	            	{
	            		rCleared++;
	            		continue GAME;
	            	}
	            }
	
	            if(input.equals("2"))
	            {
	            	p.regEng(); //odnow x energii w zamian za pominiecie tury
	            	p.recDmg(e1); //otrzymaj obrazenia ze pominiecie tury
	            	if(p.getHp()<=0) //JEZELI HP GRACZA < 0 = KONIEC GRY
	            	{
	            		m("You recieved lethal damage.");
	            		running = false;
	            		ei = skn.nextLine();
	            		continue GAME;
	            	}
	            	ei = skn.nextLine();
	            	continue FIGHTING;
	            }
	
	            if(input.equals("3")) //PICIE POTKI
	            {
	            	hpPot.drink(p, input, skn, ei);
	            	continue FIGHTING;
	            }

	            if(input.equals("4")) //STATY
				{
					m("Attack: "+p.getDmg());
					m("HP: "+p.getHp()+"/"+p.getMaxHp());
					System.out.print("Current weapon: ");
					p.getWeapon().show(); //kontynuacja powyzszej linijki

					m("Level: "+p.getLevel());
					m("Xp: "+p.getXp()+"/"+p.getXpToLevel());
					System.out.print("\n");
					m("Gold coins: "+p.getCoins());
					m("Number of potions: "+p.getPotionNum());
					m("Rooms cleared: "+rCleared);
					ei = skn.nextLine();
				}

	            else { m("");} //If input is invalid do nothing
            } //Outside fighting loop
        	
        } //Outside running loop
        skn.close();
        m("YOU DIED");
        System.out.println("Your score: "+rCleared+" rooms cleared.");
        
    } //Outside main()


    
    public static boolean playerAttack(Scanner skn, Player p, Enemy e1, String ei, boolean running, int rCleared)
	{
    	Functions f = new Functions();
    	
    	if(p.getEng() >= p.getAttCost()) //Jesli ENERERGIA gracza nie jest mniejsza od KOSZTU ATAKU to ATAKUJ
    	{
			int bDmg = p.checkWeaponBonus(p.getWeapon(), e1, p); //Checkin weapon bonus - lifesteal, doubleAttack, bonusDmg etc.
			int dmg = p.calcDmg()+bDmg; //LOSOWANIE DMG GRACZA

	    	e1.recDmg(dmg); //ODJECIE DMG OD HP PRZECIWNIKA
	    	p.setEng(p.getEng()-p.getAttCost()); //USTAWIA AKTUALNA ENERGIE GRACZA POMNIEJSZONA O KOSZT ATAKU

	    	m("You dealt "+dmg+" points of damage!");
    	} else {
    		m("Your energy is too low to perform this move!");
    		return true;
    	}
    	
    	if(e1.getHp()<=0) //JEZELI HP przeciwnika SPADNIE DO ZERA
    	{
			p.isNextRoomAnEvent = true; //Po zakonczonej walce kolejny pokoj moze znowu byc eventem.

    		m(e1.getType()+" is dead!");

    		p.recXp(e1.getXpValue());
    		m("You gained "+e1.getXpValue()+" experience points."); //EXP miedzy 20-30 + rCleared/2

    		int dropPotion = f.rand(1,100); //SZANSA NA POTKE NIZEJ
    		if(dropPotion<=30)
    		{
    			m("You found a healing potion.");
    			p.foundPotion(1);
    		}

			int dropCoins = f.rand(1,100);
			if(dropCoins<=75)
			{
				int amnt = f.rand(5,12);
				m("You found some coins. ("+amnt+")");
				p.foundCoins(amnt);
			}

			if(e1.getType().equals("Bandit Leader")) // ZABICIE BANDIT LEADERA
			{
				//co ma sie stac jak zabijesz Bandit Leadera

				ei = skn.nextLine();
				int input;
				boolean choosing = true;

				int swordDmg = (f.rand(5, 8)+15+rCleared/3);
				Weapon banditLSword = new Weapon(100, "Bandit Sword", swordDmg, "");

				m("Ringleader lies on the ground in a puddle of his own blood.");
				m("You pick up his weapon and inspect it: "); banditLSword.show();
				m("Do you want to take it?");
				System.out.println("\t1. Yes\n\t2. No");
				input = skn.nextInt();
				while(choosing)
				{
					if(input==1)
					{
						p.changeWeapon(banditLSword);
						m("You take his weapon and leave the room.");
						choosing = false;
					}
					if(input==2)
					{
						m("You leave his dead body alone and leave the room");
						choosing = false;
					}
				}
				ei = skn.nextLine();
			}

    		ei = skn.nextLine(); //Pusty input, zeby zatwierdzic kontynuacje enterem
    		return false; //Wyjscie z while(fighting)
    	}
    	p.recDmg(e1); //JEZELI PRZECIWNIK ZYJE, OTRZYMAJ OBRAZENIA
    	if(p.getHp()<=0) //JEZELI HP GRACZA < 0 = KONIEC GRY
    	{
    		m("You recieved lethal damage.");
    		running = false;
    		ei = skn.nextLine();
    		return false;

    	}
		ei = skn.nextLine(); //PUSTY INPUT
		return true;
	}
    
} //Outside Class Main.java
