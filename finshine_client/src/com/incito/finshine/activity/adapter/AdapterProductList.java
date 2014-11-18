package com.incito.finshine.activity.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.core.util.AppToast;
import com.custom.view.PopListWindow;
import com.custom.view.PopProdList;
import com.google.gson.Gson;
import com.incito.finshine.R;
import com.incito.finshine.activity.ActCustomerMarketProgress;
import com.incito.finshine.activity.ActFindTargetCustomer;
import com.incito.finshine.activity.ActPointerCommission;
import com.incito.finshine.activity.ActProductManagement;
import com.incito.finshine.activity.ActQueryCommission;
import com.incito.finshine.activity.dialog.DialogProductAppointShare;
import com.incito.finshine.common.Constant;
import com.incito.finshine.entity.MarketStateReslut;
import com.incito.finshine.entity.Product;
import com.incito.finshine.entity.RemainBudgetReturn;
import com.incito.finshine.manager.CoreManager;
import com.incito.finshine.manager.JsonParse;
import com.incito.finshine.manager.SPManager;
import com.incito.finshine.network.HttpUtils;
import com.incito.finshine.network.HttpUtils.FaliureResult;
import com.incito.finshine.network.HttpUtils.SuccessReslut;
import com.incito.finshine.network.Request;
import com.incito.finshine.network.Request.RequestType;
import com.incito.finshine.network.RequestDefine;
import com.incito.finshine.product.ProductManager;
import com.incito.utility.CommonUtil;
import com.incito.utility.DateUtil;
import com.incito.wisdomsdk.event.EventBus;
import com.incito.wisdomsdk.net.http.JsonHttpResponseHandler;

public class AdapterProductList extends BaseAdapter implements Comparator<Product> {

	private static final String TAG = "AdapterProductList";

	/** 按产品发布的时间倒序 **/
	public final static int DEFALUT_SORT = R.string.product_manager_default_sort;
	/** 按产品发行起始时间较晚的在前 **/
	public final static int PUBLISH_TINME = R.string.product_manager_publish_time;
	/** 预期收益率高的在前 **/
	public final static int EXPECTED_INCOME = R.string.product_manager_ecpected_income;
	/** 佣金高的在前 **/
	public final static int COMMISION = R.string.product_manager_commision;
	/** 期限短的在前 **/
	public final static int DEADLINE = R.string.product_manager_time_limit;
	/** 规模大的在前 **/
	public final static int SCALE = R.string.product_manager_scale;
	/** 根据各个pad端收藏该产品的次数，累计高的在前 ；注：若理财师取消了对该产品的收藏，则不会在已累计的次数里减去 **/
	public final static int HOT = R.string.product_manager_hotest;
	/** 默认排序 **/
	private int currentSortWay = DEFALUT_SORT;

	/** 产品收藏 **/
	public static final long COLLECTED = 1;

	/** 产品状态:产品状态 1新建 2审核 3预约 4预售 5在售 6封账 7下架 **/
	private static final int PRO_STATES_SOLDOUT = 6;
	private static final int PRO_STATES_ONSOLDING = 5;
	/*private static final int PRO_NEW = 1;
	private static final int PRO_CHECK = 2;*/
	private static final int PRO_ORDER = 3;
	private static final int PRO_ORDER_SALING = 4;
	private static final int PRO_SHELVES = 7;

	public static final int POSITIVE_SORTING = 1;// 默认正序
	public static final int REVERSE_SORTING = -1;// 反序

	/** 服务端推荐的产品 **/
	private static final long IS_SERVER_RECOMMAND = 1;

/*	private String[] proTypeLevelFirst = null;// 一级类别
	private String[] proTypeLevelSecondAffince = null;// 二级类别 基金资管
	private String[] proTypeLevelSecondFund = null;// 二级类别 基金
*/
	private List<Product> allProductList = null;//所有产品备用数据 做刷选用
	private List<Product> tempProductList = null;//所有产品 

	private LayoutInflater flater = null;
	private Context context;

	private List<Integer> clickPosition = new ArrayList<Integer>();

	private static final String COLLECT_SUCCESSS = "收藏成功";
	private static final String CANCLE_COLLECTED = "取消收藏成功";
	private static final String COLLECT_FALIURE = "收藏失败";
	private static final String CANCLE_COLLECTED_FALIURE = "取消收藏失败";

	private long customerId = -1;

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	/** 客户营销 2 对比还是购买意向产品 **/
	public static final int MARKET_INTENT_PRODUCT = 1;
	public static final int SELE_PROD_INTENT_PROD = 2;
	public static final int SELE_PROD_ALL_PROD = 3;
	private int selec_prod_type = SELE_PROD_INTENT_PROD;

	RemainBudgetReturn rbr = null;//检查产品预约详情
	
	public int getSelec_prod_type() {
		return selec_prod_type;
	}

	public void setSelec_prod_type(int selec_prod_type) {
		this.selec_prod_type = selec_prod_type;
	}

	private int currentAdapter = -1;

	private ProductManager prodManger = null;
	/** 存储比较的产品数据 **/
	private List<Long> compareProdId = null;

	public List<Long> getCompareProdId() {
		return compareProdId;
	}

	public void setCompareProdId(List<Long> compareProdId) {
		this.compareProdId = compareProdId;
	}

	private JsonHttpResponseHandler handlerCollRefreshProduct = new JsonHttpResponseHandler() {
		@Override
		public void onSuccess(JSONObject response) {
			Log.d(TAG, "success o= " + response.toString());
			if (Request.RESLUT_OK.equals(response.opt("status"))) {
				CommonUtil.showToast(COLLECT_SUCCESSS, context);
				try {
					response = response.getJSONObject("item");
				} catch (JSONException e) {
					e.printStackTrace();
				}
				prodManger.updateProductById(Long.valueOf(response.optLong("prodId")), true);
				if (refreshProdListener != null) {
					// 刷新重新取数据
					refreshProdListener.doRefresh(true);
				}
			} else {
				CommonUtil.showToast(COLLECT_FALIURE, context);
			}
		}

		@Override
		public void onFailure(Throwable error, String content) {
			Log.i(TAG, "onFailure = " + content);
			CommonUtil.showToast(COLLECT_FALIURE, context);
		}
	};
	private JsonHttpResponseHandler handlerCancleCollRefreshProduct = new JsonHttpResponseHandler() {
		@Override
		public void onSuccess(JSONObject response) {
			Log.d(TAG, "success o= " + response.toString());
			if (Request.RESLUT_OK.equals(response.opt("status"))) {
				CommonUtil.showToast(CANCLE_COLLECTED, context);
				try {
					response = response.getJSONObject("item");
				} catch (JSONException e) {
					e.printStackTrace();
				}
				prodManger.updateProductById(Long.valueOf(response.optLong("prodId")), false);
				if (refreshProdListener != null) {
					// 刷新重新取数据
					refreshProdListener.doRefresh(true);
				}
			} else {
				CommonUtil.showToast(CANCLE_COLLECTED_FALIURE, context);
			}
		}

		@Override
		public void onFailure(Throwable error, String content) {
			Log.i(TAG, "onFailure = " + content);
			CommonUtil.showToast(CANCLE_COLLECTED_FALIURE + content, context);
		}
	};

	public AdapterProductList(Context context) {

		super();
		this.context = context;
		prodManger = CoreManager.getInstance().getProduct();
		EventBus.getDefault().register(AdapterProductList.this);
		this.flater = LayoutInflater.from(context);

		/*proTypeLevelFirst = (String[]) context.getResources().getStringArray(R.array.product__type);
		proTypeLevelSecondAffince = (String[]) context.getResources().getStringArray(R.array.product__affiance_type);
		proTypeLevelSecondFund = (String[]) context.getResources().getStringArray(R.array.product__fund_type);*/
		compareProdId = new ArrayList<Long>();

	}

	public void setProductData(List<Product> productList) {
		// if(this.allProductList != null)
		// {
		// this.allProductList.clear();
		// }
		// if(this.tempProductList != null)
		// {
		// this.tempProductList.clear();
		// }
		// if(clickPosition != null && clickPosition.size() > 0)
		// {
		// clickPosition.clear();
		// }
		this.allProductList = productList;
		this.tempProductList = productList;
		if (productList != null && productList.size() > 0) {
			for (int i = 0; i < productList.size(); i++) {
				clickPosition.add(0);
			}
		}
		// Log.i(TAG, "tempProductList" + tempProductList.size());
		notifyDataSetChanged();
	}

	public void setAdapterType(int adapterType) {

		this.currentAdapter = adapterType;
	}

	@Override
	public int getCount() {
		return tempProductList == null ? 0 : tempProductList.size();
	}

	@Override
	public Object getItem(int position) {
		return tempProductList == null ? null : tempProductList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		ViewHolder viewHolder = null;
		if (convertView == null) {

			convertView = (View) flater.inflate(R.layout.row_product_show, null);
			viewHolder = new ViewHolder();

			viewHolder.ivIsSaled = (ImageView) convertView.findViewById(R.id.iv_is_already_sale);
            
			viewHolder.tvFirstType = (TextView) convertView.findViewById(R.id.tv_first_type);
			viewHolder.tvProductName = (TextView) convertView.findViewById(R.id.tv_invest_productname);
			viewHolder.layout = (LinearLayout) convertView.findViewById(R.id.linear_top);
			viewHolder.tvLongProductName = (TextView) convertView.findViewById(R.id.tv_long_productname);
			viewHolder.ivIsRecommand = (ImageView) convertView.findViewById(R.id.iv_is_recommand_invest);
			viewHolder.tvExpectedIncome = (TextView) convertView.findViewById(R.id.tv_ecpected_money);
			viewHolder.tvFixIncome = (TextView) convertView.findViewById(R.id.tv_fix_commision);

			viewHolder.tvStartInvestMoney = (TextView) convertView.findViewById(R.id.tv_start_invest_money);
			viewHolder.tvDeadline = (TextView) convertView.findViewById(R.id.tv_year);
			viewHolder.tvProductCode = (TextView) convertView.findViewById(R.id.tv_product_code);
			viewHolder.tvProductSecondType = (TextView) convertView.findViewById(R.id.tv_product_category);
			viewHolder.tvPublisherTime = (TextView) convertView.findViewById(R.id.tv_publish_time);
			viewHolder.tvPublisher = (TextView) convertView.findViewById(R.id.tv_publisher);
			viewHolder.tvBuyLeftDay = (TextView) convertView.findViewById(R.id.tv_buy_leftday);
			viewHolder.btnDialPhone = (LinearLayout) convertView.findViewById(R.id.btn_phone);

			viewHolder.ltCompProd = (LinearLayout) convertView.findViewById(R.id.lt_prod_compare);
			viewHolder.buyProduct = (Button) convertView.findViewById(R.id.btnBuyProd);
			viewHolder.prodCompare = (Button) convertView.findViewById(R.id.btnCompreProd);
			viewHolder.ltIntentProd = (LinearLayout) convertView.findViewById(R.id.iv_intent_prod);

			viewHolder.moreBtn = (ImageView) convertView.findViewById(R.id.btnShowItemBtn);
			viewHolder.isHOt = (TextView) convertView.findViewById(R.id.tv_is_investhot);
			viewHolder.isProdCollect = (TextView) convertView.findViewById(R.id.tvCollectProd);
			viewHolder.ivIntprodStates = (ImageView) convertView.findViewById(R.id.ivIntprodStates);
			viewHolder.tvAppointShareTitle = (TextView)convertView.findViewById(R.id.tv_appoint_share_title);
			viewHolder.tvAppointShareCount = (TextView)convertView.findViewById(R.id.tv_appoint_share_count);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.tvProductName.setOnClickListener(new View.OnClickListener() {
      
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickPosition.set(position, clickPosition.get(position) == 0 ? 1 : 0);
				notifyDataSetChanged();
			}
		});
		if (clickPosition.get(position) == 0) {
			viewHolder.layout.setVisibility(View.GONE);
		} else if (clickPosition.get(position) == 1) {
			viewHolder.layout.setVisibility(View.VISIBLE);
		}

		final Product productItem = tempProductList.get(position);
		long prodStates = productItem.getProdStatus().longValue();
		if (prodStates != 7) {
			if (currentAdapter == MARKET_INTENT_PRODUCT) {
				viewHolder.tvAppointShareCount.setVisibility(View.GONE);
				viewHolder.tvAppointShareTitle.setVisibility(View.GONE);
				viewHolder.isHOt.setVisibility(View.GONE);
				viewHolder.isProdCollect.setVisibility(View.GONE);
				viewHolder.tvBuyLeftDay.setVisibility(View.GONE);

				viewHolder.moreBtn.setVisibility(View.GONE);
				viewHolder.ltCompProd.setVisibility(View.VISIBLE);
				// 判断当前是否有要比较的产品
				// 恢复可点击的状态
				// 删除和取消意向产品
				TextView tv = (TextView)convertView.findViewById(R.id.tv_fix_commission_1);
				if(productItem.getAppointmentStatus() == null){
					tv.setText("未预约");
					viewHolder.tvFixIncome.setVisibility(View.GONE);
				}else{
					if(productItem.getAppointmentStatus() == 0){//没有预约
						if(productItem.getReservStatus() == 1){//开放状态
							tv.setText("未预约");
						}else if(productItem.getReservStatus() == 2){//关闭状态
							tv.setText("预约已关闭");
						}
						viewHolder.tvFixIncome.setVisibility(View.GONE);
					}else if(productItem.getAppointmentStatus() == 1){//预约确认中
						tv.setText("预约确认中");
						viewHolder.tvFixIncome.setVisibility(View.GONE);
					}else if(productItem.getAppointmentStatus() == 2){//预约已确认
						tv.setText("预约份额：");
						viewHolder.tvFixIncome.setVisibility(View.VISIBLE);
						viewHolder.tvFixIncome.setText((productItem.getDistributionShare() - productItem.getSalesShare()) + "/" + productItem.getDistributionShare() + "万");
					}
				}
				viewHolder.ivIntprodStates.setBackgroundResource(0);// 先删除背景
				if (selec_prod_type == SELE_PROD_ALL_PROD) {
					viewHolder.ivIntprodStates.setBackgroundResource(R.drawable.select_add_intent_prod);
				} else if (selec_prod_type == SELE_PROD_INTENT_PROD) {
					viewHolder.ivIntprodStates.setBackgroundResource(R.drawable.select_cancle_intent_prod);
				}
				viewHolder.prodCompare.setBackgroundResource(R.drawable.shape_5corner_bg);
				viewHolder.prodCompare.setEnabled(true);
				for (Long prodId : compareProdId) {
					Product prod = (Product) getItem(position);
					if (prod != null && prod.getId().longValue() == prodId.longValue()) {
						viewHolder.prodCompare.setBackgroundResource(R.drawable.sp_prod_compare_gray);
						viewHolder.prodCompare.setEnabled(false);
					}
				}
			} else {
				// 产品管理列表
				if(productItem.getAppointmentStatus() == null){
					viewHolder.tvAppointShareTitle.setText("未预约");
					viewHolder.tvAppointShareCount.setVisibility(View.GONE);
				}else{
					if(productItem.getAppointmentStatus() == 0){//没有预约
						if(productItem.getReservStatus() == 1){//开放状态
							viewHolder.tvAppointShareTitle.setText("未预约");
						}else if(productItem.getReservStatus() == 2){//关闭状态
							viewHolder.tvAppointShareTitle.setText("预约已关闭");
						}
						viewHolder.tvAppointShareCount.setVisibility(View.GONE);
					}else if(productItem.getAppointmentStatus() == 1){//预约确认中
						viewHolder.tvAppointShareTitle.setText("预约确认中");
						viewHolder.tvAppointShareCount.setVisibility(View.GONE);
					}else if(productItem.getAppointmentStatus() == 2){//预约已确认
						viewHolder.tvAppointShareTitle.setText("预约份额：");
						viewHolder.tvAppointShareCount.setVisibility(View.VISIBLE);
						viewHolder.tvAppointShareCount.setText((productItem.getDistributionShare() - productItem.getSalesShare()) + "/" + productItem.getDistributionShare() + "万");
					}
				}
				if (productItem.getIsHot() != null) {
					if (productItem.getIsHot() == 1) {
						viewHolder.isHOt.setVisibility(View.VISIBLE);
					} else {
						viewHolder.isHOt.setVisibility(View.GONE);
					}
				} else {
					viewHolder.isHOt.setVisibility(View.GONE);
				}
				viewHolder.moreBtn.setVisibility(View.VISIBLE);
				viewHolder.ltCompProd.setVisibility(View.GONE);
				// TODO 固定佣金
				viewHolder.tvFixIncome.setText(productItem.getProdCommissionBase() + "%");
			}
			if (tempProductList != null && tempProductList.size() > 0) {
				try {
					// 产品是否已售罄

					switch ((int) prodStates) {
					// case PRO_NEW:
					// viewHolder.ivIsSaled.setImageResource(R.drawable.ic_new_product);
					// break;
					// case PRO_CHECK:
					// viewHolder.ivIsSaled.setImageResource(R.drawable.ic_check);
					// break;
					case PRO_ORDER:
						viewHolder.ivIsSaled.setImageResource(R.drawable.ic_appoint);
						break;
					case PRO_ORDER_SALING:
						viewHolder.ivIsSaled.setImageResource(R.drawable.ic_prview_saling);
						break;
					case PRO_SHELVES:
						viewHolder.ivIsSaled.setImageResource(R.drawable.ic_sold_out);
						break;
					case PRO_STATES_ONSOLDING:
						viewHolder.ivIsSaled.setImageResource(R.drawable.icon_onslaeing);
						break;
					case PRO_STATES_SOLDOUT:
						viewHolder.ivIsSaled.setImageResource(R.drawable.ic_sealing_account);
						break;
					default:
						viewHolder.ivIsSaled.setImageResource(R.drawable.ic_new_product);
						break;
					}
					// 收藏图标的显示
					long collected = 0;
					if (productItem.getIsSave() != null) {
						collected = productItem.getIsSave().longValue();
						if (collected == COLLECTED) {
							viewHolder.isProdCollect.setVisibility(View.VISIBLE);
						} else {
							viewHolder.isProdCollect.setVisibility(View.GONE);
						}
					} else {
						viewHolder.isProdCollect.setVisibility(View.GONE);
					}
					// 产品类型
					try {
						// long type1 = productItem.getProdFirstType();
						// long type2 = productItem.getProdSecondtype();
						// StringBuffer sb = new StringBuffer();
						// sb.append((type1 == 1) ?
						// (proTypeLevelSecondFund[(int) (type2 - 1)])
						// : (proTypeLevelSecondAffince[(int) (type2 - 1)]));
						// viewHolder.tvFirstType.setText("["
						// + proTypeLevelFirst[(int) (type1 - 1)] + "]");
						// viewHolder.tvProductSecondType.setText(sb.toString());
						viewHolder.tvFirstType.setText("[" + productItem.getProdFirstTypeStr() + "]");
						viewHolder.tvProductSecondType.setText(productItem.getProdSecondtypeStr());
					} catch (Exception e) {
					}
					String prodName = productItem.getProdName();
					if (!TextUtils.isEmpty(prodName)) {
						viewHolder.tvLongProductName.setText(prodName);
						if (prodName.length() > 10) {
							prodName = prodName.substring(0, 9) + "...";
						}
						String filter = filterName;
						if (TextUtils.isEmpty(filter)) {
							viewHolder.tvProductName.setText(prodName);
						} else {
							viewHolder.tvProductName.setText(Html.fromHtml(prodName.replace(filter,
									"<font color='#e50150'>" + filter + "</font>")));
						}
					}
					// TODO 是否最热 有逻辑显示，收藏次数大于多少时 显示最热,没有收藏次数的字段

					// TODO 发行期
					viewHolder.tvPublisherTime.setText(DateUtil.getDateTimeByFormatAndMs(
							productItem.getProdOnDateTime(), DateUtil.FORMAT_YYYY_MM_DD)
							+ " - "
							+ DateUtil.getDateTimeByFormatAndMs(productItem.getProdEnDateTime(),
									DateUtil.FORMAT_YYYY_MM_DD));
					viewHolder.tvPublisherTime.setText(productItem.getProdOnDateTimeStr() + " - "
							+ productItem.getProdEnDateTimeStr());
					// 距离下架的时间点，距离产品发行日期结束时间的天数// 改了系统时间完蛋了
					if (!TextUtils.isEmpty(productItem.getProdEnDateTimeStr())) {
						int intervalDays = DateUtil.realDateIntervalDay(new Date(), DateUtil.formatString2Date(
								productItem.getProdEnDateTimeStr(), DateUtil.FORMAT_YYYY_MM_DD)) + 1;
						viewHolder.tvBuyLeftDay.setText("购买剩" + intervalDays + "天");
					}

					// TODO 发行商
					viewHolder.tvPublisher.setText(productItem.getProdPreference());
					// viewHolder.tvPublisher.setText(productItem.getProdPublisher());
					// TODO 缺少推荐字段
					try {
						Long isCommand = productItem.getProdTop();
						if (isCommand != null && IS_SERVER_RECOMMAND == isCommand.longValue()) {
							viewHolder.ivIsRecommand.setVisibility(View.VISIBLE);
						} else {
							viewHolder.ivIsRecommand.setVisibility(View.INVISIBLE);
						}
					} catch (Exception e) {
						viewHolder.ivIsRecommand.setVisibility(View.INVISIBLE);
					}

					// TODO 分红 缺少分红

					// TODO 预期收益
					viewHolder.tvExpectedIncome.setText(productItem.getProdYieldFloating() + "%");

					// TODO 起投资近
					viewHolder.tvStartInvestMoney.setText(productItem.getProdStart() + "");

					// TODO 期限
					viewHolder.tvDeadline.setText(productItem.getProdTime().intValue() + "");
					// TODO 产品代码
					viewHolder.tvProductCode.setText(productItem.getProdCode() + "");

				} catch (Exception e) {
					e.getLocalizedMessage();
				}
			}

			viewHolder.ltIntentProd.setOnClickListener(new BtnClick(position));
			// 拨打电话
			viewHolder.btnDialPhone.setOnClickListener(new BtnClick(position));
			// 对比产品
			viewHolder.prodCompare.setOnClickListener(new BtnClick(position));
			viewHolder.buyProduct.setOnClickListener(new BtnClick(position));
			// 产品管理中的更多按钮
			if (productItem.getCheckUsed() == null) {//判断是否使用过佣金券
				viewHolder.moreBtn.setOnClickListener(new BtnClick(position, 0));
			} else {
				viewHolder.moreBtn.setOnClickListener(new BtnClick(position, productItem.getCheckUsed()));
			}
			
			/*写预约判断*/
		}
		return convertView;
	}

	String filterName = null;

	public Filter getFilter() {

		Filter filter = new Filter() {

			@SuppressWarnings("unchecked")
			protected void publishResults(CharSequence constraint, FilterResults results) {

				if (TextUtils.isEmpty(filterName)) {
					tempProductList = allProductList;
					Log.i(TAG, "tempProductList" + tempProductList.size());
					notifyDataSetChanged();
					return;
				}
				tempProductList = (ArrayList<Product>) results.values;
				if (results.count > 0) {
					notifyDataSetChanged();
				} else {
					notifyDataSetInvalidated();
				}
			}

			@SuppressWarnings("unused")
			protected FilterResults performFiltering(CharSequence s) {
				String str = s.toString();
				filterName = str;
				FilterResults results = new FilterResults();
				List<Product> contactList = new ArrayList<Product>();
				if (allProductList != null && allProductList.size() > 0) {
					for (Product cb : allProductList) {

						if (TextUtils.isEmpty(s)) {
							continue;
						}
						if (cb.getProdName().indexOf(str) >= 0) {
							contactList.add(cb);
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

	public static class ViewHolder {

		public ImageView ivIsSaled;//
		public TextView tvProductName;//
		public TextView tvFirstType;
		public ImageView ivIsRecommand;
		public TextView tvExpectedIncome;//
		public TextView tvFixIncome;//
		public TextView tvStartInvestMoney;//
		public TextView tvDeadline;//
		public TextView tvProductCode;//
		public TextView tvProductSecondType;//
		public TextView tvPublisherTime;//
		public TextView tvPublisher;
		public TextView tvBuyLeftDay;
		public LinearLayout btnDialPhone;
		public ImageView moreBtn;
		// 意向 产品里面 一个是对比产品，加为收藏，购买产品
		public LinearLayout ltIntentProd;
		public Button prodCompare;
		public Button buyProduct;
		public LinearLayout ltCompProd;
		public TextView isHOt;
		public TextView isProdCollect;
		ImageView ivIntprodStates;// 是添加意向产品还是取消意向产品
		TextView tvLongProductName;
		LinearLayout layout;
		TextView tvAppointShareTitle;
		TextView tvAppointShareCount;
	}

	// 按钮的点击事件
	private class BtnClick implements OnClickListener {

		private int position = 0;
		private long checkUsed = 0;
		
		/*预约状态判断*/
        private long appoitmentUsed =0;
		public BtnClick(final int position, long checkUsed) {
			super();
			this.position = position;
			this.checkUsed = checkUsed;
		}

		public BtnClick(final int position) {
			super();
			this.position = position;
		}

		@Override
		public void onClick(final View v) {

			switch (v.getId()) {

			case R.id.iv_intent_prod:
				// TODO 判断意向产品和非意向产品，如果是意向产品里面，就要从意向产品中删除该产品，否则添加到意向产品
				// 掉添加意向产品接口
				// Product prod =
				// prodManger.getProdctByProdId(tempProductList.get(position).getId());
				if (selec_prod_type == SELE_PROD_ALL_PROD) {
					addIntentProduct(position, ADD_INTENT_PROD);
				} else {
					addIntentProduct(position, CANCLE_INTENT_PROD);
				}

				break;
			case R.id.btnShowItemBtn:
				/*添加选择按钮*/
				List<Integer> list = new ArrayList<Integer>();
				list.add(R.string.order_share);
				if (checkUsed == 0) {
					list.add(R.string.application_fees);
				} else if (checkUsed == 1) {
					list.add(R.string.application_feed);
				}
				list.add(R.string.sales_live);
				list.add(R.string.gaol_customer);
				list.add(R.string.dividend);
				int collected = 0;
				Product item = tempProductList.get(position);
				if (item.getIsSave() != null) {
					collected = item.getIsSave().intValue();
				}
				if (collected == COLLECTED) {
					list.add(R.string.cancle_collected);
				} else {
					list.add(R.string.collected);
				}

				final PopListWindow popWindow = new PopListWindow(context, v, list,
						R.drawable.popbg_custom_manager_listitem, PopListWindow.RIGHT_2_LEFT);
				popWindow.setCallBackCLickEvent(new PopListWindow.CallBackCLickEvent() {

					@Override
					public void doClick(int posi, Object obj) {
						switch (posi) {
						case 0:
							Window window = ((ActProductManagement)context).getWindow();
							DialogProductAppointShare pop = new DialogProductAppointShare(context,v,window,true);
							pop.setProduct(allProductList.get(position));
							pop.showPopWindow();
							 WindowManager.LayoutParams lp = window.getAttributes();  
					          lp.alpha = 0.5f;  
					          window.setAttributes(lp);
							break;
						case 1:
							Intent in = new Intent();
							if (checkUsed == 0) {
								in.setClass(context, ActPointerCommission.class);
							} else if (checkUsed == 1) {
								in.setClass(context, ActQueryCommission.class);
							}
							in.putExtra("productId", allProductList.get(position).getId()).putExtra("fromClass",
									"manager");
							context.startActivity(in);
							// popWindow.closePopWindow();
							break;
						case 2:
//							CommonUtil.showToast("销售实况 ", context);
							PopProdList prodSale = new PopProdList(context, v, (Product) getItem(position),
									PopProdList.BOTTOM_2_TOP, R.layout.pop_sale_real);
							prodSale.showPopWindow();
							break;
						case 3:
							/*目标客户*/
							popWindow.closePopWindow();
							Intent i = new Intent(context, ActFindTargetCustomer.class);
							i.putExtra(ActFindTargetCustomer.PROD_ACTION, (Product) getItem(position));
							context.startActivity(i);
							break;
						case 4:
							PopProdList prodSMoney = new PopProdList(context, v, (Product) getItem(position),
									PopProdList.BOTTOM_2_TOP, R.layout.pop_prod_share_money);
							prodSMoney.showPopWindow();
//							CommonUtil.showToast("分红 ", context);
							break;
						case 5:
							popWindow.closePopWindow();
							collectProdBtn((Product) getItem(position));
							// CommonUtil.showToast("收藏 ", context);
							break;
						default:
							break;
						}

						popWindow.closePopWindow();
					}
				});
				popWindow.showPopWindow();
				break;
			case R.id.btnBuyProd:
				Product p = (Product) getItem(position);
				checkProductAppointStatus(p,v);//check产品预约状态
				break;
			case R.id.btnCompreProd:
				if (currentAdapter == MARKET_INTENT_PRODUCT) {
					// 产品意向跳转到 产品对比界面
					// 点击产品对比后，产品对比按钮灰掉，
					if (compareProdId.size() >= 2) {
						CommonUtil.showToast("您已经选择了两个产品，请点击比较按钮查看详情", context);
						return;
					}
					if ((Product) getItem(position) == null) {
						CommonUtil.showToast("该产品信息有误，请选择其它产品", context);
						return;
					}
					// Log.i(TAG,((Product)getItem(position)).getId() +
					// " is id");
					prodManger.setListaLLProduct(allProductList);
					compareProdId.add(((Product) getItem(position)).getId());
					if (refreshProdListener != null) {
						refreshProdListener.doRefreshProdCompare();
					}
				}
				break;
			case R.id.btn_phone:
				CommonUtil.dialPhone(context, ((Product) getItem(position)).getProdCstTel());
				break;
			default:
				break;
			}
		}
	}
	//刷新产品状态
	public void onEvent(Product p){
		for(int i = 0;i < allProductList.size();i ++){
			Product product = allProductList.get(i);
			if(product.getId() == p.getId()){
				allProductList.set(i, p);
				break;
			}
		}
		notifyDataSetChanged();
	}
	
	//购买产品
	private void buyProduct(final Product p){
		if (currentAdapter == MARKET_INTENT_PRODUCT) {
			// 产品意向 [ 购买产品 ],调用客户营销的选择产品
			JSONObject params = new JSONObject();
			try {
				// Product prod =
				// prodManger.getProdctByProdId(tempProductList.get(position).getId());
				params.put("salesId", SPManager.getInstance().getLongValue(SPManager.ID));
				params.put("customerId", customerId);
				params.put("prodId", p.getId());

			} catch (JSONException e) {
				e.printStackTrace();
			}
			HttpUtils httpUtils = new HttpUtils(context, RequestDefine.MARKET_RQ_SELECT_PROD, RequestType.POST,
					params);
			httpUtils.setSuccessListener(new SuccessReslut() {

				@Override
				public void getResluts(JSONObject response) {
					if (JsonParse.setMarketStepReslut(response)) {
						CommonUtil.showToast("选择产品成功", context);
						try {
							JSONObject json = response.getJSONObject("item");
							int marketingStatusId = json.getInt("marketingStatusId");
							Intent i = new Intent(context, ActCustomerMarketProgress.class);
							i.putExtra("Position", marketingStatusId-1);// 2或者3
							i.putExtra("product", p);
							context.startActivity(i);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						CommonUtil.showToast("购买产品失败", context);
					}
				}
			});
			httpUtils.executeRequest();
		}
	}
	
	//检查产品预约状态
	private void checkProductAppointStatus(final Product p,final View v){
		JSONObject json = new JSONObject();
		try {
			json.put("prodId",p.getId());
			json.put("salesId", SPManager.getInstance().getLongValue(SPManager.ID));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		HttpUtils httpUtils = new HttpUtils(context,RequestDefine.APPOINT_RQ_PRODUCT_CHECK,RequestType.POST,json);
		httpUtils.setSuccessListener(new SuccessReslut() {
			
			@Override
			public void getResluts(JSONObject response) {
				// TODO Auto-generated method stub
				Log.i("", response.toString());
				try {
					String status = response.getString("status");
					if(status.equals("0")){
						if(!response.isNull("item")){
							buyProduct(p);
						}
					}else if(status.equals("1")){
						if(!response.isNull("item")){
							Gson gson = new Gson();
							rbr = gson.fromJson(response.getJSONObject("item").toString(), RemainBudgetReturn.class);
							if(rbr != null){
								switch(Integer.parseInt(rbr.getCheckStatus())){
								case 1:
								case 2:
								case 3:
								case 4:
									Window window = ((ActProductManagement)context).getWindow();
									DialogProductAppointShare pop = new DialogProductAppointShare(context,v,window,false);
									pop.setProduct(p);
									pop.showPopWindow();
									pop.setRemainBudgetReturn(rbr);
									WindowManager.LayoutParams lp = window.getAttributes();  
							        lp.alpha = 0.5f;  
							        window.setAttributes(lp);
									break;
								}
							}
						}else{
							AppToast.ShowToast("获取已预约份额失败!");
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
				AppToast.ShowToast("获取已预约份额失败!");
			}
		});
		httpUtils.executeRequest();
	}

	// 设置排序方式进行过滤
	public void setSortWays(int id) {
		this.currentSortWay = id;
		Collections.sort(tempProductList, this);
		notifyDataSetChanged();
	}

	@Override
	public int compare(Product pro1, Product pro2) {

		if (pro1 == null || pro2 == null) {
			return -1;
		}
		int sortWay = 0;

		switch (currentSortWay) {
		case DEFALUT_SORT:
			if (pro1.getCreated() < pro2.getCreated()) {
				sortWay = POSITIVE_SORTING;
			} else if (pro1.getCreated() > pro2.getCreated()) {
				sortWay = REVERSE_SORTING;
			}
			break;
		case HOT:
			if (pro1.getHotPoint() == null || pro2.getHotPoint() == null) {
				return 0;
			}
			if (pro1.getHotPoint() < pro2.getHotPoint()) {
				sortWay = POSITIVE_SORTING;
			} else if (pro1.getHotPoint() > pro2.getHotPoint()) {
				sortWay = REVERSE_SORTING;
			}
			break;
		case PUBLISH_TINME:
			if (pro1.getProdOnDateTime() < pro2.getProdOnDateTime()) {
				sortWay = POSITIVE_SORTING;
			} else if (pro1.getProdOnDateTime() > pro2.getProdOnDateTime()) {
				sortWay = REVERSE_SORTING;
			}
			break;
		case EXPECTED_INCOME:
			if (pro1.getProdYieldFloating() < pro2.getProdYieldFloating()) {

				sortWay = POSITIVE_SORTING;
			} else if (pro1.getProdYieldFloating() > pro2.getProdYieldFloating()) {
				sortWay = REVERSE_SORTING;
			}
			break;
		case COMMISION:
			if (pro1.getProdCommissionBase() < pro2.getProdCommissionBase()) {
				sortWay = POSITIVE_SORTING;
			} else if (pro1.getProdCommissionBase() > pro2.getProdCommissionBase()) {
				sortWay = REVERSE_SORTING;
			}
			break;
		case DEADLINE:
			if (pro1.getProdTime() > pro2.getProdTime()) {

				sortWay = POSITIVE_SORTING;
			} else if (pro1.getProdTime() < pro2.getProdTime()) {
				sortWay = REVERSE_SORTING;
			}
			break;
		case SCALE:
			if (pro1.getProdSize() < pro2.getProdSize()) {
				sortWay = POSITIVE_SORTING;
			} else if (pro1.getProdSize() > pro2.getProdSize()) {
				sortWay = REVERSE_SORTING;
			}
			break;
		default:
			if (allProductList != null) {
				tempProductList = allProductList;
				Log.i(TAG, "tempProductList" + tempProductList.size());
			}
			break;
		}
		return sortWay;
	}

	private RefreshProdData refreshProdListener;

	public interface RefreshProdData {

		// 刷新产品列表
		public void doRefresh(boolean b);//change by SGDY for BUG#5305 at 2014/8/13 13:21

		// 刷新 产品比较列表
		public void doRefreshProdCompare();
		
		//修改意向产品List
		public void resresh4IntentProduct(Product product,boolean isAdd);
	}

	public void setRefreshProdListener(RefreshProdData refreshProdListener) {
		this.refreshProdListener = refreshProdListener;
	}

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
				request = new Request(RequestDefine.RQ_PRODUCT_COLLECTION, productItem.getId(), RequestType.POST, json,
						handlerCancleCollRefreshProduct);
			} else {
				request = new Request(RequestDefine.RQ_PRODUCT_COLLECTION, productItem.getId(), RequestType.POST, json,
						handlerCollRefreshProduct);
			}
			CoreManager.getInstance().postRequest(request);
		} catch (Exception e) {
		}
	}

	public void refreshSelecCompProd(Long key) {
		compareProdId.remove(key);
		if (refreshProdListener != null) {
			refreshProdListener.doRefreshProdCompare();
		}
		notifyDataSetChanged();
	}

	public void setSortMethodsByType(int sortWays) {
		this.currentSortWay = sortWays;
		Collections.sort(tempProductList, AdapterProductList.this);
		notifyDataSetChanged();
	}

	public void destroyAdapter() {
		if (compareProdId != null) {
			compareProdId.clear();
			notifyDataSetChanged();
		}
	}

/*	private HttpUtils httpUtils = null;

	private void initHttpUtils(MarketStateReslut result) {

		JSONObject params = new JSONObject();
		try {
			params.put("salesId", result.getSalesId());
			params.put("customerId", result.getCustomerId());
			httpUtils = new HttpUtils(context, RequestDefine.MARKET_RQ_QUERY_BIND_PROTECAL, RequestType.POST, params);
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
						obj = response.getJSONObject("item");
						System.out.println("绑定协议iD:" + String.valueOf(obj.optLong("id")));
						Intent i = new Intent(context, ActCustomerMarketProgress.class);
						if (Constant.CUSTOMER_ALREADY_BIND.equals(obj.optString("status"))) {
							// 跳转到合同签订； 之前的只能预览了
							i.putExtra("Position", ActCustomerMarketProgress.F_SIGN_FIRST);// 更改
						} else {
							// 跳转到绑定协议
							i.putExtra("Position", ActCustomerMarketProgress.F_BIND);
						}
						context.startActivity(i);
						((Activity) context).finish();
					} else {
						CommonUtil.showToast("购买产品失败", context);
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		httpUtils.executeRequest();

	}*/

	private static final int ADD_INTENT_PROD = 1;
	private static final int CANCLE_INTENT_PROD = 2;

	private void addIntentProduct(int position, final int currentType) {

		// http://localhost:8080/app/prod/getProdInvestMentByCustom
		// {
		// "salesId":1,
		// "customId":1,
		// "prodId":1
		// }
		final Product product = (Product) getItem(position);
		JSONObject params = new JSONObject();
		try {
			params.put("salesId", SPManager.getInstance().getLongValue(SPManager.ID));
			params.put("customId", customerId);
			params.put("prodId", product.getId());
		} catch (JSONException e) {
			e.printStackTrace();
			return;
		}
		int id = 0;
		switch (currentType) {
		case ADD_INTENT_PROD:
			id = RequestDefine.ADD_INTENT_PRODUCT;
			break;
		case CANCLE_INTENT_PROD:
			id = RequestDefine.CANCLE_INTENT_PRODUCT;
			break;
		default:
			break;
		}
		Log.i(TAG, id + " " + RequestDefine.RQ_LOGIN);
		HttpUtils httpUtils = new HttpUtils(context, id, RequestType.POST, params);
		httpUtils.setShowDiloag(true);
		httpUtils.setSuccessListener(new SuccessReslut() {

			@Override
			public void getResluts(JSONObject response) {

				if (Request.RESLUT_OK.equals(response.optString("status"))) {
					switch (currentType) {
					case ADD_INTENT_PROD:
						if (refreshProdListener != null) {
							// 刷新重新取数据
							refreshProdListener.resresh4IntentProduct(product, true);
						}
						CommonUtil.showToast("添加意向产品成功", context);
						break;
					case CANCLE_INTENT_PROD:
						if (refreshProdListener != null) {
							// 刷新重新取数据
							refreshProdListener.resresh4IntentProduct(product, false);
						}
						CommonUtil.showToast("取消意向产品成功", context);
						break;
					default:
						break;
					}
					if (refreshProdListener != null) {
						// 刷新重新取数据
						refreshProdListener.doRefresh(false);
					}
				} else {
					if ("1".equals(response.optString("status"))) {
						switch (currentType) {
						case ADD_INTENT_PROD:
							CommonUtil.showToast("该产品已是您的意向产品，不能再次添加", context);
							break;
						default:
							break;
						}
					}
				}
			}
		});
		httpUtils.setFaliureResult(new FaliureResult() {

			@Override
			public void getResluts(String msg) {
				switch (currentType) {
				case ADD_INTENT_PROD:
					CommonUtil.showToast("添加意向产品失败", context);
					break;
				case CANCLE_INTENT_PROD:
					CommonUtil.showToast("取消意向产品失败", context);
					break;
				default:
					break;
				}
			}
		});
		httpUtils.executeRequest();

	}

}
