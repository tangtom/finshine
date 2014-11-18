package com.incito.finshine.activity.adapter;

import java.util.List;

import com.custom.view.CommFlipDot;
import com.incito.finshine.R;
import com.incito.finshine.entity.Customer;
import com.incito.finshine.entity.RiskIndicator;
import com.incito.finshine.entity.RiskIndicatorItem;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class AdapterQuestionViewPager extends PagerAdapter {
	public List<View> mListViews;
	public List<RiskIndicator> mQuestions;
	private TextView title;
	private TextView question;
	private RadioButton option1;
	private RadioButton option2;
	private RadioButton option3;
	private RadioButton option4;
	
	public AdapterQuestionViewPager(List<View> lists) {
		this.mListViews = lists;
	}

	public AdapterQuestionViewPager(List<View> lists, List<RiskIndicator> questions) {
		this.mListViews = lists;
		this.mQuestions = questions;
	}

	@Override
	public void destroyItem(View arg0, int position, Object arg2) {
		Log.i("liningtest", "destroyItem From ViewPager");
		((ViewPager) arg0).removeView(mListViews.get(position));// 0 1
	}

	@Override
	public void finishUpdate(View arg0) {
	}

	@Override
	public Object instantiateItem(View view, final int position) {
		Log.i("liningtest", "instantiateItem From ViewPager");

		((ViewPager) view).addView(mListViews.get(position), 0);// 0 1 2 3
		if(position<mQuestions.size()){
			
			title = (TextView) view.findViewById(R.id.txtTitle);
			question = (TextView) view.findViewById(R.id.txtQuestion);
			option1 = (RadioButton) view.findViewById(R.id.rdoOption1);
			option2 = (RadioButton) view.findViewById(R.id.rdoOption2);
			option3 = (RadioButton) view.findViewById(R.id.rdoOption3);
			option4 = (RadioButton) view.findViewById(R.id.rdoOption4);
			RiskIndicator q = mQuestions.get(position);
			List<RiskIndicatorItem> options = q.getItems();
			question.setText(q.getTitle());
			option1.setText(options.get(0).getKey());
			option2.setText(options.get(1).getKey());
			option3.setText(options.get(2).getKey());
			option4.setText(options.get(3).getKey());
			
		}
		System.out.println("lihs arg1==" + position);
		return mListViews.get(position);
	}

	@Override
	public int getCount() {
		return mListViews.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == (arg1);
	}

}
