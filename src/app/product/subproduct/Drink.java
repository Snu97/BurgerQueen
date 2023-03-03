package app.product.subproduct;

import app.product.Product;

public class Drink  extends Product {
    private boolean straw;

    public Drink(int id, String name, int price, int kcal, boolean straw) {
        super(id, name, price, kcal);
        this.straw = straw;
    }

    public Drink(Drink drink) {
        super(drink.getName(), drink.getPrice(), drink.getKcal());
        this.straw = drink.hasStraw();
    }

    public boolean hasStraw() {
        return straw;
    }

    public void setStraw(boolean straw) {
        this.straw = straw;
    }
}
