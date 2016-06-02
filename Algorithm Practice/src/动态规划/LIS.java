package 动态规划;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.zip.Inflater;
import java.lang.Math;
import java.lang.reflect.Array;

public class LIS
{

	public static void main(String[] args)
	{			   //0 1 2 3 4 5 6 7 8
		int[] arr = {2,1,5,3,6,4,8,9,7};
//		int result = findLIS(arr);
		int result2 = findLIS2(arr);
//		System.out.println(result);
		System.out.println(result2);

		
	}

	
	private static int findLIS2(int[] arr) 
	{
		int dp[] = new int[arr.length];
		int max = 0;
		for(int i = 0; i< dp.length; i++)
		{
			for(int j = i - 1; j >= 0 ; j--)
			{
				if(arr[j] < arr[i])
				{
//					dp[i] = dp[i] > dp[j] + 1? dp[i]:dp[j]+1;
					dp[i] = dp[j] + 1;
					System.out.println("i: "+i+"    j:"+j+"    dp[i]:"+dp[i]);
				}
				
				if(max < dp[i])
				{
					max =dp[i];
				}
			}
		}
		return max + 1;
	}


	private static int findLIS(int[] arr)
	{
		int markArr[] = new int[arr.length];
		
		
		// index 0 and index default set
		markArr[0] = 1;
		for(int i = 1; i < markArr.length; i++)
		{
			markArr[i] = 0;
		}
		
		int max = 1;
	 
		//for each number i
		for(int i = 0 ; i < arr.length; i++)
		{	System.out.println("i:"+i);
			//check numbers before j
			boolean hasSmallNumber = false;
			ArrayList<Integer> candidates = new ArrayList<Integer>();
			candidates.add(0);
			int theCountBefore = 0;
			
			for(int j = i-1 ; j >= 0; j--)
			{
				System.out.println("j:"+j);
				if(arr[j] < arr[i])
				{
					System.out.println(arr[j] +"<" + arr[i] );
					hasSmallNumber = true;
					candidates.add(markArr[j]);
				}
				else 
				{
					candidates.add(0);
				}
			}
			
//			if(hasSmallNumber == false)
//			{
//				System.out.println("NO SMALL NUM");
//				markArr[i] = 1;
//				candidates.add(0);
//			}
			Collections.sort(candidates);
			System.out.println(candidates);
			markArr[i] = candidates.get(candidates.size()-1)+1;
			
			if(markArr[i] >= max)
			{
				max = markArr[i];
			}
			System.out.println("mark:"+ markArr[i]+"\n");

		}
		
		return max;
	}
	//{2,1,5,3,6,4,8,9,7};

}
