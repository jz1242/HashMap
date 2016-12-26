/**Jason Zhang jzhan127@jhu.edu
 *
 * @param <K>
 * @param <V>
 */
import java.util.ArrayList;
import java.util.Iterator;


public class HashMap<K, V> implements Map<K, V> {
    private Entry[] data;
    private int pairs;
    private int size;
    
    private static class Entry<K, V> {
        K key;
        V value;

        Entry(K k, V v){
            this.key = k;
            this.value = v;
            
        }
    }
    public HashMap(){
        this.size = 10;
        this.pairs = 0;
        this.data = new Entry[10];
 
    }
    private final class HashMapIterator implements Iterator<K> {
        private int start;
        private int end;
        private ArrayList<K> s;
        
        public HashMapIterator(){
            this.start = 0;
            this.end = end;
            s = new ArrayList<K>();
            for (int i = 0; i < data.length; i++){
                if(data[i] != null){
                    s.add((K) data[i].key);
                }
            }
        }
        public boolean hasNext(){
            return this.start < s.size();
            
        }
        @Override
        public K next() {
            return s.get(start++);
        }
    
    }
    
    @Override
    public Iterator<K> iterator() {

        return new HashMapIterator();
    }
    private int hash(K key, int a ){
        return Math.abs(key.hashCode() % a);
    }
    
    private Entry find(K key){
        if(key == null){
            throw new IllegalArgumentException();
        }
        int a = hash(key, size);
        
        for (int i = a; data[i] != null; i = (i + 1) % size){ 
                if (key.equals(data[i].key)){
                    return data[i];
                }
            
        }

        return null;
    }
    @Override
    public void insert(K key, V value) throws IllegalArgumentException {
        Entry n = this.find(key);
        if(n != null){
            throw new IllegalArgumentException();
        }
        n = new Entry(key, value);
        pairs++;
        insert(n);
        
       
        
    }
    private Entry insert(Entry n){
        if(pairs >= size/2){
            grow(2 * size);
        }
        int index = hash((K) n.key, size);
        while (this.data[index] != null){
            index = (index + 1) % size;
        }
        this.data[index] = n;
        return n;
    }
    
    private void grow(int a){
        Entry[] temp = new Entry[a];
        for(int i = 0; i < size; i++){
            if(data[i] != null){
               int index = hash((K) data[i].key, a);
               while (temp[index] != null){
                   index = (index + 1) % a;
               }
               temp[index] = data[i];
              
            }
        }
        this.size = a;
        this.data = temp;
    }

    @Override
    public V remove(K k) throws IllegalArgumentException {
        Entry n = this.find(k);
        if(n == null){
            throw new IllegalArgumentException();
        }
        V hold = (V) n.value;
        int index = hash(k, size);
        while(!k.equals(this.data[index].key)){
            index = (index + 1) % size;
        }
        this.data[index].key = null;
        this.data[index].value = null;
        pairs--;
        
        index = (index + 1) % size;
        while(this.data[index] != null){
            Entry temp = this.data[index];
            K temp2 = (K) this.data[index].key;
            V temp3 = (V) this.data[index].value;
            this.data[index].key = null;
            this.data[index].value = null;
            this.data[index] = null;
            pairs--;
            insert(temp2, temp3);
            index = (index + 1) % size;
        }
        
        return hold;
    }

    @Override
    public void put(K k, V v) throws IllegalArgumentException {
        Entry n = this.find(k);
        if(n == null){
            throw new IllegalArgumentException();
        }
        
        n.value = v;
        
    }

    @Override
    public V get(K k) throws IllegalArgumentException {
        Entry n = this.find(k);
        if(n == null){
            throw new IllegalArgumentException();
        }
        return (V) n.value;
    }

    @Override
    public boolean has(K k) {
        if(k == null){
            return false;
        }
        return this.find(k) != null;
    }

    @Override
    public int size() {
        return pairs;
    }
    
    public String toString(){
        String total = "";
        for(int i = 0; i < this.data.length; i++){
            if(data[i] != null){
                total += data[i].key + "->" + data[i].value + "\n";
            }
        }
        return total;
    }


}
