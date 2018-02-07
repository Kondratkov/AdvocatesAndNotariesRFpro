package kondratkov.advocatesandnotariesrfpro.my_info;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import kondratkov.advocatesandnotariesrfpro.IN;
import kondratkov.advocatesandnotariesrfpro.R;
import kondratkov.advocatesandnotariesrfpro.Sidebar;

public class My_theme extends FragmentActivity {

    private MyAdapter mAdapter;
    private ViewPager mPager;

    public TextView tv_1, tv_2;
    public ImageView iv_1, iv_2;
    public IN in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_my_theme);

        tv_1 = (TextView)findViewById(R.id.my_quest_tv_1);
        tv_2 = (TextView)findViewById(R.id.my_quest_tv_2);

        iv_1 = (ImageView)findViewById(R.id.my_quest_iv_1);
        iv_2 = (ImageView)findViewById(R.id.my_quest_iv_2);

        in = new IN();

        mAdapter = new MyAdapter(getSupportFragmentManager());

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);
        mPager.setCurrentItem(0); // выводим one экран

        mPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View v, float pos) {
                final float invt = Math.abs(Math.abs(pos) - 1);
                v.setAlpha(invt);
                int alpha = (int)(pos*215);
                tv_1.setTextColor(Color.argb(alpha + 40, 255, 255, 255));
                iv_1.setBackgroundColor(Color.argb(alpha + 40, 254, 179, 42));

                tv_2.setTextColor(Color.argb((int) (215 - alpha) + 40, 255, 255, 255));
                iv_2.setBackgroundColor(Color.argb((int) (215 - alpha) + 40, 254, 179, 42));
            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();
        if(in.getOnemay()==false){
            this.finish();
        }
        in.setChoice_of_menus(2);
        in.set_context(My_theme.this);
        in.set_activity(My_theme.this);
    }

    public void onClick(View v){
        Intent intent = null;
        switch (v.getId()){
            case R.id.my_quest_but_close:
                My_theme.this.finish();
                break;

            case R.id.my_quest_but_menu:
                intent = new Intent(My_theme.this, Sidebar.class);
                startActivity(intent);
                break;

            case R.id.my_quest_tv_1:
                mPager.setCurrentItem(0);
                break;

            case R.id.my_quest_tv_2:
                mPager.setCurrentItem(1);
                break;
        }
    }

    public static class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    Log.d("qwerty", "Fragment 1");
                    return new My_frag_quest_private();

                case 1:
                    Log.d("qwerty", "Fragment 2");
                    return new My_frag_quest_forum();//forum();
                default:
                    return null;
            }
        }
    }
}
