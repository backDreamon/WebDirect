package spider.drcar.kr.webdirect;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.webkit.GeolocationPermissions;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import static spider.drcar.kr.webdirect.R.id.webView;

public class MainActivity extends AppCompatActivity {

    WebView wb;


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (wb.canGoBack() == true) {
                        wb.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        wb = (WebView) findViewById(webView);

        // WebViewClient 지정
        wb.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {

                handler.proceed();

                super.onReceivedSslError(view, handler, error);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

                return super.shouldOverrideUrlLoading(view, request);
            }


        });

        // 웹뷰에서 자바스크립트 사용
        wb.getSettings().setJavaScriptEnabled(true);
        // 웹뷰에서 Geoloacion 사용
        wb.getSettings().setGeolocationEnabled(true);
        // 웹뷰에서 플러그인 허용
        wb.getSettings().setSupportZoom(true);


        wb.getSettings().setAppCacheEnabled(true);
        wb.getSettings().setDatabaseEnabled(true);
        //wb.getSettings().setDomStorageEnabled(true);

        wb.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("닥터카스파이더")
                        .setMessage(message)
                        .setPositiveButton(android.R.string.ok, new AlertDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                result.confirm();
                            }
                        })
                        .setCancelable(true)
                        .create()
                        .show();

                return true;

            }

            /*@Override
            public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
                new AlertDialog.Builder(getApplicationContext())
                        .setTitle("닥터카스파이더")
                        .setMessage(message)
                        .setPositiveButton(android.R.string.ok, new AlertDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                result.confirm();
                            }
                        })
                        .setCancelable(true)
                        .create()
                        .show();


                return super.onJsConfirm(view, url, message, result);
            }*/

            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {

                callback.invoke(origin, true, false);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }
        });


        // URL 지정
        wb.loadUrl("https://dev-dreamon.cloud.or.kr/mobile");


    }


}
