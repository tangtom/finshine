package com.incito.finshine.activity.dialog;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.incito.finshine.R;
import com.incito.finshine.activity.ActProductCustomer;
import com.incito.finshine.activity.ActProductCustomer.AddTargetCustomerListener;
import com.incito.finshine.activity.adapter.AdapterFindCustomer;
import com.incito.finshine.activity.adapter.AdapterFindCustomer.ViewHolder;
import com.incito.finshine.entity.Customer;
import com.incito.finshine.entity.Product;
import com.incito.finshine.manager.CoreManager;
import com.incito.finshine.network.Request;
import com.incito.finshine.network.Request.RequestType;
import com.incito.finshine.network.RequestDefine;
import com.incito.utility.CommonUtil;
import com.incito.wisdomsdk.net.http.JsonHttpResponseHandler;

/**
 * <dl>
 * <dt>DialogFindTargetCustomer.java</dt>
 * <dd>Description:发现客户数据</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 昆山灵动信息技术有限公司</dd>
 * <dd>CreateDate: 2014-5-19 下午2:37:53</dd>
 * </dl>
 * 
 * @author lihs
 */
public class DialogFindTargetCustomer implements OnClickListener {

	private static final String TAG = "DialogFindTargetCustomer";

	private Context context;
	private Product pro;

	private static final int FIX_WIDTH = 1000;
	private static final int FIX_HEIGHT = 500;

	private CheckBox isCheckAll;
	private View contentView;
	
	private List<Customer> findCustomer = null;

	private AdapterFindCustomer findCustomerAda;
	private ListView listView;

	public View getContentView() {
		return contentView;
	}

	private Dialog dialog;

	public Dialog getDialog() {
		return dialog;
	}

	public DialogFindTargetCustomer(Context context, Product product) {

		this.context = context;
		this.pro = product;

		dialog = new Dialog(context, R.style.CustomProgressDialog);
		contentView = LayoutInflater.from(context).inflate( R.layout.act_find_target_customer, null);
		LayoutParams params = new LayoutParams(FIX_WIDTH, FIX_HEIGHT);

		dialog.setCancelable(false);
		dialog.setCanceledOnTouchOutside(false);
		dialog.addContentView(contentView, params);
		dialog.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {
                
			}
		});
		initCustomerData();
	}

	

	public void initCustomerData() {
		
		
		((TextView)contentView.findViewById(R.id.tvProdName)).setText(pro.getProdName());
		
		isCheckAll = (CheckBox) contentView.findViewById(R.id.cbCustomerCheck);
		isCheckAll.setOnClickListener(this);

		contentView.findViewById(R.id.iv_close).setOnClickListener(this);
		// 新增
		contentView.findViewById(R.id.btn_add_customer).setOnClickListener(this);
		// 删除
		contentView.findViewById(R.id.btn_del_customer).setOnClickListener(this);
		// 群发邮件
		contentView.findViewById(R.id.btn_send_emali).setOnClickListener(this);
		// 群发短讯
		contentView.findViewById(R.id.btn_send_sms).setOnClickListener(this);

		TextView selectecCount = (TextView) contentView.findViewById(R.id.tvSelectedCustomer);

		findCustomerAda = new AdapterFindCustomer(context, isCheckAll, selectecCount,pro);
		listView = (ListView) contentView.findViewById(R.id.listView_findTargetCustomer);

		// TODO 测试数据
		List<Customer> list = new ArrayList<Customer>();
//		Customer item = null;
//		for (int i = 0; i < 5; i++) {
//			item = new Customer();
//			item.setAge(i + 1);
//			list.add(item);
//		}
		findCustomerAda.setCustomerList(list);
		listView.setAdapter(findCustomerAda);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position < 0) {
					return;
				}
				ViewHolder holder = (ViewHolder) view.getTag();
				holder.cbSelected.toggle();
				findCustomerAda.select(position, holder.cbSelected.isChecked());
				findCustomerAda.notifyDataSetChanged();
			}
		});

		initQueryData();
		
		ActProductCustomer.setAddTargetCustomerListener(new AddTargetCustomerListener() {
			
			@Override
			public void addTargetCustomer(List<Customer> list) {
				
				// 对已经添加的客户 就不在添加了
				if (findCustomer == null) {
					findCustomer = new ArrayList<Customer>();
				}
				List<Customer> tempList = findCustomer;
				List<Customer> addDataList =new ArrayList<Customer>();
				//排重 
				Customer selCustomer =null;
				boolean isAdded = false;
				for (int i = 0; i < list.size(); i++) {
					isAdded = false;
					selCustomer = list.get(i);
					for (int j = 0; j < tempList.size(); j++) {
						if (selCustomer.getId() == tempList.get(j).getId()) {
							isAdded = true;
							break;
						}
					}
					if (!isAdded) {
						addDataList.add(selCustomer);
					}
				}
				// TODO 添加目标数据
				if (addDataList.size() > 0) {
					addTargetCustomers(addDataList);
				}else{
					CommonUtil.showToast("选择的数据已存在", context);
				}
				
			}
		});
	}

	// delete target customer
	private void deleteTargetCustomers(List<Customer> selist){
		
		if (selist == null || selist.size() == 0) {
			CommonUtil.showToast("请至少选择一个要删除的客户", context);
			return;
		}
		 
		List<Customer> notSeleted = new ArrayList<Customer>();
		for (Customer customer : findCustomer) {
			if (!selist.contains(customer)) {
				notSeleted.add(customer);
			}
		}
		// 传递未选中的客户id
		try {
			JSONObject json = new JSONObject();
			StringBuffer customerIds = new StringBuffer();
			for (Customer customer : notSeleted) {
				customerIds.append(customer.getId()+",");
			}
			 
			if (customerIds.length() > 0) {
				json.put("customIds", customerIds.substring(0, customerIds.length()-1));
			}else{
				json.put("customIds", "");
			}
			Request request = new Request(RequestDefine.RQ_DELETE_TARGET_CUSTOMER,pro.getId(), RequestType.POST, json, handlerDeleteTargetCustomer);
			CoreManager.getInstance().postRequest(request);
		  } catch (Exception e) {
		}
	
	}
	
	// add target customer
	private void addTargetCustomers(List<Customer> list){
		if (list.size() == 0) {
			return;
		}
		try {
			JSONObject json = new JSONObject();
			StringBuffer customerIds = new StringBuffer();
			for (Customer customer : list) {
				customerIds.append(customer.getId()+",");
			}
			json.put("customIds", customerIds.substring(0, customerIds.length()-1));
			Request request = new Request(RequestDefine.RQ_ADD_TARGET_CUSTOMER,pro.getId(), RequestType.POST, json, handlerAddTargetCustomer);
			CoreManager.getInstance().postRequest(request);
		  } catch (Exception e) {
		}
	}
	
	private void initQueryData() {
		
		//   根据产品类型进行查询该产品下的客户比如 资管：政府类
		if (pro != null && !TextUtils.isEmpty(pro.getId()+"")) {
			try {
				Request request = new Request(RequestDefine.RQ_QUERY_TARGET_CUSTOMER, pro.getId(), RequestType.GET, null, handlerFindTargetCustomer);
				CoreManager.getInstance().postRequest(request);
			} catch (Exception e) {
			}
			
		}
	}

	 
	private JsonHttpResponseHandler handlerFindTargetCustomer = new JsonHttpResponseHandler() {
		@Override
		public void onSuccess(JSONObject response) {
			Log.d(TAG, "success o= " + response.toString());
			// {"msg":"查询成功","status":"0","item":{"id":3,"lastMod":1400586480000,"created":1400586480000,"status":"A","prodId":63,"salesId":1,"customIds":"1,2,3,4","version":1}}
			Log.d(TAG, "success a= " + response.toString());
			if (Request.RESLUT_OK.equals(response.opt("status"))) {
				try {
					JSONArray arr = response.getJSONArray("item");
					Gson gson = new Gson();
					if (arr != null) {
						findCustomer = gson.fromJson(arr.toString(), new TypeToken<List<Customer>>() {}.getType());
					}
					findCustomerAda.setCustomerList(findCustomer);
					
				} catch (JSONException e) {

					e.printStackTrace();
				}
			}
		}

		@Override
		public void onFailure(Throwable error, String content) {
			Log.i(TAG, "onFailure = " + content);
			CommonUtil.showToast("发现客户异常"+content, context);
		}
	};
	
	private JsonHttpResponseHandler handlerAddTargetCustomer = new JsonHttpResponseHandler() {
		@Override
		public void onSuccess(JSONObject response) {
			Log.d(TAG, "success o= " + response.toString());
			if (Request.RESLUT_OK.equals(response.opt("status"))) {
				CommonUtil.showToast("添加目标客户成功", context);
				// 重新查询数据刷新
				initQueryData();
			}else{
				CommonUtil.showToast("添加目标客户失败", context);
			}
		}

		@Override
		public void onFailure(Throwable error, String content) {
			Log.i(TAG, "onFailure = " + content);
		}
	};
	
	private JsonHttpResponseHandler handlerDeleteTargetCustomer = new JsonHttpResponseHandler() {
		@Override
		public void onSuccess(JSONObject response) {
			Log.d(TAG, "success o= " + response.toString());
			if (Request.RESLUT_OK.equals(response.opt("status"))) {
				CommonUtil.showToast("删除目标客户成功", context);
				// 重新查询数据刷新
				initQueryData();
			}else{
				CommonUtil.showToast("删除目标客户失败", context);
			}
		}

		@Override
		public void onFailure(Throwable error, String content) {
			Log.i(TAG, "onFailure = " + content);
			CommonUtil.showToast("删除目标客户失败"+content, context);
		}
	};
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.cbCustomerCheck:
			if (findCustomer == null) {
				return;
			}
			findCustomerAda.isSeletAllCustomer(isCheckAll.isChecked());
			break;
		case R.id.btn_add_customer:
			// 添加目标客户
			context.startActivity(new Intent(context, ActProductCustomer.class));

			break;
		case R.id.btn_del_customer:
			//  调用接口进行删除数据
			deleteTargetCustomers(findCustomerAda.getSeletCustomerData());
			
			break;
		case R.id.btn_send_emali:
			if (findCustomer == null) {
				return;
			}
			String[] targetEmail= new String[findCustomer.size()];
			List<Customer> listS = findCustomerAda.getSeletCustomerData();
			if (listS != null && listS.size() == 0) {
				CommonUtil.showToast("邮件号码为空", context);
				return;
			}
			for (int i =0 ;i < listS.size() ; i++) {
				targetEmail[i] =  listS.get(i).getEmail1();
			}
			CommonUtil.sendEmail(context, targetEmail, "", "");
			break;
		case R.id.btn_send_sms:
			StringBuffer targetPhone = new StringBuffer();
			List<Customer> list = findCustomerAda.getSeletCustomerData();
			for (Customer customer : list) {
				targetPhone.append(customer.getCellPhone1()+",");
			}
			CommonUtil.sendSms(context, targetPhone.toString());
			break;
		case R.id.iv_close:
			if (dialog != null) {
				dialog.dismiss();
				dialog = null;
			}
			break;
		default:
			break;
		}
	}
}
