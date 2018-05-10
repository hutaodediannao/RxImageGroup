# RxImageGroup

一个放朋友圈上传图片的控件，支持图片的删除，添加，点击事件，加载采用回调的方式由用户自己使用自己喜欢的加载框架
![](https://github.com/hutaodediannao/RxImageGroup/blob/master/Screenshot_2018-05-10-01-16-05-145_com.hutao.ui.i.png)
![](https://github.com/hutaodediannao/RxImageGroup/blob/master/Screenshot_2018-05-10-01-16-26-249_com.hutao.ui.i.png)

使用方式：
compile 'com.hutao:RxImageGroup:1.0.3'

1.在xml布局中添加一下控件：
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    xmlns:app="http://schemas.android.com/apk/res-auto">

    <com.hutao.ui.imagetabgroup.RxImageGroup
        android:id="@+id/rxImageGroup"
        android:layout_width="match_parent"
        android:layout_height="200dp"
        app:deleteIconSrc="@mipmap/delete"
        app:isAbleEdit="true"
        app:row="3"/>

</FrameLayout>

2.在activity代码中：

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
        rxImageGroup.setAbleEdit(true)
                .updateData(imgSrcList)
                .setmRxImageGroupListener(new RxImageGroup.RxImageGroupListener() {
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


