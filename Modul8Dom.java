interface Beverage {
    String getDescription();
    double cost();
}

// Базовые напитки
class Espresso implements Beverage {
    public String getDescription() {
        return "Espresso";
    }

    public double cost() {
        return 2.0;
    }
}

class Tea implements Beverage {
    public String getDescription() {
        return "Tea";
    }

    public double cost() {
        return 1.5;
    }
}

// Декоратор
abstract class BeverageDecorator implements Beverage {
    protected Beverage beverage;

    public BeverageDecorator(Beverage beverage) {
        this.beverage = beverage;
    }
}

// Добавки
class Milk extends BeverageDecorator {
    public Milk(Beverage beverage) {
        super(beverage);
    }

    public String getDescription() {
        return beverage.getDescription() + ", Milk";
    }

    public double cost() {
        return beverage.cost() + 0.5;
    }
}

class Sugar extends BeverageDecorator {
    public Sugar(Beverage beverage) {
        super(beverage);
    }

    public String getDescription() {
        return beverage.getDescription() + ", Sugar";
    }

    public double cost() {
        return beverage.cost() + 0.2;
    }
}

// ================= ADAPTER =================

interface IPaymentProcessor {
    void processPayment(double amount);
}

// PayPal
class PayPalPaymentProcessor implements IPaymentProcessor {
    public void processPayment(double amount) {
        System.out.println("Paid " + amount + " using PayPal");
    }
}

// Сторонняя система
class StripePaymentService {
    public void makeTransaction(double totalAmount) {
        System.out.println("Paid " + totalAmount + " using Stripe");
    }
}

// Адаптер
class StripePaymentAdapter implements IPaymentProcessor {
    private StripePaymentService stripe;

    public StripePaymentAdapter(StripePaymentService stripe) {
        this.stripe = stripe;
    }

    public void processPayment(double amount) {
        stripe.makeTransaction(amount);
    }
}

// ================= MAIN =================

public class Modul8Dom {
    public static void main(String[] args) {

        // DECORATOR тест
        Beverage drink = new Espresso();
        drink = new Milk(drink);
        drink = new Sugar(drink);

        System.out.println("Drink: " + drink.getDescription());
        System.out.println("Cost: " + drink.cost());

        System.out.println("----------------------");

        // ADAPTER тест
        IPaymentProcessor paypal = new PayPalPaymentProcessor();
        paypal.processPayment(100);

        IPaymentProcessor stripe = new StripePaymentAdapter(new StripePaymentService());
        stripe.processPayment(200);
    }
}
