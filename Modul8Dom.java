import java.util.*;

// ============================================================================
// ЧАСТЬ 1: СИСТЕМА УПРАВЛЕНИЯ ЗАКАЗАМИ В КАФЕ (ПАТТЕРН ДЕКОРАТОР)
// ============================================================================

// Базовый интерфейс для напитков [cite: 54]
interface Beverage {
    String getDescription();
    double cost();
}

// Базовые напитки [cite: 55]
class Espresso implements Beverage {
    public String getDescription() { return "Эспрессо"; }
    public double cost() { return 2.0; }
}

class Tea implements Beverage {
    public String getDescription() { return "Чай"; }
    public double cost() { return 1.5; }
}

// Дополнительные напитки по заданию [cite: 67]
class Latte implements Beverage {
    public String getDescription() { return "Латте"; }
    public double cost() { return 3.0; }
}

class Mocha implements Beverage {
    public String getDescription() { return "Мокко"; }
    public double cost() { return 3.5; }
}

// Абстрактный декоратор [cite: 56]
abstract class BeverageDecorator implements Beverage {
    protected Beverage beverage;
    public BeverageDecorator(Beverage beverage) { this.beverage = beverage; }
    public String getDescription() { return beverage.getDescription(); }
    public double cost() { return beverage.cost(); }
}

// Конкретные декораторы (добавки) [cite: 58]
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

// Целевой интерфейс системы [cite: 76, 84]
interface IPaymentProcessor {
    void processPayment(double amount);
}

// Существующая реализация [cite: 77, 85]
class PayPalPaymentProcessor implements IPaymentProcessor {
    public void processPayment(double amount) {
        System.out.println("Оплата $" + amount + " через PayPal выполнена успешно.");
    }
}

// Сторонняя система №1 (Stripe) [cite: 79, 86]
class StripePaymentService {
    public void makeTransaction(double totalAmount) {
        System.out.println("Транзакция на сумму $" + totalAmount + " через Stripe проведена.");
    }
}

// Адаптер для Stripe [cite: 82, 87]
class StripePaymentAdapter implements IPaymentProcessor {
    private StripePaymentService stripeService;
    public StripePaymentAdapter(StripePaymentService service) { this.stripeService = service; }
    public void processPayment(double amount) { stripeService.makeTransaction(amount); }
}

// Дополнительная система №2 (Square) [cite: 93]
class SquarePaymentService {
    public void executePayment(double val) {
        System.out.println("Платеж на сумму $" + val + " через Square выполнен.");
    }
}

// Адаптер для Square [cite: 93]
class SquarePaymentAdapter implements IPaymentProcessor {
    private SquarePaymentService squareService;
    public SquarePaymentAdapter(SquarePaymentService service) { this.squareService = service; }
    public void processPayment(double amount) { squareService.executePayment(amount); }
}

// ============================================================================
// КЛИЕНТСКИЙ КОД (ТЕСТИРОВАНИЕ)
// ============================================================================

public class Modul8Dom {
    public static void main(String[] args) {
        // 1. Тестирование системы кафе [cite: 60]
        System.out.println("--- ЗАКАЗ В КАФЕ ---");
        
        Beverage order = new Mocha(); // Базовый напиток
        order = new Milk(order);      // Добавка: Молоко
        order = new Syrup(order);     // Добавка: Сироп
        order = new WhippedCream(order); // Добавка: Сливки
        
        System.out.println("Заказ: " + order.getDescription());
        System.out.println("Итоговая стоимость: $" + order.cost());

        // 2. Тестирование системы оплаты [cite: 88, 94]
        System.out.println("\n--- СИСТЕМА ОПЛАТЫ ---");

        IPaymentProcessor paypal = new PayPalPaymentProcessor();
        IPaymentProcessor stripe = new StripePaymentAdapter(new StripePaymentService());
        IPaymentProcessor square = new SquarePaymentAdapter(new SquarePaymentService());

        paypal.processPayment(order.cost()); // Оплата суммы заказа
        stripe.processPayment(25.0);
        square.processPayment(40.0);
    }
}
