package 栈和队列;

public class MyQueue 
{
	//底层是数组
	private long[] arr;
	//有效数据大小
	private int elements;
	//队头 队尾
	private int front;
	private int end;
	
	/**
	 * 构造
	 */
	public MyQueue()
	{
		arr = new long[10];
		elements = 0;
		front = 0;
		end = -1;
	}
	
	public MyQueue(int maxSize)
	{
		arr = new long[maxSize];
		elements = 0;
		front = 0;
		end = -1;
	}
	
	//插入 从队尾
	public void insert(long value)
	{
		arr[++end] = value;
		elements ++;
	}
	
	//从队伍离开，从队头
	public long remove()
	{
		elements--;
		return arr[front++];
		
	}
	
	/*
	 * 查看数据
	 */
	public long peek()
	{
		return arr[front];
	}
	
	public boolean isEmpty()
	{
		return elements == 0;
	}
	public boolean isFull()
	{
		return elements == arr.length;
	} 
	
}
