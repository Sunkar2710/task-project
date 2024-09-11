import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import static java.nio.file.StandardOpenOption.*;

public class Delivery {
    int customerNumber = 1;
    Customer customer;

    private boolean doProceedPurchase() {
        Scanner sc = new Scanner(System.in);
        System.out.print("if you wish proceed with the purchase, press 1. If you don't, press 0: ");
        byte choice = sc.nextByte();
        return switch (choice) {
            case 0 -> false;
            case 1 -> true;
            default -> {
                System.out.println("Invalid value. Please press either 0 or 1");
                yield doProceedPurchase();
            }
        };
    }

    protected void addOrder() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter your name: ");
        String name = sc.nextLine();
        System.out.print("Enter your address: ");
        String address = sc.nextLine();

        Path waitingFile = Paths.get("./waitinglist.txt");
        List<String> order = new ArrayList<>();
        try (OutputStream out = new BufferedOutputStream(Files.newOutputStream(waitingFile, CREATE, APPEND))) {
            out.write(String.format("Customer #%s:\n", customerNumber).getBytes());
            out.write(String.format("Name: %s\n", name).getBytes());
            out.write(String.format("Address: %s\n", address).getBytes());
            int count = 1;
            while (doProceedPurchase()) {
                String item = sc.nextLine();
                order.add(item);
                byte[] data = String.format("%d. %s\n", count, item).getBytes();
                out.write(data);
                count++;
            }

            customer = new Customer(name, address, order);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private double getCost() {
        Path file = Paths.get(".\\menu.txt");
        Charset charset = StandardCharsets.US_ASCII;
        try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
            String line;
            double cost = 0.0;
            List<String> order = customer.getOrder();

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

    protected void displayOrder() {
        if (customer == null) return;
        System.out.printf("Name: %s\n", customer.getName());
        System.out.printf("Address: %s\n", customer.getAddress());
        int size = customer.getOrder().size();
        for (int i = 0; i < size; i++) {
            int num = i + 1;
            System.out.printf("%d. %s\n", num, customer.getOrder().get(i));
        }
        System.out.printf("Cost: %.2f", getCost());
    }

    protected void displayMenu() {
        Path file = Paths.get("./menu.txt");
        try (InputStream in = Files.newInputStream(file);
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
