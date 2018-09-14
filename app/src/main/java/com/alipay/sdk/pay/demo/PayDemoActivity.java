package com.alipay.sdk.pay.demo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import com.alipay.sdk.app.PayTask;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

public class PayDemoActivity extends FragmentActivity {

	// 商户PID
	//partner PID
	public static final String PARTNER = "2088621909478396";
	// 商户收款账号
	//Seller's Alipay account
	public static final String SELLER = "forex_314438@alitest.com";
	// 商户私钥，pkcs8格式
	//RSA private key of merchant,pkcs8 format
	public static final String RSA_PRIVATE = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCuEWJmIGDnmZzyfOrBzUODfUg3csqoaiBEy+VVRg+74z5409/sPl/BXwL4mLmfLXG/OifJvJcneMpWl3lT8tpZI5BEIaHIXYmyTN+OMddV4CSaFz4lQtLLqiIxemSovilP1ZMYWlFdBpFgEsVk9honUdZRlOoDbvplYjTnEafpEXcKn5y+I6terQv2vgpuCo1EQHsA94F4UN2i9OXzcxzMi2V4M40rwniZj03wQ00+IeR7YmeuESzPzhOuV4XJCnRMWFYVrpgoPXhhm/I79vxSJRfAGnkcDxkEqRstpBvYKHd3bxmwbOC8FQaUY0y3zzIyQnFvd2rZLLNyygxHpg2BAgMBAAECggEACwEAVnJvjdSRLmclNBE01mgiG5c/JnxnA9Jb8hMWxYrtFRhXzIh45zGJb4vAGEb4LN2RTFiGubESIX0750mv8fikkxoJLzBylvnzG/scoMolpjQX8qURzFw6AyS4nk4/MFPRE7YJ9LaIlCk7n1FLf/bVdJzUWlk2rRgTPhRIqBwe2Mm/Lug13RSSpJ/Nru3ubZ0oQJpPlFCAF44D5wWQVabgNf0ZqHWUIVNELJXEGr8sG83UO3rAnsQ4p54QyvHASggMAyClNp7CZg6WdadYHencNE1YEPRSuBhOTMkD81RxslAhfpdJwssIz9BXcaX6iCw9E09wcpZgXjqxe7pyQQKBgQDTy3rTJ036bc1SIeoQNZvP2pBJqDL3McHgZO7ATvuojnBhfwX0B8p1XzC4CAohTqQcPrMTwWqy6OylTuphKZWL6R0sG6VMtQ1GTp80AYFN42LXPJ4j4dcE4/iFvRJfsXyuJTgUhMDJwPq3M4NkbE3qk/oO18CoWNJrf4eGj+aXyQKBgQDSZhjdEyJDrS+TsbQDMlbOADyXEcwBbohKanHxrIhNDcWg/hon56VsZvVDTUFY4z1X1zpa8xPXDPQgtiLoIP+tBRpJ2Z4BKSoTvwOhulE9J2RCumJL0RarfV/sEKZzhnwFNiwoKVZs/SeOyQt0QIq4/tB+zBSybx+0ugmhb5ST+QKBgQDHOhE/ryla+Q66w8w5kzQOQbqYju/iN5v4SPmNTL7Nv69XBvXCp9F6tyBM/MMKsb8OEqzBWJ/Vy+VPWvx+iW9zPMWESD0l+umRyUNY2FGZwtunPA5GEpsArrGInGI8QejWp4wpQPdY0X8F9h8SyA5DKw9+BtXjhinplP0XRyXSgQKBgAxyV8Q0TpGcXDUrjQ+nWbVEFFGHjv8Cr/NfCrCWWVXdm7Rjc96qeIHKMXYyysvcXZOluuEIgkL1lDefLr92vElbBOwbgJpm+gNDCI73u7iXbPn5lrkPfHzWpOcNMRuieBHC26fgkilObJmo0RGmg85f3KuznQHVSxXg75LqS+rZAoGBAMCUjulTUCmeP+8HLNEL6UrST6e/fLG5Zrre7S3Mm+Y2z2CMa2tkyZTuKKkolhFNGOfl5jBsDQ1bmcqauYniroFE94l8PTXVFT8CK7+cE3vBIadlQMdDf279icqvODfrOMISZ0xASSAVLp2219l+2vlI0KC7dgWdPk5oV8RCpkTB";
	// 支付宝公钥
	//Alipay public key
	public static final String RSA_PUBLIC = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArhFiZiBg55mc8nzqwc1Dg31IN3LKqGogRMvlVUYPu+M+eNPf7D5fwV8C+Ji5ny1xvzonybyXJ3jKVpd5U/LaWSOQRCGhyF2JskzfjjHXVeAkmhc+JULSy6oiMXpkqL4pT9WTGFpRXQaRYBLFZPYaJ1HWUZTqA276ZWI05xGn6RF3Cp+cviOrXq0L9r4KbgqNREB7APeBeFDdovTl83MczItleDONK8J4mY9N8ENNPiHke2JnrhEsz84TrleFyQp0TFhWFa6YKD14YZvyO/b8UiUXwBp5HA8ZBKkbLaQb2Ch3d28ZsGzgvBUGlGNMt88yMkJxb3dq2SyzcsoMR6YNgQIDAQAB";
	private static final int SDK_PAY_FLAG = 1;

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@SuppressWarnings("unused")
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case SDK_PAY_FLAG: {
					PayResult payResult = new PayResult((String) msg.obj);
					/**
					 * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://global.alipay.com/service/app/9) 建议商户依赖异步通知
					 * Logics verification of synchronous results must be processed at server（For rules pls check https://global.alipay.com/service/app/9)
					 * It is strongly recommended that sellers should directly check the asynchronous notification at server and neglect the synchronous response
					 */
					String resultInfo = payResult.getResult();// 同步返回需要验证的信息The info needs to be verified from synchronous results

					String resultStatus = payResult.getResultStatus();
					// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
					// check whether the resultStatus is "9000" which demonstrate paid succeed,for detailed info of the codes returned pls check in the documents
					if (TextUtils.equals(resultStatus, "9000")) {
						Toast.makeText(PayDemoActivity.this, "支付成功Paid succeed", Toast.LENGTH_SHORT).show();
					} else {
						// 判断resultStatus 为非"9000"则代表可能支付失败
						// "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
						//If the resultStatus is not "9000" the result might be failure.
						//"8000" means under processing due to the payment channel or system (payment might have been made successfully), please check the payment status returns from Alipay async notifications. (Status of small probability)
						if (TextUtils.equals(resultStatus, "8000")) {
							Toast.makeText(PayDemoActivity.this, "支付结果确认中Under processing", Toast.LENGTH_SHORT).show();

						} else {
							// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
							//You can consider other codes as failure,including user cancel the payment or system error etc.
							Toast.makeText(PayDemoActivity.this, "支付失败Failure", Toast.LENGTH_SHORT).show();

						}
					}
					break;
				}
				default:
					break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay_main);
	}

	/**
	 * call alipay sdk pay. 调用SDK支付
	 *
	 */
	public void pay(View v) {
		if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE) || TextUtils.isEmpty(SELLER)) {
			new AlertDialog.Builder(this).setTitle("警告alert").setMessage("需要配置Pls configure PARTNER | RSA_PRIVATE| SELLER")
				.setPositiveButton("确定OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialoginterface, int i) {
						//
						finish();
					}
				}).show();
			return;
		}
		String orderInfo = getOrderInfo("测试的商品product", "该测试商品的详细描述description body", "0.01");

		/**
		 * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
		 * Pay special attention,this signature logic needs to be done on sever side,don't reveal you private key in the code of client!
		 */
		String sign = sign(orderInfo);
		try {
			/**
			 * 仅需对sign 做URL编码
			 * Only sign needs to be URL encoded
			 */
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		/**
		 * 完整的符合支付宝参数规范的订单信息
		 * The order info including the integral Alipay parameters
		 */
		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();

		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				//Construct object:PayTask
				PayTask alipay = new PayTask(PayDemoActivity.this);
				// 调用支付接口，获取支付结果
				//call the pay interface ,get the payment result
				String result = alipay.pay(payInfo, true);

				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		// 必须异步调用
		//asynchronous call
		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}

	/**
	 * get the sdk version. 获取SDK版本号
	 *
	 */
	public void getSDKVersion() {
		PayTask payTask = new PayTask(this);
		String version = payTask.getVersion();
		Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 原生的H5（手机网页版支付切native支付） 【对应页面网页支付按钮】
	 * Native H5(From app website to native )【Correspondent pay button on website】
	 *
	 * @param v
	 */
	public void h5Pay(View v) {
		Intent intent = new Intent(this, H5PayDemoActivity.class);
		Bundle extras = new Bundle();
		/**
		 * url是测试的网站，在app内部打开页面是基于webview打开的，demo中的webview是H5PayDemoActivity，
		 * demo中拦截url进行支付的逻辑是在H5PayDemoActivity中shouldOverrideUrlLoading方法实现，
		 * 商户可以根据自己的需求来实现
		 * url is the test website，it opens based on webview in app，the webview in demo is H5PayDemoActivity，
		 * The logic to intercept the url to pay is achieved by function shouldOverrideUrlLoading in H5PayDemoActivity in demo，
		 * Merchants can realize according to their own requirements
		 */
		String url = "http://m.taobao.com";
		// url可以是一号店或者淘宝等第三方的购物wap站点，在该网站的支付过程中，支付宝sdk完成拦截支付
		//url can be the wap site of shopping site like taobao etc,during the payment process ,Alipay sdk intercept and pay
		extras.putString("url", url);
		intent.putExtras(extras);
		startActivity(intent);
	}

	/**
	 * create the order info. 创建订单信息
	 *
	 */
	private String getOrderInfo(String subject, String body, String price) {

		// 签约合作者身份ID
		//parter ID
		String orderInfo = "partner=" + "\"" + PARTNER + "\"";

		// 签约卖家支付宝账号
		//Seller's Alipay account
		orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

		// 商户网站唯一订单号
		//The unique transanction id in your system
		orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

		// 商品名称
		//product name
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// 商品详情
		//detailed product info
		orderInfo += "&body=" + "\"" + body + "\"";

		// 商品金额
		//specifies the foreign price of the items
		orderInfo += "&total_fee=" + "\"" + price + "\"";

		// 服务器异步通知页面路径
		//Async notification page
		orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm" + "\"";

		// 服务接口名称， 固定值
		//service name,fixed,no need to modify
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 支付类型， 固定值
		//payment type,fixed,no need to modify
		orderInfo += "&payment_type=\"1\"";

		// 参数编码， 固定值
		//charset,fixed,no need to modify
		orderInfo += "&_input_charset=\"utf-8\"";


		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		// set the overtime of non-payment transaction
		// the default value is 30m ，The transaction will be closed automatically once the time is up.
		// Range of values：1m～15d。
		// m-minute, h-hour, d-day, 1c-current day (Whenever the transaction is created, it will be closed at 0:00).
		// Demical point of the numerical value of this parameter is rejected, for example, 1.5h can be converted into 90m.
		orderInfo += "&it_b_pay=\"30m\"";

		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
		//Token (including account information) returned by open platform (authorization token, a right for merchant to access to some services of Alipay within a specified period).
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		// The page redirected to after the payment，nullable
		orderInfo += "&return_url=\"m.alipay.com\"";
		//global pay special parameters
		orderInfo += "&currency=\"USD\"";
		orderInfo += "&forex_biz=\"FP\"";

		return orderInfo;
	}

	/**
	 * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
	 *
	 */
	private String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
		Date date = new Date();
		String key = format.format(date);

		Random r = new Random();
		key = key + r.nextInt();
		key = key.substring(0, 15);
		return key;
	}

	/**
	 * sign the order info. 对订单信息进行签名
	 *
	 * @param content
	 *            待签名订单信息
	 */
	private String sign(String content) {
		return SignUtils.sign(content, RSA_PRIVATE);
	}

	/**
	 * get the sign type we use. 获取签名方式
	 *
	 */
	private String getSignType() {
		return "sign_type=\"RSA\"";
	}

}
