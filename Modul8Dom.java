// ================= DECORATOR =================

// Базовый интерфейс для напитков [cite: 7]
interface Beverage {
    String getDescription();
    double cost();
}

// Базовые напитки [cite: 8, 20]
class Espresso implements Beverage {
    public String getDescription() { return "Espresso"; }
    public double cost() { return 2.0; }
}

class Tea implements Beverage {
    public String getDescription() { return "Tea"; }
    public double cost() { return 1.5; }
}

class Latte implements Beverage {
    public String getDescription() { return "Latte"; }
    public double cost() { return 3.0; }
}

class Mocha implements Beverage {
    public String getDescription() { return "Mocha"; }
    public double cost() { return 3.5; }
}

// Абстрактный декоратор [cite: 9, 10]
abstract class BeverageDecorator implements Beverage {
    protected Beverage beverage; // Ссылка на декорируемый объект

    public BeverageDecorator(Beverage beverage) {
        this.beverage = beverage;
    }

    public String getDescription() {
        return beverage.getDescription();
    }

    public double cost() {
        return beverage.cost();
    }
}

// Конкретные добавки [cite: 11, 12, 21]
class Milk extends BeverageDecorator {
    public Milk(Beverage beverage) { super(beverage); }
    @Override
    public String getDescription() { return beverage.getDescription() + ", Milk"; }
    @Override
    public double cost() { return beverage.cost() + 0.5; }
}

class Sugar extends BeverageDecorator {
    public Sugar(Beverage beverage) { super(beverage); }
    @Override
    public String getDescription() { return beverage.getDescription() + ", Sugar"; }
    @Override
    public double cost() { return beverage.cost() + 0.2; }
}

class WhippedCream extends BeverageDecorator {
    public WhippedCream(Beverage beverage) { super(beverage); }
    @Override
    public String getDescription() { return beverage.getDescription() + ", Whipped Cream"; }
    @Override
    public double cost() { return beverage.cost() + 0.7; }
}

class Syrup extends BeverageDecorator {
    public Syrup(Beverage beverage) { super(beverage); }
    @Override
    public String getDescription() { return beverage.getDescription() + ", Syrup"; }
    @Override
    public double cost() { return beverage.cost() + 0.4; }
}

// ================= ADAPTER =================

// Целевой интерфейс системы [cite: 37]
interface IPaymentProcessor {
    void processPayment(double amount);
}

// Существующая реализация [cite: 38]
class PayPalPaymentProcessor implements IPaymentProcessor {
    public void processPayment(double amount) {
        System.out.println("Processing payment of $" + amount + " via PayPal.");
    }
}

// Сторонняя система №1 (Stripe) [cite: 39]
class StripePaymentService {
    public void makeTransaction(double totalAmount) {
        System.out.println("Making transaction of $" + totalAmount + " via Stripe.");
    }
}

// Адаптер для Stripe [cite: 35, 40]
class StripePaymentAdapter implements IPaymentProcessor {
    private StripePaymentService stripeService;

    public StripePaymentAdapter(StripePaymentService service) {
        this.stripeService = service;
    }

    public void processPayment(double amount) {
        stripeService.makeTransaction(amount);
    }
}

// Дополнительная сторонняя система №2 (Square) 
class SquarePaymentService {
    public void executePayment(double val) {
        System.out.println("Executing payment of $" + val + " via Square.");
    }
}

// Адаптер для Square 
class SquarePaymentAdapter implements IPaymentProcessor {
    private SquarePaymentService squareService;

    public SquarePaymentAdapter(SquarePaymentService service) {
        this.squareService = service;
    }

    public void processPayment(double amount) {
        squareService.executePayment(amount);
    }
}

// ================= КЛИЕНТСКИЙ КОД =================

public class Modul8Dom {
    public static void main(String[] args) {
        // Тестирование Декоратора [cite: 13, 22]
        System.out.println("--- CAFE ORDER SYSTEM ---");
        
        Beverage myCoffee = new Mocha(); // Базовый напиток: Мокко
        myCoffee = new Milk(myCoffee);   // Добавили молоко
        myCoffee = new Syrup(myCoffee);  // Добавили сироп
        myCoffee = new WhippedCream(myCoffee); // Добавили сливки
        
        System.out.println("Order: " + myCoffee.getDescription());
        System.out.println("Total Cost: $" + myCoffee.cost());

        System.out.println("\n--- PAYMENT SYSTEM ---");

        // Тестирование Адаптера [cite: 41, 47]
        IPaymentProcessor paypal = new PayPalPaymentProcessor();
        IPaymentProcessor stripe = new StripePaymentAdapter(new StripePaymentService());
        IPaymentProcessor square = new SquarePaymentAdapter(new SquarePaymentService());

        paypal.processPayment(15.0);
        stripe.processPayment(25.0);
        square.processPayment(40.0);
    }
}
