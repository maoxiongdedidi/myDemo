package com.example.ding.testble.callback;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by ding on 2016/11/1.
 */
public interface EditTextListener extends TextWatcher {

    @Override
    void afterTextChanged(Editable s);
}
