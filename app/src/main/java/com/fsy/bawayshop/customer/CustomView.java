package com.fsy.bawayshop.customer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fsy.bawayshop.R;

/**
 * @author : FangShiKang
 * @date : 2019/01/07.
 * email : fangshikang@outlook.com
 * desc :
 */
public class CustomView extends LinearLayout {
    private TextView revserse;    //减号
    private TextView add;         //加号
    private EditText editText;
    private int number = 1 ;

    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //设置布局
        View view = LayoutInflater.from(context).inflate(R.layout.custom_view, null, false);
        revserse =  (TextView)view.findViewById(R.id.sub);
        add = (TextView)view.findViewById(R.id.add);
        editText = (EditText) view.findViewById(R.id.number);

        //点击减号时的情况变化
        revserse.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String content =  editText.getText().toString().trim() ;
                    //商品数量大于0时才可以变化，等于0的情况不能出现
                    int count = Integer.valueOf(content);
                    if (count > 1){
                        number = count - 1;
                        editText.setText(number+"");

                        //当减号点击后，数值变化
                        if (listener != null){
                            listener.click(number);
                        }
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        });

        //点击加号时的情况变化
        add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String content =  editText.getText().toString().trim() ;
                    int count = Integer.valueOf(content) + 1;
                    number = count;
                    editText.setText(count+"");
                    //当加号点击后，数值变化
                    if (listener != null){
                        listener.click(count);
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        });
        addView(view);
    }

    //设置方法获取EditText输入框的值
    public void  setEditText(int count){
        editText.setText(count+"");
    }
    //声明number变量
    public int getCurrentCount(){
        return number;
    }

    //定义回调接口,用于判断是否点击情况
    public ClickListener listener;
    public void setListener(ClickListener listener){
        this.listener = listener;
    }
    public interface ClickListener {
        public void click(int count);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
