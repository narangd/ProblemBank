package person.sykim.problembank.view.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import person.sykim.problembank.R;
import person.sykim.problembank.adapter.EditorAdapter;
import person.sykim.problembank.data.Source;
import sykim.person.editor.Function;
import sykim.person.editor.NameSpaceManager;
import sykim.person.editor.SourceJson;
import sykim.person.editor.dialog.ConsoleDialog;
import sykim.person.editor.dialog.ExecutableDialog;
import sykim.person.editor.execute.MakeVariable;

public class EditorActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    private static final String TAG = EditorActivity.class.getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.editor_recycler_view)
    RecyclerView editorRecyclerView;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    EditorAdapter adapter;

    InputMethodManager inputMethodManager;

    Source source;

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

        fab.setOnDragListener((view, dragEvent) -> {
            Log.d(TAG, "onDrag: "+view+", event:"+dragEvent);
//            dragEvent.get
            return false;
        });

        loadSource();
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
                new ConsoleDialog(this)
                        .setSource(adapter.toFunction())
                        .show();
                return true;
            case R.id.action_save:
//                SourceJson source = new SourceJson();
//                source.setName("Preview");
                String json = SourceJson.getGsonPretty().toJson(adapter.toFunction());
//                source.setJson(json);
                new MaterialAlertDialogBuilder(this)
                        .setTitle("Save source?")
                        .setMessage(json)
                        .setPositiveButton(android.R.string.ok, (dialogInterface, i) -> {
//                            List<Source> list = Source.find(Source.class, "name = ?", source.getName());
//                            if (list.size() <= 0) {
//                                source.save();
//                            } else {
//                                Source dbSource = list.get(0);
//                                dbSource.setUpdateTime(new Date());
//                                dbSource.setJson(json);
//                            }
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

    @Override
    protected void onStop() {
        super.onStop();
        Function function = adapter.toFunction();
        source.setJson( SourceJson.getGson().toJson(function) );
        source.save();
        Log.d(TAG, "onStop: saved: "+source.getJson());
    }

    // click event

    @OnClick(R.id.fab)
    public void onFab() {
        Log.d(TAG, "onFab: ");
        // (temp)
        ExecutableDialog.create(this, new MakeVariable.Adapter(), adapter);
    }

    @OnLongClick(R.id.fab)
    public void onLongFab(View fab) {
        Log.d(TAG, "onLongFab: "+fab);
//        fab.startDragAndDrop(null, new View.DragShadowBuilder(fab), null, DragEvent.ACTION_DRAG_STARTED);
        fab.startDrag(null, new View.DragShadowBuilder(fab), null, 0);
//        fab.setOnKeyListener((view, i, keyEvent) -> {
//            keyEvent.getAction() == KeyEvent.ACTION_DOWN
//        });
    }

//    @OnTouch(R.id.fab)
//    public void onTouchFab(View fab, MotionEvent event) {
//        Log.d(TAG, "onTouchFab: "+event.getAction());
//    }
//
//    @OnFocusChange(R.id.fab)
//    public void onFocusChangeFab(boolean hasFocus) {
//        Log.d(TAG, "onFocusChangeFab: "+hasFocus);
//    }

    private void loadSource() {
        // load from database
        source = Source.findDefault();
        if (source == null) {
            source = Source.saveDefault();
        }
        Log.d(TAG, "loadSource: "+source.getJson());

        Function function = SourceJson.getGson().fromJson(source.getJson(), Function.class);

        Log.d(TAG, "loadSource: "+function);

        toolbar.setTitle(function.getName());

        adapter.setList(function.getList());
        adapter.notifyDataSetChanged();

        // name to manager
        NameSpaceManager.getInstance().load(function);
    }

}
