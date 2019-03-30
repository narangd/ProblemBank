package person.sykim.problembank.data;

import com.pixplicity.easyprefs.library.Prefs;

public enum Preference {
    BANK,
    ;

    public void save(String value) {
        Prefs.edit()
                .putString(name(), value)
                .commit();
    }

    public String string() {
        return Prefs.getString(name(), null);
    }
}
