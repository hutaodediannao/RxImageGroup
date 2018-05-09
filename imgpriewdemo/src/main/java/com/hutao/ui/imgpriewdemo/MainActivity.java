package com.hutao.ui.imgpriewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.hutao.ui.imagetabgroup.RxImageGroup;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    private RxImageGroup rxImageGroup;
    private LinkedList<String> imgSrcList = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgSrcList.add("http://image.uczzd.cn/1426464133004721941.jpg?id=0&from=export&height=140&width=270");
        imgSrcList.add("http://image.uczzd.cn/2202153990775164727.jpg?id=0&from=export&height=140&width=270");
        imgSrcList.add("http://image.uczzd.cn/18405101937006007183.jpg?id=0&from=export&height=140&width=270");
        imgSrcList.add("http://image.uczzd.cn/11816533162455942032.jpg?id=0&from=export&height=140&width=270");
        imgSrcList.add("http://image.uczzd.cn/14148914851966540504.jpg?id=0&from=export&height=140&width=270");
        imgSrcList.add("http://image.uczzd.cn/14148914851966540504.jpg?id=0&from=export&height=140&width=270");

        rxImageGroup = findViewById(R.id.rxImageGroup);
        rxImageGroup.updateData(imgSrcList);

        rxImageGroup.setmRxImageGroupListener(new RxImageGroup.RxImageGroupListener() {
            @Override
            public void loadImage(ImageView imageView, Object objectPath) {

                Glide.with(MainActivity.this)
                        .load(objectPath)
                        .error(R.mipmap.ic_launcher)
                        .placeholder(R.mipmap.ic_launcher)
                        .into(imageView);
            }

            @Override
            public void clickItem(int position) {
                Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void deleteItem(int position) {
                rxImageGroup.deletePosition(position);
            }
        });



    }
}
