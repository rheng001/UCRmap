package theunderground.com.ucrmap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by Sara on 5/2/16.
 */
public class WebViewActivity extends Activity {
    WebView mWebView = null;
    TextView mTextView = null;
    private ImageButton mBackButton = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view_layout);
        mWebView = (WebView) findViewById(R.id.displayView);
        mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        WebView myWebView = (WebView) findViewById(R.id.displayView);
        myWebView.setWebViewClient(new WebViewClient());


        mTextView = (TextView) findViewById(R.id.libraryTitle);
        Bundle bundle = getIntent().getExtras();
        String filePath = bundle.getString("Path");
        String title = bundle.getString("Title");
        mTextView.setText(title);
        mWebView.loadUrl(filePath);
        mBackButton = (ImageButton)findViewById(R.id.backButton);

        if(bundle.getString("JavaScript") != null)
        {
            final String permissions = bundle.getString("JavaScript");
            mWebView.getSettings().setJavaScriptEnabled(true);
            mWebView.getSettings().setDomStorageEnabled(true);
            mWebView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);

                    mWebView.loadUrl(permissions);

                }

            });
        }



        mBackButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                back();
            }
        });
    }

    private void back(){
        finish();
    }
}
