public class Armor {
    private int id;
    private int block;
    private int money;
    private String name;

    public Armor(int id,String name, int block, int money) {
        this.id = id;
        this.name = name;
        this.block = block;
        this.money = money;

    }
    public static Armor[] armors(){
        Armor[] armorList = new Armor[3];
        armorList[0] = new Armor(1,"Hafif",1,15);
        armorList[1] = new Armor(2,"Orta",3,25);
        armorList[2] = new Armor(3,"Ağır",5,40);
        return armorList;
    }
    public static Armor getArmorObjById(int id){
        for (Armor a : Armor.armors()){
            if (a.getId() == id){
                return a;
            }
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBlock() {
        return block;
    }

    public void setBlock(int block) {
        this.block = block;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
