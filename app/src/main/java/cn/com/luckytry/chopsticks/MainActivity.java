package cn.com.luckytry.chopsticks;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import cn.com.luckytry.chopsticks.ui.fragment.DiscoveryFragment;
import cn.com.luckytry.chopsticks.ui.fragment.TakeOutFragment;

public class MainActivity extends AppCompatActivity {

    private RadioButton btn1,btn2,btn3,btn4;

    private RadioGroup bottomBar;
    private TakeOutFragment fragment1;
    private DiscoveryFragment fragment2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 去除标题  必须在setContentView()方法之前调用
        setContentView(R.layout.activity_main);
        initView();
        fragment1 = new TakeOutFragment();
        fragment2 = new DiscoveryFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.add(fragment1,"1");
//        transaction.add(fragment2,"2");
//        transaction.show(fragment1);
        transaction.replace(R.id.container,fragment2);
        transaction.commit();
    }

    private void initView() {
        btn1 = (RadioButton) findViewById(R.id.rg_takeout);
        btn2 = (RadioButton) findViewById(R.id.rg_discover);
        btn3 = (RadioButton) findViewById(R.id.rg_order);
        btn4 = (RadioButton) findViewById(R.id.rg_mine);
        bottomBar = (RadioGroup) findViewById(R.id.ll_rg);
        bottomBar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                btn1.setSelected(false);
                btn2.setSelected(false);
                btn3.setSelected(false);
                btn4.setSelected(false);

                switch (checkedId){
                    case R.id.rg_takeout:
                        btn1.setSelected(true);

                        break;
                    case R.id.rg_discover:
                        btn2.setSelected(true);

                        break;
                    case R.id.rg_order:
                        btn3.setSelected(true);
                        break;
                    case R.id.rg_mine:
                        btn4.setSelected(true);
                        break;
                }
            }
        });
        btn1.setSelected(true);
    }
}
