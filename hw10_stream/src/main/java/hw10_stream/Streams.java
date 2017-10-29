package hw10_stream;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

public class Streams<T> {
    private Collection<T> collection;

    public Streams() {
    }

    /**
     * Конструктор. Создает новый список на основе входящей коллекции
     * @param list
     */
    public Streams(Collection<T> list) {
        this.collection = new ArrayList<T>(list);
    }

    /**
     * статический метод, который принимает коллекцию и создает новый объект hw10_stream.Streams
     * @param list
     * @param <T>
     * @return
     */
    public static <T> Streams<T> of(Collection<T> list) {
      return new Streams<T>(list);
  }

    /**
     * оставляет в коллекции только те элементы, которые удовлетворяют условию в лямбде
     * @param predicate
     * @return
     */
  public Streams<T> filter(Predicate<? super T> predicate) {
        Collection<T> new_collection = new ArrayList<>();

      for (T item:collection
           ) {
         if (predicate.test(item))
             new_collection.add(item);
      }
      collection = new_collection;
      return this;
  }

    /**
     * преобразует элемент в другой
     * @param function
     * @return
     */
  public Streams<T> transform(Function<? super T, ? extends T> function) {
      Collection<T> new_collection = new ArrayList<>();
      for (T item:collection
           ) {
          new_collection.add( function.apply(item));
      }
      collection = new_collection;
      return this;
  }

    /**
     * принимает 2 лямбды для создания мапы,
     * в одной указывается, что использовать в качестве ключа, в другой, что в качестве значения.
     * @param consumerKey
     * @param consumerValue
     * @param <K>
     * @param <V>
     * @return
     */
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
