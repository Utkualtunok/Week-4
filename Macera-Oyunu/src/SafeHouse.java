public class SafeHouse extends NormalLoc{
    public SafeHouse(Player player) {
        super(player, "Güvenli Ev");
    }

    @Override
    public boolean onLocation() {
        //Son olarak kullanıcı tüm ödülleri kazanıp güvenli eve geldiğinde envanterinde tüm ödüller var ise oyunu bitiriyor
        if (this.getPlayer().getInventory().isFood() && this.getPlayer().getInventory().isWater() && this.getPlayer().getInventory().isFirewood()){
            System.out.println();
            System.out.println("Tebrikler oyunu kazandınız.");
            return false;
        }
        System.out.println("Güvenli Evdesiniz.");
        System.out.println("Canınız Yenilendi.");
        this.getPlayer().setHealth(this.getPlayer().getOrjinalHealth());
        return true;
    }
}
