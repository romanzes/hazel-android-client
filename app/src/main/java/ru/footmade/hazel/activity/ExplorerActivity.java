package ru.footmade.hazel.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.kennyc.view.MultiStateView;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.NonConfigurationInstance;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.springframework.web.client.RestClientException;

import java.util.List;

import ru.footmade.hazel.model.FileInfo;
import ru.footmade.hazel.adapter.FileListAdapter;
import ru.footmade.hazel.task.LoadFilesTask;
import ru.footmade.hazel.R;

/**
 * File browser activity
 */
@EActivity(R.layout.activity_main)
public class ExplorerActivity extends AppCompatActivity {

    @Extra String rootUrl;

    @NonConfigurationInstance @Bean LoadFilesTask loadFilesTask;

    @ViewById MultiStateView multiStateView;
    @ViewById ListView listFiles;

    @Bean FileListAdapter adapter;

    @InstanceState String currentPath;

    @AfterInject
    void afterInject() {
        loadFilesTask.setRootUrl(rootUrl);
    }

    @AfterViews
    void startLoading() {
        if (currentPath == null) {
            currentPath = "";
        }
        loadContents();
    }

    public void bindAdapter(List<FileInfo> files) {
        adapter.setItems(files);
        listFiles.setAdapter(adapter);
        multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
    }

    @ItemClick
    void listFilesItemClicked(FileInfo file) {
        if (file.isDirectory()) {
            cd(file.getName());
        } else if (file.isFile()) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            String url = loadFilesTask.getRootUrl() + "/" + currentPath + file.getName();
            intent.setDataAndType(Uri.parse(url), MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(url)));
            startActivity(intent);
        }
    }

    private void cd(String directory) {
        if (directory.equals("..")) {
            int lastSlash = currentPath.lastIndexOf("/");
            int lastButOneSlash = currentPath.lastIndexOf("/", lastSlash - 1);
            if (lastButOneSlash == -1) {
                currentPath = "";
            } else {
                currentPath = currentPath.substring(0, lastButOneSlash + 1);
            }
        } else {
            currentPath += directory + "/";
        }
        loadContents();
    }

    private void loadContents() {
        multiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
        loadFilesTask.loadItems(currentPath);
    }

    @UiThread
    public void showError(RestClientException ex) {
        View errorView = multiStateView.getView(MultiStateView.VIEW_STATE_ERROR);
        if (errorView != null) {
            TextView tvError = (TextView) errorView.findViewById(R.id.tvError);
            tvError.setText(getString(R.string.error, ex.getMessage()));
            Button tryAgain = (Button) errorView.findViewById(R.id.btnTryAgain);
            tryAgain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loadContents();
                }
            });
        }
        multiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
    }
}
