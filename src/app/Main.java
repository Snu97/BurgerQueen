package app;

public class Main {
    public static void main(String[] args) {
        AppConfigurer appConfigurer = new AppConfigurer();
        OderApp oderApp = new OderApp(appConfigurer.productRepository(), appConfigurer.menu(),
                appConfigurer.cart(), appConfigurer.order()
        );
        oderApp.start();
    }
}
