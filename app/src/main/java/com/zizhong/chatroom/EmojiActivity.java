package com.zizhong.chatroom;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zizhong.chatroom.base.IBaseActivity;

import java.util.ArrayList;
import java.util.List;

public class EmojiActivity extends IBaseActivity {
    private ImageView iv_back;
    private GridView gridView;
    private List<String> emojiStrs;
    private EmojiAdapter emojiAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_emoji;
    }

    @Override
    protected void initView() {
        iv_back = findViewById(R.id.iv_back);
        gridView = findViewById(R.id.emoji_gridview);
    }

    @Override
    protected void initData() {
        emojiStrs = new ArrayList<>();
        emojiStrs.add("( ≧Д≦)");
        emojiStrs.add("(;≧皿≦)");
        emojiStrs.add("<(｀^´)>");
        emojiStrs.add("( >д<)");
        emojiStrs.add("(¬_¬)");
        emojiStrs.add("︿(￣︶￣)︿");
        emojiStrs.add("w(ﾟДﾟ)w");
        emojiStrs.add("(*￣rǒ￣)");
        emojiStrs.add("(ノへ￣、)");
        emojiStrs.add("(￣_,￣ )");
        emojiStrs.add("ヽ(✿ﾟ▽ﾟ)ノ");
        emojiStrs.add("(๑•̀ㅂ•́)و✧");
        emojiStrs.add("(*￣rǒ￣)");
        emojiStrs.add("♪(^∇^*)");
        emojiStrs.add("╰(*°▽°*)╯");
        emojiStrs.add("（○｀ 3′○）");
        emojiStrs.add("(#`O′)");
        emojiStrs.add("(°ー°〃)");
        emojiStrs.add("（＝。＝）");
        emojiStrs.add("(ーー゛)");
        emojiStrs.add("(ー`´ー)");
        emojiStrs.add("(⊙﹏⊙)");
        emojiStrs.add("(。﹏。*)");
        emojiStrs.add("(✿◡‿◡)");
        emojiStrs.add("(*/ω＼*)");
        emojiAdapter = new EmojiAdapter();
        gridView.setAdapter(emojiAdapter);
    }

    @Override
    protected void initListener() {
        iv_back.setOnClickListener(this);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String str = emojiStrs.get(i);
                ClipboardManager clip = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                clip.setPrimaryClip(ClipData.newPlainText("url", str));
                Toast.makeText(EmojiActivity.this, "Copy successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void initOther(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    class EmojiAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return emojiStrs.size();
        }

        @Override
        public Object getItem(int i) {
            return emojiStrs.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = (ViewGroup) LayoutInflater.from(EmojiActivity.this).inflate(R.layout.item_emoji_content, null);
            TextView emojiTv = view.findViewById(R.id.emoji_str);
            emojiTv.setText(emojiStrs.get(i));
            return view;
        }
    }
}