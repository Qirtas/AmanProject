package com.aman.amanapp;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnDragListener, View.OnTouchListener, View.OnLongClickListener,
        GridAdapter.OnLongClickListener{
    public static String[] osNameList = {
            "Messages",
            "Gmail"
    };
    public static int[] osImages = {
            R.drawable.messages,
            R.drawable.gmail};
    LinearLayout lowerLayout;
    GridView gridView;
    ScrollView scrollView;
    int top;
    int resized = 0;
    DatabaseForApps database;
    RelativeLayout messages_layout , inner_layout;
    public String TAG = "AMANAPP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = new DatabaseForApps(this);

     /* ImageButton ibr = (ImageButton) findViewById(R.id.ibr);
        ibr.setOnDragListener(this);
        ibr.setOnLongClickListener(this);
        ImageButton button= (ImageButton) findViewById(R.id.ib);
        button.setOnDragListener(this);
        button.setOnLongClickListener(this);*/

        inner_layout = (RelativeLayout) findViewById(R.id.inner_layout);

        lowerLayout = (LinearLayout) findViewById(R.id.lower_layout);
        lowerLayout.setOnDragListener(this);
        lowerLayout.setOnLongClickListener(this);
        LinearLayout activityDrag = (LinearLayout) findViewById(R.id.activity_drag);
        activityDrag.setOnDragListener(this);
         gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(new GridAdapter(this, getInstalledApps() , new MainActivity()));
        gridView.setOnLongClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public boolean onDrag(View view1, DragEvent event) {
        float X = event.getX();
        float Y = event.getY();
        Log.d("Drag", "X " + (int) X + "Y " + (int) Y);
        Log.d("Drag","Here"+event.getAction());
        switch (event.getAction()) {

            case DragEvent.ACTION_DROP:

                if(view1.getId() == R.id.upper_layout)
                {
                    Log.e("Drag","Upper");
                }
                else
                {
                    if(view1.getId() == R.id.lower_layout)
                    {
                        Log.e("Drag","Lower");
                    }
                }

                Log.d("Drag", "X " + (int) X + "Y " + (int) Y);
                View view = (View) event.getLocalState();
                Display display = getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                int width = size.x;
                int height = size.y;
                int midHeight = height/2;
                if(Y<midHeight)
                view.setY(0);
                else
                {
                  if(resized<0)
                  {
                      view.setY(midHeight - 100-(resized * 100));
                  }
                    else
                  {
                      view.setY(midHeight - 100 - (resized * 200));
                  }
                }

                break;
           case DragEvent.ACTION_DRAG_ENDED:
               Log.d("Drag","Ended");
               break;
            case DragEvent.ACTION_DRAG_ENTERED:
                Log.d("Drag","Entered");
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                Log.d("Drag","Exited");
                break;
            case DragEvent.ACTION_DRAG_LOCATION:
                Log.d("Drag","Location");
                break;
            case DragEvent.ACTION_DRAG_STARTED:
                Log.d("Drag","Started");
                break;


        }
        return true;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            view.startDrag(null, shadowBuilder, view, 0);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onLongClick(View view) {
        Log.d(TAG ,"OnLongClicked");
        if(view.getId()==R.id.lower_layout) {
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
        view.startDrag(null, shadowBuilder, view, 0);
            return true;
        }
        int targetHeight = 200;
        int startHeight = lowerLayout.getHeight();
        int duration = 5000;
        ResizeAnimation resizeAnimation = new ResizeAnimation(
                lowerLayout,
                targetHeight,
                startHeight
        );
        resizeAnimation.setDuration(duration);
        lowerLayout.startAnimation(resizeAnimation);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onLongClicked(int index) {
        Log.e( TAG , "onLongClicked Index" + index+"");
        if(index%3==0)
        {

            int startHeight = lowerLayout.getHeight();
            int duration = 500;
            ResizeAnimation resizeAnimation = new ResizeAnimation(
                    lowerLayout,
                    startHeight+200,
                    startHeight
            );
            resized++;
            resizeAnimation.setDuration(duration);
            lowerLayout.startAnimation(resizeAnimation);
        }
        else {
            if(index==2 || (index-2)%3==0) {
                int startHeight = lowerLayout.getHeight();
                int duration = 500;
                ResizeAnimation resizeAnimation = new ResizeAnimation(
                        lowerLayout,
                        startHeight-200,
                        startHeight
                );
                resized--;
                resizeAnimation.setDuration(duration);
                lowerLayout.startAnimation(resizeAnimation);
            }
            else {
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(lowerLayout);
                lowerLayout.startDrag(null, shadowBuilder, lowerLayout, 0);
            }
       }
    }


    private ArrayList<AppItem> getInstalledApps() {

        ArrayList<AppItem> res = new ArrayList<AppItem>();
        List<PackageInfo> packs = getPackageManager().getInstalledPackages(0);

        Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> availableActivities = getPackageManager().queryIntentActivities(i, 0);

        database.open();
        int j=0;
//        for (int i = 0; i < packs.size(); i++) {
//           if(j==50)
//            {
//                break;
//            }

        for(ResolveInfo ri:availableActivities){
          //  PackageInfo p = packs.get(i);
//           if ((isSystemPackage(p) == false)) {
//               j++;
                String appName = ri.loadLabel(getPackageManager()).toString();
                Drawable icon = ri.activityInfo.loadIcon(getPackageManager());
              String packageName =  ri.activityInfo.packageName;
               AppItem appItem = new AppItem(appName,icon,packageName);
                res.add(appItem);
               database.insertRecordFirstTime(appItem);
         //   }
        }
        database.close();
        return res;
    }



//    private void loadApps(){
//        manager = getPackageManager();
//
//        Intent i = new Intent(Intent.ACTION_MAIN, null);
//        i.addCategory(Intent.CATEGORY_LAUNCHER);
//
//        List<ResolveInfo> availableActivities = manager.queryIntentActivities(i, 0);
//        for(ResolveInfo ri:availableActivities){
//            AppDetail app = new AppDetail();
//            app.label = ri.loadLabel(manager);
//            app.name = ri.activityInfo.packageName;
//            app.icon = ri.activityInfo.loadIcon(manager);
//            apps.add(app);
//        }
//    }


    private boolean isSystemPackage(PackageInfo pkgInfo) {
        return ((pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) ? true : false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = new Intent(MainActivity.this,StatsActivity.class);
        startActivity(intent);

        return true;
    }

    public void addFragment(Fragment mFragment, boolean addToBackStack)
    {
        gridView.setVisibility(View.GONE);
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager != null)
        {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.inner_layout , mFragment)
                    .addToBackStack(addToBackStack ? "" : null)
                    .commit()
            ;
        }
    }

    public void openMessageslayout()
    {
        inner_layout.setVisibility(View.GONE);
        messages_layout.setVisibility(View.VISIBLE);

        messages_layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onLongClicked(2);
                return false;
            }
        });
    }
}