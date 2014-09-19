package subu.dreamseed.thanbeehul;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class Gallery_pic extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    static  boolean RestartViewpager=false,connectionStatus=false;
    static int gallerycount=1;
    private int index=0,setcustom,temp;
    ImageView im;
    String dirImage;
    Activity parent;
    ProgressBar percentage;
    File img;
    boolean downloadStatus=false;
    DownloadAsyncTask downSync;
    public  static Gallery_pic newInstance(int pos)
    {
        Bundle args=new Bundle();
        args.putInt("POS",pos);

        Gallery_pic frag=new Gallery_pic();
        frag.setArguments(args);
        return  frag;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        parent=(Gallery)getActivity();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null)
            index=getArguments().getInt("POS");



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        dirImage= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath()+"/thanbee"+Integer.toString(index)+".jpg";

       // Log.e("POS",Integer.toString(index));

        img=new File(dirImage);
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_gallery_pic, container, false);
        percentage= (ProgressBar) view.findViewById(R.id.progressBar);
        if(!RestartViewpager) {


            im = (ImageView) view.findViewById(R.id.imageViewgalery);
            if (index == 0)
                im.setImageDrawable(getResources().getDrawable(R.drawable.f));

            else if (index == 1)
                im.setImageDrawable(getResources().getDrawable(R.drawable.ss));
            else {

                //  Log.e("File existence : "+dirImage,Boolean.toString(img.exists())+":"+Integer.toString(index));

                if (img.exists()) {
                    im.setVisibility(View.VISIBLE);

                    Bitmap bmp = BitmapFactory.decodeFile(dirImage);
                    try {
                        int b = bmp.getHeight();
//                        //  Log.e("Bitmap Height:on "+index,Integer.toString());
                        im.setImageBitmap(bmp);

                    } catch (Exception e) {
                        e.printStackTrace();
                        downSync = new DownloadAsyncTask();
//
//                        img.delete();

                        // Log.e("Corrupted : "+index,"");
//                       percentage.setVisibility(View.VISIBLE);
                       downSync.execute();
                    }


                }
                else {
                    Log.e("     "+index,"Not Exists");
                   // img.delete();
                    downSync = new DownloadAsyncTask();
                    downSync.execute();


                }
            }
        }
        else
        {
            percentage.setVisibility(View.VISIBLE);
        }


        return view;
    }
    public  boolean isPersistent()
    {

        return true;
    }

    class DownloadAsyncTask extends AsyncTask<Void,Integer,Boolean>
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
        String imgUrlname="",filename;
        ProgressBar downloading;


        @Override
        protected Boolean doInBackground(Void... params) {
            if (connectionStatus=(((Gallery)getActivity()).isNetworkConnected()))
            {
                Log.e("  Async","Started");
                try {

                    imgUrlname = "http://www.dreamseed.in/apps/Mobile/Android/thanbeehul/Pictures/thanbee" + Integer.toString(index) + ".jpg";
                    //Toast.makeText(getApplicationContext(), "d : " + name, Toast.LENGTH_SHORT).show();
                    filename = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/" + Uri.parse(imgUrlname).getLastPathSegment();

                    thanbee = new File(filename);
                    thanbeeFileOutputStream = new FileOutputStream(thanbee);
                    imagUrl = new URL(imgUrlname);

                    HttpURLConnection imageConnection = (HttpURLConnection) imagUrl.openConnection();
                    downloadsize = imageConnection.getContentLength();
                    imgInputStream = imageConnection.getInputStream();
                    byte[] array = new byte[1024];
                    while ((BufSize = imgInputStream.read(array)) != -1) {
                        thanbeeFileOutputStream.write(array, 0, BufSize);
                        counter += BufSize;
                        publishProgress(counter);
                    }

                    downloadStatus = true;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    // Log.e("Stop"+index,"Dont ViewPage");
                    thanbee.delete();
                    RestartViewpager = true;
                    if (RestartViewpager) {

                        ((Gallery) getActivity()).restartViewpager(index);

                        //this.cancel(true);
                        /*if (index == 2)
                            Toast.makeText(getActivity(), "Check your internet connection!", Toast.LENGTH_SHORT).show();*/
                        RestartViewpager = false;

                    }


                    e.printStackTrace();
                } finally {
                    if (imageConnection != null) {
                        imageConnection.disconnect();
                    }
                    if (thanbeeFileOutputStream != null) {
                        try {
                            thanbeeFileOutputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (imgInputStream != null) {
                        try {
                            imgInputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }

            }

        else
            {
                setcustom=index;
                if (gallerycount>index)
                    setcustom=gallerycount;

                ((Gallery) getActivity()).restartViewpager(setcustom);

                //this.cancel(true);       bbbbbbbbbbbbbbbbbbbbbbbbbbbbb


            }


            return downloadStatus;

        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            Progressperc =(int)((values[0]*100)/downloadsize);
            if (Progressperc%100==0)
                Log.e("Prog : "+Progressperc,"");
                //Toast.makeText(getActivity(),"Dowload"+values[0]+"/"+downloadsize+"="+Progressperc,Toast.LENGTH_LONG).show();
            percentage.setProgress(Progressperc);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            percentage.setVisibility(View.INVISIBLE);

            if (aBoolean)
            {
               //
               // *jLog.e("aBoolean"+filename,Boolean.toString(aBoolean));
              Bitmap bmp=BitmapFactory.decodeFile(filename);
                im.setImageBitmap(bmp);
                gallerycount++;
                /*temp=index;
                ((Gallery) getActivity()).restartViewpager(++temp);*/

            }
            else if (connectionStatus)
              thanbee.delete();
            if (index == 2&&!aBoolean)
                Toast.makeText(getActivity(), "Check your internet connection!", Toast.LENGTH_LONG).show();


        }

        @Override
        protected void onPreExecute() {
            percentage.setVisibility(View.VISIBLE);
            percentage.setIndeterminate(false);
        }



    }


}
