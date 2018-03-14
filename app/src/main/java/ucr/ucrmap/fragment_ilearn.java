package ucr.ucrmap;


import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


public class fragment_ilearn extends Fragment {


    public fragment_ilearn() {
        // Required empty public constructor
    }

    public static fragment_ilearn newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt("argsInstance", instance);
        fragment_ilearn ilearnFragment = new fragment_ilearn();
        ilearnFragment.setArguments(args);
        return ilearnFragment;
    }


    private static final String EXTRA_CUSTOM_TABS_TOOLBAR_COLOR = "android.support.customtabs.extra.TOOLBAR_COLOR";
    private static final String PACKAGE_NAME = "com.android.chrome";
    private CustomTabsClient mClient;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        warmUpChrome();
        launchUrl();


        View v =  inflater.inflate(R.layout.fragment_ilearn, container, false);
        return v;
    }

    private void warmUpChrome() {
        CustomTabsServiceConnection service = new CustomTabsServiceConnection() {
            @Override
            public void onCustomTabsServiceConnected(ComponentName name, CustomTabsClient client) {
                mClient = client;
                mClient.warmup(0);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mClient = null;
            }
        };

        CustomTabsClient.bindCustomTabsService(getActivity(), PACKAGE_NAME, service);
    }

    private void launchUrl() {
        //Uri uri = getActivity().getIntent().getData();
        String url = "https://ilearn.ucr.edu/";
        //String url = "http://ucr.evanced.info/dibs/Login";
        //String url = "http://rweb.ucr.edu";
        //String url = "http://registrar.ucr.edu/Registrar/academic-calendar/index.html";




        CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder().build();
        //customTabsIntent.intent.setData(url);
        customTabsIntent.intent.putExtra(EXTRA_CUSTOM_TABS_TOOLBAR_COLOR, getResources().getColor(R.color.md_red_50));


        PackageManager packageManager = getActivity().getPackageManager();
        List<ResolveInfo> resolveInfoList = packageManager.queryIntentActivities(customTabsIntent.intent, PackageManager.MATCH_DEFAULT_ONLY);

        for (ResolveInfo resolveInfo : resolveInfoList) {
            String packageName = resolveInfo.activityInfo.packageName;
            if (TextUtils.equals(packageName, PACKAGE_NAME))
                customTabsIntent.intent.setPackage(PACKAGE_NAME);
        }

        customTabsIntent.launchUrl(getActivity(), Uri.parse(url));
    }

}
