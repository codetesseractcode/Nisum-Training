// 11. Create two interfaces with the same default method run(). 
// Implement both in a class and resolve the conflict.
public class Assignment11 {
    public static void main(String[] args) {
        InterfaceImplementer implementer = new InterfaceImplementer();
        implementer.run();
    }
}

interface Interface1 {
    default void run() {
        System.out.println("Default run method from Interface1");
    }
}

interface Interface2 {
    default void run() {
        System.out.println("Default run method from Interface2");
    }
}

class InterfaceImplementer implements Interface1, Interface2 {
    @Override
    public void run() {
        System.out.println("Overriding the default run method to resolve conflict");
        Interface1.super.run();
    }
}
