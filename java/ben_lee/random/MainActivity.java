package ben_lee.random;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private String[] choices_array;
    private ActionBarDrawerToggle mDrawerToggle;

    private Fragment oldFragment = null;
    private Fragment fragmentToSet = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar appBar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(appBar);
        initialiseFragment(savedInstanceState);
        createDrawer();
    }

    private void initialiseFragment(Bundle savedInstanceState) {
        Fragment initialFragment = new RandomNumberFragment();
        oldFragment = initialFragment;
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, initialFragment).commit();
        getSupportActionBar().setTitle(getResources().getStringArray(R.array.choices_array)[0]);
        /*
        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            Fragment initialFragment = new RandomNumberFragment();
            oldFragment = initialFragment;
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, initialFragment).commit();
        }
        */
    }

    private void createDrawer() {
        choices_array = getResources().getStringArray(R.array.choices_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.drawer_list);
        TextView listHeader = findViewById(R.id.drawer_title);
        listHeader.setText(getResources().getString(R.string.options));
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, choices_array));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        Toolbar appBar = (Toolbar) findViewById(R.id.app_bar);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, appBar, R.string.drawer_open, R.string.drawer_open) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                replaceFragment();
            }
        };
        mDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            mDrawerLayout.closeDrawer(findViewById(R.id.left_drawer));
            switchOption(position);
        }
    }

    private void switchOption(int position) {
        switch (position) {
            case 0:
                fragmentToSet = new RandomNumberFragment();
                break;
            case 1:
                fragmentToSet = new RandomChoiceFragment();
                break;
            case 2:
                fragmentToSet = new CoinFlipFragment();
                break;
            case 3:
            default:
                break;
        }
        String[] titles = getResources().getStringArray(R.array.choices_array);
        getSupportActionBar().setTitle(titles[position]);
    }

    private void replaceFragment() {
        if (!fragmentToSet.getClass().toString().equals(oldFragment.getClass().toString())) {
            oldFragment = fragmentToSet;
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, fragmentToSet);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

}
