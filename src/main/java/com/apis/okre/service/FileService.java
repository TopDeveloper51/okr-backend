package com.apis.okre.service;

import java.nio.file.Path;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.apis.okre.entity.*;

public interface FileService {

    public List<TFile> selectByFields(TFile param);
    
    public Long addOne(TFile param);
        
    public int deleteByFields(TFile param);
    
    public int updateByFields(TFile param);
    
    public Path createOperatorDir();
    
    public TFile storeFile(MultipartFile file, Path pPath);
    
    public Resource loadFileAsResource(String fileUri, Path fileStorageLocation) ;
    
}