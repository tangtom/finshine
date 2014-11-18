package com.custom.view;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.incito.finshine.R;
import com.incito.wisdomsdk.event.EventBus;

/**
 * <dl>
 * <dt>DlgCitySelected.java</dt>
 * <dd>Description:城市选择对话框、投资偏好</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 昆山灵动信息技术有限公司</dd>
 * <dd>CreateDate: 2014-6-3 下午1:07:48</dd>
 * </dl>
 * 
 * @author lihs
 */
@SuppressLint("UseSparseArrays")
public class DlgCitySelected implements OnItemClickListener {

	private Context cx;
	private Dialog dialog;
	private View cityView;

	private ListView provienceList;
	private ListView cityList;

	private String[] provStr;
	private String[] cityStr;

	private ProvienceAdapter proAdapter = null;
	private CityAdapter cityAdapter = null;

	private Map<Integer, Boolean> proMap;
	private Map<Integer, Boolean> cityMap;

	private int pro_position = 0;
	private int city_position = 0;

	private static final String PROVIENCE_TAG = "listview_provience";

	public static final int CATEGORY_SEL_CITY = 1;
	public static final int CATEGORY_SEL_INVEST_HOBBY = 2;

	private int categoryType = CATEGORY_SEL_CITY;

	private String title = "";

	private Map<Integer, Integer> proVsCity;

	private Map<Integer, Integer> getData(int type) {// 二级

		proVsCity = new HashMap<Integer, Integer>();
		if (categoryType == CATEGORY_SEL_INVEST_HOBBY) {
			proVsCity.put(0, R.array.all_provience);
			proVsCity.put(1, R.array.investment2_status);
			proVsCity.put(2, R.array.investment2_status);
		} else {

			proVsCity.put(0, R.array.all_provience);
			proVsCity.put(1, R.array.beijin_province_item);
			proVsCity.put(2, R.array.tianjin_province_item);

			proVsCity.put(3, R.array.heibei_province_item);
			proVsCity.put(4, R.array.shanxi1_province_item);
			proVsCity.put(5, R.array.neimenggu_province_item);
			proVsCity.put(6, R.array.liaoning_province_item);
			proVsCity.put(7, R.array.jilin_province_item);
			proVsCity.put(8, R.array.heilongjiang_province_item);
			proVsCity.put(9, R.array.shanghai_province_item);
			proVsCity.put(10, R.array.jiangsu_province_item);
			proVsCity.put(11, R.array.zhejiang_province_item);
			proVsCity.put(12, R.array.anhui_province_item);
			proVsCity.put(13, R.array.fujian_province_item);

			proVsCity.put(14, R.array.jiangxi_province_item);
			proVsCity.put(15, R.array.shandong_province_item);
			proVsCity.put(16, R.array.henan_province_item);

			proVsCity.put(17, R.array.hubei_province_item);
			proVsCity.put(18, R.array.hunan_province_item);
			proVsCity.put(19, R.array.guangdong_province_item);
			proVsCity.put(20, R.array.guangxi_province_item);

			proVsCity.put(21, R.array.hainan_province_item);
			proVsCity.put(22, R.array.chongqing_province_item);
			proVsCity.put(23, R.array.sichuan_province_item);
			proVsCity.put(24, R.array.guizhou_province_item);

			proVsCity.put(25, R.array.yunnan_province_item);
			proVsCity.put(26, R.array.xizang_province_item);
			proVsCity.put(27, R.array.shanxi2_province_item);

			proVsCity.put(28, R.array.gansu_province_item);
			proVsCity.put(29, R.array.qinghai_province_item);
			proVsCity.put(30, R.array.linxia_province_item);

			proVsCity.put(31, R.array.xinjiang_province_item);
			proVsCity.put(32, R.array.hongkong_province_item);
			proVsCity.put(33, R.array.aomen_province_item);
			proVsCity.put(34, R.array.taiwan_province_item);
		}
		return proVsCity;
	}

	public DlgCitySelected(Context cx, String cityName) {

		super();
		this.cx = cx;
		dialog = new Dialog(cx, R.style.CustomProgressDialog);
		cityView = LayoutInflater.from(cx).inflate(R.layout.dlg_city_seleted,
				null);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		dialog.addContentView(cityView, params);
		dialog.setCanceledOnTouchOutside(true);

		proMap = new HashMap<Integer, Boolean>();
		cityMap = new HashMap<Integer, Boolean>();

		initData(cityName);

		init();

	}

	public DlgCitySelected(Context cx, String cityName, int categoryType,
			String title) {

		super();
		this.cx = cx;

		this.categoryType = categoryType;
		this.title = title;
 		dialog = new Dialog(cx, R.style.CustomProgressDialog);
		cityView = LayoutInflater.from(cx).inflate(R.layout.dlg_city_seleted,
				null);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		dialog.addContentView(cityView, params);
		dialog.setCanceledOnTouchOutside(true);

		proMap = new HashMap<Integer, Boolean>();
		cityMap = new HashMap<Integer, Boolean>();

		initData(cityName);

		init();

	}

	private void initData(String cityName) {

		((TextView) cityView.findViewById(R.id.tv_invest_hobby)).setText(title);
		if (categoryType == CATEGORY_SEL_INVEST_HOBBY) {
			// 资管-信托 一级
			provStr = cx.getResources().getStringArray(
					R.array.investment1_status);

		} else {
			provStr = cx.getResources().getStringArray(R.array.province_item);
		}

		proVsCity = getData(categoryType);// 二级
		if (provStr != null) {
			for (int i = 0; i < provStr.length; i++) {
				proMap.put(i, false);
			}
		}
		if (TextUtils.isEmpty(cityName)) {
			cityStr = cx.getResources().getStringArray(R.array.all_provience);
			proMap.put(0, true);// 默认全部
		} else {
			String tempSel[] = null;
			if (categoryType == CATEGORY_SEL_INVEST_HOBBY) {
				// 资管-信托
				
				String proName = "";
				if (cityName.contains("-")) {
					tempSel = cityName.split("\\-");
					if (tempSel != null && tempSel.length == 2) {
						proName = tempSel[0];
						cityName = tempSel[1];
					}
				}
				else
				{
					proName = "全部";
					cityName = "";
				}
				
				int size = proVsCity.size();
				for (int k = 0; k < size; k++) {
//					pro_position = 0;
					
					if (provStr[k].equals(proName))
					{
						pro_position = k;
						proMap.put(pro_position, true);
						String[] tempCity = cx.getResources().getStringArray(
								proVsCity.get(pro_position));
						for (int j = 0; j < tempCity.length; j++) {
							if (tempCity[j].equals(cityName)) {
								city_position = j;
								cityStr = tempCity;
								break;
							}
						}
					} else {
						proMap.put(k, false);
					}
				}
			}

			else {
				int size = proVsCity.size();
				for (int k = 0; k < size; k++) {
					pro_position = 0;
					if ("全部".equals(cityName)) {
						if (k == 0) {
							proMap.put(k, true);
						} else {
							proMap.put(k, false);
						}
					} else {
						pro_position = k;
						String[] tempCity = cx.getResources().getStringArray(
								proVsCity.get(k));
						for (int j = 0; j < tempCity.length; j++) {
							if (tempCity[j].equals(cityName)) {
								city_position = j;
								proMap.put(k, true);
								cityStr = tempCity;
								break;
							} else {
								proMap.put(k, false);
							}
						}
					}

				}
			}

		}
		if (cityMap != null && cityStr != null) {

			for (int i = 0; i < cityStr.length; i++) {
				if (city_position == i) {
					cityMap.put(i, true);
				} else {
					cityMap.put(i, false);
				}
			}
		}
	}

	public void showDialog() {
		if (dialog != null) {
			dialog.show();
		}
	}

	private void init() {

		provienceList = (ListView) cityView
				.findViewById(R.id.listview_provience);
		cityList = (ListView) cityView.findViewById(R.id.listview_city);

		provienceList.setOnItemClickListener(this);
		cityList.setOnItemClickListener(this);

		proAdapter = new ProvienceAdapter();
		provienceList.setAdapter(proAdapter);

		cityAdapter = new CityAdapter();
		cityList.setAdapter(cityAdapter);

	}

	private SelctedCityListener listener = null;

	public void setRefreshSortListener(SelctedCityListener listener) {
		this.listener = listener;
	}

	public interface SelctedCityListener {
		public void selectedCity(String city);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		String listViewTag = (String) parent.getTag();
		if (PROVIENCE_TAG.equals(listViewTag)) {
			if (position == 0) {
				if (listener != null) {
					listener.selectedCity("全部");
					if (dialog != null) {
						dialog.dismiss();
						dialog = null;
					}
				}
			}
			// 点击省选择器
			pro_position = position;
			// proMap.put(position, true);
			setProSelect(position);

		} else {
			// 点击市选择器
			city_position = position;
			if (listener != null) {
				String reslut = null;
				if (categoryType == CATEGORY_SEL_INVEST_HOBBY) {
					reslut = provStr[pro_position] + "-"
							+ cityStr[city_position];
					Message msg = new Message();
					if(reslut.length() > 7)
					{
						msg.obj = reslut;
						reslut = reslut.substring(0,7) + "...";
					}
					else
					{
						msg.obj = "";
					}
					EventBus.getDefault().postSticky(msg);
				} else {
					reslut = cityStr[city_position];
				}
				listener.selectedCity(reslut);
				if (dialog != null) {
					dialog.dismiss();
					dialog = null;
				}
			}
		}
	}

	private void setProSelect(int position) {

		if (provStr != null) {
			for (int i = 0; i < provStr.length; i++) {
				if (i == position) {
					proMap.put(i, true);
				} else {
					proMap.put(i, false);
				}
			}
		}
		if (proAdapter != null) {
			proAdapter.notifyDataSetChanged();
		}

		// 刷新城市数据
		cityStr = cx.getResources().getStringArray(proVsCity.get(position));
		if (cityMap != null) {
			for (int i = 0; i < cityStr.length; i++) {
				if (city_position == i) {
					cityMap.put(i, true);
				} else {
					cityMap.put(i, false);
				}
			}
		}
		if (cityAdapter != null) {
			cityAdapter.notifyDataSetChanged();
		}
	}

	private class ProvienceAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return provStr == null ? 0 : provStr.length;
		}

		@Override
		public Object getItem(int position) {
			return provStr == null ? 0 : provStr[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View proView, ViewGroup parent) {

			ProvHolder pHolder = null;
			if (proView == null) {
				proView = LayoutInflater.from(cx).inflate(
						R.layout.dlg_prov_select, null);

				pHolder = new ProvHolder();
				pHolder.provienceTv = (TextView) proView
						.findViewById(R.id.tv_provience_name);
				pHolder.ltProv = (LinearLayout) proView
						.findViewById(R.id.lt_pro);
				proView.setTag(pHolder);
			} else {
				pHolder = (ProvHolder) proView.getTag();
			}
			if (proMap.get(position)) {
				pHolder.ltProv.setBackgroundColor(cx.getResources().getColor(
						R.color.text_brown_d7c093));
			} else {
				pHolder.ltProv
						.setBackgroundResource(R.drawable.product_list_item_bg);
			}
			pHolder.provienceTv.setText((String) getItem(position));
			return proView;
		}

		public class ProvHolder {

			public TextView provienceTv;
			public LinearLayout ltProv;
		}
	}

	private class CityAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return cityStr == null ? 0 : cityStr.length;
		}

		@Override
		public Object getItem(int position) {
			return cityStr == null ? 0 : cityStr[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View cityView, ViewGroup parent) {

			CityHolder pHolder = null;
			if (cityView == null) {

				cityView = LayoutInflater.from(cx).inflate(
						R.layout.dlg_city_select, null);
				pHolder = new CityHolder();
				pHolder.cityTv = (TextView) cityView
						.findViewById(R.id.tv_city_name);
				pHolder.ltCity = (LinearLayout) cityView
						.findViewById(R.id.lt_city);
				cityView.setTag(pHolder);

			} else {
				pHolder = (CityHolder) cityView.getTag();
			}
			if (cityMap.get(position) != null) {
				if (cityMap.get(position)) {
					pHolder.ltCity.setBackgroundColor(cx.getResources()
							.getColor(R.color.text_brown_d7c093));
				} else {
					pHolder.ltCity
							.setBackgroundResource(R.drawable.product_list_item_bg);
				}
			}
			pHolder.cityTv.setText((String) getItem(position));

			return cityView;
		}

		public class CityHolder {

			public TextView cityTv;
			public LinearLayout ltCity;
		}
	}
}
