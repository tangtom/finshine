package com.incito.finshine.activity;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.custom.view.DlgCommFilter;
import com.custom.view.PopDatePicker;
import com.custom.view.DlgCommFilter.RefreshFilterListener;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.incito.finshine.R;
import com.incito.finshine.common.Constant;
import com.incito.finshine.entity.Customer;
import com.incito.finshine.entity.MarketCsOrder;
import com.incito.finshine.manager.BitmapManager;
import com.incito.finshine.network.HttpUtils;
import com.incito.finshine.network.HttpUtils.FileSuccessReslut;
import com.incito.finshine.network.HttpUtils.SuccessReslut;
import com.incito.finshine.network.LogCito;
import com.incito.finshine.network.Request;
import com.incito.finshine.network.Request.RequestType;
import com.incito.finshine.network.RequestDefine;
import com.incito.utility.CommonUtil;
import com.incito.utility.DateUtil;
import com.incito.wisdomsdk.event.EventBus;
import com.incito.wisdomsdk.net.http.RequestParams;
import com.incito.wisdomsdk.utils.BitmapUtils;
/**
 * 
 * <dl>
 * <dt>FragmentBindFirst.java</dt>
 * <dd>Description:客户营销  绑定第一步 Fragment</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 昆山灵动信息技术有限公司</dd>
 * <dd>CreateDate: 2014年6月16日 下午5:00:59</dd>
 * </dl>
 * 
 * @author LiNing
 */
public class FragmentBindFirst extends FragmentDetail implements OnClickListener , OnItemSelectedListener{
	
	View view;
	public  FragmentBindFirst instance;
	ActBind actBind;
	
	private int idType = 0;
	private Spinner spinnerIdenfication;
	private Customer cs = null;
	EditText idnCode;
	EditText effectiveDate;
	private PopDatePicker popDatePicker;
	// 判断该界面是可以编辑还是只是显示界面 true 是可以编辑，否则不可以编辑
	public boolean CanEdit(){
		LogCito.d("可以编辑协议FragmentBindFirst"+((ActCustomerMarketProgress)getActivity()).canEdit(ActCustomerMarketProgress.F_BIND));
		return ((ActCustomerMarketProgress)getActivity()).canEdit(ActCustomerMarketProgress.F_BIND);
	}
	
	public static FragmentBindFirst newInstance(int id) {

		FragmentBindFirst f = new FragmentBindFirst();
		Bundle args = new Bundle();
		args.putInt(FIELD_ID, id);
		f.setArguments(args);

		return f;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (container == null) {
			return null;
		}
		view = inflater.inflate(R.layout.frag_customer_market_bind_first11, container, false);
		instance = this;
		actBind =ActBind.instanceA;
		order = ((ActCustomerMarketProgress)getActivity()).getMarketCs();
		LogCito.d("bind1 客户id="+order.getCustomerId()+"产品id:"+order.getProdId()+" 理财师id：="+order);
		EventBus.getDefault().registerSticky(this);
		initUI();
		initHttpType(QUERY_CS_INFO);
		return view;
	}
	
 
	@Override
	public void onStart() {
		super.onStart();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if (!CanEdit()) {
			cannotEdit();
		}
	}

	private void cannotEdit(){
		
		spinnerIdenfication.setEnabled(false);
		idnCode.setEnabled(false);
		effectiveDate.setEnabled(false);
 		ivOpposive.setEnabled(false);
		ivDefensive.setEnabled(false);
		uploadOpp.setEnabled(false);;
		uploadDefen.setEnabled(false);
	}
	
	private Button uploadOpp;
	private Button uploadDefen;
	private ImageView ivOpposive;
	private ImageView ivDefensive;
	private static final int  QUERY_CS_INFO = 4;
	
	private static final int REQUEST_TAKE_PHOTO = 1;
	private static final int REQUEST_ALBUM_PHOTO_RESLUT = 2;
	private static final int PHOTO_PICKED_WITH_DATA = 3;
	private File albumTakePhotoFile; 
	/**证件照正面上传文件名**/
	private String cer_posi_up;
	/**证件照面下载文件名**/
	private String cer_posi_down;
	private String cer_defen_up;
	private String cer_defen_down;
	
	private void initUI() {
		
//		cer_posi_up = Constant.CER_POSI_UP+order.getCustomerId()+".jpg";
//		cer_posi_down= Constant.CER_POSI_DOWN+order.getCustomerId()+".jpg";
//	    cer_defen_up =  Constant.CER_DEFEN_UP+order.getCustomerId()+".jpg";
//	    cer_defen_down = Constant.CER_DEFEN_DOWN+order.getCustomerId()+".jpg";
		
		idnCode = (EditText)view.findViewById(R.id.idenficationPhone);
		effectiveDate = (EditText)view.findViewById(R.id.btnDate);
		spinnerIdenfication = (Spinner)view.findViewById(R.id.spinnerIdenfication);
		ivOpposive = (ImageView)view.findViewById(R.id.ivOposive);
		ivDefensive = (ImageView)view.findViewById(R.id.ivdefensive);
		uploadOpp = (Button)view.findViewById(R.id.btnPositive);
	    uploadDefen = (Button)view.findViewById(R.id.btndefensive);
		
		effectiveDate.setOnClickListener(this);
		ivOpposive.setOnClickListener(this);
		ivDefensive.setOnClickListener(this);
	    uploadOpp.setOnClickListener(this);
	    uploadDefen.setOnClickListener(this);
	    initSpinner(spinnerIdenfication,
				getResources().getStringArray(R.array.idenfication_type), false);
		
		if (CommonUtil.isNotEmptyfile(Constant.CERTIFICATE+cer_posi_down)) {
	         // 优先显示下载过的头像
			  uploadOpposive = true;
			  ivOpposive.setImageBitmap(BitmapManager.decodeSampledBitmapFromFile(Constant.CERTIFICATE+cer_posi_down, Constant.USER_ICON_WIDTH, Constant.USER_ICON_HEIGHT));
			}else if (CommonUtil.isNotEmptyfile(Constant.CERTIFICATE+cer_posi_up )) {
			    // 否则显示上次选择的照片
			  uploadOpposive = true;
			  ivOpposive.setImageBitmap(BitmapManager.decodeSampledBitmapFromFile(Constant.CERTIFICATE+cer_posi_up, Constant.USER_ICON_WIDTH, Constant.USER_ICON_HEIGHT));
		 }
		 if (CommonUtil.isNotEmptyfile(Constant.CERTIFICATE+cer_defen_down)) {
	        	// 优先显示下载过的头像
		         uploadDefensive = true;
			     ivDefensive.setImageBitmap(BitmapManager.decodeSampledBitmapFromFile(Constant.CERTIFICATE+cer_defen_down, Constant.USER_ICON_WIDTH, Constant.USER_ICON_HEIGHT));
			
		 }else if (CommonUtil.isNotEmptyfile(Constant.CERTIFICATE+cer_defen_up)) {
			    // 否则显示上次选择的照片
			    uploadDefensive = true;
				ivDefensive.setImageBitmap(BitmapManager.decodeSampledBitmapFromFile(Constant.CERTIFICATE + cer_defen_up, Constant.USER_ICON_WIDTH, Constant.USER_ICON_HEIGHT));
		}
	}
	
	// Spinner设置
		private void initSpinner(Spinner sp, String[] dataList,
				boolean isResaveDefaultValue) {

			if (isResaveDefaultValue) {
				sp.setSelection(0, true);
			} else {
				ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(),
						R.layout.common_spinner_item, dataList);// android.R.layout.simple_spinner_item
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);//
				sp.setAdapter(adapter);
				sp.setOnItemSelectedListener(this);
			}
		}

	@Override
	public void onClick(final View v) {
		 switch (v.getId()) {
		 case R.id.btnIdenfication:
				DlgCommFilter nation = new DlgCommFilter(getActivity(), R.array.idenfication_type, R.string.sele_idenfication, "", true, 0);
				nation.setHiddenInput(true);
				nation.setListener(new RefreshFilterListener() {
					
					@Override
					public void doRefresh(String reslut, int position,boolean b,int title) {
						((Button)v).setText(reslut);
						 idType = position;
					}
				});
			 nation.showDialog();
			 break;
		 case R.id.btnDate:
			if(popDatePicker == null)
			{
				popDatePicker = new PopDatePicker(this.getActivity(),v,true);
			}
			popDatePicker.showPopWindow();
//			 CommonUtil.showToast("id code", getActivity());
			 break;
		 case R.id.btnPositive:
			 // 上传正面
			 currentDataType = UPLOAD_OPPOSIVE;
			 initHttpType(currentDataType);
			 break;
		 case R.id.btndefensive:
			 // 上传反面
			 currentDataType = UPLOAD_DEFENSIVE;
			 initHttpType(currentDataType);
			 break;
		case R.id.ivOposive:
		case R.id.ivdefensive:
			 if (v.getId() == R.id.ivOposive) {
				 currentDataType = UPLOAD_OPPOSIVE;
			   }else{
				 currentDataType = UPLOAD_DEFENSIVE;
			 }
			 DlgCommFilter dlgCommFilter = new DlgCommFilter(getActivity(), R.array.select_icon, R.string.ablum_selec, "拍照", true, 0);
	         dlgCommFilter.setListener(new RefreshFilterListener() {
				
				@Override
				public void doRefresh(String reslut, int position,boolean b,int title) {
					 if (position == 0) {
						 // 拍照
						 Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
						 if (v.getId() == R.id.ivOposive) {
								// 正面
							    albumTakePhotoFile = CommonUtil.getFile(Constant.CERTIFICATE,cer_posi_up);
							    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(albumTakePhotoFile));
						   }else{
								// 反面
						        albumTakePhotoFile = CommonUtil.getFile(Constant.CERTIFICATE,cer_defen_up);
							    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(albumTakePhotoFile));
						}
						 LogCito.d("id文件："+albumTakePhotoFile.getAbsolutePath());
						 startActivityForResult(intent, REQUEST_TAKE_PHOTO);
					 }else{
						// 从相册选择
						Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
						intent.setType("image/*");
						startActivityForResult(intent, REQUEST_ALBUM_PHOTO_RESLUT);
					}
				}
			  });
	         dlgCommFilter.setHiddenInput(true);
	         dlgCommFilter.showDialog();
			break;

		default:
			break;
		}
	}
	
	public void onEvent(String value)
	{	
		if(value != null)
		{
			effectiveDate.setText(value);
		}
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode ==  getActivity().RESULT_OK) {
			try {
				Bitmap selectImage = null;
				if (requestCode == REQUEST_ALBUM_PHOTO_RESLUT) {
					String file = data.getDataString();
					if (!TextUtils.isEmpty(file) && file.startsWith("file:///")) {
						if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
							file = file.replace("file://","");
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
					}else{
						Uri url  = data.getData();
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
					saveLocalFile(data,selectImage);
				}
			} catch (Exception e) {
				e.getLocalizedMessage();
				LogCito.d("拍照异常"+e.getLocalizedMessage());
			}
		}
	}
  
	
	private void saveLocalFile(Intent data, Bitmap selectImage) {
		Bundle extras = data.getExtras();
		if (extras == null) {
			String file = data.getDataString();
			if (!TextUtils.isEmpty(file) && file.startsWith("file:///")) {
				if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
					file = file.replace("file://","");
					 try {
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
		
		if (currentDataType == UPLOAD_DEFENSIVE) {
			// 显示反面
			ivDefensive.setImageBitmap(selectImage);
			CommonUtil.storeFile(BitmapUtils.bmpToByteArray(selectImage, false),Constant.CERTIFICATE,cer_defen_up);
		}else if (currentDataType == UPLOAD_OPPOSIVE){
			// 显示正面
			ivOpposive.setImageBitmap(selectImage);
			CommonUtil.storeFile(BitmapUtils.bmpToByteArray(selectImage, false),Constant.CERTIFICATE,cer_posi_up);
		}
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
	private static final int UPLOAD_OPPOSIVE  = 1;// 上传正面
	private static final int UPLOAD_DEFENSIVE  = 2;// 上传反面
	private MarketCsOrder order = null;
	private int currentDataType = UPLOAD_OPPOSIVE;//
     
	private void initHttpType(final int currentDataType){
		
		try {
			    RequestParams  reqParams = new RequestParams();
				if (currentDataType == UPLOAD_DEFENSIVE) {
					
					if (!CommonUtil.isNotEmptyfile(Constant.CERTIFICATE+cer_defen_up)) {
						CommonUtil.showToast("请选择证件照反面", getActivity());
						return ;
					}
					// 上传反面
					httpUtils = new HttpUtils(getActivity(), RequestDefine.DOWN_UPLOAD_IDENFICATION_OPPOSIVE, RequestType.POST, null);
					reqParams.put("file", CommonUtil.getFile(Constant.CERTIFICATE,cer_defen_up));
					httpUtils.setRequestParms(reqParams);
					httpUtils.setUploadFile(true);
					httpUtils.setCustomerId(order.getCustomerId());
					
				}else if (currentDataType == UPLOAD_OPPOSIVE){
					// 上传正面
					if (!CommonUtil.isNotEmptyfile(Constant.CERTIFICATE+cer_posi_up)) {
						CommonUtil.showToast("请选择证件照正面", getActivity());
						return ;
					}
					httpUtils = new HttpUtils(getActivity(), RequestDefine.DOWN_UPLOAD_IDENFICATION_POSITIVE, RequestType.POST, null);
					reqParams.put("file", CommonUtil.getFile(Constant.CERTIFICATE,cer_posi_up));
					httpUtils.setRequestParms(reqParams);
					httpUtils.setUploadFile(true);
					httpUtils.setCustomerId(order.getCustomerId());
					
				} else if(currentDataType == QUERY_CS_INFO) {
					
					 httpUtils = new HttpUtils(getActivity(),RequestDefine.RQ_CUSTOMER_GET_SINGLE, RequestType.GET, null);
					 httpUtils.setShowDiloag(false);
					 httpUtils.setCustomerId(order.getCustomerId());
				}
				
			   httpUtils.setSuccessListener(new SuccessReslut() {
				
				@Override
				public void getResluts(JSONObject response) {
					if (TextUtils.isEmpty(response.toString())) {
						return;
					}
						switch (currentDataType) {
						case UPLOAD_DEFENSIVE:
							if (Request.RESLUT_OK.equals(response.optString("status"))){
							   CommonUtil.showToast("上传证件照反面成功", getActivity());
							   uploadDefensive = true;
							}
							break;
						case UPLOAD_OPPOSIVE:
							if (Request.RESLUT_OK.equals(response.optString("status"))){
								CommonUtil.showToast("上传证件照正面成功", getActivity());
								uploadOpposive = true;
							}
							break;
						case QUERY_CS_INFO:
							try {
								cs  = new Gson().fromJson(response.toString(), new TypeToken<Customer>() {}.getType());
								if (cs.getPositivePhotoId() > 0) {
									downLoadIconByOpposive();
								}
								if (cs.getBackPhotoId() > 0) {
									downLoadIconByDefensive();
								}
								isSave(false);
								order.setCustomer(cs);
							} catch (JsonSyntaxException e) {
								e.printStackTrace();
							}  
							break;
						default:
							break;
						}
					}
			});
			httpUtils.executeRequest();
			
		}  catch (Exception e) {
	  }
	}
	
	
	private void downLoadIconByOpposive(){
		
		LogCito.d("开始下载证件照正面 ");
		HttpUtils	httpUtils = new HttpUtils(getActivity(), RequestDefine.DOWN_UPLOAD_IDENFICATION_OPPOSIVE, RequestType.GET, null);
		httpUtils.setShowDiloag(false);
		httpUtils.setCustomerId(order.getCustomerId());
		httpUtils.setDownLoadFile(true);
		httpUtils.setFileSuccessListener(new FileSuccessReslut() {
			
			@Override
			public void getResluts(int responeCode,byte[] binaryData) {
				
				if (responeCode == 200 && binaryData != null) {
						// 下载 正面
				   LogCito.d("下载证件照证面成功");
				   CommonUtil.storeFile(binaryData,Constant.CERTIFICATE,cer_posi_down);
				   Bitmap bitMap1 = BitmapManager.decodeSampledBitmapFromFile(Constant.CERTIFICATE+cer_posi_down, Constant.COMMOM_WIDTH, Constant.COMMOM_HEIGHT);
				   ivDefensive.setImageBitmap(bitMap1);
				}
			}
		});
		httpUtils.executeRequest();
	}
	
   private void downLoadIconByDefensive(){
	   LogCito.d("开始下载证件照饭面 ");
		HttpUtils httpUtils = new HttpUtils(getActivity(), RequestDefine.DOWN_UPLOAD_IDENFICATION_POSITIVE, RequestType.GET, null);
		httpUtils.setShowDiloag(false);
		httpUtils.setCustomerId(order.getCustomerId());
		httpUtils.setDownLoadFile(true);
		httpUtils.setFileSuccessListener(new FileSuccessReslut() {
			
			@Override
			public void getResluts(int responeCode,byte[] binaryData) {
				
				if (responeCode == 200 && binaryData != null) {
					// 下载反面
					 LogCito.d("下载证件照饭面成功");
					 CommonUtil.storeFile(binaryData,Constant.CERTIFICATE,cer_defen_down);
					Bitmap bitMap2 = BitmapManager.decodeSampledBitmapFromFile(Constant.CERTIFICATE+cer_defen_down, Constant.COMMOM_WIDTH, Constant.COMMOM_HEIGHT);
					ivDefensive.setImageBitmap(bitMap2);
				}
			}
		});
		httpUtils.executeRequest();
	}
	
	public void isSave(boolean isEdit){
		
		if (isEdit) {
			actBind.temp.setIDNumber(idnCode.getText().toString());
			actBind.temp.setIDType((idType));
		}else{
			if (cs == null) {
				return;
			}
			//spinnerIdenfication.setSelected(getActivity().getResources().getStringArray(R.array.idenfication_type)[cs.getIDType()]);
			idnCode.setText(cs.getIDNumber());
//			effectiveDate.setText(cs.getIDValid() +"");
		}
	}
	
	private boolean uploadOpposive = false;
	private boolean uploadDefensive = false;
		
	public boolean checkData(){
		
		if (TextUtils.isEmpty(spinnerIdenfication.getSelectedItem().toString())) {
			CommonUtil.showToast("请选择证件类型", getActivity());
			return false;
		}else if (TextUtils.isEmpty(idnCode.getText().toString())) {
			CommonUtil.showToast("请输入证件号码", getActivity());
			return false;
		}else if (!uploadOpposive) {
			CommonUtil.showToast("请先上传证件照正面", getActivity());
			return false;
		}else if (!uploadDefensive) {
			CommonUtil.showToast("请先上传证件照反面", getActivity());
			return false;
		}
		return true;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
}
