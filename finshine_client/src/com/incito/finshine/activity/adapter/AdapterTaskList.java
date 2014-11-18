package com.incito.finshine.activity.adapter;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.json.JSONObject;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.android.core.util.SharedUtil;
import com.custom.view.DialogTaskFinish;
import com.incito.finshine.R;
import com.incito.finshine.entity.UserTaskWSEntity;
import com.incito.finshine.manager.SPManager;
import com.incito.finshine.network.HttpUtils;
import com.incito.finshine.network.Request;
import com.incito.finshine.network.RequestDefine;
import com.incito.finshine.network.HttpUtils.FaliureResult;
import com.incito.finshine.network.HttpUtils.SuccessReslut;
import com.incito.finshine.network.Request.RequestType;

public class AdapterTaskList extends BaseAdapter implements
		Comparator<UserTaskWSEntity> {
	private static final String TAG = "AdapterTaskList";
	private List<UserTaskWSEntity> tempTaskList = null;//
	private LayoutInflater flater = null;
	private Context context;
	private TextView tvTaskName, tvTaskContent, tvTaskDate;
	private Button btnTaskAcccept;

	public AdapterTaskList(Context context, List<UserTaskWSEntity> tempTaskList) {
		super();
		this.context = context;
		this.tempTaskList = tempTaskList;
		this.flater = LayoutInflater.from(context);

	}

	@Override
	public int getCount() {
		return tempTaskList == null ? 0 : tempTaskList.size();
	}

	@Override
	public Object getItem(int position) {
		return tempTaskList == null ? null : tempTaskList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = (View) flater.inflate(R.layout.row_task_show, null);
		}
		tvTaskName = (TextView) convertView.findViewById(R.id.tv_task_name);
		tvTaskDate = (TextView) convertView.findViewById(R.id.tv_task_date);
		tvTaskContent = (TextView) convertView
				.findViewById(R.id.tv_task_content);
		btnTaskAcccept = (Button) convertView.findViewById(R.id.bt_task_accept);
		tvTaskName.setText(tempTaskList.get(position).getType().getName());
		long diff = System.currentTimeMillis()
				- tempTaskList.get(position).getTask().getDateOfCreate();
		long m = diff / (60 * 1000);
		if (m < 60) {
			tvTaskDate.setText(m + "分钟前");
		} else if (m < 60 * 24) {
			tvTaskDate.setText(m / 60 + "小时前");
		} else {
			tvTaskDate.setText(m / (60 * 24) + "天前");
		}

		switch ((int) tempTaskList.get(position).getStatus().getId()) {
		case 30:
			btnTaskAcccept.setText("已过期");
			break;
		case 20:
			btnTaskAcccept.setText("已完成");
			break;
		case 10:
			btnTaskAcccept.setText("进行中");
			break;
		case -1:
			btnTaskAcccept.setText("接受任务");
			btnTaskAcccept.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if(tempTaskList.get(position).getStatus().getId() == -1){
						getAcceptTaskList(tempTaskList.get(position));
					}					
				}
			});
			break;

		default:
			break;
		}
		tvTaskContent.setText(tempTaskList.get(position).getTask().getName());

		return convertView;
	}

	public void getAcceptTaskList(final UserTaskWSEntity userTaskWSEntity) {
		JSONObject json = null;
		try {
			json = new JSONObject();
			json.put("user_fk",
					SPManager.getInstance().getLongValue(SPManager.ID));
			json.put("task_fk", userTaskWSEntity.getTask().getId());
			Log.i(TAG, "更新后的用户json数据为" + json.toString());
		} catch (Exception e) {
		}
		HttpUtils httpUtils = new HttpUtils(context,
				RequestDefine.TASK_ACCEP_TASK, RequestType.PUT, json);
		httpUtils.setShowDiloag(false);
		httpUtils
				.setUser_fk(SPManager.getInstance().getLongValue(SPManager.ID));
		httpUtils.setTask_fk(userTaskWSEntity.getTask().getId());
		httpUtils.setSuccessListener(new SuccessReslut() {

			@Override
			public void getResluts(JSONObject response) {

				if (Request.RESLUT_OK.equals(response.optString("status"))) {

					Toast.makeText(context,
							userTaskWSEntity.getTask().getName() + "已接受", 1)
							.show();
					userTaskWSEntity.getStatus().setId(10);
					notifyDataSetChanged();

				}
			}
		});
		httpUtils.setFaliureResult(new FaliureResult() {

			@Override
			public void getResluts(String msg) {
				Toast.makeText(context, "获取任务失败", 1).show();
			}
		});
		httpUtils.executeRequest();
	}

	@Override
	public int compare(UserTaskWSEntity cs1, UserTaskWSEntity cs2) {
		int i = 0;
		switch (currentSortValue) {
		case R.string.task_default:
			i = 1;

		case R.string.task_new:
			if (cs1.getDateOfCreate() > cs2.getDateOfCreate())
				i = -1;
			else if (cs1.getDateOfCreate() == cs2.getDateOfCreate())
				i = 0;
			else
				i = 1;
			break;

		case R.string.task_degree:
			if (cs1.getDifficulty().getCoefficient() > cs2.getDifficulty()
					.getCoefficient())
				i = -1;
			else if (cs1.getDifficulty().getCoefficient() == cs2
					.getDifficulty().getCoefficient())
				i = 0;
			else
				i = 1;
			break;

		default:
			break;
		}
		return i;
	}

	private int currentSortValue = R.string.total_asset;

	public interface RefreshData {

		void refreshData();
	}

	public void sortData(int currentSort) {
		this.currentSortValue = currentSort;
		Collections.sort(tempTaskList, this);
		// notifyDataSetChanged();
	}

	Handler handler = new Handler();

	public void showTaskEndDialog() {
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				if (tempTaskList != null && tempTaskList.size() > 0) {

					for (UserTaskWSEntity u : tempTaskList) {

						if (u.getStatus().getIsFinished() == 1) {
							String uString = SharedUtil.getPreferStr("task_"
									+ u.getTask().getId());
							if (TextUtils.isEmpty(uString)
									|| uString.equals("0")) {
								DialogTaskFinish dialogTaskFinish = new DialogTaskFinish(
										context, u);
								dialogTaskFinish
										.setCanceledOnTouchOutside(false);
								dialogTaskFinish.show();
							}

						}

					}

				}

			}
		}, 500);

	}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();		
	}

}
