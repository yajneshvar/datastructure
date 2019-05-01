import java.util.*;

public class MovingAvgListImpl<E extends Element> implements MovingAverageList<E> {

    private LinkedList<E> list;
    Integer capacity;
    private Integer fixedPeriod;
    private Double movingAverage;


    public MovingAvgListImpl(Integer fixedPeriod, Integer capacity) {
        list = new LinkedList<>();
        this.fixedPeriod = fixedPeriod;
        this.capacity = capacity;
    }

    @Override
    public Double getMovingAverage() {
        return movingAverage;
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
    public List<E> getList() {
        return Collections.unmodifiableList(list);
    }
}
