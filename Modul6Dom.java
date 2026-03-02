import java.util.ArrayList;
import java.util.List;

interface IPaymentStrategy {
    void pay(double amount);
}

class CardPayment implements IPaymentStrategy {
    public void pay(double amount) {
        System.out.println("Card: " + amount);
    }
}

class PayPalPayment implements IPaymentStrategy {
    public void pay(double amount) {
        System.out.println("PayPal: " + amount);
    }
}

class CryptoPayment implements IPaymentStrategy {
    public void pay(double amount) {
        System.out.println("Crypto: " + amount);
    }
}

class PaymentContext {
    private IPaymentStrategy strategy;

    public void setStrategy(IPaymentStrategy strategy) {
        this.strategy = strategy;
    }

    public void executePayment(double amount) {
        strategy.pay(amount);
    }
}

interface ICurrencyObserver {
    void update(String currency, double rate);
}

interface ICurrencySubject {
    void addObserver(ICurrencyObserver o);
    void removeObserver(ICurrencyObserver o);
    void notifyObservers();
}

class CurrencyExchange implements ICurrencySubject {
    private List<ICurrencyObserver> observers = new ArrayList<>();
    private String currency;
    private double rate;

    public void setRate(String currency, double rate) {
        this.currency = currency;
        this.rate = rate;
        notifyObservers();
    }

    public void addObserver(ICurrencyObserver o) {
        observers.add(o);
    }

    public void removeObserver(ICurrencyObserver o) {
        observers.remove(o);
    }

    public void notifyObservers() {
        for (ICurrencyObserver o : observers) {
            o.update(currency, rate);
        }
    }
}

class Bank implements ICurrencyObserver {
    public void update(String currency, double rate) {
        System.out.println("Bank: " + currency + " = " + rate);
    }
}

class Investor implements ICurrencyObserver {
    public void update(String currency, double rate) {
        System.out.println("Investor: " + currency + " = " + rate);
    }
}

class WebTerminal implements ICurrencyObserver {
    public void update(String currency, double rate) {
        System.out.println("Web: " + currency + " = " + rate);
    }
}

public class Modul6Dom {
    public static void main(String[] args) {
        PaymentContext context = new PaymentContext();
        context.setStrategy(new CardPayment());
        context.executePayment(500.0);

        CurrencyExchange exchange = new CurrencyExchange();
        exchange.addObserver(new Bank());
        exchange.addObserver(new Investor());
        exchange.addObserver(new WebTerminal());
        exchange.setRate("USD/EUR", 0.92);
    }
}