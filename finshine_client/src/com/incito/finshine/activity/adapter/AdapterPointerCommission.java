package com.incito.finshine.activity.adapter;

import java.math.BigDecimal;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.incito.finshine.R;
import com.incito.finshine.entity.PropsUsedItemWSEntity;
import com.incito.wisdomsdk.event.EventBus;
import com.incito.wisdomsdk.utils.BitmapUtils;

public class AdapterPointerCommission extends BaseAdapter {

	private final String TAG = AdapterPointerCommission.class.getSimpleName();
	private Context context;
	private List<PropsUsedItemWSEntity> propLists;
	private boolean isUse;
	public AdapterPointerCommission(Context context,List<PropsUsedItemWSEntity> propLists,boolean isUse) {
		super();
		this.context = context;
		this.propLists = propLists;
		this.isUse = isUse;
	}



	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return propLists.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return propLists.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.row_commission_detail,null);
			viewHolder = new ViewHolder();
			viewHolder.imgCommission = (ImageView)convertView.findViewById(R.id.imgCommission);
			viewHolder.textPropName = (TextView)convertView.findViewById(R.id.textPropName);
			viewHolder.seekbarCommission = (SeekBar)convertView.findViewById(R.id.seekbarCommission);
			viewHolder.textUsedMaxQty = (TextView)convertView.findViewById(R.id.textUsedMaxQty);
			viewHolder.textRemaining = (TextView)convertView.findViewById(R.id.textRemaining);
			viewHolder.textSubtotalRatio = (TextView)convertView.findViewById(R.id.textSubtotalRatio);
			viewHolder.textSeekBarProgress = (TextView)convertView.findViewById(R.id.textSeekBarProgress);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		PropsUsedItemWSEntity props = propLists.get(position);
		if(props.getProps().getPicture() != null)
		{
			byte[] pic = android.util.Base64.decode(props.getProps().getPicture(), Base64.DEFAULT);
			BitmapFactory.Options opts = new BitmapFactory.Options();

			opts.inSampleSize = 4; //这个的值压缩的倍数（2的整数倍），数值越小，压缩率越小，图片越清晰

			Bitmap bmap = BitmapFactory.decodeByteArray(pic, 0, pic.length, opts);
			viewHolder.imgCommission.setImageBitmap(bmap);
		}
		
		viewHolder.textPropName.setText(props.getProps().getName());
		viewHolder.textUsedMaxQty.setText(props.getQtyOfUsed() + "/" + props.getMaxQty());
		viewHolder.textRemaining.setText(String.valueOf(props.getQtyOfRemaining()));
		viewHolder.textSubtotalRatio.setText(String.valueOf(new BigDecimal(props.getSubtotalRatio()).setScale(2,BigDecimal.ROUND_HALF_UP)) + "%");
//		Log.i(TAG, props.getQtyOfUsed() + "-----------" + props.getQtyOfRemaining() + "--------" + props.getMaxQty());
		int d = props.getQtyOfUsed() == 0 || props.getMaxQty() == 0 ? 5 : 90*props.getQtyOfUsed()/props.getMaxQty();
		MySeekBarListener mySeekBarListener = new MySeekBarListener(viewHolder.textRemaining,viewHolder.textSubtotalRatio,viewHolder.textUsedMaxQty,viewHolder.textSeekBarProgress,props,d,propLists);
		if(isUse){
			if(props.getQtyOfRemaining() == 0){
				viewHolder.seekbarCommission.setEnabled(false);
			}
			else{
				viewHolder.seekbarCommission.setOnSeekBarChangeListener(mySeekBarListener);
			} 
		}else{
			viewHolder.seekbarCommission.setEnabled(false);
		}
		
		viewHolder.seekbarCommission.setProgress(d);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		lp.setMargins(6*d, 0, 0, 0);
		viewHolder.textSeekBarProgress.setLayoutParams(lp);
		viewHolder.textSeekBarProgress.setText(String.valueOf(props.getQtyOfUsed()));
		return convertView;
	}

	class ViewHolder{
		ImageView imgCommission;
		TextView textPropName;
		SeekBar seekbarCommission;
		TextView textUsedMaxQty;
		TextView textRemaining;
		TextView textSubtotalRatio;
		TextView textSeekBarProgress;
	}
	
	
	private class MySeekBarListener implements OnSeekBarChangeListener{

		private TextView textSeekBarProgress;
		private TextView textUsedMaxQty;
		private TextView textRemaining;
		private TextView textSubtotalRatio;
		private PropsUsedItemWSEntity props;
		private int position;
		private List<PropsUsedItemWSEntity> propLists;
		public MySeekBarListener(TextView textRemaining,TextView textSubtotalRatio,TextView textUsedMaxQty,TextView textSeekBarProgressView,PropsUsedItemWSEntity props,int position,List<PropsUsedItemWSEntity> propLists) {
			super();
			this.textSeekBarProgress = textSeekBarProgressView;
			this.textRemaining = textRemaining;
			this.textUsedMaxQty = textUsedMaxQty;
			this.textSubtotalRatio = textSubtotalRatio;
			this.props = props;
			this.position = position;
			this.propLists = propLists;
 		}

		@Override
		public void onProgressChanged(SeekBar seekbar, int position, boolean fromUser) {
			int max = props.getMaxQty();
			if(Integer.parseInt(textRemaining.getText().toString()) > 0){
				for(int i = 0;i < max;i ++){
					if(position > ((90 * i)/max + 5) && position < (90 * i + 45)/max + 5){
						textSeekBarProgress.setText(String.valueOf(i));
						textSubtotalRatio.setText(String.valueOf(new BigDecimal((i) * props.getPropertyValue()).setScale(2,BigDecimal.ROUND_HALF_UP)) + "%");
						textRemaining.setText(String.valueOf(props.getQtyOfRemaining() - (i)));
						textUsedMaxQty.setText((i) + "/" + props.getMaxQty());
					}else if(position >= (90 * i + 45)/max + 5 && position < (90 * (i + 1))/max + 5){
						textSeekBarProgress.setText(String.valueOf(i + 1));
						textSubtotalRatio.setText(String.valueOf(new BigDecimal((i + 1) * props.getPropertyValue()).setScale(2,BigDecimal.ROUND_HALF_UP)) + "%");
						textRemaining.setText(String.valueOf(props.getQtyOfRemaining() - (i + 1)));
						textUsedMaxQty.setText((i + 1) + "/" + props.getMaxQty());
					}
				}
				if(position <= 5){
					position = 5;
					seekbar.setProgress(position);
				}else if(position >= 95){
					position = 95;
					seekbar.setProgress(position);
				}
				this.position = position;
			}else{
				if(position <= 5){
					position = 5;
				}
			}
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			lp.setMargins(position*6, 0, 0, 0);
			textSeekBarProgress.setLayoutParams(lp);
			if(Integer.parseInt(textRemaining.getText().toString()) == 0)
			{	
				if(position < 5 + 45/max){
					textSeekBarProgress.setText(String.valueOf(0));
					textSubtotalRatio.setText(String.valueOf(new BigDecimal(0 * props.getPropertyValue()).setScale(2,BigDecimal.ROUND_HALF_UP)) + "%");
					textRemaining.setText(String.valueOf(Integer.parseInt(textRemaining.getText().toString()) + 1));
					textUsedMaxQty.setText(0 + "/" + props.getMaxQty());
				}
			}
			for(int i = 0;i < propLists.size();i ++){
				if(props.equals(propLists.get(i))){
					props.setQtyOfUsed(Integer.parseInt(textSeekBarProgress.getText().toString()));
					propLists.set(i, props);
				}
			}
//			ActPointerCommissionListener listener = (ActPointerCommissionListener)context;
//			listener.setTextView(propLists);
			EventBus.getDefault().postSticky(propLists);
		}
		
		@Override
		public void onStartTrackingTouch(SeekBar seekbar) {
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekbar) {
			int max = props.getMaxQty();
			for(int i = 0;i < max;i ++){
				if(position > ((90 * i)/max + 5) && position < (90 * i + 45)/max + 5){
					int d = (90 * i)/max + 5;
					seekbar.setProgress(d);
					textSeekBarProgress.setText(String.valueOf(i));
					textSubtotalRatio.setText(String.valueOf(new BigDecimal((i) * props.getPropertyValue()).setScale(2,BigDecimal.ROUND_HALF_UP)) + "%");
					textRemaining.setText(String.valueOf(props.getQtyOfRemaining() - (i)));
					textUsedMaxQty.setText((i) + "/" + props.getMaxQty());
				}else if(position >= (90 * i + 45)/max + 5 && position < (90 * (i + 1))/max + 5){
					int d = 90 * (i + 1)/max + 5;
					seekbar.setProgress(d);
					LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
					lp.setMargins(6*d, 0, 0, 0);
					textSeekBarProgress.setLayoutParams(lp);
					textSeekBarProgress.setText(String.valueOf(i + 1));
					textSubtotalRatio.setText(String.valueOf(new BigDecimal((i + 1) * props.getPropertyValue()).setScale(2,BigDecimal.ROUND_HALF_UP)) + "%");
					textRemaining.setText(String.valueOf(props.getQtyOfRemaining() - (i + 1)));
					textUsedMaxQty.setText((i + 1) + "/" + props.getMaxQty());
				}
			}
		}
	}
	public interface ActPointerCommissionListener
	{
		public void setTextView(List<PropsUsedItemWSEntity> propLists);
	}
}
