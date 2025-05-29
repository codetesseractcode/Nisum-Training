import java.util.*;
import java.util.stream.Collectors;

// Problem 14: Sort a list of addresses first by city, then by country using Comparator.thenComparing().
public class Problem14 {
    public static void main(String[] args) {
        List<Address> addresses = createAddressList();
        
        System.out.println("Addresses before sorting:");
        addresses.forEach(addr -> System.out.println(addr.getCity() + ", " + addr.getCountry()));
        
        List<Address> sortedAddresses = addresses.stream()
                .sorted(Comparator.comparing(Address::getCity)
                        .thenComparing(Address::getCountry))
                .collect(Collectors.toList());
        
        System.out.println("\nAddresses after sorting by city, then country:");
        sortedAddresses.forEach(addr -> System.out.println(addr.getCity() + ", " + addr.getCountry()));
    }
    
    private static List<Address> createAddressList() {
        List<Address> addresses = new ArrayList<>();        addresses.add(new Address("123 MG Road", "Mumbai", "India", "400001"));
        addresses.add(new Address("456 Anna Salai", "Chennai", "India", "600001"));
        addresses.add(new Address("789 Brigade Road", "Mumbai", "Bangladesh", "560001"));
        addresses.add(new Address("321 Connaught Place", "Chennai", "Bangladesh", "110001"));
        addresses.add(new Address("654 Park Street", "Kolkata", "India", "700001"));
        return addresses;
    }
}
