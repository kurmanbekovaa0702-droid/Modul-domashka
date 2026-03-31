import java.util.ArrayList;
import java.util.List;

// ============================================================================
// 1-БӨЛІМ: DECORATOR ПАТТЕРНІ (Кафе жүйесі)
// ============================================================================

// Базалық интерфейс
interface Beverage {
    String getDescription();
    double cost();
}

// Нақты сусындар
class Espresso implements Beverage {
    public String getDescription() { return "Эспрессо"; }
    public double cost() { return 550.0; }
}

class Tea implements Beverage {
    public String getDescription() { return "Шай"; }
    public double cost() { return 400.0; }
}

class Latte implements Beverage {
    public String getDescription() { return "Латте"; }
    public double cost() { return 750.0; }
}

// Абстрактілі декоратор
abstract class BeverageDecorator implements Beverage {
    protected Beverage beverage;
    public BeverageDecorator(Beverage beverage) { this.beverage = beverage; }
    public String getDescription() { return beverage.getDescription(); }
    public double cost() { return beverage.cost(); }
}

// Нақты қоспалар (Декораторлар)
class Milk extends BeverageDecorator {
    public Milk(Beverage beverage) { super(beverage); }
    @Override
    public String getDescription() { return beverage.getDescription() + ", Сүт"; }
    @Override
    public double cost() { return beverage.cost() + 150.0; }
}

class Sugar extends BeverageDecorator {
    public Sugar(Beverage beverage) { super(beverage); }
    @Override
    public String getDescription() { return beverage.getDescription() + ", Қант"; }
    @Override
    public double cost() { return beverage.cost() + 50.0; }
}

class WhippedCream extends BeverageDecorator {
    public WhippedCream(Beverage beverage) { super(beverage); }
    @Override
    public String getDescription() { return beverage.getDescription() + ", Сливки"; }
    @Override
    public double cost() { return beverage.cost() + 200.0; }
}

// ============================================================================
// 2-БӨЛІМ: ADAPTER ПАТТЕРНІ (Төлем жүйесі)
// ============================================================================

// Қолданыстағы төлем интерфейсі
interface IPaymentProcessor {
    void processPayment(double amount);
}

// PayPal жүйесі (Интерфейсті тікелей іске асырады)
class PayPalPaymentProcessor implements IPaymentProcessor {
    public void processPayment(double amount) {
        System.out.println("PayPal арқылы " + amount + " тг төлем өңделді.");
    }
}

// Бөтен жүйе: Stripe (Интерфейсі басқа)
class StripePaymentService {
    public void makeTransaction(double totalAmount) {
        System.out.println("Stripe арқылы " + totalAmount + " тг транзакция жасалды.");
    }
}

// Stripe Адаптері
class StripePaymentAdapter implements IPaymentProcessor {
    private StripePaymentService stripeService;
    public StripePaymentAdapter(StripePaymentService service) {
        this.stripeService = service;
    }
    @Override
    public void processPayment(double amount) {
        stripeService.makeTransaction(amount);
    }
}

// Бөтен жүйе: Kaspi (Қосымша тапсырма ретінде)
class KaspiPaymentService {
    public void sendMoney(double sum) {
        System.out.println("Kaspi арқылы " + sum + " тг аударылды.");
    }
}

class KaspiPaymentAdapter implements IPaymentProcessor {
    private KaspiPaymentService kaspi = new KaspiPaymentService();
    @Override
    public void processPayment(double amount) {
        kaspi.sendMoney(amount);
    }
}

// ============================================================================
// НЕГІЗГІ КЛАСС (Файл атымен бірдей: Modul8Dom2.java)
// ============================================================================
public class Modul8Dom2 {
    public static void main(String[] args) {
        
        // 1. DECORATOR ТЕСТІЛЕУ
        System.out.println("=== DE
