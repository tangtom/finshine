package com.custom.view;

import java.math.BigDecimal;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.incito.finshine.R;
import com.incito.finshine.activity.adapter.AdapterPopUseCommission;
import com.incito.finshine.entity.PropsAvailable;
import com.incito.finshine.entity.PropsUsedItemWSEntity;
import com.incito.utility.CommonUtil;

public class PopUseCommission implements OnClickListener{
	private ListView listUseCommission;
	private Context context = null;
	private AdapterPopUseCommission adapter;
	private View popView = null;
	private TextView textProductName,textLongProductName,textTotalProportion;
	private View clickView = null;
	private Button btnSure,btnCancel;
	private String productName;
	private List<PropsUsedItemWSEntity> propLists;
	private PropsAvailable propsAvailable;
	public PopupWindow getPopWin() {
		return popWin;
	}
	private PopupWindow popWin = null;

	/** popwindow 弹出方向 **/
	public static final int TOP_2_BOTTOM = 3;

	private int popDirection = TOP_2_BOTTOM;


	public PopUseCommission(Context context, View clickView,PropsAvailable propsAvailable) {

		super();
		this.propsAvailable = propsAvailable;
		this.clickView = clickView;
		this.context = context;
		initPopwindow(clickView);
	}

	private void initPopwindow(View clickView) {

		popView = LayoutInflater.from(context).inflate(
				R.layout.pop_use_commission, null);
		popWin = new PopupWindow(popView, 500, 600, true);

		popWin.setBackgroundDrawable(new ColorDrawable());
		popWin.setOutsideTouchable(true);
		popWin.setFocusable(true);
		popWin.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss() {
				CommonUtil.hideSoftInputFromWindow((Activity)context);
				
			}
		});
		initData();
	}

	public void showPopWindow() {

		if (popWin != null) {
			int[] location = new int[2];
			clickView.getLocationOnScreen(location);
			switch (popDirection) {
			case TOP_2_BOTTOM:
				productNameFormat();
				popWin.setAnimationStyle(R.style.anim_top_2_bottom);
				popWin.showAtLocation(clickView, Gravity.NO_GRAVITY,390, 20 + clickView.getHeight());
				break;
			default:
				break;
			}
		}
	}
	
	public void productNameFormat()
	{
		productName = textProductName.getText().toString();
		StringBuffer sb = new StringBuffer();
		if(productName.length() > 6)
		{
			for(int i = 0;i < 6;i ++)
			{
				sb.append(productName.charAt(i));
			}
			sb.append("...");
		}else{
			sb.append(productName);
		}
		textProductName.setText(sb.toString());
	}

	private void initData() {
		propLists = propsAvailable.getItems();
		listUseCommission = (ListView)popView.findViewById(R.id.list_use_commission);
		adapter  = new AdapterPopUseCommission(context,propLists);
		listUseCommission.setAdapter(adapter);
		btnSure = (Button)popView.findViewById(R.id.buttonSure);
		btnCancel = (Button)popView.findViewById(R.id.buttonCancel);
		btnSure.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
		textProductName = (TextView)popView.findViewById(R.id.text_product_name);
		textProductName.setText(propsAvailable.getProduct().getProdName());
		textProductName.setOnClickListener(this);
		textLongProductName = (TextView)popView.findViewById(R.id.text_long_product_name);
		textTotalProportion = (TextView)popView.findViewById(R.id.text_total_proportion);
		double total = 0.0;
		for(PropsUsedItemWSEntity p : propLists){
			total += p.getQtyOfUsed() * p.getPropertyValue();
		}
		textTotalProportion.setText(new BigDecimal(total).setScale(2,BigDecimal.ROUND_HALF_UP) + "%");
	}
	
	public void closePopWindow() {
		if (popWin != null) {
			if (popWin.isShowing()) {
				popWin.dismiss();
//				popWin = null;
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

	@Override
	public void onClick(View v) {
		PopUseCommissionListener popUseCommissionListener = (PopUseCommissionListener)context;
		switch(v.getId()){
		case R.id.buttonSure:
			popUseCommissionListener.sendMsgToServer();
			break;
		case R.id.buttonCancel:
			closePopWindow();
			break;
		case R.id.text_product_name:
			textLongProductName.setText(productName);
			int visibility = textLongProductName.getVisibility() == View.GONE ? View.VISIBLE : View.GONE;
			textLongProductName.setVisibility(visibility);
			break;
		default:
			break;
		}
	}
	
	public interface PopUseCommissionListener
	{
		public void sendMsgToServer();
	}

}
