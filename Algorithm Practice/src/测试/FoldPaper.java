package 测试;

import java.util.*;
 
public class FoldPaper {
	public static void main(String []args)
	{
		System.out.println(foldPaper(3));
	}
   
	public static  ArrayList<String> foldPaper(int n) 
    {
	 
         ArrayList<String> arr=new ArrayList<String>();
        getFold(n,arr,1,true);
//        String[] res=new String[arr.size()];
//        for(int i=0;i<arr.size();i++)
//        {
//            res[i]=arr.get(i);
//        }
        return arr; 
    }
    public static void getFold(int n,ArrayList<String> arr,int i,boolean down)
    {
        if(i>n)
        {
            return;
        }
        getFold(n,arr,i+1,true);
        if(down)
        {
            arr.add("down");
        }
        else
        {
            arr.add("up");
        }
        getFold(n,arr,i+1,false);
    }
}