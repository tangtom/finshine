package com.incito.finshine.activity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.custom.view.CommFlipDot;
import com.incito.finshine.R;
import com.incito.finshine.activity.adapter.AdapterCustomerBaseDetailViewPager;
import com.incito.finshine.entity.AnnualIncomExpend;
import com.incito.finshine.entity.Article;
import com.incito.finshine.entity.AssetInfo;

/**
 * 
 * <dl>
 * <dt>FragmentCustomFinancialInfo.java</dt>
 * <dd>Description:客户管理——经济状况——理财目标显示的fragment</dd>
 * <dd>CreateDate: 2014年5月13日 下午2:21:09</dd>
 * </dl>
 * 
 * @author LiNing
 */
public class FragmentCustomFinancialInfo extends FragmentDetail {
	private static final String TAG = FragmentCustomFinancialInfo.class
			.getSimpleName();

	static TextView educationTxt;
	static TextView pensionTxt;
	static TextView heritageTxt;

	View v;
	static Bundle textArgs;

	private ViewPager mPager;// 页卡内容
	private List<View> listViews; // Tab页面列表
	private View view1, view2, view3, view4;

	private static int currentView = 0;
	private static AssetInfo assetInfo;
	private static AnnualIncomExpend incomeInfo;

	private ViewHolder1 holder1;
	private ViewHolder2 holder2;

	public static FragmentCustomFinancialInfo newInstance(int id, int position,
			AssetInfo asset, AnnualIncomExpend income) {
		// textArgs = bundle;
		FragmentCustomFinancialInfo f = new FragmentCustomFinancialInfo();
		Bundle args = new Bundle();
		args.putInt(FIELD_ID, id);
		f.setArguments(args);

		// if (bundle != null)
		// getBundleText(bundle);
		currentView = position;
		assetInfo = asset;
		incomeInfo = income;

		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}
		v = inflater.inflate(R.layout.frag_customer_base_info_viewpager,
				container, false);// custom_economy_financial_goal

		initViewPager();
		init();
		return v;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public void onStart() {
		super.onStart();
	}

	private void initViewPager() {

		mPager = (ViewPager) v.findViewById(R.id.viewPagerCustomerDetail);
		listViews = new ArrayList<View>();
		LayoutInflater mInflater = getActivity().getLayoutInflater();
		view1 = mInflater.inflate(R.layout.frag_custom_economy_financial_goal,
				null);
		view2 = mInflater.inflate(R.layout.frag_custom_economy_family_assets,
				null);
		view3 = mInflater.inflate(R.layout.frag_custom_economy_family_income,
				null);
		view4 = mInflater.inflate(
				R.layout.frag_custom_economy_historical_invest, null);
		listViews.add(view1);
		listViews.add(view2);
		listViews.add(view3);
		listViews.add(view4);
		mPager.setAdapter(new AdapterCustomerBaseDetailViewPager(listViews,
				null));
		mPager.setCurrentItem(currentView);
		flipDot = new CommFlipDot(getActivity(), 4,
				(LinearLayout) v.findViewById(R.id.ltDot));
		flipDot.setSeletion(currentView);
		mPager.setOnPageChangeListener(new MyPage(flipDot));

		// mPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	private CommFlipDot flipDot = null;

	private class MyPage implements OnPageChangeListener {

		CommFlipDot flipDot = null;

		public MyPage(CommFlipDot flipDot) {
			super();
			this.flipDot = flipDot;
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int arg0) {
			if (flipDot != null) {
				flipDot.setSeletion(arg0);
			}
		}

	}

	public void init() {
		holder1 = new ViewHolder1();
		holder2 = new ViewHolder2();
		initView1(assetInfo);
		initView23(assetInfo);
		initView23(incomeInfo);
		initView4(incomeInfo);
	}


	public void initView1(AssetInfo assetInfo) {

		if ((assetInfo != null) && (assetInfo.getGoal() != null)) {
			LinearLayout linearLayout = (LinearLayout) view1
					.findViewById(R.id.linearLayout);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			params.setMargins(380, 5, 0, 0);
			linearLayout.setLayoutParams(params);
			linearLayout.setGravity(Gravity.CENTER_VERTICAL);
			linearLayout.removeAllViews();
			
			String[] goal = assetInfo.getGoal().split(",");
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
				// If goal has no value ,show text waring.
				if (assetInfo.getGoal().equals("") && goal.length == 1) {
					TextView text = new TextView(this.getActivity());
					text.setText(R.string.edit_button_warning);
					text.setGravity(Gravity.LEFT);
					text.setTextSize(30);
					lp.setMargins(0, 120, 0, 0);
					text.setLayoutParams(lp);
					linearLayout.setGravity(Gravity.LEFT);
					linearLayout.addView(text);
				} else {
					for (int i = 0; i < goal.length; i++) {
						TextView text = new TextView(this.getActivity());
						text.setLayoutParams(new LayoutParams(
								LayoutParams.WRAP_CONTENT,
								LayoutParams.WRAP_CONTENT));
						text.setGravity(Gravity.LEFT);
						text.setTextAppearance(getActivity(),
								R.style.Txt16Gray808080);
						text.setText(goal[i]);
						lp.setMargins(100, 5, 0, 0);
						text.setLayoutParams(lp);
						linearLayout.addView(text);
					}
				}
		}
	}

	public void initView23(Object obj) {
		View view;
		Object holder;
		if (obj instanceof AssetInfo) {
			view = view2;
			holder = holder1;
		} else {
			view = view3;
			holder = holder2;
		}

		Field[] fields = holder.getClass().getDeclaredFields();// Field[] fields
																// =
																// ViewHolder1.class.getDeclaredFields();
		try {
			for (Field f : fields) {

				Class cr = Class.forName("com.incito.finshine.R$id");
				int rid = cr.getField(f.getName()).getInt(cr);
				View v = view.findViewById(rid);// View v =
												// view2.findViewById(rid);
				if (v != null) {
					f.set(holder, v);
				}

			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		if (obj != null) {
			Field[] hfields = holder.getClass().getDeclaredFields();
			try {
				for (Field f : hfields) {
					String methodName = "get"
							+ f.getName().substring(0, 1).toUpperCase()
							+ f.getName().substring(1);
					Log.i("tag", "method name = " + methodName);
					Method get = obj.getClass().getMethod(methodName);

					if (get != null) {
						String value = String.valueOf(get.invoke(obj));
						Method set = TextView.class.getMethod("setText",
								CharSequence.class);

						Field txt = holder.getClass().getDeclaredField(
								f.getName());

						Log.i("tag", "field = " + txt.getType());

						TextView tv = (TextView) txt.get(holder);
						set.invoke(tv, value);
					}

				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
		}
	}

	public void initView4(AnnualIncomExpend incomeInfo) {
		TextView historicalView = (TextView) view4
				.findViewById(R.id.textHistorical);
		
		historicalView.setText((incomeInfo == null || incomeInfo.getHistoryExperience() == null)? "暂无"
				: incomeInfo.getHistoryExperience());
	}

	// public void initDate(Object obj, Object holder) {
	//
	// if (obj != null) {
	// Field[] hfields = holder.getClass().getDeclaredFields();
	// try {
	// for (Field f : hfields) {
	// String methodName = "get"
	// + f.getName().substring(0, 1).toUpperCase()
	// + f.getName().substring(1);
	// Log.i("tag", "method name = " + methodName);
	// Method get = obj.getClass().getMethod(methodName);
	//
	// if (get != null) {
	// String value = String.valueOf(get.invoke(assetInfo));
	// Method set = TextView.class.getMethod("setText",
	// CharSequence.class);
	//
	// Field txt = holder.getClass().getDeclaredField(
	// f.getName());
	//
	// Log.i("tag", "field = " + txt.getType());
	//
	// TextView tv = (TextView) txt.get(holder);
	// set.invoke(tv, value);
	// }
	//
	// }
	// } catch (IllegalArgumentException e) {
	// e.printStackTrace();
	// } catch (SecurityException e) {
	// e.printStackTrace();
	// } catch (IllegalAccessException e) {
	// e.printStackTrace();
	// } catch (InvocationTargetException e) {
	// e.printStackTrace();
	// } catch (NoSuchMethodException e) {
	// e.printStackTrace();
	// } catch (NoSuchFieldException e) {
	// e.printStackTrace();
	// }
	// }
	//
	// }

	static class ViewHolder1 {
		TextView cash;
		TextView demanDeposit;
		TextView fixDeposit;
		TextView stock;
		TextView fund;
		TextView bond;
		TextView estate;
		TextView otherAsset;
		TextView totalAsset;
		TextView houseFundLoan;
		TextView houseBusinessLoan;
		TextView carLoan;
		TextView consumerLoan;
		TextView renovationLoan;
		TextView privateLoan;
		TextView unpaidCreditCard;
		TextView otherLoan;
		TextView totalLoan;
		TextView netAsset;
	}

	static class ViewHolder2 {
		TextView income;
		TextView spouseIncome;
		TextView endBonus;
		TextView rental;
		TextView interest;
		TextView dividends;
		TextView otherDividends;
		TextView otherFixIncome;
		TextView otherUnfixIncome;
		TextView totalIncome;
		TextView livingCost;
		TextView housExpense;
		TextView medicExpense;
		TextView eduExpense;
		TextView insurance;
		TextView travel;
		TextView alimony;
		TextView otherFixExpense;
		TextView otherUnfixExpense;
		TextView totalExpenditure;
		TextView annualBalance;
	}

	public int getNowView() {
		return mPager.getCurrentItem();
	}

	// static void getBundleText(Bundle textArgs){
	// Log.i("education", textArgs.getString("education"));
	// Log.i("pension", textArgs.getString("pension"));
	// Log.i("heritage", textArgs.getString("heritage"));
	//
	// educationTxt.setText(textArgs.getString("education"));
	// pensionTxt.setText(textArgs.getString("pension"));
	// heritageTxt.setText(textArgs.getString("heritage"));
	// }
}