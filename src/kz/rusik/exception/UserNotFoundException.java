package kz.rusik.exception;

//Дропаем когда не найден юзер
public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String message) {
        super(message);
    }
}
