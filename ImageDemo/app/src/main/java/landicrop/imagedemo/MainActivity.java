package landicrop.imagedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.ivShow)
    ImageView ivShow;
    SunView svShow;
    @BindView(R.id.layoutMain)
    ConstraintLayout layoutMain;
    SurView surView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //imageview
//        ivShow.setImageResource(R.mipmap.lr2);

        //自定义view
        ConstraintLayout.LayoutParams layoutParams =
                new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        svShow = new SunView(this);
        svShow.setLayoutParams(layoutParams);
        svShow.setBitmap(R.mipmap.lr2);
        layoutMain.addView(svShow);


        //surfaceview
//        surView = new SurView(this);
//        surView.setLayoutParams(layoutParams);
//        surView.setBitmap(R.mipmap.lr2);
//        layoutMain.addView(surView);
//        Thread thread = new Thread(surView);
//        thread.start();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick(R.id.layoutMain)
    void onClick(){
        Intent intent = new Intent();
        intent.setClass(this,CameraActivity.class);
        startActivity(intent);
    }

}
