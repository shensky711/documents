/*****************************************************************************
 * Logcat.java
 * ****************************************************************************
 * Copyright © 2011-2015 VLC authors and VideoLAN
 * <p/>
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston MA 02110-1301, USA.
 *****************************************************************************/

package cn.ut.library.monitor;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import cn.ut.library.bean.LogInfo;
import cn.ut.library.utils.CloseUtils;

public class Logcat implements Runnable {

    private Callback mCallback = null;
    private Thread mThread = null;
    private Process mProcess = null;
    private boolean mRun = false;

    public interface Callback {
        void onLog(LogInfo log);
    }

    public Logcat() {
    }

    @Override
    public void run() {
        final String[] args = {"logcat", "-v", "time"};
        InputStreamReader input = null;
        BufferedReader br = null;
        try {
            synchronized (this) {
                if (!mRun) {
                    return;
                }
                mProcess = Runtime.getRuntime().exec(args);
                input = new InputStreamReader(
                        mProcess.getInputStream());
            }

            br = new BufferedReader(input);
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    mCallback.onLog(LogInfo.fromString(line));
                } catch (Exception ignore) {

                }
            }
        } catch (IOException e) {
        } finally {
            CloseUtils.close(input);
            CloseUtils.close(br);
        }
    }

    /**
     * Start a thread that will send logcat via a callback
     *
     * @param callback
     */
    public synchronized void start(Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("callback should not be null");
        }

        if (mThread != null || mProcess != null) {
            throw new IllegalStateException("logcat is already started");
        }

        mCallback = callback;
        mRun = true;
        mThread = new Thread(this);
        mThread.start();
    }

    /**
     * Stop the thread previously started
     */
    public synchronized void stop() {
        mRun = false;
        if (mProcess != null) {
            mProcess.destroy();
            mProcess = null;
        }
        if (mThread != null) {
            try {
                mThread.join();
            } catch (InterruptedException e) {
            }
            mThread = null;
        }
        mCallback = null;
    }
}
