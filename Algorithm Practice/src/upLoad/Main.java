package upLoad;

import java.util.ArrayList;
import java.util.Scanner;
public class Main{
    
    static Node[] nodes  = new Node[100001];

	static int[] distance   = new int[100001];
    public static void main(String[] args) 
    {
    
		for (int i = 0; i< distance.length; i++)
		{
			distance[i] = -1;
		}
		
		Scanner scanner = new Scanner(System.in);
		String line = scanner.nextLine();
		line.trim();
    	String []lineArr  = line.split("(\\s){1,5}") ;
		int n = Integer.parseInt(lineArr[0]);
		int m = Integer.parseInt(lineArr[1]);
		for(int i =0; i <= n; i++)
		{
			nodes[i] = new Node();
		}
		
		for(int i =1; i < n; ++i)
		{
			String line2 = scanner.nextLine();
			String []line2Arr  = line2.trim().split("(\\s){1,5}") ;
 	  
			int ai = Integer.parseInt(line2Arr[0]);
			int bi = Integer.parseInt(line2Arr[1]);

			nodes[ai].city.add(bi);
			nodes[bi].city.add(ai);
		}
		
		distance[1] = 0;
		dfs(1,0);
		
		for(int i = 0; i < m ; ++i)
		{
			String line3 = scanner.nextLine();
    		String []line3Arr  = line3.split("(\\s){1,5}") ;
 	  
			int ci = Integer.parseInt(line3Arr[0]);
			int pi = Integer.parseInt(line3Arr[1]);
			
			if(ci ==1)
			{
				distance[pi] = 0;
				dfs(pi, 0);
			}
			else 
			{
				System.out.println(distance[pi]);
			}
		}
		
		scanner.close();
 
    }
    
    private static void dfs(int x, int p) 
	{
 		ArrayList<Integer> vecs = nodes[x].city;
		
		for(int i = 0; i< vecs.size(); ++i)
		{
			if( vecs.get(i) == p)
			{
 				continue;
			}
			
 			distance[vecs.get(i)] = distance[x] + 1;
 			dfs(vecs.get(i), x);
		}
	}
}

class Node
{
	 ArrayList<Integer> city;
	 public Node()
	 {
		 city = new ArrayList<Integer>();
 	 }
}