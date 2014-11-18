package com.incito.finshine.activity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart;
import org.achartengine.chart.LineChart;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.HttpAuthHandler;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.ZoomDensity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.custom.view.CommonWaitDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.incito.finshine.R;
import com.incito.finshine.activity.adapter.AdapterPopCustomerMarketing;
import com.incito.finshine.common.Constant;
import com.incito.finshine.entity.Customer;
import com.incito.finshine.entity.InvestmentAnalysis;
import com.incito.finshine.entity.InvestmentAnalysisStatistics;
import com.incito.finshine.entity.InvestmentDistributionStatistics;
import com.incito.finshine.manager.CoreManager;
import com.incito.finshine.manager.SPManager;
import com.incito.finshine.network.Request;
import com.incito.finshine.network.Request.RequestType;
import com.incito.finshine.network.RequestDefine;
import com.incito.wisdomsdk.net.http.JsonHttpResponseHandler;

/**
 * <dl>
 * <dt>FraInvestAnalisy.java</dt>
 * <dd>Description:客户管理的 投资分析/dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 昆山灵动信息技术有限公司</dd>
 * <dd>CreateDate: 2014-6-5 上午9:37:25</dd>
 * </dl>
 * 
 * @author lihs
 */
public class FragmentInvestAnalisy extends FragmentDetail {

	private static final String TAG = FragmentInvestAnalisy.class
			.getSimpleName();

	private Customer customer;
	private CommonWaitDialog waitDlg = null;
	private View view = null;
	private InvestmentAnalysisStatistics analysis;
	private List<InvestmentDistributionStatistics> listDistribution;
	private List<InvestmentAnalysis> invesList;

	private TextView txtCustomerName;
	private TextView textInvestTotalAsserts;
	private TextView cumulativeTotalRevenue;
	private TextView averageExpectedRevenue;
	private TextView averageInvestmentPeriod;
	private TextView totalPurchasedQty;
	private TextView averageActualRevenue;

	private ListView listInvestMarketing;
	private TextView noInfo;
	private AdapterPopCustomerMarketing adapterMarketing;

	private WebView webView;
	public static final int F_BASE_INFO = 0;
	private long customerID = -1;

	public static FragmentInvestAnalisy newInstance(int id) {

		FragmentInvestAnalisy f = new FragmentInvestAnalisy();
		Bundle args = new Bundle();
		args.putInt(FIELD_ID, id);
		f.setArguments(args);
		return f;
	}

	// 左一
	private JsonHttpResponseHandler handlerGetInvestAnalysis = new JsonHttpResponseHandler() {
		@Override
		public void onSuccess(JSONObject response) {
			Log.i(TAG, "success o= " + response.toString());

			int statues = response.optInt("status");
			if (0 == statues) {

				Log.i(TAG, "success a= " + response.toString());
				Gson gson = new Gson();
				analysis = gson.fromJson(response.optString("item"),
						InvestmentAnalysisStatistics.class);

				if (analysis != null) {
					initDate(true);
					getInvestDistribution();
				} else
					initDate(false);
				
				closeDialog();

			} else
				closeDialog();
		}

		@Override
		public void onSuccess(JSONArray response) {
			closeDialog();
			Log.i(TAG, "success a= " + response.toString());
		}

		@Override
		public void onFailure(Throwable error, String content) {
			closeDialog();
			Log.i(TAG, "onFailure = " + content);
		}
	};

	private JsonHttpResponseHandler handlerGetInvestDistribution = new JsonHttpResponseHandler() {
		@Override
		public void onSuccess(JSONObject response) {
			Log.i(TAG, "success o= " + response.toString());

			int statues = response.optInt("status");
			if (0 == statues) {

				Log.i(TAG, "success a= " + response.toString());
				Gson gson = new Gson();
				listDistribution = gson
						.fromJson(
								response.optString("list"),
								new TypeToken<List<InvestmentDistributionStatistics>>() {
								}.getType());

				/*if (listDistribution != null) {
					if (adapterMarketing != null) {
						adapterMarketing.setItemList(listDistribution);
						adapterMarketing
								.setLayout(R.layout.row_invest_analysis);
						listInvestMarketing.setAdapter(adapterMarketing);

						getAnalysisList();
					}
				}*/
				if (listDistribution != null) {
					if (adapterMarketing != null) {
						adapterMarketing.setItemList(listDistribution);
						adapterMarketing
								.setLayout(R.layout.row_invest_analysis);
						listInvestMarketing.setAdapter(adapterMarketing);
						if(listDistribution.size()==0){
							noInfo.setVisibility(0);
							listInvestMarketing.setVisibility(8);
						}else{
							noInfo.setVisibility(8);
							listInvestMarketing.setVisibility(0);
						}
						
						
					}else{
						noInfo.setVisibility(0);
						listInvestMarketing.setVisibility(8);
					}
				} else {
					noInfo.setVisibility(0);
					listInvestMarketing.setVisibility(8);
				}

			}
			closeDialog();
		}

		@Override
		public void onSuccess(JSONArray response) {
			closeDialog();
			Log.i(TAG, "success a= " + response.toString());
		}

		@Override
		public void onFailure(Throwable error, String content) {
			closeDialog();
			Log.i(TAG, "onFailure = " + content);
		}
	};

	private JsonHttpResponseHandler handlerGetAnalysisList = new JsonHttpResponseHandler() {
		@Override
		public void onSuccess(JSONObject response) {
			Log.i(TAG, "success o= " + response.toString());

			int statues = response.optInt("status");
			if (0 == statues) {

				Log.i(TAG, "success a= " + response.toString());
				Gson gson = new Gson();
				invesList = gson.fromJson(response.optString("list"),
						new TypeToken<List<InvestmentAnalysis>>() {
						}.getType());
				startBarChart();
			}
		}

		@Override
		public void onSuccess(JSONArray response) {
			closeDialog();
			Log.i(TAG, "success a= " + response.toString());
		}

		@Override
		public void onFailure(Throwable error, String content) {
			closeDialog();
			Log.i(TAG, "onFailure = " + content);
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.fra_invest_analyise, null);

		customer = ((ActCustomerDetail) getActivity()).getCustomer();

		init();
		initCustomer();
		getAnalysis();
		getGraphicalData();
		return view;
	}

	private void getGraphicalData() {
		// TODO Auto-generated method stub

	}

	private void initCustomer() {
		txtCustomerName.setText(customer.getName()
				+ (customer.getGender() == 1 ? "先生" : "女士"));
	}

	private void init() {// 添加webview
		webView = (WebView) view.findViewById(R.id.investweb);
		WebSettings webset = webView.getSettings();
		webset.setJavaScriptEnabled(true);
		//webset.setLoadWithOverviewMode(true);
		webset.setUseWideViewPort(true);
		final String username=SPManager.getInstance().getStringValue(SPManager.USERNAME);
		final String paw=new String(Base64.decode(SPManager.getInstance().getStringValue(SPManager.PWD), Base64.DEFAULT));

		WebViewClient wvcc = new WebViewClient() {
			public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
				handler.proceed(username, paw);
			};

		};
		webView.setWebViewClient(wvcc);
		webView.setInitialScale(75);

		webView.loadUrl(Constant.FINSHINE_URL+"/app/customer/"+ customer.getId() + "/returnanalysis");
		
		txtCustomerName = (TextView) view.findViewById(R.id.txtCustomerName);
		textInvestTotalAsserts = (TextView) view
				.findViewById(R.id.textInvestTotalAsserts);
		cumulativeTotalRevenue = (TextView) view
				.findViewById(R.id.cumulativeTotalRevenue);
		averageExpectedRevenue = (TextView) view
				.findViewById(R.id.averageExpectedRevenue);
		averageInvestmentPeriod = (TextView) view
				.findViewById(R.id.averageInvestmentPeriod);
		totalPurchasedQty = (TextView) view
				.findViewById(R.id.totalPurchasedQty);
		averageActualRevenue = (TextView) view
				.findViewById(R.id.averageActualRevenue);

		listInvestMarketing = (ListView) view.findViewById(R.id.investList);
		noInfo=(TextView) view.findViewById(R.id.noInfo);
		if (adapterMarketing == null)
			adapterMarketing = new AdapterPopCustomerMarketing(getActivity());
	}

	private void initDate(boolean flag) {
		txtCustomerName.setText(customer.getName()
				+ (customer.getGender() == 1 ? "先生" : "女士"));
		if (flag) {
			textInvestTotalAsserts.setText(analysis.getInvestmentTotalAmount()
					.setScale(0, BigDecimal.ROUND_HALF_UP) + "万");
			cumulativeTotalRevenue.setText(analysis.getCumulativeTotalRevenue()
					.setScale(2, BigDecimal.ROUND_HALF_UP) + "万");
			averageExpectedRevenue.setText(analysis.getAverageExpectedRevenue()
					.setScale(2, BigDecimal.ROUND_HALF_UP) + "%");
			averageInvestmentPeriod.setText(analysis
					.getAverageInvestmentPeriod().setScale(0,
							BigDecimal.ROUND_HALF_UP)
					+ "");
			totalPurchasedQty.setText(analysis.getTotalPurchasedQty() + "");
			averageActualRevenue.setText(analysis.getAverageActualRevenue()
					.setScale(2, BigDecimal.ROUND_HALF_UP) + "%");
		} else {
			textInvestTotalAsserts.setText("0万");
			cumulativeTotalRevenue.setText("0万");
			averageExpectedRevenue.setText("0%");
			averageInvestmentPeriod.setText("0");
			totalPurchasedQty.setText("0%");
		}

	}

	// 左一
	private boolean getAnalysis() {
		waitDlg = new CommonWaitDialog(this.getActivity(), "",
				R.string.load_data);

		JSONObject json = new JSONObject();
		try {
			json.put("salesId",
					SPManager.getInstance().getLongValue(SPManager.ID));
			json.put("customerId", customer.getId());
		} catch (JSONException e) {
			e.printStackTrace();
		}

		Request request = new Request(RequestDefine.RQ_CUSTOMER_POST_ANALYSIS,
				RequestType.POST, json, handlerGetInvestAnalysis);
		CoreManager.getInstance().postRequest(request);
		return true;
	}

	// 右一
	private boolean getInvestDistribution() {
		JSONObject json = new JSONObject();
		try {
			json.put("customerId", customer.getId());
		} catch (JSONException e) {
			e.printStackTrace();
		}

		Request request = new Request(
				RequestDefine.RQ_CUSTOMER_POST_INVEST_DISTRIBUTION,
				RequestType.POST, json, handlerGetInvestDistribution);
		CoreManager.getInstance().postRequest(request);
		return true;
	}

	// 统计图信息
	private boolean getAnalysisList() {
		JSONObject json = new JSONObject();
		try {
			json.put("customerId", customer.getId());
			json.put("yearParam", 5);
			json.put("quarterParam", 8);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		Request request = new Request(
				RequestDefine.RQ_CUSTOMER_POST_ANALYSISLIST, RequestType.POST,
				json, handlerGetAnalysisList);
		CoreManager.getInstance().postRequest(request);
		return true;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		// startBarChart();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	/***********************************************************************************/
	private XYMultipleSeriesDataset dataset;
	private XYMultipleSeriesRenderer renderer;

	@SuppressLint("ResourceAsColor")
	private void startBarChart() {

		GraphicalView views = getView(getActivity());
		LinearLayout lt = (LinearLayout) view.findViewById(R.id.addView);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		lt.setBackgroundColor(android.R.color.white);
		lt.addView(views, params);

	}

	public GraphicalView getView(Context context) {
		double[] cumulativeGain = new double[invesList.size()];
		double[] CurrentInvestment = new double[invesList.size()];
		double[] QuarterReturn = new double[invesList.size()];
		for (int i = 0; i < invesList.size(); i++) {
			cumulativeGain[i] = invesList.get(0).getCumulativeGain();
			System.out.println(cumulativeGain[i]);
			CurrentInvestment[i] = invesList.get(0).getCurrentInvestment();
			System.out.println(CurrentInvestment[i]);
			QuarterReturn[i] = invesList.get(i).getQuarterReturn();
			System.out.println(QuarterReturn[i]);
		}
		String[] titles = new String[] { "", "" };
		List<double[]> values = new ArrayList<double[]>();
		values.add(cumulativeGain);
		values.add(CurrentInvestment);
		int[] colors = new int[] { Color.BLUE, Color.CYAN };
		XYMultipleSeriesRenderer renderer = buildBarRenderer(colors);
		setChartSettings(renderer, "Monthly", "Month", "Units sold", 0.5, 8.5,
				0, 5200, Color.GRAY, Color.LTGRAY);
		renderer.getSeriesRendererAt(0).setDisplayChartValues(true);
		renderer.getSeriesRendererAt(1).setDisplayChartValues(true);
		renderer.setXLabels(12);
		renderer.setYLabels(10);
		renderer.setXLabelsAlign(Align.CENTER);
		renderer.setYLabelsAlign(Align.RIGHT);
		renderer.setPanEnabled(true, false);

		renderer.setYLabelsAlign(Align.RIGHT);
		renderer.setShowGrid(true);
		renderer.setShowGridX(true);
		renderer.setShowGridY(true);
		renderer.setShowCustomTextGrid(true);

		renderer.setXLabelsAlign(Align.CENTER);
		renderer.setPanEnabled(false, false);// 设置x,y坐标轴不会因用户划动屏幕而移动
		renderer.setBackgroundColor(Color.WHITE); // 设置表格背景色透明
		renderer.setApplyBackgroundColor(true); // 使背景色生效
		// 设置4边留白透明
		renderer.setMarginsColor(Color.argb(0, 0xff, 0, 0));

		renderer.setAxisTitleTextSize(16); // 设置坐标轴标题文本大小
		renderer.setAxesColor(Color.BLUE);
		//
		renderer.setChartTitleTextSize(20); // 设置图表标题文本大小
		//
		renderer.setLabelsTextSize(20); // 设置轴标签文本大小
		//
		renderer.setLegendTextSize(20); // 设置图例文本大小
		//
		renderer.setMargins(new int[] { 50, 50, 40, 0 }); // 设置4边留白、上坐下有

		renderer.setZoomRate(1.1f);
		renderer.setBarSpacing(0.5f);

		XYMultipleSeriesDataset dateSet = buildBarDataset(titles, values);

		String[] titles2 = new String[] { "", "" };
		List<double[]> x = new ArrayList<double[]>();
		for (int i = 0; i < titles2.length; i++) {
			x.add(new double[] { 1, 2, 3, 4, 5, 6, 7, 8 });
		}
		List<double[]> y = new ArrayList<double[]>();
		y.add(QuarterReturn);
		y.add(new double[] { 0, 0, 0, 0, 0, 0, 0, 0 });

		int[] colors2 = new int[] { Color.GREEN, Color.rgb(200, 150, 0) };
		PointStyle[] styles = new PointStyle[] { PointStyle.CIRCLE,
				PointStyle.DIAMOND };
		renderer = buildRenderer(renderer, colors2, styles);
		renderer.setPointSize(5.5f);

		int length = renderer.getSeriesRendererCount();
		for (int i = 2; i < length; i++) {
			XYSeriesRenderer r = (XYSeriesRenderer) renderer
					.getSeriesRendererAt(i);
			r.setLineWidth(5);
			r.setFillPoints(true);
		}
		setChartSettings(renderer, "Weather data", "Month", "Temperature", 0.5,
				8.5, 0, 5500, Color.LTGRAY, Color.LTGRAY);

		dateSet = buildDataset(dateSet, titles2, x, y);

		renderer.setXLabels(12);
		renderer.setYLabels(10);
		renderer.setShowGrid(true);
		renderer.setXLabelsAlign(Align.RIGHT);
		renderer.setYLabelsAlign(Align.RIGHT);
		renderer.setZoomButtonsVisible(true);
		renderer.setPanLimits(new double[] { -10, 20, -10, 40 });
		renderer.setZoomLimits(new double[] { -10, 20, -10, 40 });

		String[] types = new String[] { BarChart.TYPE, BarChart.TYPE,
				LineChart.TYPE, LineChart.TYPE };
		return ChartFactory.getCombinedXYChartView(context, dateSet, renderer,
				types);

	}

	protected XYMultipleSeriesRenderer buildBarRenderer(int[] colors) {

		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		renderer.setAxisTitleTextSize(16);
		renderer.setChartTitleTextSize(20);
		renderer.setLabelsTextSize(15);
		renderer.setLegendTextSize(15);
		int length = colors.length;
		for (int i = 0; i < length; i++) {
			SimpleSeriesRenderer r = new SimpleSeriesRenderer();
			r.setColor(colors[i]);
			renderer.addSeriesRenderer(r);
		}
		return renderer;
	}

	protected XYMultipleSeriesDataset buildDataset(
			XYMultipleSeriesDataset dataset, String[] titles,
			List<double[]> xValues, List<double[]> yValues) {
		// XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		addXYSeries(dataset, titles, xValues, yValues, 0);
		return dataset;
	}

	public void addXYSeries(XYMultipleSeriesDataset dataset, String[] titles,
			List<double[]> xValues, List<double[]> yValues, int scale) {
		int length = titles.length;
		for (int i = 0; i < length; i++) {
			XYSeries series = new XYSeries(titles[i], scale);
			double[] xV = xValues.get(i);
			double[] yV = yValues.get(i);
			int seriesLength = xV.length;
			for (int k = 0; k < seriesLength; k++) {
				series.add(xV[k], yV[k]);
			}
			dataset.addSeries(series);
		}
	}

	protected XYMultipleSeriesRenderer buildRenderer(
			XYMultipleSeriesRenderer renderer, int[] colors, PointStyle[] styles) {
		setRenderer(renderer, colors, styles);
		return renderer;
	}

	protected void setRenderer(XYMultipleSeriesRenderer renderer, int[] colors,
			PointStyle[] styles) {
		renderer.setAxisTitleTextSize(16);
		renderer.setChartTitleTextSize(20);
		renderer.setLabelsTextSize(15);
		renderer.setLegendTextSize(15);
		renderer.setPointSize(5f);
		renderer.setMargins(new int[] { 20, 30, 15, 20 });
		int length = colors.length;
		for (int i = 0; i < length; i++) {
			XYSeriesRenderer r = new XYSeriesRenderer();
			r.setColor(colors[i]);
			r.setPointStyle(styles[i]);
			renderer.addSeriesRenderer(r);
		}
	}

	protected void setChartSettings(XYMultipleSeriesRenderer renderer,
			String title, String xTitle, String yTitle, double xMin,
			double xMax, double yMin, double yMax, int axesColor,
			int labelsColor) {
		// renderer.setChartTitle(title);
		// renderer.setXTitle(xTitle);
		// renderer.setYTitle(yTitle);
		renderer.setXAxisMin(xMin);
		renderer.setXAxisMax(xMax);
		renderer.setYAxisMin(yMin);
		renderer.setYAxisMax(yMax);
		renderer.setAxesColor(axesColor);
		renderer.setLabelsColor(labelsColor);
	}

	protected XYMultipleSeriesDataset buildBarDataset(String[] titles,
			List<double[]> values) {
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		int length = titles.length;
		for (int i = 0; i < length; i++) {
			CategorySeries series = new CategorySeries(titles[i]);
			double[] v = values.get(i);
			int seriesLength = v.length;
			for (int k = 0; k < seriesLength; k++) {
				series.add(v[k]);
			}
			dataset.addSeries(series.toXYSeries());
		}
		return dataset;
	}

	private void closeDialog() {

		if (waitDlg != null) {
			waitDlg.clearAnimation();
		}
	}
}
