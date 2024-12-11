package work3;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

/**
 * Interface representing a generic aggregate of strings.
 *
 * @author Ivanka Teteruk
 */
interface StringAggregate {
    /**
     * Creates an iterator for sequential traversal.
     *
     * @return an Iterator for the aggregate.
     */
    Iterator<String> createIterator();

    /**
     * Creates a filtered iterator based on a given condition.
     *
     * @param filter the predicate for filtering strings.
     * @return a FilteredIterator for the aggregate.
     */
    FilteredIterator createFilteredIterator(Predicate<String> filter);
}

/**
 * Concrete class implementing the StringAggregate interface.
 *
 * @author Ivanka Teteruk
 */
class LinearStringList implements StringAggregate {
    private final List<String> strings;

    /**
     * Constructor to initialize the list of strings.
     */
    public LinearStringList() {
        this.strings = new ArrayList<>();
    }

    /**
     * Adds a string to the list.
     *
     * @param str the string to add.
     */
    public void addString(String str) {
        strings.add(str);
        System.out.println("Added string: " + str);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<String> createIterator() {
        System.out.println("Creating sequential iterator.");
        return strings.iterator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FilteredIterator createFilteredIterator(Predicate<String> filter) {
        System.out.println("Creating filtered iterator with condition.");
        return new FilteredIterator(strings, filter);
    }
}

/**
 * Class for a filtered iterator over a list of strings.
 *
 * @author Ivanka Teteruk
 */
class FilteredIterator implements Iterator<String> {
    private final Iterator<String> baseIterator;
    private final Predicate<String> filter;
    private String nextElement;
    private boolean hasNext;

    /**
     * Constructor to initialize the filtered iterator.
     *
     * @param strings the list of strings to iterate over.
     * @param filter  the predicate for filtering.
     */
    public FilteredIterator(List<String> strings, Predicate<String> filter) {
        this.baseIterator = strings.iterator();
        this.filter = filter;
        advance();
    }

    /**
     * Advances to the next valid element.
     */
    private void advance() {
        while (baseIterator.hasNext()) {
            String candidate = baseIterator.next();
            if (filter.test(candidate)) {
                nextElement = candidate;
                hasNext = true;
                return;
            }
        }
        hasNext = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasNext() {
        return hasNext;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String next() {
        if (!hasNext) {
            throw new IllegalStateException("No more elements match the filter.");
        }
        String current = nextElement;
        advance();
        return current;
    }
}

/**
 * Demonstration of the implemented classes and functionality.
 *
 * @author Ivanka Teteruk
 */
public class Main {
    public static void main(String[] args) {
        LinearStringList stringList = new LinearStringList();

        stringList.addString("hourglass");
        stringList.addString("cat");
        stringList.addString("manifestation");
        stringList.addString("city");

        // Sequential traversal
        Iterator<String> iterator = stringList.createIterator();
        System.out.println("Sequential traversal:");
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        // Filtered traversal (e.g., strings with length > 5)
        FilteredIterator filteredIterator = stringList.createFilteredIterator(s -> s.length() > 5);
        System.out.println("Filtered traversal (length > 5):");
        while (filteredIterator.hasNext()) {
            System.out.println(filteredIterator.next());
        }
    }
}
