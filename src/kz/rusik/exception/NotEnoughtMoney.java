package kz.rusik.exception;

//Дропаем когда нету денях(
public class NotEnoughtMoney extends RuntimeException{
    public NotEnoughtMoney(String message) {
        super(message);
    }
}
