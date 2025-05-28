// 15. Create an interface StringUtils with static methods isNullOrEmpty(String s) and capitalize(String s).
public class Assignment15 {
    public static void main(String[] args) {
        String testString = "hello";
        String nullString = null;
        String emptyString = "";
        System.out.println("Is 'hello' null or empty? " + StringUtils.isNullOrEmpty(testString));
        System.out.println("Is null null or empty? " + StringUtils.isNullOrEmpty(nullString));
        System.out.println("Is empty string null or empty? " + StringUtils.isNullOrEmpty(emptyString));
        System.out.println("Capitalize 'hello': " + StringUtils.capitalize(testString));
        System.out.println("Capitalize null: " + StringUtils.capitalize(nullString));
        System.out.println("Capitalize empty string: " + StringUtils.capitalize(emptyString));
    }
}

interface StringUtils {
    static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }
    static String capitalize(String s) {
        if (isNullOrEmpty(s)) {
            return s;
        }
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }
}
