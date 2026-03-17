import java.util.*;

// ============================================================================
// ЧАСТЬ 1: СИСТЕМА УПРАВЛЕНИЯ ЗАКАЗАМИ В КАФЕ (ПАТТЕРН ДЕКОРАТОР)
// ============================================================================

// Базовый интерфейс для напитков [cite: 7]
interface Beverage {
    String getDescription();
    double cost();
}

// Базовые напитки [cite: 8]
class Espresso implements Beverage {
    public String getDescription() { return "Эспрессо"; }
    public double cost() { return 2.0; }
}

class Tea implements Beverage {
    public String getDescription() { return "Чай"; }
    public double cost() { return 1.5; }
}

// Дополнительные напитки [cite: 20]
class Latte implements Beverage {
    public String getDescription() { return "Латте"; }
    public double cost() { return 3.0; }
}

class Mocha implements Beverage {
    public String getDescription() { return "Мокко"; }
    public double cost() { return 3.5; }
}

// Абстрактный класс-декоратор [cite: 9, 10]
abstract class BeverageDecorator implements Beverage {
    protected Beverage beverage; 

    public BeverageDecorator(Beverage beverage) {
        this.beverage = beverage;
    }

    public String getDescription() { return beverage.getDescription(); }
    public double cost() { return beverage.cost(); }
}

// Конкретные декораторы (добавки) [cite: 11, 12]
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

// Новые типы добавок [cite: 21]
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

// Целевой интерфейс системы [cite: 29, 37]
interface IPaymentProcessor {
    void processPayment(double amount);
}

// Существующая реализация [cite: 30, 38]
class PayPalPaymentProcessor implements IPaymentProcessor {
    public void processPayment(double amount) {
        System.out.println("Оплата $" + amount + " через PayPal выполнена успешно.");
    }
}

// Сторонняя система 1 (Stripe) [cite: 32, 39]
class StripePaymentService {
    public void makeTransaction(double totalAmount) {
        System.out.println("Транзакция на сумму $" + totalAmount + " через Stripe проведена.");
    }
}

// Адаптер для Stripe [cite: 34, 35, 40]
class StripePaymentAdapter implements IPaymentProcessor {
    private StripePaymentService stripeService;

    public StripePaymentAdapter(StripePaymentService service) {
        this.stripeService = service;
    }

    public void processPayment(double amount) {
        stripeService.makeTransaction(amount);
    }
}

// Дополнительный сторонний сервис (Square) [cite: 46]
class SquarePaymentService {
    public void executePayment(double val) {
        System.out.println("Платеж на сумму $" + val + " через Square выполнен.");
    }
}

// Адаптер для Square [cite: 46]
class SquarePaymentAdapter implements IPaymentProcessor {
    private SquarePaymentService squareService;

    public SquarePaymentAdapter(SquarePaymentService service) {
        this.squareService = service;
    }

    public void processPayment(double amount) {
        squareService.executePayment(amount);
    }
}

// ============================================================================
// КЛИЕНТСКИЙ КОД (ТЕСТИРОВАНИЕ)
// ============================================================================

public class Modul8Dom {
    public static void main(String[] args) {
        // Тестирование системы кафе [cite: 13, 22]
        System.out.println("--- ЗАКАЗ В КАФЕ ---");
        
        Beverage order = new Mocha(); // Базовый напиток: Мокко [cite: 20]
        order = new Milk(order);      // Добавка: Молоко [cite: 11]
        order = new Syrup(order);     // Добавка: Сироп [cite: 21]
        order = new WhippedCream(order); // Добавка: Сливки [cite: 21]
        
        System.out.println("Заказ: " + order.getDescription()); // [cite: 14]
        System.out.println("Итоговая стоимость: $" + order.cost()); // [cite: 14]

        System.out.println("\n--- СИСТЕМА ОПЛАТЫ ---");

        // Тестирование платежных адаптеров [cite: 41, 47]
        IPaymentProcessor paypal = new PayPalPaymentProcessor();
        IPaymentProcessor stripe = new StripePaymentAdapter(new StripePaymentService());
        IPaymentProcessor square = new SquarePaymentAdapter(new SquarePaymentService());

        paypal.processPayment(15.0);
        stripe.processPayment(25.0);
        square.processPayment(40.0);
    }
}
