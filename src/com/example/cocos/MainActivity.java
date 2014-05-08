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
	 
	    /**��ȡ��Phone���ֶ�**/  
	    private static final String[] PHONES_PROJECTION = new String[] {  
	        Phone.DISPLAY_NAME, Phone.NUMBER, Photo.PHOTO_ID,Phone.CONTACT_ID };  
	     
	    /**��ϵ����ʾ����**/  
	    private static final int PHONES_DISPLAY_NAME_INDEX = 0;  
	      
	    /**�绰����**/  
	    private static final int PHONES_NUMBER_INDEX = 1;  
	      
	    /**ͷ��ID**/  
	    private static final int PHONES_PHOTO_ID_INDEX = 2;  
	     
	    /**��ϵ�˵�ID**/  
	    private static final int PHONES_CONTACT_ID_INDEX = 3;  
	      
	 
	    /**��ϵ������**/  
	    private ArrayList<String> mContactsName = new ArrayList<String>();  
	      
	    /**��ϵ��ͷ��**/  
	    private ArrayList<String> mContactsNumber = new ArrayList<String>();  
	 
	    /**��ϵ��ͷ��**/  
	    private ArrayList<Bitmap> mContactsPhonto = new ArrayList<Bitmap>();  
	      
	    ListView mListView = null;  
	    ListAdapter myAdapter = null; 
	    /**�õ��ֻ�ͨѶ¼��ϵ����Ϣ**/ 
	    private void getphonecontact(){
	    	ContentResolver resolver = mContext.getContentResolver();  
	    	 
	        // ��ȡ�ֻ���ϵ��  
	        Cursor phoneCursor = resolver.query(Phone.CONTENT_URI,PHONES_PROJECTION, null, null, null);  
	        if (phoneCursor != null) {  
	            while (phoneCursor.moveToNext()) {  
	     
	            //�õ��ֻ�����  
	            String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);  
	            //���ֻ�����Ϊ�յĻ���Ϊ���ֶ� ������ǰѭ��  
	            if (TextUtils.isEmpty(phoneNumber))  
	                continue;  
	              
	            //�õ���ϵ������  
	            String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);  
	              System.out.println(contactName);
	            //�õ���ϵ��ID  
	            Long contactid = phoneCursor.getLong(PHONES_CONTACT_ID_INDEX);  
	     
	            //�õ���ϵ��ͷ��ID  
	            Long photoid = phoneCursor.getLong(PHONES_PHOTO_ID_INDEX);  
	              
	            //�õ���ϵ��ͷ��Bitamp  
	            Bitmap contactPhoto = null;  
	     
	            //photoid ����0 ��ʾ��ϵ����ͷ�� ���û�и���������ͷ�������һ��Ĭ�ϵ�  
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
//		// �õ������director����
//		CCDirector director=CCDirector.sharedDirector();
//		//���õ�ǰ����ʹ�õ�view����
//		director.attachInView(view);
//		//��ʾ֡���ʡ�
//		setContentView(view);
//		director.setDisplayFPS(true);
//		//������Ϸ��Ļ����
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
