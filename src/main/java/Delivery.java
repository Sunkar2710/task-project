import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Scanner;
import static java.nio.file.StandardOpenOption.*;

public class Delivery {
    static int customerNumber;

    protected static void addOrder() {
        Scanner sc = new Scanner(System.in);
        Path waitingFile = Paths.get("./waitinglist.txt");
        try (OutputStream out = new BufferedOutputStream(Files.newOutputStream(waitingFile, CREATE, APPEND))) {
            out.write(String.format("Customer #%d:\n", ++customerNumber).getBytes());
            String order;
            int count = 1;
            while (!Objects.equals(order = sc.nextLine(), "")) {
                byte[] data = String.format("%d. %s\n", count, order).getBytes();
                out.write(data);
                count++;
            }
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
