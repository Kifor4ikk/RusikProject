package kz.rusik.entity;

import kz.rusik.exception.NotEnoughtMoney;

import java.math.BigDecimal;

//Кошель мадам с крысой
//Хуй его знает, нахуя я создал отдельный кошель, но пусть будет.
public class Purse {

    private BigDecimal balance = BigDecimal.ZERO;
    private Long userId;

    public Purse(long userId) {
        this.userId = userId;
    }
    public Purse(User user) {
        this.userId = user.getId();
    }

    public void credit(BigDecimal sum){
        this.balance.add(sum);
    }

    public void debit(BigDecimal sum){
        if(balance.subtract(sum).compareTo(BigDecimal.ZERO) == -1)
            throw new NotEnoughtMoney("Not enought money!");
        else
            this.balance.subtract(sum);
    }
}
