package cn.ut.logdemo.monitor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.ut.library.bean.LogInfo;
import cn.ut.logdemo.R;

/**
 * Created by chenhang on 2016/6/1.
 */
public class LogAdapter extends RecyclerView.Adapter<LogAdapter.ViewHolder> {

    private Context mContext;
    private List<LogInfo> mData;

    public LogAdapter(Context context) {
        mContext = context;
    }

    public void setData(List<LogInfo> data) {
        mData = data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_logcat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        LogInfo log = mData.get(position);
        holder.mLog.setText(log.getMessage());
        int colorId;
        switch (log.getLevel()) {
            case VERBOSE:
                colorId = R.color.log_verbose;
                break;
            case DEBUG:
                colorId = R.color.log_debug;
                break;
            case INFO:
                colorId = R.color.log_info;
                break;
            case WARN:
                colorId = R.color.log_warn;
                break;
            case ERROR:
                colorId = R.color.log_error;
                break;
            case ASSERT:
                colorId = R.color.log_assert;
                break;
            default:
                throw new IllegalArgumentException("invalid log level");
        }
        holder.mLog.setTextColor(mContext.getResources().getColor(colorId));
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mLog;

        public ViewHolder(View view) {
            super(view);
            mLog = (TextView) view.findViewById(R.id.item_log);
        }
    }
}
