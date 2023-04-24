public abstract class EmptyAVLTree<T extends Comparable<T>> extends AVLTree<T> {
    public abstract AVLTree<T> insert(T element);

    @Override
    public T min() {
        return null;
    }

    @Override
    public T max() {
        return null;
    }

    @Override
    public AVLTree<T> find(T element) {
        return null;
    }

    @Override
    public int getHeight() {
        return -1;
    }

    @Override
    public String toString() {
        return "{}";
    }
}
