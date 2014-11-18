package com.incito.finshine.activity.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.core.util.BitmapUtil;
import com.android.core.util.StrUtil;
import com.custom.view.CommonPopWindow;
import com.custom.view.CommonPopWindow.CallBackCLickEvent;
import com.incito.finshine.R;
import com.incito.finshine.activity.ActCustomerMarketCollectBaseInfo;
import com.incito.finshine.activity.ActCustomerMarketOrderManager;
import com.incito.finshine.activity.ActCustomerMarketProgress;
import com.incito.finshine.activity.ActProductManagement;
import com.incito.finshine.common.Constant;
import com.incito.finshine.entity.MarKetCustomer;
import com.incito.finshine.entity.MarketCsOrder;
import com.incito.finshine.manager.CoreManager;
import com.incito.finshine.network.Request;
import com.incito.finshine.network.RequestDefine;
import com.incito.finshine.network.Request.RequestType;
import com.incito.utility.CommonUtil;
import com.incito.wisdomsdk.net.http.BinaryHttpResponseHandler;

/**
 * <dl>
 * <dt>AdapterCustomerMarketing.java</dt>
 * <dd>Description:客户营销列表首页适配器</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 昆山灵动信息技术有限公司</dd>
 * <dd>CreateDate: 2014-6-17 下午11:09:41</dd>
 * </dl>
 * 
 * @author lihs
 */
public class AdapterCustomerMarketing extends BaseExpandableListAdapter {

	private Context mContext;
	private Intent i;
	private LayoutInflater mInflater;
	private String filterName;
	private Filter filter;

	private List<MarKetCustomer> marCs;
	private List<MarKetCustomer> marCs1;//备用集合
	private List<Integer> clickPosition = new ArrayList<Integer>();//客户姓名点击集合
	private List<Integer> clickProductGroup = new ArrayList<Integer>();//产品组点击集合
	private Map<Integer,List<Integer>> clickProductItem = new HashMap<Integer,List<Integer>>();//产品子组点击集合
	private final String TAG = AdapterCustomerDetail.class.getSimpleName();
	public AdapterCustomerMarketing(Context context) {
		super();
		this.mContext = context;
		this.mInflater = LayoutInflater.from(context);
	}

	public void click(List<MarKetCustomer> marCs)
	{
		if(marCs != null && marCs.size() > 0)
		{
			for(int i = 0;i < marCs.size();i ++)
			{
				/*
				 * 客户姓名集合中所有值赋值为0，当点击某个位置的姓名时，把这个位置的值设为1，刷新Adapter，如果值为1，显示长的名字，如果为0，长的名字隐藏
				 * 产品组同上
				 * 遍历营销中订单集合，如果有订单，则将对应的groupPosition作为key，将内容均为0的集合做为value，点击某个子组产品名，通过groupPosition和childPosition
				 * 将其中的值至为0或1，然后判断是否是现实或者隐藏
				 */
				clickPosition.add(0);
				clickProductGroup.add(0);
				if(marCs.get(i).getMaCsorder() != null && marCs.get(i).getMaCsorder().size() > 0)
				{
					for(int j = 0;j < marCs.get(i).getMaCsorder().size();j ++)
					{
						List<Integer> childs = new ArrayList<Integer>();
						if(clickProductItem.containsKey(i))
						{
							childs = clickProductItem.get(i);
						}
						childs.add(0);
						clickProductItem.put(i, childs);
					}
				}
			}
		}
	}
	
	public void setMarketCsList(List<MarKetCustomer> marCs) {
		this.marCs = marCs;
		this.marCs1 = marCs;//备用筛选集合赋值
		click(marCs);
		notifyDataSetChanged();
	}

	@Override
	public int getGroupCount() {
		return marCs == null ? 0 : marCs.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {

		int count = 0;
		List<MarketCsOrder> childSize = null;
		if (marCs != null && marCs.size() > 0) {
			childSize = marCs.get(groupPosition).getMaCsorder();
			if (childSize != null && childSize.size() > 0) {
				count = childSize.size();
			}
		}
		if (count < 1) {
			count = 0;
		} else {
			// >= 1
			count--;
		}
		return count;
	}

	private MarketCsOrder getOrderMarket(int groupPosition) {

		MarketCsOrder cs = null;
		int count = 0;
		List<MarketCsOrder> childSize = null;
		if (marCs != null && marCs.size() > 0) {
			childSize = marCs.get(groupPosition).getMaCsorder();
			if (childSize != null && childSize.size() > 0) {
				count = childSize.size();
			}
		}
		if (count >= 1) {
			cs = childSize.get(count - 1);
		}
		return cs;
	}

	private boolean getOrderMarketOrder(int groupPosition) {

		int count = 0;
		List<MarketCsOrder> childSize = null;
		if (marCs != null && marCs.size() > 0) {
			childSize = marCs.get(groupPosition).getMaCsorder();
			if (childSize != null && childSize.size() > 0) {
				count = childSize.size();
			}
		}
		if (count > 1) {
			return true;
		}
		return false;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return marCs == null ? 0 : marCs.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {

		List<MarketCsOrder> childSize = null;
		MarketCsOrder childData = null;
		if (marCs != null && marCs.size() > 0) {
			childSize = marCs.get(groupPosition).getMaCsorder();
			if (childSize != null && childSize.size() > 0) {
				childData = childSize.get(childPosition);
			}
		}
		return childData;
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public View getGroupView(final int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(
					R.layout.row_customer_market_expandable_group, null);
			holder = new ViewHolder();

			holder.marketIcon= (ImageView) convertView.findViewById(R.id.marketIcon);
			holder.dotLine = (LinearLayout) convertView
					.findViewById(R.id.linearDot);
			holder.imageMore = (ImageView) convertView
					.findViewById(R.id.btnMore);
			holder.txtCollectInfo = (TextView) convertView
					.findViewById(R.id.txtCollectInfo);
			holder.txtHistoricOrder = (TextView) convertView
					.findViewById(R.id.txtHistoricOrder);
			holder.btnChooseProduct = (Button) convertView
					.findViewById(R.id.btnChooseProduct);
			holder.ltOrder = (LinearLayout) convertView
					.findViewById(R.id.ltOrder);
			holder.tvCsName = (TextView) convertView
					.findViewById(R.id.tvCsName);
			holder.tvCsLongName = (TextView)convertView.findViewById(R.id.tvCsLongName);
			holder.bindCs = (ImageView) convertView.findViewById(R.id.ivBind);

			convertView.setTag(holder);
		}

		else {
			holder = (ViewHolder) convertView.getTag();
		}
		int poi = groupPosition%4;
		switch (poi) {
		case 0:
			holder.marketIcon.setImageResource(R.drawable.headicon1);
			break;
		case 1:
			holder.marketIcon.setImageResource(R.drawable.headicon2);
			break;
		case 2:
			holder.marketIcon.setImageResource(R.drawable.headicon3);
			break;
		default:
			holder.marketIcon.setImageResource(R.drawable.headicon4);
			break;
		}

		// 是否显示第一条数据，显示的是最后一条数据; 这里第一条显示的是最后一条数据，其它是第一到最后-1
		MarketCsOrder csOrder = getOrderMarket(groupPosition);
		// 判断分组是否展开，分别传入不同的图片资源
		if (isExpanded) {
			holder.imageMore.setImageResource(R.drawable.ic_pack);
		} else {
			holder.imageMore.setImageResource(R.drawable.ic_cbi_more);
		}
		if (getOrderMarketOrder(groupPosition)) {//判断是否有订单  如果没有 则不显示扩展图片
			holder.imageMore.setVisibility(View.VISIBLE);
		} else {
			holder.imageMore.setVisibility(View.GONE);
		}
		initStep(csOrder, convertView,groupPosition,true,0);
		if (csOrder == null) {//当订单为空时，该订单所占区域留白
			holder.ltOrder.setVisibility(View.INVISIBLE);
		} else {
			holder.ltOrder.setVisibility(View.VISIBLE);
		}

		final MarKetCustomer item = (MarKetCustomer) getGroup(groupPosition);
		if (item != null) {
			if (TextUtils.isEmpty(filterName))
			{
				if(item.getName().length() > 5)//判断客户名称是否大于5个字符，大于的话，显示5个字符，其他字符以...代替
				{
					holder.tvCsName.setText(item.getName().substring(0, 5) + "...");
				}
				else
				{
					holder.tvCsName.setText(item.getName());
				}
			}
			else
			{ 
				if(item.getName().length() > 5)//判断客户名称是否大于5个字符，大于的话，显示5个字符，其他字符以...代替
				{
					String str = item.getName().substring(0, 5);
					holder.tvCsName.setText(str.contains(filterName) ? Html.fromHtml(str.replace(filterName, "<font color='#e50150'>"
							+ filterName + "</font>") + "...") : (str + "..."));
				}
				else
				{
					holder.tvCsName.setText(Html.fromHtml(item.getName().replace(filterName, "<font color='#e50150'>"
							+ filterName + "</font>")));
				}
			}
			holder.tvCsLongName.setText(item.getName());  
			holder.tvCsName.setOnClickListener(new View.OnClickListener() {//客户名称监听事件，点击显示全部名称。
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					/*clickPosition.set(groupPosition, clickPosition.get(groupPosition) == 0 ? 1 : 0);
					notifyDataSetChanged();*///暂停此功能
				}
			});
			if(clickPosition.get(groupPosition) == 0)//判断 ： 客户名称未点击，不显示全部名称
			{
				holder.tvCsLongName.setVisibility(View.INVISIBLE);
			}else if(clickPosition.get(groupPosition) == 1)//判断 ： 客户名称已点击，显示全部名称
			{
				holder.tvCsLongName.setVisibility(View.VISIBLE);
			}
			long bindStates = item.getBindingStatusId();//判断是否绑定理财师
			if (bindStates == MarKetCustomer.HAS_ALREADY) {
				holder.bindCs.setVisibility(View.VISIBLE);
			} else {
				holder.bindCs.setVisibility(View.GONE);
			}
			
		}

		holder.dotLine.setLayerType(View.LAYER_TYPE_SOFTWARE, null);// 4.0上面虚线变实线
		// 采集信息
		holder.txtCollectInfo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i.putExtra(Constant.CUSTOMER_ID, ((MarKetCustomer) getGroup(groupPosition)).getId());
				i.setClass(mContext, ActCustomerMarketCollectBaseInfo.class);
				mContext.startActivity(i);
			}
		});
		// 历史订单
		holder.txtHistoricOrder.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent i = new Intent();
				i.putExtra(Constant.ACTION_TO_ORDER_MANAGER, ((MarKetCustomer) getGroup(groupPosition)));
				i.setClass(mContext, ActCustomerMarketOrderManager.class);
				mContext.startActivity(i);
			}
		});

		// 选择产品
		holder.btnChooseProduct.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i.putExtra(ActProductManagement.ACTION_FROM_MARKET_CSID,((MarKetCustomer) getGroup(groupPosition)).getId());
				i.putExtra(ActProductManagement.ACTION_INTENT_PRODUCT, 0);
				i.setClass(mContext, ActProductManagement.class);
				mContext.startActivity(i);
			}
		});

		// 点击客户图像，弹出电话、短信popwindow
		holder.marketIcon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				final List<Integer> list = new ArrayList<Integer>();
				list.add(R.drawable.customer_manager_email);
				list.add(R.drawable.customer_manager_tel);
				list.add(R.drawable.customer_manager_sms);
				CommonPopWindow popWindow = new CommonPopWindow(mContext, v,list, R.drawable.popbg_custom_manager_content,
						CommonPopWindow.LEFT_2_RIGHT);
				// 回调函数
				popWindow.setCallBackCLickEvent(new CallBackCLickEvent() {

					@Override
					public void doClick(int position, Object obj) {
						switch (position) {
						case 0:
							Toast.makeText(mContext, "发邮件", Toast.LENGTH_SHORT).show();
							break;
						case 1:
							// Toast.makeText(mContext, "打电话",
							// Toast.LENGTH_SHORT);
							CommonUtil.dialPhone(mContext,
									((MarKetCustomer) getGroup(groupPosition))
											.getCellPhone1());
							break;
						case 2:
							CommonUtil.sendSms(mContext,((MarKetCustomer) getGroup(groupPosition)).getCellPhone1());
							// Toast.makeText(mContext, "发信息",
							// Toast.LENGTH_SHORT);
							break;

						default:
							break;
						}
					}
				});
				popWindow.showPopWindow();
			}
		});
		sendMessage(new BinaryHttpResponseHandler(holder.marketIcon){
			ImageView icon  = holder.marketIcon;
			@Override
			public void onSuccess(byte[] binaryData) {
				super.onSuccess(binaryData);
				Bitmap bitmap = BitmapUtil.Bytes2Bimap(binaryData);
				if (bitmap != null) {
					icon.setImageBitmap(bitmap);
				} else{
					icon.setImageResource(R.drawable.headicon1);
				}
			}
			
			@Override
			public void onFailure(Throwable error,String content){
				Log.i(TAG, content);
				icon.setImageResource(R.drawable.ts_avatar2);
			}
		},item.getId());
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View view, ViewGroup parent) {

		if (view == null) {
			view = mInflater.inflate(
					R.layout.row_customer_market_expandable_child, null);
		}

		initStep((MarketCsOrder) getChild(groupPosition, childPosition), view,groupPosition,false,childPosition);
		return view;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	private void initStep(MarketCsOrder order, View view,final int groupPosition,boolean isGroupView,final int childPosition) {
		if (order == null) {
			return;
		}
		TextView prodName = (TextView) view.findViewById(R.id.tvProdName);
		TextView longProdName = (TextView) view.findViewById(R.id.tvLongProdName);
		TextView prodCode = (TextView) view.findViewById(R.id.tvProdCode);
		if(StrUtil.isNotBlank(order.getProdName()))
		{
			prodName.setText(order.getProdName().length() > 10 ? order.getProdName().substring(0, 9) + "..." : order.getProdName());
			longProdName.setText(order.getProdName());
		} 
		prodCode.setText(String.valueOf(order.getSalesOrderNo()));
		if(isGroupView)
		{
			prodName.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					/*clickProductGroup.set(groupPosition, clickProductGroup.get(groupPosition) == 0 ? 1 : 0);
					notifyDataSetChanged();*///暂停此功能
				}
			});
			if(clickProductGroup.get(groupPosition) == 0)//判断 ： groupView产品名称未点击，不显示全部名称
			{
				longProdName.setVisibility(View.INVISIBLE);
			}else if(clickProductGroup.get(groupPosition) == 1)//判断 ：  groupView产品名称已点击，显示全部名称
			{
				longProdName.setVisibility(View.VISIBLE);
			}
		}
		else
		{
			prodName.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					/*clickProductItem.get(groupPosition).set(childPosition, clickProductItem.get(groupPosition).get(childPosition) == 0 ? 1 : 0);
					notifyDataSetChanged();*///暂停此功能
				}
			});
			if(clickProductItem.containsKey(groupPosition))
			{	
				if(clickProductItem.get(groupPosition).get(childPosition) == 0)//判断 ：  childView产品名称未点击，不显示全部名称
				{
					longProdName.setVisibility(View.INVISIBLE);
				}else if(clickProductItem.get(groupPosition).get(childPosition) == 1)//判断 ： childView产品名称已点击，显示全部名称
				{
					longProdName.setVisibility(View.VISIBLE);
				}
			}
		}
		long marketStates = order.getMarketingStatusId();
		int marStates = (int) marketStates;
		marStates = marStates - 1;
		for (int i = 0; i < 5; i++) {
			Button stepBtn = (Button) view
					.findViewById(R.id.btnStepChooseProduct + i);
			if (i <= marStates) {
				stepBtn.setOnClickListener(new SeleOrderClick(order,groupPosition));
			}
			if (i < marStates) {
				stepBtn.setBackgroundResource(R.drawable.market_progress1);//正在进行中的某一步时，按钮字体颜色变为白色，按钮图片恢复默认图片
				stepBtn.setTextColor(mContext.getResources().getColor(
						R.color.white));
			} else if (i == marStates) {
				stepBtn.setBackgroundResource(R.drawable.market_progress2);//正在进行中的某一步时，按钮字体颜色变为白色，按钮图片改变
				stepBtn.setTextColor(mContext.getResources().getColor(
						R.color.white));
			} else {
				stepBtn.setBackgroundResource(R.drawable.market_progress1);//大于进行中的某一步时，按钮字体颜色变为灰色，按钮图片恢复默认图片
				stepBtn.setTextColor(mContext.getResources().getColor(
						R.color.text_gray_808080));
			}
		}

		for (int i = 0; i < 5; i++) {
			TextView stepTxt = (TextView) view
					.findViewById(R.id.txtStepChooseProduct + i);
			if (i <= marStates) {
				stepTxt.setOnClickListener(new SeleOrderClick(order,groupPosition));
			}
			if (i < marStates) {
				// 查看详情
				stepTxt.setTextColor(mContext.getResources().getColor(R.color.text_brown_d7c093));
			} else if (i == marStates) {
				stepTxt.setTextColor(mContext.getResources().getColor(R.color.text_orange_ff8b23));
			} else {
				stepTxt.setTextColor(mContext.getResources().getColor(R.color.text_gray_808080));
			}
		}
	}

	// 传递到其它界面的数据对象
	public static final String ACTION_SELE_ORDER = "action_sele_order";
	public static final String ACTION_SELE_NAME = "action_sele_name";

	private class SeleOrderClick implements OnClickListener {

		private MarketCsOrder maOrder;
		private int position = 0;
		
		public SeleOrderClick(final MarketCsOrder maCs,int position) {
			super();
			this.maOrder = maCs;
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			
			i = new Intent();
			maOrder.setCustomerName(marCs.get(position).getName());
			i.putExtra(ACTION_SELE_ORDER, maOrder);
			Log.i("", "product is null really ? " + (maOrder.getProd() == null) + "-----------------------");
			i.setClass(mContext, ActCustomerMarketProgress.class);
			switch (v.getId()) {
			case R.id.btnStepChooseProduct:
			case R.id.txtStepChooseProduct:
				i.putExtra("Position", 0);
				mContext.startActivity(i);
				break;
			case R.id.btnStepBind:
			case R.id.txtStepBind:
				i.putExtra("Position", 1);
				mContext.startActivity(i);
				break;
			case R.id.btnStepSignContract:
			case R.id.txtStepSignContract:
				i.putExtra("Position", 2);
				mContext.startActivity(i);
				break;
			case R.id.btnStepCreateOrder:
			case R.id.txtStepCreateOrder:
				i.putExtra("Position", 8);
				mContext.startActivity(i);
				break;
			case R.id.btnStepPayResult:
			case R.id.txtStepPayResult:
				i.putExtra("Position", 9);
				mContext.startActivity(i);
				break;
			default:
				break;
			}

		}
	}

	static class ViewHolder {

		ImageView marketIcon;
		LinearLayout dotLine;
		ImageView imageMore;
		TextView txtCollectInfo;
		TextView txtHistoricOrder;
		Button btnChooseProduct;
		ImageView imageAvatar;
		LinearLayout ltOrder;
		TextView tvCsName;
		ImageView bindCs;
		TextView tvCsLongName;
	}
	
	
	/*---------------    根据客户姓名搜索过滤       ---------------*/
	public Filter getFilter()
	{
		
		if(filter == null)
		{
			filter = new MyFilter();
		}
		return filter;
	}
	
	private class MyFilter extends Filter
	{

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			// TODO Auto-generated method stub
			constraint = constraint.toString().toLowerCase();
			filterName = constraint.toString();
			FilterResults results = new FilterResults();
			List<MarKetCustomer> lists = new ArrayList<MarKetCustomer>();
			if(constraint != null && constraint.toString().length() > 0 && !marCs.isEmpty())
			{
				for(MarKetCustomer mc : marCs)
				{
					if(mc.getName().toLowerCase().contains(constraint))
					{
						lists.add(mc);
					}
				}
			}
			results.count = lists.size();
			results.values = lists;
			return results;
		}

		@Override
		protected void publishResults(CharSequence constraint, FilterResults results) {
			// TODO Auto-generated method stub
			if(TextUtils.isEmpty(constraint))
			{
				marCs = marCs1;
				notifyDataSetChanged();
				return;
			}
			marCs = (ArrayList<MarKetCustomer>)results.values;
			click(marCs);
			if(results.count > 0)
			{
				notifyDataSetChanged();
			}
			else
			{
				notifyDataSetInvalidated();
			}
		}
	}
	
	/* 网络请求 */
	public void sendMessage(BinaryHttpResponseHandler binaryHttpResponseHandler,long customerId ) {
		Request request = new Request(RequestDefine.MARKET_RQ_DOWNLOAD_PHOTO, customerId, RequestType.GET, null,
				binaryHttpResponseHandler);
		request.setDownLoadFile(true);
		CoreManager.getInstance().postRequest(request);
	}
}
