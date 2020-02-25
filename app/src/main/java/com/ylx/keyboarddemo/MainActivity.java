package com.ylx.keyboarddemo;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ylx.keyboarddemo.keyboard.CustomNumKeyboardView;
import com.ylx.keyboarddemo.keyboard.KeyboardNumUtil;

public class MainActivity extends AppCompatActivity implements KeyboardNumUtil.NumKeyboardListener {

    private EditText m_edit_txt;
    private CustomNumKeyboardView keyboardView;

    private KeyboardNumUtil mKeyboardNumUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m_edit_txt = findViewById(R.id.m_edit_txt);
        keyboardView = findViewById(R.id.keyboardView);

        mKeyboardNumUtil = new KeyboardNumUtil(this, keyboardView);
        mKeyboardNumUtil.attachEditText(m_edit_txt);
        mKeyboardNumUtil.setNumKeyboardListener(this);
        mKeyboardNumUtil.showNumKeyboard();
        m_edit_txt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                hideSystemKeyBoard();
            }
        });
    }

    public void hideSystemKeyBoard() {
        if (mKeyboardNumUtil != null) {
            mKeyboardNumUtil.hideSystemKeyBoard();
        }
    }

    @Override
    public void onClose() {
        Toast.makeText(this, "关闭软盘", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConfirm() {
        Toast.makeText(this, "点击确定", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNumberChanged(String paramString) {

    }
}
