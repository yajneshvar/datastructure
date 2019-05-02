/*
 * This Java source file was generated by the Gradle 'init' task.
 */
import org.junit.Test;
import org.omg.CORBA.INTERNAL;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AppTest {

    @Test
    public void testAddingMoreThanCapacity() {
        CircularBuffer<Element> buffer = new CircularBuffer<>(5);
        List<ElementImpl> elementList = new ArrayList<>(6);
        for(int i =0; i < 8; i++) {
            elementList.add(new ElementImpl("A", (double) i));
        }
        buffer.addAll(elementList);
        double movingAvg = buffer.getMovingAverage(3);
        int size = buffer.getList().size();
        assertEquals(size, 5);
        assertEquals(movingAvg, 6, 0.1d);
    }

    @Test
    public void testAddingMoreThanLargeCapacity() {
        CircularBuffer<Element> buffer = new CircularBuffer<>(5);
        List<ElementImpl> elementList = new ArrayList<>();
        for(int i =0; i < 200; i++) {
            elementList.add(new ElementImpl("A", (double) i));
        }
        buffer.addAll(elementList);
        int size = buffer.getList().size();
        assertEquals(size, 5);

    }

    @Test
    public void testAddingMoreThanCapacityAvgList() {
        MovingAvgListImpl<Element> buffer = new MovingAvgListImpl<>(5);
        List<ElementImpl> elementList = new ArrayList<>(6);
        for(int i =0; i < 7; i++) {
            elementList.add(new ElementImpl("A", (double) i));
        }
        buffer.addAll(elementList);
        double movingAvg = buffer.getMovingAverage(3);
        int size = buffer.getList().size();
        assertEquals(size, 5);
        assertEquals(movingAvg, 5, 0.1d);
    }



}
