package cn.j1angvei.cnbetareader.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.j1angvei.cnbetareader.R;
import cn.j1angvei.cnbetareader.contract.CommentsContract;

/**
 * Created by Wayne on 2016/8/12.
 */
public class PublishCommentDialog extends DialogFragment {
    private static final String IS_ADD = "PublishCommentDialog.is_publish";
    private static final String QUOTE = "PublishCommentDialog.quote";
    @BindView(R.id.tv_add_cmt_hint)
    TextView tvHint;
    @BindView(R.id.tv_add_cmt_quote)
    TextView tvQuote;
    @BindView(R.id.et_add_cmt_content)
    EditText etContent;
    @BindView(R.id.et_add_cmt_captcha)
    EditText etCaptcha;
    @BindView(R.id.iv_add_cmt_captcha)
    ImageView ivCaptcha;
    @BindView(R.id.btn_add_cmt_send)
    Button btnSend;
    boolean mIsAdd;
    String mQuote;
    CommentsContract.View mView;

    public static PublishCommentDialog newInstance(boolean isAdd, String quote) {
        PublishCommentDialog dialog = new PublishCommentDialog();
        Bundle args = new Bundle();
        args.putBoolean(IS_ADD, isAdd);
        args.putString(QUOTE, quote);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mView = (CommentsContract.View) getActivity();
        mIsAdd = getArguments().getBoolean(IS_ADD);
        mQuote = getArguments().getString(QUOTE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_comment_publish, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        getDialog().setTitle(mIsAdd ? R.string.title_publish_cmt_add : R.string.title_publish_cmt_reply);
        etCaptcha.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }
}
