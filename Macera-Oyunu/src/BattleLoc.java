import java.sql.SQLOutput;
import java.util.Random;

public class BattleLoc extends Location{
    private Monster monster;
    private String award;
    private int maxMonster;
    private static Random random = new Random();
    public BattleLoc(Player player, String name, Monster monster, String award, int maxMonster) {
        super(player, name);
        this.monster = monster;
        this.award = award;
        this.maxMonster = maxMonster;
    }
    public int randomMonsterNumber(){
        Random r = new Random();
        return r.nextInt(this.getMaxMonster()) + 1;

    }

    public int getMaxMonster() {
        return maxMonster;
    }

    public void setMaxMonster(int maxMonster) {
        this.maxMonster = maxMonster;
    }

    public Monster getMonster() {
        return monster;
    }

    public void setMonster(Monster monster) {
        this.monster = monster;
    }

    public String getAward() {
        return award;
    }

    public void setAward(String award) {
        this.award = award;
    }

    @Override
    public boolean onLocation() {
        int monsterNumber = this.randomMonsterNumber();
        System.out.println("Şu an buradasınız : " + this.getName());
        System.out.println("Dikkatli ol burada " + monsterNumber + " tane " + this.getMonster().getName() + " yaşıyor.");
        System.out.println("<S>avaş veya <K>aç");
        String selectCase = input.nextLine();
        selectCase = selectCase.toUpperCase();
        if (selectCase.equals("S") && combat(monsterNumber)){
            System.out.println(this.getName() + " düşmanlarını yendiniz !");

            //Gelen ödülün envanterde olma durumu kontrol ediliyor ve yoksa envantere ekleniyor
            if (this.getAward().equals("Food") && !this.getPlayer().getInventory().isFood()){
                System.out.println(this.award + " Kazandınız! ");
                this.getPlayer().getInventory().setFood(true);
            } else if (this.getAward().equals("Water") && !this.getPlayer().getInventory().isWater()) {
                System.out.println(this.award + " Kazandınız! ");
                this.getPlayer().getInventory().setWater(true);
            } else if (this.getAward().equals("Firewood") && !this.getPlayer().getInventory().isFirewood()) {
                System.out.println(this.award + " Kazandınız! ");
                this.getPlayer().getInventory().setFirewood(true);
            }
            if (this.getMonster().getName().equals("Yılan")){
                rewardAfterCombat();
            }
            return true;
        }
        if (this.getPlayer().getHealth() <= 0 ){
            System.out.println("Öldünüz!");
            return false;
        }
        return true;
    }
    public boolean combat(int monsterNumber){
        for (int i = 1; i<= monsterNumber; i++){
            this.getMonster().setHealth(this.getMonster().getOrjinalHealth());
            playerStats();
            monsterStats(i);
            while (this.getPlayer().getHealth() > 0 && this.getMonster().getHealth() > 0){
                int firstAttack = random.nextInt(10) + 1;
                System.out.println("<V>ur veya <K>aç");
                String selectCombat = input.nextLine();
                selectCombat = selectCombat.toUpperCase();
                if (selectCombat.equals("V")){
                    if (firstAttack > 5){
                        System.out.println("Siz vurdunuz !");
                        this.getMonster().setHealth(this.getMonster().getHealth() - this.getPlayer().getTotalDamage());
                        afterHit();
                        if (this.getMonster().getHealth() > 0){
                            System.out.println();
                            System.out.println("Canavar size vurdu !");
                            int monsterDmg = this.getMonster().getDamage() - this.getPlayer().getInventory().getArmor().getBlock();
                            if (monsterDmg < 0){
                                monsterDmg = 0;
                            }
                            this.getPlayer().setHealth(this.getPlayer().getHealth() - monsterDmg);
                            afterHit();
                        }
                    } else {
                        System.out.println("Canavar size vurdu !");
                        int monsterDmg = this.getMonster().getDamage() - this.getPlayer().getInventory().getArmor().getBlock();
                        if (monsterDmg < 0){
                            monsterDmg = 0;
                        }
                        this.getPlayer().setHealth(this.getPlayer().getHealth() - monsterDmg);
                        afterHit();
                        if (this.getPlayer().getHealth() > 0){
                            System.out.println("Siz vurdunuz !");
                            this.getMonster().setHealth(this.getMonster().getHealth() - this.getPlayer().getTotalDamage());
                            afterHit();
                        }
                    }
                } else {
                    return false;
                }
            }
            if (this.getMonster().getHealth() < this.getPlayer().getHealth()){
                if (!getMonster().getName().equals("Yılan")){
                    System.out.println("Düşmanı yendiniz.");
                    System.out.println(this.getMonster().getAward() + "Para kazandınız.");
                    this.getPlayer().setMoney(this.getPlayer().getMoney() + this.getMonster().getAward());
                    System.out.println("Güncel paranız : " + this.getPlayer().getMoney());
                    System.out.println();
                } else {
                    System.out.println();
                }
            } else {
                return false;
            }
        }
        return true;
    }
    //Maden'e girildiğinde yılanlardan düşecek itemlerin yüzdesel dağılımı
    public void rewardAfterCombat(){
        int armorChance = random.nextInt(100) + 1;
            if (armorChance <= 15) { // %15 ihtimal Armor
                int lightweightArmorChance = random.nextInt(100) + 1;
                if (lightweightArmorChance <= 50) {
                    Armor armor = Armor.getArmorObjById(1);
                    this.getPlayer().getInventory().setArmor(armor);
                } else if (lightweightArmorChance <= 80) {
                    Armor armor = Armor.getArmorObjById(2);
                    this.getPlayer().getInventory().setArmor(armor);
                } else {
                    Armor armor = Armor.getArmorObjById(3);
                    this.getPlayer().getInventory().setArmor(armor);
                }
            } else if (armorChance <= 30) {
                int weaponChance = random.nextInt(100) + 1;
                if (weaponChance <= 20) { // %20 ihtimal
                    // Tüfek kazanma işlemi
                    Weapon weapon = Weapon.getWeaponObjById(3);
                    this.getPlayer().getInventory().setWeapon(weapon);
                } else if (weaponChance <= 50) { // %30 ihtimal
                    // Kılıç kazanma işlemi
                    Weapon weapon = Weapon.getWeaponObjById(2);
                    this.getPlayer().getInventory().setWeapon(weapon);
                } else { // %50 ihtimal
                    // Tabanca kazanma işlemi
                    Weapon weapon = Weapon.getWeaponObjById(1);
                    this.getPlayer().getInventory().setWeapon(weapon);
                }
            } else if (armorChance <= 55) {
                int moneyChance = random.nextInt(100) + 1;
                if (moneyChance <= 20) { // %20 ihtimal
                    // 10 Para kazanma işlemi
                    this.getPlayer().setMoney(this.getPlayer().getMoney() + 10);
                } else if (moneyChance <= 50) { // %30 ihtimal
                    // 5 Para kazanma işlemi
                    this.getPlayer().setMoney(this.getPlayer().getMoney() + 5);
                } else { // %50 ihtimal
                    // 1 Para kazanma işlemi
                    this.getPlayer().setMoney(this.getPlayer().getMoney() + 1);
                }
            } else {
                System.out.println("Hiçbir şey kazanamadınız.");
            }

    }
    public void playerStats(){
        System.out.println("Oyuncu değerleri");
        System.out.println("------------------------------");
        System.out.println("Sağlık : " + this.getPlayer().getHealth());
        System.out.println("Silah : " + this.getPlayer().getWeapon().getName());
        System.out.println("Zırh : " + this.getPlayer().getArmor().getName());
        System.out.println("Bloklama : " + this.getPlayer().getArmor().getBlock());
        System.out.println("Hasar : " + this.getPlayer().getTotalDamage());
        System.out.println("Para : " + this.getPlayer().getMoney());
        System.out.println();
    }
    public void monsterStats(int i){
        System.out.println(i + ". " +this.getMonster().getName() + " Değerleri ");
        System.out.println("------------------------------");
        System.out.println("Sağlık : " + this.getMonster().getHealth());
        System.out.println("Hasar : " + this.getMonster().getDamage());
        System.out.println("Ödülü : " + this.getMonster().getAward());
        System.out.println();
    }
    public void afterHit(){
        System.out.println("Canınız : " + this.getPlayer().getHealth());
        System.out.println(this.getMonster().getName() +" Canı : " + this.getMonster().getHealth());
        System.out.println();
    }
}
