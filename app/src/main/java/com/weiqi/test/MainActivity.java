package com.weiqi.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.weiqi.slog.SLog;
import com.weiqi.slog.comparator.LogFileComparator;
import com.weiqi.test.util.SDUtils;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private int wwqCount = 0;

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.tv_count);

        ((Button) findViewById(R.id.btn_normal)).setOnClickListener(this);
        ((Button) findViewById(R.id.btn_json)).setOnClickListener(this);
        ((Button) findViewById(R.id.btn_large)).setOnClickListener(this);
        ((Button) findViewById(R.id.btn_im)).setOnClickListener(this);
        ((Button) findViewById(R.id.btn_crash)).setOnClickListener(this);
        ((Button) findViewById(R.id.btn_performance)).setOnClickListener(this);
        ((Button) findViewById(R.id.btn_network)).setOnClickListener(this);
        ((Button) findViewById(R.id.btn_comparator)).setOnClickListener(this);
        ((Button) findViewById(R.id.btn_exit)).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_normal) {
            SLog.d(TAG, "测试");
        } else if (v.getId() == R.id.btn_json) {
            SLog.d(TAG, "{\"key\":\"log\",\"name\":\"SLog\"}");
            SLog.d(TAG, "[{\"key\":\"log\",\"name\":\"SLog\"},{\"key2\":\"log\"," +
                    "\"name2\":\"SLog\"}]");
        } else if (v.getId() == R.id.btn_large) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < 1000; i++) {
                stringBuilder.append(String.format("A_B_C_d_E_F_G_H_I_J_[%s]", i));
            }
            wwqCount++;
            mTextView.setText(String.format("count:%s", wwqCount));
            SLog.wtf(TAG, stringBuilder.toString());
        } else if (v.getId() == R.id.btn_im) {
            /**
             * 异步方式将 im相关日志单独写入特定文件中
             * 方便查阅
             */
            SLog.wtfParams(TAG, "socket", "im", false);
        } else if (v.getId() == R.id.btn_crash) {
            /**
             * 同步方式将 crash日志单独写入特定文件中
             * 使用同步方式保证进程被杀之前保存好信息
             * 正常场景推荐 异步方式
             */
            SLog.wtfParams(TAG, "catch a crash info", "carsh", true);
        } else if (v.getId() == R.id.btn_performance) {
            wwqCount += 30;
            mTextView.setText(String.format("count:%s", wwqCount));
            for (int i = 0; i < 10; i++) {
                new Thread(new MyRunnable(i)).start();
            }
        } else if (v.getId() == R.id.btn_network) {
            String requestUrl = "www.eastmoney.com";
            String requestParams = "POST\n{ctoken=xx,utoken=yy,version=00001}\nresultCode=200";
            String result = "{\"code\":0,\"message\":\"success\"}";
            SLog.netWork(TAG, requestUrl, requestParams, result);
        } else if (v.getId() == R.id.btn_exit) {
            //应用退出或不再使用Slog时调用 主动释放内存
            SLog.onDestroy();
        } else if (v.getId() == R.id.btn_comparator) {
            testFileComparator();
        }
    }

    private void testFileComparator() {
        File dir = new File(SDUtils.getLogPath(this));
        List<File> listFiles = Arrays.asList(dir.listFiles());
        Collections.sort(listFiles, new LogFileComparator());
        for (int i = 0; i < listFiles.size(); i++) {
            Log.d("test", "file name:" + listFiles.get(i).getName());
        }
    }

    private class MyRunnable implements Runnable {

        private int mRid;

        public MyRunnable(int rid) {
            this.mRid = rid;
        }

        @Override
        public void run() {
            for (int i = 0; i < 3; i++) {
                SLog.wtf(TAG, String.format("线程 RID:%s 测试(%s)", mRid, i));
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
