import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import static java.nio.file.StandardOpenOption.*;

public class Delivery {
    static int customerNumber = 1;

    private static boolean doProceedPurchase() {
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

    protected static void addOrder() {
        Scanner sc = new Scanner(System.in);
        Path waitingFile = Paths.get("./waitinglist.txt");
        List<String> order = new ArrayList<>();
        try (OutputStream out = new BufferedOutputStream(Files.newOutputStream(waitingFile, CREATE, APPEND))) {
            out.write(String.format("Customer #%s:\n", customerNumber).getBytes());
            int count = 1;
            while (doProceedPurchase()) {
                String item = sc.nextLine();
                order.add(item);
                byte[] data = String.format("%d. %s\n", count, item).getBytes();
                out.write(data);
                count++;
            }

            System.out.print("Name: ");
            String name = sc.nextLine();
            System.out.print("Address: ");
            String address = sc.nextLine();

            Customer customer = new Customer(name, address, order);
            System.out.println(customer.getCost());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected static void displayMenu() {
        Path file = Paths.get("./menu.txt");
        try (InputStream in = Files.newInputStream(file);
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
