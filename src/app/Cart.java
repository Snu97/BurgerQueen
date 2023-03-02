package app;

import app.product.Product;
import app.product.subproduct.*;


import java.sql.SQLOutput;
import java.util.Scanner;

public class Cart {
    private Product[] items = new Product[0];
    private Scanner sc = new Scanner(System.in);

    public void printCart(){
        System.out.println("🧺 장바구니");
        System.out.println("-".repeat(60));

        printCartItemDetails();

        System.out.println("-".repeat(60));
        System.out.printf("합계 : %d원\n", 4);

        System.out.println("이전으로 돌아가려면 엔터를 누르세요. ");
        sc.nextLine();
    }

    private void printCartItemDetails(){
        for(Product product:items){
            if(product instanceof BurgerSet){
                BurgerSet burgerSet = (BurgerSet) product;
                System.out.printf(
                        "  %s %6d원 (%s(케첩 %d개), %s(빨대 %s))\n",
                        burgerSet.getName(), burgerSet.getPrice(), burgerSet.getSide().getName(), burgerSet.getSide().getKetchup(),
                        burgerSet.getDrink().getName(), burgerSet.getDrink().hasStraw() ? "있음" : "없음");
            } else if (product instanceof Hamburger){
                Hamburger hamburger = (Hamburger) product;
                System.out.printf(
                        "  %-8s %6d원 (단품)\n", hamburger.getName(), hamburger.getPrice()
                );

            } else if (product instanceof Side){
                System.out.printf("  %-8s %6d원 (케첩 %d개)", product.getName(), product.getPrice(), ((Side) product).getKetchup());

            } else if (product instanceof Drink){
                System.out.printf("  %-8s %6d원 (케첩 %d개)", product.getName(), product.getPrice(), ((Drink) product).hasStraw() ? "있음" : "없음");
            }
        }
    }

    private int calculateTotalPrice(){
        int totalPrice = 0;
        for(Product product : items){
            totalPrice += product.getPrice();
        }
        return totalPrice;
    }

    public void addToCart(int productId){

        

    }
}

