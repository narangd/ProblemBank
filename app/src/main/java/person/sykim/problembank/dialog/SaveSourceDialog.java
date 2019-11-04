package person.sykim.problembank.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonStreamParser;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import person.sykim.problembank.R;
import person.sykim.problembank.data.Source;
import sykim.person.editor.Function;
import sykim.person.editor.SourceJson;

public class SaveSourceDialog extends ViewDialog {

    @BindView(R.id.source_name_layout)
    TextInputLayout nameLayout;
    @BindView(R.id.source_name_edit_text)
    EditText nameEditText;
    @BindView(R.id.source_json_text_view)
    TextView jsonTextView;

    private Source source;

    private OnCommitListener listener;

    public SaveSourceDialog(Context context) {
        super(context, R.layout.dialog_save_source);
        builder.setTitle("소스코드를 다른 이름으로 저장");
    }

    @Override
    protected void bind(View root) {
        ButterKnife.bind(this, root);
    }

    @Override
    protected boolean tryCommit() {
        boolean exist = Source.existName(nameValue());
        if (exist) {
            nameLayout.setError("이미 같은 제목을 가지는 소스코드가 있습니다.");
        } else {
            nameLayout.setError(null);
        }
        return !exist;
    }

    @Override
    protected void commit() {
        source.setUpdateTime(null);
        source.setName(nameValue());
        source.setId( source.save() );
        listener.onCommit(source);
    }

    public SaveSourceDialog setSource(String name, Function function, @NonNull OnCommitListener listener) {
        source = new Source();
        source.setName(name);
        source.setJson(new Gson().toJson(function));

        nameEditText.setText(name);
        jsonTextView.setText(SourceJson.getGsonPretty().toJson(function));

        this.listener = listener;

        return this;
    }

    private String nameValue() {
         Editable value = nameEditText.getText();
         return value != null ? value.toString() : "";
    }

    public interface OnCommitListener {
        void onCommit(Source source);
    }
}
