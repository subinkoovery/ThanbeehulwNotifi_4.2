package subu.dreamseed.thanbeehul;

import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class Schedule extends ActionBarActivity {
    ViewPager tab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_schedule);

        tab= (ViewPager) findViewById(R.id.tabContainer);
        Typeface swasdee=Typeface.createFromAsset(getAssets(), "sawasdee.ttf");
        TextView tittle= (TextView) findViewById(R.id.fragEvent_NameT);
        tittle.setTypeface(swasdee);
        tab.setAdapter(new myTabAdapter(getSupportFragmentManager()));
        ImageView myimg= (ImageView) findViewById(R.id.actionT);
        myimg.setImageResource(R.drawable.ic_drawer);
        myimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.schedule, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
class myTabAdapter extends FragmentPagerAdapter {
    public myTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment frag = null;
        if (i == 0)
            frag = new Scedul_day1();
        else if (i == 1)
            frag = new Schedule_Day2();
        else
            frag = new Schedule_Day3();
        return frag;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        CharSequence mytitle;
        if (position == 0) {
            mytitle = "Day 1";

        } else if (position == 1)
            mytitle = "Day 2";

        else
            mytitle = "Day 3";

        return mytitle;
    }
}


