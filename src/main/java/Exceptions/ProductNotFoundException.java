package Exceptions;

import java.util.Collection;
import java.util.stream.Collectors;

public class ProductNotFoundException extends FramworkException {
    public ProductNotFoundException(String requestedProduct, Collection<String> availableProducts) {
        super(buildMessage(requestedProduct, availableProducts));
    }

    private static String buildMessage(String requestedProduct, Collection<String> availableProducts) {
        String available = availableProducts == null || availableProducts.isEmpty()
                ? "<none found on page>"
                : availableProducts.stream().collect(Collectors.joining(", "));
        return "Product '" + requestedProduct + "' was not found on the page. "
                + "Products actually listed: [" + available + "]";
    }
}
