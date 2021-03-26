package edu.cnm.deepdive.northstarsharing.service;

import java.io.IOException;
import org.springframework.core.io.Resource;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.multipart.MultipartFile;

public class LocalFilesystemStorageService implements StorageService{

  // TODO Implement class.

  @Override
  public String store(MultipartFile file) throws IOException, HttpMediaTypeException {
    return null;
  }

  @Override
  public Resource retrieve(String key) throws IOException {
    return null;
  }

  @Override
  public boolean delete(String key)
      throws IOException, UnsupportedOperationException, SecurityException {
    return false;
  }

}
