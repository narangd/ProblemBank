package person.sykim.problembank.data;

import com.pixplicity.easyprefs.library.Prefs;

public enum Preference {
    BANK,
    TRY_LOGIN,
    UUID,
    ;

    public void save(String value) {
        Prefs.putString(name(), value);
    }
    public void save(boolean value) {
        Prefs.putBoolean(name(), value);
    }

    public String string() {
        return Prefs.getString(name(), null);
    }
    public boolean bool() {
        return Prefs.getBoolean(name(), false);
    }
    public boolean bool(boolean defaultValue) {
        return Prefs.getBoolean(name(), defaultValue);
    }
}
