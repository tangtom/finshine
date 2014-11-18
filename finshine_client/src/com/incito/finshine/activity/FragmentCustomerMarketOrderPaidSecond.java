package com.incito.finshine.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.custom.view.CommonDialog;
import com.custom.view.CommonDialog.BtnClickedListener;
import com.custom.view.DlgCommFilter;
import com.custom.view.DlgCommFilter.RefreshFilterListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.incito.finshine.R;
import com.incito.finshine.common.Constant;
import com.incito.finshine.entity.MarketCsOrder;
import com.incito.finshine.entity.OrderInfoItem;
import com.incito.finshine.manager.BitmapManager;
import com.incito.finshine.network.HttpUtils;
import com.incito.finshine.network.HttpUtils.FileSuccessReslut;
import com.incito.finshine.network.HttpUtils.SuccessReslut;
import com.incito.finshine.network.LogCito;
import com.incito.finshine.network.Request;
import com.incito.finshine.network.Request.RequestType;
import com.incito.finshine.network.RequestDefine;
import com.incito.utility.CommonUtil;
import com.incito.utility.FileUtils;
import com.incito.utility.MoneyFormat;
import com.incito.wisdomsdk.net.http.RequestParams;
import com.incito.wisdomsdk.utils.BitmapUtils;

/**
 * 
 * <dl>
 * <dt>FragmentCustomerMarketOrderPaidSecond.java</dt>
 * <dd>Description:客户营销 订单管理 第二步 里面的部分界面View切换</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 昆山灵动信息技术有限公司</dd>
 * <dd>CreateDate: 2014年6月12日 上午9:32:46</dd>
 * </dl>
 *    
 * @author LiNing
 */    
public class FragmentCustomerMarketOrderPaidSecond extends FragmentDetail {

	private final String TAG = FragmentCustomerMarketOrderPaidSecond.class
			.getSimpleName();

	private LinearLayout mainLayout;
	private LinearLayout.LayoutParams params;
	private View view;
	private ImageView orderImage;
	private MarketCsOrder order;
	private Handler handler = new Handler();
	private StringBuffer pictureMiddleName = new StringBuffer();
	public static FragmentCustomerMarketOrderPaidSecond newInstance(int id) {

		FragmentCustomerMarketOrderPaidSecond f = new FragmentCustomerMarketOrderPaidSecond();
		Bundle args = new Bundle();
		args.putInt(FIELD_ID, id);
		f.setArguments(args);
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (container == null) {
			return null;
		}

		view = inflater.inflate(R.layout.frag_customer_market_orderpaid_second,
				container, false);

		ActCustomerMarketProgress activity = (ActCustomerMarketProgress) getActivity();
		order = activity.getMarketCs();
		init();
		queryOrderInfo();
		
		pictureMiddleName.append(order.getCustomerId() + "-");
		pictureMiddleName.append(order.getSalesOrderId() + "-8.jpg");
		
		if (CommonUtil.isNotEmptyfile(Constant.ORDER_PAYMENT
				+ Constant.DOWN_ORDER_PAYMENT_NAME + pictureMiddleName)) {
			// 优先显示下载过的头像
			orderImage
					.setImageBitmap(BitmapManager.decodeSampledBitmapFromFile(
							Constant.ORDER_PAYMENT
									+ Constant.DOWN_ORDER_PAYMENT_NAME
									+ pictureMiddleName,
							Constant.USER_ICON_WIDTH, Constant.USER_ICON_HEIGHT));
		} else if (CommonUtil.isNotEmptyfile(Constant.ORDER_PAYMENT
				+ Constant.UP_ORDER_PAYMENT_NAME + pictureMiddleName)) {

			LogCito.d("onstart path=" + Constant.ORDER_PAYMENT
					+ Constant.UP_ORDER_PAYMENT_NAME + pictureMiddleName);
			// 否则显示上次选择的照片
			Bitmap temp = BitmapManager.decodeSampledBitmapFromFile(
					Constant.ORDER_PAYMENT + Constant.UP_ORDER_PAYMENT_NAME
							+ pictureMiddleName, Constant.USER_ICON_WIDTH,
					Constant.USER_ICON_HEIGHT);
			orderImage.setImageBitmap(temp);

			LogCito.d("orderImage.getid = " + orderImage.getId()
					+ " bitmap=getWidth= " + temp.getWidth() + " getHeight="
					+ temp.getHeight());
		}

		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	// 判断该界面是可以编辑还是只是显示界面 true 是可以编辑，否则不可以编辑
	public boolean CanEdit() {
		LogCito.d("可以编辑协议FragmentBindFirst"
				+ ((ActCustomerMarketProgress) getActivity())
						.canEdit(ActCustomerMarketProgress.F_ORDER_PAYMENT2));
		return ((ActCustomerMarketProgress) getActivity())
				.canEdit(ActCustomerMarketProgress.F_BIND);
	}

	@Override
	public void onResume() {
		// if (!CanEdit()) {
		// orderImage.setEnabled(false);
		// fileUpload.setEnabled(false);
		// btnCommitPayment.setEnabled(false);
		// }
		super.onResume();
	}

	@Override
	public void onStart() {

		super.onStart();
	}

	Button fileUpload;
	Button btnCommitPayment;
	ImageView transferImage;
	Button btnOrderPaymentNext2;

	private void init() {

		params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		params.gravity = Gravity.CENTER;
		mainLayout = (LinearLayout) view.findViewById(R.id.mainLayout);
		final View view1 = (View) getActivity().getLayoutInflater().inflate(
				R.layout.frag_customer_market_orderpaid_second_1, null);
		final View view2 = (View) getActivity().getLayoutInflater().inflate(
				R.layout.frag_customer_market_orderpaid_second_2, null);
		mainLayout.addView(view1, params);

		// 转账支付
		ImageView transferImage = (ImageView) view1
				.findViewById(R.id.transferImage);
		transferImage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				mainLayout.removeAllViews();
				mainLayout.addView(view2, params);
			}
		});

		// 第一个里面的上一步
		Button btnOrderPaymentPre1 = (Button) view1
				.findViewById(R.id.btnOrderPaymentPre1);
		btnOrderPaymentPre1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				((ActCustomerMarketProgress) getActivity()).showDetails(
						ActCustomerMarketProgress.F_SIGN_FIFTH, 4);
			}
		});

		// 第二个里面的上一步
		Button btnOrderPaymentPre2 = (Button) view2
				.findViewById(R.id.btnOrderPaymentPre2);
		btnOrderPaymentPre2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mainLayout.removeAllViews();
				mainLayout.addView(view1, params);
			}
		});

		// 第二个界面里面的下一步
		// btnOrderPaymentNext2 = (Button) view2
		// .findViewById(R.id.btnOrderPaymentNext2);
		// btnOrderPaymentNext2.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		//
		// if (!CanEdit()) {
		// ActCustomerMarketProgress.marketStates =
		// ActCustomerMarketProgress.F_TRANSACTION;
		// ActCustomerMarketProgress.currentView =
		// ActCustomerMarketProgress.F_TRANSACTION;
		// ((ActCustomerMarketProgress) getActivity()).showDetails(
		// ActCustomerMarketProgress.F_TRANSACTION, 0);
		// return;
		// }
		// if (!isUpload) {
		// CommonUtil.showToast("请先选择订单支付凭证文件", getActivity());
		// return;
		// }
		// CommonDialog internetSett = new CommonDialog(getActivity());
		// internetSett.setTitle(R.string.alert);
		// internetSett.setMessage("您确定提交支付凭证并购买" + order.getProdName()
		// + "?");
		// internetSett.setPositiveButton(new BtnClickedListener() {
		//
		// public void onBtnClicked() {
		// upDownLoadOrderFile(UPLOAD_ORDER_ICON);
		// }
		// }, R.string.btn_ok);
		// internetSett.setCancleButton(null, R.string.btn_cancle);
		// internetSett.showDialog();
		// }
		// });

		// 提交支付凭证并购买
		btnCommitPayment = (Button) view2.findViewById(R.id.btnCommitPayment);
		btnCommitPayment.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!isUpload) {
					CommonUtil.showToast("请先选择订单支付凭证文件", getActivity());
					return;
				}
				CommonDialog internetSett = new CommonDialog(getActivity());
				internetSett.setTitle(R.string.alert);
				internetSett.setMessage("您确定提交支付凭证并购买" + order.getProdName()
						+ "?");
				internetSett.setPositiveButton(new BtnClickedListener() {

					public void onBtnClicked() {
						upDownLoadOrderFile(UPLOAD_ORDER_ICON);
					}
				}, R.string.btn_ok);
				internetSett.setCancleButton(null, R.string.btn_cancle);
				internetSett.showDialog();

			}
		});

		orderImage = (ImageView) view2.findViewById(R.id.ivPayment);
		orderImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});
		fileUpload = (Button) view2.findViewById(R.id.btnUpload);
		fileUpload.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DlgCommFilter dlgCommFilter = new DlgCommFilter(getActivity(),
						R.array.select_icon, R.string.ablum_selec, "拍照", true,
						0);
				dlgCommFilter.setListener(new RefreshFilterListener() {

					@Override
					public void doRefresh(String reslut, int position,
							boolean b, int title) {
						if (position == 0) {
							// 拍照
							albumTakePhotoFile = CommonUtil.getFile(
									Constant.ORDER_PAYMENT,
									Constant.UP_ORDER_PAYMENT_NAME
											+ pictureMiddleName);
							Intent intent = new Intent(
									"android.media.action.IMAGE_CAPTURE");
							intent.putExtra(MediaStore.EXTRA_OUTPUT,
									Uri.fromFile(albumTakePhotoFile));
							startActivityForResult(intent, REQUEST_TAKE_PHOTO);
						} else {
							// 从相册选择
							Intent intent = new Intent(
									Intent.ACTION_GET_CONTENT);
							intent.setType("image/*");
							startActivityForResult(intent,
									REQUEST_ALBUM_PHOTO_RESLUT);
						}
					}
				});
				dlgCommFilter.setHiddenInput(true);
				dlgCommFilter.showDialog();
			}
		});
	}

	private static final int UPLOAD_ORDER_ICON = 1;
	private static final int DOWNLOAD_ORDER_ICON = 2;
	private boolean isUpload = false;

	// 上传订单支付凭证
	private void upDownLoadOrderFile(final int type) {

		JSONObject params = new JSONObject();
		RequestParams reqParams = new RequestParams();
		HttpUtils httpUtils = null;
		switch (type) {
		case UPLOAD_ORDER_ICON:
			if (!FileUtils.isFileExist(Constant.ORDER_PAYMENT
					+ Constant.UP_ORDER_PAYMENT_NAME + pictureMiddleName)) {
				CommonUtil.showToast("请重新选择订单支付文件", getActivity());
				return;
			}
			// 上传订单支付凭证扫描附件
			httpUtils = new HttpUtils(getActivity(),
					RequestDefine.MARKET_RQ_ORDER_PAY_SCANNER,
					RequestType.POST, null);
			try {
				reqParams.put("paymentDocumentFile", CommonUtil.getFile(
						Constant.ORDER_PAYMENT, Constant.UP_ORDER_PAYMENT_NAME
								+ pictureMiddleName));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			reqParams.put("salesOrderId", order.getSalesOrderId() + "");
			httpUtils.setRequestParms(reqParams);
			httpUtils.setUploadFile(true);
			break;
		case DOWNLOAD_ORDER_ICON:
			// 下载支付订单凭证扫描附件
			if (orderPaymentId <= 0) {
				return;
			}
			LogCito.d("下载订单凭证orderPaymentId=" + orderPaymentId);

			try {
				params.put("attachmentId", orderPaymentId);// 订单支付凭证的id，调一次查询接口
			} catch (JSONException e) {
				e.printStackTrace();
			}
			httpUtils = new HttpUtils(getActivity(),
					RequestDefine.MARKET_RQ_DOWNLOAD_FILE, RequestType.POST,
					params);
			httpUtils.setDownLoadFile(true);
			break;
		default:
			break;
		}

		httpUtils.setSuccessListener(new SuccessReslut() {

			@Override
			public void getResluts(JSONObject response) {
				if (Request.RESLUT_OK.equals(response.optString("status"))) {
					CommonUtil.showToast("上传订单支付凭证成功", getActivity());
					submitOrderPaid();
				}
			}
		});

		httpUtils.setFileSuccessListener(new FileSuccessReslut() {

			@Override
			public void getResluts(int responeCode, byte[] binaryData) {
				if (responeCode == 200 && binaryData != null && binaryData.length > 0) {
					switch (type) {
					case DOWNLOAD_ORDER_ICON:
						CommonUtil.storeFile(
								binaryData,
								Constant.ORDER_PAYMENT,
								Constant.DOWN_ORDER_PAYMENT_NAME
										+ order.getCustomerId());
						Bitmap bitMap1 = BitmapManager
								.decodeSampledBitmapFromFile(
										Constant.ORDER_PAYMENT
												+ Constant.DOWN_ORDER_PAYMENT_NAME
												+ order.getCustomerId(),
										Constant.COMMOM_WIDTH,
										Constant.COMMOM_HEIGHT);
						isUpload = true;
						orderImage.setImageBitmap(bitMap1);
						break;
					default:
						break;
					}
				}
			}
		});
		httpUtils.executeRequest();
	}

	// 订单支付
	private void submitOrderPaid() {

		JSONObject params = new JSONObject();
		HttpUtils httpUtils = new HttpUtils(getActivity(),
				RequestDefine.MARKET_RQ_ORDER_PAY_SCANNER, RequestType.POST,
				null);
		try {
			params.put("salesOrderId", order.getSalesOrderId() + "");
			params.put("recordId", order.getId());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		httpUtils = new HttpUtils(getActivity(),
				RequestDefine.MARKET_RQ_ORDER_PAY, RequestType.POST, params);
		httpUtils.setSuccessListener(new SuccessReslut() {

			@Override
			public void getResluts(JSONObject response) {
				if (Request.RESLUT_OK.equals(response.optString("status"))) {
					CommonUtil.showToast("订单支付成功", getActivity());
					// 跳转到 交易结果界面
					((ActCustomerMarketProgress) getActivity()).showDetails(
							ActCustomerMarketProgress.F_TRANSACTION, 0);
				} else {
					CommonUtil.showToast(response.optString("msg"),
							getActivity());
				}
			}
		});
		httpUtils.executeRequest();
	}

	private long orderPaymentId = 0;

	// 查询订单支付凭证
	private void queryOrderPayment() {

		JSONObject params = new JSONObject();
		try {
			params.put("salesOrderId", order.getSalesOrderId());

		} catch (JSONException e) {
			e.printStackTrace();
			return;
		}

		HttpUtils httpUtils = new HttpUtils(getActivity(),
				RequestDefine.MARKET_RQ_QUERY_ORDER_PAYMENT, RequestType.POST,
				params);
		httpUtils.setSuccessListener(new SuccessReslut() {

			@Override
			public void getResluts(JSONObject response) {

				if (Request.RESLUT_OK.equals(response.optString("status"))) {

					try {
						orderPaymentId = response.getJSONObject("item")
								.optLong("paymentDocmentFileId");
						if (orderPaymentId > 0) {
							LogCito.d("下载订单支付凭证" + orderPaymentId);
							upDownLoadOrderFile(DOWNLOAD_ORDER_ICON);
						}

					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		});
		httpUtils.executeRequest();
	}

	List<OrderInfoItem> orderList;

	private void queryOrderInfo() {

		/**
		 * 根据订单id 和客户id 和理财师 id 查询该订单信息
		 */
		JSONObject params = new JSONObject();
		try {
			params.put("salesId", order.getSalesId());
			params.put("customerId", order.getCustomerId());
			params.put("salesOrderId", order.getSalesOrderId());

		} catch (JSONException e) {

			e.printStackTrace();
			return;
		}

		HttpUtils httpUtils = new HttpUtils(getActivity(),
				RequestDefine.MARKET_RQ_QUERY_ORDER_LIST, RequestType.POST,
				params);
		httpUtils.setSuccessListener(new SuccessReslut() {

			@Override
			public void getResluts(JSONObject response) {

				if (Request.RESLUT_OK.equals(response.optString("status"))) {
					Gson gson = new Gson();
					orderList = gson.fromJson(response.optJSONArray("list")
							.toString(), new TypeToken<List<OrderInfoItem>>() {
					}.getType());
					if (order != null) {
						order.setOrderlists(orderList);
					}
					refreshData();
					queryOrderPayment();
				}
			}
		});
		httpUtils.executeRequest();
	}

	private void refreshData() {

		if (orderList != null && orderList.size() > 0) {
			OrderInfoItem order = orderList.get(0);
			// 订单编号
			((TextView) view.findViewById(R.id.tv1)).setText(order
					.getSalesOrderNumber());
			// 生成时间
			((TextView) view.findViewById(R.id.tv2)).setText(order
					.getCreatedStr());
			// 订单总额
			((TextView) view.findViewById(R.id.tv3)).setText(String
					.valueOf(order.getChangeAmount()));
			// 订单状态
			((TextView) view.findViewById(R.id.tv4)).setText(order
					.getOrderStatusName());
			// 客户姓名
			((TextView) view.findViewById(R.id.tv5)).setText(order
					.getCustomerName());
			// 产品名称
			((TextView) view.findViewById(R.id.tv6)).setText(order
					.getProdName());
			// 订购份额
			((TextView) view.findViewById(R.id.tv7)).setText(String
					.valueOf(order.getChangeAmount()));
			// 认购金额
			((TextView) view.findViewById(R.id.tv8)).setText(String
					.valueOf(order.getChangeAmount()));

			((TextView) view.findViewById(R.id.tv9)).setText(String
					.valueOf(order.getProductQuantity()));

			long money = order.getChangeAmount();
			BigDecimal bd = new BigDecimal(money);
			String moneys = "";
			if (money > 0) {
				moneys = "人民币" + new MoneyFormat().format(bd.toPlainString());
			} else {
				moneys = "人民币 0 万元";
			}
			((TextView) view.findViewById(R.id.tv9)).setText(moneys);
		}
	}

	private static final int REQUEST_TAKE_PHOTO = 1;
	private static final int REQUEST_ALBUM_PHOTO_RESLUT = 2;
	private static final int PHOTO_PICKED_WITH_DATA = 3;
	public static final String ACTION_CUSTOMER_ID = "action_customer_id";
	private File albumTakePhotoFile;

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == getActivity().RESULT_OK) {
			try {
				Bitmap selectImage = null;
				if (requestCode == REQUEST_ALBUM_PHOTO_RESLUT) {
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
						doCropPhoto(new File(file));

					} else {
						Uri url = data.getData();
						try {

							Intent intent = new Intent();
							intent.setDataAndType(url, "image/*");
							final Bitmap selectImage1 = CommonUtil.saveLocalFile(intent, selectImage);

							CommonUtil.storeFile(BitmapUtils.bmpToByteArray(selectImage1, false), Constant.ORDER_PAYMENT, Constant.UP_ORDER_PAYMENT_NAME
									+ pictureMiddleName);
							orderImage.setImageBitmap(selectImage1);
							isUpload = true;
						} catch (Exception e) {
						}
					}
				} else if (requestCode == REQUEST_TAKE_PHOTO) {
					// doCropPhoto(albumTakePhotoFile);
					Uri uri = Uri.parse(albumTakePhotoFile.getPath());
					Intent intent = new Intent();
					intent.setDataAndType(uri, "image/*");
					final Bitmap selectImage1 = CommonUtil.saveLocalFile(intent, selectImage);

					CommonUtil.storeFile(BitmapUtils.bmpToByteArray(selectImage1, false), Constant.ORDER_PAYMENT, Constant.UP_ORDER_PAYMENT_NAME
							+ pictureMiddleName);
					orderImage.setImageBitmap(selectImage1);
					isUpload = true;

				} else if (requestCode == PHOTO_PICKED_WITH_DATA) {

					final Bitmap selectImage1 = CommonUtil.saveLocalFile(data, selectImage);

					CommonUtil.storeFile(BitmapUtils.bmpToByteArray(selectImage1, false), Constant.ORDER_PAYMENT, Constant.UP_ORDER_PAYMENT_NAME
							+ pictureMiddleName);
					orderImage.setImageBitmap(selectImage1);
					isUpload = true;

				}
			} catch (Exception e) {
				e.getLocalizedMessage();
			}
		}
	}

	protected void doCropPhoto(File f) {
		try {
			Bitmap selectImage = null;
			Uri uri = Uri.parse(f.getPath());
			Intent intent = new Intent();
			intent.setDataAndType(uri, "image/*");
			final Bitmap selectImage1 = CommonUtil.saveLocalFile(intent, selectImage);

			CommonUtil.storeFile(BitmapUtils.bmpToByteArray(selectImage1, false), Constant.ORDER_PAYMENT, Constant.UP_ORDER_PAYMENT_NAME + pictureMiddleName);
			orderImage.setImageBitmap(selectImage1);
			isUpload = true;

			// startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
		} catch (Exception e) {
			Toast.makeText(getActivity(), "裁剪图片异常", Toast.LENGTH_SHORT).show();
		}
	}

}
