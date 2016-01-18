package ru.footmade.hazel.pref;

import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * Saved root URL
 */
@SharedPref
public interface RootUrlPref {
    @DefaultString("http://abcd1234.ngrok.io/mount") String rootUrl();
}
