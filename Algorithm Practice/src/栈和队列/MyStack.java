package 栈和队列;

public class MyStack 
{
	//底层实现
	private long[] arr;
	private int top;
	
	/**
	 * constructor
	 */
	public MyStack()
	{
		arr = new long[10];
		top = -1;
	}
	
	/**
	 *  constuctor with parameters
	 */
	public MyStack(int maxSize)
	{
		arr = new long[maxSize];
		top = -1;
	}
	
	/**
	 *  push,pop, peek , isEmpty, isFull
	 */
	public void push(int value) 
	{
		arr[++top] = value;
	}
	
	public long pop()
	{
		return arr[top--];
	}
	
	public long peek()
	{
		return arr[top];
	}
	
	public boolean isEmpty()
	{
		return top == -1;
	}
	
	public boolean isFull()
	{
		return top == arr.length-1;
	}
	
}
