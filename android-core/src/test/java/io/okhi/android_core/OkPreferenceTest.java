package io.okhi.android_core;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import android.content.Context;
import android.content.SharedPreferences;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import io.okhi.android_core.models.Constant;
import io.okhi.android_core.models.OkHiException;
import io.okhi.android_core.models.OkPreference;

public class OkPreferenceTest {
    SharedPreferences prefs = Mockito.mock(SharedPreferences.class);
    SharedPreferences.Editor editor = Mockito.mock(SharedPreferences.Editor.class);
    Context context = Mockito.mock(Context.class);

    // Test data
    String key = "okhi:fcm:token";
    String testToken = "ewrtyuio45678rtyuiop6578tyui";
    String no_key = "no key";

    @Before
    public void before(){
        Mockito.when(context.getSharedPreferences(Constant.OKHI_PREFERENCE_NAME, Context.MODE_PRIVATE)).thenReturn(prefs);
        Mockito.when(prefs.edit()).thenReturn(editor);
    }

    @Test
    public void set_item_test() throws OkHiException {
        Mockito.when(prefs.getString(key, null)).thenReturn(testToken);
        Mockito.when(editor.putString(eq(key), anyString())).thenReturn(editor);

        new OkPreference(prefs).setItem(key, testToken);
        verify(editor).putString(key, testToken);
        verify(editor).apply();
    }

    @Test
    public void get_item_test() throws OkHiException {

        Mockito.when(prefs.getString(key, "")).thenReturn(testToken);

        String token = new OkPreference(prefs).getItem(key);
        assertThat(token).isEqualTo(testToken);
    }

    @Test
    public void get_item_return_null_test() throws OkHiException {
        Mockito.when(prefs.getString(no_key, "")).thenReturn("");

        String token = new OkPreference(prefs).getItem(no_key);
        assertThat(token).isEqualTo(null);
    }

    @Test
    public void set_item_exception_test() {
        try{
            new OkPreference(prefs).setItem(key, testToken);
        } catch (Exception e){
            assertThat(e).isInstanceOf(OkHiException.class);
        }
    }

    @Test
    public void get_item_exception_test() {
        Mockito.when(prefs.getString(no_key, null)).thenReturn(null);
        try{
            new OkPreference(prefs).getItem(key);
        } catch (Exception e){
            assertThat(e).isInstanceOf(OkHiException.class);
        }
    }
}
