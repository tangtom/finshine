package com.custom.view;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.incito.finshine.R;

public class PopLetterIndex {

	private Context context = null;

	private View popView = null;

	private View clickView = null;

	private PopupWindow popWin = null;

	private List<String[]> allCity = null;

	private int selePosition = 0;

	// 快速定位条
	private SideBar sideBar;
	// 快速定位条，选中提示
	private TextView mDialogText;
	private View dialogView;
	String[] sections;
	// 是否显示字母分类，默认不显示
	private boolean hasCatalog = false;
	// 是单选还是多选啊 true是多选，false 是单选
	private boolean isNotSignal = false;

	private String lastCatalog = "";  // 最新一条的首字母
	private String currentLetter = "";//  当前首字母
	private String max = String.valueOf((char)(255)); // 常用字符最大ASCII
	
	private  String[] letters = {"A","B", "C","D", "E","F", "G","H", "I","J",
			"K","L", "M","N", "O","P", "Q","R", "S","T", "U","V", "W","X", "Y","Z"};
	

	private List<String[]> getData() {

		allCity = new ArrayList<String[]>();
		allCity.add(context.getResources().getStringArray(
				R.array.all_provience));
		allCity.add(context.getResources().getStringArray(
				R.array.beijin_province_item));
		allCity.add(context.getResources().getStringArray(
				R.array.tianjin_province_item));
		allCity.add(context.getResources().getStringArray(
				R.array.heibei_province_item));
		allCity.add(context.getResources().getStringArray(
				R.array.shanxi1_province_item));
		allCity.add(context.getResources().getStringArray(
				R.array.neimenggu_province_item));
		allCity.add(context.getResources().getStringArray(
				R.array.liaoning_province_item));
		allCity.add(context.getResources().getStringArray(
				R.array.jilin_province_item));
		allCity.add(context.getResources().getStringArray(
				R.array.heilongjiang_province_item));
		allCity.add(context.getResources().getStringArray(
				R.array.shanghai_province_item));
		allCity.add(context.getResources().getStringArray(
				R.array.jiangsu_province_item));
		allCity.add(context.getResources().getStringArray(
				R.array.anhui_province_item));
		allCity.add(context.getResources().getStringArray(
				R.array.zhejiang_province_item));
		allCity.add(context.getResources().getStringArray(
				R.array.fujian_province_item));
		allCity.add(context.getResources().getStringArray(
				R.array.jiangxi_province_item));
		allCity.add(context.getResources().getStringArray(
				R.array.shandong_province_item));
		allCity.add(context.getResources().getStringArray(
				R.array.henan_province_item));

		allCity.add(context.getResources().getStringArray(
				R.array.hubei_province_item));
		allCity.add(context.getResources().getStringArray(
				R.array.hunan_province_item));
		allCity.add(context.getResources().getStringArray(
				R.array.guangdong_province_item));
		allCity.add(context.getResources().getStringArray(
				R.array.guangxi_province_item));

		allCity.add(context.getResources().getStringArray(
				R.array.hainan_province_item));
		allCity.add(context.getResources().getStringArray(
				R.array.chongqing_province_item));
		allCity.add(context.getResources().getStringArray(
				R.array.sichuan_province_item));
		allCity.add(context.getResources().getStringArray(
				R.array.guizhou_province_item));

		allCity.add(context.getResources().getStringArray(
				R.array.yunnan_province_item));
		allCity.add(context.getResources().getStringArray(
				R.array.xizang_province_item));
		allCity.add(context.getResources().getStringArray(
				R.array.shanxi2_province_item));

		allCity.add(context.getResources().getStringArray(
				R.array.gansu_province_item));
		allCity.add(context.getResources().getStringArray(
				R.array.qinghai_province_item));
		allCity.add(context.getResources().getStringArray(
				R.array.linxia_province_item));
		allCity.add(context.getResources().getStringArray(
				R.array.xinjiang_province_item));
		allCity.add(context.getResources().getStringArray(
				R.array.hongkong_province_item));
		allCity.add(context.getResources().getStringArray(
				R.array.aomen_province_item));
		allCity.add(context.getResources().getStringArray(
				R.array.taiwan_province_item));
		return allCity;
	}

	private List<String> cityList = new ArrayList<String>();
	private List<String> tempList = null;

	private List<String> getAllCity(List<String[]> allCitys) {

		for (String[] item : allCitys) {
			for (String item1 : item) {
				cityList.add(item1);
			}
		}
		tempList = cityList;
		return cityList;
	}

	public PopLetterIndex(Context context, View clickView) {

		super();
		this.clickView = clickView;
		this.context = context;

		cityList = getAllCity(getData());

		initPopwindow(clickView);
		
		init();

	}

	private void init() {

		ListView listView = (ListView) popView.findViewById(R.id.cityListView);
		final CityAdapter adapter = new CityAdapter();
		listView.setAdapter(adapter);
 		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				selePosition = position;
				if (listener != null) {
					listener.selectedCity(cityList.get(position));
					closePopWindow();

				}
			}
		});

		sideBar = (SideBar) popView.findViewById(R.id.letter_sideBar);
		// 在屏幕中央添加一个TextView，用来提示快速定位条选中的字母
		dialogView = (View) LayoutInflater.from(context).inflate(
				R.layout.letter_list_position, null);
		mDialogText = (TextView) dialogView.findViewById(R.id.position_text);
		sideBar.setTextView(mDialogText);
		sideBar.setPopView(dialogView);
		sideBar.setListView(listView);

		EditText etSearch = (EditText) popView.findViewById(R.id.et_search_text);
		etSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				adapter.getFilter().filter(s.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
	}

	private void initPopwindow(View clickView) {

		popView = LayoutInflater.from(context).inflate(R.layout.zm_index, null);
		popWin = new PopupWindow(popView,500,600, true);

		popWin.setBackgroundDrawable(new ColorDrawable());
		popWin.setOutsideTouchable(true);
		popWin.setFocusable(true);
	}

	private SelctedCityListener listener = null;

	public void setRefreshSortListener(SelctedCityListener listener) {
		this.listener = listener;
	}

	public interface SelctedCityListener {
		public void selectedCity(String city);
	}

	private class CityAdapter extends BaseAdapter implements SectionIndexer {

		@Override
		public int getCount() {
			return tempList == null ? 0 : tempList.size();
		}

		@Override
		public Object getItem(int position) {
			return tempList == null ? 0 : tempList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View cityView, ViewGroup parent) {

			CityHolder pHolder = null;
			if (cityView == null) {

				cityView = LayoutInflater.from(context).inflate(
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
//			if (selePosition == position) {
//				pHolder.ltCity.setBackgroundColor(context.getResources()
//						.getColor(R.color.text_brown_d7c093));
//			} else {
				pHolder.ltCity.setBackgroundResource(R.drawable.product_list_item_bg);
//			}

			String filter = filterName;
			if (TextUtils.isEmpty(filter)) {
				pHolder.cityTv.setText((String) getItem(position));
			} else {
				pHolder.cityTv.setText(Html.fromHtml(((String) getItem(position))
								.replace(filter, "<font color='#e50150'>"
										+ filter + "</font>")));
			}

			return cityView;
		}

		public class CityHolder {

			public TextView cityTv;
			public LinearLayout ltCity;
		}

		/**
		 * @author: lihs
		 * @Title: getFilter
		 * @Description: 关键字过滤，根据输入的关键字，筛选出需要的数据放入显示列表中
		 */
		String filterName;

		public Filter getFilter() {

			Filter filter = new Filter() {

				String filterNum = null;

				@Override
				@SuppressWarnings("unchecked")
				protected void publishResults(CharSequence constraint,
						FilterResults results) {

					filterName = filterNum;
					if (TextUtils.isEmpty(filterNum)) {
						// 过滤为空时 显示所有的数据
						tempList = cityList;
						notifyDataSetChanged();
						return;
					}
					tempList = (ArrayList<String>) results.values;
					if (results.count > 0) {
						notifyDataSetChanged();
					} else {
						notifyDataSetInvalidated();
					}
				}

				@Override
				@SuppressWarnings("unused")
				protected FilterResults performFiltering(CharSequence s) {
					String str = s.toString();
					filterNum = str;
					FilterResults results = new FilterResults();
					ArrayList<String> list = new ArrayList<String>();
					if (cityList != null && cityList.size() > 0) {
						for (String cb : cityList) {
							// 筛选数据：当没有输入筛选数字时
							if (TextUtils.isEmpty(s)) {
								continue;
							}
							// 根据关键字进行刷选
							if (cb.indexOf(str) >= 0 || getFullPingYin(cb).indexOf(str) >=0) {
								list.add(cb);
							}
						}
					}
					results.values = list;
					results.count = list.size();
					return results;
				}
			};
			return filter;
		}

		@Override
		public Object[] getSections() {
			return letters;
		}

		@Override
		public int getSectionForPosition(int position) {
			return 1;
		}

		@Override
		public int getPositionForSection(int section) {
//			return positonMap.size() !=0 ? positonMap.get(sections[section]):0;
			return section;
		}

	}

	// 汉字转换为全拼
	public  String getFullPingYin(String src) {

        char[] t1 = null;
        t1 = src.toCharArray();
        String[] t2 = new String[t1.length];
        HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
        t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        t3.setVCharType(HanyuPinyinVCharType.WITH_V);
        String t4 = "";
        int t0 = t1.length;
        try {
            for (int i = 0; i < t0; i++) {
                // 判断是否为汉字字符
                if (java.lang.Character.toString(t1[i]).matches(
                        "[\\u4E00-\\u9FA5]+")) {
                    t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
                    t4 += t2[0];
                } else
                    t4 += java.lang.Character.toString(t1[i]);
            }
            return t4;
        } catch (BadHanyuPinyinOutputFormatCombination e1) {
            e1.printStackTrace();
        }
        return t4;
    }

	public void showPopWindow() {
		if (popWin != null) {
			int[] location = new int[2];
			clickView.getLocationOnScreen(location);
			popWin.showAtLocation(clickView, Gravity.CENTER, 0, 0);
		}
	}

	public void closePopWindow() {
		
		if (popWin != null) {
			if (popWin.isShowing()) {
				popWin.dismiss();
				popWin = null;
			}
		}
		if (dialogView != null) {
			dialogView = null;
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

}
