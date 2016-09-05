package cn.j1angvei.cbnews.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import cn.j1angvei.cbnews.R;
import cn.j1angvei.cbnews.contract.AllTopicsContract;
import cn.j1angvei.cbnews.util.StringUtil;

/**
 * Created by Wayne on 2016/8/29.
 */

public class PickLetterDialog extends BottomSheetDialogFragment {
    private static final String CUR_PAGE = "PickLetterDialog.cur_page";
    private BottomSheetBehavior mBehavior;
    private int mCurPos;

    public static PickLetterDialog newInstance(int page) {
        PickLetterDialog dialog = new PickLetterDialog();
        Bundle args = new Bundle();
        args.putInt(CUR_PAGE, page);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurPos = getArguments().getInt(CUR_PAGE) - 1;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = View.inflate(getActivity(), R.layout.dialog_pick_letter, null);
        dialog.setContentView(view);
        GridView gridView = (GridView) view.findViewById(R.id.grid_view);
        gridView.setChoiceMode(GridView.CHOICE_MODE_SINGLE);
        List<String> alphabet = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            alphabet.add(StringUtil.intToAlphabetLetter(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.item_letter, alphabet);
        gridView.setAdapter(adapter);
        gridView.setItemChecked(mCurPos, true);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((AllTopicsContract.View) getTargetFragment()).onLetterChosen(position + 1);
                dismiss();
            }
        });
        mBehavior = BottomSheetBehavior.from((View) view.getParent());
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }
}
