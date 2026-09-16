package Models;

public class Customer {
    private final String firstName;
    private final String lastName;
    private final String postalCode;
    private final String productToBuy;

    public Customer(String firstName, String lastName, String postalCode, String productToBuy) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.postalCode = postalCode;
        this.productToBuy = productToBuy;
    }

    public String firstName() {
        return firstName;
    }

    public String lastName() {
        return lastName;
    }

    public String postalCode() {
        return postalCode;
    }

    public String productToBuy() {
        return productToBuy;
    }

    @Override
    public String toString() {
        return "Customer{firstName='" + firstName + "', lastName='" + lastName
                + "', postalCode='" + postalCode + "', productToBuy='" + productToBuy + "'}";
    }
}
