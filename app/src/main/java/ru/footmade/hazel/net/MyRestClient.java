package ru.footmade.hazel.net;

import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Rest;
import org.androidannotations.rest.spring.api.RestClientErrorHandling;
import org.androidannotations.rest.spring.api.RestClientRootUrl;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

import java.util.List;

import ru.footmade.hazel.model.FileInfo;

/**
 * Http client
 */
@Rest(converters = { GsonHttpMessageConverter.class })
public interface MyRestClient extends RestClientRootUrl, RestClientErrorHandling {
    @Get("/{path}") List<FileInfo> getDirectoryContents(@Path String path);
}
