import java.util.*;

public class CircularBuffer<E extends Element> implements MovingAverageList<E> {

    private Object[] list;
    private Integer count;
    private Integer capacity;
    private Integer writePointer;


    public CircularBuffer(Integer capacity) {
        count = 0;
        this.capacity = capacity;
        list = new Object[capacity];
        writePointer = -1;
    }

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
