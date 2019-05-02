import java.util.*;

public class CircularBuffer<E extends Element> implements MovingAverageList<E> {

    /**
     * Array to keep track of elements added
     */
    private Object[] list;
    /**
     * Total items added to datastructure
     */
    private Integer count;
    /**
     * Max items to keep track off
     */
    private Integer capacity;
    /**
     * Next location to insert item
     */
    private Integer writePointer;


    public CircularBuffer(Integer capacity) {
        count = 0;
        this.capacity = capacity;
        list = new Object[capacity];
        writePointer = -1;
    }

    /**
     * Returns next available position to insert item
     * @return next position
     */
    private Integer getWritePostion() {
        writePointer = (writePointer + 1) % capacity;
        if (count < capacity) {
            count++;
        }
        return writePointer;
    }

    @Override
    public void add(E e) {
        int writeIndex = getWritePostion();
        list[writeIndex] = e;
    }

    @Override
    public void addAll(Collection<? extends E> items) {
        items.forEach( item -> add(item));
    }

    @Override
    public E get(Integer index) {
        return (E) list[index];
    }

    /**
     * Calculate the moving average for the last n elements
     * The items are read from the current write position and wraps around
     * to the next item added into the datastructure
     * @param n
     * @return moving average
     */
    @Override
    public Double getMovingAverage(Integer n) {
        Double sum = 0d;
        if (count < capacity) {
            for (int i = writePointer; i > 0; i--) {
                sum += ((E) list[i]).getValue();
            }
            return sum / n;
        } else {
            for(int i=0; i < n; i++) {
                int tmp = writePointer - i;
                int index = tmp > -1 ? tmp : capacity + tmp;
                sum += ((E) list[index]).getValue();
            }
            return  sum/n;
        }

    }

    @Override
    public List<? extends E> getList() {
        List<?> tempList = Arrays.asList(list);
        return (List<? extends E>) tempList;
    }
}
