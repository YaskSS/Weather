package com.example.serhey.weather.ui.activities;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.serhey.weather.R;
import com.example.serhey.weather.core.App;
import com.example.serhey.weather.core.AppBridge;
import com.example.serhey.weather.db.SharedPrefHelper;
import com.example.serhey.weather.ui.activities.SettingsActivity;
import com.example.serhey.weather.ui.fragments.TabAdapter;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Serhey on 04.09.2016.
 */
public class TabActivity extends AppCompatActivity implements OnMenuItemClickListener, OnMenuItemLongClickListener {

   private ViewPager viewPager;
   private TabLayout tabLayout;
   private Toolbar toolbar;
   private AppBridge appBridge;

    private FragmentManager fragmentManager;
    private ContextMenuDialogFragment mMenuDialogFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        toolbar = (Toolbar) findViewById(R.id.toolbarTabs);
        setSupportActionBar(toolbar);

        viewPager.setAdapter(new TabAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

        fragmentManager = getSupportFragmentManager();

        appBridge = (AppBridge) App.getContext().getApplicationContext();
        appBridge.getNetManager().checkConnectionWithInternet();

        initToolbar();
        initMenuFragment();
    }

    protected boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void initMenuFragment() {
        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize((int) getResources().getDimension(R.dimen.tool_bar_height));
        menuParams.setMenuObjects(getMenuObjects());
        menuParams.setClosableOutside(true);

        mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
        mMenuDialogFragment.setItemClickListener(this);
        mMenuDialogFragment.setItemLongClickListener(this);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private List<MenuObject> getMenuObjects() {
        List<MenuObject> menuObjects = new ArrayList<>();

        MenuObject closeMenuItem = new MenuObject();
        closeMenuItem.setResource(R.drawable.exit);

        MenuObject searchMenuItem = new MenuObject(getString(R.string.search_sity));
        searchMenuItem.setResource(R.drawable.search);

        MenuObject settingsMenuItem = new MenuObject(getString(R.string.settings));
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.settings);
        settingsMenuItem.setBitmap(b);

        MenuObject likeMenuItem = new MenuObject(getString(R.string.like));
        BitmapDrawable bd = new BitmapDrawable(getResources(),
                BitmapFactory.decodeResource(getResources(), R.drawable.like));
        likeMenuItem.setDrawable(bd);

        MenuObject errorMenuItem = new MenuObject(getString(R.string.send_error));
        errorMenuItem.setResource(R.drawable.error);

        menuObjects.add(closeMenuItem);
        menuObjects.add(searchMenuItem);
        menuObjects.add(settingsMenuItem);
        menuObjects.add(likeMenuItem);
        menuObjects.add(errorMenuItem);

        return menuObjects;
    }

    private void initToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbarTabs);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.context_menu:
                if (fragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
                    mMenuDialogFragment.show(fragmentManager, ContextMenuDialogFragment.TAG);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mMenuDialogFragment != null && mMenuDialogFragment.isAdded()) {
            mMenuDialogFragment.dismiss();
        } else {
            finish();
        }
    }

    @Override
    public void onMenuItemLongClick(View clickedView, int position) {
        Toast.makeText(this, "Clicked on position: " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMenuItemClick(View clickedView, int position) {
        Intent intent = new Intent(this, SettingsActivity.class);
        switch (position) {
            case 1:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showAlertDialog();
                    }
                }, 50);
                break;
            case 2:
                startActivity(intent);
                break;
            case 3:
                break;
            case 4:
                break;
        }
    }

    private void showAlertDialog() {
        new MaterialDialog.Builder(this)
                .title(R.string.city)
                .inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL)
                .input(null, null, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        SharedPrefHelper.getInstance().saveCity(dialog.getInputEditText().getText().toString());
                    }
                }).show();
    }
}
