package kz.rusik.entity;

import java.util.ArrayList;
import java.util.List;

//Пользователь
//Изначально все юзеры ЮЗЕРЫ, чтоб получить роль админа - нужно в БД установить роль админ, имхо самое безопасное что я сейчас могу предложить
//activeSubs все подписки пользователя
//cart - Корзина
//Как видишь, кошелька тут нету, ибо он связан невидимыми связями д̶р̶у̶ж̶б̶ы̶ базы данных.
public class User {

    private long id;
    private String name;
    private String password;
    private Role role = Role.USER;
    private String fullName;
    private String address;
    private List<Subscription> activeSubs = new ArrayList<>();
    private List<Product> cart = new ArrayList<>();

    public User(String name,String fullName, String address, String password) {
        this.name = name;
        this.password = password;
        this.address = address;
        this.fullName = fullName;
    }

    public User(long id, String name, String fullName, String address, Role role) {
        this.id = id;
        this.name = name;
        this.fullName = fullName;
        this.address = address;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setActiveSubs(List<Subscription> activeSubs) {
        this.activeSubs = activeSubs;
    }

    public void setCart(List<Product> cart) {
        this.cart = cart;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public List<Subscription> getActiveSubs() {
        return activeSubs;
    }

    public List<Product> getCart() {
        return cart;
    }

    public String toString(){
        return "\n\t|USER|\n" +  "|ID|: " + id + "\n|R|: " + role.toString() + "\n|L|: " + this.name + "\n" +
                "|FIO|: " + fullName + "\n|A|: " + address + "\n" +
                "|CART|\n" + cart + "\n" + "|ACTIVE SUBS|\n" + activeSubs + "\n";
    }
}
