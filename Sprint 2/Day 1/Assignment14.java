// 14. Create an interface with a static method int square(int x). Call it from the main method.
public class Assignment14 {
    public static void main(String[] args) {
        int result = StaticMethodInterface.square(5);
        System.out.println("5 squared = " + result);
    }
}

interface StaticMethodInterface {
    static int square(int x) {
        return x * x;
    }
}
