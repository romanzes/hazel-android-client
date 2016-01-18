package ru.footmade.hazel.activity;

import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.EditorAction;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import ru.footmade.hazel.R;
import ru.footmade.hazel.pref.RootUrlPref_;

/**
 * Start form with URL field
 */
@EActivity(R.layout.activity_enter)
public class EnterActivity extends AppCompatActivity {
    public static final String PREFIX = "http://";
    public static final String POSTFIX = ".ngrok.io/mount";

    @Pref RootUrlPref_ rootUrlPref;

    @ViewById EditText editUrl;

    @AfterViews
    public void init() {
        String rootUrl = rootUrlPref.rootUrl().get();
        editUrl.setText(rootUrl);
        if (rootUrl.startsWith(PREFIX) && rootUrl.endsWith(POSTFIX)) {
            editUrl.setSelection(PREFIX.length(), rootUrl.length() - POSTFIX.length());
        } else {
            editUrl.setSelection(rootUrl.length());
        }
    }

    @Click
    public void btnStartClicked() {
        rootUrlPref.edit().rootUrl().put(editUrl.getText().toString()).apply();
        ExplorerActivity_.intent(this).rootUrl(editUrl.getText().toString()).start();
    }

    @EditorAction(R.id.editUrl)
    void handleDoneButton(TextView tv, int actionId, KeyEvent event) {
        if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
            btnStartClicked();
        }
    }
}
