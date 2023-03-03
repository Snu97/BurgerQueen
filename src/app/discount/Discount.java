package app.discount;

import app.discount.discountCondition.DiscountCondition;

public class Discount {
    private DiscountCondition[] discountConditions;

    public Discount(DiscountCondition[] discountConditions) {
        this.discountConditions = discountConditions;
    }

    public int discount(int price){
        for(DiscountCondition discountCondition : discountConditions){
            discountCondition.checkDiscountCondition();
            if(discountCondition.isSatisfied()) price = discountCondition.applyDiscount(price);
        }
        return price;
    }
}
