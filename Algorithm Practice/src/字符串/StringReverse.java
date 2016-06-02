package 字符串;

import java.lang.reflect.Array;


// A to B -> B to A
public class StringReverse 
{

	public static void main(String[] args) 
	{
		System.out.println(allReverse("Dog likes pig"));	
	}
	


	static String allReverse(String str)
	{
		str = reverse(str);
		String[] array = str.split(" ");
		String rst = "";
		for(int i = 0; i < array.length; i++)
		{
			array[i] = reverse(array[i]);
			rst += array[i] + " ";
		}
		
		return rst.substring(0, rst.length() - 1);
	}
	
	static String reverse(String str)
	{
		char[] array = str.toCharArray();
		for(int i = 0, j = array.length-1; i != j; i++,j-- )
		{
			char tmp = array[j];
			array[j] = array[i];
			array[i] = tmp;
		}
  		return String.valueOf(array);		
	}
}
