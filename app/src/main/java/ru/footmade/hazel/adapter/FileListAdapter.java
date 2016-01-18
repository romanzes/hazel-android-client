package ru.footmade.hazel.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.List;

import ru.footmade.hazel.adapter.view.FileItemView;
import ru.footmade.hazel.adapter.view.FileItemView_;
import ru.footmade.hazel.model.FileInfo;

/**
 * List adapter representing folder contents
 */
@EBean
public class FileListAdapter extends BaseAdapter {

    List<FileInfo> files;

    @RootContext Context context;

    public void setItems(List<FileInfo> files) {
        this.files = files;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FileItemView personItemView;
        if (convertView == null) {
            personItemView = FileItemView_.build(context);
        } else {
            personItemView = (FileItemView) convertView;
        }

        personItemView.bind(getItem(position));

        return personItemView;
    }

    @Override
    public int getCount() {
        return files.size();
    }

    @Override
    public FileInfo getItem(int position) {
        return files.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
