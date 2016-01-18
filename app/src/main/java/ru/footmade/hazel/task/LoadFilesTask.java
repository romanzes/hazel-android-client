package ru.footmade.hazel.task;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.web.client.RestClientException;

import java.util.List;

import ru.footmade.hazel.model.FileInfo;
import ru.footmade.hazel.activity.ExplorerActivity;
import ru.footmade.hazel.net.MyRestClient;

/**
 * Loads folder contents in background
 */
@EBean
public class LoadFilesTask {
    @RootContext ExplorerActivity activity;

    @RestService MyRestClient client;

    @Background
    public void loadItems(String path) {
        try {
            List<FileInfo> files = client.getDirectoryContents(path);
            if (!path.isEmpty()) {
                files.add(0, new FileInfo("..", FileInfo.FileType.DIRECTORY));
            }
            updateUI(files);
        } catch (RestClientException ex) {
            activity.showError(ex);
        }
    }

    public String getRootUrl() {
        return client.getRootUrl();
    }

    public void setRootUrl(String url) {
        client.setRootUrl(url);
    }

    @UiThread
    void updateUI(List<FileInfo> files) {
        activity.bindAdapter(files);
    }
}
