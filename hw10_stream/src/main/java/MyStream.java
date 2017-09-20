import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
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


}
