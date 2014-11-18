package com.incito.finshine.activity;

import com.android.core.net.http.volley.HttpService;
import com.android.core.util.AppToast;
import com.android.core.util.RefreshUtil;
import com.android.core.util.SharedUtil;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.custom.view.CommSortView;
import com.custom.view.CommSortView.RefreshSortListener;
import com.custom.view.DialogTaskGoingon;
import com.custom.view.DialogTaskGoingon.TaskAccept;
import com.custom.view.DialogTaskGoingon.TaskAcceptModel;
import com.custom.view.HorizontalListView;
import com.custom.view.PopPageFind;
import com.custom.view.PopPageMe;
import com.custom.view.PopTaskSelect;
import com.custom.view.PopTaskSelect.DataFilter;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.incito.finshine.R;
import com.incito.finshine.activity.adapter.AdapterTaskList;
import com.incito.finshine.activity.adapter.HorizontalListViewAdapter;
import com.incito.finshine.activity.dialog.CrmsDialog;
import com.incito.finshine.common.Constant;
import com.incito.finshine.entity.DataFilterModel;
import com.incito.finshine.entity.Difficulty;
import com.incito.finshine.entity.Props;
import com.incito.finshine.entity.Task;
import com.incito.finshine.entity.TaskStatus;
import com.incito.finshine.entity.TaskType;
import com.incito.finshine.entity.UserTaskWSEntity;
import com.incito.finshine.manager.SPManager;
import com.incito.finshine.network.HttpUtils;
import com.incito.finshine.network.HttpUtils.FaliureResult;
import com.incito.finshine.network.HttpUtils.SuccessReslut;
import com.incito.finshine.network.Request;
import com.incito.finshine.network.Request.RequestType;
import com.incito.finshine.network.RequestDefine;
import com.incito.utility.SharedKey;
import com.net.Data;
import com.net.DataRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <dl>
 * <dt>ActFinshinePage.java</dt>
 * <dd>Description:山榕项目首页</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 昆山灵动信息技术有限公司</dd>
 * <dd>CreateDate: 2014-6-24 下午1:58:18</dd>
 * </dl>
 * 
 * @author zw
 */
public class ActFinshineHomePage extends Activity implements OnClickListener, DataFilter, OnGestureListener {
	private static final String TAG = "ActFinshineHomePage";
	private Button btnFilter;
	private List<Integer> pageFindList;
	private List<Integer> pageMeList;
	private PullToRefreshListView listTask;
	private AdapterTaskList adapterTaskList;
	private List<UserTaskWSEntity> taskList;
	private List<UserTaskWSEntity> tempTaskList;
	private UserTaskWSEntity userTaskWSEntity;
	private static HorizontalListViewAdapter hlva;
	private HorizontalListView hlv;
	private List<Integer> pageNavigation;
	private List<Integer> pageNavigationPress;
	private int isProcess = 0;
	private int pageNum = 0;//
	private int pageSize = 6;// 每页显示条数

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_finishine_home_page);
		customerSort();
		init();
		loadSysconfig();
	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	@Override
	protected void onStart() {
		super.onStart();

	}

	public void init() {
		pageFindList = new ArrayList<Integer>();
		pageFindList.add(R.string.findproduct);
		pageFindList.add(R.string.finddeal);
		pageFindList.add(R.string.findkaiying);
		pageFindList.add(R.string.findnews);
		pageFindList.add(R.string.findcoll);
		pageFindList.add(R.string.findhelp);
		pageMeList = new ArrayList<Integer>();
		pageMeList.add(R.string.metool);
		pageMeList.add(R.string.meyongjin);
		pageMeList.add(R.string.meset);
		pageNavigation = new ArrayList<Integer>();
		pageNavigation.add(R.drawable.task_list_press);
		pageNavigation.add(R.drawable.task_contact_press);
		pageNavigation.add(R.drawable.task_find_press);
		pageNavigation.add(R.drawable.task_me_press);
		pageNavigationPress = new ArrayList<Integer>();
		pageNavigationPress.add(R.drawable.task_list_unpress);
		pageNavigationPress.add(R.drawable.task_contact_unpress);
		pageNavigationPress.add(R.drawable.task_find_unpress);
		pageNavigationPress.add(R.drawable.task_me_unpress);

		btnFilter = (Button) this.findViewById(R.id.btnFilter);
		btnFilter.setOnClickListener(this);

		if (isProcess == 1) {
			getTaskList();
			isProcess = 0;
		}

		listTask = (PullToRefreshListView) findViewById(R.id.listTaskDetail);
		listTask.setMode(Mode.BOTH);
		listTask.setOnRefreshListener(mRefreshListener);
		listTask.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				UserTaskWSEntity data = (UserTaskWSEntity) parent.getItemAtPosition(position);
				if (data == null) {
					return;
				}
				DialogTaskGoingon dialogTaskGoingon = new DialogTaskGoingon(ActFinshineHomePage.this, R.style.DialogTaskAccept, data, position);
				dialogTaskGoingon.setTaskAccept(new TaskAccept() {

					@Override
					public void taskAccept(TaskAcceptModel model) {

						try {
							if (!TextUtils.isEmpty(String.valueOf(model.getIsProcessing()))) {
								isProcess = model.getIsProcessing();
								// UserTaskWSEntity userTaskWSEntity=
								// taskList.get(currentIndex);
								// userTaskWSEntity.getStatus().setIsProcessing(model.getIsProcessing());
								adapterTaskList.notifyDataSetChanged();

							}
						} catch (Exception e) {
							e.printStackTrace();
							return;
						}
					}
				});
				dialogTaskGoingon.show();
				currentIndex = position;

			}
		});

		hlv = (HorizontalListView) findViewById(R.id.horizontallistview1);
		hlva = new HorizontalListViewAdapter(this, pageNavigation, pageNavigationPress);
		hlva.notifyDataSetChanged();
		hlv.setAdapter(hlva);
		hlv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Intent i = new Intent();
				if (arg2 == 0) {

				}
				if (arg2 == 1) {
					if (SharedUtil.getPreferBool(SharedKey.CONFIG_CRMS, false)) {
						i.setClass(arg1.getContext(), ActCustomerManagement.class);
						arg1.getContext().startActivity(i);
					} else {
						CrmsDialog dialog = new CrmsDialog(ActFinshineHomePage.this);
						dialog.show();
					}

				}
				if (arg2 == 2) {
					PopPageFind popFindDetail = new PopPageFind(ActFinshineHomePage.this, arg1, R.drawable.pop_page_find, PopPageFind.BOTTON_2_TOP,
							pageFindList);
					popFindDetail.showPopWindow();
				}
				if (arg2 == 3) {
					PopPageMe popPageMe = new PopPageMe(ActFinshineHomePage.this, arg1, R.drawable.pop_page_me, PopPageMe.BOTTON_2_TOP, pageMeList, getWindow());
					popPageMe.showPopWindow();
				}
				hlva.setSelectItem(arg2);
				hlva.notifyDataSetInvalidated();
			}
		});
		taskList = new ArrayList<UserTaskWSEntity>();
		getTaskList();

	}

	int sortId = R.string.task_default;

	// 客户排序
	private void customerSort() {

		List<Integer> list = new ArrayList<Integer>();
		list.add(R.string.task_default);
		list.add(R.string.task_new);
		list.add(R.string.task_degree);
		CommSortView sortView = new CommSortView(this, list, (LinearLayout) findViewById(R.id.sortButton1), R.string.task_default);
		sortView.setRefreshSortListener(new RefreshSortListener() {

			@Override
			public void doDataSort(int id) {

				adapterTaskList.sortData(id);
				// adapterTaskList.notifyDataSetChanged();
				sortId = id;
			}
		});
	}

	PopTaskSelect popTaskSelect;

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btnFilter:
			popTaskSelect = new PopTaskSelect(ActFinshineHomePage.this, v, R.drawable.pop_task_select, PopTaskSelect.RIGHT_2_LEFT);
			popTaskSelect.setDataFilter(this);
			popTaskSelect.showPopWindow();
			break;

		default:
			break;
		}
	}

	DataFilterModel dataFilterModel = new DataFilterModel();

	private void getTaskList() {
		JSONObject params;
		try {
			params = new JSONObject();
			params.put("user_fk", SPManager.getInstance().getLongValue(SPManager.ID));

			if (!TextUtils.isEmpty(dataFilterModel.getKeyword())) {
				params.put("keyword", dataFilterModel.getKeyword());
			}

			if (dataFilterModel.getTypeFK() != 0) {
				params.put("type_fk", dataFilterModel.getTypeFK());
			}

			if (dataFilterModel.getStatusFK() != 0) {
				params.put("status_fk", dataFilterModel.getStatusFK());
			}
			if (!TextUtils.isEmpty(dataFilterModel.getDifficultyName())) {
				params.put("difficultyName", dataFilterModel.getDifficultyName());
			}
			params.put("pageStart", pageNum);
			params.put("pageSize", pageSize);

		} catch (JSONException e) {
			e.printStackTrace();
			return;
		}
		HttpUtils httpUtils = new HttpUtils(this, RequestDefine.HOME_TASK_QUERY, RequestType.POST, params);
		httpUtils.setShowDiloag(true);
		httpUtils.setSuccessListener(new SuccessReslut() {

			@SuppressWarnings("null")
			@Override
			public void getResluts(JSONObject response) {

				if (Request.RESLUT_OK.equals(response.optString("status"))) {
					try {
						if (response.isNull("result")) {
						} else {
							tempTaskList = new ArrayList<UserTaskWSEntity>();
							JSONArray arr = response.getJSONArray("result");
							Gson gson = new Gson();

							Log.e(TAG, arr.toString());

							for (int i = 0; i < arr.length(); i++) {
								JSONObject item = arr.getJSONObject(i);
								userTaskWSEntity = new UserTaskWSEntity();
								// userTaskWSEntity =
								// gson.fromJson(item.toString(),UserTaskWSEntity.class);

								JSONArray bonusesArr = item.getJSONArray("bonuses");
								Log.e(TAG, bonusesArr.toString());

								ArrayList<Props> propsArrayList = new ArrayList<Props>();
								for (int m = 0; m < bonusesArr.length(); m++) {
									JSONObject bonusesObject = bonusesArr.getJSONObject(m);
									Log.e(TAG, bonusesObject.toString());
									Props props = gson.fromJson(bonusesObject.toString(), Props.class);
									propsArrayList.add(props);
								}
								userTaskWSEntity.setBonuses(propsArrayList);

								JSONObject jsonObject = item.getJSONObject("difficulty");
								Log.e(TAG, jsonObject.toString());
								Difficulty difficulty = gson.fromJson(jsonObject.toString(), Difficulty.class);
								userTaskWSEntity.setDifficulty(difficulty);

								jsonObject = item.getJSONObject("status");
								Log.e(TAG, jsonObject.toString());
								TaskStatus status = gson.fromJson(jsonObject.toString(), TaskStatus.class);
								userTaskWSEntity.setStatus(status);

								jsonObject = item.getJSONObject("task");
								Log.e(TAG, jsonObject.toString());
								Task task = gson.fromJson(jsonObject.toString(), Task.class);
								userTaskWSEntity.setTask(task);

								jsonObject = item.getJSONObject("type");
								Log.e(TAG, jsonObject.toString());
								TaskType type = gson.fromJson(jsonObject.toString(), TaskType.class);
								userTaskWSEntity.setType(type);

								JSONArray jsonArray = item.getJSONArray("taskAchievements");
								Log.e(TAG, jsonArray.toString());
								List<String> taskAchievements = new ArrayList<String>();
								for (int k = 0; k < jsonArray.length(); k++) {
									String taskcon = jsonArray.getString(k);
									taskAchievements.add(taskcon);
								}
								userTaskWSEntity.setTaskAchievements(taskAchievements);

								long dateOfCreate = item.getLong("dateOfCreate");
								userTaskWSEntity.setDateOfCreate(dateOfCreate);
								String dateOfExpire = item.getString("dateOfExpire");
								userTaskWSEntity.setDateOfExpire(dateOfExpire);
								String valueOfTarget = item.getString("valueOfTarget");
								userTaskWSEntity.setValueOfTarget(valueOfTarget);
								String valueOfInProcess = item.getString("valueOfInProcess");
								userTaskWSEntity.setValueOfInProcess(valueOfInProcess);
								int user_fk = item.getInt("user_fk");
								userTaskWSEntity.setUser_fk(user_fk);
								short isNew = (short) item.getInt("isNew");
								userTaskWSEntity.setIsNew(isNew);
								long id = item.getLong("id");
								userTaskWSEntity.setId(id);

								if (userTaskWSEntity.getTask().getIsEnabled() == 1) {
									if (!taskList.contains(userTaskWSEntity)) {
										taskList.add(userTaskWSEntity);
										tempTaskList.add(userTaskWSEntity);
									}

								}
							}
						}
						if (tempTaskList.size() == 0) {
							AppToast.ShowToast("已全部加载完成！");
						}
						// taskList.addAll(tempTaskList);

						if (pageNum != 0) {
							RefreshUtil.refreshComplete(listTask);

						} else {
							adapterTaskList = new AdapterTaskList(ActFinshineHomePage.this, taskList);
							listTask.setAdapter(adapterTaskList);
							RefreshUtil.refreshComplete(listTask);
						}
						adapterTaskList.notifyDataSetChanged();
						adapterTaskList.sortData(sortId);
						adapterTaskList.showTaskEndDialog();
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		});
		httpUtils.setFaliureResult(new FaliureResult() {

			@Override
			public void getResluts(String msg) {
				Toast.makeText(ActFinshineHomePage.this, "获取任务列表失败", 1).show();
			}
		});
		httpUtils.executeRequest();
	}

	@Override
	public void dataFilter(DataFilterModel model) {
		dataFilterModel = model;
		if (taskList != null)
			taskList.clear();
		pageNum = 0;
		getTaskList();

	}

	// @Override
	// public boolean onKeyDown(int keyCode, KeyEvent event) {
	//
	// if (keyCode == event.KEYCODE_BACK && event.getRepeatCount() == 0) {
	// return false;
	// }
	// return super.onKeyDown(keyCode, event);
	// }

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		// TODO Auto-generated method stub
		return false;
	}

	int currentIndex = 0;

	/**
	 * @description 下拉刷新上拉加载更多
	 * @author Andy.fang
	 */
	OnRefreshListener<ListView> mRefreshListener = new OnRefreshListener<ListView>() {
		@Override
		public void onRefresh(PullToRefreshBase<ListView> refreshView) {

			if (refreshView.isHeaderShown()) {
				AppToast.ShowToast("下拉刷新");
				pageSize = taskList.size();
				pageNum = 0;
				taskList = new ArrayList<UserTaskWSEntity>();
				getTaskList();
			} else {
				AppToast.ShowToast("上拉加载更多");
				pageNum += pageSize;
				tempTaskList = null;
				getTaskList();
			}

		}
	};

	/**
	 * @description 加载客户端模块打开/关闭开关
	 * @author Andy.fang
	 * @createDate 2014年8月19日
	 */
	void loadSysconfig() {
		String url = Constant.getRootUrl() + "/app/sysconfig";
		DataRequest request = new DataRequest(url, new Listener<Data>() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				AppToast.ShowToast(arg0.getMessage());
			}

			@Override
			public void onResponse(Data data) {
				if ("0".equals(data.getStatus())) {
					List<HashMap<String, String>> result = data.getResult();
					if (result != null && result.size() > 0) {
						HashMap<String, String> map = result.get(0);
						String value = map.get("value");
						String key = map.get("key");
						if ("crms".equals(key) && "1".equals(value)) {
							SharedUtil.setPreferBool(SharedKey.CONFIG_CRMS, true);
						} else {
							SharedUtil.setPreferBool(SharedKey.CONFIG_CRMS, false);
						}
						//AppToast.ShowToast("value==" + value);
					}
				} else {
					AppToast.ShowToast(data.getMsg());
				}
			}

			@Override
			public void onStart() {

			}

		});
		HttpService.VOLLEY.startCashLoad(null, request, true);
	}

	public static void backHome() {
		// TODO Auto-generated method stub
		if (hlva != null) {
			hlva.setSelectItem(0);
			hlva.notifyDataSetInvalidated();
		}
	}
}
