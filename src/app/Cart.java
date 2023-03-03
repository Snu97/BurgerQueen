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
        System.out.println("🧺 장바구니");
        System.out.println("-".repeat(60));

        printCartItemDetails();

        System.out.println("-".repeat(60));
        System.out.printf("합계 : %d원\n", calculateTotalPrice());

        System.out.println("이전으로 돌아가려면 엔터를 누르세요. ");
        sc.nextLine();
    }

    protected void printCartItemDetails(){
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
                System.out.printf("  %-8s %6d원 (케첩 %d개)\n", product.getName(), product.getPrice(), ((Side) product).getKetchup());

            } else if (product instanceof Drink){
                System.out.printf("  %-8s %6d원 (빨대 %s)\n", product.getName(), product.getPrice(), ((Drink) product).hasStraw() ? "있음" : "없음");
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
        //Product product = productId를 통해 productId를 id로 가지는 상품 찾기
        Product product = productRepository.findById(productId);



        // 2.2.2.
        //상품 옵션 설정 // chooseOption()
        chooseOption(product);
        // 2.2.3.
        //if (product가 Hamburger의 인스턴스이고, isBurgerSet이 true라면) {
            //product = 세트 구성 // composeSet();
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
        //items에 product 추가
        Product[] temp = items;
        items = new Product[items.length+1];
        System.arraycopy(temp, 0, items, 0 , temp.length);
        items[temp.length] = product;
        //"[📣] XXXX를(을) 장바구니에 담았습니다." 출력
        System.out.printf("[\uD83D\uDCE3] %s를(을) 장바구니에 담았습니다.\n", product.getName());

    }

    private void chooseOption(Product product){
        String input;
        if(product instanceof Hamburger){
            System.out.printf("단품으로 주문하시겠어요? (1)_단품(%d원) (2)_세트(%d원)\n", product.getPrice(), ((Hamburger) product).getBurgerSetPrice());
            input = sc.nextLine();
            if(input.equals("2")) ((Hamburger) product).setBurgerSet(true);

        } else if (product instanceof Side) {
            System.out.println("케첩은 몇개가 필요하신가요?");
            input = sc.nextLine();
            ((Side) product).setKetchup(Integer.parseInt(input));
        }
        else if(product instanceof Drink){
            System.out.println("빨대가 필요하신가요? (1)_예 (2)_아니오");
            input = sc.nextLine();
            if(input.equals("2")) ((Drink) product).setStraw(false);
        }
    }

    private BurgerSet composeSet(Hamburger hamburger){
        Side side;
        Drink drink;
        String input;
        //사이드 메뉴 출력

        System.out.println("사이드를 골라주세요");
        menu.printSide(false);
        //메뉴 선택
        input = sc.nextLine();
        side = new Side((Side) productRepository.findById(Integer.parseInt(input)));
        //옵션 선택
        chooseOption(side);
        //음료 메뉴 출력
        System.out.println("음료를 골라주세요");
        menu.printDrink(false);
        input = sc.nextLine();
        drink = new Drink((Drink) productRepository.findById(Integer.parseInt(input)));
        //옵션 선택
        chooseOption(drink);


        return new BurgerSet(hamburger.getName() + "세트", hamburger.getBurgerSetPrice(),
                hamburger.getKcal() + side.getKcal() + drink.getKcal(), hamburger, side,drink);
    }
}

