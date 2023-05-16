package Clases_auxiliares;



public class MyList<T> {

    private Object[] elements;
    private int size;

    private T[] array;

    private static final int Tamano = 7;

    public MyList() {
        array = (T[]) new Object[Tamano];
        size = 0;
    }

    public void add(T item) {
        if (size == array.length) {
            T[] newArray = (T[]) new Object[array.length * 2];
            System.arraycopy(array, 0, newArray, 0, array.length);
            array = newArray;
        }
        array[size++] = item;
    }
    public int size() {
        return size;
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return array[index];
    }
}



