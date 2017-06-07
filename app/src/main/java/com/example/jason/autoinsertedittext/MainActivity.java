package com.example.jason.autoinsertedittext;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText editInput;
    private static char SEPARACHAR = ',';
    private static int SEPARANUM = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        initView();
    }

    private void initView() {
        editInput = ((EditText) findViewById(R.id.edit_input));
        editInput.addTextChangedListener(new TextWatcher() {
            public int location ;
            public boolean isChanged;
            private int beforLength;
            private StringBuffer sBuffer = new StringBuffer();
            private int separaNum = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //输入文本框改变之前，把保存的数据清理掉
                beforLength = s.length();
                if (sBuffer.length() > 0) {
                    sBuffer.delete(0, sBuffer.length());
                }
                //记录之前的分割符的数量
                separaNum = beforLength - s.toString().trim().replaceAll(String.valueOf(SEPARACHAR), "").length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sBuffer.append(s.toString());
                if(isChanged){
                    isChanged = false;
                    return ;
                }
                isChanged = true;
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(isChanged){
                    location = editInput.getSelectionEnd();
                    int index = 0;
                    while (index < sBuffer.length()) {
                        if (sBuffer.charAt(index) == SEPARACHAR) {
                            sBuffer.deleteCharAt(index);
                        } else {
                            index++;
                        }
                    }

                    index = 0;
                    int sepaNmu = 0;
                    while (index < sBuffer.length()) {
                        if ((index + 1) % (SEPARANUM + 1) == 0) {
                            sBuffer.insert(index, SEPARACHAR);
                            sepaNmu++;
                        }
                        index++;
                    }
                    if (sepaNmu > separaNum) {
                        location += (sepaNmu - separaNum);
                    }

                    String str = sBuffer.toString();
                    if (location > str.length()) {
                        location = str.length();
                    } else if (location < 0) {
                        location = 0;
                    }

                    editInput.setText(str);


                    Editable etable = editInput.getText();
                    if (location > 0 && etable.length() >= location){
                        Selection.setSelection(etable, location);
                    }
                    isChanged = false;
                }
                }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
