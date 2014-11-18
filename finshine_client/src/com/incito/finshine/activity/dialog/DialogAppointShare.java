package com.incito.finshine.activity.dialog;

import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.incito.utility.CommonUtil;
import com.incito.finshine.R;
import com.incito.finshine.entity.Product;
import com.incito.finshine.manager.CoreManager;
import com.incito.finshine.network.Request;
import com.incito.finshine.network.Request.RequestType;
import com.incito.finshine.network.RequestDefine;
import com.incito.wisdomsdk.net.http.JsonHttpResponseHandler;

public class DialogAppointShare implements OnClickListener{
 
	private static final String TAG ="DialogAppointShare:";
	
	private Context context;
	
	private View contentView;
	
	private Product pro;
	
	private EditText etInput;
	
	private TextView tvApponitMoney;
	
	private TextView tvPerShare;
	
	private float totalMoney;
	
	public View getContentView() {
		return contentView;
	}
	 
	private Dialog dialog;
	
	public Dialog getDialog() {
		return dialog;
	}

	public DialogAppointShare(Context context,Product product) {
		
		this.context = context;
		this.pro = product;
		
		dialog = new Dialog(context, R.style.CustomProgressDialog);
        contentView = LayoutInflater.from(context).inflate(R.layout.dialog_appoint_share, null);
        LayoutParams params = new LayoutParams(480,440);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.addContentView(contentView, params);
        dialog.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss(DialogInterface dialog) {
				//取消
			}
		});
        
        init();

        etInput.setOnClickListener(this);
		
	} 
	
	private void init(){
        
		
		contentView.findViewById(R.id.btn_save).setOnClickListener(this);
		
		contentView.findViewById(R.id.btn_cancle).setOnClickListener(this);
		
		etInput = (EditText)contentView.findViewById(R.id.et_appoint_amount);
		
		tvApponitMoney = (TextView)contentView.findViewById(R.id.tv_totalmoney);
		
		tvPerShare = (TextView)contentView.findViewById(R.id.tv_per_share_money);
		
		TextView tvProdName = (TextView)contentView.findViewById(R.id.tvProdName);
		
		if (pro != null && !TextUtils.isEmpty(pro.getProdName())) {
			tvProdName.setText(pro.getProdName()+"预约份额");
		}
		if (pro != null) {
			tvPerShare.setText("1份 = "+pro.getProdPrice()+"元");
		}
		
		tvApponitMoney.setText("共预约0万份，总共0元");
		etInput.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
				try {
					
					String amount = s.toString();
					if (pro == null) {
						return;
					}
					float toTalMoeny = pro.getProdPrice()*Long.parseLong(amount);
					totalMoney = toTalMoeny;
					tvApponitMoney.setText("共预约"+amount+"万份，"+"总共"+toTalMoeny+"万元");
				} catch (Exception e) {
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_close:
			close();
			break;
		case R.id.btn_save:
			 // 保存
			doSave();
			break;
		case R.id.btn_cancle:
			close();
			break;
		case R.id.btn_ok:
          // 掉接口 取数据 
		  try {
				JSONObject json = new JSONObject();
				Request request = new Request(RequestDefine.RQ_PRODUCT_APPOINT_SHARE,pro.getId() ,RequestType.PUT, json, handlerAppointShare);
				request.setAppointAmount(etInput.getText().toString());
				CoreManager.getInstance().postRequest(request);
				} catch (Exception e) {
			}
		 break;
		default:
			break;
		}
	}
	
	private void doSave(){
		
		if (TextUtils.isEmpty(etInput.getText().toString())) {
			CommonUtil.showToast("请输入预约的份数", context);
			return;
		}
		contentView.findViewById(R.id.ltbtn).setVisibility(View.GONE);
		contentView.findViewById(R.id.btn_ok).setVisibility(View.VISIBLE);
		contentView.findViewById(R.id.btn_ok).setOnClickListener(this);
		((LinearLayout)contentView.findViewById(R.id.lt_3)).setGravity(Gravity.CENTER);
		tvPerShare.setVisibility(View.GONE);
		contentView.findViewById(R.id.lt_2).setVisibility(View.GONE);
		tvApponitMoney.setVisibility(View.VISIBLE);
		
	}
	
	private void close(){
		if (dialog != null) {
			dialog.dismiss();
			dialog = null;
		}
	}
	 
	private JsonHttpResponseHandler handlerAppointShare = new JsonHttpResponseHandler() {
		@Override
		public void onSuccess(JSONObject response) {
			Log.d(TAG, "success o= " + response.toString());
			String states =response.optString("status");
			if (Request.RESLUT_OK.equals(states)) {
				CommonUtil.showToast("预约成功", context);
			}else{
				CommonUtil.showToast("预约失败", context);
			}
			close();
		}

		@Override
		public void onFailure(Throwable error, String content) {
			Log.i(TAG, "onFailure = " + content);
			CommonUtil.showToast("预约失败"+content, context);
			close();
		}
	};

}
