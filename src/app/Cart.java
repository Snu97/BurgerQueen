package app;

import app.product.Product;
import app.product.ProductRepository;
import app.product.subproduct.*;


import java.sql.SQLOutput;
import java.util.Scanner;

public class Cart {
    private Product[] items = new Product[0];
    private Scanner sc = new Scanner(System.in);
    private ProductRepository productRepository = new ProductRepository();
    private Menu menu;

    public Cart(ProductRepository productRepository, Menu menu) {
        this.productRepository = productRepository;
        this.menu = menu;
    }

    public void printCart(){
        System.out.println("๐งบ ์ฅ๋ฐ๊ตฌ๋");
        System.out.println("-".repeat(60));

        printCartItemDetails();

        System.out.println("-".repeat(60));
        System.out.printf("ํฉ๊ณ : %d์\n", calculateTotalPrice());

        System.out.println("์ด์ ์ผ๋ก ๋์๊ฐ๋ ค๋ฉด ์ํฐ๋ฅผ ๋๋ฅด์ธ์. ");
        sc.nextLine();
    }

    protected void printCartItemDetails(){
        for(Product product:items){
            if(product instanceof BurgerSet){
                BurgerSet burgerSet = (BurgerSet) product;
                System.out.printf(
                        "  %s %6d์ (%s(์ผ์ฒฉ %d๊ฐ), %s(๋นจ๋ %s))\n",
                        burgerSet.getName(), burgerSet.getPrice(), burgerSet.getSide().getName(), burgerSet.getSide().getKetchup(),
                        burgerSet.getDrink().getName(), burgerSet.getDrink().hasStraw() ? "์์" : "์์");
            } else if (product instanceof Hamburger){
                Hamburger hamburger = (Hamburger) product;
                System.out.printf(
                        "  %-8s %6d์ (๋จํ)\n", hamburger.getName(), hamburger.getPrice()
                );

            } else if (product instanceof Side){
                System.out.printf("  %-8s %6d์ (์ผ์ฒฉ %d๊ฐ)\n", product.getName(), product.getPrice(), ((Side) product).getKetchup());

            } else if (product instanceof Drink){
                System.out.printf("  %-8s %6d์ (๋นจ๋ %s)\n", product.getName(), product.getPrice(), ((Drink) product).hasStraw() ? "์์" : "์์");
            }
        }
    }

    protected int calculateTotalPrice(){
        int totalPrice = 0;
        for(Product product : items){
            totalPrice += product.getPrice();
        }
        return totalPrice;
    }

    public void addToCart(int productId){

        // 2.2.1.
        //Product product = productId๋ฅผ ํตํด productId๋ฅผ id๋ก ๊ฐ์ง๋ ์ํ ์ฐพ๊ธฐ
        Product product = productRepository.findById(productId);



        // 2.2.2.
        //์ํ ์ต์ ์ค์  // chooseOption()
        chooseOption(product);
        // 2.2.3.
        //if (product๊ฐ Hamburger์ ์ธ์คํด์ค์ด๊ณ , isBurgerSet์ด true๋ผ๋ฉด) {
            //product = ์ธํธ ๊ตฌ์ฑ // composeSet();
        //}
        if(product instanceof Hamburger){
            Hamburger hamburger = (Hamburger) product;
            if(((Hamburger) product).isBurgerSet())
                product = composeSet(hamburger);
        }
        Product newProduct;
        if(product instanceof Hamburger) newProduct = new Hamburger((Hamburger) product);
        else if (product instanceof Side) newProduct = new Side((Side) product);
        else if (product instanceof Drink) newProduct = new Drink((Drink) product);
        else newProduct = product;



        // 2.2.4.
        //items์ product ์ถ๊ฐ
        Product[] temp = items;
        items = new Product[items.length+1];
        System.arraycopy(temp, 0, items, 0 , temp.length);
        items[temp.length] = product;
        //"[๐ฃ] XXXX๋ฅผ(์) ์ฅ๋ฐ๊ตฌ๋์ ๋ด์์ต๋๋ค." ์ถ๋ ฅ
        System.out.printf("[\uD83D\uDCE3] %s๋ฅผ(์) ์ฅ๋ฐ๊ตฌ๋์ ๋ด์์ต๋๋ค.\n", product.getName());

    }

    private void chooseOption(Product product){
        String input;
        if(product instanceof Hamburger){
            System.out.printf("๋จํ์ผ๋ก ์ฃผ๋ฌธํ์๊ฒ ์ด์? (1)_๋จํ(%d์) (2)_์ธํธ(%d์)\n", product.getPrice(), ((Hamburger) product).getBurgerSetPrice());
            input = sc.nextLine();
            if(input.equals("2")) ((Hamburger) product).setBurgerSet(true);

        } else if (product instanceof Side) {
            System.out.println("์ผ์ฒฉ์ ๋ช๊ฐ๊ฐ ํ์ํ์ ๊ฐ์?");
            input = sc.nextLine();
            ((Side) product).setKetchup(Integer.parseInt(input));
        }
        else if(product instanceof Drink){
            System.out.println("๋นจ๋๊ฐ ํ์ํ์ ๊ฐ์? (1)_์ (2)_์๋์ค");
            input = sc.nextLine();
            if(input.equals("2")) ((Drink) product).setStraw(false);
        }
    }

    private BurgerSet composeSet(Hamburger hamburger){
        Side side;
        Drink drink;
        String input;
        //์ฌ์ด๋ ๋ฉ๋ด ์ถ๋ ฅ

        System.out.println("์ฌ์ด๋๋ฅผ ๊ณจ๋ผ์ฃผ์ธ์");
        menu.printSide(false);
        //๋ฉ๋ด ์ ํ
        input = sc.nextLine();
        side = new Side((Side) productRepository.findById(Integer.parseInt(input)));
        //์ต์ ์ ํ
        chooseOption(side);
        //์๋ฃ ๋ฉ๋ด ์ถ๋ ฅ
        System.out.println("์๋ฃ๋ฅผ ๊ณจ๋ผ์ฃผ์ธ์");
        menu.printDrink(false);
        input = sc.nextLine();
        drink = new Drink((Drink) productRepository.findById(Integer.parseInt(input)));
        //์ต์ ์ ํ
        chooseOption(drink);


        return new BurgerSet(hamburger.getName() + "์ธํธ", hamburger.getBurgerSetPrice(),
                hamburger.getKcal() + side.getKcal() + drink.getKcal(), hamburger, side,drink);
    }
}

