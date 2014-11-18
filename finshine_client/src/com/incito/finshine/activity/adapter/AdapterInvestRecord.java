package com.incito.finshine.activity.adapter;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.incito.finshine.R;
import com.incito.finshine.customer.ContactHistory;
import com.incito.finshine.entity.Customer;
import com.incito.finshine.entity.InvestmentRecord;
import com.incito.utility.DateUtil;

public class AdapterInvestRecord extends BaseAdapter implements Comparator<InvestmentRecord> {

	private LayoutInflater mInflater;
	private Context context;

	private List<InvestmentRecord> mItems;
	private List<InvestmentRecord> tempCustomerList;

	public AdapterInvestRecord(Context context) {

		mInflater = LayoutInflater.from(context);
		this.context = context;

	}

	public void setItemList(List<InvestmentRecord> list) {
		mItems = list;
		tempCustomerList = mItems;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		int size = 0;
		if (tempCustomerList != null) {
			size = tempCustomerList.size();
		}
		return size;
	}

	@Override
	public Object getItem(int position) {
		return tempCustomerList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return tempCustomerList.get(position).getProdId();
	}

	private int currentSortValue = R.string.product_manager_default_sort;

	public void sortProduct(int currentSort) {
		this.currentSortValue = currentSort;
		Collections.sort(tempCustomerList, this);
		notifyDataSetChanged();
	}
	
	@Override
	public int compare(InvestmentRecord invest1, InvestmentRecord invest2) {
		int i = 0;
		switch (currentSortValue) {
		
		case R.string.product_manager_default_sort:
			if (invest1.getExpireStatus() > invest2.getExpireStatus())// 倒序  
				i = 1;
			else
				i = -1;
			break;

		case R.string.invest_money:
			if (invest1.getChangeAmount() > invest2.getChangeAmount())// 投资额
				i = -1;
			else
				i = 1;
			break;

		case R.string.invest_deadline:
			if (invest1.getInvestmentPeriod() > invest2.getInvestmentPeriod())//投资期限
				i = -1;
			else
				i = 1;
			break;
			
		case R.string.invest_ecpected_income_rate:
			if (invest1.getExpectedRevenue() > invest2.getExpectedRevenue())//预期收益率
				i = -1;
			else
				i = 1;
			break;

		default:
			break;
		}
		return i;

	}
	
	@Override
	public View getView(int position, View view, ViewGroup parent) {

		InvestViewHolder holder;

		if (view == null) {
			view = mInflater.inflate(R.layout.row_invest_record, null);
			holder = new InvestViewHolder();
			holder.invtDueStat = (Button) view
					.findViewById(R.id.btn_invest_states);// 到期
			holder.investCompaneyIv = (ImageView) view
					.findViewById(R.id.iv_invest_name);// 图片
			holder.investHobby = (TextView) view
					.findViewById(R.id.tv_invest_hobby);// 信托
			holder.invProjWeek = (TextView) view
					.findViewById(R.id.tv_invest_proweek);// 名字
			holder.invsetCode = (TextView) view
					.findViewById(R.id.tv_invest_code);// 编码 0089
			holder.investStates = (TextView) view
					.findViewById(R.id.tv_invest_status);// 状态
			holder.incomeRate = (TextView) view
					.findViewById(R.id.tv_income_rate);// 预期收益
			holder.startMoney = (TextView) view
					.findViewById(R.id.tv_start_money);// 起投金额
			holder.investMoney = (TextView) view
					.findViewById(R.id.tv_invest_money);// 投资金额
			holder.buyDate = (TextView) view.findViewById(R.id.tv_buy_date);
			;// 购买时间
			holder.investCmpName = (TextView) view
					.findViewById(R.id.tv_company_name);// 机构名称
			holder.publishTime = (TextView) view
					.findViewById(R.id.tv_publish_date);// 发布时间
			holder.toActIncomeMoney = (TextView) view
					.findViewById(R.id.tv_due_money);// 是否到账收益

			view.setTag(holder);
		} else {
			holder = (InvestViewHolder) view.getTag();
		}

		// Bind the data efficiently with the holder.

		InvestmentRecord item = tempCustomerList.get(position);
		if (tempCustomerList != null) {

			if (item.getExpireStatus() == 1)//1即将到期
			{
				 holder.invtDueStat.setBackgroundResource(R.drawable.bg_to_due);
				 holder.invtDueStat.setText("即\n将\n到\n期");
				 holder.invtDueStat.setTextColor(context.getResources().getColor(R.color.text_orange_ff8b23));
			}
			
			else if (item.getExpireStatus() == 2){//2未到期
				 holder.invtDueStat.setBackgroundResource(R.drawable.bg_not_due);
				 holder.invtDueStat.setText("未\n到\n期");
				 holder.invtDueStat.setTextColor(context.getResources().getColor(R.color.text_brown_d7c093));
			}
			else if (item.getExpireStatus() == 3){//3、已过期
				 holder.invtDueStat.setBackgroundDrawable(null);
				 holder.invtDueStat.setText("已\n过\n期");
				 holder.invtDueStat.setTextColor(context.getResources().getColor(R.color.text_gray_808080));
			}
			
			holder.investCompaneyIv.setImageResource(item.getProdFirstTypeName().equals("资管") ? R.drawable.ic_manage : R.drawable.ic_xintuo);

			holder.investHobby.setText("[" + item.getProdFirstTypeName() + "]");
			holder.invProjWeek.setText(item.getProdName());
			holder.invsetCode.setText(item.getProdCode());
			holder.investStates.setText(item.getProdStatusName());
			holder.incomeRate
					.setText(new BigDecimal(item.getExpectedRevenue()).setScale(2,BigDecimal.ROUND_HALF_UP) + "%");
			holder.startMoney.setText(new BigDecimal(item.getProdStart()).setScale(0, BigDecimal.ROUND_HALF_UP) + "");
			holder.investMoney.setText(new BigDecimal(item.getChangeAmount()).setScale(0, BigDecimal.ROUND_HALF_UP) + "");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			holder.buyDate.setText(sdf.format(new Date(item.getPurchasedDate())));//购买时间
			holder.investCmpName.setText(item.getProdPreference());
			holder.publishTime.setText(DateUtil.formatDate2String(new Date(item.getProdOnDateTime()), DateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS));
			// prodOnDateTime prodEnDateTime
			holder.toActIncomeMoney.setText(new BigDecimal(item
					.getObtainedProfit()).setScale(2, BigDecimal.ROUND_HALF_UP) + "万");

			// holder.background.setBackgroundColor(color);
		}

		return view;
	}
	
	// 搜索客户
		public Filter getFilter() {

			Filter filter = new Filter() {

				String filterNum = null;

				@Override
				@SuppressWarnings("unchecked")
				protected void publishResults(CharSequence constraint,
						FilterResults results) {

					if (TextUtils.isEmpty(filterNum)) {
						tempCustomerList = mItems;
						notifyDataSetChanged();
						return;
					}
					tempCustomerList = (List<InvestmentRecord>) results.values;
					if (results.count > 0) {
						notifyDataSetChanged();
					} else {
						notifyDataSetInvalidated();
					}
				}

				@Override
				protected FilterResults performFiltering(CharSequence s) {
					String str = s.toString();
					filterNum = str;
					FilterResults results = new FilterResults();
					List<InvestmentRecord> contactList = new ArrayList<InvestmentRecord>();

					if (mItems != null && mItems.size() > 0) {
						for (InvestmentRecord cu : mItems) {
							if (TextUtils.isEmpty(s)) {
								continue;
							}

							if (cu.getProdName().indexOf(str) >= 0) {
								contactList.add(cu);
							}
						}
					}
					results.values = contactList;
					results.count = contactList.size();
					return results;
				}
			};
			return filter;
		}

	static class InvestViewHolder {

		Button invtDueStat;
		ImageView investCompaneyIv;
		TextView investHobby;
		TextView invProjWeek;// 投资项目的期数
		TextView invsetCode;
		TextView investStates;
		TextView incomeRate;
		TextView startMoney;
		TextView investMoney;
		TextView buyDate;
		TextView investCmpName;
		TextView publishTime;
		TextView toActIncomeMoney;// 是否到账收益
	}

}
