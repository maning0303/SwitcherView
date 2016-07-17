package com.maning.switcherview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.maning.library.SwitcherView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "------";
    private SwitcherView switcherView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        switcherView = (SwitcherView) findViewById(R.id.switcherView);

        ArrayList<String> strs = new ArrayList<>();
        strs.add("哎呦，不错哦");
        strs.add("你知道我是谁吗你知道我是谁吗你知道我是谁吗");
        strs.add("哈哈哈");
        strs.add("1111111111111");
        switcherView.setResource(strs);
        switcherView.startRolling();

        switcherView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, switcherView.getCurrentItem(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(MainActivity.this, switcherView.getCurrentIndex(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onDestroy() {
        switcherView.destroySwitcher();
        super.onDestroy();
    }
}
