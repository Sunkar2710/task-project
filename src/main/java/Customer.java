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

    public double getCost() {
        Path file = Paths.get(".\\menu.txt");
        Charset charset = StandardCharsets.US_ASCII;
        try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
            String line;
            double cost = 0.0;

            while ((line = reader.readLine()) != null) {
                if (!line.contains(": $")) continue;

                for (String item : order) {
                    if (line.contains(item + ": $")) {
                        String price = line.split(": \\$")[1].trim();
                        cost += Double.parseDouble(price);
                    }
                }
            }
            return cost;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
