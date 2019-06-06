package person.sykim.problembank.view.activity;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import person.sykim.problembank.MyApplication;
import person.sykim.problembank.R;
import person.sykim.problembank.adapter.ProblemAdapter;
import person.sykim.problembank.data.Preference;
import person.sykim.problembank.data.User;
import person.sykim.problembank.data.bank.Problem;
import person.sykim.problembank.data.bank.ProblemBank;
import person.sykim.problembank.dialog.LoginDialog;
import person.sykim.problembank.util.SecurityUtils;
import person.sykim.problembank.view.dialog.ProblemBankDialog;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private MainActivity THIS = this;

    private static final String TAG = "MainActivity";

    MyApplication application;
    ProblemBank bank;
    User user;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.problem_recycler_view)
    RecyclerView problemRecyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.problem_page_tab)
    TabLayout pageTabLayout;

    @BindDrawable(R.drawable.ic_add_circle_outline)
    Drawable addUserDrawable;

    Menu navigationMenu;

    HeaderViewHolder headerViewHolder;
    ProgressDialog progressDialog;

    ProblemAdapter problemAdapter;

    // todo Model-View-Presenter 패턴 적용

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        application = (MyApplication) getApplication();
        user = application.user;

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationMenu = navigationView.getMenu();
        headerViewHolder = new HeaderViewHolder( navigationView.getHeaderView(0) );

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Loading...");

        refreshLayout.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorAccent));
        refreshLayout.setOnRefreshListener(this::loadList);

        problemRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        problemRecyclerView.setAdapter(problemAdapter = new ProblemAdapter());
        Log.d(TAG, "onCreate: 완료");

        String bankKey = Preference.BANK.string();
        if (bankKey != null) {
            THIS.bank = application.bank.get(bankKey);

            initList();
        } else {
            new ProblemBankDialog(this)
                    .setProblemBanks(application, key -> {
                        THIS.bank = application.bank.get(key);
                        Preference.BANK.save(key);
                        initList();
                    })
                    .show();
        }
    }

    void initProblemType(boolean tutorial) {
        navigationMenu.findItem(R.id.tutorial).setChecked(tutorial);
        navigationMenu.findItem(R.id.total).setChecked(!tutorial);
        Prefs.putBoolean(bank.key+"-tutorial", tutorial);
    }

    void initList() {
        initProblemType(Prefs.getBoolean(bank.key+"-tutorial", false));
        if (bank == null) {
            Toast.makeText(this, "은행이 없어 목록을 로드하지 못했습니다", Toast.LENGTH_SHORT).show();
            return;
        }
        refreshLayout.setRefreshing(true);
        String username = Prefs.getString(bank.key+"-username", "");
        String encrypted = Prefs.getString(bank.key+"-password", "");
        String password = "";
        try {
            if (user != null) {
                if (encrypted.length() > 0) {
                    password = SecurityUtils.decrypt(user.getKey(), encrypted);
                }
            } else {
                String key = Preference.UUID.string();
                password = SecurityUtils.decrypt(key, encrypted);
            }
        } catch (Exception e) {
            Log.e(TAG, "onCreate: decrypt", e);
            password = "";
        }
        tryLogin(username, password);
    }

    void tryLogin(String username, String password) {
        AsyncTask.execute(() -> {
            if (username.length() > 0 && password.length() > 0) {
                Log.i(TAG, "tryLogin: login using pref");
                bank.login(username, password);
            }

            Log.i(TAG, "tryLogin: get problemList");
            applyProblemList(bank.loadProblemList());
        });
    }

    void loadList() {
        AsyncTask.execute(() -> {
            applyProblemList(bank.loadProblemList());
        });
    }

    void applyProblemList(List<Problem> list) {
        runOnUiThread(() -> {
            drawer.closeDrawer(GravityCompat.START);
            problemAdapter.reset(list);
            String username = bank.getUserName();
            if (username != null) {
                headerViewHolder.usernameTextView.setText(username);
                headerViewHolder.emailTextView.setText("-");
            } else {
                headerViewHolder.usernameTextView.setText("-");
                headerViewHolder.emailTextView.setText("-");
                headerViewHolder.userImageView.setImageDrawable(addUserDrawable);
            }

            if (pageTabLayout.getTabCount() > 0) {
                pageTabLayout.removeAllTabs();
            }
            for (int page = bank.minPage; page <= bank.maxPage; page++) {
                TabLayout.Tab newTab = pageTabLayout.newTab();
                newTab.setText(page+"");
                pageTabLayout.addTab(newTab, page == 1);
            }
            refreshLayout.setRefreshing(false);
        });
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.nav_other_bank:
                new ProblemBankDialog(this)
                        .setProblemBanks(application, key -> {
                            THIS.bank = application.bank.get(key);
                            Preference.BANK.save(key);
                            loadList();
                        })
                        .cancelable()
                        .show();
                break;
            case R.id.tutorial:
                initProblemType(true);
                loadList();
                break;
            case R.id.total:
                initProblemType(false);
                loadList();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    class HeaderViewHolder {
        @BindView(R.id.username_text_view)
        TextView usernameTextView;
        @BindView(R.id.email_text_view)
        TextView emailTextView;
        @BindView(R.id.user_image_view)
        ImageView userImageView;

        HeaderViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.username_layout)
        void onOpenSelectAccount() {
//            bank.
        }

        @OnClick(R.id.user_image_view)
        void onAddAccount() {
            new LoginDialog(MainActivity.this)
                    .setPositiveButton((username, password) -> AsyncTask.execute(() -> {
                        runOnUiThread(() -> refreshLayout.setRefreshing(true));
                        try {
                            String encrypted;
                            if (user != null) {
                                encrypted = SecurityUtils.encrypt(user.getKey(), password);
                            } else {
                                String key = Preference.UUID.string();
                                encrypted = SecurityUtils.encrypt(key, password);
                            }
                            Log.d(TAG, "onAddAccount: "+encrypted);
                            Prefs.putString(bank.key+"-password", encrypted);
                        } catch (Exception e) {
                            Log.e(TAG, "tryLogin: encrypt", e);
                        }
                        Prefs.putString(bank.key+"-username", username);

                        tryLogin(username, password);
                    }))
                    .show();
        }
    }
}
