public class AVLTree<T extends Comparable<T>> {
    public final T value;
    public AVLTree<T> leftNode;
    public AVLTree<T> rightNode;




    public AVLTree(){
        value = null;
    }
    public AVLTree(T value){
        if (value == null){
            throw new IllegalArgumentException("can not be null");
        }
        this.value = value;
        this.leftNode = new EmptyAVL<>();
        this.rightNode = new EmptyAVL<>();
    }

    public AVLTree(T value, AVLTree<T> leftNode, AVLTree<T> rightNode){
        if (value == null || leftNode == null || rightNode == null)
            throw new IllegalArgumentException("Inputs cannot be null");

        this.value = value;
        this.leftNode = leftNode;
        this.rightNode = rightNode;
    }


    public  T min(){

            return (leftNode instanceof EmptyAVLTree<T>) ? value : leftNode.min();
        }
    public  T max(){
            return (rightNode instanceof EmptyAVLTree<T>) ? value : rightNode.max();
        }

    public AVLTree<T> find(T element){
        if (element == null)
            throw new IllegalArgumentException("Input cannot be null");

        if (element.compareTo(value) == 0) {
            return this;
        } else if (element.compareTo(value) < 0) {
            return leftNode.find(element);
        } else {
            return rightNode.find(element);
        }
    }

    public AVLTree<T> insert(T element) {
        /*
            TODO: Write and or complete your insertion code here
            Note that what each method does is described in its superclass unless edited.
            E.g. what 'insert' does is described in Tree.java.
         */

        // Ensure input is not null.
        if (element == null)
            throw new IllegalArgumentException("Input cannot be null");


//        AVLTree<T> newTree = this;
        AVLTree<T> newTree = new AVLTree<>(value,leftNode,rightNode);
        if (element.compareTo(value) > 0) {
            // COMPLETE
            newTree = new AVLTree<>(value,leftNode,rightNode.insert(element));
        } else if (element.compareTo(value) < 0) {

            newTree = new AVLTree<>(value,leftNode.insert(element),rightNode);
            // COMPLETE
        }

        if (newTree.getBalanceFactor()==-2&&newTree.rightNode instanceof AVLTree<T>){
            if (((AVLTree<T>) newTree.rightNode).getBalanceFactor() == 1){
                newTree.rightNode = ((AVLTree<T>) newTree.rightNode).rightRotate();
                newTree = newTree.leftRotate();
            }else if (((AVLTree<T>) newTree.rightNode).getBalanceFactor() == -1){
                newTree = newTree.leftRotate();
            }
        }else if (newTree.getBalanceFactor() == 2 && newTree.leftNode instanceof AVLTree<T>){
            if (((AVLTree<T>) newTree.leftNode).getBalanceFactor() == 1){
                newTree = newTree.rightRotate();
            }else if (((AVLTree<T>) newTree.leftNode).getBalanceFactor() == -1){
                newTree.leftNode = ((AVLTree<T>) newTree.leftNode).leftRotate();
                newTree = newTree.rightRotate();
            }
        }
        return newTree; // Change to return something different
    }
    public int getBalanceFactor() {
        return leftNode.getHeight() - rightNode.getHeight();
    }
    public int getHeight() {
        // Check whether leftNode or rightNode are EmptyTree
        int leftNodeHeight = leftNode instanceof EmptyAVLTree<T> ? 0 : 1 + leftNode.getHeight();
        int rightNodeHeight = rightNode instanceof EmptyAVLTree<T> ? 0 : 1 + rightNode.getHeight();
        return Math.max(leftNodeHeight, rightNodeHeight);
    }

    public AVLTree<T> leftRotate() {
        AVLTree<T> newParent = this.rightNode;
        AVLTree<T> newRightOfCurrent = newParent.leftNode;
        newParent.leftNode = this;
        newParent.leftNode.rightNode = newRightOfCurrent;
        return (AVLTree<T>) newParent;
    }

    public AVLTree<T> rightRotate() {
        AVLTree<T> newParent = this.leftNode;
        AVLTree<T> newLeftOfCurrent = newParent.rightNode;
        newParent.rightNode = this;
        newParent.rightNode.leftNode = newLeftOfCurrent;
        return (AVLTree<T>) newParent; // Change to return something different
    }

    public String toString() {
        return "{" +
                "value=" + value +
                ", leftNode=" + leftNode +
                ", rightNode=" + rightNode +
                '}';
    }

    public static class EmptyAVL<T extends Comparable<T>> extends EmptyAVLTree<T> {

        public AVLTree<T> insert(T element) {
            // The creation of a new Tree, hence, return tree.
            return new AVLTree<>(element);
        }
    }


}

