import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class AddNewData {

	public static void main(String[] args) throws IOException 
	{
		// TODO Auto-generated method stub
		String readFileName = "input.csv";
		String writeFileName = "ProcessedOut.csv";
		System.out.println("读取文件：" + readFileName);
		System.out.println("写入文件： " + writeFileName);

		File file = new File(readFileName);
		Scanner input = new Scanner(file);
		ArrayList<String> inputList = new ArrayList<String>();
		ArrayList<List> outList = new ArrayList<List>();

		while (input.hasNextLine()) {
			inputList.add(input.nextLine());
		}
		
		//*******
		//添加新行
		inputList.add("Oct 2015,1,2,3,4,5,6,\n7,8");
		
		FileWriter fw = new FileWriter("TestTest.csv");
		inputList.forEach(e->{try {
			fw.write(e.toString()+"\r");
			System.out.println(e);
		} catch (Exception e1) {
			e1.printStackTrace();
		}});
		fw.close();
	}//end of main

}
