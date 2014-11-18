package com.incito.utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.incito.finshine.common.Constant;
import com.incito.finshine.manager.BitmapManager;

public class CommonUtil extends ContextWrapper {

	static Context base;

	public CommonUtil(Context base) {
		super(base);
		// TODO Auto-generated constructor stub
		this.base = base;
	}

	public static void showToast(String alertMsg, Context context) {
		Toast.makeText(context, alertMsg, Toast.LENGTH_SHORT).show();
	}

	public static void showToast(int alertId, Context context) {
		Toast.makeText(context, context.getString(alertId), Toast.LENGTH_SHORT).show();
	}

	public static void hideSoftInputFromWindow(Activity activity) {
		InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm != null) {
			View view = activity.getCurrentFocus();
			if (view != null) {
				imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
			}
		}
	}

	public static File getFile(String url, String fileName) {
		File file = null;
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			file = new File(url);
			if (!file.exists()) {
				file.mkdirs();
			}
			file = new File(url, fileName);
			try {
				if (!file.exists()) {
					file.createNewFile();
				}
			} catch (Exception e) {
			}
			if (fileName.contains("txt")) {
				try {
					FileOutputStream strem = new FileOutputStream(file);
					byte[] bytes = new byte[10];
					for (int i = 0; i < bytes.length; i++) {
						bytes[i] = 's';
					}
					try {
						strem.write(bytes);
						strem.flush();
					} catch (IOException e) {
						e.printStackTrace();
					}

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}

			}
		}
		Log.i("file:", file.getAbsolutePath() + file.getName());
		return file;
	}

	public static File getFile(String url) {
		File file = null;
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			file = new File(url);
		}
		return file;
	}

	/**
	 * @author: lihs
	 * @Title: storeFileImag
	 * @Description: 拍照完成存储在本地文件中
	 * @param byteArray
	 * @date: 2013-8-1 下午4:17:34
	 */
	public static void storeFile(byte[] byteArray, String path, String fileName) {

		String states = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(states)) {
			// sd卡必须在挂载的情况下方可使用
			try {
				File file = new File(path);
				if (!file.exists()) {
					file.mkdirs();
				}
				file = new File(path, fileName);
				if (!file.exists()) {
					try {
						file.createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				OutputStream os = new FileOutputStream(file);
				os.write(byteArray, 0, byteArray.length);
				os.flush();
				os.close();
			} catch (Exception e) {
				Log.e("file", " 读写文件异常" + e.getLocalizedMessage());
			}
		}
	}

	public static void renameFile(String path, String deleFileName, String desFileName) {
		String states = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(states)) {
			// sd卡必须在挂载的情况下方可使用
			try {
				// 肯定存在路径
				File file = new File(path, deleFileName);
				if (file.exists()) {// 文件存在
					file.delete();
				}
				File currentfile = new File(path, desFileName);
				currentfile.renameTo(file);

			} catch (Exception e) {
				Log.e("file", " 读写文件异常" + e.getLocalizedMessage());
			}

		}
	}

	public static void PhotoFromAblum(Activity cx) {

		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("image/*");
		cx.startActivityForResult(intent, Constant.REQUEST_ALBUM_PHOTO_RESLUT);
	}

	public static void PhotoFromCamera(Activity cx, String path, String fileName) {

		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(CommonUtil.getFile(Constant.FINSHINE, Constant.OPPOSIVE)));
		cx.startActivityForResult(intent, Constant.REQUEST_TAKE_PHOTO);
	}

	public static void doCropPhoto(File f, Activity cx) {
		try {
			Intent intent = getCropImageIntent(Uri.fromFile(f));
			cx.startActivityForResult(intent, Constant.PHOTO_PICKED_WITH_DATA);
		} catch (Exception e) {
			Toast.makeText(base, "裁剪图片异常", Toast.LENGTH_SHORT).show();
		}
	}

	public static Intent getCropImageIntent(Uri photoUri) {
		Intent intent = null;
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

	public static Bitmap saveLocalFile(Intent data, Bitmap selectImage) {
		Bundle extras = data.getExtras();
		if (extras == null) {
			String file = data.getDataString();
			if (!TextUtils.isEmpty(file) && file.startsWith("file:///")) {
				if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
					file = file.replace("file://", "");
					try {
						file = URLDecoder.decode(file, "UTF-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
			}
			selectImage = BitmapManager
					.decodeSampledBitmapFromFile(file, Constant.COMMOM_WIDTH, Constant.COMMOM_HEIGHT);
		} else {
			selectImage = (Bitmap) extras.get("data");
		}
		return selectImage;
	}

	public static void sendSms(Context context, String targetPhone) {
		if (TextUtils.isEmpty(targetPhone)) {
			CommonUtil.showToast("目标号码不能为空", context);
			return;
		}
		// 多个号码以逗号拆分
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_SENDTO);
		// 需要发短息的号码
		intent.setData(Uri.parse("smsto:" + targetPhone));
		context.startActivity(intent);
	}

	public static void sendEmail(Context context, String[] emailReciver, String emailContent, String emialTitle) {
		 Intent email = new Intent(Intent.ACTION_SEND);
		 email.setType("plain/text");
		/// String emailSubject = "从问道分享来的文章";
		 //设置邮件默认地址
		 email.putExtra(android.content.Intent.EXTRA_EMAIL, emailReciver);
		// 设置邮件默认标题
		// email.putExtra(android.content.Intent.EXTRA_SUBJECT, emailSubject);
		// 设置要默认发送的内容
		// email.putExtra(android.content.Intent.EXTRA_TEXT, emailContent);
		// 调用系统的邮件系统
		 ComponentName componentName = email.resolveActivity(context.getPackageManager());
			if (componentName != null) {
				 email.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				 context.startActivity(Intent.createChooser(email, "请选择邮件发送软件"));
			}
		
	}

	public static void dialPhone(Context context, String targetPhone) {

		if (TextUtils.isEmpty(targetPhone)) {
			CommonUtil.showToast("目标号码不能为空", context);
			return;
		}
		Intent intent = new Intent(Intent.ACTION_DIAL);
		intent.setData(Uri.parse("tel:" + targetPhone));
		context.startActivity(intent);
	}

	public static final String getProductStates(long states) {

		int state = (int) states;
		// 1新建 2审核 3预约 4预售 5在售 6封账 7下架
		String prodStaes = "";
		switch (state) {
		case 1:
			prodStaes = "新建";
			break;
		case 2:
			prodStaes = "审核";
			break;
		case 3:
			prodStaes = "预约";
			break;
		case 4:
			prodStaes = "预售";
			break;
		case 5:
			prodStaes = "在售 ";
			break;
		case 6:
			prodStaes = "封账";
			break;
		default:
			break;
		}
		return prodStaes;
	}

	private static int screen_w = 0; // 手机屏幕的宽度，单位像素
	private static int screen_h = 0; // 手机屏幕的高度，单位像素

	private static void initScreenInfo(Context mContext) {
		DisplayMetrics dm = new DisplayMetrics();
		dm = mContext.getResources().getDisplayMetrics();

		float density = dm.density; // 屏幕密度（像素比例：0.75/1.0/1.5/2.0）
		int densityDPI = dm.densityDpi; // 屏幕密度（每寸像素：120/160/240/320）
		float xdpi = dm.xdpi;
		float ydpi = dm.ydpi;

		// 获取屏幕密度（方法3）
		// dm = new DisplayMetrics();
		// getWindowManager().getDefaultDisplay().getMetrics(dm);

		density = dm.density; // 屏幕密度（像素比例：0.75/1.0/1.5/2.0）
		densityDPI = dm.densityDpi; // 屏幕密度（每寸像素：120/160/240/320）
		xdpi = dm.xdpi;
		ydpi = dm.ydpi;

		Log.e("commonutil" + "  DisplayMetrics", "xdpi=" + xdpi + "; ydpi=" + ydpi);
		Log.e("commonutil" + "  DisplayMetrics", "density=" + density + "; densityDPI=" + densityDPI);

		screen_w = dm.widthPixels;
		screen_h = dm.heightPixels;

		// Log.e(TAG + "  DisplayMetrics(222)", "screenWidthDip=" +
		// screenWidthDip + "; screenHeightDip=" + screenHeightDip);
		//
		// screen_w = (int)(dm.widthPixels * density + 0.5f); // 屏幕宽（px，如：480px）
		// screen_h = (int)(dm.heightPixels * density + 0.5f); //
		// 屏幕高（px，如：800px）

		Log.e("commonutil" + "  DisplayMetrics(222)", "screenWidth=" + screen_w + "; screenHeight=" + screen_h);
	}

	public static int getScreenWidth(Context mContext) {
		// if(screen_w == 0)
		// {
		initScreenInfo(mContext);
		// }
		return screen_w;
	}

	public static int getScreenHeight(Context mContext) {
		// if(screen_h == 0)
		// {
		initScreenInfo(mContext);
		// }
		return screen_h;
	}

	public static Bitmap convertFileToImage(String path, String name) {

		try {
			// oom 问题
			return BitmapFactory.decodeFile(path + "/" + name);
		} catch (Exception e) {
		}
		return null;
	}

	public static final boolean isNotEmptyfile(String filePath) {
		if (!FileUtils.isFileExist(filePath) || FileUtils.getFileSize(filePath) <= 0) {
			return false;
		}
		return true;
	}

	public static String trimString(String text) {
		if (text == null)
			return "未知";
		else
			return text;
	}

	public static String getContentType(String fileName) {

		if (TextUtils.isEmpty(fileName)) {
			return "";
		}
		String stuff = fileName.substring(fileName.lastIndexOf("."), fileName.length() - 1);

		Map<String, String> contenType = new HashMap<String, String>();
		contenType.put(".jpg", "image/jpeg");
		contenType.put(".jpeg", "image/jpeg");
		contenType.put(".mp4", "video/mpeg4");
		contenType.put(".mpeg", "video/mpg");
		contenType.put(".mpg", "video/mpg");
		contenType.put(".mpe", "video/x-mpeg");
		contenType.put(".png", "image/png");

		return contenType.get(stuff);
	}

}
