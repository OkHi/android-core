package io.okhi.android_core;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Before;
import org.junit.Test;

import io.okhi.android_core.models.OkHiUser;

public class OkhiUserTest {

    OkHiUser user;
    OkHiUser.Builder buider;
    private String phone = "254718201";
    private String firstName = "Granson";
    private String lastName = "Oyombe";
    private String id = "okhi_id";
    private String email = "oyombegranson@gmail.com";
    private String fcmPushNotificationToken = "54678tryuivbn76jhkmn,n8";

    @Before
    public void set_up_user(){
        buider = new OkHiUser.Builder(phone);
        buider.withOkHiUserId(id);
        buider.withEmail(email);
        buider.withFirstName(firstName);
        buider.withLastName(lastName);
        buider.withFcmPushNotificationToken(fcmPushNotificationToken);
        user = buider.build();
    }

    @Test
    public void test_get_phone(){
        assertThat(user.getPhone()).isEqualTo(phone);
    }

    @Test
    public void test_get_first_name(){
        assertThat(user.getFirstName()).isEqualTo(firstName);
    }

    @Test
    public void test_get_last_name(){
        assertThat(user.getLastName()).isEqualTo(lastName);
    }
    @Test
    public void test_get_id(){
        assertThat(user.getId()).isEqualTo(id);
    }
    @Test
    public void test_get_email(){
        assertThat(user.getEmail()).isEqualTo(email);
    }
    @Test
    public void test_get_push_notification_token(){
        assertThat(user.getFcmPushNotificationToken()).isEqualTo(fcmPushNotificationToken);
    }
}
