package io.okhi.android_core.models;

import androidx.annotation.NonNull;

public class OkHiUser {
    private String phone;
    private String firstName;
    private String lastName;

    private OkHiUser(OkHiUser.Builder builder) {
        this.phone = builder.phone;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
    }

    public static class Builder {
        private String phone;
        private String firstName;
        private String lastName;
        public Builder(@NonNull String phone) {
            this.phone = phone;
        }
        public Builder withFirstName(@NonNull String firstName){
            this.firstName = firstName;
            return this;
        }
        public Builder withLastName(@NonNull String lastName){
            this.lastName = lastName;
            return this;
        }
        public OkHiUser build() {
            return new OkHiUser(this);
        }
    }

    public String getPhone() {
        return phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
