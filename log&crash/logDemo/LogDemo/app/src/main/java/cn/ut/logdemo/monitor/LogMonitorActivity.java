package cn.ut.logdemo.monitor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.List;

import cn.ut.library.monitor.Logcat;
import cn.ut.library.bean.LogInfo;
import cn.ut.library.utils.StringUtils;
import cn.ut.logdemo.R;
import cn.ut.logdemo.monitor.adapter.LogAdapter;

/**
 * Created by chenhang on 2016/6/1.
 */
public class LogMonitorActivity extends AppCompatActivity implements Logcat.Callback, CompoundButton.OnCheckedChangeListener {

    private RecyclerView mRecyclerView;
    private Switch mSwitch;
    private EditText mFilter;
    private LogAdapter mAdapter;
    private List<LogInfo> mData = new ArrayList<>();
    private Logcat mLogcat;
    private boolean mBottom = true;
    private int mScrollState = RecyclerView.SCROLL_STATE_IDLE;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_monitor);
        initViews();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLogcat != null) {
            mLogcat.stop();
        }
    }

    private void initViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.log_monitor_list);
        mSwitch = (Switch) findViewById(R.id.log_monitor_enable);
        mFilter = (EditText) findViewById(R.id.log_monitor_filter);

        mSwitch.setOnCheckedChangeListener(LogMonitorActivity.this);
        setupRecyclerView();

    }

    private void setupRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(LogMonitorActivity.this));
        mAdapter = new LogAdapter(LogMonitorActivity.this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setData(mData);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                mScrollState = newState;
                if (newState == RecyclerView.SCROLL_STATE_IDLE || newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    if (mRecyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                        LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                        int position = layoutManager.findLastVisibleItemPosition();
                        mBottom = (position == layoutManager.getItemCount() - 1 &&
                                layoutManager.findViewByPosition(position).getBottom() <= mRecyclerView.getHeight());
                    }
                }
            }
        });
    }

    @Override
    public void onLog(final LogInfo log) {
        String filter = mFilter.getText().toString();
        if (!StringUtils.isEmpty(filter) && !log.getMessage().contains(filter)) {
            return;
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                boolean needScroll = mBottom && mScrollState == RecyclerView.SCROLL_STATE_IDLE;
                mData.add(log);
                mAdapter.notifyDataSetChanged();
                if (needScroll) {
                    mRecyclerView.smoothScrollToPosition(mData.size() - 1);
                }
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            mLogcat = new Logcat();
            mLogcat.start(LogMonitorActivity.this);
        } else {
            if (mLogcat != null) {
                mLogcat.stop();
            }
        }
    }
}
