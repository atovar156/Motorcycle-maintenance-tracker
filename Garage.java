import java.util.ArrayList;
import java.util.List;

public class Garage {
    private List<Motorcycle> motorcycles;

    public Garage() {
        this.motorcycles = new ArrayList<>();
    }

    public void addMotorcycle(Motorcycle m) {
        motorcycles.add(m);
    }

    public Motorcycle getMotorcycleByModel(String model) {
        for (Motorcycle m : motorcycles) {
            if (m.getModel().equalsIgnoreCase(model)) {
                return m;
            }
        }
        return null;
    }

    public List<Motorcycle> getAll() {
        return motorcycles;
    }

    public void listAll() {
        for (int i = 0; i < motorcycles.size(); i++) {
            System.out.println((i + 1) + ". " + motorcycles.get(i)) ;
        }
    }
}