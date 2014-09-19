package subu.dreamseed.thanbeehul;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;


public class Downloading extends ActionBarActivity {
    Button download;
    ImageView downloaded;
    ProgressBar downloading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downloading);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.downloading, menu);
        return true;
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
    public void downloading(View V)
    {
        /*String url="http://www.dreamseed.in//apps/Mobile/Android/thanbeehul/Pictures/2.jpg";
        if(isNetworkConnected())
              new DownloadAsyncTask().execute(url);
        else
            Toast.makeText(getApplicationContext(),"No connection!!!!!Try again",Toast.LENGTH_LONG).show();

*/
        String name=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath()+"/"+ "subu.jpg";
        Bitmap bmp= BitmapFactory.decodeFile(name);
        downloaded= (ImageView) findViewById(R.id.imageViewss);
        downloaded.setVisibility(View.VISIBLE);
        downloaded.setImageBitmap(bmp);


    }

    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com"); //You can replace it with your name

            if (ipAddr.equals("")) {
                return false;
            } else {
                return true;
            }

        } catch (Exception e) {
            return false;
        }

    }
    class DownloadAsyncTask extends AsyncTask<String,Integer,Boolean>
    {
        URL imagUrl;
        int downloadsize;
        FileOutputStream thanbeeFileOutputStream;
        HttpURLConnection imageConnection;
        InputStream imgInputStream;
        File thanbee;
        int BufSize,i=0;
        int Progressperc;
        int counter =0;
        private boolean succes=false;
        String name;

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                String name=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath()+"/"+ Uri.parse(params[0]).getLastPathSegment();
                Toast.makeText(getApplicationContext(),"d : "+name,Toast.LENGTH_SHORT).show();
                thanbee=new File(name);
                thanbeeFileOutputStream=new FileOutputStream(thanbee);
                imagUrl=new URL(params[0]);

                HttpURLConnection imageConnection= (HttpURLConnection) imagUrl.openConnection();
                downloadsize=imageConnection.getContentLength();
                imgInputStream=imageConnection.getInputStream();
                byte[] array=new  byte[1024];
                while ((BufSize=imgInputStream.read(array))!=-1)
                {
                    thanbeeFileOutputStream.write(array,0,BufSize);
                    counter+=BufSize;
                    publishProgress(counter);
                }

                succes=true;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally
                {
                 if(imageConnection!=null)
                 {
                     imageConnection.disconnect();
                 }
                 if(thanbeeFileOutputStream!=null)
                 {
                     try {
                         thanbeeFileOutputStream.close();
                     } catch (IOException e) {
                         e.printStackTrace();
                     }
                 }
                    if (imgInputStream!=null)
                    {
                        try {
                            imgInputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }

            return succes;

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            Progressperc =(int)((values[0]*100)/downloadsize);
            Toast.makeText(getApplicationContext(),"Dowload"+values[0]+"/"+downloadsize+"="+Progressperc,Toast.LENGTH_LONG).show();
            downloading.setProgress(Progressperc);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            downloading.setVisibility(View.INVISIBLE);
            if (aBoolean)
            {
                Bitmap bmp= BitmapFactory.decodeFile(name);
                downloaded.setVisibility(View.VISIBLE);
                downloaded.setImageBitmap(bmp);
            }
            else
                Toast.makeText(getApplicationContext(),"Dowloading Failed!! :(",Toast.LENGTH_LONG).show();


        }

        @Override
        protected void onPreExecute() {
            downloading= (ProgressBar) findViewById(R.id.progressBar);
            downloaded= (ImageView) findViewById(R.id.imageViewss);
        }



    }
}
