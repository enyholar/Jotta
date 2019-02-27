package com.gideondev.jotta.databases.dto;

import com.gideondev.jotta.model.FolderModel;
import java.util.List;

public interface FolderDTO {

    boolean addFolder(FolderModel model);

    boolean updateFolder(FolderModel model);

    void deleteFolder(String id);


    List<FolderModel> getFolderModels();

   // void onChange(long accountid);
}
