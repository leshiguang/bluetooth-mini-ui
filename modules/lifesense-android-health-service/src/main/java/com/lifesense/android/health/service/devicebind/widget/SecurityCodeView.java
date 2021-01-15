package com.lifesense.android.health.service.devicebind.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lifesense.android.health.service.R;


public class SecurityCodeView extends RelativeLayout {
    private EditText editText;
    private TextView[] TextViews;
    private StringBuffer stringBuffer = new StringBuffer();
    private int count = 4;
    private int defaultCount = 4;
    private String inputContent;
    private View item_view_5,item_view_6;
    private TextView tv_item_code_iv5,tv_item_code_iv6;

    public SecurityCodeView(Context context) {
        this(context, null);
    }

    public SecurityCodeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SecurityCodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TextViews = new TextView[6];
        View.inflate(context, R.layout.scale_view_security_code, this);

        item_view_5=  findViewById(R.id.item_view_5);
        item_view_6=  findViewById(R.id.item_view_6);
        tv_item_code_iv5 = findViewById(R.id.item_code_iv5);
        tv_item_code_iv6= findViewById(R.id.item_code_iv6);
        
        
        editText = findViewById(R.id.item_edittext);
        TextViews[0] = findViewById(R.id.item_code_iv1);
        TextViews[1] = findViewById(R.id.item_code_iv2);
        TextViews[2] = findViewById(R.id.item_code_iv3);
        TextViews[3] = findViewById(R.id.item_code_iv4);
        TextViews[4] = findViewById(R.id.item_code_iv5);
        TextViews[5] = findViewById(R.id.item_code_iv6);






        
        editText.setCursorVisible(false);//将光标隐藏
        setListener();
    }

    private void setListener() {
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //重点   如果字符不为""时才进行操作
                if (!editable.toString().equals("")) {
                    if (stringBuffer.length() > defaultCount-1) {
                        //当文本长度大于3位时edittext置空
                        editText.setText("");
                        return;
                    } else {
                        //将文字添加到StringBuffer中
                        stringBuffer.append(editable);
                        editText.setText("");//添加后将EditText置空  造成没有文字输入的错局
                        //  Log.e("TAG", "afterTextChanged: stringBuffer is " + stringBuffer);
                        count = stringBuffer.length();//记录stringbuffer的长度
                        inputContent = stringBuffer.toString();
                        if (stringBuffer.length() == defaultCount) {
                            //文字长度位4  则调用完成输入的监听
                            if (inputCompleteListener != null) {
                                inputCompleteListener.inputComplete();
                            }
                        }
                    }

                    for (int i = 0; i < stringBuffer.length(); i++) {
                        if(TextViews.length > i) {  //ArrayIndexOutOfBoundsException
                            TextViews[i].setText(String.valueOf(inputContent.charAt(i)));
                            TextViews[i].setBackgroundResource(R.mipmap.scale_bg_verify_press);
                        }
                    }

                }
            }
        });

        editText.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL
                        && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (onKeyDelete()) {
                        return true;
                    }
                    return true;
                }
                return false;
            }
        });
    }


    public boolean onKeyDelete() {
        if (count == 0) {
            count = defaultCount;
            return true;
        }
        if (stringBuffer.length() > 0) {
            //删除相应位置的字符
            stringBuffer.delete((count - 1), count);
            count--;
            //   Log.e(TAG, "afterTextChanged: stringBuffer is " + stringBuffer);
            inputContent = stringBuffer.toString();
            TextViews[stringBuffer.length()].setText("");
            TextViews[stringBuffer.length()].setBackgroundResource(R.mipmap.scale_bg_verify);
            if (inputCompleteListener != null) {
                inputCompleteListener.deleteContent(true);//有删除就通知manger
            }

        }
        return false;
    }

    /**
     * 清空输入内容
     */
    public void clearEditText() {
        stringBuffer.delete(0, stringBuffer.length());
        inputContent = stringBuffer.toString();
        for (int i = 0; i < TextViews.length; i++) {
            TextViews[i].setText("");
            TextViews[i].setBackgroundResource(R.mipmap.scale_bg_verify);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    private InputCompleteListener inputCompleteListener;

    public void setInputCompleteListener(InputCompleteListener inputCompleteListener) {
        this.inputCompleteListener = inputCompleteListener;
    }

    public interface InputCompleteListener {
        void inputComplete();

        void deleteContent(boolean isDelete);
    }

    /**
     * 获取输入文本
     *
     * @return
     */
    public String getEditContent() {
        return inputContent;
    }

    /**
     * 限制输入长度
     *
     * @return
     */
    public void setDefaultCount(int customCount) {

        if (customCount>0 && customCount>4)
        {
            this.defaultCount=customCount;
        }
        else
        {
            this.defaultCount = 4;
        }

        if (defaultCount>4)
        {
            if(defaultCount==5)
            {
                item_view_6.setVisibility(View.GONE);
                tv_item_code_iv6.setVisibility(View.GONE);
            }
        }
        else
        {
            item_view_5.setVisibility(View.GONE);
            tv_item_code_iv5.setVisibility(View.GONE);
            item_view_6.setVisibility(View.GONE);
            tv_item_code_iv6.setVisibility(View.GONE);
        }

    }

    //弹出软键盘
    public void showSoftInput(){
        editText.requestFocus();
        ((InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(editText, 0);

    }

}