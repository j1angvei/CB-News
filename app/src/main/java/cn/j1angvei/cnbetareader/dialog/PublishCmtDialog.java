package cn.j1angvei.cnbetareader.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.j1angvei.cnbetareader.R;
import cn.j1angvei.cnbetareader.activity.BaseActivity;
import cn.j1angvei.cnbetareader.contract.PublishCmtContract;
import cn.j1angvei.cnbetareader.di.component.ActivityComponent;
import cn.j1angvei.cnbetareader.di.module.FragmentModule;
import cn.j1angvei.cnbetareader.presenter.PublishCmtPresenter;
import cn.j1angvei.cnbetareader.util.MessageUtil;

/**
 * Created by Wayne on 2016/8/26.
 */

public class PublishCmtDialog extends DialogFragment implements BaseDialog, PublishCmtContract.View {
    private static final String TAG = "PublishCmtDialog";
    private static final String IS_ADD = "PublishCmtDialog.is_add";
    private static final String QUOTE = "PublishCmtDialog.quote";
    private static final String SID = "PublishCmtDialog.sid";
    private static final String PID = "PublishCmtDialog.pid";
    @BindView(R.id.til_publish_cmt_quote)
    TextInputLayout tilQuote;
    @BindView(R.id.tv_publish_cmt_quote)
    TextInputEditText etQuote;
    @BindView(R.id.et_publish_cmt_content)
    TextInputEditText etContent;
    @BindView(R.id.et_publish_cmt_captcha)
    TextInputEditText etCaptcha;
    @BindView(R.id.iv_publish_cmt_captcha)
    ImageView ivCaptcha;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @Inject
    PublishCmtPresenter mPresenter;
    private boolean mIsAdd;
    private String mQuote;
    private String mPid;
    private String mSid;
    private Context mContext;

    public static PublishCmtDialog newInstance(boolean isAdd, String quote, String sid, String pid) {
        PublishCmtDialog dialog = new PublishCmtDialog();
        Bundle args = new Bundle();
        args.putBoolean(IS_ADD, isAdd);
        args.putString(QUOTE, quote);
        args.putString(SID, sid);
        args.putString(PID, pid);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIsAdd = getArguments().getBoolean(IS_ADD);
        mQuote = getArguments().getString(QUOTE);
        mPid = getArguments().getString(PID);
        mSid = getArguments().getString(SID);
        inject(((BaseActivity) getActivity()).getActivityComponent());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_publish_cmt, null);
        initView(view);
        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setTitle(mIsAdd ? R.string.title_publish_cmt : R.string.title_reply_cmt)
                .setView(view)
                .setPositiveButton(R.string.action_send, null)
                .setNegativeButton(R.string.action_cancel, null)
                .create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button send = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String content = etContent.getText().toString();
                        String captcha = etCaptcha.getText().toString();
                        if (TextUtils.isEmpty(content)) {
                            MessageUtil.toast(R.string.error_no_comment, view.getContext());
                        } else if (TextUtils.isEmpty(captcha)) {
                            MessageUtil.toast(R.string.error_no_captcha, view.getContext());
                        } else {
                            mPresenter.sendComment(etContent.getText().toString(), etCaptcha.getText().toString(), mSid, mPid);
                        }
                    }
                });
            }
        });
        return alertDialog;
    }

    @Override
    public void initView(View view) {
        ButterKnife.bind(this, view);
        mPresenter.setView(this);
        mPresenter.getCaptchaImage(mSid);
        //comment input area
        etContent.requestFocus();
        //quote content
        tilQuote.setHint(getString(mIsAdd ? R.string.header_publish_cmt_news_title : R.string.header_publish_cmt_origin_cmt));
        etQuote.setText(mQuote);
        etQuote.setKeyListener(null);
        //set captcha image
        ivCaptcha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivCaptcha.setImageDrawable(null);
                mPresenter.getCaptchaImage(mSid);
            }
        });
    }

    @Override
    public void showCaptcha(Bitmap bitmap) {
        ivCaptcha.setImageBitmap(bitmap);
    }

    @Override
    public void onShowFail() {
        MessageUtil.toast(R.string.info_get_captcha_fail, mContext);
    }

    @Override
    public void onSendSuccess(String message) {
        dismiss();
        if (TextUtils.equals("comment_ok", message)) {
            MessageUtil.toast(R.string.info_cmt_success, mContext);
        } else {
            MessageUtil.toast(message, mContext);
        }
    }

    @Override
    public void onSendFail() {
        MessageUtil.toast(R.string.info_cmt_fail, mContext);
    }

    @Override
    public void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public Context getViewContext() {
        return mContext;
    }

    @Override
    public void inject(ActivityComponent component) {
        component.fragmentComponent(new FragmentModule(this)).inject(this);
    }

}
