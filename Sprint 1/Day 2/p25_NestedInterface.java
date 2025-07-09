/**
 * Program to demonstrate nested interfaces in Java
 * 
 * Nested interfaces can be defined in:
 * 1. Inside a class (interfaces within classes)
 * 2. Inside another interface (interfaces within interfaces)
 *
 * This program demonstrates both types of nested interfaces
 */

public class p25_NestedInterface {
    public static void main(String[] args) {
        System.out.println("=== Nested Interfaces Demonstration ===\n");
        
        // Using interface nested within a class
        System.out.println("1. Interface nested within a class:");
        BankAccount savingsAccount = new SavingsAccount("Rajesh Kumar", "SB10001", 15000.0);
        savingsAccount.showAccountDetails();
        savingsAccount.deposit(5000.0);
        savingsAccount.withdraw(2000.0);
        System.out.println();
        
        // Using interface nested within another interface
        System.out.println("2. Interface nested within another interface:");
        MusicPlayer mp3Player = new PortablePlayer("Sony Walkman X", "MP3");
        mp3Player.play();
        mp3Player.pause();
        mp3Player.stop();
        
        // Using the nested interface for volume control
        MediaPlayer.VolumeControl volumeController = mp3Player;
        volumeController.increaseVolume();
        volumeController.decreaseVolume();
        volumeController.mute();
        System.out.println();
        
        // Another implementation of nested interfaces
        System.out.println("3. Another implementation (Smartphone as MediaPlayer):");
        MusicPlayer smartPhone = new SmartPhonePlayer("Xiaomi Redmi Note", "MP4");
        smartPhone.play();
        
        MediaPlayer.VolumeControl phoneVolume = (MediaPlayer.VolumeControl) smartPhone;
        phoneVolume.increaseVolume();
        phoneVolume.setVolume(80);
        
        System.out.println("\n=== End of Nested Interfaces Demonstration ===");
    }
    
    // Interface nested within a class
    public interface AccountOperations {
        void deposit(double amount);
        boolean withdraw(double amount);
        double checkBalance();
    }
}

// Abstract class with nested interface implementation
abstract class BankAccount implements p25_NestedInterface.AccountOperations {
    protected String accountHolderName;
    protected String accountNumber;
    protected double balance;
    
    public BankAccount(String name, String accountNumber, double initialBalance) {
        this.accountHolderName = name;
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }
    
    public void showAccountDetails() {
        System.out.println("Account Holder: " + accountHolderName);
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Current Balance: ₹" + balance);
    }
    
    @Override
    public double checkBalance() {
        return balance;
    }
}

// Implementation of the class with nested interface
class SavingsAccount extends BankAccount {
    private static final double MIN_BALANCE = 1000.0;
    
    public SavingsAccount(String name, String accountNumber, double initialBalance) {
        super(name, accountNumber, initialBalance);
    }
    
    @Override
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("₹" + amount + " deposited successfully.");
            System.out.println("New Balance: ₹" + balance);
        } else {
            System.out.println("Invalid amount for deposit.");
        }
    }
    
    @Override
    public boolean withdraw(double amount) {
        if (amount > 0 && (balance - amount) >= MIN_BALANCE) {
            balance -= amount;
            System.out.println("₹" + amount + " withdrawn successfully.");
            System.out.println("New Balance: ₹" + balance);
            return true;
        } else {
            System.out.println("Withdrawal failed. Insufficient funds or below minimum balance requirement.");
            return false;
        }
    }
}

// Interface containing a nested interface
interface MediaPlayer {
    void play();
    void stop();
    void pause();
    
    // Nested interface within an interface
    interface VolumeControl {
        void increaseVolume();
        void decreaseVolume();
        void mute();
        void setVolume(int level);
    }
}

// Interface combining the outer and nested interfaces
interface MusicPlayer extends MediaPlayer, MediaPlayer.VolumeControl {
    String getPlayerType();
}

// Implementation of interface with nested interface
class PortablePlayer implements MusicPlayer {
    private String model;
    private String supportedFormat;
    private boolean isPlaying;
    private int volumeLevel;
    private boolean isMuted;
    
    public PortablePlayer(String model, String supportedFormat) {
        this.model = model;
        this.supportedFormat = supportedFormat;
        this.isPlaying = false;
        this.volumeLevel = 50;
        this.isMuted = false;
    }
    
    @Override
    public void play() {
        isPlaying = true;
        System.out.println(model + " is now playing music.");
    }
    
    @Override
    public void stop() {
        isPlaying = false;
        System.out.println(model + " stopped playback.");
    }
    
    @Override
    public void pause() {
        if (isPlaying) {
            isPlaying = false;
            System.out.println(model + " paused playback.");
        }
    }
    
    @Override
    public void increaseVolume() {
        if (volumeLevel < 100) {
            volumeLevel += 10;
            System.out.println("Volume increased to " + volumeLevel + "%");
        } else {
            System.out.println("Volume already at maximum");
        }
    }
    
    @Override
    public void decreaseVolume() {
        if (volumeLevel > 0) {
            volumeLevel -= 10;
            System.out.println("Volume decreased to " + volumeLevel + "%");
        } else {
            System.out.println("Volume already at minimum");
        }
    }
    
    @Override
    public void mute() {
        isMuted = true;
        System.out.println(model + " is now muted");
    }
    
    @Override
    public void setVolume(int level) {
        if (level >= 0 && level <= 100) {
            volumeLevel = level;
            System.out.println("Volume set to " + volumeLevel + "%");
        } else {
            System.out.println("Invalid volume level. Must be between 0-100");
        }
    }
    
    @Override
    public String getPlayerType() {
        return "Portable " + supportedFormat + " Player";
    }
}

// Another implementation of the interfaces
class SmartPhonePlayer implements MusicPlayer {
    private String model;
    private String supportedFormat;
    private int volumeLevel;
    
    public SmartPhonePlayer(String model, String supportedFormat) {
        this.model = model;
        this.supportedFormat = supportedFormat;
        this.volumeLevel = 50;
    }
    
    @Override
    public void play() {
        System.out.println("Smartphone " + model + " is playing " + supportedFormat + " content");
    }
    
    @Override
    public void stop() {
        System.out.println("Smartphone " + model + " stopped playback");
    }
    
    @Override
    public void pause() {
        System.out.println("Smartphone " + model + " paused playback");
    }
    
    @Override
    public void increaseVolume() {
        volumeLevel += 5;
        System.out.println("Smartphone volume: " + volumeLevel);
    }
    
    @Override
    public void decreaseVolume() {
        volumeLevel -= 5;
        System.out.println("Smartphone volume: " + volumeLevel);
    }
    
    @Override
    public void mute() {
        System.out.println("Smartphone " + model + " muted");
    }
    
    @Override
    public void setVolume(int level) {
        volumeLevel = level;
        System.out.println("Smartphone volume set to: " + volumeLevel);
    }
    
    @Override
    public String getPlayerType() {
        return "Smartphone Media Player";
    }
}
