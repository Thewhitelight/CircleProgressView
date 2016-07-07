package cn.libery.cirlceprogressview;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import cn.libery.circleprogressview.CircleProgressView;

public class MainActivity extends AppCompatActivity {

    private CircleProgressView mCircleStroke;
    private CircleProgressView mCircleFill;
    private CircleProgressView mCircleStrokeASC;
    private CircleProgressView mCircleFillASC;
    float num = 2.4f;
    float num2 = 1.8f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCircleStroke = (CircleProgressView) findViewById(R.id.circleView_stroke);
        mCircleFill = (CircleProgressView) findViewById(R.id.circleView_fill);
        mCircleStrokeASC = (CircleProgressView) findViewById(R.id.circleView_stroke_asc);
        mCircleFillASC = (CircleProgressView) findViewById(R.id.circleView_fill_asc);
        mCircleStroke.setMaxProgress(3000);
        mCircleStroke.setFinishListener(new CircleProgressView.FinishListener() {
            @Override
            public void finish() {
                Toast.makeText(getApplicationContext(), "finish:" + mCircleStroke.getProgress(), Toast
                        .LENGTH_SHORT).show();
            }
        });

        mCircleFill.setPaintStyle(Paint.Style.FILL);
        mCircleFill.setMaxProgress(5000);


        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                mCircleStroke.reset();
                mCircleFill.reset();
            }
        });

        mCircleStrokeASC.setProgressMode(CircleProgressView.ASC);
        mCircleStrokeASC.setProgress(num);
        mCircleStrokeASC.setFinishListener(new CircleProgressView.FinishListener() {
            @Override
            public void finish() {
                Toast.makeText(getApplicationContext(), "mCircleStrokeASC", Toast
                        .LENGTH_SHORT).show();
            }
        });
        mCircleStrokeASC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                mCircleStrokeASC.setProgress(num += num);
            }
        });

        mCircleFillASC.setProgressMode(CircleProgressView.ASC);
        mCircleFillASC.setProgress(num2);
        mCircleFillASC.setPaintStyle(Paint.Style.FILL);
        mCircleFillASC.setFinishListener(new CircleProgressView.FinishListener() {
            @Override
            public void finish() {
                Toast.makeText(getApplicationContext(), "mCircleFillASC", Toast.LENGTH_SHORT).show();
            }
        });
        mCircleFillASC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                mCircleFillASC.setProgress(num2 += num2);
            }
        });
    }
}
