import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class MyStreamTest {

    @Test
    public void TestOf() {
        List<Integer> list = Arrays.asList(1,2,3);
       MyStream<Integer> my_stream = MyStream.of(list);

        Assert.assertNotNull(my_stream);

       my_stream.filter(a->a==2);

       my_stream.transform(a->3);
       Map map = my_stream.toMap(a->a.toString(),a->a);

        for (Map.Entry<String,Integer> item:((Map<String,Integer>)map).entrySet()
             ) {
            Assert.assertTrue("В мапе у всех записей вэлью должен быть равен 3"
                    ,item.getValue()==3);
        }

        Assert.assertNotNull(map);

       Person person1 = new Person("Bob", 18);
        Person person2 = new Person("Masha", 21);
        Person person3 = new Person("Dima", 25);

        List<Person> list_pers = Arrays.asList(person1,person2,person3);
        Map<String,Person> map_pers = MyStream.of(list_pers)
                .filter(p->p.getAge()>20)
                .transform(p->new Person(p.getName(),p.getAge()+30))
                .toMap(p->p.getName(),p->p);

        Assert.assertNotNull(map_pers);

        for (Map.Entry<String,Person> item:map_pers.entrySet()
             ) {
         System.out.println(item.getKey()+" "+item.getValue());
         Assert.assertTrue("Возраст персон в мапе должен быть больше 50 лет"
                 ,item.getValue().getAge()>50);

        }
    }
}
