package com.example.haoshul.richtextdemo;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.URLSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {
    String TAG = "main";
    @InjectView(R.id.textView)
    TextView textView;
    @InjectView(R.id.textView2)
    TextView textView2;
    @InjectView(R.id.textView3)
    TextView textView3;
    @InjectView(R.id.textView4)
    TextView textView4;

    private static HashMap<String,Integer> emojimap = new HashMap<>();
    static {
        emojimap.put("大兵",R.drawable.ic_launcher);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
//        Log.d(TAG, "onCreate() returned: " + emojimap.get("大兵"));
//        textView.setText(showTextwithImage("我是文本[大兵]",R.drawable.ic_launcher));
        textView.setText(showTextwithImage("我是文本[大兵]"));

        textView2.setText(showTextwithColor("haoshul,lai等2人觉得很赞", Color.BLUE));

        textView3.setText(showTextwithSuperLink("详情请点击<a href ='http://www.baidu.com'>百度一下</a>"));
        textView3.setMovementMethod(LinkMovementMethod.getInstance());//设置可以点击超链接

        textView4.setText(showTextwithMyText("haoshul,lai等2人觉得很赞"));
        textView4.setMovementMethod(LinkMovementMethod.getInstance());

    }

//    private SpannableString showTextwithImage(String text,int imageRes){
//        SpannableString ss = new SpannableString(text);
//        Drawable drawable = getResources().getDrawable(imageRes);
//        //设置drawable的边界
//        drawable.setBounds(0,0,50,50);
//        ImageSpan imageSpan = new ImageSpan(drawable);//可以在字符串中显示图片了
//        int start = text.indexOf("[");
//        int end = text.indexOf("]")+1;
//        Log.d(TAG, "showTextwithImage() returned: " + text.substring(start,end));
//        ss.setSpan(imageSpan,start,end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//        return ss;
//    }

    private SpannableString showTextwithImage(String text){
        SpannableString ss = new SpannableString(text);
        int start = text.indexOf("[");
        int end = text.indexOf("]")+1;
        String key = text.substring(start+1,end-1);
        int imageRes = emojimap.get(key);
        Drawable drawable = getResources().getDrawable(imageRes);
        //设置drawable的边界
        drawable.setBounds(0,0,50,50);
        ImageSpan imageSpan = new ImageSpan(drawable);//可以在字符串中显示图片了
        ss.setSpan(imageSpan,start,end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return ss;
    }

    private CharSequence showTextwithColor(String text,int color){
        SpannableString ss = new SpannableString(text);
        ForegroundColorSpan span = new ForegroundColorSpan(color);//前景色
        ForegroundColorSpan span2 = new ForegroundColorSpan(color);
        BackgroundColorSpan span3 = new BackgroundColorSpan(Color.GREEN);//背景色
        int end = text.indexOf("等");

        ss.setSpan(span,0,end,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        ss.setSpan(span2,end+1,end+3,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        ss.setSpan(span3,text.length()-1,text.length(),Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return ss;
    }

    private CharSequence showTextwithSuperLink(String text){
        Spanned spanned = Html.fromHtml(text);
        return spanned;
    }

    private CharSequence showTextwithMyText(String text){
        SpannableString ss = new SpannableString(text);
        MyUrlSpan span = new MyUrlSpan(text.substring(0,text.indexOf(",")));
        ss.setSpan(span,0,text.indexOf(","),Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return ss;
    }

    private class MyUrlSpan extends URLSpan{
        public MyUrlSpan(String url) {
            super(url);
        }

        @Override
        public void onClick(View widget) {
            super.onClick(widget);
            Toast.makeText(MainActivity.this, getURL(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(Color.BLUE);
            ds.setUnderlineText(false);
        }
    }
}
