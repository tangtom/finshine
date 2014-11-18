package com.custom.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.android.core.util.SharedUtil;
import com.incito.finshine.R;
import com.incito.finshine.entity.DataFilterModel;
import com.incito.utility.CommonUtil;

public class PopTaskSelect implements OnClickListener {
	public DataFilter getDataFilter() {
		return dataFilter;
	}

	public void setDataFilter(DataFilter dataFilter) {
		this.dataFilter = dataFilter;
	}

	private Context context = null;
	private View popView = null;
	private View clickView = null;
	private PopupWindow popWin = null;
	private int popDirection = 0;
	EditText keywordsEditText, difficultyNameEditText;
	RadioGroup radioGroup1, radioGroup2, radioGroup3;
	RadioButton radio00, radio01, radio02, radio03, radio10, radio11, radio12,
			radio13, radio20, radio21, radio22, radio23, radio24;
	TextView textViewSubmit;
	private ImageView search_icon;
	/** popwindow 弹出方向 **/
	public static final int LEFT_2_RIGHT = 1;
	public static final int RIGHT_2_LEFT = 2;
	public static final int TOP_2_BOTTOM = 3;

	private int backGroundId = -1;

	private int type = 0;
	private int states = 0;
	private int difficulty = 0;

	/** popwindow 背景图片 **/

	public PopTaskSelect(Context context, View clickView, int backGroundId,
			int popDirection) {

		super();
		this.clickView = clickView;
		this.context = context;
		this.backGroundId = backGroundId;
		this.popDirection = popDirection;

		initPopwindow(clickView);
	}

	private void initPopwindow(View clickView) {

		popView = LayoutInflater.from(context).inflate(
				R.layout.pop_task_select, null);
		popWin = new PopupWindow(popView, 690, 420, true);
		if (backGroundId > -1) {
			popView.findViewById(R.id.lt_task_select).setBackgroundResource(
					backGroundId);
		}
		String saveStates = SharedUtil.getPreferStr("saveStates");
		if (saveStates == null) {
			saveStates = "0,0,0";
		}
		type = Integer.parseInt(saveStates.split(",")[0]);
		states = Integer.parseInt(saveStates.split(",")[1]);
		difficulty = Integer.parseInt(saveStates.split(",")[2]);

		keywordsEditText = (EditText) popView.findViewById(R.id.et_search_text);
		search_icon = (ImageView) popView.findViewById(R.id.search_icon);
		if(SharedUtil.getPreferStr("saveStatesCondition")!=null){
			keywordsEditText.setText(SharedUtil.getPreferStr("saveStatesCondition"));
			search_icon.setImageDrawable(context.getResources()
					.getDrawable(R.drawable.delete));
		}
		dataFilterModel.setKeyword(SharedUtil.getPreferStr("saveStatesCondition"));
		
		radioGroup1 = (RadioGroup) popView.findViewById(R.id.radioGroup1);
		radioGroup1.check(type);
		radioGroup2 = (RadioGroup) popView.findViewById(R.id.radioGroup2);
		radioGroup2.check(states);
		radioGroup3 = (RadioGroup) popView.findViewById(R.id.radioGroup3);
		radioGroup3.check(difficulty);
		radio00 = (RadioButton) popView.findViewById(R.id.radio00);
		radio01 = (RadioButton) popView.findViewById(R.id.radio01);
		radio02 = (RadioButton) popView.findViewById(R.id.radio02);
		radio03 = (RadioButton) popView.findViewById(R.id.radio03);
		radio10 = (RadioButton) popView.findViewById(R.id.radio10);
		radio11 = (RadioButton) popView.findViewById(R.id.radio11);
		radio12 = (RadioButton) popView.findViewById(R.id.radio12);
		radio13 = (RadioButton) popView.findViewById(R.id.radio13);
		radio20 = (RadioButton) popView.findViewById(R.id.radio20);
		radio21 = (RadioButton) popView.findViewById(R.id.radio21);
		radio22 = (RadioButton) popView.findViewById(R.id.radio22);
		radio23 = (RadioButton) popView.findViewById(R.id.radio23);
		radio24 = (RadioButton) popView.findViewById(R.id.radio24);
		if (type == 0) {
			radio00.setChecked(true);
			
		} else if (type == 1) {
			radio01.setChecked(true);
			dataFilterModel.setTypeFK(10);
		} else if (type == 2) {
			radio02.setChecked(true);
			dataFilterModel.setTypeFK(20);
		} else if (type == 3) {
			radio03.setChecked(true);
			dataFilterModel.setTypeFK(30);
		}
		if (states == 0) {
			radio10.setChecked(true);
		} else if (states == 1) {
			dataFilterModel.setStatusFK(-1);
			radio11.setChecked(true);
		} else if (states == 2) {
			dataFilterModel.setStatusFK(10);
			radio12.setChecked(true);
		} else if (states == 3) {
			dataFilterModel.setStatusFK(20);
			radio13.setChecked(true);
		}
		if (difficulty == 0) {
			radio20.setChecked(true);
			
		} else if (difficulty == 1) {
			dataFilterModel.setDifficultyName("简单");
			radio21.setChecked(true);
		} else if (difficulty == 2) {
			dataFilterModel.setDifficultyName("普通");
			radio22.setChecked(true);
		} else if (difficulty == 3) {
			dataFilterModel.setDifficultyName("困难");
			radio23.setChecked(true);
		} else if (difficulty == 4) {
			dataFilterModel.setDifficultyName("史诗");
			radio24.setChecked(true);
		}
		textViewSubmit = (TextView) popView.findViewById(R.id.textViewSubmit);
		textViewSubmit.setOnClickListener(this);
		popWin.setBackgroundDrawable(new ColorDrawable());
		popWin.setOutsideTouchable(true);
		popWin.setFocusable(true);
		radioGroup1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup radiogroup, int i) {

				RadioButton radioButton = (RadioButton) popView
						.findViewById(radiogroup.getCheckedRadioButtonId());
				String tempString = radioButton.getText().toString().trim();
				if (tempString.equals("普通")) {
					dataFilterModel.setTypeFK(10);
					type = 1;
				} else if (tempString.equals("日常")) {
					dataFilterModel.setTypeFK(20);
					type = 2;
				} else if (tempString.equals("成就")) {
					dataFilterModel.setTypeFK(30);
					type = 3;
				} else {
					type = 0;
					dataFilterModel.setTypeFK(0);
				}

			}
		});
		radioGroup2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup radiogroup, int i) {

				RadioButton radioButton = (RadioButton) popView
						.findViewById(radiogroup.getCheckedRadioButtonId());
				String tempString = radioButton.getText().toString().trim();

				if (tempString.equals("可接")) {
					dataFilterModel.setStatusFK(-1);
					states = 1;
				} else if (tempString.equals("进行中")) {
					dataFilterModel.setStatusFK(10);
					states = 2;
				} else if (tempString.equals("已完成")) {
					dataFilterModel.setStatusFK(20);
					states = 3;
				} else {
					states = 0;
					dataFilterModel.setStatusFK(0);
				}
			}
		});
		radioGroup3.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup radiogroup, int i) {

				RadioButton radioButton = (RadioButton) popView
						.findViewById(radiogroup.getCheckedRadioButtonId());
				dataFilterModel.setDifficultyName(radioButton.getText()
						.toString());
				String tempString = radioButton.getText().toString().trim();
				if (tempString.equals("简单")) {
					difficulty = 1;
				} else if (tempString.equals("普通")) {
					difficulty = 2;
				} else if (tempString.equals("困难")) {
					difficulty = 3;
				} else if (tempString.equals("史诗")) {
					difficulty = 4;
				} else {
					difficulty = 0;
					dataFilterModel.setDifficultyName("");
				}
			}
		});

		keywordsEditText.addTextChangedListener(new TextWatcher() {
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (count != 0) {
					search_icon.setImageDrawable(context.getResources()
							.getDrawable(R.drawable.delete));
				} else {
					search_icon.setImageDrawable(context.getResources()
							.getDrawable(R.drawable.product_search_icon));
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void afterTextChanged(Editable s) {
			}
		});
		search_icon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (search_icon.getDrawable().getCurrent().getConstantState() == context
						.getResources().getDrawable(R.drawable.delete)
						.getConstantState()) {
					keywordsEditText.setText("");
				}
			}
		});

	}

	DataFilterModel dataFilterModel = new DataFilterModel();

	public void showPopWindow() {
		if (popWin != null) {
			int[] location = new int[2];
			clickView.getLocationOnScreen(location);
			switch (popDirection) {
			case LEFT_2_RIGHT:
				// 从左到右弹出
				popWin.setAnimationStyle(R.style.anim_left_2_right);
				popWin.showAtLocation(clickView, Gravity.NO_GRAVITY,
						location[0] + clickView.getWidth() + 2, location[1]
								+ clickView.getHeight() + 2);
				break;
			case RIGHT_2_LEFT:
				// 从右到左弹出
				popWin.setAnimationStyle(R.style.product_more_windown_animstyle);
				popWin.showAtLocation(clickView, Gravity.NO_GRAVITY,
						clickView.getWidth() + 200, clickView.getHeight() + 20);
				break;
			case TOP_2_BOTTOM:
				popWin.setAnimationStyle(R.style.anim_top_2_bottom);
				int screenW = CommonUtil.getScreenWidth(context);
				popWin.showAtLocation(clickView, Gravity.NO_GRAVITY,
						screenW - 610, location[1] + clickView.getHeight() + 3);
				break;
			default:
				break;
			}
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

	@Override
	public void onClick(View v) {

		if (dataFilter != null) {
			
			dataFilterModel.setKeyword(keywordsEditText.getText().toString());
			dataFilter.dataFilter(dataFilterModel);

			SharedUtil.setPreferStr("saveStates", type + "," + states + ","
					+ difficulty);
			SharedUtil.setPreferStr("saveStatesCondition", keywordsEditText.getText().toString().trim());
		}
		closePopWindow();
	}

	DataFilter dataFilter;

	public interface DataFilter {
		void dataFilter(DataFilterModel model);
	}

	 
}
