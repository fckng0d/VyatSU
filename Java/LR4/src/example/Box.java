package example;

import java.util.*;

public class Box<T extends Fruit> {
    private List<T> fruits = new ArrayList<>();
    private final Class<?> fruitType;
    private int countOfFruits = 0;
    private Class<?> removeFruitType;

    public Box(T... fruits) {
        if (fruits.length == 0) {
            throw new RuntimeException("Нельзя создать пустую коробку");
        }
        this.fruits.addAll(Arrays.asList(fruits));
        this.countOfFruits = fruits.length;
        this.fruitType = fruits[0].getClass();
    }

    public void addFruit(int count) {
        if (count > 0) {
            for (int i = 0; i < count; i++) {
                try {
                    fruits.add((T) fruitType.newInstance());
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                countOfFruits++;
            }
            System.out.println("\nВ коробку было добавлено " + count + " фруктов\n");
        } else {
            System.out.println("\nЧисло добавляемых фруктов должно быть > 0\n");
        }
    }

    public <V extends T> void addFruit(int count, V fruit) {
        if (count > 0) {

            for (int i = 0; i < count; i++) {
                try {
                    fruits.add((T) fruit.getClass().newInstance());
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                countOfFruits++;
                System.out.println("\nВ коробку было добавлено " + count + " фруктов\n");
            }
        }
    }

    private void takeOutFruit(int count) {
        int size = fruits.size();
        if (count <= size) {
            fruits.removeIf(fruit -> fruit.getClass() == removeFruitType);
            countOfFruits -= count;
        } else {
            fruits.clear();
            countOfFruits = 0;
        }
        removeFruitType = null;
    }

    public boolean compareBoxesWeight(Box<? extends Fruit> box) {
        if (!this.equals(box)) {
            return Math.abs(this.getBoxWeight() - box.getBoxWeight()) < 0.0001;
        } else {
            System.out.println("\nНельзя сравнивать коробку саму с собой\n");
        }
        return false;
    }

    public void transferFruit(Box<T> boxTo) {
        if (!this.equals(boxTo)) {
            int countOfTransferFruit = 0;
            removeFruitType = fruitType;
            for (T fruit : fruits) {
                if (fruit.getClass() == boxTo.fruitType) {
                    boxTo.getFruits().add(fruit);
                    boxTo.countOfFruits++;
                    countOfTransferFruit++;
                }
            }
            takeOutFruit(countOfTransferFruit);

            if (countOfTransferFruit != 0) {
                System.out.println("\nБыло переложено " + countOfTransferFruit + " фруктов \n");
            }
        }
    }

    public float getBoxWeight() {
        float weight = 0.0f;
        if (fruits.isEmpty()) {
            return 0.0f;
        } else {
            for (T fruit : fruits) {
                weight += fruit.getWeight();
            }
            return weight;
        }
    }

    public static void printBox(Box box) {
        if (!box.getFruits().isEmpty()) {
            System.out.println(translateType(box) + "(" + box.countOfFruits +
                    " штук(и), общий вес = " + box.getBoxWeight() + "):");

            System.out.println(box.getFruits() + "\n");
        } else {
            System.out.println(translateType(box) + " пуста\n");
        }
    }

    private static String translateType(Box box) {
        if (box.fruitType == Apple.class) {
            return "Коробка с яблоками ";
        } else if (box.fruitType == Orange.class) {
            return "Коробка с апельсинами ";
        } else {
            return "Коробка с лимонами ";
        }
    }

    public List<T> getFruits() {
        return fruits;
    }
}