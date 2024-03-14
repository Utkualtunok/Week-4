public class ToolStore extends NormalLoc{
    public ToolStore(Player player) {
        super(player, "Mağaza");
    }
    @Override
    public boolean onLocation() {
        System.out.println("---------- Mağazaya Hoşgeldiniz ! ------------");
        boolean showMenu = true;
       while (true){
           System.out.println("1- Silahlar ");
           System.out.println("2- Zırhlar ");
           System.out.println("3- Çıkış ");
           System.out.println("Seçiminiz :  ");
           int selectCase =  Location.input.nextInt();
           while (selectCase < 1 || selectCase > 3){
               System.out.print("Geçersiz değer tekrar giriniz : ");
               selectCase = input.nextInt();
           }
           switch (selectCase){
               case 1:
                   printWeapon();
                   buyWeapon();
                   break;
               case 2:
                   printArmor();
                   buyArmor();
                   break;
               case 3:
                   System.out.println("Güle güle!");
                   showMenu = false;
                   break;

           }
           return true;
       }
    }

    public void printWeapon(){
        System.out.println("----------- Silahlar ------------");
        for (Weapon w : Weapon.weapons()){
            System.out.println(w.getId()+ " - " +w.getName() +
                    " <Para : "+ w.getMoney() + ", Hasar: " + w.getDamage() + ">");
        }
    }
    public void buyWeapon(){
        System.out.println("Bir silah seçiniz : ");
        int selectWeaponId = input.nextInt();
        while (selectWeaponId < 1 || selectWeaponId > Weapon.weapons().length){
            System.out.println("Geçersiz değer, tekrar giriniz : ");
            selectWeaponId = input.nextInt();
        };

        Weapon selectedWeapon = Weapon.getWeaponObjById(selectWeaponId);
        if (selectedWeapon != null){
            if (selectedWeapon.getMoney() > this.getPlayer().getMoney()){
                System.out.println("Yeterli paranız bulunmamaktadır.");
            }else {
                System.out.println(selectedWeapon.getName() + " Silahını satın aldınız.");
                int balance = this.getPlayer().getMoney() - selectedWeapon.getMoney();
                this.getPlayer().setMoney(balance);
                System.out.println("Kalan paranız : " + this.getPlayer().getMoney());
                System.out.println("Önceki silahınız : " + this.getPlayer().getInventory().getWeapon().getName());
                this.getPlayer().getInventory().setWeapon(selectedWeapon);
                System.out.println("Yeni silahınız : " + this.getPlayer().getInventory().getWeapon().getName());

            }
        }
    }
    public void printArmor(){
        System.out.println("----------- Zırhlar -------------");
        for (Armor a : Armor.armors()){
            System.out.println(a.getId()+ " - " +a.getName() +
                    " <Para : "+ a.getMoney() + ", Zırh: " + a.getBlock() + ">");
        }
    }
    public void buyArmor(){
        System.out.println("Bir zırh seçiniz : ");
        int selectArmorId = input.nextInt();
        while (selectArmorId < 1 || selectArmorId > Armor.armors().length){
            System.out.println("Geçersiz değer, tekrar giriniz : ");
            selectArmorId = input.nextInt();
        }

        Armor selectedArmor = Armor.getArmorObjById(selectArmorId);
        if (selectedArmor != null){
            if (selectedArmor.getMoney() > this.getPlayer().getMoney()){
                System.out.println("Yeterli paranız bulunmamaktadır.");
            }else {
                System.out.println(selectedArmor.getName() + "Zırhını satın aldınız.");
                int balance = this.getPlayer().getMoney() - selectedArmor.getMoney();
                this.getPlayer().setMoney(balance);
                System.out.println("Kalan paranız : " + this.getPlayer().getMoney());
                System.out.println("Önceki zırhınız : " + this.getPlayer().getInventory().getArmor().getName());
                this.getPlayer().getInventory().setArmor(selectedArmor);
                System.out.println("Yeni zırhınız : " + this.getPlayer().getInventory().getArmor().getName());
            }
        }
    }
}
