package cn.ut.logdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import cn.ut.library.bean.LogLevel;
import cn.ut.library.logcat.UTLog;
import cn.ut.logdemo.monitor.LogMonitorActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mShowLogBtn;
    private Button mCrashBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initViews();
        testUTLog();
    }

    private void initViews() {
        mShowLogBtn = (Button) findViewById(R.id.activity_main_show_logs_btn);
        mCrashBtn = (Button) findViewById(R.id.activity_main_crash_btn);

        mShowLogBtn.setOnClickListener(MainActivity.this);
        mCrashBtn.setOnClickListener(MainActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_main_show_logs_btn:
                startActivity(new Intent(MainActivity.this, LogMonitorActivity.class));
                break;
            case R.id.activity_main_crash_btn:
                throw new RuntimeException("just for test.");
            default:
                break;
        }
    }

    private void testUTLog() {
        UTLog.d("Hans", "log here 1");

        UTLog.setDebugEnabled(false);
        UTLog.d("Hans", "log here 2");
        UTLog.setDebugEnabled(true);
        UTLog.d("Hans", "log here 3");

        UTLog.setLogLevel(LogLevel.DEBUG);
        UTLog.v("Hans", "log here 4");
        UTLog.d("Hans", "log here 5");
        UTLog.i("Hans", "log here 6");
        UTLog.w("Hans", "log here 7");
        UTLog.e("Hans", "log here 8");

        UTLog.setTagPrefix("Hans");
        UTLog.d("Hans", "log here 9");
        UTLog.setTagPrefix("");
        UTLog.d("Hans", "log here 10");

        UTLog.title("YourTitle").d("Hans", "log here 11");
        UTLog.watchStack().d("Hans", "log here 12");
        UTLog.watchMemory().d("Hans", "log here 13");
        UTLog.watchThread().d("Hans", "log here 14");

        UTLog.title("YourTitle").watchStack().watchThread().watchMemory().d("Hans", "log here 15");
    }
}
