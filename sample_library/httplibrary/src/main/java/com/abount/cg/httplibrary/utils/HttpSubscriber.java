package com.abount.cg.httplibrary.utils;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.abount.cg.commonlibrary.AcgCommon;
import com.abount.cg.commonlibrary.utils.ToastUtil;
import com.abount.cg.commonlibrary.views.wigdets.EmptyView;
import com.google.gson.JsonSyntaxException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Collection;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * Created by mo_yu on 2018/3/22.
 * Http模块专用的Rx Subscriber
 * 对加载条,错误信息和返回空数据作了基本的处理
 */
public class HttpSubscriber<T> extends Subscriber<T> {

    public static final String TAG = HttpSubscriber.class.getSimpleName();
    private Context mContext;
    private Dialog progressDialog;
    private View progressBar;
    private SubscriberOnNextListener onNextListener;
    private SubscriberOnErrorListener onErrorListener;
    private SubscriberOnCompletedListener onCompletedListener;
    private EmptyView emptyView;
    private ListView listView;
    private View contentView;
    private boolean dataNullable = false;
    private String errorMsg;
    private boolean isToastHidden;

    private HttpSubscriber() {

    }

    @Override
    public void onStart() {
        super.onStart();
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
        if (progressDialog != null) {
            progressDialog.show();
        }
    }

    @Override
    public void onCompleted() {
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
        if (progressDialog != null) {
            try {
                progressDialog.cancel();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (onCompletedListener != null) {
            onCompletedListener.onCompleted();
        }
    }

    @Override
    public void onError(Throwable e) {
        try {
            showEmptyView();

            // 错误信息处理和显示
            String msg = null;
            if (e instanceof SocketTimeoutException || e instanceof UnknownHostException) {
                msg = "网络中断，请检查您的网络状态";
            } else if (e instanceof ConnectException) {
                msg = "网络中断，请检查您的网络状态";
            } else if (e instanceof HttpException) {
                if (TextUtils.isEmpty(errorMsg)) {
                    msg = e.getMessage();
                }
            } else if (e instanceof JsonSyntaxException) {
                if (TextUtils.isEmpty(errorMsg)) {
                    msg = "数据解析错误";
                }
            } else {
                if (TextUtils.isEmpty(errorMsg)) {
                    msg = e.getMessage();
                }
            }

            if (TextUtils.isEmpty(msg) && !TextUtils.isEmpty(errorMsg)) {
                msg = errorMsg;
            }
            if (!isToastHidden) {
                ToastUtil.showToast(mContext, msg, 0);
            }
            e.printStackTrace();

            if (onErrorListener != null) {
                onErrorListener.onError(e);
            }

            if (progressBar != null) {
                progressBar.setVisibility(View.GONE);
            }
            if (progressDialog != null) {
                progressDialog.cancel();
            }
        } catch (Exception e2) {
            if (AcgCommon.DEBUG) {
                throw e2;
            } else {
                e2.printStackTrace();
            }
        }
    }

    @Override
    public void onNext(T t) {
        if (!dataNullable && isDataEmpty(t)) {
            Log.d("pagination tool", "on empty data");
            showEmptyView();
        } else if (onNextListener != null) {
            Log.d("pagination tool", "on data");
            if (contentView != null) {
                contentView.setVisibility(View.VISIBLE);
            }
            if (emptyView != null) {
                emptyView.hideEmptyView();
            }

            onNextListener.onNext(t);
        }
    }

    public void setOnErrorListener(SubscriberOnErrorListener onErrorListener) {
        this.onErrorListener = onErrorListener;
    }

    /**
     * 判断返回数据是否是空的
     *
     * @param data
     * @return
     */
    private boolean isDataEmpty(T data) {
        if (data == null) {
            return true;
        }
        if (data instanceof Collection) {
            Collection collection = (Collection) data;
            return collection.isEmpty();
        }
        return false;
    }

    /**
     * 如果有设置list view或者content view则显示空页面
     */
    private void showEmptyView() {
        if (emptyView == null) {
            return;
        }
        if (listView != null) {
            emptyView.showEmptyView();
            listView.setEmptyView(emptyView);
        }
        if (contentView != null) {
            contentView.setVisibility(View.GONE);
            emptyView.showEmptyView();
        }
    }

    public static <T> Builder<T> buildSubscriber(Context context) {
        return new Builder<>(context);
    }

    public static class Builder<T> {
        private Context mContext;
        private View progressBar;
        private Dialog progressDialog;
        private SubscriberOnNextListener onNextListener;
        private SubscriberOnErrorListener onErrorListener;
        private SubscriberOnCompletedListener onCompletedListener;
        private EmptyView emptyView;
        private ListView listView;
        private View contentView;
        private boolean dataNullable;
        private String errorMsg;
        private boolean isToastHidden;

        public Builder(Context context) {
            this.mContext = context;
        }

        public Builder setErrorMsg(String errorMsg) {
            this.errorMsg = errorMsg;
            return this;
        }

        /**
         * 设置是否可以允许空数据,即空数据的时候不进入空数据emptyview处理
         *
         * @param dataNullable
         * @return
         */
        public Builder setDataNullable(boolean dataNullable) {
            this.dataNullable = dataNullable;
            return this;
        }

        public Builder setProgressBar(View progressBar) {
            this.progressBar = progressBar;
            return this;
        }

        public Builder setProgressDialog(Dialog dialog) {
            this.progressDialog = dialog;
            return this;
        }

        public Builder setListView(ListView l) {
            this.listView = l;
            return this;
        }

        public Builder setContentView(View c) {
            this.contentView = c;
            return this;
        }

        public Builder setOnNextListener(SubscriberOnNextListener n) {
            this.onNextListener = n;
            return this;
        }

        public Builder setOnErrorListener(SubscriberOnErrorListener e) {
            this.onErrorListener = e;
            return this;
        }

        public Builder setOnCompletedListener(SubscriberOnCompletedListener c) {
            this.onCompletedListener = c;
            return this;
        }

        public Builder toastHidden() {
            this.isToastHidden = true;
            return this;
        }

        public HttpSubscriber build() {
            HttpSubscriber subscriber = new HttpSubscriber();
            subscriber.mContext = this.mContext;
            subscriber.progressBar = this.progressBar;
            subscriber.progressDialog = this.progressDialog;
            subscriber.contentView = this.contentView;
            subscriber.emptyView = this.emptyView;
            subscriber.onErrorListener = this.onErrorListener;
            subscriber.onNextListener = this.onNextListener;
            subscriber.onCompletedListener = this.onCompletedListener;
            subscriber.listView = this.listView;
            subscriber.dataNullable = this.dataNullable;
            subscriber.errorMsg = this.errorMsg;
            subscriber.isToastHidden = this.isToastHidden;
            return subscriber;
        }
    }


}
