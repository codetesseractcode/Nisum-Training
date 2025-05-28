// 3. How does the @FunctionalInterface annotation work? Is it mandatory?
public class Assignment3 {
    public static void main(String[] args) {
        System.out.println("The @FunctionalInterface annotation ensures that the interface has exactly one abstract method.");
        System.out.println("It's not mandatory but helps in compile-time checking and documentation.");
        
        MyInterface myLambda = () -> System.out.println("Implementation of the abstract method");
        myLambda.myMethod();
    }
}

@FunctionalInterface
interface MyInterface {
    void myMethod();
    
    default void defaultMethod() {
        System.out.println("This is a default method");
    }
    
    static void staticMethod() {
        System.out.println("This is a static method");
    }
}
