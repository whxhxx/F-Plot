import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import javax.imageio.ImageIO;
import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;

import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;
import org.imgscalr.Scalr.Mode;
import org.omg.CORBA.DATA_CONVERSION;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
 
public class Demo 
{ 
 	private final static String base = "https://showcase1.firebaseio.com/";
 	
 	/**
 	 * TEST
 	 * @param args
 	 * @throws InterruptedException
 	 * @throws IOException
 	 */
	public static void main(String[] args) throws InterruptedException, IOException 
	{
		 System.out.println("Program Start...");
			 
		
		 String a = "Lorem ipsum dolor sit er elit lamet, consectetaur cillium adipisicing pecu, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Nam liber te conscient to factor tum poen legum odioque civiuda.";
	
//		setImageValue("e.png", "new data", "zone1", "2023");
//		setImageValue("e.png", "new data", "zone2", "2023");
//		setImageValue("e.png", "new data", "zone3", "2023");
//		setImageValue("e.png", "new data", "zone4", "2023");
//		setImageValue("e.png", "new data", "zone1", "2042");
//		setImageValue("e.png", "new data", "zone1", "2043");
		setImageValue("e.png", "new data", "zonebpis", "123");

		

 
//		 int number  =    124   ;
//while(number< 125)
//// 
//{
// 		setTextValue(a,"BPP VS Real Time Chart(SPX)","zone1",String.valueOf(number++));
// 	
//
//
//}


 		
//
// 		setTextValue(a,"badge test","zone4","2020");
// 		setTextValue(a,"badge test","zone5","2020");

// 		setTextValue(a,"badge test","zone5","2003");
//
// 		setTextValue(a,"badge test","zone5","2004");
//
// 		setTextValue(a,"badge test","zone5","2005");
// 		setTextValue(a,"badge test","zone5","2006");


//		 for(int i = 6 ; i< 10 ; i++)
//		 {
//			 setImageValue("f.png", "new data", "zone8", "20161000");
//			 setImageValue("g.png", "new data", "zone8", "20161001");
 
		 
//		 for(int i = 6 ; i< 10 ; i++)
//		 {
//			 setImageValue("f.png", "10", "zone5", "2002");
//		 }
//		 for(int i = 6 ; i< 10 ; i++)
//		 {
//			 setImageValue("f.png", "10"+String.valueOf(i), "zone3", "10"+String.valueOf(i));
//		 }
//		 for(int i = 6 ; i< 10 ; i++)
//		 {
//			 setImageValue("f.png", "10"+String.valueOf(i), "zone4", "10"+String.valueOf(i));
//		 }
//		 for(int i = 6 ; i< 10 ; i++)
//		 {
//			 setImageValue("f.png", "10"+String.valueOf(i), "zone5", "10"+String.valueOf(i));
//		 }
//		 for(int i = 6 ; i< 10 ; i++)
//		 {
//			 setImageValue("f.png", "10"+String.valueOf(i), "zone6", "10"+String.valueOf(i));
//		 }
//		 for(int i = 6 ; i< 10 ; i++)
//		 {
//			 setImageValue("f.png", "10"+String.valueOf(i), "zone7", "10"+String.valueOf(i));
//		 }
//		 for(int i = 6 ; i< 10 ; i++)
//		 {
//			 setImageValue("f.png", "10"+String.valueOf(i), "zone8", "10"+String.valueOf(i));
//		 }
//		 for(int i = 6 ; i< 10 ; i++)
//		 {
//			 setImageValue("f.png", "10"+String.valueOf(i), "zone9", "10"+String.valueOf(i));
//		 }
//		 for(int i = 6 ; i< 10 ; i++)
//		 {
//			 setImageValue("f.png", "10"+String.valueOf(i), "zone10", "10"+String.valueOf(i));
//		 }
//

	
		System.out.println("Done:Java upload");
		System.exit(0);	 		
	}
	
	/**
	 * this function is to create a small resolution image
	 * @param img
	 * @return
	 */
	public static BufferedImage createThumbnail(BufferedImage img) 
	{
		  img = Scalr.resize(img, Method.SPEED, Mode.AUTOMATIC, 600);
 		  return img;
	}

	/**
	 * 
	 * @param imageName : local image
	 * @param title : post title
	 * @param section: higher level node
	 * @param location: lower level node    root/section/location
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private static void setImageValue(String imageName, String title ,String section, String location) throws IOException, InterruptedException {

		Firebase ref = new Firebase(base);
		String base64String;
		String base64StringSmall;
		Map<String, Object> post1 = new HashMap<String, Object>();
		Map<String, Object> post2 = new HashMap<String, Object>();
		final CountDownLatch done = new CountDownLatch(2);
		Firebase postRef1 = ref.child(section).child(location);
		Firebase postRef2 = ref.child("image").child(section).child(location);

		//if image and text mode -----------------------------
		
			ByteArrayOutputStream baos=new ByteArrayOutputStream(1000);
			ByteArrayOutputStream baos2=new ByteArrayOutputStream(1000);
			
			BufferedImage img=ImageIO.read(new File(imageName));
			BufferedImage imgSmall = createThumbnail(img);
			
			ImageIO.write(img, "png", baos);
			baos.flush();
			base64String=Base64.getEncoder().encodeToString(baos.toByteArray());
			baos.close();
			
			ImageIO.write(imgSmall, "png", baos2);
			baos2.flush();
			base64StringSmall=Base64.getEncoder().encodeToString(baos2.toByteArray());
			baos2.close();
			
			System.out.println(base64StringSmall );
			post1.put("time", Calendar.getInstance().getTime().toString());
			post1.put("description", "");
			post1.put("title", title);
			post1.put("text", "n");
			post1.put("smallImage", base64StringSmall);
  		
			post2.put("imageString", base64String);
			
			postRef1.setValue(post1, new Firebase.CompletionListener() 
			{
			
			    @Override
			    public void onComplete(FirebaseError firebaseError, Firebase firebase) 
			    {
			    	
			        if (firebaseError != null) 
			        {
			            System.out.println("part1 could not be saved. " + firebaseError.getMessage());
			            done.countDown();
			        } 
			        else 
			        {
			            System.out.println("part1 saved successfully.");
			            done.countDown();
			        }
			    }
			});
			
			postRef2.setValue(post2, new Firebase.CompletionListener() 
			{
			
			    @Override
			    public void onComplete(FirebaseError firebaseError, Firebase firebase) 
			    {
			    	
			        if (firebaseError != null) 
			        {
			            System.out.println("part2 could not be saved. " + firebaseError.getMessage());
			            done.countDown();
			        } 
			        else 
			        {
			            System.out.println("part2 saved successfully.");
			            done.countDown();
			        }
			    }
			});
			done.await();
	
		return ;
	
			
	}

	/**
	 * 
	 * @param string : text content
	 * @param title : text title
	 * @param section : root/section/location
	 * @param location
	 * @throws InterruptedException
	 */
	private static void setTextValue(String string, String title,  String section, String location) throws InterruptedException 
	{
		Firebase ref = new Firebase(base);
 		Map<String, Object> post1 = new HashMap<String, Object>();
 		final CountDownLatch done = new CountDownLatch(1);
 		Firebase postRef1 = ref.child(section).child(location);
 		post1.put("time", Calendar.getInstance().getTime().toString());
		post1.put("description", string);
		post1.put("title", title);
		post1.put("text", "y");
	
		postRef1.setValue(post1, new Firebase.CompletionListener() 
			{
			
			    @Override
			    public void onComplete(FirebaseError firebaseError, Firebase firebase) 
			    {
			    	
			        if (firebaseError != null) 
			        {
			            System.out.println("part1 could not be saved. " + firebaseError.getMessage());
			            done.countDown();
			        } 
			        else 
			        {
			            System.out.println("part1 saved successfully.");
			            done.countDown();
			        }
			    }
			});
		done.await();
	
		return ;
	
	}

	
	/**
	 *  this method is for create user on Java . Not in use
	 * @param ref
	 * @param email
	 * @param password
	 */
	public static void createUser(Firebase ref, String email, String password)
	{
		Map<String, Object> newUsew = new HashMap<String, Object>();
		newUsew.put("email", email);
		newUsew.put("password", password);
		ref.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() 
		{
			@Override
			public void onSuccess(Map<String, Object> result) 
			{
				System.out.println("Successfully created user account with uid: " + result.get("uid"));
				Firebase postRef = ref.child("users").child((String) result.get("uid"));
				postRef.setValue(newUsew);
			}

			@Override
			public void onError(FirebaseError firebaseError) 
			{
				        // there was an error
			}
		});    
	}

	/**
	 * this method is for login. user login will have a uid generated by website. NOT in use
	 * @param ref
	 * @param email
	 * @param password
	 */
	public static void userLogin(Firebase ref, String email, String password)
	{
		System.out.println("ff");
		ref.authWithPassword(email, password, new Firebase.AuthResultHandler() {
		    @Override
		    public void onAuthenticated(AuthData authData) {
		        System.out.println("User ID: " + authData.getUid() + ", Provider: " + authData.getProvider());
		        Map<String, Object> post = new HashMap<String, Object>();
				post.put("description", "li jing ");
				post.put("imageUrl", "http://www.gmbpcapital.com/Images/rsz_logogmbp.png");
				ref.child("users").child(authData.getUid()).child("post1").updateChildren(post);
		    }

		    @Override
		    public void onAuthenticationError(FirebaseError firebaseError) {
		        // there was an error
		    }
		});
	}

	

	
}
