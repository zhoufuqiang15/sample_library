package com.abount.cg.commonlibrary.views.activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.abount.cg.commonlibrary.R;
import com.abount.cg.commonlibrary.utils.ToastUtil;


/**
 * Created by mo_yu on 2018/3/23.奔溃日志详情
 */

public class CrashInfoActivity extends BaseNoBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash_info___cm);
        final String info = getIntent().getStringExtra("info");
        findViewById(R.id.copy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(
                        CLIPBOARD_SERVICE);
                clipboardManager.setPrimaryClip(ClipData.newPlainText(getString(R.string.app_name),
                        info));
                ToastUtil.showToast(CrashInfoActivity.this, "已成功复制回帖内容到粘贴板", 0);
            }
        });
        TextView textView = (TextView) findViewById(R.id.tv_info);
        textView.setText(info);
    }
}
