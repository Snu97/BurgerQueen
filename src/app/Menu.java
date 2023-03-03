package app;

import app.product.Product;
import app.product.subproduct.Hamburger;
import app.product.subproduct.Side;
import app.product.subproduct.Drink;

public class Menu {
    private Product[] products;

    public Menu(Product[] products) {
        this.products = products;
    }

    public void printMenu(){
        System.out.println("[\uD83D\uDD3B] 메뉴");
        System.out.println("-".repeat(60));

        printHamburgers(true);
        printSide(true);
        printDrink(true);

        System.out.println();
        System.out.println("🧺 (0) 장바구니");
        System.out.println("📦 (+) 주문하기");
        System.out.println("-".repeat(60));
        System.out.print("[📣] 메뉴를 선택해주세요 : ");
    }

    protected void printDrink(boolean printPrice) {
        System.out.println("\uD83E\uDD64 음료");
        for(Product product : products)
            if(product instanceof Drink)
                printEachMenu(product, printPrice);
        System.out.println();
    }

    protected void printSide(boolean printPrice) {
        System.out.println("\uD83C\uDF5F 사이드");
        for(Product product : products)
            if(product instanceof Side)
                printEachMenu(product, printPrice);
        System.out.println();
    }

    protected void printHamburgers(boolean printPrice) {
        System.out.println("\uD83C\uDF54 햄버거");
        for (Product product : products)
            if (product instanceof Hamburger)
                printEachMenu(product, printPrice);
        System.out.println();
    }

    private static void printEachMenu(Product product, boolean printPrice) {
        if(true)
            System.out.printf("   (%d) %s %5dKcal %5d원\n",
                product.getId(), product.getName(), product.getKcal(), product.getPrice());
        else System.out.printf("   (%d) %s %5dKcal\n",
                product.getId(), product.getName(), product.getKcal());
    }
}
