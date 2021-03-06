package com.itheima.mobilesafe;

import com.itheima.mobilesafe.db.dao.NumberAddressQueryUtils;

import android.app.Activity;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 	号码查询
 * @author libo
 *
 */
public class NumberAddressQueryActivity extends Activity {
	
	private static final String TAG = "NumberAddressQueryActivity";
	private EditText et_phone;
	private TextView result;
	
	private Vibrator vibrator;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_number_address_query);
		
		et_phone = (EditText)findViewById(R.id.et_phone);
		result = (TextView)findViewById(R.id.result);
		
		vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);
		
		et_phone.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (s!=null  && s.length() >=3) {
					String address = NumberAddressQueryUtils.querNumber(s.toString());
					result.setText(address);
				}
				if (s==null || s.length() == 0) {
					result.setText("显示结果");
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}
		});
	}
	
	/**
	 * 	查询归属地
	 * @param view
	 */
	public void numberAddressQuery(View view)
	{
		String phone = et_phone.getText().toString().trim();
		if (TextUtils.isEmpty(phone)) {
			Toast.makeText(this, "号码为空", 0).show();
			//抖动动画
			Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
			et_phone.startAnimation(shake);
			
			//震动
//			vibrator.vibrate(2000);
			long[] patterm = {200,200,300,300,1000,2000};
			vibrator.vibrate(patterm, 0); //0循环 -1不重复 
			
			return;
		}else {
			//查询数据库
			Log.i(TAG, "要查询的号码:" + phone);
			String address = NumberAddressQueryUtils.querNumber(phone);
			result.setText(address);
		}
	}
}
