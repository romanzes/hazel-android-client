package ru.footmade.hazel.model;

import com.google.gson.annotations.SerializedName;

/**
 * Folder contents item
 */
public class FileInfo {
    private @SerializedName("file_name") String name;
    private @SerializedName("file_type") FileType fileType;

    public FileInfo(String name, FileType fileType) {
        this.name = name;
        this.fileType = fileType;
    }

    public String getName() {
        return name;
    }

    public boolean isFile() {
        return fileType == FileType.FILE;
    }

    public boolean isDirectory() {
        return fileType == FileType.DIRECTORY;
    }

    public enum FileType {
        @SerializedName("File") FILE,
        @SerializedName("Dir") DIRECTORY,
        @SerializedName("Symlink") SYMLINK
    }
}
