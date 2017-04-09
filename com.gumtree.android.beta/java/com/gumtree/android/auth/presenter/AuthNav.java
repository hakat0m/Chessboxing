package com.gumtree.android.auth.presenter;

public interface AuthNav<A> {
    void onLoginClicked();

    void onRegistrationClicked(A a);
}
