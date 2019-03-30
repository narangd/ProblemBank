package person.sykim.problembank.view.dialog;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import androidx.appcompat.app.AlertDialog;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import person.sykim.problembank.MyApplication;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProblemBankDialog {
    Context context;
    AlertDialog.Builder builder;

    public ProblemBankDialog(Context context) {
        this.context = context;
        builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setTitle("문제를 풀 은행을 선택해주세요");
    }

    public ProblemBankDialog setProblemBanks(MyApplication application, OnClickListener listener) {
        ArrayList<String> nameList = new ArrayList<>( application.bank.keySet() );
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, nameList);
        builder.setAdapter(adapter, (dialogInterface, i) -> {
            if (listener != null) {
                String key = nameList.get(i);
                listener.onClick(key);
            }
        });
        return this;
    }

    public AlertDialog show() {
        return builder.show();
    }

    public interface OnClickListener {
        void onClick(String key);
    }
}
