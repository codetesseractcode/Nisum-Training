// 12. Show how to call a default method from an implementing class when it's overridden.
public class Assignment12 {
    public static void main(String[] args) {
        DefaultMethodImpl impl = new DefaultMethodImpl();
        impl.callDefaultMethod();
    }
}

interface DefaultMethod {
    default void show() {
        System.out.println("Default method from interface");
    }
}

class DefaultMethodImpl implements DefaultMethod {
    @Override
    public void show() {
        System.out.println("Overridden method in the class");
    }
    
    public void callDefaultMethod() {
        System.out.println("Calling overridden method:");
        show();
        
        System.out.println("Calling original default method from interface:");
        DefaultMethod.super.show(); 
    }
}
