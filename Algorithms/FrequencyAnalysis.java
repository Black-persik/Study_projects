//Ivan Vasilev CSE-01
import java.util.*;
public class FrequencyAnalysis {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numberOfWords = scanner.nextInt();
        scanner.nextLine();
        int frequency = scanner.nextInt();
        scanner.nextLine();
        String line = scanner.nextLine();
        String[] words = line.split(" ");
        HashMap<String, Integer> hashTable = new HashMap<>(numberOfWords);
        hashTable.put(words[0], 1);
        int maxCount = -199;

        for (int i = 1; i < numberOfWords; i++) {
            if (hashTable.get(words[i]) == null) {
                hashTable.put(words[i], 1);
                if (hashTable.get(words[i]) >= maxCount) {
                    maxCount = hashTable.get(words[i]);

                }
            } else {
                hashTable.put(words[i], hashTable.get(words[i]) + 1);
                if (hashTable.get(words[i]) >= maxCount) {
                    maxCount = hashTable.get(words[i]);

                }
            }
        }
        String[][] helpMe = new String[hashTable.keyOrder.size()][2];
        for (int i = 0; i < hashTable.keyOrder.size(); i++) {
            helpMe[i][0] = hashTable.keyOrder.get(i);
            helpMe[i][1] = hashTable.get(hashTable.keyOrder.get(i)).toString();
        }
        for (int i = 0; i < helpMe.length; i++) {
            for (int j = 1; j < helpMe.length - i; j++) {
                if (Integer.parseInt(helpMe[j - 1][1]) < Integer.parseInt(helpMe[j][1])) {
                    String[] temp = helpMe[j - 1];
                    helpMe[j - 1] = helpMe[j];
                    helpMe[j] = temp;
                }
            }
        }


        System.out.println(helpMe[frequency - 1][0]);
    }
}

interface Map<K, V>{
    void put(K key, V value);
    V get(K key);
    void remove(K key);
    int size();
    boolean isEmpty();
}
class KeYvaluePair<K, V> {
    K key;
    V value;
    public KeYvaluePair(K key, V value) {
        this.key = key;
        this.value = value;
    }
}
class HashMap<K, V> implements Map<K, V>{
    List<KeYvaluePair> hashTable[];
    int tableCapacity;
    int numberOfElements;
    List<K> keyOrder =  new ArrayList<>();
    @Override
    public void put(K key, V value) {
        int index = Math.abs(key.hashCode() % tableCapacity);
        for(KeYvaluePair<K, V> kv: this.hashTable[index]){
            if(kv.key.equals(key)){
                kv.value = value;
                return;
            }
        }
        this.hashTable[index].add(new KeYvaluePair<>(key, value));
        ++this.numberOfElements;

        if (!keyOrder.contains(key)) {
            keyOrder.add(key);
        }

    }

    @Override
    public V get(K key) {
        int index = Math.abs(key.hashCode() % this.tableCapacity);
        for (KeYvaluePair<K, V> kv: this.hashTable[index]){
            if(kv.key.equals(key)){
                return kv.value;
            }
        }
        return null;
    }

    @Override
    public void remove(K key) {
        int index = Math.abs(key.hashCode() % this.tableCapacity);
        for (KeYvaluePair<K, V> kv: this.hashTable[index]){
            if(kv.key.equals(key)){
                kv.value = null;
                break;
            }
        }
    }

    @Override
    public int size() {
        return this.numberOfElements;
    }

    @Override
    public boolean isEmpty() {
        return this.numberOfElements == 0;
    }
    public HashMap(int tableCapacity) {
        this.numberOfElements = 0;
        this.tableCapacity = tableCapacity;
        this.hashTable = new List[tableCapacity];
        for (int i = 0; i < tableCapacity; ++i) {
            this.hashTable[i] = new LinkedList<>();
        }
    }
}

