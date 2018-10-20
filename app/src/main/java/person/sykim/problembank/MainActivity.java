package person.sykim.problembank;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import person.sykim.problembank.adapter.ProblemAdapter;
import person.sykim.problembank.data.Problem;
import person.sykim.problembank.dialog.LoginDialog;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";

    MyApplication application;

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

    HeaderViewHolder headerViewHolder;
    ProgressDialog progressDialog;

    ProblemAdapter problemAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        application = (MyApplication) getApplication();

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        headerViewHolder = new HeaderViewHolder( navigationView.getHeaderView(0) );

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Loading...");

        problemRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Problem> problemList = new ArrayList<>();
        for (int i=1; i<=10; i++) problemList.add(null);
        problemRecyclerView.setAdapter(problemAdapter = new ProblemAdapter(problemList));

        AsyncTask.execute(() -> {
            List<Problem> list = application.baekjoon.parseProblemList(null);
            runOnUiThread(() -> {
                applyProblemList();
                problemAdapter.reset(list);
            });
        });
    }

    void applyProblemList() {
        String username = application.baekjoon.getUserName();
        if (username != null) {
            headerViewHolder.usernameTextView.setText(username);
            headerViewHolder.emailTextView.setText("-");
        } else {
            headerViewHolder.usernameTextView.setText("-");
            headerViewHolder.emailTextView.setText("-");
            headerViewHolder.userImageView.setImageDrawable(addUserDrawable);
        }

        for (int page = application.baekjoon.minPage; page <= application.baekjoon.maxPage; page++) {
            TabLayout.Tab newTab = pageTabLayout.newTab();
            newTab.setText(page+"");
            pageTabLayout.addTab(newTab, false);
        }
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
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

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
//            application.baekjoon.
        }

        @OnClick(R.id.user_image_view)
        void onAddAccount() {
            new LoginDialog(MainActivity.this)
                    .setPositiveButton((username, password) -> AsyncTask.execute(() -> {
                        Document document = application.baekjoon.login(username, password);
                        System.out.println(document);
                        List<Problem> list = application.baekjoon.parseProblemList(document);
                        runOnUiThread(() -> {
                            drawer.closeDrawer(GravityCompat.START);
                            problemAdapter.reset(list);
                        });
                    }))
                    .show();
        }
    }
}
