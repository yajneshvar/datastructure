import java.util.Collection;
import java.util.List;

public interface MovingAverageList<E> {

    /**
     * Returns the moving average for a the specified last n item
     * @return the moving average
     */
    Double getMovingAverage(Integer n);

    /**
     * Add element to the list
     * @param e
     */
    void add(E e);

    /**
     * Add all the elements given in to the list
     */
    void addAll(Collection<? extends E> items);

    /**
     * Get the element specified at index i
     * @param i
     */
    E get(Integer i);

    /**
     * Returns all the elements added
     * @return the elements as a list
     */
    List<? extends E> getList();

}
