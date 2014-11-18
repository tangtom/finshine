
package com.codans.blossom.datepicker;

import com.incito.finshine.R;

import android.os.Bundle;
import android.app.Dialog;
import android.content.Context;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;

public class DlgDatePicker extends Dialog {
    
    private int resTitle;
    private int year = 0;
    private int month = 0;
    private int day = 0;
	public interface OnColorChangedListener {
		void colorChanged(int color);
	}

	private OnDateChangedListener mListener;
	
	public DlgDatePicker(Context context, int title,OnDateChangedListener listener,int y,int m,int d) {
        this(context,title,listener);
        year = y;
        month = m;
        day = d;
    }
	public DlgDatePicker(Context context, int title,OnDateChangedListener listener) {
		super(context);
		resTitle = title;
		mListener = listener;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.dialog_date_picker);
		setTitle(resTitle);
		final DateDrumPicker date = (DateDrumPicker) findViewById(R.id.datepicker);
		if(year >0){
		    date.setYear(year);
		    date.setMonth(month);
		    date.setDay(day);
		}
		date.setOnDateChangedListener(new OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year,
                    int monthOfYear, int dayOfMonth) {
                DlgDatePicker.this.year = year;
                DlgDatePicker.this.month = monthOfYear;
                DlgDatePicker.this.day = dayOfMonth;
            }
        });
		Button ok = (Button)findViewById(R.id.buttonOK);
		ok.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                if(mListener != null){
                    mListener.onDateChanged(null, year, month, day);
                }
                dismiss();
            }
        });
		Button cancel = (Button)findViewById(R.id.buttonCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
	}
	public int getResTitle() {
		return resTitle;
	}
	public void setResTitle(int resTitle) {
		this.resTitle = resTitle;
	}
}
