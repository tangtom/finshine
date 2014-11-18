package com.incito.finshine.activity;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.custom.view.CommonDialog;
import com.custom.view.CommonDialog.BtnClickedListener;
import com.custom.view.DlgCommFilter;
import com.custom.view.DlgCommFilter.RefreshFilterListener;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.incito.finshine.R;
import com.incito.finshine.common.Constant;
import com.incito.finshine.entity.Customer;
import com.incito.finshine.entity.MarketCsOrder;
import com.incito.finshine.entity.MarketStateReslut;
import com.incito.finshine.manager.BitmapManager;
import com.incito.finshine.manager.SPManager;
import com.incito.finshine.network.HttpUtils;
import com.incito.finshine.network.HttpUtils.FaliureResult;
import com.incito.finshine.network.HttpUtils.FileSuccessReslut;
import com.incito.finshine.network.HttpUtils.SuccessReslut;
import com.incito.finshine.network.Request;
import com.incito.finshine.network.Request.RequestType;
import com.incito.finshine.network.RequestDefine;
import com.incito.utility.CommonUtil;
import com.incito.wisdomsdk.net.http.RequestParams;
import com.incito.wisdomsdk.utils.BitmapUtils;

/**
 * 
 * <dl>
 * <dt>ActBind.java</dt>
 * <dd>Description:绑定理财师 两步</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 昆山灵动信息技术有限公司</dd>
 * <dd>CreateDate: 2014年6月10日 上午11:15:26</dd>
 * </dl>
 * 
 * @author LiNing
 */
public class ActBind extends FragmentDetail implements OnClickListener {

	private final String TAG = ActBind.class.getSimpleName();
	private static final int F_FIRST_BIND = 0;
	private static final int F_SECOND_BIND = 1;

	public static int currentShowView = F_FIRST_BIND;

	private ImageView icon;

	static ActBind instanceA;
	Button nation;
	EditText csName;
	TextView txtCsName;// 理财师名称
	public View view;

	private static final int REQUEST_TAKE_PHOTO = 1;
	private static final int REQUEST_ALBUM_PHOTO_RESLUT = 2;
	private static final int PHOTO_PICKED_WITH_DATA = 3;
	public static final String ACTION_CUSTOMER_ID = "action_customer_id";
	private MarketCsOrder csOrder; // 点击按钮传过来的参数

	public Customer temp = new Customer();
	private Customer cs = null;

	public long marketStatues = Constant.BIND_AGREE_MENT_STATUS;
	private SPManager spManager;//SharedPreferences公众类
	public long getMarketStatues() {
		return marketStatues;
	}

	public MarketCsOrder getCsOrder() {
		return csOrder;
	}

	// 判断该界面是可以编辑还是只是显示界面 true 是可以编辑，false不可以编辑
	public boolean isCanEditBindProtecol() {
		return ((ActCustomerMarketProgress) getActivity())
				.canEdit(ActCustomerMarketProgress.F_BIND);
	}

	public static ActBind newInstance(int id) {

		ActBind f = new ActBind();
		Bundle args = new Bundle();
		args.putInt(FIELD_ID, id);
		f.setArguments(args);
		instanceA = f;
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		System.out.print("ActBind onCreate :currentView=" + currentShowView);
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (container == null) {
			return null;
		}
		view = inflater.inflate(R.layout.act_bind, container, false);
		// 上一步
		Button btnPre = (Button) view.findViewById(R.id.btnPre);
		btnPre.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				FragmentDetail details = (FragmentDetail) getFragmentManager()
						.findFragmentById(R.id.details);
				// 数据有没有更新，保存数据
				boolean ret = details instanceof FragmentBindSecond;
				if (ret) {
					showDetails(F_FIRST_BIND);
				} else {
					if (ret & details != null) {
						((FragmentBindFirst) details).isSave(true);
						ActBind.this.isSave(true);
						initHttpType(UPDATE_CS_INFO);

						// TODO
						// final FragmentDetail details1 = (FragmentDetail)
						// getFragmentManager().findFragmentById(R.id.details);
						// if (details instanceof FragmentBindSecond) {
						// CommonDialog internetSett = new
						// CommonDialog(getActivity());
						// internetSett.setTitle(R.string.alert);
						// internetSett.setMessage("您确定与理财师"+csOrder.getSalesId()+"进行绑定!");
						// internetSett.setPositiveButton(new
						// BtnClickedListener() {
						//
						// public void onBtnClicked() {
						// ((FragmentBindSecond)details1).initHttpUtils(FragmentBindSecond.UPLOAD_BIND_PROTECAL_FILE);
						// }
						// }, R.string.btn_ok);
						// internetSett.setCancleButton(null,
						// R.string.btn_cancle);
						// internetSett.showDialog();
						// }
					}
					// 跳到产品详情
					((ActCustomerMarketProgress) getActivity()).showDetails(
							ActCustomerMarketProgress.F_PRODUCT_COMPARE, 0);
				}
			}
		});

		// 下一步
		Button btnNext = (Button) view.findViewById(R.id.btnNext);
		btnNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				FragmentDetail details = (FragmentDetail) getFragmentManager()
						.findFragmentById(R.id.details);
				boolean ret = details instanceof FragmentBindFirst;
				if (ret) {
					// 第一步
					if (isCanEditBindProtecol()) {
						if (details != null) {
							// 数据写到 客户中,
							if (!isUploadHead) {
								CommonUtil.showToast("请上传头像认证", getActivity());
							} else if (!((FragmentBindFirst) details)
									.checkData()) {
								return;
							} else {
								((FragmentBindFirst) details).isSave(true);
								showDetails(F_SECOND_BIND);
							}
						}

					} else {
						showDetails(F_SECOND_BIND);
					}
				} else {
					// 第二步骤
					ret = details instanceof FragmentBindSecond;
					if (!isCanEditBindProtecol()) {
						// 签订合同
						ActCustomerMarketProgress.currentView = ActCustomerMarketProgress.F_SIGN_FIRST;
						((ActCustomerMarketProgress) getActivity())
								.showDetails(
										ActCustomerMarketProgress.F_SIGN_FIRST,
										0);
					}
					if (!((FragmentBindSecond) details).CheckData()) {
						return;
					} else {
						// 保存数据
						((FragmentBindSecond) details).initData(false);
						ActBind.this.isSave(true);
						initHttpType(UPDATE_CS_INFO);
					}
				}
			}
		});

		csOrder = ((ActCustomerMarketProgress) getActivity()).getMarketCs();
		uploadFileName = Constant.UP_HEAD + csOrder.getCustomerId() + ".jpg";
		downLoadFileName = Constant.DOWN_HEAD + csOrder.getCustomerId()
				+ ".jpg";
		initUI();

		marketStatues = csOrder.getMarketingStatusId();
		isCanEdit(!isCanEditBindProtecol());

		initHttpType(QUERY_CS_INFO);
		queryMarketStatues();

		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
		showDetails(currentShowView);
	}

	private boolean isUploadHead = false;
	private Button uploadIcon;
	private TextView txtSalesName;

	private boolean initUI() {

		txtSalesName = (TextView) view.findViewById(R.id.txtSalesName);
		csName = (EditText) view.findViewById(R.id.editName);
		nation = (Button) view.findViewById(R.id.spinnerCountry);
		txtCsName = (TextView) view.findViewById(R.id.txtCsName);
		spManager = SPManager.getInstance();
		txtSalesName.setText(spManager.getStringValue(SPManager.NAME));
		icon = ((ImageView) view.findViewById(R.id.csIcon));
		icon.setOnClickListener(this);
		uploadIcon = (Button) view.findViewById(R.id.btnUploadIcon);
		uploadIcon.setOnClickListener(this);
		nation = (Button) view.findViewById(R.id.spinnerCountry);
		nation.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View v) {

				DlgCommFilter nation = new DlgCommFilter(getActivity(),
						R.array.nationality_status, R.string.sele_national, "",
						true, 0);
				nation.setHiddenInput(true);
				nation.setListener(new RefreshFilterListener() {

					@Override
					public void doRefresh(String reslut, int position,boolean b,int title) {
						((Button) v).setText(reslut);
					}
				});
				nation.showDialog();
			}
		});

		if (CommonUtil.isNotEmptyfile(Constant.HEAD + "/" + downLoadFileName)) {
			// 优先显示下载过的头像
			isUploadHead = true;
			icon.setImageBitmap(BitmapManager.decodeSampledBitmapFromFile(
					Constant.HEAD + "/" + downLoadFileName,
					Constant.USER_ICON_WIDTH, Constant.USER_ICON_HEIGHT));
		} else if (CommonUtil.isNotEmptyfile(Constant.HEAD + "/"
				+ uploadFileName)) {
			// 否则显示上次选择的照片
			isUploadHead = true;
			icon.setImageBitmap(BitmapManager.decodeSampledBitmapFromFile(
					Constant.HEAD + "/" + uploadFileName,
					Constant.USER_ICON_WIDTH, Constant.USER_ICON_HEIGHT));
		}
		return true;
	}

	public void isCanEdit(boolean CannotEdit) {
		if (CannotEdit) {
			nation.setEnabled(false);
			icon.setEnabled(false);
			csName.setFocusable(false);
			uploadIcon.setEnabled(false);
		}
	}

	private void isSave(boolean isEdit) {

		if (cs == null) {
			return;
		}
		if (isEdit) {
			temp.setName(csName.getText().toString());
			// temp.setNationality(nation.getText().toString());

		} else {
			csName.setText(cs.getName());
			try {
				// nation.setText(getResources().getStringArray(R.array.all_nationnal)[Integer.parseInt(cs.getNationality())]);
			} catch (Exception e) {
			}
			txtCsName.setText(cs.getName());
			Log.i(TAG, "-----------" + csOrder.getSalesId());
//			txtSalesName.setText(csOrder.getSalesId() + " ");
		}
	}

	public void showDetails(int id) {

		currentShowView = id;
		ImageView titleImage;
		titleImage = (ImageView) view.findViewById(R.id.titleImage);
		Log.i(TAG, "actbind showDetails = " + currentShowView);
		FragmentDetail details = (FragmentDetail) getFragmentManager()
				.findFragmentById(R.id.details);
		if (details == null || details.getId() != id) {
			switch (id) {
			case F_FIRST_BIND:
				details = FragmentBindFirst.newInstance(id);
				titleImage
						.setImageResource(R.drawable.customer_market_collectinfo1);
				break;
			case F_SECOND_BIND:
				details = FragmentBindSecond.newInstance(id);
				titleImage
						.setImageResource(R.drawable.customer_market_collectinfo2);
				break;
			default:
				break;
			}
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.replace(R.id.details, details);
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.commit();
		}
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.csIcon:
			// 客户头像
			DlgCommFilter dlgCommFilter = new DlgCommFilter(getActivity(),
					R.array.select_icon, R.string.ablum_selec, "拍照", true, 0);
			dlgCommFilter.setListener(new RefreshFilterListener() {

				@Override
				public void doRefresh(String reslut, int position,boolean b,int title) {
					if (position == 0) {
						// 拍照
						albumTakePhotoFile = CommonUtil.getFile(Constant.HEAD,
								uploadFileName);
						Intent intent = new Intent(
								"android.media.action.IMAGE_CAPTURE");
						intent.putExtra(MediaStore.EXTRA_OUTPUT,
								Uri.fromFile(albumTakePhotoFile));
						startActivityForResult(intent, REQUEST_TAKE_PHOTO);
					} else {
						// 从相册选择
						Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
						intent.setType("image/*");
						startActivityForResult(intent,
								REQUEST_ALBUM_PHOTO_RESLUT);
					}
				}
			});
			dlgCommFilter.setHiddenInput(true);
			dlgCommFilter.showDialog();
			break;
		case R.id.btnUploadIcon:
			initHttpType(UPLOAD_USER_ICON);
			break;
		default:
			break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == getActivity().RESULT_OK) {
			try {
				Bitmap selectImage = null;
				if (requestCode == REQUEST_ALBUM_PHOTO_RESLUT) {
					String file = data.getDataString();
					if (!TextUtils.isEmpty(file) && file.startsWith("file:///")) {
						if (Environment.MEDIA_MOUNTED.equals(Environment
								.getExternalStorageState())) {
							file = file.replace("file://", "");
							try {
								file = URLDecoder.decode(file, "UTF-8");
							} catch (UnsupportedEncodingException e) {
								e.printStackTrace();
							}
						}
						//doCropPhoto(new File(file));
						Uri uri = Uri.parse(file);
						Intent intent = new Intent();
						intent.setDataAndType(uri, "image/*");
						saveLocalFile(intent, selectImage);
					} else {
						Uri url = data.getData();
						try {
							Intent intent = new Intent();
							intent.setDataAndType(url, "image/*");
							saveLocalFile(intent, selectImage);
						} catch (Exception e) {
						}
					}
				} else if (requestCode == REQUEST_TAKE_PHOTO) {
					//doCropPhoto(albumTakePhotoFile);
					Uri uri = Uri.parse(albumTakePhotoFile.getPath());
					Intent intent = new Intent();
					intent.setDataAndType(uri, "image/*");
					saveLocalFile(intent, selectImage);

				} else if (requestCode == PHOTO_PICKED_WITH_DATA) {
					saveLocalFile(data, selectImage);
				}
			} catch (Exception e) {
				e.getLocalizedMessage();
			}
		}
	}

	private void saveLocalFile(Intent data, Bitmap selectImage) {
		Bundle extras = data.getExtras();
		if (extras == null) {
			String file = data.getDataString();
			if (!TextUtils.isEmpty(file) && file.startsWith("file:///")) {
				if (Environment.MEDIA_MOUNTED.equals(Environment
						.getExternalStorageState())) {
					file = file.replace("file://", "");
					try {
						file = URLDecoder.decode(file, "UTF-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
			}
			selectImage = BitmapFactory.decodeFile(file);

		} else {
			selectImage = (Bitmap) extras.get("data");
		}
		// 绑定头像
		icon.setImageBitmap(selectImage);
		CommonUtil.storeFile(BitmapUtils.bmpToByteArray(selectImage, false),
				Constant.HEAD, uploadFileName);
	}

	protected void doCropPhoto(File f) {
		try {
			Intent intent = getCropImageIntent(Uri.fromFile(f));
			startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
		} catch (Exception e) {
			Toast.makeText(getActivity(), "裁剪图片异常", Toast.LENGTH_SHORT).show();
		}
	}

	private Intent getCropImageIntent(Uri photoUri) {
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

	private HttpUtils httpUtils = null;
	private static final int UPLOAD_USER_ICON = 1;// 上传用户头像
	private static final int DOWNLOAD_CS_ICON = 2;// 下载客户头像
	private static final int UPDATE_CS_INFO = 7;// 更新用户信息
	private static final int QUERY_CS_INFO = 3;//

	private void initHttpType(final int currentDataType) {

		try {
			JSONObject params = new JSONObject();
			RequestParams reqParams = new RequestParams();
			switch (currentDataType) {

			case UPLOAD_USER_ICON:
				// 上传头像
				if (!CommonUtil.isNotEmptyfile(Constant.HEAD + "/"
						+ uploadFileName)) {
					CommonUtil.showToast("请选择要上传的头像文件", getActivity());
					return;
				}
				httpUtils = new HttpUtils(getActivity(),
						RequestDefine.MARKET_RQ_UPLOAD_PHOTO, RequestType.POST,
						null);
				reqParams.put("file", CommonUtil.getFile(Constant.HEAD + "/"
						+ uploadFileName));
				httpUtils.setRequestParms(reqParams);
				httpUtils.setUploadFile(true);
				httpUtils.setCustomerId(csOrder.getCustomerId());
				httpUtils.setShowDiloag(true);
				break;
			case DOWNLOAD_CS_ICON:
				// 下载头像
				httpUtils = new HttpUtils(getActivity(),
						RequestDefine.MARKET_RQ_DOWNLOAD_PHOTO,
						RequestType.GET, null);
				httpUtils.setShowDiloag(false);
				httpUtils.setCustomerId(csOrder.getCustomerId());
				httpUtils.setDownLoadFile(true);
				break;
			case UPDATE_CS_INFO:
				JSONObject json = null;
				try {
					if (csOrder.getCustomerId() < 0) {
						CommonUtil.showToast("该客户不存在", getActivity());
						return;
					}
					temp.setId(csOrder.getCustomerId());
					json = new JSONObject();
					json.put("cellPhone1", temp.getCellPhone1());
					json.put("id", temp.getId());
					Log.i(TAG, "更新后的用户json数据为" + json.toString());
				} catch (Exception e) {
				}
				httpUtils = new HttpUtils(getActivity(),
						RequestDefine.RQ_CUSTOMER_UPDATE, RequestType.PUT, json);
				httpUtils.setShowDiloag(false);
				httpUtils.setCustomerId(csOrder.getCustomerId());
				break;
			case QUERY_CS_INFO:
				httpUtils = new HttpUtils(getActivity(),
						RequestDefine.RQ_CUSTOMER_GET_SINGLE, RequestType.GET,
						null);
				httpUtils.setShowDiloag(false);
				httpUtils.setCustomerId(csOrder.getCustomerId());
				break;
			default:
				break;
			}

			httpUtils.setSuccessListener(new SuccessReslut() {

				@Override
				public void getResluts(JSONObject response) {
					if (TextUtils.isEmpty(response.toString())) {
						return;
					}
					Gson gson = new Gson();
					switch (currentDataType) {
					case UPDATE_CS_INFO:
						if (response != null) {
							cs = gson.fromJson(response.toString(),
									new TypeToken<Customer>() {
									}.getType());
							csOrder.setCustomer(cs);
							if (cs.getPhotoId() > 0) {
								initHttpType(DOWNLOAD_CS_ICON);
							}
							isSave(false);
							CommonUtil.showToast("修改用户信息成功", getActivity());
							final FragmentDetail details = (FragmentDetail) getFragmentManager()
									.findFragmentById(R.id.details);
							if (details instanceof FragmentBindSecond) {
								CommonDialog internetSett = new CommonDialog(
										getActivity());
								internetSett.setTitle(R.string.alert);
								internetSett.setMessage("您确定与理财师"
										+ csOrder.getSalesId() + "进行绑定!");
								internetSett.setPositiveButton(
										new BtnClickedListener() {

											public void onBtnClicked() {
												((FragmentBindSecond) details)
														.initHttpUtils(FragmentBindSecond.UPLOAD_BIND_PROTECAL_FILE);
											}
										}, R.string.btn_ok);
								internetSett.setCancleButton(null,
										R.string.btn_cancle);
								internetSett.showDialog();
							}
						}
						break;
					case UPLOAD_USER_ICON:
						String states = response.optString("status");
						if (states.equals(Request.RESLUT_OK)) {
							CommonUtil.showToast("上传头像成功", getActivity());
							isUploadHead = true;
						}
						break;
					case QUERY_CS_INFO:
						try {
							cs = gson.fromJson(response.toString(),
									new TypeToken<Customer>() {
									}.getType());
							if (cs.getPhotoId() > 0) {
								System.out.println("开始下载客户头像：文件id="
										+ cs.getPhotoId());
								initHttpType(DOWNLOAD_CS_ICON);
							}
							isSave(false);
							csOrder.setCustomer(cs);
						} catch (JsonSyntaxException e) {
							e.printStackTrace();
						}
						break;
					default:
						break;
					}
				}
			});
			httpUtils.setFaliureResult(new FaliureResult() {

				@Override
				public void getResluts(String msg) {

					if (currentDataType == UPDATE_CS_INFO) {
						if (csOrder != null) {
							// 数据不改变
							csOrder.setCustomer(temp);
						}
					}
				}
			});
			httpUtils.setFileSuccessListener(new FileSuccessReslut() {

				@Override
				public void getResluts(int statesCode, byte[] binaryData) {
					if (statesCode == 200 && binaryData != null) {
						switch (currentDataType) {
						case DOWNLOAD_CS_ICON:
							// 下载 头像,显示头像
							System.out.println("下载客户头像成功");
							if (binaryData != null && binaryData.length > 0) {
								CommonUtil.storeFile(binaryData, Constant.HEAD,
										downLoadFileName);
								Bitmap bitMap1 = BitmapManager
										.decodeSampledBitmapFromFile(
												Constant.HEAD + "/"
														+ downLoadFileName,
												Constant.COMMOM_WIDTH,
												Constant.COMMOM_HEIGHT);
								icon.setImageBitmap(bitMap1);
								isUploadHead = true;
							}
							break;
						default:
							break;
						}
					}
				}
			});
			httpUtils.executeRequest();

		} catch (Exception e) {
		}
	}

	private boolean cannotEdit = false;

	private void queryMarketStatues() {

		JSONObject params = new JSONObject();
		HttpUtils httpUtils = null;
		// 查询营销记录 ,获取营销状态
		try {
			params.put("id", csOrder.getId());
			params.put("customerId", csOrder.getCustomerId());
			httpUtils = new HttpUtils(getActivity(),
					RequestDefine.MARKET_RQ_QUERY_CSINFO, RequestType.POST,
					params);
			httpUtils.setShowDiloag(false);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		httpUtils.setSuccessListener(new SuccessReslut() {

			@Override
			public void getResluts(JSONObject response) {
				if (Request.RESLUT_OK.equals(response.optString("status"))) {
					try {
						List<MarketStateReslut> market = new Gson().fromJson(
								response.getJSONArray("list").toString(),
								new TypeToken<List<MarketStateReslut>>() {
								}.getType());
						if (market != null) {
							marketStatues = market.get(0)
									.getMarketingStatusId();
							if (marketStatues - 1 > ActCustomerMarketProgress.F_BIND) {
								cannotEdit = true;
							}
							isCanEdit(!isCanEditBindProtecol());
						}
					} catch (JsonSyntaxException e) {
						e.printStackTrace();
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		});
		httpUtils.executeRequest();

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		temp = null;
		cs = null;
		currentShowView = F_FIRST_BIND;
	}

	private File albumTakePhotoFile;
	private String uploadFileName = "";
	private String downLoadFileName = "";

}
