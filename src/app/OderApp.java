package app;

import app.product.ProductRepository;

import java.util.Scanner;

public class OderApp {
    public void start(){
        Scanner sc = new Scanner(System.in);
        ProductRepository productRepository = new ProductRepository();
        Menu menu = new Menu(productRepository.getAllProducts());
        Cart cart = new Cart(productRepository, menu);
        Order order = new Order(cart);

        System.out.println("\uD83C\uDF54 BurgerQueen Order Service");
        while(true){
            //1. 메뉴 출력
            menu.printMenu();
            //  입력받기
            String input = sc.nextLine();
            if(input.equals("+")){
                //2. 메뉴를 선택한 경우
                //  옵션선택 - 장바구니 담기 - 메뉴 출력
                order.makeOrder();
                break;
            }
            else{
                int menuNum = Integer.parseInt(input);
                if (menuNum == 0) {
                    //3. 장바구니를 선택한 경우
                    //  장바구니 출력 - 메뉴 출력
                    cart.printCart();
                } else if (menuNum>0 && menuNum<productRepository.getAllProducts().length)
                //4. 주문하기를 누른 경우
                //  주문내역 출력 - 프로그램 종료
                    cart.addToCart(menuNum);
            }
        }
    }


}
