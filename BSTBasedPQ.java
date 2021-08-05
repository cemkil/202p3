package code;

import given.Entry;
import given.iAdaptablePriorityQueue;

/*
 * Implement a binary search tree based priority queue
 * Do not try to create heap behavior (e.g. no need for a last node)
 * Just use default binary search tree properties
 */

public class BSTBasedPQ<Key, Value> extends BinarySearchTree<Key, Value> implements iAdaptablePriorityQueue<Key, Value> {

  /*
   *
   * YOUR CODE BELOW THIS
   *
   */
BinaryTreeNode<Key ,Value> valueBasedReplacedNode = null;

  @Override
  public void insert(Key k, Value v) {
    // TODO Auto-generated method stub
    if (get(k) == null) {
      put(k, v);
    } else {
      BinaryTreeNode<Key, Value> temp = getRoot();
      BinaryTreeNode<Key, Value> rightChild = new BinaryTreeNode<>(null, null);
      BinaryTreeNode<Key, Value> leftChild = new BinaryTreeNode<>(null, null);

      while (isInternal(temp)) {
        if (getComparator().compare(k, temp.getKey()) <= 0) {
          temp = temp.getLeftChild();
        } else {
          temp = temp.getRightChild();
        }
      }
      temp.setKey(k);
      temp.setValue(v);
      temp.setLeftChild(leftChild);
      temp.setRightChild(rightChild);
      leftChild.setParent(temp);
      rightChild.setParent(temp);
      keySet.add(k);
      size++;
    }
  }

  @Override
  public Entry<Key, Value> pop() {
    if (size == 0) {
      return null;
    } else {
      BinaryTreeNode<Key, Value> temp = (BinaryTreeNode<Key, Value>) top();
      if (isRoot(temp)) {
        root = temp.rightChild;
        root.parent = null;
      } else {
        temp.rightChild.setParent(temp.parent);
        temp.parent.setLeftChild(temp.rightChild);
      }
      keySet.remove(temp.getKey());
      size--;
      return temp;
    }
  }

  @Override
  public Entry<Key, Value> top() {
    if (size == 0) {
      return null;
    } else {
      BinaryTreeNode<Key, Value> temp = getRoot();
      while (isInternal(temp.getLeftChild())) {
        temp = temp.getLeftChild();
      }
      return temp;
    }
  }


  @Override
  public Key replaceKey(Entry<Key, Value> entry, Key k) {
    if (size != 0) {
      BinaryTreeNode<Key, Value> temp = getRoot();
      while (isInternal(temp)) {
        if (getComparator().compare(temp.getKey(), entry.getKey()) < 0 ) {
          temp = temp.getRightChild();
        } else if (getComparator().compare(temp.getKey(), entry.getKey()) > 0) {
          temp = temp.getLeftChild();
        }else if (getComparator().compare(temp.getKey(), entry.getKey()) == 0 && temp.getValue() != entry.getValue()){
          temp = temp.getLeftChild();
        }else if ((getComparator().compare(temp.getKey(), entry.getKey()) == 0 && temp.getValue() == entry.getValue())){
          break;
        }
      }
      if (isExternal(temp)) {
        return null;
      } else {
        Entry<Key, Value> removedEntry = removeEntry(temp);
        Key key = removedEntry.getKey();
        removedEntry.setKey(k);
        put(removedEntry.getKey() , removedEntry.getValue());
        return key;
      }
    }
    return null;
  }
  public Entry<Key ,Value> removeEntry(Entry<Key ,Value> entry){
    // TODO Auto-generated method stub
    BinaryTreeNode<Key , Value> temp = (BinaryTreeNode<Key, Value>) entry;
    BinaryTreeNode<Key , Value> willReturns = new BinaryTreeNode<>(entry.getKey() , entry.getValue());
    if (isExternal(temp.getLeftChild())&& isExternal(temp.getRightChild())) {
      deleteFromKeySet(temp.getKey());
      temp.setValue(null);
      temp.setKey(null);
      temp.setLeftChild(null);
      temp.setRightChild(null);
      if (size==1){
        root.setValue(null);
        root.setKey(null);
        root.setLeftChild(null);
        root.setRightChild(null);
      }

    }

    else if(isInternal(temp.getRightChild()) && isInternal(temp.getLeftChild())){
      deleteFromKeySet(temp.getKey());
      BinaryTreeNode<Key , Value> childToReplace = temp.getRightChild();

      while(isInternal(childToReplace.getLeftChild())){
        childToReplace = childToReplace.getLeftChild();
      }
      temp.setKey(childToReplace.getKey());
      temp.setValue(childToReplace.getValue());


      if(isInternal(childToReplace.getRightChild())){
        if(isLeftChild(childToReplace)){
          childToReplace.getParent().setLeftChild(childToReplace.getRightChild());
          childToReplace.getRightChild().setParent(childToReplace.getParent());
        }else if(isRightChild(childToReplace)){
          childToReplace.getParent().setRightChild(childToReplace.getRightChild());
          childToReplace.getRightChild().setParent(childToReplace.getParent());
        }
        childToReplace = null;

      }else if(isExternal(childToReplace.getRightChild())){
        childToReplace.setKey(null);
        childToReplace.setValue(null);
        childToReplace.setLeftChild(null);
        childToReplace.setRightChild(null);

      }

    }
    else if (isInternal(temp.getRightChild()) || isInternal(temp.getLeftChild())) {
      deleteFromKeySet(temp.getKey());
      if (isInternal(temp.getLeftChild())) {

        if(isLeftChild(temp)){
          temp.getParent().setLeftChild(temp.getLeftChild());
          temp.getLeftChild().setParent(temp.getParent());
        }else if(isRightChild(temp)){
          temp.getParent().setRightChild(temp.getLeftChild());
          temp.getLeftChild().setParent(temp.getParent());
        }
        temp=null;
      }else if (isInternal(temp.getRightChild())) {

        if(isLeftChild(temp)){
          temp.getParent().setLeftChild(temp.getRightChild());
          temp.getRightChild().setParent(temp.getParent());
        }else if(isRightChild(temp)){
          temp.getParent().setRightChild(temp.getRightChild());
          temp.getRightChild().setParent(temp.getParent());
        }
        temp=null;
      }
    }

    size--;
    return willReturns;
  }

  @Override
  public Key replaceKey(Value v, Key k) {
    if(size == 0){
      return null;
    }else{
      InOrderTraversal(getRoot() , v);
      if(valueBasedReplacedNode == null){
        return null;
      }else{
        Entry<Key, Value> removedEntry = removeEntry(valueBasedReplacedNode);
        Key key = removedEntry.getKey();
        removedEntry.setKey(k);
        put(removedEntry.getKey() , removedEntry.getValue());
        valueBasedReplacedNode = null;
        return key;
      }
    }
  }

  public void InOrderTraversal(BinaryTreeNode<Key ,Value> entry , Value value ){
    if(isInternal(entry.getLeftChild() )){
      InOrderTraversal(entry.getLeftChild() , value );
    }
    if(entry.getValue().equals(value)){
      valueBasedReplacedNode = entry;
    }

    if(isInternal(entry.getRightChild())){
      InOrderTraversal(entry.getRightChild(), value );

    }
  }

  @Override
  public Value replaceValue(Entry<Key, Value> entry, Value v) {
    if (size != 0) {
      BinaryTreeNode<Key, Value> temp = getRoot();
      while (isInternal(temp)) {
        if (getComparator().compare(temp.getKey(), entry.getKey()) < 0 ) {
          temp = temp.getRightChild();
        } else if (getComparator().compare(temp.getKey(), entry.getKey()) > 0) {
          temp = temp.getLeftChild();
        }else if ((getComparator().compare(temp.getKey(), entry.getKey()) == 0) && (temp.getValue() != entry.getValue())){
          temp = temp.getLeftChild();
        }else if (((getComparator().compare(temp.getKey(), entry.getKey()) == 0) && (temp.getValue() == entry.getValue())) || isExternal(temp)){
          break;
        }
      }
      if (isExternal(temp)) {
        return null;
      } else {
        Value value = temp.getValue();
        temp.setValue(v);
        return value;
      }
    }
    return null;
  }
}
