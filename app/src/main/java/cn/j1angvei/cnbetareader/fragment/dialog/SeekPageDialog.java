package cn.j1angvei.cnbetareader.fragment.dialog;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.j1angvei.cnbetareader.R;

/**
 * Created by Wayne on 2016/7/17.
 */
public class SeekPageDialog extends DialogFragment implements SeekBar.OnSeekBarChangeListener{
    private static final String CUR_PAGE = "SeekPageDialog.cur_page";

    @BindView(R.id.seek_bar)
    SeekBar mSeekBar;

    public static SeekPageDialog newInstance(int curPage) {
        SeekPageDialog dialog = new SeekPageDialog();
        Bundle args = new Bundle();
        args.putInt(CUR_PAGE, curPage);
        dialog.setArguments(args);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_seek_page, container);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().setTitle("Select topic index");
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
