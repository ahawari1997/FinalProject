package Swaglabs.dataproviders;

import Models.Customer;
import Utils.CsvReader;
import org.testng.annotations.DataProvider;

import java.util.List;

public class DataProviders {
    private DataProviders() {
    }
    @DataProvider(name = "rejectedLogins")
    public static Object[][] rejectedLogins() {
        return new Object[][]{
                {"locked_out_user", "secret_sauce", "Sorry, this user has been locked out."},
                {"standard_user", "wrong_password", "Username and password do not match"},
                {"not_a_real_user", "secret_sauce", "Username and password do not match"},
                {"", "secret_sauce", "Username is required"},
                {"standard_user", "", "Password is required"},
        };
    }
    @DataProvider(name = "missingCheckoutFields")
    public static Object[][] missingCheckoutFields() {
        return new Object[][]{
                {"", "Doe", "10001", "First Name is required"},
                {"John", "", "10001", "Last Name is required"},
                {"John", "Doe", "", "Postal Code is required"},
        };
    }
    @DataProvider(name = "customers")
    public static Object[][] customers() {
        List<String[]> rows = CsvReader.readRows("testdata/customers.csv");
        Object[][] data = new Object[rows.size()][1];
        for (int i = 0; i < rows.size(); i++) {
            String[] row = rows.get(i);
            data[i][0] = new Customer(row[0], row[1], row[2], row[3]);
        }
        return data;
    }
}
