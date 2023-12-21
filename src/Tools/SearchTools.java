package Tools;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

//Search into a generic list ALL items with a generic property
public final class SearchTools {
    public static <T> List<T> findByProperty(Collection<T> col, Predicate<T> filter) {
        List<T> filteredList = (List<T>) col.stream().filter(filter).collect(Collectors.toList());
        return filteredList;
    }
}