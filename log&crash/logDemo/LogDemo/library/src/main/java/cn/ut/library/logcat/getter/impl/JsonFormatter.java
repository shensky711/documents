package cn.ut.library.logcat.getter.impl;

import org.json.JSONArray;
import org.json.JSONObject;

import cn.ut.library.logcat.getter.StringFormatter;
import cn.ut.library.utils.StringUtils;


/**
 * Created by chenhang on 2016/6/14.
 */
public class JsonFormatter implements StringFormatter {

    @Override
    public String format(String string) throws Exception {

        String result;
        if (StringUtils.isEmpty(string)) {
            result = "Empty/Null json content";
        } else if (string.startsWith("{")) {
            JSONObject jsonObject = new JSONObject(string);
            result = jsonObject.toString(4);
        } else if (string.startsWith("[")) {
            JSONArray jsonArray = new JSONArray(string);
            result = jsonArray.toString(4);
        } else {
            result = "Invalid json: \n" + string;
        }

        return result;
    }
}
