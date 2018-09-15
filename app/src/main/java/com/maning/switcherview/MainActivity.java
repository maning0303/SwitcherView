package com.maning.switcherview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        strs.add("双十一购物节");
        strs.add("双十二购物节");
        strs.add("京东购物节");
        strs.add("买买买买买买买买买买买买买买买买买买买买买买买买买买买买买买买买买买买买");
        switcherView.setResource(strs);
        switcherView.startRolling();

        switcherView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, switcherView.getCurrentItem(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void btnStart(View view){
        switcherView.startRolling();
    }
    public void btnStop(View view){
        switcherView.stopRolling();
    }
    public void btnUpdate(View view){
        ArrayList<String> strs = new ArrayList<>();
        strs.add("双十二购物节1");
        strs.add("双十二购物节2");
        strs.add("双十二购物节3");
        strs.add("双十二购物节4");
        strs.add("双十二购物节5");
        switcherView.setResource(strs);
    }

    public void btnNext(View view){
        switcherView.rollingToNext();
    }

    public void btnAnim01(View view){
        switcherView.setAnimationTop2Bottom();
    }

    public void btnAnim02(View view){
        switcherView.setAnimationBottom2Top();
    }

    public void btnAnim03(View view){
        switcherView.setAnimationLeft2Right();
    }

    public void btnAnim04(View view){
        switcherView.setAnimationRight2Left();
    }

    public void btnCustomAnmi(View view){
        switcherView.setInAnimation(R.anim.anim_custom_in);
        switcherView.setOutAnimation(R.anim.anim_custom_out);
    }

    @Override
    protected void onResume() {
        super.onResume();
        switcherView.startRolling();
    }

    @Override
    protected void onPause() {
        super.onPause();
        switcherView.stopRolling();
    }

    @Override
    protected void onDestroy() {
        switcherView.destroySwitcher();
        super.onDestroy();
    }
}
