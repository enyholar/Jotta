package com.gideondev.jotta.databases.dto;

import com.gideondev.jotta.model.SettingsModel;
import java.util.List;

public interface SettingsDTO {

    boolean addSettings(SettingsModel model);

    boolean updateSettings(SettingsModel model);

    void deleteSettings(String id);


    List<SettingsModel> getSettingsModels();


    // void onChange(long accountid);
}
