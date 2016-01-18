package ru.footmade.hazel.adapter.view;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import ru.footmade.hazel.model.FileInfo;
import ru.footmade.hazel.R;

/**
 * Adapter item representing explorer object
 */
@EViewGroup(R.layout.item_file)
public class FileItemView extends LinearLayout {
    @ViewById ImageView ivFileType;
    @ViewById TextView tvFileName;

    public FileItemView(Context context) {
        super(context);
    }

    public void bind(FileInfo file) {
        if (file.isDirectory()) {
            ivFileType.setImageResource(R.drawable.ic_folder);
        } else {
            ivFileType.setImageResource(R.drawable.ic_music);
        }
        tvFileName.setText(file.getName());
    }
}
