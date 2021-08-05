package code;

import java.util.*;

import given.iMap;
import given.iBinarySearchTree;

/*
 * Implement a vanilla binary search tree using a linked tree representation
 * Use the BinaryTreeNode as your node class
 */

public class BinarySearchTree<Key, Value> implements iBinarySearchTree<Key, Value>, iMap<Key, Value> {
  public boolean checker = false;
  public BinaryTreeNode<Key , Value> root = new BinaryTreeNode<>(null,null);
  public ArrayList<Key> keySet = new ArrayList<Key>();
  public int size = 0;
  public ArrayList<BinaryTreeNode<Key, Value>> orderedNodeList = new ArrayList<BinaryTreeNode<Key, Value>>();
  public Comparator<Key> comparator= null;
  @Override

  public Value get(Key k) {
    if (size == 0) {
      return null;
    } else {
      BinaryTreeNode<Key, Value> temp = root;
      while (isInternal(temp) && temp.getKey() != k) {
        if (getComparator().compare(k, temp.getKey()) > 0) {
          temp = temp.getRightChild();
        } else if (getComparator().compare(k, temp.getKey()) < 0) {
          temp = temp.getLeftChild();
        }else{
          break;
        }
      }
      return temp.getValue();

    }
  }

  @Override
  public Value put(Key k, Value v) {
    Value willReturn = null ;
    BinaryTreeNode<Key, Value> rightChild =new BinaryTreeNode<>(null , null);
    BinaryTreeNode<Key, Value> leftChild =new BinaryTreeNode<>(null , null);
    if(size == 0){
      root.setKey(k);
      root.setValue(v);
      root.setLeftChild(leftChild);
      root.setRightChild(rightChild);
      rightChild.setParent(root);
      leftChild.setParent(root);
      root.setParent(null);
      keySet.add(k);
      size++;
    }else {
      BinaryTreeNode<Key , Value> temp = root;
      while(isInternal(temp)){
        if(getComparator().compare(k , temp.getKey()) == 0){
          break;
        }
        else if(getComparator().compare(k , temp.getKey()) > 0){
          temp = temp.getRightChild();
        }else if(getComparator().compare(k , temp.getKey()) < 0){
          temp = temp.getLeftChild();
        }
      }

      if(isExternal(temp)){
        temp.setKey(k);
        temp.setValue(v);
        temp.setRightChild(rightChild);
        temp.setLeftChild(leftChild);
        leftChild.setParent(temp);
        rightChild.setParent(temp);
        keySet.add(k);
        size++;
      }else if (isInternal(temp)){
        willReturn = temp.getValue();
        temp.setValue(v);
      }

    }
    return willReturn;
  }

  @Override
  public Value remove(Key k) {
    // TODO Auto-generated method stub
    BinaryTreeNode<Key , Value> temp = getNode(k);
    Value willReturns = null;
    if(temp != null) {
      willReturns = temp.getValue();
      if(isInternal(temp)) {
        if (isExternal(temp.getLeftChild()) && isExternal(temp.getRightChild())) {
          deleteFromKeySet(temp.getKey());
          temp.setValue(null);
          temp.setKey(null);
          temp.setLeftChild(null);
          temp.setRightChild(null);
          if (size == 1) {
            root.setValue(null);
            root.setKey(null);
            root.setLeftChild(null);
            root.setRightChild(null);
          }

        } else if (isInternal(temp.getRightChild()) && isInternal(temp.getLeftChild())) {
          deleteFromKeySet(temp.getKey());
          BinaryTreeNode<Key, Value> childToReplace = temp.getRightChild();

          while (isInternal(childToReplace.getLeftChild())) {
            childToReplace = childToReplace.getLeftChild();
          }
          temp.setKey(childToReplace.getKey());
          temp.setValue(childToReplace.getValue());


          if (isInternal(childToReplace.getRightChild())) {
            if (isLeftChild(childToReplace)) {
              childToReplace.getParent().setLeftChild(childToReplace.getRightChild());
              childToReplace.getRightChild().setParent(childToReplace.getParent());
            } else if (isRightChild(childToReplace)) {
              childToReplace.getParent().setRightChild(childToReplace.getRightChild());
              childToReplace.getRightChild().setParent(childToReplace.getParent());
            }
            childToReplace = null;

          } else if (isExternal(childToReplace.getRightChild())) {
            childToReplace.setKey(null);
            childToReplace.setValue(null);
            childToReplace.setLeftChild(null);
            childToReplace.setRightChild(null);

          }

        } else if ( isInternal(temp.getRightChild()) || isInternal(temp.getLeftChild())) {
          deleteFromKeySet(temp.getKey());
          if (isInternal(temp.getLeftChild())) {

            if (isLeftChild(temp)) {
              temp.getParent().setLeftChild(temp.getLeftChild());
              temp.getLeftChild().setParent(temp.getParent());
            } else if (isRightChild(temp)) {
              temp.getParent().setRightChild(temp.getLeftChild());
              temp.getLeftChild().setParent(temp.getParent());
            }
            temp = null;
          } else if (isInternal(temp.getRightChild())) {

            if (isLeftChild(temp)) {
              temp.getParent().setLeftChild(temp.getRightChild());
              temp.getRightChild().setParent(temp.getParent());
            } else if (isRightChild(temp)) {
              temp.getParent().setRightChild(temp.getRightChild());
              temp.getRightChild().setParent(temp.getParent());
            }
            temp = null;
          }
        }
      }
      size--;
    }
    return willReturns;
  }
  public void deleteFromKeySet(Key k){
    keySet.remove(getNode(k));
  }

  @Override
  public Iterable<Key> keySet() {
    // TODO Auto-generated method stub
    return (Iterable<Key>) keySet;
  }

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
  public BinaryTreeNode<Key, Value> getRoot() {
    // TODO Auto-generated method stub
    return root;
  }

  @Override
  public BinaryTreeNode<Key, Value> getParent(BinaryTreeNode<Key, Value> node) {
    // TODO Auto-generated method stub
    return node.getParent();
  }

  @Override
  public boolean isInternal(BinaryTreeNode<Key, Value> node) {
    // TODO Auto-generated method stub
    if(node != null)  {
      return (node.getKey() != null);
    }else
      return false;
  }

  @Override
  public boolean isExternal(BinaryTreeNode<Key, Value> node) {
    // TODO Auto-generated method stub

    if (node!= null){
      return node.getKey()==null;
    }else
      return true;
  }

  @Override
  public boolean isRoot(BinaryTreeNode<Key, Value> node) {
    // TODO Auto-generated method stub
    return node == root;
  }

  @Override
  public BinaryTreeNode<Key, Value> getNode(Key k) {
    if (size == 0) {
      return null;
    } else {
      if(root.getKey() == k){
        return root;}
      BinaryTreeNode<Key, Value> temp = root;
      while (isInternal(temp)) {
        if(getComparator().compare(temp.getKey() , k) ==0){
          break;
        }
        else if (getComparator().compare(k, temp.getKey()) > 0) {
          temp = temp.getRightChild();
        } else if (getComparator().compare(k, temp.getKey()) < 0) {
          temp = temp.getLeftChild();
        }
      }
      if(isExternal(temp)){
        return null;
      }else{
        return temp;
      }

    }
  }

  @Override
  public Value getValue(Key k) {
    // TODO Auto-generated method stub
    return get(k);
  }

  @Override
  public BinaryTreeNode<Key, Value> getLeftChild(BinaryTreeNode<Key, Value> node) {
    if(node.getLeftChild() == null){
      return null;
    }else{
      return node.getLeftChild();
    }
  }

  @Override
  public BinaryTreeNode<Key, Value> getRightChild(BinaryTreeNode<Key, Value> node) {
    if(node.getRightChild() == null){
      return null;
    }else{
      return node.getRightChild();
    }
  }

  @Override
  public BinaryTreeNode<Key, Value> sibling(BinaryTreeNode<Key, Value> node) {
    // TODO Auto-generated method stub
    if(isRoot(node)){
      return null;
    }
    BinaryTreeNode<Key, Value> sibling=null ;
    if(isLeftChild(node)){
      sibling =node.getParent().getRightChild();
    }else if(isRightChild(node)){
      sibling = node.getParent().getLeftChild();
    }
    if(sibling.getKey()!=null){
      return sibling;
    }else{
      return null;
    }
  }

  @Override
  public boolean isLeftChild(BinaryTreeNode<Key, Value> node) {
    // TODO Auto-generated method stub
    if (isRoot(node)){
      return false;
    }else{
      return node.getParent().getLeftChild() == node;
    }
  }

  @Override
  public boolean isRightChild(BinaryTreeNode<Key, Value> node) {
    // TODO Auto-generated method stub
    if (isRoot(node)){
      return false;
    }else{
      return node.getParent().getRightChild() == node;
    }

  }

  public void createInOrderedList(BinaryTreeNode<Key ,Value> v){
    if (isInternal(v.getLeftChild())){
      createInOrderedList(v.getLeftChild());
    }
    orderedNodeList.add(v);
    if(isInternal(v.getRightChild())){
      createInOrderedList(v.getRightChild());
    }
  }
  @Override
  public List<BinaryTreeNode<Key, Value>> getNodesInOrder() {
    orderedNodeList.clear();
    if(size == 0){
      return orderedNodeList;
    }
    createInOrderedList(root);
    return  orderedNodeList;
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
  public BinaryTreeNode<Key, Value> ceiling(Key k) {
    getNodesInOrder();
    BinaryTreeNode<Key , Value> temp = null;
    for (BinaryTreeNode<Key, Value> keyValueBinaryTreeNode : orderedNodeList) {
      if (getComparator().compare(keyValueBinaryTreeNode.getKey(), k) >= 0) {
        temp = keyValueBinaryTreeNode;
        break;
      }
    }
    return temp;
  }

  @Override
  public BinaryTreeNode<Key, Value> floor(Key k) {
    // TODO Auto-generated method stub
    getNodesInOrder();
    BinaryTreeNode<Key , Value> temp = null;
    for (BinaryTreeNode<Key, Value> keyValueBinaryTreeNode : orderedNodeList) {
      if (getComparator().compare(keyValueBinaryTreeNode.getKey(), k) <= 0) {
        temp = keyValueBinaryTreeNode;
      }
    }
    return temp;

  }
}
