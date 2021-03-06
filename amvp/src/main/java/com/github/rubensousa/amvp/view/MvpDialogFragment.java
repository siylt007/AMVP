/*
 * Copyright 2016 Rúben Sousa
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.rubensousa.amvp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;

import com.github.rubensousa.amvp.MvpView;
import com.github.rubensousa.amvp.MvpPresenter;
import com.github.rubensousa.amvp.delegate.MvpDelegate;
import com.github.rubensousa.amvp.delegate.MvpDelegateCallbacks;
import com.github.rubensousa.amvp.delegate.MvpDelegateImpl;


public abstract class MvpDialogFragment<V extends MvpView<P>, P extends MvpPresenter<V>>
        extends AppCompatDialogFragment implements MvpView<P>, MvpDelegateCallbacks<V, P> {

    private MvpDelegate<V, P> mDelegate;
    private P mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDelegate = new MvpDelegateImpl<>(this);
        mPresenter = mDelegate.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mDelegate.onSaveInstanceState(outState);
    }

    // onViewStateRestored isn't called for dialogs if a view isn't returned on onCreateView
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDelegate.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onStop() {
        super.onStop();
        mDelegate.detachView();
    }

    @Override
    public void onStart() {
        super.onStart();
        mDelegate.attachView();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (attachOnResumeDetachOnPause()) {
            mDelegate.detachView();
        }
        if (getActivity().isFinishing()) {
            mDelegate.destroyPresenter();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (attachOnResumeDetachOnPause()) {
            mDelegate.attachView();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mDelegate.attachView();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        mDelegate.destroyPresenter();
    }

    @Override
    public P getPresenter() {
        return mPresenter;
    }

    @Override
    public String getPresenterKey() {
        return getClass().getSimpleName();
    }

    @SuppressWarnings("unchecked")
    @Override
    public V getMvpView() {
        return (V) this;
    }

    public boolean attachOnResumeDetachOnPause() {
        return false;
    }
}
