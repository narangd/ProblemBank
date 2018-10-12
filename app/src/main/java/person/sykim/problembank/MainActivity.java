package person.sykim.problembank;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import person.sykim.problembank.adapter.ProblemAdapter;
import person.sykim.problembank.data.ParseOption;
import person.sykim.problembank.data.Problem;

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

    ProgressDialog progressDialog;


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


        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Loading...");


        problemRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        AsyncTask.execute(MainActivity.this::printList);
    }



    public void printList() {
        // {"code":1000,"ratio":46.434,"solveCount":48723,"status":"none","title":"A+B","totalCount":145316,"url":"/problem/1000"}

        ArrayList<Problem> problems = new ArrayList<>();
        Document document;
        ParseOption parseOption = application.baekjoon.normal;
        try {
            Log.d(TAG, "printList: url: "+ parseOption.url);
            Connection connection = Jsoup.connect(parseOption.url)
                    .timeout(10000);

            switch (parseOption.method) {
                case "GET":
                    connection.method(Connection.Method.GET);
                    break;
                case "POST":
                    connection.method(Connection.Method.POST);
                    break;
            }
//                    .cookies( load() )
            Connection.Response response = connection.execute();

//            save( response.cookies() );
            Log.d(TAG, "getProblems: done");

            document = response.parse();

            Log.d(TAG, "getProblems: problem");

            Elements trElements = document.select(parseOption.list);
            for (Element trElement : trElements) {
                Problem problem = new Problem();

                String code = trElement.select(parseOption.code).first().text();
                problem.code = Integer.parseInt(code);

                problem.title = trElement.select(parseOption.title).first().text();

                String status = "none";
                Elements labelElements = trElement.select(parseOption.success);
                if (labelElements.size() > 0) {
                    status = "success";
                }
                labelElements = trElement.select(parseOption.failure);
                if (labelElements.size() > 0) {
                    status = "failure";
                }
                problem.status = status;

                String solveCount = trElement.select(parseOption.solveCount).first().text();
                problem.solveCount = Integer.parseInt(solveCount);

                String totalCount = trElement.select(parseOption.totalCount).first().text();
                problem.totalCount = Integer.parseInt(totalCount);

                String ratio = trElement.select(parseOption.ratio).first().text().replaceAll("[^\\.|\\d]", "");
                problem.ratio = Double.parseDouble(ratio);

                problems.add(problem);

                Log.d(TAG, "printList: "+ new Gson().toJson(problem));
            }

//            updateUserName(document);

//            pageMin = 1;
//            pageMax = Integer.parseInt(document.select("ul.pagination a").last().text());
//            updateDate = 0;

            runOnUiThread(() -> problemRecyclerView.setAdapter(new ProblemAdapter(problems)));

        } catch (IOException e) {
            e.printStackTrace();
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
}
