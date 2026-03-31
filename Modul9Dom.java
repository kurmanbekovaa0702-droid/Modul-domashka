import java.util.ArrayList;
import java.util.List;

// ============================================================================
// 1-БӨЛІМ: ФАСАД ПАТТЕРНІ (Мультимедиа орталығы) [cite: 7, 8]
// ============================================================================

// Ішкі жүйелер (Subsystems) [cite: 10]
class TV {
    void on() { System.out.println("Телевизор: Қосылды"); } [cite: 11]
    void off() { System.out.println("Телевизор: Өшірілді"); } [cite: 11]
    void setChannel(int channel) { System.out.println("Телевизор: " + channel + "-арна таңдалды"); } [cite: 11]
}

class AudioSystem {
    void on() { System.out.println("Аудиожүйе: Қосылды"); } [cite: 12]
    void off() { System.out.println("Аудиожүйе: Өшірілді"); } [cite: 12]
    void setVolume(int level) { System.out.println("Аудиожүйе: Дыбыс деңгейі " + level + " қойылды"); } [cite: 12, 31]
}

class DVDPlayer {
    void play() { System.out.println("DVD: Воспроизведение басталды"); } [cite: 13, 23]
    void pause() { System.out.println("DVD: Паузаға қойылды"); } [cite: 13, 23]
    void stop() { System.out.println("DVD: Тоқтатылды"); } [cite: 13, 23]
}

class GameConsole {
    void on() { System.out.println("Игровая консоль: Қосылды"); } [cite: 14, 24]
    void startGame(String game) { System.out.println("Игровая консоль: '" + game + "' ойыны басталды"); } [cite: 14, 24]
}

// Фасад класы [cite: 15, 25]
class HomeTheaterFacade {
    private TV tv;
    private AudioSystem audio;
    private DVDPlayer dvd;
    private GameConsole console;

    public HomeTheaterFacade(TV tv, AudioSystem audio, DVDPlayer dvd, GameConsole console) {
        this.tv = tv;
        this.audio = audio;
        this.dvd = dvd;
        this.console = console;
    }

    public void watchMovie() { [cite: 16]
        System.out.println("\n--- Сценарий: Фильм көру ---");
        tv.on();
        audio.on();
        audio.setVolume(20);
        dvd.play();
    }

    public void playGame(String gameName) { [cite: 18]
        System.out.println("\n--- Сценарий: Ойын бастау ---");
        tv.on();
        console.on();
        console.startGame(gameName);
    }

    public void listenToMusic() { [cite: 30]
        System.out.println("\n--- Сценарий: Музыка тыңдау ---");
        tv.on();
        audio.on();
        audio.setVolume(15);
    }

    public void turnOffAll() { [cite: 17]
        System.out.println("\n--- Жүйені толық өшіру ---");
        tv.off();
        audio.off();
        dvd.stop();
    }

    public void setVolume(int level) { [cite: 31]
        audio.setVolume(level);
    }
}

// ============================================================================
// 2-БӨЛІМ: КОМПОНОВЩИК ПАТТЕРНІ (Файлдық жүйе) [cite: 34, 39]
// ============================================================================

// Ортақ компонент интерфейсі [cite: 44, 45, 55]
abstract class FileSystemComponent {
    protected String name; [cite: 42, 43]
    public FileSystemComponent(String name) { this.name = name; }
    public abstract void display(); [cite: 47]
    public abstract int getSize(); [cite: 48]
}

// Файл класы [cite: 50, 56]
class File extends FileSystemComponent {
    private int size;
    public File(String name, int size) {
        super(name);
        this.size = size; [cite: 42]
    }
    @Override
    public void display() { System.out.println("  Файл: " + name + " (" + size + " KB)"); } [cite: 47]
    @Override
    public int getSize() { return size; } [cite: 48]
}

// Папка класы [cite: 51, 56]
class Directory extends FileSystemComponent {
    private List<FileSystemComponent> components = new ArrayList<>();

    public Directory(String name) { super(name); } [cite: 43]

    public void addComponent(FileSystemComponent component) { [cite: 57]
        if (!components.contains(component)) { [cite: 59]
            components.add(component);
        }
    }

    public void removeComponent(FileSystemComponent component) { [cite: 57]
        if (components.contains(component)) { [cite: 59]
            components.remove(component);
        }
    }

    @Override
    public void display() { [cite: 47, 53]
        System.out.println("Папка: " + name + " [Барлығы: " + getSize() + " KB]");
        for (FileSystemComponent component : components) {
            component.display();
        }
    }

    @Override
    public int getSize() { [cite: 48, 58]
        int totalSize = 0;
        for (FileSystemComponent component : components) {
            totalSize += component.getSize();
        }
        return totalSize;
    }
}

// ============================================================================
// НЕГІЗГІ КЛАСС (Файл атымен бірдей болуы керек: Modul9Dom.java)
// ============================================================================
public class Modul9Dom {
    public static void main(String[] args) { [cite: 32, 58]
        
        // 1. ФАСАД ТЕСТІЛЕУ
        System.out.println("========== ПАТТЕРН: ФАСАД ==========");
        HomeTheaterFacade facade = new HomeTheaterFacade(new TV(), new AudioSystem(), new DVDPlayer(), new GameConsole());
        
        facade.watchMovie();
        facade.listenToMusic();
        facade.playGame("Counter-Strike");
        facade.turnOffAll();

        System.out.println("\n");

        // 2. КОМПОНОВЩИК ТЕСТІЛЕУ
        System.out.println("========== ПАТТЕРН: КОМПОНОВЩИК ==========");
        Directory root = new Directory("MainFolder"); [cite: 53]
        Directory subFolder = new Directory("Homework");
        
        root.addComponent(new File("photo.png", 1500)); [cite: 58]
        root.addComponent(subFolder);
        subFolder.addComponent(new File("Task.docx", 45));

        root.display();
        System.out.println("Жалпы көлем: " + root.getSize() + " KB");
    }
}
