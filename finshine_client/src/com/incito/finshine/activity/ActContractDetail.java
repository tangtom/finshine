package com.incito.finshine.activity;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.artifex.mupdfdemo.MuPDFCore;
import com.artifex.mupdfdemo.MuPDFPageAdapter;
import com.artifex.mupdfdemo.MuPDFReaderView;
import com.artifex.mupdfdemo.OutlineActivityData;
import com.incito.finshine.R;
import com.incito.finshine.activity.dialog.WritePadDialog;
import com.incito.finshine.activity.dialog.WritePadDialog.WritePadDialogListener;
import com.incito.finshine.common.Constant;
import com.incito.finshine.entity.BindProtecolParms;
import com.incito.utility.CommonUtil;
import com.incito.wisdomsdk.utils.BitmapUtils;

/**
 * 
 * <dl>
 * <dt>ActContractDetail.java</dt>
 * <dd>Description:签署协议 绑定协议</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 昆山灵动信息技术有限公司</dd>
 * <dd>CreateDate: 2014年6月13日 下午1:42:43</dd>
 * </dl>
 * 
 * @author LiNing
 */
public class ActContractDetail extends Activity implements
		WritePadDialogListener {

	BindProtecolParms parms = new BindProtecolParms();
	private MuPDFCore core;
	private AlertDialog.Builder mAlertBuilder;
	private MuPDFReaderView mDocView;

	private long customerId = 0l;
	public static final String KEY_CUSTOMER_ID = "key_customer_id";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_contract);
		init();
		initPdf();
		createUI(savedInstanceState);
		customerId = getIntent().getLongExtra(KEY_CUSTOMER_ID, 0l);
	}

	private boolean init() {

		TextView title = (TextView) findViewById(R.id.textTitle);
		title.setText(R.string.title_bind);

		ImageView back = (ImageView) findViewById(R.id.imageBack);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		// 重签
		Button resign = (Button) findViewById(R.id.btnResign);
		resign.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				WritePadDialog dialog = new WritePadDialog(
						ActContractDetail.this, ActContractDetail.this);
				dialog.show();
			}
		});

		// 确定
		Button next = (Button) findViewById(R.id.btnConfirm);
		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				ActContractDetail.this.finish();
			}
		});
		return true;
	}

	private void initPdf() {
		mAlertBuilder = new AlertDialog.Builder(this);

		if (core == null) {
			core = (MuPDFCore) getLastNonConfigurationInstance();

			// if (savedInstanceState != null &&
			// savedInstanceState.containsKey("FileName")) {
			// mFileName = savedInstanceState.getString("FileName");
			// }
		}
		if (core == null) {
			/*
			 * byte buffer[] = null;
			 * 
			 * Uri uri = Uri.parse(""); if
			 * (uri.toString().startsWith("content://")) { // Handle view
			 * requests from the Transformer Prime's file // manager //
			 * Hopefully other file managers will use this same scheme, if //
			 * not // using explicit paths. Cursor cursor =
			 * getContentResolver().query(uri, new String[] { "_data" }, null,
			 * null, null); if (cursor.moveToFirst()) { String str =
			 * cursor.getString(0); String reason = null; if (str == null) { try
			 * { InputStream is = getContentResolver() .openInputStream(uri);
			 * int len = is.available(); buffer = new byte[len]; is.read(buffer,
			 * 0, len); is.close(); } catch (java.lang.OutOfMemoryError e) {
			 * System.out .println("Out of memory during buffer reading");
			 * reason = e.toString(); } catch (Exception e) { reason =
			 * e.toString(); } if (reason != null) { buffer = null; Resources
			 * res = getResources(); AlertDialog alert = mAlertBuilder.create();
			 * setTitle(String .format(res
			 * .getString(R.string.cannot_open_document_Reason), reason));
			 * alert.setButton(AlertDialog.BUTTON_POSITIVE,
			 * getString(R.string.dismiss), new
			 * DialogInterface.OnClickListener() { public void onClick(
			 * DialogInterface dialog, int which) { finish(); } });
			 * alert.show(); return; } } else { uri = Uri.parse(str); } } }
			 */
			// if (buffer != null) {
			// core = openBuffer(buffer);
			// } else {

			 String path = Environment.getExternalStorageDirectory()
			 +"/Download/AndroidApplicationTestingGuide.pdf";

			core = openFile(path);
			// }

			if (core != null && core.needsPassword()) {

				return;
			}
			if (core != null && core.countPages() == 0) {
				core = null;
			}
		}
		if (core == null) {
//			AlertDialog alert = mAlertBuilder.create();
//			alert.setTitle(R.string.cannot_open_document);
//			alert.setButton(AlertDialog.BUTTON_POSITIVE,
//					getString(R.string.dismiss),
//					new DialogInterface.OnClickListener() {
//						public void onClick(DialogInterface dialog, int which) {
//							finish();
//						}
//					});
//			alert.show();
			return;
		}

	}


	public void createUI(Bundle savedInstanceState) {
		if (core == null)
			return;
		mDocView = new MuPDFReaderView(this) {
			@Override
			protected void onMoveToChild(int i) {
				if (core == null)
					return;
				super.onMoveToChild(i);
			}

			@Override
			protected void onTapMainDocArea() {

			}

			@Override
			protected void onDocMotion() {

			}
		};
		mDocView.setAdapter(new MuPDFPageAdapter(this, core));

		// Stick the document view and the buttons overlay into a parent view

		LinearLayout view = (LinearLayout) findViewById(R.id.linPdf);
		view.addView(mDocView);

	}

	private MuPDFCore openBuffer(byte buffer[]) {

		System.out.println("Trying to open byte buffer");
		try {
			core = new MuPDFCore(this, buffer);
			// New file: drop the old outline data
			OutlineActivityData.set(null);
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
		return core;
	}

	private MuPDFCore openFile(String path) {
		int lastSlashPos = path.lastIndexOf('/');
		String mFileName = new String(lastSlashPos == -1 ? path
				: path.substring(lastSlashPos + 1));
		System.out.println("Trying to open " + path);
		try {
			core = new MuPDFCore(this, path);
			// New file: drop the old outline data
			OutlineActivityData.set(null);
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
		return core;
	}

	@Override
	public void onSigned(Bitmap b) {
		// 保存文件
		CommonUtil.storeFile(BitmapUtils.bmpToByteArray(b, false),
				Constant.SIGN_DIC_BIND, Constant.SIGN_PROTECOL_FILE_UP
						+ customerId + ".jpg");
		hasSignFile = true;
	}

	public static boolean hasSignFile = false;
}
