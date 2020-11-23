package test;

import main.XlassLoader;
import org.junit.Assert;
import org.junit.Test;
import java.util.Arrays;

public class XlassLoaderTest extends ClassLoader{
    @Test
    public void should_success_to_load_when_load_Hello_class_given_the_xlass_file() throws ClassNotFoundException {
        XlassLoader xlassloader = new XlassLoader();
        Class<?> klass = xlassloader.loadClass("Hello");
        Assert.assertNotNull(klass);
        Assert.assertEquals("Hello", klass.getName());
    }

    @Test
    public void should_loaded_class_has_hello_method_when_load_Hello_class_given_the_xlass_file() throws ClassNotFoundException {
        XlassLoader xlassloader = new XlassLoader();
        Class<?> klass = xlassloader.loadClass("Hello");
        Assert.assertTrue(Arrays.stream(klass.getMethods()).anyMatch(method1 -> method1.getName().endsWith("hello")));
    }
}
