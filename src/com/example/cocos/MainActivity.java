package com.example.cocos;

import java.io.InputStream;
import java.util.ArrayList;

import org.cocos2d.opengl.CCGLSurfaceView;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts.Photo;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
	
	private CCGLSurfaceView view;
	 Context mContext = null;  
	 
	    /**获取库Phone表字段**/  
	    private static final String[] PHONES_PROJECTION = new String[] {  
	        Phone.DISPLAY_NAME, Phone.NUMBER, Photo.PHOTO_ID,Phone.CONTACT_ID };  
	     
	    /**联系人显示名称**/  
	    private static final int PHONES_DISPLAY_NAME_INDEX = 0;  
	      
	    /**电话号码**/  
	    private static final int PHONES_NUMBER_INDEX = 1;  
	      
	    /**头像ID**/  
	    private static final int PHONES_PHOTO_ID_INDEX = 2;  
	     
	    /**联系人的ID**/  
	    private static final int PHONES_CONTACT_ID_INDEX = 3;  
	      
	 
	    /**联系人名称**/  
	    private ArrayList<String> mContactsName = new ArrayList<String>();  
	      
	    /**联系人头像**/  
	    private ArrayList<String> mContactsNumber = new ArrayList<String>();  
	 
	    /**联系人头像**/  
	    private ArrayList<Bitmap> mContactsPhonto = new ArrayList<Bitmap>();  
	      
	    ListView mListView = null;  
	    ListAdapter myAdapter = null; 
	    /**得到手机通讯录联系人信息**/ 
	    private void getphonecontact(){
	    	ContentResolver resolver = mContext.getContentResolver();  
	    	 
	        // 获取手机联系人  
	        Cursor phoneCursor = resolver.query(Phone.CONTENT_URI,PHONES_PROJECTION, null, null, null);  
	        if (phoneCursor != null) {  
	            while (phoneCursor.moveToNext()) {  
	     
	            //得到手机号码  
	            String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);  
	            //当手机号码为空的或者为空字段 跳过当前循环  
	            if (TextUtils.isEmpty(phoneNumber))  
	                continue;  
	              
	            //得到联系人名称  
	            String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);  
	              System.out.println(contactName);
	            //得到联系人ID  
	            Long contactid = phoneCursor.getLong(PHONES_CONTACT_ID_INDEX);  
	     
	            //得到联系人头像ID  
	            Long photoid = phoneCursor.getLong(PHONES_PHOTO_ID_INDEX);  
	              
	            //得到联系人头像Bitamp  
	            Bitmap contactPhoto = null;  
	     
	            //photoid 大于0 表示联系人有头像 如果没有给此人设置头像则给他一个默认的  
	            if(photoid > 0 ) {  
	                Uri uri =ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI,contactid);  
	                InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(resolver, uri);  
	                contactPhoto = BitmapFactory.decodeStream(input);  
	            }else {  
	                contactPhoto = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);  
	            }  
	            mContactsName.add(contactName);  
	            mContactsNumber.add(phoneNumber);  
	            mContactsPhonto.add(contactPhoto);  
	            }  
	     
	            phoneCursor.close();  
	        }  
	              
	        
	    }
	    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, 
//				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//		view =new CCGLSurfaceView(this);
//		// 得到共享的director对象。
//		CCDirector director=CCDirector.sharedDirector();
//		//设置当前程序使用的view对象。
//		director.attachInView(view);
//		//显示帧速率。
//		setContentView(view);
//		director.setDisplayFPS(true);
//		//控制游戏屏幕方向。
//		director.setDeviceOrientation(CCDirector.kCCDeviceOrientationLandscapeLeft);
//		director.setAnimationInterval(1.0f/30);
//		CCScene scene = CCScene.node();
//		 scene.addChild(new Layer());
//		 director.runWithScene(scene);
//		getphonecontact();
		System.out.println("1234");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}
