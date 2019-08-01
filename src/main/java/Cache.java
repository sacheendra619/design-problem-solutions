import java.util.HashMap;

/**
 * Data structure to hold key and data and links to prev and next Node.
 * @param <K>
 * @param <V>
 */
class Node<K,V> {
    V data;
    K key;
    Node<K,V> next;
    Node<K,V> prev;
    public Node(K key, V data, Node<K,V> next, Node<K,V> prev) {
        this.data = data;
        this.next = next;
        this.prev = prev;
        this.key = key;
    }
}
/**
 * Simple Doubly Linked List Implementation with only few methods implemented.
 * @param <K>
 * @param <V>
 */
class DoublyLinkedList<K,V> {
    private Node<K,V> head;
    private Node<K,V> tail;
    private int len = 0;

    public DoublyLinkedList() {
        this.head = new Node<>(null,null,null,null);
        this.tail = new Node<>(null,null,null,null);
        this.head.next = tail;
        this.tail.prev = head;
        this.len = 2;
    }

    public void deleteNode(Node<K,V> node){
        node.prev.next = node.next;
        node.next.prev = node.prev;
        len--;
    }

    public void addToHead(Node<K,V> node) {
        node.next = head.next;
        node.next.prev = node;
        node.prev = head;
        head.next = node;
        len++;
    }
    public int getLen(){
        return len-2;
    }
    public Node<K,V> getTail(){
        return tail;
    }
}

/**
 * basic LRU cache implementation
 * @param <K> key of the cache
 * @param <V> data corresponding to that key.
 */
class LRUCache<K,V> {
    private HashMap<K,Node<K,V>> hashMap;
    DoublyLinkedList<K,V> doublyLinkedList;
    private int size;
    public LRUCache(int size) {
        this.size = size;
        hashMap = new HashMap<>();
        doublyLinkedList = new DoublyLinkedList<>();
    }
    public V get(K key) {
        if(hashMap.containsKey(key)) {
            Node<K,V> node = hashMap.get(key);
            doublyLinkedList.deleteNode(node);
            doublyLinkedList.addToHead(node);
            return node.data;
        }
        return null;
    }
    public void put(K key, V value){
        if(hashMap.containsKey(key)){
            Node<K,V> node = hashMap.get(key);
            node.data = value;
            doublyLinkedList.deleteNode(node);
            doublyLinkedList.addToHead(node);
        } else {
            Node<K,V> node = new Node<>(key,value,null,null);
            hashMap.put(key,node);
            if(doublyLinkedList.getLen() < size){
                doublyLinkedList.addToHead(node);
            } else {
                Node<K,V> lastNode = doublyLinkedList.getTail().prev;
                hashMap.remove(lastNode.key);
                doublyLinkedList.deleteNode(lastNode);
                doublyLinkedList.addToHead(node);
            }
        }
    }
}

class Data {
    String name;
    Integer rollNo;

    public Data(String name, Integer rollNo) {
        this.name = name;
        this.rollNo = rollNo;
    }
}
public class Cache {
    public static void main(String[] args) {
        LRUCache<Integer,Data> cache = new LRUCache<>(5);
        cache.put(1,new Data("Sacheendra",24));
        cache.put(2,new Data("Alia",12));
        System.out.println(cache.get(2).name + " " + cache.get(2).rollNo);
        System.out.println(cache.get(1).name + " " + cache.get(1).rollNo);
    }
}