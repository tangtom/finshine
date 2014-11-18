package com.incito.finshine.activity.adapter;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.incito.finshine.R;
import com.incito.finshine.activity.adapter.AdapterProductList.RefreshProdData;
import com.incito.finshine.entity.Product;
import com.incito.finshine.manager.CoreManager;
import com.incito.finshine.network.Request;
import com.incito.finshine.network.RequestDefine;
import com.incito.finshine.network.Request.RequestType;
import com.incito.utility.CommonUtil;
import com.incito.utility.DateUtil;
import com.incito.wisdomsdk.net.http.JsonHttpResponseHandler;

public class FirstPageProdAdapter extends BaseAdapter {

	private List<Product> prodList;
	private Context context;

	public FirstPageProdAdapter(Context context, List<Product> prodList) {
		super();
		this.context = context;
		this.prodList = prodList;
	}

	public void setListData(List<Product> prodList) {
		this.prodList = prodList;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return prodList == null ? 0 : prodList.size();
	}

	@Override
	public Object getItem(int position) {
		return prodList == null ? 0 : prodList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {

		ProdView prod = null;
		if (view == null) {
			view = LayoutInflater.from(context).inflate(
					R.layout.row_first_page_prod, null);
			prod = new ProdView();
			prod.collect = (ImageView) view.findViewById(R.id.prodCollected);
			prod.deadline = (TextView) view.findViewById(R.id.prodDeadLine);
			prod.expectedIncome = (TextView) view.findViewById(R.id.prodIncome);
			prod.investMoney = (TextView) view
					.findViewById(R.id.prodInvstMoney);
			prod.prodName = (TextView) view.findViewById(R.id.prodName);
			prod.prodType = (TextView) view.findViewById(R.id.prodType);
			prod.publisherTime = (TextView) view
					.findViewById(R.id.prodPublisher);
			view.setTag(prod);
		} else {
			prod = (ProdView) view.getTag();
		}

		final Product item = (Product) getItem(position);
		prod.prodName.setText(item.getProdName());
		prod.deadline.setText(item.getProdTime() + "年");
		prod.expectedIncome.setText(item.getProdReceipts() + "%");
		prod.investMoney.setText(item.getProdPrice() + " 万");

		String[] proTypeLevelFirst = (String[]) context.getResources()
				.getStringArray(R.array.product__type);
		;// 一级类别
		String[] proTypeLevelSecondAffince = (String[]) context.getResources()
				.getStringArray(R.array.product__affiance_type);
		;// 二级类别 基金资管
		String[] proTypeLevelSecondFund = (String[]) context.getResources()
				.getStringArray(R.array.product__fund_type);
		;// 二级类别 基金
		long type1 = item.getProdFirstType();
		// long type2 = item.getProdSecondtype();
		StringBuffer sb = new StringBuffer();
		try {
			// sb.append((type1 == 1) ? (proTypeLevelSecondFund[(int)
			// (type2-1)]) : (proTypeLevelSecondAffince[(int) (type2-1)]));
			// prod.prodType.setText(proTypeLevelFirst[(int) (type1-1)] +
			// sb.toString());
			prod.prodType.setText(proTypeLevelFirst[(int) (type1 - 1)]);
		} catch (Exception e) {
		}

		long collected = 0;
		if (item.getIsSave() != null) {
			collected = item.getIsSave().longValue();
			if (collected == COLLECTED) {
				prod.collect.setImageResource(R.drawable.ic_has_collected);
			} else {
				prod.collect.setImageResource(R.drawable.ic_collect);
			}
		} else {
			prod.collect.setImageResource(R.drawable.ic_collect);
		}

		prod.publisherTime.setText(DateUtil.getDateTimeByFormatAndMs(
				item.getProdOnDateTime(), DateUtil.FORMAT_YYYY_MM_DD)
				+ " 至 "
				+ DateUtil.getDateTimeByFormatAndMs(item.getProdEnDateTime(),
						DateUtil.FORMAT_YYYY_MM_DD));

		prod.collect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				collectProdBtn(item);
			}
		});

		return view;
	}

	class ProdView {
		TextView prodName;
		TextView prodType;
		TextView deadline;
		TextView publisherTime;
		TextView investMoney;
		TextView expectedIncome;
		ImageView collect;
	}

	private static final String COLLECT_SUCCESSS = "收藏成功";
	private static final String CANCLE_COLLECTED = "取消收藏成功";
	private static final String COLLECT_FALIURE = "收藏失败";
	private static final String CANCLE_COLLECTED_FALIURE = "取消收藏失败";

	private JsonHttpResponseHandler handlerCollRefreshProduct = new JsonHttpResponseHandler() {
		@Override
		public void onSuccess(JSONObject response) {
			if (Request.RESLUT_OK.equals(response.opt("status"))) {
				CommonUtil.showToast(COLLECT_SUCCESSS, context);
				try {
					response = response.getJSONObject("item");
				} catch (JSONException e) {
					e.printStackTrace();
				}
				if (refreshProdListener != null) {
					refreshProdListener.doRefresh();
				}

			} else {
				CommonUtil.showToast(COLLECT_FALIURE, context);
			}
		}

		@Override
		public void onFailure(Throwable error, String content) {
			CommonUtil.showToast(COLLECT_FALIURE, context);
		}
	};
	private JsonHttpResponseHandler handlerCancleCollRefreshProduct = new JsonHttpResponseHandler() {
		@Override
		public void onSuccess(JSONObject response) {
			if (Request.RESLUT_OK.equals(response.opt("status"))) {
				CommonUtil.showToast(CANCLE_COLLECTED, context);
				try {
					response = response.getJSONObject("item");
				} catch (JSONException e) {
					e.printStackTrace();
				}
				if (refreshProdListener != null) {
					refreshProdListener.doRefresh();
				}
			} else {
				CommonUtil.showToast(CANCLE_COLLECTED_FALIURE, context);
			}
		}

		@Override
		public void onFailure(Throwable error, String content) {
			CommonUtil.showToast(CANCLE_COLLECTED_FALIURE + content, context);
		}
	};

	private RefreshProdData refreshProdListener;

	public void setRefreshProdListener(RefreshProdData refreshProdListener) {
		this.refreshProdListener = refreshProdListener;
	}

	public interface RefreshProdData {

		// 刷新产品列表
		public void doRefresh();
	}

	/** 产品收藏 **/
	public static final long COLLECTED = 1;

	private void collectProdBtn(final Product productItem) {

		int collected = 0;
		if (productItem.getIsSave() != null) {
			collected = productItem.getIsSave().intValue();
		}
		JSONObject json = null;
		try {
			json = new JSONObject();
			Request request = null;
			if (collected == COLLECTED) {
				request = new Request(RequestDefine.RQ_PRODUCT_COLLECTION,
						productItem.getId(), RequestType.POST, json,
						handlerCancleCollRefreshProduct);
			} else {
				request = new Request(RequestDefine.RQ_PRODUCT_COLLECTION,
						productItem.getId(), RequestType.POST, json,
						handlerCollRefreshProduct);
			}
			CoreManager.getInstance().postRequest(request);
		} catch (Exception e) {
		}
	}
}
