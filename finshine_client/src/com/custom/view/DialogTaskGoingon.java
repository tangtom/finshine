package com.custom.view;

import org.json.JSONObject;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.incito.finshine.R;
import com.incito.finshine.entity.Props;
import com.incito.finshine.entity.UserTaskWSEntity;
import com.incito.finshine.manager.SPManager;
import com.incito.finshine.network.HttpUtils;
import com.incito.finshine.network.HttpUtils.FaliureResult;
import com.incito.finshine.network.HttpUtils.SuccessReslut;
import com.incito.finshine.network.Request;
import com.incito.finshine.network.Request.RequestType;
import com.incito.finshine.network.RequestDefine;

public class DialogTaskGoingon extends Dialog implements OnClickListener {
	private Context context;
	private UserTaskWSEntity userTaskWSEntity;
	private RatingBar ratingBar1;
	private TextView textTaskDegree, textTaskContent1, textTaskCondition1,
			tv_task_goingon2, textTaskTitle, tv_task_process, tv_task_target;
	private LinearLayout linearLayoutBonuses;
	private int index;
	Button acceptButton;
	private ImageView imageTask, imageFinish, imageTaskTarget;
	private LayoutInflater inflater;
	private LinearLayout lineartask, lineartaskprocess;
	private TaskAccept taskAccept;
	TaskAcceptModel taskAcceptModel = new TaskAcceptModel();
	LayoutParams para;

	public TaskAccept getTaskAccept() {
		return taskAccept;
	}

	public void setTaskAccept(TaskAccept taskAccept) {
		this.taskAccept = taskAccept;
	}

	public DialogTaskGoingon(Context context) {
		super(context);
		this.context = context;
	}

	public DialogTaskGoingon(Context context, int theme,
			UserTaskWSEntity userTaskWSEntity, int index) {
		super(context, theme);
		this.context = context;
		this.userTaskWSEntity = userTaskWSEntity;
		this.index = index;
	}

	public DialogTaskGoingon(Context context, int theme) {
		super(context, theme);
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.dlg_task_goingon);
		loadViewData();

	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
	}

	void loadViewData() {
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		textTaskTitle = (TextView) findViewById(R.id.textTaskTitle);
		textTaskContent1 = (TextView) findViewById(R.id.textTaskContent1);
		textTaskDegree = (TextView) findViewById(R.id.textTaskDegree);
		textTaskCondition1 = (TextView) findViewById(R.id.textTaskCondition1);
		tv_task_process = (TextView) findViewById(R.id.tv_task_process);
		tv_task_target = (TextView) findViewById(R.id.tv_task_target);
		tv_task_goingon2 = (TextView) findViewById(R.id.tv_task_goingon2);
		ratingBar1 = (RatingBar) findViewById(R.id.ratingBar1);
		linearLayoutBonuses = (LinearLayout) findViewById(R.id.linearLayoutBonuses);
		acceptButton = (Button) findViewById(R.id.button_accept);
		acceptButton.setOnClickListener(this);
		imageTask = (ImageView) findViewById(R.id.imageTask);
		imageFinish = (ImageView) findViewById(R.id.iv_task_timeout);
		imageTaskTarget = (ImageView) findViewById(R.id.imageTaskTarget);
		lineartask = (LinearLayout) findViewById(R.id.lineartask);
		lineartaskprocess = (LinearLayout) findViewById(R.id.lineartaskprocess);
		LayoutParams para;
		para = imageTask.getLayoutParams();
		// para.height = imageTask.getHeight();

		textTaskTitle.setText(userTaskWSEntity.getTask().getName());
		textTaskContent1.setText(userTaskWSEntity.getTask().getMemo());
		StringBuffer sbTaskCondition = new StringBuffer();
		for (int i = 0; i < userTaskWSEntity.getTaskAchievements().size(); i++) {
			sbTaskCondition.append(
					userTaskWSEntity.getTaskAchievements().get(i)).append("\n");
		}
		textTaskCondition1.setText(sbTaskCondition);
		ratingBar1.setNumStars(6);
		ratingBar1.setStepSize(1);
		ratingBar1.setRating(userTaskWSEntity.getDifficulty().getCoefficient());

		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.MATCH_PARENT);
		if (userTaskWSEntity.getBonuses() != null
				&& userTaskWSEntity.getBonuses().size() > 0) {
			for (Props p : userTaskWSEntity.getBonuses()) {
				LinearLayout layout = (LinearLayout) inflater.inflate(
						R.layout.dlg_row_task_finished, null);
				TextView textTaskReward1 = (TextView) layout
						.findViewById(R.id.textTaskReward1);
				TextView textTaskReward2 = (TextView) layout
						.findViewById(R.id.textTaskReward2);
				textTaskReward1.setText(p.getPropertyValue());
				textTaskReward2.setText(p.getBonusQty() + "");
				linearLayoutBonuses.addView(layout);
			}

		}
		long diff = userTaskWSEntity.getTask().getDateOfExpire()
				- System.currentTimeMillis();
		long d = diff / (24 * 60 * 60 * 1000);
		if (d < 1) {
			d = 0;
		}
		tv_task_goingon2.setText("" + d + "");

		if ("".equals(userTaskWSEntity.getValueOfTarget())
				|| userTaskWSEntity.getValueOfTarget() == null) {
			tv_task_target.setText("1");
		} else {
			tv_task_process.setText((int) parseDouble(userTaskWSEntity
					.getValueOfInProcess()) + "");
			tv_task_target.setText((int) parseDouble(userTaskWSEntity
					.getValueOfTarget()) + "");
			int w = View.MeasureSpec.makeMeasureSpec(0,
					View.MeasureSpec.UNSPECIFIED);
			int h = View.MeasureSpec.makeMeasureSpec(0,
					View.MeasureSpec.UNSPECIFIED);
			imageTaskTarget.measure(w, h);

			int widthTarget = imageTaskTarget.getMeasuredWidth();

			Log.i("widthTarget", widthTarget + "");
			para.width = (int) (widthTarget * (parseDouble(userTaskWSEntity
					.getValueOfInProcess()) / parseDouble(userTaskWSEntity
					.getValueOfTarget())));
			Log.i("para.width", para.width + "");
			imageTask.setLayoutParams(para);
		}

		switch ((int) userTaskWSEntity.getStatus().getId()) {
		case 30:
			lineartaskprocess.setVisibility(View.GONE);
			lineartask.setVisibility(View.GONE);
			acceptButton.setVisibility(View.INVISIBLE);
			imageFinish.setBackgroundResource(R.drawable.task_time_out);
			break;
		case 20:
			acceptButton.setVisibility(View.INVISIBLE);
			imageFinish.setBackgroundResource(R.drawable.task_finish_logo);
			textTaskDegree.setText("任务完成");
			if ("".equals(userTaskWSEntity.getValueOfTarget())
					|| userTaskWSEntity.getValueOfTarget() == null) {
				tv_task_process.setText("1");
			}
			break;
		case 10:
			acceptButton.setVisibility(View.INVISIBLE);
			imageFinish.setVisibility(View.INVISIBLE);
			textTaskDegree.setText("任务"
					+ userTaskWSEntity.getStatus().getName());
			if ("".equals(userTaskWSEntity.getValueOfTarget())
					|| userTaskWSEntity.getValueOfTarget() == null) {
				tv_task_process.setText("0");
			}
			break;
		case -1:
			acceptButton.setText("接受任务");
			imageFinish.setVisibility(View.INVISIBLE);
			textTaskDegree.setText("任务");
			if ("".equals(userTaskWSEntity.getValueOfTarget())
					|| userTaskWSEntity.getValueOfTarget() == null) {
				tv_task_process.setText("0");
			}
			break;

		default:
			break;
		}

	}

	double parseDouble(String str) {
		double result = 1;
		try {
			result = Double.parseDouble(str);
		} catch (Exception e) {
			result = 1;
		}
		return result;
	}

	// 接受任务
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_accept:
			getAcceptTaskList(userTaskWSEntity);
			break;

		default:
			break;
		}
	}
	
	public void getAcceptTaskList(final UserTaskWSEntity userTaskWSEntity){
		JSONObject json = null;
		try {
			json = new JSONObject();
			json.put("user_fk",
					SPManager.getInstance().getLongValue(SPManager.ID));
			json.put("task_fk", userTaskWSEntity.getTask().getId());
		} catch (Exception e) {
		}
		HttpUtils httpUtils = new HttpUtils(context,
				RequestDefine.TASK_ACCEP_TASK, RequestType.PUT, json);
		httpUtils.setShowDiloag(false);
		httpUtils.setUser_fk(SPManager.getInstance().getLongValue(
				SPManager.ID));
		httpUtils.setTask_fk(userTaskWSEntity.getTask().getId());
		httpUtils.setSuccessListener(new SuccessReslut() {

			@Override
			public void getResluts(JSONObject response) {

				if (Request.RESLUT_OK.equals(response
						.optString("status"))) {
					if(userTaskWSEntity.getStatus().getId() == -1){
						Toast.makeText(
								context,
								userTaskWSEntity.getTask().getName()
										+ "已接受", 1).show();
						userTaskWSEntity.getStatus().setId(10);
						taskAcceptModel.setIsProcessing(1);
						if (taskAccept != null)
							taskAccept.taskAccept(taskAcceptModel);
						dismiss();
					}						
				}
			}

		});
		httpUtils.setFaliureResult(new FaliureResult() {

			@Override
			public void getResluts(String msg) {
				Toast.makeText(context, "接受任务失败", 1).show();
			}
		});
		httpUtils.executeRequest();
	}

	public interface TaskAccept {
		void taskAccept(TaskAcceptModel model);
	}

	public class TaskAcceptModel {
		private int isProcessing;

		public int getIsProcessing() {
			return isProcessing;
		}

		public void setIsProcessing(int isProcessing) {
			this.isProcessing = isProcessing;
		}

	}

}
