package code;

import given.Entry;

/*
 * The binary node class which extends the entry class.
 * This will be used in linked tree implementations
 *
 */
public class BinaryTreeNode<Key, Value> extends Entry<Key, Value> {

  public BinaryTreeNode<Key , Value> parent;
  public BinaryTreeNode<Key , Value> leftChild;
  public BinaryTreeNode<Key , Value> rightChild;

  public BinaryTreeNode(Key k, Value v) {
    super(k, v);
    parent = null;
    leftChild = null;
    rightChild = null;
    /*
     *
     * This constructor is needed for the autograder. You can fill the rest to your liking.
     * YOUR CODE AFTER THIS:
     *
     */
  }
  public void setParent(BinaryTreeNode<Key , Value> parent){
    this.parent = parent;
  }
  public void setLeftChild(BinaryTreeNode<Key, Value> leftChild){
    this.leftChild = leftChild;
  }
  public void setRightChild(BinaryTreeNode<Key , Value> rightChild){
    this.rightChild = rightChild;
  }

  public BinaryTreeNode<Key, Value> getLeftChild() {
    return leftChild;
  }

  public BinaryTreeNode<Key, Value> getParent() {
    return parent;
  }

  public BinaryTreeNode<Key, Value> getRightChild() {
    return rightChild;
  }
}
