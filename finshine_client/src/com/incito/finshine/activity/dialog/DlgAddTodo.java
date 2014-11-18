package com.incito.finshine.activity.dialog;

import java.util.Date;
import java.util.List;

import com.codans.blossom.datepicker.DlgDatePicker;
import com.incito.finshine.R;
import com.incito.finshine.entity.Customer;
import com.incito.finshine.entity.TodoItem;
import com.incito.finshine.manager.CoreManager;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import android.widget.DatePicker.OnDateChangedListener;
public class DlgAddTodo extends DlgBase implements OnDateChangedListener{
	private EditTodoListener listener;
	private Date startDate = new Date(System.currentTimeMillis());
	private Date endDate;
	TextView txtDate; 
	private List<Customer> listCustomer;

	public DlgAddTodo(Context context) {
		super(context);
	}
	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		final EditText title = (EditText)findViewById(R.id.edtTitle);
		final EditText content = (EditText)findViewById(R.id.edtContent);
		txtDate= (TextView)findViewById(R.id.txtDate);
		txtDate.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DlgDatePicker picker = new DlgDatePicker(getContext(),
						R.string.title_select, DlgAddTodo.this);
				picker.show();
			}
		});
		final TextView time = (TextView)findViewById(R.id.txtTime);
		final Spinner customer = (Spinner)findViewById(R.id.spnCustomer);
		listCustomer = CoreManager.getInstance().getCustomerManager().getListCustomer();
		String[] customers = new String[listCustomer.size()];
		for(int i=0;i<listCustomer.size();i++){
			customers[i]=listCustomer.get(i).getName();
		}
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, customers);
		customer.setAdapter(adapter);
		final Button save = (Button)findViewById(R.id.btnPositive);
		save.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (listener!=null) {
					String strTitle = title.getText().toString();
					String strContent = content.getText().toString();
					TodoItem item = new TodoItem();
					item.setTitle(strTitle);
					item.setContent(strContent);
					item.setStartTime(startDate.getTime());
					item.setCustomerId(0);
					listener.onAdd(item);
				} 
				dismiss();
			}
		});
		final Button cancel =(Button)findViewById(R.id.btnNegative);
		cancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
	}
	public void setEditListener(EditTodoListener l)
	{
		listener = l;
	}
	@Override
	public void onDateChanged(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		startDate.setYear(year-1900);
		startDate.setMonth(monthOfYear-1);
		startDate.setDate(dayOfMonth);
		if(txtDate !=null)
		{
			txtDate.setText(startDate.toLocaleString());
		}
	}

}
