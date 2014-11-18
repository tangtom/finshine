package com.custom.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.incito.finshine.R;
import com.incito.finshine.activity.ActProdCompareDetail;
import com.incito.finshine.activity.adapter.AdapterProductList;
import com.incito.finshine.entity.Customer;
import com.incito.finshine.entity.Product;
import com.incito.finshine.manager.CoreManager;
import com.incito.utility.CommonUtil;

public class PopProdCompare implements OnTouchListener,OnClickListener{
	
	 
	private Customer customer;
	
	private Context context = null;
	 
	private View popView = null; 
	
	public View getPopView() {
		return popView;
	}
 
	private View clickView = null;
	
	private PopupWindow popWin = null;
	
	public PopupWindow getPopWin() {
		return popWin;
	}

	private int lastX,lastY,dx,dy,mScreenX,mScreenY;
	
	private AdapterProductList productAdapter = null;
	

	public PopProdCompare(Context context,View clickView,AdapterProductList productAdapter) {
		
		super();
		this.context = context;
		this.clickView =clickView;
		this.productAdapter = productAdapter;
		
		initPopwindow(clickView);
		
		initWedgit();
		
	}
		
	private void initPopwindow(View clickView){
		
		popView = LayoutInflater.from(context).inflate(R.layout.pop_prod_compare, null);
		
		popView.findViewById(R.id.fl_prod_compare).setOnTouchListener(this);
		popView.findViewById(R.id.btn_compare_prod).setOnClickListener(this);
		popWin = new PopupWindow(popView,800, 280,false);
		
		popWin.setBackgroundDrawable(new ColorDrawable());
//		popWin.setOutsideTouchable(true);
//		popWin.setFocusable(true);
		popWin.update();
	}
	
	private void initWedgit(){
 
		popView.findViewById(R.id.iv_delete_prod1).setOnClickListener(this);
		popView.findViewById(R.id.iv_delete_prod2).setOnClickListener(this); 
		popView.findViewById(R.id.btn_compare_prod).setOnClickListener(this);
 
	}
	
	
	
	 public void showPopWindow(){
	    	if (popWin != null) {
	    		popWin.showAtLocation(clickView, Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL,0,0);
			}
	    }
		
	    public void closePopWindow(){
	    	if (popWin != null) {
				if (popWin.isShowing()) {
					popWin.dismiss();
					popWin = null;
				}
			}
	    }
	    
		public boolean isShowing(){
			
			if (popWin != null) {
				if (popWin.isShowing()) {
					return true;
				}
			}
			return false;
		}


		@Override
		public boolean onTouch(View v, MotionEvent event) {
			int action = event.getAction();
			switch (action) {
			case MotionEvent.ACTION_DOWN:
				lastX = (int) event.getRawX();
				lastY = (int) event.getRawY();
				break;
			case MotionEvent.ACTION_UP:
				mScreenX = dx;
				mScreenY = dy;
				break;
			case MotionEvent.ACTION_MOVE:
				dx = (int) event.getRawX() - lastX + mScreenX;
				dy =  lastY - (int)event.getRawY() + mScreenY;
				popWin.update( dx,dy, -1, -1);
				break;
			}
			return true;
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			 
			case R.id.iv_delete_prod1:
				// 删除产品一时 要注意根据产品名称比较如果相等删除，并且要刷新数据源，更新按钮状态，都删除时要隐藏对比框
				delCompareProd(R.id.tv_product_name1);
				break;
			case R.id.iv_delete_prod2:
				// 删除产品一时 要注意根据产品名称比较如果相等删除，并且要刷新数据源，更新按钮状态，都删除时要隐藏对比框
				delCompareProd(R.id.tv_product_name2);
				break;
			case R.id.btn_compare_prod:
				// 跳转到产品对比详情界面
				List<Long> listProdId = productAdapter.getCompareProdId();
				if (listProdId.size() == 2) {
					List<Product> list = new ArrayList<Product>(); 
					for (Long prodId : listProdId) {
						 list.add( CoreManager.getCoreManager().getProduct().getProdctByProdId(prodId));
					}
					// 显示数据
					Intent i = new Intent(context,ActProdCompareDetail.class );
//					Log.i("", list.size() + "" + );
					i.putExtra(ActProdCompareDetail.ACTION_PROD, (Serializable)list);
					context.startActivity(i);
					closePopWindow();
					productAdapter.destroyAdapter();
					
				}else{ 
					CommonUtil.showToast("请选择两件产品进行比较", context);
				}
				break;
			default:
				break;
			}
		}
	
		private void delCompareProd(int id){
			
//			Map<Integer, Product> map = productAdapter.get();
			List<Long> list = productAdapter.getCompareProdId();
			// 跳转到 产品对比详情界面
			String prodName = ((TextView)popView.findViewById(id)).getText().toString();
			if (!TextUtils.isEmpty(prodName)) {
//				for (Map.Entry<Integer, Product> item : map.entrySet()) {
//					if (prodName.equals(item.getValue().getProdName())) {
//						productAdapter.refreshSelecCompProd(item.getKey());
//						return;
//					}
//				}
				Product prod = null;
				for (Long prodId : list) {
					prod = CoreManager.getCoreManager().getProduct().getProdctByProdId(prodId);
					if (prodName.equals(prod.getProdName())) {
						productAdapter.refreshSelecCompProd(prodId);
						return;
					}
				}
			}else{
				CommonUtil.showToast("还没有选中要对比的产品", context);
			}
		}
}
