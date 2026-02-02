package parts;

public class Engine {
    // horsepower and type
    private int horsepower;
    private String type;

    // Constructor
    public Engine(int horsepower, String type) {
        setHorsepower(horsepower);
        setType(type);
    }

    // Getters
    public int getHorsepower() {
        return horsepower;
    }

    public String getType() {
        return type;
    }

    // setters
    // Engine horsepower must be a positive number
    public void setHorsepower(int horsepower) {
        if (horsepower <= 0) {
            throw new IllegalArgumentException("Horsepower must be positive");
        }
        this.horsepower = horsepower;
    }

    public void setType(String type) {
        if (type == null || type.isEmpty()) {
            throw new IllegalArgumentException("Engine type cannot be null or empty");
        }
        this.type = type;
    }

    @Override
    public String toString() {
        return "Engine{horsepower=" + horsepower + ", type='" + type + "'}";
    }
}