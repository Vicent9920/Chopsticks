package cn.com.luckytry.chopsticks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import cn.com.luckytry.chopsticks.background.AnalysisServices;

/**
 * 欢迎页
 */
public class WelcomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 去除标题  必须在setContentView()方法之前调用
        setContentView(R.layout.activity_welcome);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // 设置全屏
        startService(new Intent(this, AnalysisServices.class));
//       new Thread(){
//           @Override
//           public void run() {
//               super.run();
//               List<ShopBean> data = DataSupport.findAll(ShopBean.class);
//               if(data.size() == 0){
//                   LUtil.e("开始解析数据");
//                   JsoupUtil.getData();
//               }else{
//                   LUtil.e("数据不用解析");
//                   Const.isReady = true;
//               }
//           }
//       }.start();
        new Thread(){
            @Override
            public void run() {
                super.run();

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //TODO 待申请权限，待权限回调成功以后启动主界面
                startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
//                finish();
            }


        }.start();
    }


}
