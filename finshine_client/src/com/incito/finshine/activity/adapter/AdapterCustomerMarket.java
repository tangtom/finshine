package com.incito.finshine.activity.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.incito.finshine.R;
import com.incito.finshine.activity.ActCustomerDetail;
import com.incito.finshine.activity.ActProductManagement;
import com.incito.finshine.common.IntentDefine;
import com.incito.finshine.entity.ExpireCustomer;
import com.incito.utility.CommonUtil;

public class AdapterCustomerMarket extends BaseAdapter {

	private List<ExpireCustomer> marketList = null;
	private Context context = null;

	public AdapterCustomerMarket(Context context) {
		super();
		this.context = context;
	}

	public void setRefeshData(List<ExpireCustomer> list) {
		this.marketList = list;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return marketList == null ? 0 : marketList.size();
	}

	@Override
	public Object getItem(int position) {
		return marketList == null ? 0 : marketList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		MarketViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.row_customer_market, parent, false);
			holder = new MarketViewHolder();
			holder.csName = (TextView) convertView
					.findViewById(R.id.tv_customer_name);

			holder.lookOverDueProd = (Button) convertView
					.findViewById(R.id.btn_look_orverdue_prod);
			holder.marketCustomer = (Button) convertView
					.findViewById(R.id.btn_market_customer);
			holder.mail = (ImageView) convertView.findViewById(R.id.iv_mail);
			holder.phone = (ImageView) convertView.findViewById(R.id.iv_phone);
			holder.sms = (ImageView) convertView.findViewById(R.id.iv_sms);
			holder.description = (TextView) convertView
					.findViewById(R.id.tv_customer_description);
			convertView.setTag(holder);
		} else {
			holder = (MarketViewHolder) convertView.getTag();
		}

		final ExpireCustomer cs = (ExpireCustomer) getItem(position);
		holder.csName.setText(cs.getName());
		StringBuffer sb = new StringBuffer();
		sb.append("<font color='#FF0000'>" + cs.getExpireProuctQty() + "</font>" +"款产品")
		  .append("<font color='#FF0000'>" + cs.getExpireOrderTotalAmount() + "</font>" +"万即将到期");
		holder.description.setText(Html.fromHtml(sb.toString()));		 
		holder.lookOverDueProd.setOnClickListener(new Myclick(position));
		holder.marketCustomer.setOnClickListener(new Myclick(position));
		holder.mail.setOnClickListener(new Myclick(position));
		holder.phone.setOnClickListener(new Myclick(position));
		holder.sms.setOnClickListener(new Myclick(position));
		
		return convertView;
		
		
		
	}

	private class Myclick implements OnClickListener {

		private int pos = 0;

		public Myclick(int position) {
			super();
			this.pos = position;
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_look_orverdue_prod:
				//   跳转到客户管理的投资记录
				Intent intent = new Intent(context, ActCustomerDetail.class);
				intent.putExtra(ActCustomerDetail.KEY_SHOW_FRAGMENT, ActCustomerDetail.F_INVEST_RECORD);
				intent.putExtra(IntentDefine.CUSTOMER_ID, ((ExpireCustomer)getItem(pos)).getId());
				context.startActivity(intent);
				break;
			case R.id.btn_market_customer:
				// 选择产品,
				Intent i = new Intent(context, ActProductManagement.class);
				i.putExtra(ActProductManagement.ACTION_FROM_MARKET_CSID, ((ExpireCustomer)getItem(pos)).getId());
				i.putExtra(ActProductManagement.ACTION_INTENT_PRODUCT, 1);
				context.startActivity(i); 
				break;
			case R.id.iv_mail:
//				CommonUtil.sendEmail(context, emailReciver, emailContent, emialTitle);
				CommonUtil.sendSms(context, ((ExpireCustomer)getItem(pos)).getCellPhone1());
				break;
			case R.id.iv_sms:
//				CommonUtil.sendSms(context, ((ExpireCustomer)getItem(pos)).getCellPhone1());
				break;
			case R.id.iv_phone:
//				CommonUtil.sendSms(context, ((ExpireCustomer)getItem(pos)).getCellPhone1());
				CommonUtil.dialPhone(context, ((ExpireCustomer)getItem(pos)).getCellPhone1());
				break;
			default:
				break;
			}

		}

	}

	public class MarketViewHolder {

		Button lookOverDueProd;
		Button marketCustomer;
		TextView csName;
		ImageView sms;
		ImageView mail;
		ImageView phone;
		TextView description;
	}
	
	
	
	
}
