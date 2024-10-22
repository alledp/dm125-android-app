// Generated by view binder compiler. Do not edit!
package com.aduilio.mytasks.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.aduilio.mytasks.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityLoginBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button btCreateAccount;

  @NonNull
  public final Button btLogin;

  @NonNull
  public final TextInputEditText etEmail;

  @NonNull
  public final TextInputEditText etPassword;

  @NonNull
  public final TextInputLayout tilEmail;

  @NonNull
  public final TextInputLayout tilPassword;

  private ActivityLoginBinding(@NonNull ConstraintLayout rootView, @NonNull Button btCreateAccount,
      @NonNull Button btLogin, @NonNull TextInputEditText etEmail,
      @NonNull TextInputEditText etPassword, @NonNull TextInputLayout tilEmail,
      @NonNull TextInputLayout tilPassword) {
    this.rootView = rootView;
    this.btCreateAccount = btCreateAccount;
    this.btLogin = btLogin;
    this.etEmail = etEmail;
    this.etPassword = etPassword;
    this.tilEmail = tilEmail;
    this.tilPassword = tilPassword;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityLoginBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityLoginBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_login, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityLoginBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btCreateAccount;
      Button btCreateAccount = ViewBindings.findChildViewById(rootView, id);
      if (btCreateAccount == null) {
        break missingId;
      }

      id = R.id.btLogin;
      Button btLogin = ViewBindings.findChildViewById(rootView, id);
      if (btLogin == null) {
        break missingId;
      }

      id = R.id.etEmail;
      TextInputEditText etEmail = ViewBindings.findChildViewById(rootView, id);
      if (etEmail == null) {
        break missingId;
      }

      id = R.id.etPassword;
      TextInputEditText etPassword = ViewBindings.findChildViewById(rootView, id);
      if (etPassword == null) {
        break missingId;
      }

      id = R.id.tilEmail;
      TextInputLayout tilEmail = ViewBindings.findChildViewById(rootView, id);
      if (tilEmail == null) {
        break missingId;
      }

      id = R.id.tilPassword;
      TextInputLayout tilPassword = ViewBindings.findChildViewById(rootView, id);
      if (tilPassword == null) {
        break missingId;
      }

      return new ActivityLoginBinding((ConstraintLayout) rootView, btCreateAccount, btLogin,
          etEmail, etPassword, tilEmail, tilPassword);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}