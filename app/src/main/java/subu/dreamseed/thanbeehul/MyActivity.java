package subu.dreamseed.thanbeehul;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MyActivity extends ActionBarActivity {
    TextView eventTittle,company_name,company_site;
    Drawable event_logo;
    ImageView mosque;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();        setContentView(R.layout.activity_my);
        eventTittle= (TextView) findViewById(R.id.eventName);
        Typeface swasdee=Typeface.createFromAsset(getAssets(), "sawasdee.ttf");
        eventTittle.setTypeface(swasdee);


    }



    public void next(View view)
    {
        Intent Frag=new Intent(this,mainHome.class);
        finish();
        startActivity(Frag);
    }
}
