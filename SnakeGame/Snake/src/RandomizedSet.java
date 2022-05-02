import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class RandomizedSet<T> {
    Map<T, Integer> map;
    List<T> allValues;

    public RandomizedSet() {
        map = new HashMap<>();
        allValues = new ArrayList<>();
    }

    public boolean insert(T val) {
        if (map.containsKey(val)) return false;

        allValues.add(val);
        map.put(val, allValues.size() - 1);

        return true;
    }

    public boolean remove(T val) {
        if (!map.containsKey(val)) return false;

        allValues.set(map.get(val), allValues.get(allValues.size() - 1));
        map.put(allValues.get(allValues.size() - 1), map.get(val));

        map.remove(val);
        allValues.remove(allValues.size() - 1);

        return true;
    }

    public T getRandom() {
        int randIndex = (int)(Math.random() * allValues.size());
        if (randIndex < allValues.size()) return allValues.get(randIndex);
        return null;
    }
}