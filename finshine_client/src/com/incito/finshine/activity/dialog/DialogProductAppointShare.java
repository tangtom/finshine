package com.incito.finshine.activity.dialog;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.android.core.util.AppToast;
import com.google.gson.Gson;
import com.incito.finshine.R;
import com.incito.finshine.entity.ProdAppointmentWSEntity;
import com.incito.finshine.entity.ProdNewAppointment;
import com.incito.finshine.entity.Product;
import com.incito.finshine.entity.RemainBudgetReturn;
import com.incito.finshine.manager.SPManager;
import com.incito.finshine.network.HttpUtils;
import com.incito.finshine.network.HttpUtils.FaliureResult;
import com.incito.finshine.network.HttpUtils.SuccessReslut;
import com.incito.finshine.network.Request.RequestType;
import com.incito.finshine.network.RequestDefine;
import com.incito.utility.CommonUtil;
import com.incito.wisdomsdk.event.EventBus;


/**
 * 
 * @author SGDY
 * @time 2014/8/19 14:00
 *
 */


public class DialogProductAppointShare implements android.view.View.OnClickListener{

	private Context context;
	private View contentView;
	private View clickView;
	private PopupWindow popWin = null;
	private Window window;
	private Product product;//获取产品
	private ProdAppointmentWSEntity prodAppoint;//小明接口返回的状态
	private RemainBudgetReturn rbr;//针对tingchang的接口
	private boolean isCheck = false;//判断是否要检查服务器状态
	private ProdNewAppointment pNewAppoint;//创建预约之后返回的状态
	private boolean isBuy = false;//是否是购买产品
	
	/*
	 * 初始化所有UI
	 */
	/*----TextView---*/
	private TextView tvProductName;//产品名称
	private TextView tvProductPrice;//产品单价
	private TextView tvAppointClose;//预约关闭 TextView
	private TextView tvAppointSuccessCount;//预约成功份数
	private TextView tvAppointSureTitle;//预约确认标题（预约确认中||已销售）
	private TextView tvAppointSureCount;//预约确认数量
	private TextView tvAppointTotalTitle;//共 || 剩余
	private TextView tvAppointTotalCount;//共 || 剩余 数量
	private TextView tvAppointShare;//预约份额剩余
	private TextView tvAppointService;//发行商客服
	private TextView tvAppointWaiting;//请耐心等待确认通过
	private TextView tvAppointSpace;//空格  用以填充布局
	
	/*-----LinearLayout------*/
	private LinearLayout layoutAppointBefore;//未预约Layout
	private LinearLayout layoutAppointSuccess;//预约成功Layout
	private LinearLayout layoutAppointSure;//预约确认Layout
	private LinearLayout layoutAppointContact;//预约联系Layout
	
	/*-----EditText------*/
	private EditText etAppointBefore;//填入预约份数
	
	/*-----Button------*/
	private Button btnBuySure;//购买确定按钮
	private Button btnBuyCancel;//购买取消按钮
	private Button btnSure;//最底下的确认按钮
	
	/*-----ImageView------*/
	
	public DialogProductAppointShare(Context context,View clickView,Window window,boolean isCheck){
		this.context = context;
		this.clickView = clickView;
		this.window = window;
		this.isCheck = isCheck;
		init();
	}
	
	public void setProduct(Product product){
		this.product = product;
	}
	
	public void setRemainBudgetReturn(RemainBudgetReturn rbr){
		this.rbr = rbr;
		tvProductName.setText(rbr.getProdName().length() > 10 ? rbr.getProdName().substring(0, 9) + "..." : rbr.getProdName());
		switch(Integer.parseInt(rbr.getCheckStatus())){
		case 1://未预约 -- 提醒预约 开放状态
			UIFreshCommon(View.GONE, View.GONE, View.GONE, View.GONE, View.GONE, View.GONE, View.VISIBLE, View.INVISIBLE, View.VISIBLE,View.INVISIBLE);
			tvAppointShare.setText("对不起！您尚未预约，请先预约份额");
			btnSure.setText(context.getResources().getString(R.string.order_share));
			break;
		case 2://未预约 -- 预约已关闭 关闭状态
			UIFreshCommon(View.GONE, View.VISIBLE, View.GONE, View.INVISIBLE, View.GONE, View.GONE, View.GONE, View.INVISIBLE, View.VISIBLE,View.GONE);
			btnSure.setText(context.getResources().getString(R.string.btn_ok));
			break;
		case 3://预约确认中--后台正在确认 开放状态和关闭状态共用
			UIFreshCommon(View.VISIBLE, View.GONE, View.GONE, View.GONE, View.VISIBLE, View.VISIBLE, View.GONE, View.VISIBLE, View.VISIBLE,View.GONE);
			tvAppointSureTitle.setText("预约确认中");
			tvAppointTotalTitle.setText("共");
			tvAppointSureCount.setText((int)rbr.getOriginalAmount() + "万份");
			tvAppointTotalCount.setText((int)rbr.getOriginalAmount() + "万元");
			btnSure.setText(context.getResources().getString(R.string.btn_ok));
			break;
		case 4://份额不足
			UIFreshCommon(View.GONE, View.GONE, View.GONE, View.GONE, View.GONE, View.GONE, View.VISIBLE, View.VISIBLE, View.VISIBLE,View.INVISIBLE);
			tvAppointShare.setText("对不起！您预约份额不足"+ (int)rbr.getRemainAmount() + "/" + (int)rbr.getOriginalAmount() +"万");
			btnSure.setText(context.getResources().getString(R.string.btn_ok));
			break;
		}
	}
	
	private void init(){
		contentView = LayoutInflater.from(context).inflate(R.layout.dlg_appoint_common,null);
		btnSure = (Button)contentView.findViewById(R.id.btnSure);
		btnSure.setOnClickListener(this);
		popWin = new PopupWindow(contentView, 540, 520, true);
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
		
		initUI();
//		UIFresh(7);
	}
	//从服务器获取预约状态
	private void checkAppoint(){
		HttpUtils httpUtils = new HttpUtils(context,RequestDefine.APPOINT_RQ_SHARE,RequestType.GET,null);
		httpUtils.setProductId(product.getId());
		httpUtils.setSuccessListener(new SuccessReslut() {
			
			@Override
			public void getResluts(JSONObject response) {
				// TODO Auto-generated method stub
				Log.i("", response.toString());
				try {
					String status = response.getString("status");
					if(!response.isNull("item")){
						Gson gson = new Gson();
						prodAppoint = gson.fromJson(response.getJSONObject("item").toString(), ProdAppointmentWSEntity.class);
					}
					if(prodAppoint != null){
						if(status.equals("0")){//成功
							switch(prodAppoint.getReservationStatus_fk()){//预约状态
							case 1://开放预约
								if(prodAppoint.getAppoitmentStatus_fk() == 1){//确认中
									UIFresh(3);
								}else if(prodAppoint.getAppoitmentStatus_fk() == 2){//已确认
									UIFresh(4);
								}else if(prodAppoint.getAppoitmentStatus_fk() == -1){//未预约
									UIFresh(5);
								}
								break;
							case 2://关闭预约
								if(prodAppoint.getAppoitmentStatus_fk() == 1){//确认中
									UIFresh(3);
								}else if(prodAppoint.getAppoitmentStatus_fk() == 2){//已确认
									UIFresh(7);
								}else if(prodAppoint.getAppoitmentStatus_fk() == -1){//未预约
									UIFresh(6);
								}
								break;
							}
						}
						else if(status.equals("1")){//失败
							AppToast.ShowToast("获取失败!");
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		httpUtils.setFaliureResult(new FaliureResult() {
			
			@Override
			public void getResluts(String msg) {
				// TODO Auto-generated method stub
				AppToast.ShowToast("获取失败!");
			}
		});
		httpUtils.executeRequest();
	}
	
	private void initUI(){
		tvProductName = (TextView)contentView.findViewById(R.id.tv_product_name);
		tvProductPrice = (TextView)contentView.findViewById(R.id.tv_product_price);
		tvAppointClose = (TextView)contentView.findViewById(R.id.tv_appoint_close);
		tvAppointSuccessCount = (TextView)contentView.findViewById(R.id.tv_appoint_success_count);
		tvAppointSureTitle = (TextView)contentView.findViewById(R.id.tv_appoint_sure_title);
		tvAppointSureCount = (TextView)contentView.findViewById(R.id.tv_appoint_sure_count);
		tvAppointTotalTitle = (TextView)contentView.findViewById(R.id.tv_appoint_total_title);
		tvAppointTotalCount = (TextView)contentView.findViewById(R.id.tv_appoint_total_count);
		tvAppointShare = (TextView)contentView.findViewById(R.id.tv_appoint_share);
		tvAppointWaiting = (TextView)contentView.findViewById(R.id.tv_appoint_waiting);
		tvAppointService = (TextView)contentView.findViewById(R.id.tv_appoint_service);
		tvAppointService.setOnClickListener(this);
		tvAppointSpace = (TextView)contentView.findViewById(R.id.tv_appoint_space);
		
		layoutAppointBefore = (LinearLayout)contentView.findViewById(R.id.layout_appoint_before);
		layoutAppointSuccess = (LinearLayout)contentView.findViewById(R.id.layout_appoint_success);
		layoutAppointSure = (LinearLayout)contentView.findViewById(R.id.layout_appoint_sure);
		layoutAppointContact = (LinearLayout)contentView.findViewById(R.id.layout_appoint_contact);
		
		etAppointBefore = (EditText)contentView.findViewById(R.id.et_appoint_before);
		
		btnBuySure = (Button)contentView.findViewById(R.id.btnBuySure);
		btnBuySure.setOnClickListener(this);
		btnBuyCancel = (Button)contentView.findViewById(R.id.btnBuyCancel);
		btnBuyCancel.setOnClickListener(this);
		btnSure = (Button)contentView.findViewById(R.id.btnSure);
		btnSure.setOnClickListener(this);
		UIFreshCommon(View.GONE, View.GONE, View.GONE, View.GONE, View.GONE, View.GONE, View.GONE, View.GONE, View.GONE, View.GONE);
	}
	
	public void showPopWindow(){
		if(popWin != null){
			int[] location = new int[2];
			clickView.getLocationOnScreen(location);
			popWin.setAnimationStyle(R.style.anim_top_2_bottom);
			popWin.showAtLocation(clickView, Gravity.NO_GRAVITY,350, 120);
		}
		if(isCheck){
			checkAppoint();
		}
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btnSure:
			if(isBuy){//购买之后点击确认  修改产品列表字段
				product.setAppointmentStatus(1L);//修改预约状态为预约确认中
				EventBus.getDefault().post(product);
				if(popWin != null){
					prodAppoint = null;
					popWin.dismiss();
				}
			}else if(!isCheck && (rbr != null && rbr.getCheckStatus().equals("1"))){//点击确定 开始购买产品
				UIFresh(5);
			}else{//点击确定弹框直接消失
				if(popWin != null){
					prodAppoint = null;
					popWin.dismiss();
				}
			}			
			break;
		case R.id.btnBuySure:
			isBuy = true;
			createAppoint();
			break;
		case R.id.btnBuyCancel:
			if(popWin != null){
				prodAppoint = null;
				popWin.dismiss();
			}
			break;
		case R.id.et_appoint_before:
			break;
		case R.id.tv_appoint_service:
			if(isCheck){
				if(prodAppoint.getPublisherTelephone() != null){
					CommonUtil.dialPhone(context, prodAppoint.getPublisherTelephone());
				}
			}else{
				if(rbr.getIssuerPhone() != null){
					CommonUtil.dialPhone(context, rbr.getIssuerPhone());
				}
			}
			break;
		}
	}
	//创建预约
	private void createAppoint(){
		JSONObject json = new JSONObject();
		try {
			json.put("appointmentShare",etAppointBefore.getText().toString());
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		HttpUtils httpUtils = new HttpUtils(context,RequestDefine.APPOINT_RQ_CREATE,RequestType.POST,json);
		httpUtils.setProductId(product.getId());
		httpUtils.setUser_fk(SPManager.getInstance().getLongValue(SPManager.ID));
		httpUtils.setSuccessListener(new SuccessReslut() {
			
			@Override
			public void getResluts(JSONObject response) {
				// TODO Auto-generated method stub
				Log.i("", response.toString());
				try {
					String status = response.getString("status");
					if(!response.isNull("item")){
						Gson gson = new Gson();
						pNewAppoint = gson.fromJson(response.getJSONObject("item").toString(), ProdNewAppointment.class);
					}
					if(pNewAppoint != null){
						if(status.equals("0")){//成功
							int status_fk = response.getJSONObject("item").getInt("status_fk");//预约确认中
							if(status_fk == 1){
								UIFresh(3);
							}
						}
						else if(status.equals("1")){//失败
							AppToast.ShowToast("获取失败!");
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		httpUtils.setFaliureResult(new FaliureResult() {
			
			@Override
			public void getResluts(String msg) {
				// TODO Auto-generated method stub
				AppToast.ShowToast("获取失败!");
			}
		});
		httpUtils.executeRequest();
	}
	
	private void UIFreshCommon(int status1,int status2,int status3,int status4,int status5,int status6,int status7,int status8,int status9,int status10){
		tvProductPrice.setVisibility(status1);
		tvAppointClose.setVisibility(status2);
		layoutAppointBefore.setVisibility(status3);
		layoutAppointSuccess.setVisibility(status4);
		layoutAppointSure.setVisibility(status5);
		tvAppointWaiting.setVisibility(status6);
		tvAppointShare.setVisibility(status7);
		layoutAppointContact.setVisibility(status8);
		btnSure.setVisibility(status9);
		tvAppointSpace.setVisibility(status10);
	}

	private void UIFresh(int status){
		if(prodAppoint != null){
			tvProductName.setText(prodAppoint.getProductName().length() > 10 ? prodAppoint.getProductName().substring(0, 9) + "..." : prodAppoint.getProductName());
		}
		switch(status){
		case 1:
			break;
		case 2:
			
			break;
		case 3://预约确认中--后台正在确认 开放状态和关闭状态共用
			UIFreshCommon(View.VISIBLE, View.GONE, View.GONE, View.GONE, View.VISIBLE, View.VISIBLE, View.GONE, View.VISIBLE, View.VISIBLE,View.GONE);
			tvAppointSureTitle.setText("预约确认中");
			tvAppointTotalTitle.setText("共");
			if(pNewAppoint != null){
				tvAppointSureCount.setText((int)pNewAppoint.getAppointmentShare() + "万份");
				tvAppointTotalCount.setText((int)pNewAppoint.getAppointmentShare() + "万元");
			}else{
				tvAppointSureCount.setText((int)prodAppoint.getConfirmingShare() + "万份");
				tvAppointTotalCount.setText((int)prodAppoint.getConfirmingShare() + "万元");
			}
			btnSure.setText(context.getResources().getString(R.string.btn_ok));
			break;
		case 4://预约已确认 -- 预约成功  开放状态
			UIFreshCommon(View.VISIBLE, View.GONE, View.GONE, View.VISIBLE, View.VISIBLE, View.GONE, View.GONE, View.VISIBLE, View.VISIBLE,View.GONE);
			tvAppointSuccessCount.setText((int)prodAppoint.getAppointmentShare() + "万份");
			tvAppointSureTitle.setText("已销售");
			tvAppointSureCount.setText((int)prodAppoint.getSalesShare() + "万份");
			tvAppointTotalTitle.setText("剩余");
			tvAppointTotalCount.setText((int)prodAppoint.getRemainingShare() + "万份");
			btnSure.setText(context.getResources().getString(R.string.btn_ok));
			break;
		case 5://未预约 -- 开始预约
			UIFreshCommon(View.VISIBLE, View.GONE, View.VISIBLE, View.GONE, View.GONE, View.GONE, View.GONE, View.GONE, View.GONE,View.GONE);
			break;
		case 6://未预约 -- 预约已关闭 关闭状态
			UIFreshCommon(View.GONE, View.VISIBLE, View.GONE, View.INVISIBLE, View.GONE, View.GONE, View.GONE, View.INVISIBLE, View.VISIBLE,View.GONE);
			btnSure.setText(context.getResources().getString(R.string.btn_ok));
			break;
		case 7://预约成功 -- 关闭状态
			UIFreshCommon(View.VISIBLE, View.GONE, View.GONE, View.VISIBLE, View.VISIBLE, View.GONE, View.VISIBLE, View.GONE, View.VISIBLE,View.GONE);
			tvAppointSuccessCount.setText(String.valueOf((int)prodAppoint.getAppointmentShare()));
			tvAppointSureTitle.setText("已销售");
			tvAppointSureCount.setText(String.valueOf((int)prodAppoint.getSalesShare()));
			tvAppointTotalTitle.setText("剩余");
			tvAppointTotalCount.setText(String.valueOf((int)prodAppoint.getRemainingShare()));
			tvAppointShare.setText("当前预约已关闭，无法追加份额");
			btnSure.setText(context.getResources().getString(R.string.btn_ok));
			break;
		default:
			break;
		}
	}
	
}
