import java.util.*;

public class MovingAvgListImpl<E extends Element> implements MovingAverageList<E> {

    private LinkedList<E> list;
    private Integer capacity;

    public MovingAvgListImpl( Integer capacity) {
        list = new LinkedList<>();
        this.capacity = capacity;
    }

    @Override
    public Double getMovingAverage(Integer n) {
        if ( n > list.size()) {
            throw new IndexOutOfBoundsException(String.format("Index %d is greater than size", n));
        }
        Iterator<E> itr = list.descendingIterator();
        Double sum = 0d;
        for(int i=0; i < n && itr.hasNext(); i++) {
            E e = itr.next();
            sum += e.getValue();
        }
        return sum/n;
    }

    @Override
    public void add(E e) {
        if(capacity == 0) {
            list.remove();
            list.add(e);
        } else {
            list.add(e);
            capacity--;
        }
    }

    @Override
    public void addAll(Collection<? extends E> items) {
        for (E e: items) {
            add(e);
        }
    }

    @Override
    public E get(Integer i) {
        return list.get(i);
    }

    @Override
    public List<? extends E> getList() {
        return Collections.unmodifiableList(list);
    }
}
