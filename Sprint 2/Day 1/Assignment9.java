// 9. How many default methods we can have in Functional interface
public class Assignment9 {
    public static void main(String[] args) {
        System.out.println("A functional interface can have any number of default methods.");
        System.out.println("Only the number of abstract methods is restricted to exactly one.");
        
        MyFunctionalInterface instance = () -> System.out.println("Abstract method implementation");
        
        instance.abstractMethod();
        instance.defaultMethod1();
        instance.defaultMethod2();
        instance.defaultMethod3();
    }
}

@FunctionalInterface
interface MyFunctionalInterface {
    void abstractMethod();
    
    default void defaultMethod1() {
        System.out.println("Default method 1");
    }
    
    default void defaultMethod2() {
        System.out.println("Default method 2");
    }
    
    default void defaultMethod3() {
        System.out.println("Default method 3");
    }
}
