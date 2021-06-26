package jvm;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HelloClassLoader extends ClassLoader
{

	/**
	 * @param args
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 */
	public static void main(String[] args)
	{
		// 加载并初始化Hello类
		Class<?> helloClass;
		try
		{
			helloClass = new HelloClassLoader().findClass("Hello");
			Method helloMethod = helloClass.getMethod("hello");
			helloMethod.invoke(helloClass.newInstance());
		}
		catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (NoSuchMethodException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (SecurityException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IllegalArgumentException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InstantiationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException
	{
		// 类加载器与.xlass文件需要在同个目录
		String PathString = System.getProperty("user.dir") + "\\src\\jvm\\" + name + ".xlass";
		Path path = Paths.get(PathString);
		try
		{
			// 读取数据
			byte[] byteArray = Files.readAllBytes(path);
			for (int n = 0; n < byteArray.length; n++)
			{
				byteArray[n] = (byte) (255 - byteArray[n]);
			}
			return defineClass(name, byteArray, 0, byteArray.length);
		}
		catch (IOException e1)
		{
			// TODO Auto-generated catch block
			throw new ClassNotFoundException(name, e1);
		}
		finally
		{
		}
	}
}
