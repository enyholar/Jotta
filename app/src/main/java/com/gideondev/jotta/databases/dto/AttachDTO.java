package com.gideondev.jotta.databases.dto;

import com.gideondev.jotta.model.Attach;

import java.util.List;

public interface AttachDTO {

    boolean addAttach(Attach model);

    boolean updateAttach(Attach model);

    void deleteAttach(String id);

    List<Attach> getAllAttach();

  List<Attach> getAllAttachByNoteId(String noteID);
}
