package com.incito.finshine.test;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.incito.finshine.R;

public class TestPhoto extends Activity {

	private static final int REQUEST_TAKE_PHOTO = 1;
	private static final int REQUEST_ALBUM_PHOTO_RESLUT = 2;
	private static final int PHOTO_PICKED_WITH_DATA = 3;
	public static final String KEY_FILE_ABSOLUTELY = "key_file_absolutely";
	private File albumTakePhotoFile; 
	private String UPLOAD_PATH = Environment.getExternalStorageDirectory() + File.separator+ "incito";
	private String fileName = "demo.jpg";
	
	private ImageView userPictureIv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
 
		setContentView(R.layout.test_photo);
 
		albumTakePhotoFile = getFile(UPLOAD_PATH, fileName);
		
		findViewById(R.id.takephoto).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
					Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
					intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(albumTakePhotoFile));
					startActivityForResult(intent, REQUEST_TAKE_PHOTO);
				}  
			}
		});
		
		findViewById(R.id.takephoto2).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.setType("image/*");
				startActivityForResult(intent, REQUEST_ALBUM_PHOTO_RESLUT);
			}
		});
	    userPictureIv   = (ImageView)findViewById(R.id.takephoto3);
	  
	}
 
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		// �������4.1.2 �����ַ��� �ļ�·����ʧ���ȱ����ļ�·����Ȼ����oncreate�������лָ�
		if (albumTakePhotoFile != null&& !TextUtils.isEmpty(albumTakePhotoFile.getAbsolutePath())) {
			outState.putString(KEY_FILE_ABSOLUTELY,albumTakePhotoFile.getAbsolutePath());
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			try {
				Bitmap selectImage = null;
				if (requestCode == REQUEST_ALBUM_PHOTO_RESLUT) {
					// ���ļ�����ѡ���ͼƬ���ص���file:///
					String file = data.getDataString();
					if (!TextUtils.isEmpty(file) && file.startsWith("file:///")) {
						if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
							file = file.replace("file://","");
							 try {
								 // java���ļ�ϵͳ��linux,������ʽ��UTF-8�ı����ʽ
								 file = URLDecoder.decode(file, "UTF-8");
							} catch (UnsupportedEncodingException e) {
								e.printStackTrace();
							}
						}
						doCropPhoto(new File(file));
						
					}else{
						// ��ͼ��ѡ�� ���ع����� contenturl :
						Uri url  = data.getData();
						try {
							// ����galleryȥ���������Ƭ
							Intent intent = getCropImageIntent(url);
							startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
						  } catch (Exception e) {
							Toast.makeText(this, "����ͼƬʧ��", Toast.LENGTH_SHORT).show();
						}
					}
				} else if (requestCode == REQUEST_TAKE_PHOTO) {
					// �������򷵻ص�,�ٴε���ͼƬ��������ȥ�޼�ͼƬ
					doCropPhoto(albumTakePhotoFile);
					
				} else if (requestCode == PHOTO_PICKED_WITH_DATA) {
					// �ü����Ĵ���
					saveLocalFile(data, selectImage);
				}
			} catch (Exception e) {
				e.getLocalizedMessage();
			}
		}
	}
	
 

	/**
	 * @author: lihs
	 * @Title: saveLocalFile
	 * @Description: ͼƬ���浽ָ�����ļ�
	 * @param data
	 * @param selectImage
	 * @date: 2013-8-13 ����1:47:35
	 */
	private void saveLocalFile(Intent data, Bitmap selectImage) {
		Bundle extras = data.getExtras();
		if (extras == null) {
			// ��ͼƬ��ַ
			String file = data.getDataString();
			if (!TextUtils.isEmpty(file) && file.startsWith("file:///")) {
				if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
					file = file.replace("file://","");
					 try {
						 // java���ļ�ϵͳ��linux,������ʽ��UTF-8�ı����ʽ
						 file = URLDecoder.decode(file, "UTF-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
			}
			selectImage = BitmapFactory.decodeFile(file);	
			 
		}else{
			selectImage = (Bitmap) extras.get("data");
		}
		userPictureIv.setImageBitmap(selectImage);
	}
 

	/**
	 * @author: lihs
	 * @Title: doCropPhoto
	 * @Description: ����ͼƬ
	 * @param f
	 * @date: 2013-8-12 ����11:19:52
	 */
	protected void doCropPhoto(File f) {
		try {
			// ����galleryȥ���������Ƭ
			Intent intent = getCropImageIntent(Uri.fromFile(f));
			startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
		} catch (Exception e) {
			Toast.makeText(this, "ʧ��", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * @author: lihs
	 * @Title: getCropImageIntent
	 * @Description: ���òü�ͼƬ��intent
	 * @param photoUri
	 * @return
	 * @date: 2013-8-12 ����11:20:22
	 */
	private Intent getCropImageIntent(Uri photoUri) {
		Intent intent = null;
		// ����ϵͳ���շ��زü�
		intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(photoUri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 300);
		intent.putExtra("outputY", 300);
		intent.putExtra("return-data", true);
		return intent;
	}
	
	private File getFile(String url,String fileName){
		
		File file = null;
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			// sd ������
			file = new File(url);
			if (!file.exists()) {
				file.mkdirs();
			}
			file = new File(url, fileName);
		}else{
			Toast.makeText(this, "SD������ʹ��", Toast.LENGTH_LONG).show();
		}
		
		return file;
	  }
	}
  
