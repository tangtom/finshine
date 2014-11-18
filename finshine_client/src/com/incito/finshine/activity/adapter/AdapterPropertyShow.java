package com.incito.finshine.activity.adapter;

import java.util.List;

import android.R.string;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.custom.view.PopPropertyManagement;
import com.incito.finshine.R;
import com.incito.finshine.entity.PropsWSEntity;
import com.incito.utility.CommonUtil;
import com.incito.wisdomsdk.utils.BitmapUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * <dl>
 * <dt>AdapterCustomerMarketing.java</dt>
 * <dd>Description:首页道具适配器</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 昆山灵动信息技术有限公司</dd>
 * <dd>CreateDate: 2014-6-17 下午11:09:41</dd>
 * </dl>
 * 
 * @author liying
 * 
 */

/*btn.setBackgroundColor（R.drawable.×××）;*/
public class AdapterPropertyShow extends BaseAdapter implements OnClickListener{

	private Context mContext;
	private List<PropsWSEntity> props;
	private int k;
	private long p;
	public void setK(int k)
	{
		this.k = k;
		notifyDataSetChanged();
	}

	public AdapterPropertyShow(Context context,List<PropsWSEntity> props){
		this.mContext = context;
		this.props = props;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return props.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return props.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
    
	
	public long getP() {
		return p;
	}

	public void setP(long p) {
		this.p = p;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parentView) {
		ViewHolder viewHolder = null;
		if(convertView == null){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.row_property_show,null);
			viewHolder = new ViewHolder();
			viewHolder.imgPropertyPicture = (ImageView)convertView.findViewById(R.id.imgPropertyPicture1);
			viewHolder.propertyName = (TextView)convertView.findViewById(R.id.propertyName);
			viewHolder.totalQty = (TextView)convertView.findViewById(R.id.totalQty);
/*			viewHolder.propertyBorder = (RelativeLayout) convertView.findViewById(R.id.propertyBorder);*/
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		PropsWSEntity prop = props.get(position);
		
		/*viewHolder.propertyBorder = (RelativeLayout) convertView.findViewById(R.id.propertyBorder);*/
		/*viewHolder.propertyBorder.setOnClickListener(this);*/
		/*viewHolder.imgPropertyPicture.setOnClickListener(this);*/
		
		if(k == position)
		{
			if(prop.getPicture() != null){ 
				PopPropertyManagement.id=k;
				byte[] pic = android.util.Base64.decode(prop.getPicture(), Base64.DEFAULT);
				//Bitmap bmap = BitmapUtils.fromByteArray(pic);
				BitmapFactory.Options opts = new BitmapFactory.Options();

				opts.inSampleSize = 4; //这个的值压缩的倍数（2的整数倍），数值越小，压缩率越小，图片越清晰

				Bitmap	bmap = BitmapFactory.decodeByteArray(pic, 0, pic.length, opts);
				Drawable mDrawable = mContext.getResources().getDrawable(R.drawable.border_property);				
				Drawable[] array = new Drawable[2];
				array[0] = new BitmapDrawable(bmap);
				array[1] = mDrawable;
				LayerDrawable la = new LayerDrawable(array);
                la.setLayerInset(0, 0, 0, 0, 0);
                la.setLayerInset(1, 0, 0, 0, 0);
                viewHolder.imgPropertyPicture.setImageDrawable(la);
			}
		}
		else
		{
			if(prop.getPicture() != null){ 
				byte[] pic = android.util.Base64.decode(prop.getPicture(), Base64.DEFAULT);
			//	Bitmap bmap = BitmapUtils.fromByteArray(pic);

				BitmapFactory.Options opts = new BitmapFactory.Options();
				Drawable mDrawable1 = mContext.getResources().getDrawable(R.drawable.border_property1);
				opts.inSampleSize = 4; //这个的值压缩的倍数（2的整数倍），数值越小，压缩率越小，图片越清晰
				/*Bitmap	bmap = BitmapFactory.decodeByteArray(pic, 0, pic.length, opts);
				viewHolder.imgPropertyPicture.setImageBitmap(bmap);*/
				Bitmap	bmap = BitmapFactory.decodeByteArray(pic, 0, pic.length, opts);
				Drawable mDrawable = mContext.getResources().getDrawable(R.drawable.border_property1);
				Drawable[] array = new Drawable[2];
				array[0] = new BitmapDrawable(bmap);
				array[1] = mDrawable;
				LayerDrawable la = new LayerDrawable(array);
                la.setLayerInset(0, 0, 0, 0, 0);
                la.setLayerInset(1, 0, 0, 0, 0);
                viewHolder.imgPropertyPicture.setImageDrawable(la);
			}
		}
		
		viewHolder.propertyName.setText(String.valueOf(prop.getName()));
		/*viewHolder.totalQty.setText(String.valueOf(prop.getTotalQty()));*/
	    if(prop.getTotalQty()>=0){
	    	viewHolder.totalQty.setText(String.valueOf(prop.getTotalQty()));
	    }else{
	    	viewHolder.totalQty.setText("0");
	    }
	    
		return convertView;
	}
	
	public static class ViewHolder{
		ImageView imgPropertyPicture;
		TextView propertyName;
		TextView totalQty;
		RelativeLayout propertyBorder;
	}

	@Override
	public void onClick(View arg0) {
		
		
	}
	
                                                                                
	
	
}
