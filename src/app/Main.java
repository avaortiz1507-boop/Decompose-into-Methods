package app;

import parts.Engine;
import parts.Wheel;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            // Ask user for engine details
            System.out.print("Enter engine horsepower (positive integer): ");
            int horsepower = Integer.parseInt(sc.nextLine().trim());

            System.out.print("Enter engine type (e.g., gasoline): ");
            String type = sc.nextLine().trim();

            Engine engine = new Engine(horsepower, type);

            // Ask user for wheel details
            System.out.print("Enter number of wheels: ");
            int wheelCount = Integer.parseInt(sc.nextLine().trim());
            Wheel[] wheels = new Wheel[wheelCount];

            for (int i = 0; i < wheelCount; i++) {
                System.out.println("Wheel " + (i + 1) + ":");
                System.out.print("  size (positive number): ");
                double size = Double.parseDouble(sc.nextLine().trim());

                System.out.print("  pressure (non-negative number): ");
                double pressure = Double.parseDouble(sc.nextLine().trim());

                wheels[i] = new Wheel(size, pressure);
            }

            Car car = new Car(engine, wheels);
            System.out.println("Created car: " + car);

        } catch (NumberFormatException e) {
            System.err.println("Invalid number format: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid value: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sc.close();
        }
    }
}
