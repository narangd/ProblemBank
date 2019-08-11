package person.sykim.problembank.view.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import person.sykim.problembank.R;
import person.sykim.problembank.adapter.EditorAdapter;
import person.sykim.problembank.data.editor.Source;
import person.sykim.problembank.data.editor.constant.ConstantText;
import person.sykim.problembank.data.editor.constant.ConstantType;
import person.sykim.problembank.data.editor.execute.MakeVariable;
import person.sykim.problembank.data.editor.execute.PrintConsole;

public class EditorActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = EditorActivity.class.getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.editor_recycler_view)
    RecyclerView editorRecyclerView;

    EditorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        editorRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        editorRecyclerView.setAdapter(adapter = new EditorAdapter());

        adapter.getList().add(new MakeVariable(ConstantType.TEXT, "abc", "test"));
        adapter.getList().add(new PrintConsole(new ConstantText("console test text")));

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.action_run:
                return true;
            case R.id.action_save:
                Source source = new Source();
                source.setName("Preview");
                String json = Source.getGsonPretty().toJson(adapter.getFunction());
                source.setJson(json);
                new AlertDialog.Builder(this)
                        .setTitle("Save source?")
                        .setMessage(json)
                        .setPositiveButton(android.R.string.ok, (dialogInterface, i) -> {
                            List<Source> list = Source.find(Source.class, "name = ?", source.getName());
                            if (list.size() <= 0) {
                                source.save();
                            } else {
                                Source dbSource = list.get(0);
                                dbSource.setUpdateTime(new Date());
                                dbSource.setJson(json);
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, null)
                        .show();
                return true;
            case R.id.action_settings:
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

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
