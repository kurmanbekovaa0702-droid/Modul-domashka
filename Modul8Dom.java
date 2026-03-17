import java.util.*;

// ============================================================================
// ЧАСТЬ 1: СИСТЕМА УПРАВЛЕНИЯ ЗАКАЗАМИ В КАФЕ (ПАТТЕРН ДЕКОРАТОР)
// ============================================================================

interface Beverage {
    String getDescription();
    double cost();
}

// Базовые напитки
class Espresso implements Beverage {
    public String getDescription() { return "Эспрессо"; }
    public double cost() { return 2.0; }
}

class Tea implements Beverage {
    public String getDescription() { return "Чай"; }
    public double cost() { return 1.5; }
}

// Дополнительные напитки (по заданию)
class Latte implements Beverage {
    public String getDescription() { return "Латте"; }
    public double cost() { return 3.0; }
}

class Mocha implements Beverage {
    public String getDescription() { return "Мокко"; }
    public double cost() { return 3.5; }
}

// Абстрактный декоратор
abstract class BeverageDecorator implements Beverage {
    protected Beverage beverage;
    public BeverageDecorator(Beverage beverage) { this.beverage = beverage; }
    public String getDescription() { return beverage.getDescription(); }
    public double cost() { return beverage.cost(); }
}

// Конкретные добавки
class Milk extends BeverageDecorator {
    public Milk(Beverage beverage) { super(beverage); }
    @Override
    public String getDescription() { return beverage.getDescription() + ", Молоко"; }
    @Override
    public double cost() { return beverage.cost() + 0.5; }
}

class Sugar extends BeverageDecorator {
    public Sugar(Beverage beverage) { super(beverage); }
    @Override
    public String getDescription() { return beverage.getDescription() + ", Сахар"; }
    @Override
    public double cost() { return beverage.cost() + 0.2; }
}

class WhippedCream extends BeverageDecorator {
    public WhippedCream(Beverage beverage) { super(beverage); }
    @Override
    public String getDescription() { return beverage.getDescription() + ", Сливки"; }
    @Override
    public double cost() { return beverage.cost() + 0.7; }
}

class Syrup extends BeverageDecorator {
    public Syrup(Beverage beverage) { super(beverage); }
    @Override
    public String getDescription() { return beverage.getDescription() + ", Сироп"; }
    @Override
    public double cost() { return beverage.cost() + 0.4; }
}

// ============================================================================
// ЧАСТЬ 2: ИНТЕГРАЦИЯ ПЛАТЕЖНЫХ СИСТЕМ (ПАТТЕРН АДАПТЕР)
// ============================================================================

interface IPaymentProcessor {
    void processPayment(double amount);
}

class PayPalPaymentProcessor implements IPaymentProcessor {
    public void processPayment(double amount) {
        System.out.println("Оплата $" + amount + " через PayPal выполнена успешно.");
    }
}

// Сторонняя система №1 (Stripe)
class StripePaymentService {
    public void makeTransaction(double totalAmount) {
        System.out.println("Транзакция на сумму $" + totalAmount + " через Stripe проведена.");
    }
}

class StripePaymentAdapter implements IPaymentProcessor {
    private StripePaymentService stripeService;
    public StripePaymentAdapter(StripePaymentService service) { this.stripeService = service; }
    public void processPayment(double amount) { stripeService.makeTransaction(amount); }
}

// Сторонняя система №2 (Square) — дополнительное задание
class SquarePaymentService {
    public void executePayment(double val) {
        System.out.println("Платеж на сумму $" + val + " через Square выполнен.");
    }
}

class SquarePaymentAdapter implements IPaymentProcessor {
    private SquarePaymentService squareService;
    public SquarePaymentAdapter(SquarePaymentService service) { this.squareService = service; }
    public void processPayment(double amount) { squareService.executePayment(amount); }
}

// ============================================================================
// КЛИЕНТСКИЙ КОД (ТЕСТИРОВАНИЕ)
// ============================================================================

public class Modul
