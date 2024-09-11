import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Customer {
    private String name;
    private String address;
    private List<String> order;
    private double cost;

    public Customer(String name, String address, List<String> order) {
        this.name = name;
        this.address = address;
        this.order = order;
    }

    public void setAddress(String value) {
        address = value;
    }

    public String getAddress() {
        return address;
    }

    public void setName(String value) {
        name = value;
    }

    public String getName() {
        return name;
    }

    public List<String> getOrder() {
        return order;
    }
}
