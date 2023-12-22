package Tools;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

//Search into a generic list ALL items with a generic property
public final class CollectionTools {
    public static <T> List<T> findByProperty(Collection<T> col, Predicate<T> filter) {
        return col.stream().filter(filter).collect(Collectors.toList());
    }

    public static <T> boolean some(Collection <T> col, Predicate<T> filter) {
        return col.stream().anyMatch(filter);
    }
}