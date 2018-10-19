package ceuilisa.mirai.activities;

import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;

import ceuilisa.mirai.MusicService;
import ceuilisa.mirai.R;
import ceuilisa.mirai.fragments.FragmentCenter;
import ceuilisa.mirai.fragments.FragmentLeft;
import ceuilisa.mirai.fragments.FragmentRight;
import ceuilisa.mirai.utils.Constant;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Fragment[] mFragments;
    public DrawerLayout mDrawerLayout;
    private int lastShowFragment = Constant.FRAGMENT_ONE;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                switchFrament(Constant.FRAGMENT_ONE);
                return true;
            case R.id.navigation_dashboard:
                switchFrament(Constant.FRAGMENT_TWO);
                return true;
            case R.id.navigation_notifications:
                switchFrament(Constant.FRAGMENT_THREE);
                return true;


        }
        return false;
    };

    @Override
    void initLayout() {
        mLayoutID = R.layout.activity_main;
    }

    @Override
    void initView() {
        mDrawerLayout = findViewById(R.id.drawer_layout);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        initFragments();
    }

    @Override
    void initData() {
        Intent intent = new Intent(this, MusicService.class);
        startService(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(this, MusicService.class);
        stopService(intent);
    }

    private void initFragments() {
        FragmentLeft fragmentLeft = new FragmentLeft();
        FragmentCenter fragmentCenter = new FragmentCenter();
        FragmentRight fragmentRight = new FragmentRight();
        mFragments = new Fragment[]{fragmentLeft, fragmentCenter, fragmentRight};
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, mFragments[0])
                .show(mFragments[0])
                .commit();
        lastShowFragment = 0;
    }

    public void switchFrament(int index) {
        if (lastShowFragment == index) {
            return;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(mFragments[lastShowFragment]);
        if (!mFragments[index].isAdded()) {
            transaction.add(R.id.fragment_container, mFragments[index]);
        }
        transaction.show(mFragments[index]).commitAllowingStateLoss();
        lastShowFragment = index;
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}