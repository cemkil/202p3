package code;

import java.util.ArrayList;
import java.util.Comparator;

import given.Entry;
import given.iAdaptablePriorityQueue;

/*
 * Implement an array based heap
 * Note that you can just use Entry here!
 *
 */

public class ArrayBasedHeap<Key, Value> implements iAdaptablePriorityQueue<Key, Value> {

  // Use this arraylist to store the nodes of the heap. 
  // This is required for the autograder. 
  // It makes your implementation more verbose (e.g. nodes[i] vs nodes.get(i)) but then you do not have to deal with dynamic resizing
  protected ArrayList<Entry<Key,Value>> nodes;
  int size = 0;
  Comparator<Key> comparator = null;
  /*
   *
   * YOUR CODE BELOW THIS
   *
   */

  @Override
  public int size() {
    // TODO Auto-generated method stub
    return size;
  }

  @Override
  public boolean isEmpty() {
    // TODO Auto-generated method stub
    return size == 0;
  }

  @Override
  public void setComparator(Comparator<Key> C) {
    // TODO Auto-generated method stub
    comparator = C;
  }

  @Override
  public Comparator<Key> getComparator() {
    // TODO Auto-generated method stub
    return comparator;
  }

  @Override
  public void insert(Key k, Value v) {
    if(size == 0){
      nodes = new ArrayList<>();
    }
    nodes.add(new Entry<>(k , v));
    size++;
    upHeap(size-1);

  }

  @Override
  public Entry<Key, Value> pop() {
    // TODO Auto-generated method stub
    if(size == 0 ){
      return null;
    }

    Entry< Key ,Value> temp = nodes.get(0);
    swap(0,nodes.size()-1);
    nodes.remove(nodes.size()-1);
    size--;
    downHeap1(0);
    return temp;
  }

  @Override
  public Entry<Key, Value> top() {
    if (size == 0){
      return null;
    }
    return nodes.get(0);
  }

  @Override
  public Value remove(Key k) {
    if (size == 0){
      return null;
    }
    for(int i = 0; i<size ; i++){
      if(getComparator().compare(nodes.get(i).getKey() , k) == 0){
        Value v = nodes.get(i).getValue();
        nodes.remove(i);
        size--;
        makeHeap();
        return v;
      }
    }
    return null;
  }

  @Override
  public Key replaceKey(Entry<Key, Value> entry, Key k) {
    if (size == 0 || k == null){
      return null;
    }
    for(int i = 0; i<nodes.size() ; i++){
      if(getComparator().compare(nodes.get(i).getKey() , entry.getKey()) == 0 && nodes.get(i).getValue().equals(entry.getValue())){
        Key key = nodes.get(i).getKey();
        nodes.get(i).setKey(k);
        makeHeap();
        return key;
      }
    }
    return null;
  }

  @Override
  public Key replaceKey(Value v, Key k) {
    if (size == 0){
      return null;
    }
    for(int i = 0; i<nodes.size() ; i++){
      if(nodes.get(i).getValue().equals(v)){
        Key key = nodes.get(i).getKey();
        nodes.get(i).setKey(k);
        makeHeap();
        return key;
      }
    }
    return null;
  }

  @Override
  public Value replaceValue(Entry<Key, Value> entry, Value v) {
    if (size == 0 || v == null){
      return null;
    }
    for(int i = 0; i<nodes.size() ; i++){
      if(getComparator().compare(nodes.get(i).getKey() , entry.getKey()) == 0 && nodes.get(i).getValue().equals(entry.getValue())){
        Value value = nodes.get(i).getValue();
        nodes.get(i).setValue(v);
        makeHeap();
        return value;
      }
    }
    return null;
  }
  public int parentOf(int i){
    return (i-1)/2;
  }
  public int leftChildOf(int i){
    return 2*i+1;
  }
  public int rightChildOf(int i){
    return 2*i+2;
  }
  public boolean hasLeftChild(int i){
    return leftChildOf(i) < size();
  }
  public boolean hasRightChild(int i){
    return rightChildOf(i) < size();
  }
  public void upHeap(int i){
    while(i > 0){
      int p = parentOf(i);
      if(getComparator().compare(nodes.get(i).getKey() , nodes.get(p).getKey() ) >= 0 ){
        break;
      }
      swap(i , p);
      i =p;
    }
  }

  public void downHeap1(int i){
    while( hasLeftChild(i)){
      int smallChild = leftChildOf(i);
      if(hasRightChild(i)){
        int rc = rightChildOf(i);
        if(getComparator().compare(nodes.get(rc).getKey() , nodes.get(smallChild).getKey()) < 0){
          smallChild = rc;
        }
      }
      if (getComparator().compare(nodes.get(smallChild).getKey() , nodes.get(i).getKey()) >= 0){
        break;
      }else{
        swap(i ,smallChild);
        i = smallChild;
      }
    }
  }

  public void makeHeap(){
    for(int i = nodes.size()/2 ; i>=0 ; i--){
      downHeap1(i);
    }
  }
  public void downHeap2(int index){
    int i = index;
    int lc = 2*i +1;
    int rc = 2*i +2;
    if(lc <= nodes.size()-1 && getComparator().compare(nodes.get(i).getKey() , nodes.get(lc).getKey()) > 0 ){
      i = lc;
    }
    if(rc <= nodes.size()-1 && getComparator().compare(nodes.get(i).getKey() , nodes.get(rc).getKey()) > 0 ){
      i = rc;
    }
    if (i !=index){
      swap(i , index);
      downHeap2(i);
    }
  }
  public void swap(int a ,int b){
    Entry<Key , Value> temp = nodes.get(a);
    nodes.set(a , nodes.get(b));
    nodes.set(b, temp);
  }

}
