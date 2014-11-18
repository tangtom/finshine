package com.custom.view;

import java.util.HashMap;
import java.util.Map;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.incito.finshine.R;
import com.incito.utility.CommonUtil;

/**
 * <dl>
 * <dt>DlgCommFilter.java</dt>
 * <dd>Description:刷选条件</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 昆山灵动信息技术有限公司</dd>
 * <dd>CreateDate: 2014-6-5 下午11:41:29</dd>
 * </dl>
 * 
 * @author lihs
 */
public class DlgCommFilter {

	private Context cx;
	private Dialog dialog;
	private View view;

	private ListView dataList;
	private FilterAdapter fAdapter;

	private int title;
	private String data[] = null;

	private boolean IS_SIGNAL_SEL = false;// false 多选模式；true 是单选模式
	private int selePosition = 0;

	private boolean isHiddenInput = false;
	private int model = 0; // 0：不加任何 1:填写的最后加“个月” 2：每个加“%” 3：最后加“万” 4:最后加“年”

	private Button btn = null;
	private EditText et = null;
	private EditText et2 = null;
	

	public boolean isHiddenInput() {
		return isHiddenInput;
	}

	public void setHiddenInput(boolean isHiddenInput) {
		this.isHiddenInput = isHiddenInput;
	}

	private Map<Integer, Boolean> manySele = null;// 多选

	public void setSelePosition(int selePosition) {
		this.selePosition = selePosition;
		if (fAdapter != null) {
			fAdapter.notifyDataSetChanged();
		}
	}

	private int getSelection(String seleName) {
		int sel = -1;
		if (data != null) {
			for (int i = 0; i < data.length; i++) {
				if (data[i].equals(seleName)) {
					sel = i;
					break;
				}
			}
		}
		return sel;
	}

	public DlgCommFilter(Context cx, int dataId, int title, String seleName,
			boolean isSignalSel, int model) {
		super();
		this.cx = cx;
		this.title = title;
		data = cx.getResources().getStringArray(dataId);
		this.IS_SIGNAL_SEL = isSignalSel;
		this.model = model;

		dialog = new Dialog(cx, R.style.CustomProgressDialog);
		view = LayoutInflater.from(cx).inflate(R.layout.dlg_comm_filter, null);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		dialog.addContentView(view, params);
		dialog.setCanceledOnTouchOutside(true);

		init(seleName,dataId);
	}
	
	/**
	 * 
	 * change by SGDY for bug#5136
	 */

	public void setInputValue(Map<Integer,String[]> map)
	{
		if(map.containsKey(title))
		{
			et.setText(map.get(title)[0]);
			et2.setText(map.get(title)[1]);
		}
	}
	
	private void init(String selName, final int dataId) {

		TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
		tvTitle.setText(title);

		et = ((EditText) view.findViewById(R.id.etInput1));
		et.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (TextUtils.isEmpty(s.toString())) {
					btn.setVisibility(View.GONE);
				} else {
					btn.setVisibility(View.VISIBLE);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});

		et2 = ((EditText) view.findViewById(R.id.etInput2));
		et2.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (TextUtils.isEmpty(s.toString())) {
					btn.setVisibility(View.GONE);
				} else {
					btn.setVisibility(View.VISIBLE);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		// 保存
		btn = (Button) view.findViewById(R.id.btn_input_value);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String value = "";
				StringBuffer reslut = new StringBuffer();
				if (IS_SIGNAL_SEL) {
					String input1 = et.getText().toString().trim();
					String input2 = et2.getText().toString().trim();
					if (TextUtils.isEmpty(input1) || TextUtils.isEmpty(input2)) {
						CommonUtil.showToast("请输入数值", cx);
						return;
					} else {
						// 都不空
						if (Long.parseLong(input1) >= Long.parseLong(input2)) {
							CommonUtil.showToast("起始数据应该小于结束数据", cx);
							return;
						    }
						else if(dataId == R.array.deadline_status&&((input1.length()>3)|| (input2.length()>3))){
							CommonUtil.showToast("请输入三位数以内", cx); 
							return;
						}
					}
					switch (model) {
					case 1:
						String validateNumberOne = getSpilitZeroNumbers(input1);
						String validateNumberTwo = getSpilitZeroNumbers(input2);
						value = validateNumberOne + "-" + validateNumberTwo
								+ "个月";
						break;

					case 2:
						value = input1 + "%-" + input2 + "%";
						break;

					case 3:
						value = input1 + "-" + input2 + "万";
						break;
						
					case 4:
						value = input1 + "-" + input2 + "年";
						break;

					default:
						value = input1 + "-" + input2;
						break;
					}

				} else {
					for (int i = 0; i < manySele.size(); i++) {
						if (manySele.get(i)) {
							reslut.append(data[i] + ",");
						}
					}
					value = reslut.toString();

					if (!TextUtils.isEmpty(value)
							&& value.charAt(value.length() - 1) == ',') {
						value = value.substring(0, value.length() - 1);
					}
				}
				if (listener != null) {
					listener.doRefresh(value, model,true,title);
				}
				closeDialog();
			}
		});

		if (IS_SIGNAL_SEL) {
			// 单选模式
			this.selePosition = getSelection(selName);
			// if (selePosition == -1) {
			// // et.setText(selName);
			// selePosition = 0;
			// }
		} else {
			// 多选模式
			btn.setVisibility(View.VISIBLE);
			String seleValue[] = null;
			
				seleValue = selName.split("\\,");
				manySele = new HashMap<Integer, Boolean>();
				for (int i = 0; i < data.length; i++) {
					manySele.put(i, false);
					for (int j = 0; j < seleValue.length; j++) {
						if (data[i].trim().equals(seleValue[j].trim())) {
							manySele.put(i, true);
						}
					}
				}
		}

		dataList = (ListView) view.findViewById(R.id.listview_filter);
		switch(model)
		{
		case 1:/*------------  change by SGDY for BUG#4952  -------------*/
		case 2:
			et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});
			et2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});
			break;
		case 3:
			et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});
			et2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});
			break;
		case 4:
			view.findViewById(R.id.lt).setVisibility(View.GONE);
			ViewGroup.LayoutParams params = dataList.getLayoutParams();
			params.height = 400;
			dataList.setLayoutParams(params);
			break;
		}
		fAdapter = new FilterAdapter();
		dataList.setAdapter(fAdapter);

		dataList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position < 0) {
					return;
				}
				StringBuffer reslut = new StringBuffer();
				if (IS_SIGNAL_SEL) {
					setSelePosition(position);
					reslut.append(data[position]);
					if (listener != null) {
						listener.doRefresh(reslut.toString(), position,false,title);
					}
					closeDialog();
				} else {
					FilterHolder holder = (FilterHolder) view.getTag();
					holder.cb.toggle();
					manySele.put(position, holder.cb.isChecked());
					if (fAdapter != null) {
						fAdapter.notifyDataSetChanged();
					}
				}
			}
		});

	}

	public void showDialog() {

		if (isHiddenInput) {
			view.findViewById(R.id.lthidden).setVisibility(View.GONE);
			// btn.setVisibility(View.GONE);
		}
		if (dialog != null) {
			dialog.show();
		}
	}

	public void closeDialog() {
		if (dialog != null) {
			dialog.dismiss();
			dialog = null;
		}
	}

	private class FilterAdapter extends BaseAdapter {

		public FilterAdapter() {
			super();
		}

		@Override
		public int getCount() {
			return data == null ? 0 : data.length;
		}

		@Override
		public Object getItem(int position) {
			return data == null ? 0 : data[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View view, ViewGroup parent) {

			FilterHolder fHolder = null;
			if (view == null) {
				view = LayoutInflater.from(cx).inflate(R.layout.row_filter,
						null);
				fHolder = new FilterHolder();
				fHolder.sigleSel = (RadioButton) view
						.findViewById(R.id.rbSignalSel);
				fHolder.tvName = (TextView) view.findViewById(R.id.tvName);
				fHolder.cb = (CheckBox) view.findViewById(R.id.cb);
				view.setTag(fHolder);
			} else {
				fHolder = (FilterHolder) view.getTag();
			}

			if (IS_SIGNAL_SEL) {
				fHolder.cb.setVisibility(View.GONE);
				fHolder.sigleSel.setVisibility(View.VISIBLE);
				if (selePosition == position) {
					fHolder.sigleSel.setChecked(true);
				} else {
					fHolder.sigleSel.setChecked(false);
				}
			} else {
				fHolder.cb.setVisibility(View.VISIBLE);
				fHolder.sigleSel.setVisibility(View.GONE);
				fHolder.cb.setChecked(manySele.get(position));
			}
			fHolder.tvName.setText(data[position]);

			return view;
		}

	}

	public class FilterHolder {

		TextView tvName;
		RadioButton sigleSel;
		CheckBox cb;
	}

	private RefreshFilterListener listener = null;

	public void setListener(RefreshFilterListener listener) {
		this.listener = listener;
	}

	public interface RefreshFilterListener {
		public void doRefresh(String reslut, int position,boolean b,int title);//change by SGDY (b 用来判定传过去的数值是否为手动输入的数值 title表示为哪个弹出框)
	}
	
	private String getSpilitZeroNumbers(String number) {
		if (number.charAt(0) == '0' && number.charAt(1) != '0') {
			return number.substring(1);
		} else if (number.charAt(0) == '0' && number.charAt(1) == '0') {
			return number.substring(2);
		}
		return number;
	}

}
