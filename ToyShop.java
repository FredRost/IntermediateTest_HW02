import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Random;

public class ToyShop {

    private static final Random random = new Random();
    private static final int[] weights = {20, 20, 60}; // Веса 1, 2 и 3

    public static void main(String[] args) {
        PriorityQueue<Toy> toyQueue = new PriorityQueue<>();

        // Добавление игрушек в очередь
        addToy(toyQueue, "1", "робот", 2);
        addToy(toyQueue, "2", "конструктор", 2);
        addToy(toyQueue, "3", "кукла", 6);

        // Создание и запись 10 результатов в файл
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("results.txt"))) {
            for (int i = 0; i < 10; i++) {
                Toy randomToy = getToyWithWeightedProbability(toyQueue);
                writer.write(randomToy.getId() + ": " + randomToy.getName() + "\n");
            }
            System.out.println("Результаты успешно записаны в файл 'results.txt'");
        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }

    private static void addToy(PriorityQueue<Toy> toyQueue, String id, String name, int weight) {
        Toy toy = new Toy(id, name, weight);
        toyQueue.add(toy);
    }

    private static Toy getToyWithWeightedProbability(PriorityQueue<Toy> toyQueue) {
        int totalWeight = weights[0] + weights[1] + weights[2];
        int randomValue = random.nextInt(totalWeight);

        int cumulativeWeight = 0;
        for (int i = 0; i < weights.length; i++) {
            cumulativeWeight += weights[i];
            if (randomValue < cumulativeWeight) {
                return toyQueue.poll();
            }
        }

        return null; // Не должно возникать
    }

    static class Toy implements Comparable<Toy> {
        private String id;
        private String name;
        private int weight;

        public Toy(String id, String name, int weight) {
            this.id = id;
            this.name = name;
            this.weight = weight;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getWeight() {
            return weight;
        }

        @Override
        public int compareTo(Toy other) {
            return Integer.compare(this.getWeight(), other.getWeight());
        }
    }
}
