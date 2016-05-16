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

package com.github.rubensousa.samplenetwork.ui.general;


import com.github.rubensousa.amvp.MvpView;


public interface Base {

    interface View<P extends Presenter> extends MvpView<P> {
        void showProgressDialog();

        void hideProgressDialog();
    }

    interface Presenter<V extends View, I extends Interactor>
            extends com.github.rubensousa.amvp.Presenter<V> {
        I createInteractor();

        I getInteractor();
    }

    interface Interactor<P extends Presenter> {

        void setViewAttached(boolean attached);

        void setPresenter(P presenter);

        P getPresenter();

        void cancelRequest(String key);

        void attachRetrofitRequest(String key, RetrofitRequest request);

        boolean isRequestPending(String key);

        boolean isRequestFinished(String key);
    }
}
