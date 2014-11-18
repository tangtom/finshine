package com.custom.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.text.method.ScrollingMovementMethod;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.core.util.AppToast;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.incito.finshine.R;
import com.incito.finshine.activity.adapter.AdapterPropertyShow;
import com.incito.finshine.entity.PropsProperty;
import com.incito.finshine.entity.PropsStatus;
import com.incito.finshine.entity.PropsType;
import com.incito.finshine.entity.PropsWSEntity;
import com.incito.finshine.manager.CoreManager;
import com.incito.finshine.manager.SPManager;
import com.incito.finshine.network.NetworkManager;
import com.incito.finshine.network.Request;
import com.incito.finshine.network.Request.RequestType;
import com.incito.finshine.network.RequestDefine;
import com.incito.utility.DateUtil;
import com.incito.wisdomsdk.net.http.JsonHttpResponseHandler;
import com.incito.wisdomsdk.utils.BitmapUtils;
import com.incito.finshine.network.HttpUtils;
import com.incito.finshine.network.HttpUtils.FaliureResult;
import com.incito.finshine.network.HttpUtils.SuccessReslut;
/**
 * <dl>
 * <dt>ActCustomerMarket.java</dt>
 * <dd>Description:道具</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 昆山灵动信息技术有限公司</dd>
 * <dd>CreateDate:</dd>
 * </dl>
 * 
 * @author liying
 */
public class PopPropertyManagement {
	private static final String TAG = "ActPropertyManagement";
	private SPManager spManager;
	private PullToRefreshGridView PropertyGridV = null;
	private TextView propertyName;
	private TextView commissionPercent;
	private ImageView imgPropertyPicture;
	private TextView propertyType;
	private TextView propertyIntroduction;
	private RelativeLayout propertyBorder;
	private TextView popTxetType;
	private TextView popTxetIntro;
    boolean freshStatus = false;
	private AdapterPropertyShow adPropertyShow = null;
	/* 适配器AdapterPropertyShow.java */
	private List<PropsWSEntity> props = new ArrayList<PropsWSEntity>();;
	private Context context;
	private View clickView;
	private View popView;
	private PopupWindow popWin = null;
	private View dialogView;
	private Window window;
	private CommonWaitDialog dialog = null;
    private TextView warn1;
    private TextView warn2;
    public static  int id=-1;
    public static  long pid=-1; 
    
    private long curentIdOfPropsWSEntity=-1;
	public PopPropertyManagement(Context context, View clickView,Window window) {
		super();
		/*super(context);*/
		this.context = context;
		this.clickView = clickView;
		this.window=window;
		initPopwindow(clickView);
		init();
		initUI();
	}

	
	int getIndexOflist(long propsWSEntityId){
		int i = 0;
		if(props!=null&&props.size()>0){
			
			for (PropsWSEntity p:props) {
				if(p.getId()==propsWSEntityId){
					break;
				}
			   i++;	
			}
		}
		
		return i;
	}
	private void init() {
		PropertyGridV = (PullToRefreshGridView) popView.findViewById(R.id.PropertyGridV);
		PropertyGridV.requestFocus();
		PropertyGridV.setFocusable(true);
		
		adPropertyShow = new AdapterPropertyShow(context, props);
		adPropertyShow.setK(-1);
		PropertyGridV.setMode(Mode.PULL_FROM_START);
		PropertyGridV.setAdapter(adPropertyShow);
		PropertyGridV.setOnRefreshListener(mRefreshListener);
		PropertyGridV.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ImageView iv = (ImageView) view.findViewById(R.id.imgPropertyPicture1);
				if (props != null && props.size() > 0) {
					PropsWSEntity prop = props.get(position);
					adPropertyShow.setK(position);
					adPropertyShow.notifyDataSetChanged();
					curentIdOfPropsWSEntity=prop.getId();
				}
				warn1.setVisibility(View.GONE);
				warn2.setVisibility(View.GONE);
				popTxetType.setVisibility(View.VISIBLE);
				popTxetIntro.setVisibility(View.VISIBLE);
				propertyName.setVisibility(View.VISIBLE);
				commissionPercent.setVisibility(View.VISIBLE);
				propertyType.setVisibility(View.VISIBLE);
				propertyIntroduction.setVisibility(View.VISIBLE);
				popTxetIntro.setVisibility(View.VISIBLE);
				imgPropertyPicture.setVisibility(View.VISIBLE);
				popTxetType.setVisibility(View.VISIBLE);
				popTxetIntro.setVisibility(View.VISIBLE);
				propertyName.setText(props.get(position).getName());
				commissionPercent.setText(""+ props.get(position).getPropertyValue());
				/* 图片处理 */
				if (props.get(position).getPicture() != null) {
					byte[] pic = android.util.Base64.decode(props.get(position).getPicture(), Base64.DEFAULT);
					
					//Bitmap bmap = BitmapUtils.fromByteArray(pic);
					BitmapFactory.Options opts = new BitmapFactory.Options();

					opts.inSampleSize = 4; //这个的值压缩的倍数（2的整数倍），数值越小，压缩率越小，图片越清晰

					Bitmap	bmap = BitmapFactory.decodeByteArray(pic, 0, pic.length, opts);
					
					imgPropertyPicture.setImageBitmap(bmap);
				} else {
					imgPropertyPicture
							.setImageResource(R.drawable.row_property);
				}

				commissionPercent.setText("+"
						+ props.get(position).getPropertyValue() + "%");

				propertyType.setText(props.get(position).getType().getName());
				System.out.println(props.get(position).getMemo());
				propertyIntroduction.setText(props.get(position).getMemo().toString().trim());
				
			}
		});
		sendMessage();
	}

	private void initPopwindow(View clickView) {
		popView = LayoutInflater.from(context).inflate(
				R.layout.pop_property_list, null);
		popWin = new PopupWindow(popView, 825, 515, true);
		popWin.setBackgroundDrawable(new ColorDrawable());
		popWin.setOutsideTouchable(true);
		popWin.setFocusable(true);
		popWin.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss() {
				 WindowManager.LayoutParams lp = window.getAttributes();  
		          lp.alpha = 1f;  
		          window.setAttributes(lp);       
			}
		});
		// popWin.setOutsideTouchable(true);
		// popWin.setFocusable(true);
	}

	private void initUI() {
		propertyName = (TextView) popView.findViewById(R.id.property_name);
		commissionPercent = (TextView) popView.findViewById(R.id.property_percent);
		imgPropertyPicture = (ImageView) popView.findViewById(R.id.img_property_picture);
		propertyType = (TextView) popView.findViewById(R.id.property_type);
		propertyIntroduction = (TextView) popView.findViewById(R.id.property_introduction);
		propertyBorder = (RelativeLayout) popView.findViewById(R.id.propertyBorder);
		popTxetType = (TextView) popView.findViewById(R.id.popTxetType);
		popTxetIntro = (TextView) popView.findViewById(R.id.popTxetIntro);
		warn1 = (TextView) popView.findViewById(R.id.warn1);
		warn2 = (TextView) popView.findViewById(R.id.warn2);
		popTxetType.setVisibility(View.INVISIBLE);
		popTxetIntro.setVisibility(View.INVISIBLE);
	}
    
	private JsonHttpResponseHandler handlerProperty = new JsonHttpResponseHandler() {

		@Override
		public void onSuccess(JSONObject response) {
			Log.i(TAG, response.toString());
			try {
				if (response.getString("status").equals(Request.RESLUT_OK)) {
					JSONArray result = response.getJSONArray("result");
					if (result.length() > 0) {
						props.clear();
						/*
						 * for(int i= 0;i < result.length();i ++){ PropsWSEntity
						 * p = new PropsWSEntity(); props.add(p); }
						 */
						for (int i = 0; i < result.length(); i++) {
							JSONObject json = result.getJSONObject(i);
							PropsWSEntity pwse = new PropsWSEntity();
							pwse.setId(json.getLong("id"));
							Gson gson = new Gson();
							PropsType pt = gson.fromJson(
									json.getJSONObject("type").toString(),
									PropsType.class);
							PropsProperty pp = gson.fromJson(json
									.getJSONObject("property").toString(),
									PropsProperty.class);
							PropsStatus ps = gson.fromJson(
									json.getJSONObject("status").toString(),
									PropsStatus.class);
							pwse.setProperty(pp);
							pwse.setType(pt);
							pwse.setStatus(ps);
							String name = json.getString("name");
							String memo = json.getString("memo");
							String picture = json.getString("picture");
							int totalQty = json.getInt("totalQty");
							String propertyValue = json
									.getString("propertyValue");
							Date dateOfCreate = DateUtil.formatString2Date(
									DateUtil.getDateTimeByFormatAndMs(
											json.getLong("dateOfCreate"),
											DateUtil.FORMAT_YYYY_MM_DD),
									DateUtil.FORMAT_YYYY_MM_DD);
							pwse.setDateOfCreate(dateOfCreate);
							pwse.setName(name);
							pwse.setMemo(memo);
							pwse.setPicture(picture);
							pwse.setTotalQty(totalQty);
							pwse.setPropertyValue(propertyValue);
							Log.i("pwse", pwse.getId()+"");
							if(!props.contains(pwse)){
								Log.i("pwse contains:", pwse.getId()+"");
								props.add(pwse);								
							}
						}
						Log.i(TAG, props.size() + "---");
					}
				}
				PropertyGridV.onRefreshComplete();//数据完成后，调用
				adPropertyShow.notifyDataSetChanged();
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			closeDialog();
			super.onSuccess(response);

		}

		@Override
		public void onFailure(Throwable error, String content) {
			Log.i(TAG, content);
		}

	};

	public void showPopWindow() {
		if (popWin != null) {
			int[] location = new int[2];
			clickView.getLocationOnScreen(location);
			popWin.showAtLocation(clickView, Gravity.CENTER, 0, 0);
		}
	}

	public void closePopWindow() {
		if (popWin != null) {
			if (popWin.isShowing()) {
				popWin.dismiss();
				popWin = null;
			}
		}
	}

	public boolean isShowing() {

		if (popWin != null) {
			if (popWin.isShowing()) {
				return true;
			}
		}
		return false;
	}

	private  void closeDialog(){
		if (dialog != null) {
			dialog.clearAnimation();
			dialog = null;
		}
	}
	
	/* 网络请求 */
	public void sendMessage() {
		/* 将理财师ID传入requset中进行判断 */
		Request request = new Request(
				RequestDefine.PROPERTY_SC_FIRST_GET_BASEINFO, SPManager
						.getInstance().getLongValue(SPManager.ID),
				RequestType.GET, null, handlerProperty);
		CoreManager.getInstance().postRequest(request);
		if(dialog == null){
			/*context 上下文环境*/
			dialog = new CommonWaitDialog(context, "", R.string.load_data);
		}
	}

	/**
	 * @description 下拉刷新请求
	 * @author Andy.fang
	 */
	OnRefreshListener<GridView> mRefreshListener = new OnRefreshListener<GridView>() {
		@Override
		public void onRefresh(PullToRefreshBase<GridView> refreshView) {
			//请求接口

			init();
			AppToast.ShowToast("下拉刷新处理");
			
			adPropertyShow.setK(getIndexOflist(curentIdOfPropsWSEntity));
			
					/*propertyName.setVisibility(View.INVISIBLE);
					commissionPercent.setVisibility(View.INVISIBLE);
					propertyIntroduction.setVisibility(View.INVISIBLE);
					popTxetIntro.setVisibility(View.INVISIBLE);
					imgPropertyPicture.setVisibility(View.INVISIBLE);
					propertyType.setVisibility(View.INVISIBLE);
					popTxetType.setVisibility(View.INVISIBLE);
					popTxetIntro.setVisibility(View.INVISIBLE);
					popTxetType.setVisibility(View.INVISIBLE);
					popTxetIntro.setVisibility(View.INVISIBLE);*/
					
		//pid = adPropertyShow.getP();
		if(curentIdOfPropsWSEntity == -1){
			warn1.setVisibility(View.VISIBLE);
			warn2.setVisibility(View.VISIBLE);
			//init();	
			propertyName.setVisibility(View.INVISIBLE);
			commissionPercent.setVisibility(View.INVISIBLE);
			propertyIntroduction.setVisibility(View.INVISIBLE);
			popTxetIntro.setVisibility(View.INVISIBLE);
			imgPropertyPicture.setVisibility(View.INVISIBLE);
			propertyType.setVisibility(View.INVISIBLE);
			popTxetType.setVisibility(View.INVISIBLE);
			popTxetIntro.setVisibility(View.INVISIBLE);
			popTxetType.setVisibility(View.INVISIBLE);
			popTxetIntro.setVisibility(View.INVISIBLE);
		}	
		else{
			warn1.setVisibility(View.GONE);
			warn2.setVisibility(View.GONE);
			//init();	
		}
		}
	};

}
