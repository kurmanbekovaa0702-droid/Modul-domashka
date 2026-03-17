import java.util.*;

// ============================================================================
// ЧАСТЬ 1: СИСТЕМА КАФЕ (ПАТТЕРН ДЕКОРАТОР)
// ============================================================================

// Базовый интерфейс для напитков
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

// Дополнительные напитки (из раздела "Задания")
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
// ЧАСТЬ 2: ПЛАТЕЖНЫЕ СИСТЕМЫ (ПАТТЕРН АДАПТЕР)
// ============================================================================

// Целевой интерфейс
interface IPaymentProcessor {
    void processPayment(double amount);
}

// Существующая система
class PayPalPaymentProcessor implements IPaymentProcessor {
    public void processPayment(double amount) {
        System.out.println("Оплата $" + amount + " через PayPal выполнена.");
    }
}

// Сторонняя система 1 (Stripe)
class StripePaymentService {
    public void makeTransaction(double totalAmount) {
        System.out.println("Транзакция $" + totalAmount + " через Stripe завершена.");
    }
}

// Адаптер для Stripe
class StripePaymentAdapter implements IPaymentProcessor {
    private StripePaymentService stripeService;
    public StripePaymentAdapter(StripePaymentService service) { this.stripeService = service; }
    public void processPayment(double amount) { stripeService.makeTransaction(amount); }
}

// Дополнительная сторонняя система 2 (Square)
class SquarePaymentService {
    public void executePayment(double val) {
        System.out.println("Платеж $" + val + " через Square подтвержден.");
    }
}

// Адаптер для Square
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
        // Тестирование Декоратора
        System.out.println("--- ЗАКАЗ В КАФЕ ---");
        
        Beverage order = new Mocha();    // Выбрали Мокко
        order = new Milk(order);         // Добавили молоко
        order = new Syrup(order);        // Добавили сироп
        order = new WhippedCream(order); // Добавили сливки
        
        System.out.println("Заказ: " + order.getDescription());
        System.out.println("Итоговая стоимость: $" + order.cost());

        System.out.println("\n--- СИСТЕМА ОПЛАТЫ ---");

        // Тестирование Адаптеров
        IPaymentProcessor paypal = new PayPalPaymentProcessor();
        IPaymentProcessor stripe = new StripePaymentAdapter(new StripePaymentService());
        IPaymentProcessor square = new SquarePaymentAdapter(new SquarePaymentService());

        paypal.processPayment(order.cost()); // Оплата итоговой суммы заказа
        stripe.processPayment(25.0);
        square.processPayment(40.0);
    }
}
