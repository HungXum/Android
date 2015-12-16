package tracetest.hungxum.com.imitatewechat;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FragmentAdapter fragmentAdapter;
    private List<Fragment> fragmentList;
    private WeChatFragment weChatFragment;
    private ContactFragment contactFragment;
    private DiscoveryFragment discoveryFragment;
    private MeFragment meFragment;

    private TextView text_wechat,text_contact,text_discovery,text_me;
    private ViewPager viewPager;

    private ImageView bottomHolo;

    //当前viewPager为第几页
    private int currentIndex;
    //屏幕的宽度
    private int scrrenWidth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        initBottomHoloWidth();
        setTextColor();
    }



    public void init(){

        text_wechat = (TextView) findViewById(R.id.text_wechat);
        text_contact = (TextView) findViewById(R.id.text_contact);
        text_discovery = (TextView) findViewById(R.id.text_discovery);
        text_me = (TextView) findViewById(R.id.text_me);

        bottomHolo = (ImageView) findViewById(R.id.bottome_holo);

        viewPager = (ViewPager) findViewById(R.id.id_viewpager);

        fragmentList = new ArrayList<Fragment>();

        weChatFragment = new WeChatFragment();
        contactFragment = new ContactFragment();
        discoveryFragment = new DiscoveryFragment();
        meFragment = new MeFragment();

        fragmentList.add(weChatFragment);
        fragmentList.add(contactFragment);
        fragmentList.add(discoveryFragment);
        fragmentList.add(meFragment);

        fragmentAdapter = new FragmentAdapter(this.getSupportFragmentManager(),fragmentList);
        viewPager.setAdapter(fragmentAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            /*
            * position：当前页面
            * positionOffset: 滑动页面偏移的百分比
            * positionOffsetPixels: 当前页面偏移的像素位置
            * */
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) bottomHolo.getLayoutParams();
                Log.d("scroll","currentIndex=" + currentIndex + "position = " + position + ",offset = " + positionOffset + ",offsetPixels = " + positionOffsetPixels);
                /**
                 * * 利用currentIndex(当前所在页面)和position(下一个页面)以及offset来
                 * * 设置bottomHolo的左边距 滑动场景：
                 * * 记4个页面,
                 * * 从左到右分别为0,1,2,3
                 * * 0->1; 1->2; 2->3; 3->2,2->1,1->0
                 * * offset为从左到右，而-(1 - offset) 为从右到左
                 * */

                if (currentIndex == 0 && position == 0){
                    lp.leftMargin = (int) (positionOffset * (scrrenWidth * 1.0 / 4) + currentIndex * (scrrenWidth / 4));
                }else if (currentIndex == 1 && position == 0){
                    lp.leftMargin = (int) (-(1 - positionOffset) * (scrrenWidth * 1.0 / 4) + currentIndex * (scrrenWidth / 4));
                }else if (currentIndex == 1 && position == 1){
                    lp.leftMargin = (int) (positionOffset * (scrrenWidth * 1.0 / 4) + currentIndex * (scrrenWidth / 4));
                }else if (currentIndex == 2 && position == 1){
                    lp.leftMargin = (int) (-(1 - positionOffset) * (scrrenWidth * 1.0 / 4) + currentIndex * (scrrenWidth / 4));
                }else if (currentIndex == 2 && position == 2){
                    lp.leftMargin = (int) (positionOffset * (scrrenWidth * 1.0 / 4) + currentIndex * (scrrenWidth / 4));
                }else if (currentIndex == 3 && position == 2){
                    lp.leftMargin = (int) (-(1 - positionOffset) * (scrrenWidth * 1.0 / 4) + currentIndex * (scrrenWidth / 4));
                }
                bottomHolo.setLayoutParams(lp);
            }

            @Override
            //页面已经被选择了，既显示在用户面前时调用的函数
            public void onPageSelected(int position) {
                setTextColor();
                switch (position){
                    case 0:
                        text_wechat.setTextColor(Color.GREEN);
                        break;
                    case 1:
                        text_contact.setTextColor(Color.GREEN);
                        break;
                    case 2:
                        text_discovery.setTextColor(Color.GREEN);
                        break;
                    case 3:
                        text_me.setTextColor(Color.GREEN);
                        break;
                }
                currentIndex = position;
            }

            /*
            * state滑动有三种状态，（0，1，2）1：正在滑动，2：滑动完毕 0：什么都没做
            * */
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //设置底部bottomHolo的长度为屏幕长度的1/4
    public void initBottomHoloWidth(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        scrrenWidth = displayMetrics.widthPixels;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) bottomHolo.getLayoutParams();
        lp.width = scrrenWidth / 4;
        bottomHolo.setLayoutParams(lp);

    }

    private void setTextColor() {
        text_wechat.setTextColor(Color.BLACK);
        text_contact.setTextColor(Color.BLACK);
        text_discovery.setTextColor(Color.BLACK);
        text_me.setTextColor(Color.BLACK);
    }
}
