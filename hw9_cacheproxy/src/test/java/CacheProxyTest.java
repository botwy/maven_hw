import com.hw9.Cache;
import com.hw9.CacheProxy;
import com.hw9.IService;
import com.hw9.ServiceImpl;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

public class CacheProxyTest {

    @Ignore
    @Test
    public void getFolder() throws NoSuchFieldException, IllegalAccessException {
CacheProxy cacheProxy = Mockito.mock(CacheProxy.class);

            Field f_folder = cacheProxy.getClass().getField("root_folder");
            f_folder.setAccessible(true);
            f_folder.set(cacheProxy,"cache_folder");

    }

    @Test
    public void getFolderNew() throws NoSuchFieldException, IllegalAccessException {
        CacheProxy cacheProxy = Mockito.mock(CacheProxy.class);
Assert.assertTrue("Root folder не должна быть null", cacheProxy.getRoot_folder()!=null);
    }

    @Test
    public void cacheService() throws NoSuchMethodException {
        CacheProxy cacheProxy = new CacheProxy("cache_files");
        IService service = cacheProxy.cache(new ServiceImpl());

        Method[] arr_method = IService.class.getMethods();
        Method method = null;
        for (int i = 0; i < arr_method.length; i++) {
            if (arr_method[i].getName().equals("doHardWork"))
                method=arr_method[i];
        }
Class[]  arr_annotIdentBy = method.getAnnotation(Cache.class).identityBy();
        int length_AnnotIdentyBy = arr_annotIdentBy.length;
        double r1=0;
        double r2=0;
        r1 = service.doHardWork("work1",3);
        System.out.println(r1);
        r2 = service.doHardWork("work1",2);
        System.out.println(r2);
        if (length_AnnotIdentyBy==1 && arr_annotIdentBy[0].equals(String.class))
            Assert.assertTrue("r1 и r2 должны быть равны", r1==r2);
    }
}
