package com.custom.view;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.incito.finshine.R;
import com.incito.finshine.activity.ActFindTargetCustomer;
import com.incito.finshine.activity.adapter.AdapterFindCustomer;
import com.incito.finshine.entity.Customer;
import com.incito.finshine.entity.ProdSalesRealItem;
import com.incito.finshine.entity.ProdShareMOneyItem;
import com.incito.finshine.entity.Product;
import com.incito.finshine.manager.SPManager;
import com.incito.finshine.network.HttpUtils;
import com.incito.finshine.network.HttpUtils.SuccessReslut;
import com.incito.finshine.network.Request;
import com.incito.finshine.network.Request.RequestType;
import com.incito.finshine.network.RequestDefine;
import com.incito.utility.CommonUtil;
import com.incito.utility.DateUtil;

public class PopProdList implements OnClickListener {

	private Context context = null;

	private View popView = null;

	private View clickView = null;

	private PopupWindow popWin = null;

	private int popDirection = 0;

	/** popwindow 弹出方向 **/
	public static final int BOTTOM_2_TOP = 3;

	private Product prod = null;

	private int SHARE_MONEY_ID = R.layout.pop_prod_share_money;

	public PopProdList(Context context, View clickView, Product prod,
			int popDirection, int layoutID) {

		super();
		SHARE_MONEY_ID = layoutID;
		this.prod = prod;
		this.clickView = clickView;
		this.context = context;
		this.popDirection = popDirection;
		initPopwindow(clickView, layoutID);
	}

	GridView shareMoneyGV;
	ListView saleReal;

	private void initPopwindow(View clickView, int layoutID) {

		popView = LayoutInflater.from(context).inflate(layoutID, null);
		if (SHARE_MONEY_ID == R.layout.pop_prod_share_money) {
			popWin = new PopupWindow(popView, 600, 320, true);
			querySShareMoneyData();
		} else {
			popWin = new PopupWindow(popView, 800, 300, true);
			querySaleRealData();
		}
		popWin.setBackgroundDrawable(new ColorDrawable());
		popWin.setOutsideTouchable(true);
		popWin.setFocusable(true);

	}

	public void showPopWindow() {

		initData();

		if (popWin != null) {
			int[] location = new int[2];
			clickView.getLocationOnScreen(location);
			switch (popDirection) {
			case BOTTOM_2_TOP:
				popWin.setAnimationStyle(R.style.anim_bottom_2_top);
				System.out.println("location[1]=" + location[1]
						+ "location[0] =" + location[0]);

				if (SHARE_MONEY_ID == R.layout.pop_prod_share_money) {

					popWin.showAtLocation(clickView, Gravity.NO_GRAVITY, 600,
							location[1] - 285);
				} else {
					popWin.showAtLocation(clickView, Gravity.NO_GRAVITY, 280,
							location[1] - 270);
				}

				break;
			default:
				break;
			}
		}
	}

	private TextView tvTitle = null;
	private PopAdapter adapter = null;

	private void initData() {		
		tvTitle = (TextView) popView.findViewById(R.id.tvTitle);
		adapter = new PopAdapter();
		if (SHARE_MONEY_ID == R.layout.pop_prod_share_money) {
			popView.findViewById(R.id.btn_send_emali).setOnClickListener(this);
			popView.findViewById(R.id.btn_send_sms).setOnClickListener(this);
			/*
			 * popView.findViewById(R.id.btnNoticeCS).setOnClickListener(new
			 * OnClickListener() {
			 * 
			 * @Override public void onClick(View v) { if (dataMoney == null ||
			 * dataMoney.size() <= 0) { CommonUtil.showToast("没有分红的客户要通知",
			 * context); return; } StringBuffer sb = new StringBuffer(); for
			 * (ProdShareMOneyItem item : dataMoney) {
			 * sb.append(item.getCellPhone1()+","); } } });
			 */
			shareMoneyGV = (GridView) popView.findViewById(R.id.gridViewSm);
			shareMoneyGV.setAdapter(adapter);

		} else {
			TextView tvSaleName = (TextView) popView
					.findViewById(R.id.tvSalesProd);
			/*---------- change by SGDY for 5012  ------------*/
			if (prod.getProdName().length() > 20) {
				tvSaleName.setText(prod.getProdName().substring(0, 19) + "...");
			} else {
				tvSaleName.setText(prod.getProdName());
			}
			tvTitle.setText(R.string.sales_live);
			saleReal = (ListView) popView.findViewById(R.id.list_salereal);
			saleReal.setAdapter(adapter);
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

	private CallBackCLickEvent interCb = null;

	public void setCallBackCLickEvent(CallBackCLickEvent interCb) {
		this.interCb = interCb;
	}

	public interface CallBackCLickEvent {
		public void doClick(int position, Object obj);
	}

	private List<ProdSalesRealItem> dataSales = null;
	private List<ProdShareMOneyItem> dataMoney = null;

	private class PopAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			if (SHARE_MONEY_ID == R.layout.pop_prod_share_money) {
				return dataMoney == null ? 0 : dataMoney.size();
			}
			return dataSales == null ? 0 : dataSales.size();
		}

		@Override
		public Object getItem(int position) {
			if (SHARE_MONEY_ID == R.layout.pop_prod_share_money) {
				return dataMoney == null ? 0 : dataMoney.get(position);
			}
			return dataSales == null ? 0 : dataSales.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View view, ViewGroup parent) {

			ViewHolder holder = null;
			if (view == null) {
				view = LayoutInflater.from(context).inflate(
						R.layout.row_pop_prod_window, null);
				holder = new ViewHolder();

				holder.csName = (TextView) view.findViewById(R.id.tvCsName);
				holder.dueStates = (TextView) view
						.findViewById(R.id.tvInvestState);
				holder.investDate = (TextView) view
						.findViewById(R.id.tvInvestDate);
				holder.investMoney = (TextView) view
						.findViewById(R.id.tvInvestMoney);

				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}

			if (SHARE_MONEY_ID == R.layout.pop_prod_share_money) {
				holder.dueStates.setVisibility(View.GONE);
				holder.investDate.setVisibility(View.GONE);
				holder.investMoney.setVisibility(View.GONE);
				// 产品分红
				holder.csName.setText(((ProdShareMOneyItem) getItem(position))
						.getCustomerName());
			} else {
				ProdSalesRealItem item = (ProdSalesRealItem) getItem(position);
				// 销售实况，
				try {
					holder.dueStates
							.setText(item.getTransactionStatusId() == 1 ? "已成交"
									: "营销中");
				} catch (Exception e) {
				}
				holder.investDate.setText(DateUtil.getDateTimeByFormatAndMs(
						item.getOrderCreateDate(), DateUtil.FORMAT_YYYY_MM_DD));
				holder.investMoney.setText(item.getChangeAmount() + "万");
				holder.csName.setText(item.getCustomerName());
			}

			return view;
		}
	}

	class ViewHolder {
		TextView csName;
		TextView investMoney;
		TextView investDate;
		TextView dueStates;
	}

	// 查询销售实况
	private void querySaleRealData() {

		JSONObject params = new JSONObject();
		HttpUtils httpUtils = null;
		try {
			params.put("salesId",
					SPManager.getInstance().getLongValue(SPManager.ID));
			params.put("prodId", prod.getId());
			httpUtils = new HttpUtils(context,
					RequestDefine.QUERY_PROD_SALE_REAL, RequestType.POST,
					params);
			httpUtils.setShowDiloag(true);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}

		httpUtils.setSuccessListener(new SuccessReslut() {

			@Override
			public void getResluts(JSONObject response) {
				JSONObject obj;
				try {

					if (Request.RESLUT_OK.equals(response.optString("status"))) {
						//
						JSONArray arr = response.optJSONArray("list");
						if (arr != null) {
							dataSales = new Gson().fromJson(arr.toString(),
									new TypeToken<List<ProdSalesRealItem>>() {
									}.getType());
							adapter.notifyDataSetChanged();
							if (dataSales == null) {
								return;
							}
							TextView tvSaleStaues = (TextView) popView
									.findViewById(R.id.tvSalesStatus);
							int cjCount = 0;
							int marketing = 0;
							for (ProdSalesRealItem item : dataSales) {
								if (item.getTransactionStatusId() == 1) {
									cjCount++;
								} else if (item.getTransactionStatusId() == 2) {
									marketing++;
								}
							}
							tvSaleStaues.setText("已成交" + cjCount + "人," + "营销中"
									+ marketing + "人");

						}
					} else {
						CommonUtil.showToast("获取数据失败", context);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		httpUtils.executeRequest();

	}

	// 查询分红接口
	private void querySShareMoneyData() {

		JSONObject params = new JSONObject();
		HttpUtils httpUtils = null;
		try {
			params.put("expirationDays", 30);
			params.put("prodId", prod.getId());
			httpUtils = new HttpUtils(context,
					RequestDefine.QUERY_PROD_SHARE_MMONEY, RequestType.POST,
					params);
			httpUtils.setShowDiloag(true);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}

		httpUtils.setSuccessListener(new SuccessReslut() {

			@Override
			public void getResluts(JSONObject response) {
				try {
					if (Request.RESLUT_OK.equals(response.optString("status"))) {
						JSONArray arr = response.optJSONArray("list");
						if (arr != null) {
							dataMoney = new Gson().fromJson(arr.toString(),
									new TypeToken<List<ProdShareMOneyItem>>() {
									}.getType());
							adapter.notifyDataSetChanged();
						}
					} else {
						CommonUtil.showToast("获取数据失败", context);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		httpUtils.executeRequest();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_send_emali:
			if (dataMoney == null || dataMoney.size() <= 0) {
				CommonUtil.showToast("没有分红的客户要通知", context);
				return;
			}
			String[] targetEmail = new String[dataMoney.size()];
			for (int i = 0; i < dataMoney.size(); i++) {
				targetEmail[i] = dataMoney.get(i).getEmail1();
			}
			if (targetEmail.length == 0) {
				CommonUtil.showToast("邮件号码为空", context);
				return;
			}
			CommonUtil.sendEmail(context, targetEmail, "", "");
			// bug for #5427 liyng 20140814
			break;

		case R.id.btn_send_sms:
			if (dataMoney == null || dataMoney.size() <= 0) {
				CommonUtil.showToast("没有分红的客户要通知", context);
				return;
			}
			StringBuffer targetPhone = new StringBuffer();
			for (ProdShareMOneyItem item : dataMoney) {
				if(item.getCellPhone1() != null){
					targetPhone.append(item.getCellPhone1() + ",");
				}else{
					CommonUtil.showToast(item.getCustomerName() + "手机号码为空", context);
				}
				
			}
			if (TextUtils.isEmpty(targetPhone.toString())) {
				CommonUtil.showToast("短信号码不能为空", context);
				return;
			}
			CommonUtil.sendSms(context, targetPhone.toString());
			break;

		default:
			break;
		}

	}

}
