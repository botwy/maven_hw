import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class MyStream<T> {
    private Collection<T> collection;

    public MyStream() {
    }

    public MyStream(List<T> list) {
        this.collection = new ArrayList<T>(list);
       // this.collection.addAll(collection);
    }

    public static <T> MyStream<T> of(List<T> list) {
      return new MyStream<T>(list);
  }

  public MyStream<T> filter(Predicate<? super T> predicate) {
        Collection<T> new_collection = new ArrayList<>();

      for (T item:collection
           ) {
         if (predicate.test(item))
             new_collection.add(item);
      }
      collection = new_collection;
      return this;
  }

  public MyStream<T> transform(Function<? super T, ? extends T> function) {
      Collection<T> new_collection = new ArrayList<>();
      for (T item:collection
           ) {
          new_collection.add( function.apply(item));
      }
      collection = new_collection;
      return this;
  }

    public<K,V> Map<K, V> toMap
            (Function<? super T,? extends K> consumerKey, Function<? super T, ? extends V> consumerValue) {
       Map<K,V> new_map = new HashMap<K,V>();
        for (T item:collection
                ) {
            new_map.put(consumerKey.apply(item),consumerValue.apply(item));
        }

        return new_map;
    }


}
