package Items;

public class Item {
    private int value;
    private String name;

    public void show()
    {
        if(this.value > 0) System.out.println(name+", value: "+value);
        else System.out.println("----Item sold----");
    }

    public Item(int value, String name)
    {
        this.value = value;
        this.name = name;

    }

    public int getValue() { return value; }
    public String getName() { return name; }
    public String getClassString() { return "Item"; }

}
