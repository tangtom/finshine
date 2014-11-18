package com.incito.finshine.activity;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.custom.view.CommFlipDot;
import com.custom.view.DlgCommFilter;
import com.custom.view.MyViewPager;
import com.custom.view.PopDatePicker;
import com.custom.view.PopMarketSign;
import com.custom.view.DlgCommFilter.RefreshFilterListener;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.incito.finshine.R;
import com.incito.finshine.activity.adapter.AdapterCustomerBaseDetailViewPager;
import com.incito.finshine.common.Constant;
import com.incito.finshine.entity.ContractBatch;
import com.incito.finshine.entity.Customer;
import com.incito.finshine.entity.FaxTradingAgreement;
import com.incito.finshine.entity.MarketCsOrder;
import com.incito.finshine.entity.MarketStateReslut;
import com.incito.finshine.entity.spinner.FaxTradingAgreementParam;
import com.incito.finshine.manager.BitmapManager;
import com.incito.finshine.manager.CoreManager;
import com.incito.finshine.manager.JsonParse;
import com.incito.finshine.network.HttpUtils;
import com.incito.finshine.network.HttpUtils.FileSuccessReslut;
import com.incito.finshine.network.HttpUtils.SuccessReslut;
import com.incito.finshine.network.LogCito;
import com.incito.finshine.network.Request;
import com.incito.finshine.network.Request.RequestType;
import com.incito.finshine.network.RequestDefine;
import com.incito.utility.CommonUtil;
import com.incito.utility.DateUtil;
import com.incito.wisdomsdk.event.EventBus;
import com.incito.wisdomsdk.net.http.RequestParams;
import com.incito.wisdomsdk.utils.BitmapUtils;

/**
 * 
 * <dl>
 * <dt>FragmentBindFirst.java</dt>
 * <dd>Description:客户营销 签订合同第四步 小二步 Fragment</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 昆山灵动信息技术有限公司</dd>
 * <dd>CreateDate: 2014年6月16日 下午5:00:59</dd>
 * </dl>
 * 
 * @author LiNing
 */
public class FragmentSignFourthStepOne extends FragmentDetail implements OnClickListener {

	View view;
	private MyViewPager mPager;// 页卡内容
	private List<View> listViews; // Tab页面列表
	private View view1, view2, view3, view4, view5;
	public CommFlipDot flipDot = null;

	private static int currentView = 0;// 判断当前是哪一个
	/* 界面5-1 */
	public TextView txtFirstParty;
	public TextView txtSecondParty;
	public TextView fax;
	public TextView faxPhone;
	public TextView txtExpressAddress;
	public TextView txtPostCode;
	public TextView txtFirstContent;
	public TextView txtSecondContent;
	public TextView txtThirdContent;

	// private TextView txtContent1;
	// private TextView txtContent2;
	// private TextView txtContent3;
	// private TextView txtContent4;
	// private TextView txtContent5;
	/* 界面5-2 */
	private TextView txtSecondContentOne;
	/* 界面5-3 */
	private TextView txtThirdContentOne;
	/* 界面5-4 */
	private TextView txtForthContentOne;
	/* 界面5-5 */
	private ImageView btnPlanTableSign;
	private TextView txtFaxTableCompany;
	private TextView txtFaxTableSignDate;

	// private static ContractBatch contractBatch;

	private FaxTradingAgreement faxTrading;
	private FaxTradingAgreementParam faxTradingParam;

	private static final int DOWNLOAD_INVESTOR_SIGN = 0;

	// 标记照片是否上传 （是否有图片出来）
	private boolean upLoadSign = false;

	private StringBuffer pictureMiddleName = new StringBuffer();

	private HttpUtils httpUtils = null;

	private MarketCsOrder marketCs;

	private MarketStateReslut marketResult;

	public static FragmentSignFourthStepOne newInstance(int id, int position) {

		FragmentSignFourthStepOne f = new FragmentSignFourthStepOne();
		Bundle args = new Bundle();
		args.putInt(FIELD_ID, id);
		f.setArguments(args);
		currentView = position;
		// contractBatch = con;

		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		if (container == null) {
			return null;
		}

		view = inflater.inflate(R.layout.frag_sign_contract_viewpager, container, false);// frag_base_custom_normal

		flipDot = new CommFlipDot(getActivity(), 5, (LinearLayout) view.findViewById(R.id.ltDot));

		initViewPager();
		EventBus.getDefault().register(this, "setImageView");

		return view;
	}

	private void initViewPager() {

		// 上一步
		Button btnPre = (Button) view.findViewById(R.id.btnPre);
		btnPre.setOnClickListener(this);
		// 下一步
		Button btnNext = (Button) view.findViewById(R.id.btnNext);
		btnNext.setOnClickListener(this);

		mPager = (MyViewPager) view.findViewById(R.id.viewPagerCustomerDetail);
		mPager.setPagingEnabled(false);
		listViews = new ArrayList<View>();
		LayoutInflater mInflater = getActivity().getLayoutInflater();
		view1 = mInflater.inflate(R.layout.act_sign_contract_5_1, null);
		listViews.add(view1);
		view2 = mInflater.inflate(R.layout.act_sign_contract_5_2, null);
		listViews.add(view2);
		view3 = mInflater.inflate(R.layout.act_sign_contract_5_3, null);
		listViews.add(view3);
		view4 = mInflater.inflate(R.layout.act_sign_contract_5_4, null);
		listViews.add(view4);
		view5 = mInflater.inflate(R.layout.act_sign_contract_5_5, null);
		listViews.add(view5);

		initUI();

		mPager.setAdapter(new AdapterCustomerBaseDetailViewPager(listViews));
		mPager.setCurrentItem(currentView);
		flipDot.setSeletion(currentView);
		mPager.setOnPageChangeListener(new MyPage(flipDot));
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	private void initUI() {

		/* 界面5-1 */
		for (int i = 0; i < 5; i++) {
			((TextView) listViews.get(i).findViewById(R.id.title)).setText("专项资产管理计划传真交易协议书(一)");
		}

		txtFirstParty = (TextView) view1.findViewById(R.id.txtFirstParty);
		txtSecondParty = (TextView) view1.findViewById(R.id.txtSecondParty);
		fax = (TextView) view1.findViewById(R.id.fax);
		faxPhone = (TextView) view1.findViewById(R.id.faxPhone);
		txtExpressAddress = (TextView) view1.findViewById(R.id.txtExpressAddress);
		txtPostCode = (TextView) view1.findViewById(R.id.txtPostCode);
		txtFirstContent = (TextView) view1.findViewById(R.id.txtFirstContent);
		txtSecondContent = (TextView) view1.findViewById(R.id.txtSecondContent);
		txtThirdContent = (TextView) view1.findViewById(R.id.txtThirdContent);

		/* 界面5-2 */
		txtSecondContentOne = (TextView) view2.findViewById(R.id.txtSecondContentOne);
		/* 界面5-3 */
		txtThirdContentOne = (TextView) view3.findViewById(R.id.txtThirdContentOne);
		/* 界面5-4 */
		txtForthContentOne = (TextView) view4.findViewById(R.id.txtForthContentOne);
		/* 界面5-5 */
		btnPlanTableSign = (ImageView) view5.findViewById(R.id.btnPlanTableSign);
		btnPlanTableSign.setOnClickListener(this);
		txtFaxTableCompany = (TextView) view5.findViewById(R.id.txtFaxTableCompany);
		txtFaxTableSignDate = (TextView) view5.findViewById(R.id.txtFaxTableSignDate);

		initData();
		downIcon();
		isEdit();
	}

	// 下载图片附件
	private void downIcon() {
		if ((faxTrading == null)) {
			return;
		}
		// 本地有，显示本地的，没有网络加载，加载成功后覆盖本地文件
		if (CommonUtil.isNotEmptyfile(Constant.INVESTOR_SIGN + Constant.DOWN_INVESTOR_SIGN + pictureMiddleName)) {
			btnPlanTableSign.setImageBitmap(BitmapManager.decodeSampledBitmapFromFile(Constant.INVESTOR_SIGN
					+ Constant.DOWN_INVESTOR_SIGN + pictureMiddleName, Constant.SIGN_WIDTH, Constant.SIGN_HEIGHT));
			upLoadSign = true;
		} else if (CommonUtil.isNotEmptyfile(Constant.INVESTOR_SIGN + Constant.UP_INVESTOR_SIGN + pictureMiddleName)) {
			btnPlanTableSign.setImageBitmap(BitmapManager.decodeSampledBitmapFromFile(Constant.INVESTOR_SIGN
					+ Constant.UP_INVESTOR_SIGN + pictureMiddleName, Constant.SIGN_WIDTH, Constant.SIGN_HEIGHT));
			upLoadSign = true;
		} else {
			initHttpType(DOWNLOAD_INVESTOR_SIGN);
		}
	}

	private void initHttpType(final int currentType) {

		try {
			JSONObject params = new JSONObject();
			// RequestParams reqParams = new RequestParams();
			int reqId = 0;
			RequestType reqType = null;
			reqType = RequestType.POST;
			switch (currentType) {

			case DOWNLOAD_INVESTOR_SIGN:
				// 获取客户签名
				reqId = RequestDefine.MARKET_SC_DOWNLOAD_ATTACHMENT;
				params.put("transactionId", faxTrading.getId());
				params.put("transactionType", "FAXBUSINESS");
				params.put("moduleType", "AUTOGRAPH");

				httpUtils = new HttpUtils(getActivity(), reqId, reqType, params);
				httpUtils.setShowDiloag(false);
				httpUtils.setDownLoadFile(true);
				break;

			}

			httpUtils.setSuccessListener(new SuccessReslut() {

				@Override
				public void getResluts(JSONObject response) {

				}
			});

			httpUtils.setFileSuccessListener(new FileSuccessReslut() {

				@Override
				public void getResluts(int responeCode, byte[] binaryData) {

					String file = "";
					String fileName = "";

					if (responeCode == 200 && binaryData != null && binaryData.length > 0) {

						switch (currentType) {

						case DOWNLOAD_INVESTOR_SIGN:
							// 获取客户签名
							file = Constant.INVESTOR_SIGN;
							fileName = Constant.DOWN_INVESTOR_SIGN + pictureMiddleName;
							CommonUtil.storeFile(binaryData, file, fileName);
							Bitmap bitMap = BitmapManager.decodeSampledBitmapFromFile(file + "/" + fileName,
									Constant.SIGN_WIDTH, Constant.SIGN_HEIGHT);
							btnPlanTableSign.setImageBitmap(bitMap);
							break;

						default:
							break;
						}
					}
				}
			});
			httpUtils.executeRequest();

		} catch (JSONException e1) {

		}
	}

	private void initData() {
		// CoreManager.getInstance().getProduct().getContractBatch();
		// marketResult = JsonParse.getMarketReslut();
		marketCs = ((ActCustomerMarketProgress) getActivity()).getMarketCs();
		if (marketCs != null) {
			faxTrading = ((ActCustomerMarketProgress) getActivity()).getFax1();
			if (faxTrading == null)
				return;
		} else
			return;

		// marketResult.getCustomerId() +
		pictureMiddleName.append(marketCs.getCustomerId() + "-");
		pictureMiddleName.append(faxTrading.getSalesOrderId() + "-");
		pictureMiddleName.append(faxTrading.getId() + "-6.jpg");

		txtFirstParty.setText(faxTrading.getCustomerName());
		txtSecondParty.setText(faxTrading.getPublisherName());
		fax.setText(faxTrading.getServiceFax());
		faxPhone.setText(faxTrading.getConfirmedTelephone());
		txtExpressAddress.setText(faxTrading.getPostAddress());
		txtPostCode.setText(faxTrading.getPostCode());

		txtFaxTableCompany.setText(faxTrading.getPublisherName());
		txtFaxTableSignDate.setText(DateUtil.formatDate2String(new Date(faxTrading.getDateOfSign()),
				DateUtil.FORMAT_YYYY_MM_DD_ZH));

		// <$p$> 暂时的分页标示符 需要后台讲合同内容变成下面的str1-str5格式
		String[] tempContent = faxTrading.getContent().split("p");
		for (int i = 0; i < tempContent.length; i++) {

			((TextView) listViews.get(i).findViewById(R.id.txtContent)).setText(tempContent[i]);
		}

		// txtContent1.setText(str1);
		// txtContent2.setText(str2);
		// txtContent3.setText(str3);
		// txtContent4.setText(str4);
		// txtContent5.setText(str5);

		// faxTrading = new FaxTradingAgreement();
		// faxTrading.setContent("第一条 传真交易业务范围\r\n1、本协议所表述的“传真交易”是指甲方在乙"
		// + "方签署本协议后，将业务申请表及其相关资料以传真方式向乙方提交专户业务申请的交易方式"
		// + "。\r\n2、传真交易业务范围包括：交易类：乙方所管理的专户交易的认购、参与、退出。"
		// + "账户类：客户资料变更（仅指甲方非关健信息的变更，即不包括甲方姓名、证件类型、证件"
		// + "号码、印鉴及银行账号的变更）\r\n3、对于除上述业务类型外的其它业务，乙方不接受甲方以"
		// + "传真方式提交的业务申请。\r\n\r\n第二条 传真交易受理条件\r\n1、甲方须在乙方规定的相关"
		// + "专户交易受理日和受理时间内提交传真申请，否则，视同无效申请。甲方委托申请的时间以乙方"
		// + "系统记载的时间为准。\r\n2、甲方在乙方直销网点已开立交易账户，且在乙方受理传真业务"
		// + "过程中该交易账户状态正常。\r\n3、甲方在该交易日将足额资金划入乙方指定银行账户后，"
		// + "方可办理认购或参与业务；甲方在该交易日的直销交易账户内留有足够的基金份额，方可办理"
		// + "退出。否则，视同无效申请。\r\n4、申请办理传真交易的投资人应预留印鉴。\r\n5、甲方需"
		// + "传真的资料包括：业务申请书（填写完整准确，并不得涂改）、银行划款凭证复印件（认购、"
		// + "参与交易申请时）及甲方要求的其他附件。\r\n\r\n第三条 传真交易受理流程\r\n1、甲方在"
		// + "进行本协议书规定的传真交易时，应将有关申请资料传真给乙方，乙方指定专门的传真电话受"
		// + "理甲方的申请，除此之外的传真申请无效。\r\n2、乙方业务人员在规定交易时段内收到传真申"
		// + "请后，审核传真文件内容，如完全符合下述条件，乙方应受理委托申请：\r\n传真业务内容符"
		// + "合法律法规、资产管理合同及乙方的业务规则；\r\n传真申请表及其相关申请材料的内容完整"
		// + "、清晰、准确；\r\n传真申请表中的印鉴与开户预留印鉴核对一致。\r\n3、申请书传真件作"
		// + "为交易申请凭证，乙方收到甲方符合本协议第二条规定的传真件则有权认为该传真件是乙方真"
		// + "实意愿的表示。\r\n4、甲方在通过传真递交申请书后须在当天15：00以前与乙方受理业务的"
		// + "直销网点进行确认传真申请事宜，如在上述时间里没有进行电话确认的，或确认的电话与传真"
		// + "的资料有任何不一致或不完整的，乙方有权不受理该笔传真交易。\r\n5、甲、乙双方确认，"
		// + "即使乙方的专户业务规则存在其他规定, 本协议项下甲方的传真交易申请一经甲方电话确认"
		// + "则不可撤消。\r\n6、甲方应在发出传真后的五个工作日内将有关申请书及附件原件以挂号信"
		// + "或专递的形式寄至乙方受理业务的直销网点。如乙方在甲方发出传真后的15个工作日内未接"
		// + "到上述资料（资料的到达时间以到达地邮戳为准），乙方有权中止甲方传真交易的资格，并"
		// + "保留采取其他措施的权利，甲方应对由此可能造成的损失负责。\r\n\r\n第四条 免责条"
		// + "款\r\n1、出现下列情形之一的，乙方对甲方可能产生的损失不承担责任：\r\n1）乙方因传真"
		// + "设备发生故障或其他不可抗力导致无法受理传真交易申请的；\r\n2）由于电信网等原因，"
		// + "传真交易申请出现中断、停顿、延迟、数据错误等情况的；\r\n3）投资者身份被仿冒，电"
		// + "话确认时因语音失真无法识别的；\r\n4）印鉴被伪造，电子验印或人工验印对传真件无法"
		// + "识别的；\r\n5）投资者的传真设备与传真交易系统不相匹配，无法下达申请或申请失败；"
		// + "\r\n6）投资者因操作不当造成申请失败或申请失误；\r\n7）法律、法规限制传真交易进行"
		// + "的；\r\n8）其他妨碍乙方真实、完整地受理传真交易申请的情形。\r\n2、甲方和乙方确认"
		// + "甲方包含本协议第二条规定的内容的传真件与其原件有同等效力， 乙方可以充分信赖该传真"
		// + "件而受理传真交易。如果该传真件上一个或多个签名（或盖章）是伪造或未经授权的，或任"
		// + "何表述是伪造的或未经授权的，只要乙方诚信行事并相信传真件的表述和签名（或盖章）是"
		// + "真实的或经授权的，则甲方应当承认该传真交易对自己的约束力，承受由此造成的损失且补"
		// + "偿乙方由此受到的损失。\r\n\r\n第五条 协议的变更及生效\r\n1、乙方保留修改或增补本"
		// + "协议内容的权利。修改条款通知以书面形式公告于乙方的营业场所，或以其他形式通知甲方"
		// + "。\r\n2、本协议签署后，若有关法律法规和《资产管理合同》、《投资说明书》及其他双"
		// + "方应共同遵守的文件发生修订，本协议与之不相符合的内容及条款自行失效，但本协议其他"
		// + "内容和条款继续有效。\r\n3、本协议自双方签章之日起生效，发生下列情况时自动终止："
		// + "双方书面同意终止甲方撤销专户交易账户或专户账户一方违约，另一方通知对方终止本协议"
		// + "\r\n\r\n第六条 其它事项\r\n1、本协议一式两份，甲乙双方各执一份，具有同等法律效力"
		// + "。\r\n2、本协议未尽事宜由双方协商解决。\r\n");

		// String str1 = "第一条 传真交易业务范围\r\n1、本协议所表述的“传真交易”是指甲方在乙"
		// + "方签署本协议后，将业务申请表及其相关资料以传真方式向乙方提交专户业务申请的交易方式"
		// + "。\r\n2、传真交易业务范围包括：交易类：乙方所管理的专户交易的认购、参与、退出。"
		// + "账户类：客户资料变更（仅指甲方非关健信息的变更，即不包括甲方姓名、证件类型、证件"
		// + "号码、印鉴及银行账号的变更）\r\n3、对于除上述业务类型外的其它业务，乙方不接受甲方以"
		// + "传真方式提交的业务申请。";
		//
		// String str2 = "第二条 传真交易受理条件\r\n1、甲方须在乙方规定的相关"
		// + "专户交易受理日和受理时间内提交传真申请，否则，视同无效申请。甲方委托申请的时间以乙方"
		// + "系统记载的时间为准。\r\n2、甲方在乙方直销网点已开立交易账户，且在乙方受理传真业务"
		// + "过程中该交易账户状态正常。\r\n3、甲方在该交易日将足额资金划入乙方指定银行账户后，"
		// + "方可办理认购或参与业务；甲方在该交易日的直销交易账户内留有足够的基金份额，方可办理"
		// + "退出。否则，视同无效申请。\r\n4、申请办理传真交易的投资人应预留印鉴。\r\n5、甲方需"
		// + "传真的资料包括：业务申请书（填写完整准确，并不得涂改）、银行划款凭证复印件（认购、"
		// + "参与交易申请时）及甲方要求的其他附件。\r\n第三条 传真交易受理流程\r\n1、甲方在"
		// + "进行本协议书规定的传真交易时，应将有关申请资料传真给乙方，乙方指定专门的传真电话受"
		// + "理甲方的申请，除此之外的传真申请无效。";
		//
		// String str3 = "2、乙方业务人员在规定交易时段内收到传真申"
		// + "请后，审核传真文件内容，如完全符合下述条件，乙方应受理委托申请：\r\n传真业务内容符"
		// + "合法律法规、资产管理合同及乙方的业务规则；\r\n传真申请表及其相关申请材料的内容完整"
		// + "、清晰、准确；\r\n传真申请表中的印鉴与开户预留印鉴核对一致。\r\n3、申请书传真件作"
		// + "为交易申请凭证，乙方收到甲方符合本协议第二条规定的传真件则有权认为该传真件是乙方真"
		// + "实意愿的表示。\r\n4、甲方在通过传真递交申请书后须在当天15：00以前与乙方受理业务的"
		// + "直销网点进行确认传真申请事宜，如在上述时间里没有进行电话确认的，或确认的电话与传真"
		// + "的资料有任何不一致或不完整的，乙方有权不受理该笔传真交易。\r\n5、甲、乙双方确认，"
		// + "即使乙方的专户业务规则存在其他规定, 本协议项下甲方的传真交易申请一经甲方电话确认"
		// + "则不可撤消。\r\n6、甲方应在发出传真后的五个工作日内将有关申请书及附件原件以挂号信"
		// + "或专递的形式寄至乙方受理业务的直销网点。如乙方在甲方发出传真后的15个工作日内未接"
		// + "到上述资料（资料的到达时间以到达地邮戳为准），乙方有权中止甲方传真交易的资格，并"
		// + "保留采取其他措施的权利，甲方应对由此可能造成的损失负责。\r\n第四条 免责条"
		// + "款";
		//
		// String str4 = "1、出现下列情形之一的，乙方对甲方可能产生的损失不承担责任：\r\n1）乙方因传真"
		// + "设备发生故障或其他不可抗力导致无法受理传真交易申请的；\r\n2）由于电信网等原因，"
		// + "传真交易申请出现中断、停顿、延迟、数据错误等情况的；\r\n3）投资者身份被仿冒，电"
		// + "话确认时因语音失真无法识别的；\r\n4）印鉴被伪造，电子验印或人工验印对传真件无法"
		// + "识别的；\r\n5）投资者的传真设备与传真交易系统不相匹配，无法下达申请或申请失败；"
		// + "\r\n6）投资者因操作不当造成申请失败或申请失误；\r\n7）法律、法规限制传真交易进行"
		// + "的；\r\n8）其他妨碍乙方真实、完整地受理传真交易申请的情形。\r\n2、甲方和乙方确认"
		// + "甲方包含本协议第二条规定的内容的传真件与其原件有同等效力， 乙方可以充分信赖该传真"
		// + "件而受理传真交易。如果该传真件上一个或多个签名（或盖章）是伪造或未经授权的，或任"
		// + "何表述是伪造的或未经授权的，只要乙方诚信行事并相信传真件的表述和签名（或盖章）是"
		// + "真实的或经授权的，则甲方应当承认该传真交易对自己的约束力，承受由此造成的损失且补"
		// + "偿乙方由此受到的损失。";
		//
		// String str5 = "第五条 协议的变更及生效\r\n1、乙方保留修改或增补本"
		// + "协议内容的权利。修改条款通知以书面形式公告于乙方的营业场所，或以其他形式通知甲方"
		// + "。\r\n2、本协议签署后，若有关法律法规和《资产管理合同》、《投资说明书》及其他双"
		// + "方应共同遵守的文件发生修订，本协议与之不相符合的内容及条款自行失效，但本协议其他"
		// + "内容和条款继续有效。\r\n3、本协议自双方签章之日起生效，发生下列情况时自动终止："
		// + "双方书面同意终止甲方撤销专户交易账户或专户账户一方违约，另一方通知对方终止本协议"
		// + "\r\n第六条 其它事项\r\n1、本协议一式两份，甲乙双方各执一份，具有同等法律效力"
		// + "。\r\n2、本协议未尽事宜由双方协商解决。";
	}

	private boolean isFullData() {
		if (!upLoadSign) {
			CommonUtil.showToast("请先签名", getActivity());
			return false;
		}
		return true;
	}

	private boolean isEdit() {
		if (marketCs.getMarketingStatusId() > 3) {
			btnPlanTableSign.setEnabled(false);
			return false;
		}
		return true;
	}

	private void switchView(boolean isPre) {
		if (isPre) // 上一页
		{
			if (currentView == 0) {
				((ActCustomerMarketProgress) getActivity()).showDetails(ActCustomerMarketProgress.F_SIGN_THIRD, 3);
			} else {
				currentView--;
				mPager.setCurrentItem(currentView, true);
			}
		}

		else // 下一页
		{
			if (currentView == 4) {
				if (marketCs.getMarketingStatusId() < 4) {// 编辑模式
					if (isFullData()) {
						faxTradingParam = new FaxTradingAgreementParam(faxTrading);
						// CoreManager.getInstance().getProduct().setFaxParam1(faxTradingParam);
						((ActCustomerMarketProgress) getActivity()).setFaxParam1(faxTradingParam);
						((ActCustomerMarketProgress) getActivity()).showDetails(ActCustomerMarketProgress.F_SIGN_FIFTH, 0);
					}
				}else{//预览模式
					((ActCustomerMarketProgress) getActivity()).showDetails(ActCustomerMarketProgress.F_SIGN_FIFTH, 0);
				}
			} else {
				currentView++;
				mPager.setCurrentItem(currentView, true);
			}
		}
	}

	@Override
	public void onClick(final View v) {
		switch (v.getId()) {
		case R.id.btnPre:
			switchView(true);
			break;
		case R.id.btnNext:
			switchView(false);
			break;
		case R.id.btnPlanTableSign:
			PopMarketSign pms = new PopMarketSign(this.getActivity(), v);
			pms.showPopWindow();
			break;

		default:
			break;
		}
	}

	private class MyPage implements OnPageChangeListener {

		CommFlipDot flipDot = null;

		public MyPage(CommFlipDot flipDot) {
			super();
			this.flipDot = flipDot;
		}

		@Override
		public void onPageScrollStateChanged(int status) {

		}

		@Override
		public void onPageScrolled(int curPage, float arg1, int arg2) {
			currentView = curPage;
		}

		@Override
		public void onPageSelected(int curPage) {
			if (flipDot != null) {
				flipDot.setSeletion(curPage);
			}
		}

	}

	@Override
	public void onDestroy() {
		EventBus.getDefault().unregister(this);
		super.onDestroy();
	}

	// 签名完成之后存到本地
	public void setImageView(Bitmap bm) {
		btnPlanTableSign.setImageBitmap(bm);

		CommonUtil.storeFile(BitmapUtils.bmpToByteArray(bm, false), Constant.INVESTOR_SIGN, Constant.UP_INVESTOR_SIGN
				+ pictureMiddleName);
		upLoadSign = true;
	}
}
