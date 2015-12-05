package com.example.idcard;

import java.util.ArrayList;
import java.util.List;

import kankan.wheel.widget.AbstractWheelAdapter;
import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import net.hydromatic.linq4j.Linq4j;
import net.hydromatic.linq4j.function.Predicate1;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import cn.trinea.android.common.util.RandomUtils;
import cn.trinea.android.common.util.ResourceUtils;
import cn.trinea.android.common.util.SdUtils;
import cn.trinea.android.common.util.ToastUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MainActivity extends Activity {
	private List<Shen> shenArray = new ArrayList<Shen>();
	private AddressItem addressItem = null;
	private String mCurrentShen = null;
	private String mCurrentSi = null;
	private String mCurrentQu = null;

	private TextView text1, text2, text3, text4;

	private String mShenId = null;
	private String mShiId = null;
	private String mQuId = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		text1 = (TextView) findViewById(R.id.text1);
		text2 = (TextView) findViewById(R.id.text2);

		text3 = (TextView) findViewById(R.id.text3);
		text4 = (TextView) findViewById(R.id.text4);

		try {
			String strJson = ResourceUtils.getStringFromAssetsFile(
					MainActivity.this, "wcp.txt", "UTF-8");
			Gson gson = new Gson();
			shenArray = gson.fromJson(strJson, new TypeToken<List<Shen>>() {
			}.getType());
		} catch (Exception e) {
			Log.i("xx", "error=" + e.toString());
		}
		Log.i("xx", "size=" + shenArray.size());

		addressItem = new AddressItem();
		findViewById(R.id.test).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//shenShiQuDialog(mCurrentShen, mCurrentSi, mCurrentQu);
				//getQu(shenArray);
//				getCode();
				//随机生成5个身份证号码
				for(int i=0;i<5;i++){
					Log.i("xx" ,IdCardUtils.getIdCard(getRandomCode()));
				}
				
			}
		});
		
		

	}

	
	/**
	 * 得到随机的行政编码
	 * @return
	 */
	public String getRandomCode(){
		List<Qu> ls=getCode();
		if(ls!=null && ls.size()>0){
			int m=RandomUtils.getRandom(0, ls.size());
			return ls.get(m).getId();
		}
		else{
			return "110101"; //东城区
		}
	}
	/**
	 * 得到所有的行政编码
	 * @return
	 */
	public List<Qu> getCode(){
		List<Qu> list_qu=new ArrayList<Qu>();
		try {
			String strJson = ResourceUtils.getStringFromAssetsFile(
					MainActivity.this, "code.txt", "UTF-8");
			Gson gson = new Gson();
			list_qu = gson.fromJson(strJson, new TypeToken<List<Qu>>() {
			}.getType());
		} catch (Exception e) {
			Log.i("xx", "error=" + e.toString());
		}
		//ToastUtils.showLongToast(getApplicationContext(), ""+list_qu.size());
		return list_qu;
	}
	
	private void getQu(List<Shen> shenArray ){
		 List<Qu> ls=new ArrayList<Qu>();
		if(shenArray!=null &&  shenArray.size()>0 ){
			for(int i=0,len= shenArray.size();i<len;i++){
				List<Shi> ls_shi = shenArray.get(i).getShiArray();
				if(ls_shi!=null && ls_shi.size()>0){
					for(int m=0,lens=ls_shi.size();m<lens;m++){
						List<Qu> ls_qu= ls_shi.get(m).getQuArray();
						
						if(ls_qu!=null && ls_qu.size()>0){
							ls.addAll(ls_qu);
						}
						else{
							Qu qu=new Qu();
							qu.setId(ls_shi.get(m).getId());
							qu.setName(ls_shi.get(m).getName());
							ls.add(qu);
						}
					}
				}
				else{
					Qu qu=new Qu();
					qu.setId(shenArray.get(i).getId());
					qu.setName(shenArray.get(i).getName());
					ls.add(qu);
				}
			}
		}
		if(ls!=null && ls.size()>0){
			Gson gson =new Gson();
		    String str=	gson.toJson(ls);
		    if(SdUtils.StringSaveSD(str, "code.txt", "wcp"))
		    {
		    	ToastUtils.showLongToast(getApplicationContext(), "ok");
		    }
		    else{
		    	ToastUtils.showLongToast(getApplicationContext(), "erro");
		    }
		}
		
	}
	private void shenShiQuDialog(final String shen, final String shi,
			final String qu) {
		View view = LayoutInflater.from(this).inflate(
				R.layout.shen_shi_qu_dialog, (ViewGroup) null, false);
		view.setPadding(10, 10, 10, 10);

		final WheelView shenView = (WheelView) view.findViewById(R.id.shen);
		ShenAdapter shenAdapter = new ShenAdapter(this);
		shenView.setViewAdapter(shenAdapter);

		final WheelView shiView = (WheelView) view.findViewById(R.id.shi);
		ShiAdapter shiAdapter = null;

		final WheelView quView = (WheelView) view.findViewById(R.id.qu);
		QuAdapter quAdapter = null;

		Log.i("xx", "shen=" + shen + ",shi=" + shi + ",qu=" + qu);

		Shen mShenModel = null;
		Shi mShiModel = null;
		if (shenArray.isEmpty()) {
			shiAdapter = new ShiAdapter(this, null);
		} else {
			if (shen != null) {
				List<Shen> sh = Linq4j.asEnumerable(shenArray)
						.where(new Predicate1<Shen>() {
							@Override
							public boolean apply(Shen arg0) {
								return arg0.getName().equals(shen);
							}
						}).toList();
				if (sh.size() > 0) {
					mShenModel = sh.get(0);
					int index = shenArray.indexOf(mShenModel);
					shenView.setCurrentItem(index);
					shiAdapter = new ShiAdapter(this, mShenModel);
				} else {
					shiAdapter = new ShiAdapter(this, shenArray.get(0));
					mShenModel = shenArray.get(0);
				}
			} else {
				shiAdapter = new ShiAdapter(this, shenArray.get(0));
				mShenModel = shenArray.get(0);
			}
		}

		shiView.setViewAdapter(shiAdapter);

		if (shi != null) {
			// 省必不为空
			List<Shi> si = mShenModel.getShiArray();
			List<Shi> tp_si = Linq4j.asEnumerable(si)
					.where(new Predicate1<Shi>() {
						@Override
						public boolean apply(Shi arg0) {
							return arg0.getName().equals(shi);
						}
					}).toList();

			if (tp_si.size() > 0) {
				mShiModel = tp_si.get(0);
				int index_si = si.indexOf(mShiModel);
				shiView.setCurrentItem(index_si);
			} else {
				if (si != null && si.size() > 0) {
					mShiModel = si.get(0);
				}
			}
		} else {
			mShiModel = mShenModel.getShiArray().get(0);
		}

		quAdapter = new QuAdapter(this, mShiModel);
		quView.setViewAdapter(quAdapter);

		if (qu != null) {
			if (mShiModel != null) {
				List<Qu> qus = mShiModel.getQuArray();

				Log.i("xx", "qu2=" + qu);
				List<Qu> tp_qus = Linq4j.asEnumerable(qus)
						.where(new Predicate1<Qu>() {
							@Override
							public boolean apply(Qu arg0) {
								return arg0.getName().equals(qu);
							}
						}).toList();

				if (tp_qus.size() > 0) {
					int index_qu = qus.indexOf(tp_qus.get(0));
					quView.setCurrentItem(index_qu);
				}
			}
		}

		shenView.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				Shen shen = shenArray.get(newValue);
				shiView.setViewAdapter(new ShiAdapter(MainActivity.this,
						shenArray.get(newValue)));
				if (shen.getShiArray().isEmpty()) {
					quView.setViewAdapter(new QuAdapter(MainActivity.this, null));
				} else {
					shiView.setCurrentItem(0);
					Shi shi = shen.getShiArray().get(0);
					quView.setViewAdapter(new QuAdapter(MainActivity.this, shi));
					if (!shi.getQuArray().isEmpty()) {
						quView.setCurrentItem(0);
					}
				}
			}
		});

		shiView.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				Shi shi = shenArray.get(shenView.getCurrentItem())
						.getShiArray().get(newValue);
				quView.setViewAdapter(new QuAdapter(MainActivity.this, shi));
				if (!shi.getQuArray().isEmpty()) {
					quView.setCurrentItem(0);
				}
			}
		});

		new AlertDialog.Builder(this).setTitle("选择地区").setView(view)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (shenView.getCurrentItem() >= 0) {
							Shen shen = shenArray.get(shenView.getCurrentItem());
							addressItem.setShen(shen.getName());
							mShenId = shen.getId();

							if (!shen.getShiArray().isEmpty()) {
								Shi shi = shen.getShiArray().get(
										shiView.getCurrentItem());
								addressItem.setShi(shi.getName());
								mShiId = shi.getId();
								if (!shi.getQuArray().isEmpty()) {
									String qu = shi.getQuArray()
											.get(quView.getCurrentItem())
											.getName();
									addressItem.setQu(qu);
									mQuId = shi.getQuArray()
											.get(quView.getCurrentItem())
											.getId();
								} else {
									addressItem.setQu("");
									mQuId = "";
								}
							} else {

								mShiId = "";
								mQuId = "";

								addressItem.setShi("");
								addressItem.setQu("");
							}

							text1.setText(addressItem.getShen() + " "
									+ addressItem.getShi() + " "
									+ addressItem.getQu()); // 名称

							String y = mShenId + "-" + mShiId + "-" + mQuId;

							// String idcode=IdCardUtils.getIdCard(getString(y),
							// 0);
							// Log.i("xx","idcode1="+idcode);
							// Log.i("xx","idcode2="+IdCardUtils.getIdCard(getString(y)));

							text2.setText(y + "(" + getString(y) + ")"); // code

							text3.setText(IdCardUtils.getIdCard(getString(y)));// 身份证号码

							text4.setText(getxx(getString(y), shenArray)); // 位置码
																			// 所对应的位置

							mCurrentShen = addressItem.getShen();
							mCurrentSi = addressItem.getShi();
							mCurrentQu = addressItem.getQu();

						} else {
							// -1
							Toast.makeText(getApplicationContext(),
									"" + shenView.getCurrentItem(),
									Toast.LENGTH_LONG).show();
						}

					}
				}).setNegativeButton("取消", null).show();
		//

	}

	private class ShenAdapter extends AbstractWheelAdapter {

		private Context context;

		public ShenAdapter(Context context) {
			this.context = context;
		}

		@Override
		public int getItemsCount() {
			return shenArray.size();
		}

		@Override
		public View getItem(int position, View convertView, ViewGroup parent) {
			GViewHolder holder;
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(
						R.layout.wheel_text, (ViewGroup) null, false);
				holder = new GViewHolder();
				holder.textView = (TextView) convertView
						.findViewById(R.id.text);
				convertView.setTag(holder);
			} else {
				holder = (GViewHolder) convertView.getTag();
			}
			holder.textView.setText(shenArray.get(position).getName());
			return convertView;
		}

		private class GViewHolder {
			TextView textView;
		}

	}

	private class ShiAdapter extends AbstractWheelAdapter {

		private Context context;
		private Shen shen;

		public ShiAdapter(Context context, Shen shen) {
			this.context = context;
			this.shen = shen;
		}

		@Override
		public int getItemsCount() {
			if (shen == null) {
				return 0;
			} else {
				return shen.getShiArray().size();
			}
		}

		@Override
		public View getItem(int position, View convertView, ViewGroup parent) {
			GViewHolder holder;
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(
						R.layout.wheel_text, (ViewGroup) null, false);
				holder = new GViewHolder();
				holder.textView = (TextView) convertView
						.findViewById(R.id.text);
				convertView.setTag(holder);
			} else {
				holder = (GViewHolder) convertView.getTag();
			}
			holder.textView.setText(shen.getShiArray().get(position).getName());
			return convertView;
		}

		private class GViewHolder {
			TextView textView;
		}

	}

	private class QuAdapter extends AbstractWheelAdapter {

		private Context context;
		private Shi shi;

		public QuAdapter(Context context, Shi shi) {
			this.context = context;
			this.shi = shi;
		}

		@Override
		public int getItemsCount() {
			if (shi == null) {
				return 0;
			} else {
				return shi.getQuArray().size();
			}
		}

		@Override
		public View getItem(int position, View convertView, ViewGroup parent) {
			GViewHolder holder;
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(
						R.layout.wheel_text, (ViewGroup) null, false);
				holder = new GViewHolder();
				holder.textView = (TextView) convertView
						.findViewById(R.id.text);
				convertView.setTag(holder);
			} else {
				holder = (GViewHolder) convertView.getTag();
			}
			holder.textView.setText(shi.getQuArray().get(position).getName());
			return convertView;
		}

		private class GViewHolder {
			TextView textView;
		}
	}

	public String getString(String str) {
		String sty = "";
		if (str.contains("-")) {
			String s[] = str.split("-");
			for (int i = s.length - 1; i >= 0; i--) {
				if (!"".equals(s[i])) {
					sty = s[i];
					break;
				}
			}
		}
		return sty;
	}

	public String getxx(final String code, List<Shen> shenArray) {
		if (shenArray != null && shenArray.size() > 0) {

			final String sCode = code.substring(0, 2); // 头2位 省id唯一

			List<Shen> ls = Linq4j.asEnumerable(shenArray)
					.where(new Predicate1<Shen>() {

						@Override
						public boolean apply(Shen arg0) {
							return arg0.getId().startsWith(sCode);
						}
					}).toList();
			if (ls != null && ls.size() > 0) {
				String str = "";
				str = ls.get(0).getName();

				if (ls.get(0).getId().equals(code)) {
					return str;
				} else {

					List<Shi> ls_shi = ls.get(0).getShiArray();
					if (ls_shi != null && ls_shi.size() > 0) {
						for (int i = 0, len = ls_shi.size(); i < len; i++) {
							String name = ls_shi.get(i).getName();
							String id = ls_shi.get(i).getId();
							// str= str+"  "+name;
							if (id.equals(code)) {
								str = str + "  " + name;
								return str;
							} else {
								List<Qu> ls_qu = ls_shi.get(i).getQuArray();
								if (ls_qu != null && ls_qu.size() > 0) {

									for (int m = 0, lens = ls_qu.size(); m < lens; m++) {
										String names = ls_qu.get(m).getName();
										String ids = ls_qu.get(m).getId();

										if (ids.equals(code)) {
											str = str + " "
													+ ls_shi.get(i).getName()
													+ " " + names;

											return str;
										}
									}
								}
							}
						}
					}

				}

			}

		}
		return code;

	}
}
