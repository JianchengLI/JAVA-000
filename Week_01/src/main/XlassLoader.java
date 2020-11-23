package main;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

public class XlassLoader extends ClassLoader{
    public static void main(String[] args) throws Exception {
        XlassLoader xlassloader = new XlassLoader();
        Class<?> klass = xlassloader.loadClass("Hello");
        Object o = klass.newInstance();
        Method method = klass.getMethod("hello");
        method.invoke(o);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            return this.decodeXlassFile(name + ".xlass");
        } catch (IOException e) {
            throw new ClassNotFoundException();
        }
    }

    protected Class<?> decodeXlassFile(String xClassFilePath) throws IOException {
        InputStream inputStreamXlass = this.getClass().getResourceAsStream(xClassFilePath);
        int xlassLength = inputStreamXlass.available();
        byte[] xlassByteArray = new byte[xlassLength];
        inputStreamXlass.read(xlassByteArray);
        byte[] targetArray = new byte[xlassByteArray.length];
        for (int i = 0; i < xlassByteArray.length; i++) {
            targetArray[i] = (byte) (255 - xlassByteArray[i]);
        }
        return defineClass("Hello", targetArray, 0, xlassByteArray.length);
    }
}
