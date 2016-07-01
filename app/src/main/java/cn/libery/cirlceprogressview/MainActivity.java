package cn.libery.cirlceprogressview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import cn.libery.circleprogressview.CircleProgressView;

public class MainActivity extends AppCompatActivity {

    private CircleProgressView mCircleProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCircleProgressView = (CircleProgressView) findViewById(R.id.circleView);
        mCircleProgressView.setMaxProgress(3000);
        mCircleProgressView.setFinishListener(new CircleProgressView.FinishListener() {
            @Override
            public void finish() {
                Toast.makeText(getApplicationContext(), "finish:" + mCircleProgressView.getProgress(), Toast
                        .LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                mCircleProgressView.reset();
            }
        });
    }
}
