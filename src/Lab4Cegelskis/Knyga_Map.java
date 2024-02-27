/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Lab4Cegelskis;

import java.util.Arrays;
import laborai.studijosktu.HashType;
import laborai.studijosktu.MapADT;

/**
 *
 * @author zygis
 */
public class Knyga_Map<K,V> implements MapADT<K,V>{
    
    public static final int DEFAULT_INITIAL_CAPACITY = 16;
    public static final float DEFAULT_LOAD_FACTOR = 0.75f;
    public static final HashType DEFAULT_HASH_TYPE = HashType.DIVISION;
    
    // Maišos lentelė
    protected Node<K, V>[] table;
    // Lentelėje esančių raktas-reikšmė porų kiekis
    protected int size = 0;
    // Apkrovimo faktorius
    protected float loadFactor;
    // Maišos metodas
    protected HashType ht;
    //--------------------------------------------------------------------------
    //  Maišos lentelės įvertinimo parametrai
    //--------------------------------------------------------------------------
    // Maksimalus suformuotos maišos lentelės grandinėlės ilgis
    //protected int maxChainSize = 0;
    // Permaišymų kiekis
    protected int rehashesCounter = 0;
    // Paskutinės patalpintos poros grandinėlės indeksas maišos lentelėje
    //protected int lastUpdatedChain = 0;
    // Lentelės grandinėlių skaičius     
    //protected int chainsCounter = 0;
    // Einamas poros indeksas maišos lentelėje
    protected int index = 0;
    // Amount of blocks in Map
    protected int blockCount = 0;
    
     public Knyga_Map() {
        this(DEFAULT_HASH_TYPE);
    }

    public Knyga_Map(HashType ht) {
        this(DEFAULT_INITIAL_CAPACITY, ht);
    }

    public Knyga_Map(int initialCapacity, HashType ht) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR, ht);
    }

    public Knyga_Map(float loadFactor, HashType ht) {
        this(DEFAULT_INITIAL_CAPACITY, loadFactor, ht);
    }

    public Knyga_Map(int initialCapacity, float loadFactor, HashType ht) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
        }

        if ((loadFactor <= 0.0) || (loadFactor > 1.0)) {
            throw new IllegalArgumentException("Illegal load factor: " + loadFactor);
        }

        this.table = new Node[initialCapacity];
        this.loadFactor = loadFactor;
        this.ht = ht;
    }
    
     void addBlockCount(int index) {
        int oneLess, oneMore;
        if (index != 0) {
            oneLess = index - 1;
        } else {
            oneLess = -1;
        }

        if (index != table.length - 1) {
            oneMore = index + 1;
        } else {
            oneMore = -1;
        }

        if (oneLess == -1) {
            if (table[oneMore] == null) {
                blockCount++;
            }
        } else if (oneMore == -1) {
            if (table[oneLess] == null) {
                blockCount++;
            }
        } else if (table[oneLess] == null && table[oneMore] == null) {
            blockCount++;
        } else if (table[oneLess] != null && table[oneMore] != null) {
            blockCount--;
        }
    }
     
     void removeBlockCount(int index) {
        int oneLess, oneMore;
        if (index != 0) {
            oneLess = index - 1;
        } else {
            oneLess = -1;
        }

        if (index != table.length - 1) {
            oneMore = index + 1;
        } else {
            oneMore = -1;
        }
        //chainsCounter++;
        if (oneLess == -1) {
            if (table[oneMore] == null) {
                blockCount--;
            }
        } else if (oneMore == -1) {
            if (table[oneLess] == null) {
                blockCount--;
            }
        } else if (table[oneLess] == null && table[oneMore] == null) {
            blockCount--;
        } else if (table[oneLess] != null && table[oneMore] != null) {
            blockCount++;
        }
    }
     
      /**
     * Maišos funkcijos skaičiavimas: pagal rakto maišos kodą apskaičiuojamas
     * atvaizdžio poros indeksas maišos lentelės masyve
     *
     * @param key
     * @param hashType
     * @return
     */
    private int hash(K key, HashType hashType) {
        int h = key.hashCode();
        switch (hashType) {
            case DIVISION:
                return Math.abs(h) % table.length;
            case MULTIPLICATION:
                double k = (Math.sqrt(5) - 1) / 2;
                return (int) (((k * Math.abs(h)) % 1) * table.length);
            case JCF7:
                h ^= (h >>> 20) ^ (h >>> 12);
                h = h ^ (h >>> 7) ^ (h >>> 4);
                return h & (table.length - 1);
            case JCF8:
                h = h ^ (h >>> 16);
                return h & (table.length - 1);
            default:
                return Math.abs(h) % table.length;
        }
    }
    
     /**
     * Atvaizdis papildomas nauja pora.
     *
     * @param key raktas,
     * @param value reikšmė.
     * @return Atvaizdis papildomas nauja pora.
     */
    @Override
    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Key or value is null in put(Key key, Value value)");
        }
        index = hash(key, ht);
        
        if (table[index] == null) {
            table[index] = new Node<>(key, value);
            addBlockCount(index);
            size++;
        } else if (table[index].key.toString().compareTo(key.toString()) != 0){
            int i = 1;
            boolean added = false;
            while (i < table.length && added == false) {
                int newIndex = (index + i) % table.length;
                if (table[newIndex] == null) {
                    table[newIndex] = new Node<>(key, value);
                    size++;
                    addBlockCount(newIndex);
                    added = true;
                }
                if (size > table.length * loadFactor) {
                    rehash(table[index]);
                }
                i++;
            }
        }
        return value;
    }
    
    /**
     * Grąžinama atvaizdžio poros reikšmė.
     *
     * @return Grąžinama atvaizdžio poros reikšmė.
     *
     * @param key raktas.
     */
    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key is null in get(Key key)");
        }

        index = hash(key, ht);
        Node<K, V> node = null;
        if (table[index] != null && table[index].key.toString().compareTo(key.toString()) == 0) {
            node = table[index];
        } else if (table[index] != null) {
            int i = 1;
            while (i < table.length) {
                int newIndex = (index + i) % table.length;
                if (table[newIndex] != null && table[newIndex].key.toString().compareTo(key.toString()) == 0) {
                    node = table[newIndex];
                    i = table.length;
                }
                i++;
            }
        }
        return (node != null) ? node.value : null;
    }
    
    /**
     * Pora pašalinama iš atvaizdžio.
     *
     * @param key Pora pašalinama iš atvaizdžio.
     * @return key raktas.
     */
    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key is null in remove(Key key)");
        }
        V removed;
        index = hash(key, ht);
        //String daKey = table[index].key;
        if (table[index] != null && table[index].key.toString().compareTo(key.toString()) == 0) {
            removed = get(key);
            table[index] = null;
            size--;
            removeBlockCount(index);

            return removed;
        } else {
            int i = 1;
            while (i < table.length) {
                int newIndex = (index + i) % table.length;
                if (table[newIndex] != null && table[newIndex].key.toString().compareTo(key.toString()) == 0) {
                    removed = get(key);
                    table[newIndex] = null;
                    size--;
                    removeBlockCount(newIndex);
                    return removed;
                }
                i++;
            }
        }
        return null;
    }

   /**
     * Patikrinama ar pora egzistuoja atvaizdyje.
     *
     * @param key raktas.
     * @return Patikrinama ar pora egzistuoja atvaizdyje.
     */
    @Override
    public boolean contains(K key) {
        return get(key) != null;
    }

    //Techniskai nebutini
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        Arrays.fill(table, null);
        size = 0;
        index = 0;
        rehashesCounter = 0;
        blockCount = 0;
    }

    @Override
    public String[][] toArray() {
        String[][] result = new String[table.length][];
        int count = 0;
        for (Node<K, V> n : table) {
            result[count][0] = n.toString();
            count++;
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Node<K, V> node : table) {
            if (node != null) {

                result.append(node.toString()).append(System.lineSeparator());

            }
        }
        return result.toString();
    }

    /**
     * Grąžina formuojant maišos lentelę įvykusių permaišymų kiekį.
     *
     * @return Permaišymų kiekis.
     */
    @Override
    public int getRehashesCounter() {
        return rehashesCounter;
    }

    /**
     * Grąžina maišos lentelės talpą.
     *
     * @return Maišos lentelės talpa.
     */
    @Override
    public int getTableCapacity() {
        return table.length;
    }

    @Override
    public int getEmptyCounter() {
        return table.length - size;
    }

    @Override
    public int getBlockCounter() {
        return blockCount;
    }

    /**
     * Permaišymas
     *
     * @param node
     */
    private void rehash(Node<K, V> node) {
        Knyga_Map knygu_map
                = new Knyga_Map(table.length * 2, loadFactor, ht);
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                knygu_map.put(table[i].key, table[i].value);
            }
        }
        table = knygu_map.table;
        blockCount = knygu_map.blockCount;
        rehashesCounter++;
    }

    protected class Node<K, V> {

        // Raktas        
        protected K key;
        // Reikšmė
        protected V value;

        protected Node() {
        }

        protected Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return key + "=" + value;
        }
    }    
}
