package com.custom.view;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.HttpAuthHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.ZoomDensity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.custom.view.CommSortView.RefreshSortListener;
import com.incito.finshine.R;
import com.incito.finshine.common.Constant;
import com.incito.finshine.entity.CommissionStatistics;
import com.incito.finshine.manager.CoreManager;
import com.incito.finshine.manager.SPManager;
import com.incito.finshine.network.Request;
import com.incito.finshine.network.Request.RequestType;
import com.incito.finshine.network.RequestDefine;
import com.incito.utility.CommonUtil;
import com.incito.wisdomsdk.net.http.JsonHttpResponseHandler;

public class PopPointerAnalyse {
	private final String TAG = PopPointerAnalyse.class.getSimpleName();
	// private Product prod;

	private Context context = null;

	private View popView = null;

	private View clickView = null;
	private WebView webView;
	private CommissionStatistics commissionStatistics;
	private CommSortView sortView = null;

	private JsonHttpResponseHandler mhandler = new JsonHttpResponseHandler() {

		@Override
		public void onSuccess(JSONObject response) {
			try {// {"commissionInProcessing":0,"user_fk":1,"totalExtraCommission":63,"totalPoint":0,"topExtraCommission":63,"topFixedCommission":12600,"rankingOfExtraCommission":1,"totalFixedCommission":12600,"totalCommissionRatio":11.08,"rankingOfFixedCommission":1}
				Log.i(TAG, response.toString());
				if (response.getString("status").equals("0")) {
					response = response.getJSONObject("result");
					long user_fk = response.getLong("user_fk");
					int rankingOfExtraCommission = response
							.getInt("rankingOfExtraCommission");
					int rankingOfFixedCommission = response
							.getInt("rankingOfFixedCommission");
					long totalPoint = response.getLong("totalPoint");
					double topExtraCommission = response
							.getDouble("topExtraCommission");
					double topFixedCommission = response
							.getDouble("topFixedCommission");
					double totalExtraCommission = response
							.getDouble("totalExtraCommission");
					double totalFixedCommission = response
							.getDouble("totalFixedCommission");
					double totalCommissionRatio = response
							.getDouble("totalCommissionRatio");
					double commissionInProcessing = response
							.getDouble("commissionInProcessing");
					commissionStatistics = new CommissionStatistics();
					commissionStatistics.setUser_fk(user_fk);
					commissionStatistics
							.setCommissionInProcessing(commissionInProcessing);
					commissionStatistics
							.setRankingOfExtraCommission(rankingOfExtraCommission);
					commissionStatistics
							.setRankingOfFixedCommission(rankingOfFixedCommission);
					commissionStatistics
							.setTopExtraCommission(topExtraCommission);
					commissionStatistics
							.setTopFixedCommission(topFixedCommission);
					commissionStatistics
							.setTotalCommissionRatio(totalCommissionRatio);
					commissionStatistics
							.setTotalExtraCommission(totalExtraCommission);
					commissionStatistics
							.setTotalFixedCommission(totalFixedCommission);
					commissionStatistics.setTotalPoint(totalPoint);
					initData();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			super.onSuccess(response);
		}

		@Override
		public void onFailure(Throwable error, String content) {
			Log.i(TAG, content);
		}
	};

	public PopupWindow getPopWin() {
		return popWin;
	}

	private PopupWindow popWin = null;

	/** popwindow 弹出方向 **/
	public static final int TOP_2_BOTTOM = 3;

	private int popDirection = TOP_2_BOTTOM;

	public PopPointerAnalyse(Context context, View clickView) {

		super();
		// this.prod = prod;

		this.clickView = clickView;
		this.context = context;

		// this.popDirection = popDirection;

		initPopwindow(clickView);
	}

	private void initPopwindow(View clickView) {

		popView = LayoutInflater.from(context).inflate(
				R.layout.pop_commission_detail, null);
		// popWin = new PopupWindow(popView,
		// LinearLayout.LayoutParams.WRAP_CONTENT,
		// LinearLayout.LayoutParams.WRAP_CONTENT, true);
		popWin = new PopupWindow(popView, 940, 420, true);

		popWin.setBackgroundDrawable(new ColorDrawable());
		popWin.setOutsideTouchable(true);
		popWin.setFocusable(true);
		popWin.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				CommonUtil.hideSoftInputFromWindow((Activity) context);

			}
		});
	}

	public void showPopWindow() {

		getData();

		if (popWin != null) {
			int[] location = new int[2];
			clickView.getLocationOnScreen(location);
			switch (popDirection) {
			case TOP_2_BOTTOM:
				popWin.setAnimationStyle(R.style.anim_top_2_bottom);
				int screenW = CommonUtil.getScreenWidth(context);
				popWin.showAtLocation(clickView, Gravity.NO_GRAVITY,
						screenW - 700, location[1] + clickView.getHeight() + 3);
				break;
			default:
				break;
			}
		}
	}

	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		Log.i("scale", scale + "");
		return (int) (pxValue / scale + 0.5f);
	}

	private void initData() {
		Log.i("600 px", px2dip(context, 600) + "");
		Log.i("200 px", px2dip(context, 600) + "");
		List<Integer> list = new ArrayList<Integer>();
		list.add(R.string.pointer_date);
		list.add(R.string.pointer_month);
		list.add(R.string.pointer_year);
		sortView = new CommSortView(context, list,
				(LinearLayout) popView
						.findViewById(R.id.linearCommissionStatistics),
				R.string.pointer_date, 70, 35);
		sortView.setRefreshSortListener(new RefreshSortListener() {

			@Override
			public void doDataSort(int id) {
				switch (id) {
				case R.string.pointer_date:
					loadWebView("daily");
					break;
				case R.string.pointer_month:
					loadWebView("monthly");
					break;
				case R.string.pointer_year:
					loadWebView("yearly");
					break;
				default:
					break;
				}

			}
		});
		TextView txt_total_commission = (TextView) popView
				.findViewById(R.id.txt_total_commission);
		TextView txt_base_commission = (TextView) popView
				.findViewById(R.id.txt_base_commission);
		TextView txt_extra_commission = (TextView) popView
				.findViewById(R.id.txt_extra_commission);
		TextView txt_processing_commission = (TextView) popView
				.findViewById(R.id.txt_processing_commission);
		TextView txt_my_pointer = (TextView) popView
				.findViewById(R.id.txt_my_pointer);
		txt_total_commission.setText(String.valueOf(new BigDecimal(
				commissionStatistics.getTotalFixedCommission() + commissionStatistics.getTotalExtraCommission()).setScale(2,
				BigDecimal.ROUND_HALF_UP)));
		txt_base_commission.setText(String.valueOf(new BigDecimal(
				commissionStatistics.getTotalFixedCommission()).setScale(2,
				BigDecimal.ROUND_HALF_UP)));
		txt_extra_commission.setText(String.valueOf(new BigDecimal(
				commissionStatistics.getTotalExtraCommission()).setScale(2,
				BigDecimal.ROUND_HALF_UP)));
		txt_processing_commission.setText(String.valueOf(new BigDecimal(
				commissionStatistics.getCommissionInProcessing()).setScale(2,
				BigDecimal.ROUND_HALF_UP)));
		txt_my_pointer.setText(String.valueOf(new BigDecimal(
				commissionStatistics.getTotalPoint()).setScale(2,
				BigDecimal.ROUND_HALF_UP)));
		webView = (WebView) popView.findViewById(R.id.addGroupWebView);
		WebSettings webset = webView.getSettings();
		webset.setJavaScriptEnabled(true);
		// webset.setSupportZoom(true);
		webset.setUseWideViewPort(true);
		final String username = SPManager.getInstance().getStringValue(
				SPManager.USERNAME);
		final String paw = new String(Base64.decode(SPManager.getInstance()
				.getStringValue(SPManager.PWD), Base64.DEFAULT));

		WebViewClient wvcc = new WebViewClient() {
			public void onReceivedHttpAuthRequest(WebView view,
					HttpAuthHandler handler, String host, String realm) {
				handler.proceed(username, paw);
			};

		};
		webView.setWebViewClient(wvcc);
		// webView.setWebChromeClient(webChromeClient);
		webView.setInitialScale(75);
		loadWebView("daily");
	}

	void loadWebView(String type) {

		webView.loadUrl(Constant.FINSHINE_URL + "/app/gamecenter/user/"
				+ commissionStatistics.getUser_fk() + "/commission/report/"
				+ type);
	}

	public void getData() {
		SPManager spManager = SPManager.getInstance();
		long userId = spManager.getLongValue(SPManager.ID);
		Request request = new Request(RequestDefine.POINTER_RQ_POP_STATISTICS,
				userId, RequestType.GET, null, mhandler);
		CoreManager.getInstance().postRequest(request);
	}

	public void closePopWindow() {
		if (popWin != null) {
			if (popWin.isShowing()) {
				popWin.dismiss();
				// popWin = null;
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

}
