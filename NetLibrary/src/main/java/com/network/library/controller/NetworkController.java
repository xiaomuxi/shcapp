package com.network.library.controller;


import com.network.library.Model.NetworkModel;
import com.network.library.bean.BaiduOauthEntity;
import com.network.library.bean.WeatherEntity;
import com.network.library.callback.CallBack;
import com.network.library.view.BaiduOauthView;
import com.network.library.view.BaseNetView;
import com.network.library.view.GetWeatherView;
import com.network.library.view.LoginView;

public class NetworkController<V extends BaseNetView> {

    // 持有 MVP 中 View 的引用
    private V iMvpView;


    public NetworkController() {
    }

    public void attachView(V view) {
        this.iMvpView = view;
    }

    public void detachView() {
        this.iMvpView = null;
    }

    public boolean isViewAttached() {
        return iMvpView != null;
    }

    public V getView() {
        return iMvpView;
    }

    /**
     * 获取天气
     *
     * @param city 城市
     */

    public void getWeather(final String city) {
        if (!isViewAttached()) {
            return;
        }
        NetworkModel.getWeather(city, new CallBack<WeatherEntity>() {
            @Override
            public void onStart() {
                if (isViewAttached())
                    iMvpView.showLoading();
            }

            @Override
            public void onComplete() {
                if (isViewAttached())
                    iMvpView.hideLoading();
            }

            @Override
            public void onError(String msg) {
                if (isViewAttached())
                    iMvpView.onRequestError(msg, "getWeather");
            }

            @Override
            public void onSuccess(WeatherEntity data) {
                if (isViewAttached())
                    ((GetWeatherView) iMvpView).onGetWeatherSuccess(data);
            }
        });
    }

    public void baiduOauth(final String client_id, String client_secret, boolean showLoading) {
        if (!isViewAttached()) {
            return;
        }
        NetworkModel.baiduOauth(client_id, client_secret, new CallBack<BaiduOauthEntity>() {
            @Override
            public void onStart() {
                if (isViewAttached() && showLoading)
                    iMvpView.showLoading();
            }

            @Override
            public void onComplete() {
                if (isViewAttached())
                    iMvpView.hideLoading();
            }

            @Override
            public void onError(String msg) {
                if (isViewAttached())
                    iMvpView.onRequestError(msg, "baiduOauth");
            }

            @Override
            public void onSuccess(BaiduOauthEntity data) {
                if (isViewAttached())
                    ((BaiduOauthView) iMvpView).onBaiduOauthSuccess(data);
            }
        });
    }

    public void login(final String user, String password, boolean showLoading) {
        if (!isViewAttached()) {
            return;
        }
        NetworkModel.baiduOauth(user, password, new CallBack<BaiduOauthEntity>() {
            @Override
            public void onStart() {
                if (isViewAttached() && showLoading)
                    iMvpView.showLoading();
            }

            @Override
            public void onComplete() {
                if (isViewAttached())
                    iMvpView.hideLoading();
            }

            @Override
            public void onError(String msg) {
                if (isViewAttached())
                    iMvpView.onRequestError(msg, "login");
            }

            @Override
            public void onSuccess(BaiduOauthEntity data) {
                if (isViewAttached())
                    ((LoginView) iMvpView).onLoginSuccess(data);
            }
        });
    }
}