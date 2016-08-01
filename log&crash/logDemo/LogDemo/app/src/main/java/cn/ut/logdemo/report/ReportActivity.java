package cn.ut.logdemo.report;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import cn.ut.logdemo.R;

/**
 * Created by chenhang on 2016/6/16.
 */
public class ReportActivity extends AppCompatActivity {

    private static final String KEY_CRASH_INFO = "KEY_CRASH_INFO";

    private TextView mCrashPlayer;
    private String mCrashInfo;

    public static void startup(Context context, String crashInfo) {
        Intent intent = new Intent(context, ReportActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(KEY_CRASH_INFO, crashInfo);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash_report);
        initViews();
        initData();
        bindData();
    }

    private void bindData() {
        mCrashPlayer.setText(mCrashInfo == null ? "null" : mCrashInfo);
    }

    private void initData() {
        if (getIntent() != null) {
            mCrashInfo = getIntent().getStringExtra(KEY_CRASH_INFO);
        }
    }

    private void initViews() {
        mCrashPlayer = (TextView) findViewById(R.id.report_crash_info);
    }
}
