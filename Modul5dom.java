import java.io.*;
import java.util.*;

// ==========================================
// 1. SINGLETON: ConfigurationManager
// ==========================================
class ConfigurationManager {
    private static volatile ConfigurationManager instance;
    private Map<String, String> settings;
    private final String configFile = "config.txt";

    private ConfigurationManager() {
        settings = new HashMap<>();
        loadSettings();
    }

    public static ConfigurationManager getInstance() {
        if (instance == null) {
            synchronized (ConfigurationManager.class) {
                if (instance == null) {
                    instance = new ConfigurationManager();
                }
            }
        }
        return instance;
    }

    public void setSetting(String key, String value) {
        settings.put(key, value);
    }

    public String getSetting(String key) {
        return settings.getOrDefault(key, "Настройка не найдена");
    }

    public void saveSettings() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(configFile))) {
            for (Map.Entry<String, String> entry : settings.entrySet()) {
                writer.println(entry.getKey() + "=" + entry.getValue());
            }
        } catch (IOException e) {
            System.err.println("Ошибка сохранения настроек.");
        }
    }

    private void loadSettings() {
        try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length == 2) settings.put(parts[0], parts[1]);
            }
        } catch (IOException e) {
            // Файл не существует, создаем настройки по умолчанию
            settings.put("AppTheme", "Dark");
            settings.put("Language", "RU");
        }
    }
}

// ==========================================
// 2. BUILDER: Упрощенная система отчетов
// ==========================================
class HWReport {
    public String header, content, footer;
    public void show() {
        System.out.println("Заголовок: " + header);
        System.out.println("Текст: " + content);
        System.out.println("Подвал: " + footer);
    }
}

interface IHWReportBuilder {
    void setHeader(String header);
    void setContent(String content);
    void setFooter(String footer);
    HWReport getReport();
}

class HWTextReportBuilder implements IHWReportBuilder {
    private HWReport report = new HWReport();
    public void setHeader(String h) { report.header = "TEXT HEADER: " + h; }
    public void setContent(String c) { report.content = c; }
    public void setFooter(String f) { report.footer = "TEXT FOOTER: " + f; }
    public HWReport getReport() { return report; }
}

class HWReportDirector {
    public void constructReport(IHWReportBuilder builder) {
        builder.setHeader("Отчет по продажам");
        builder.setContent("Продажи выросли на 20%.");
        builder.setFooter("Конец отчета");
    }
}

// ==========================================
// 3. PROTOTYPE: Заказы (Orders)
// ==========================================
class Product implements Cloneable {
    public String name;
    public double price;
    public int qty;

    public Product(String name, double price, int qty) {
        this.name = name; this.price = price; this.qty = qty;
    }

    @Override protected Product clone() {
        try { return (Product) super.clone(); }
        catch (CloneNotSupportedException e) { return null; }
    }
}

class Discount implements Cloneable {
    public String name;
    public double percent;

    public Discount(String name, double percent) {
        this.name = name; this.percent = percent;
    }

    @Override protected Discount clone() {
        try { return (Discount) super.clone(); }
        catch (CloneNotSupportedException e) { return null; }
    }
}

class Order implements Cloneable {
    public int orderId;
    public List<Product> products = new ArrayList<>();
    public List<Discount> discounts = new ArrayList<>();
    public double deliveryCost;
    public String paymentMethod;

    public Order(int orderId) { this.orderId = orderId; }

    @Override
    public Order clone() {
        try {
            Order cloned = (Order) super.clone();
            cloned.products = new ArrayList<>();
            for (Product p : this.products) cloned.products.add(p.clone());

            cloned.discounts = new ArrayList<>();
            for (Discount d : this.discounts) cloned.discounts.add(d.clone());

            return cloned;
        } catch (CloneNotSupportedException e) { return null; }
    }

    public void printOrder() {
        System.out.println("Заказ #" + orderId + " | Товаров: " + products.size() + " | Способ оплаты: " + paymentMethod);
    }
}

// ==========================================
// ОСНОВНОЙ КЛАСС ДЛЯ ЗАПУСКА ДОМАШНЕЙ РАБОТЫ
// ==========================================
public class Modul5dom {
    public static void main(String[] args) {
        System.out.println("=== ТЕСТ SINGLETON (CONFIG) ===");
        ConfigurationManager config1 = ConfigurationManager.getInstance();
        ConfigurationManager config2 = ConfigurationManager.getInstance();
        config1.setSetting("DbPort", "5432");
        config1.saveSettings();
        System.out.println("DbPort из config2: " + config2.getSetting("DbPort"));
        System.out.println("Один и тот же объект? " + (config1 == config2));

        System.out.println("\n=== ТЕСТ BUILDER (HW REPORT) ===");
        HWReportDirector director = new HWReportDirector();
        IHWReportBuilder textBuilder = new HWTextReportBuilder();
        director.constructReport(textBuilder);
        HWReport report = textBuilder.getReport();
        report.show();

        System.out.println("\n=== ТЕСТ PROTOTYPE (ORDER) ===");
        Order templateOrder = new Order(101);
        templateOrder.deliveryCost = 500.0;
        templateOrder.paymentMethod = "Карта";
        templateOrder.products.add(new Product("Ноутбук", 50000, 1));
        templateOrder.discounts.add(new Discount("Новогодняя", 10.0));

        Order newOrder = templateOrder.clone();
        newOrder.orderId = 102;
        newOrder.products.get(0).price = 55000; // Проверка глубокого клонирования

        templateOrder.printOrder();
        System.out.println("Цена товара в оригинале: " + templateOrder.products.get(0).price);
        newOrder.printOrder();
        System.out.println("Цена товара в клоне (изменена): " + newOrder.products.get(0).price);
    }
}