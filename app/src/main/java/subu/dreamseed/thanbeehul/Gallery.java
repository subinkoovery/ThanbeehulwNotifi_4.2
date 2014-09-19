package subu.dreamseed.thanbeehul;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;



public class Gallery extends ActionBarActivity {
    private int count=100;
    TextView tittle;
       ViewPager gallery;
    ImageView myimg;
    ProgressBar myProgressBar;
    private boolean succes=false;
    myFragAdapter myAdt;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_gallery);
  tittle= (TextView) findViewById(R.id.fragEvent_NameT);
        Typeface swasdee=Typeface.createFromAsset(getAssets(), "sawasdee.ttf");
        tittle.setTypeface(swasdee);
        tittle.setText("GALLERY");
        gallery= (ViewPager) findViewById(R.id.pager);
        myAdt=new myFragAdapter(getSupportFragmentManager());
        gallery.setAdapter(myAdt);
        myimg= (ImageView) findViewById(R.id.actionT);
        myimg.setImageResource(R.drawable.ic_drawer);
        myimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.gallery, menu);
        return true;
    }
    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            // There are no active networks.
            return false;
        } else
            return true;
    }
    public void printMessage(String msg)
    {
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
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


    public void restartViewpager(int pos)
    {
        final int v=pos;

        gallery.postDelayed(new Runnable() {
            @Override
            public void run() {
                myAdt.setCount(v);
                myAdt.notifyDataSetChanged();

            }
        },200);
    }
    public void setitem(final int pos)
    {
        gallery.postDelayed(new Runnable() {
            @Override
            public void run() {
                gallery.setCurrentItem(pos);
            }
        },100);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
}

// TODO  ghjhhi
class myFragAdapter extends FragmentStatePagerAdapter
{
    private int count=50;
    public myFragAdapter(FragmentManager fm) {
        super(fm);
    }
    public  void setCount(int pos)
    {
        count=pos;
    }

    @Override
    public Fragment getItem(int i) {
        return Gallery_pic.newInstance(i);
    }

    @Override
    public int getCount() {
        return count;
    }
}