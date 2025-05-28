// 13. Demonstrate calling an interface's default method using InterfaceName.super.methodName()
public class Assignment13 {
    public static void main(String[] args) {
        SuperMethodCaller caller = new SuperMethodCaller();
        caller.callMethod();
    }
}

interface SuperMethod {
    default void method() {
        System.out.println("Default method in SuperMethod interface");
    }
}

class SuperMethodCaller implements SuperMethod {
    @Override
    public void method() {
        System.out.println("Overridden method in SuperMethodCaller class");
    }
    
    public void callMethod() {
        System.out.println("Calling overridden method:");
        method(); 
        
        System.out.println("Calling interface's default method using super:");
        SuperMethod.super.method();
    }
}
