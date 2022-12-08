package com.apis.okre.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.apis.okre.entity.*;
import com.apis.okre.mapper.*;
import com.apis.okre.service.*;
import com.apis.okre.util.JwtTokenUtil;

import com.apis.okre.exception.*;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileMapper mMapper;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public Path createOperatorDir() {
        Path fileStorageLocation;
        fileStorageLocation = Paths
                .get(uploadDir + "/"
                        + jwtTokenUtil.getUserDetailFromTokenPhone().user_post_address.replaceAll(".*:(.*)@.*", "$1"))
                .toAbsolutePath().normalize();
        try {
            if (!Files.exists(fileStorageLocation)) {
                Files.createDirectories(fileStorageLocation);
            }
            return fileStorageLocation;
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.",
                    ex);
        }
    }

    @Override
    public TFile storeFile(MultipartFile file, Path pPath) {
        // Normalize file name
        String orgName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileName = StringUtils.cleanPath(UUID.randomUUID().toString());

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = pPath.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            TFile mFile = new TFile();
            mFile.file_name = orgName;
            mFile.file_uri = fileName;
            mFile.file_owner = jwtTokenUtil.getUserDetailFromTokenPhone().id;
            mFile.file_type = file.getContentType();
            mFile.file_size = file.getSize();
            mMapper.addOne(mFile);

            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/file/downloadFile/")
                    .path(mFile.file_id.toString()).toUriString();
            mFile.file_downUri = fileDownloadUri;

            return mFile;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    @Override
    public Resource loadFileAsResource(String fileUri, Path fileStorageLocation) {
        try {
            
            Path filePath = fileStorageLocation.resolve(fileUri).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("File not found ");
            }
        } catch (MalformedURLException ex) {
            throw new FileNotFoundException("File not found ", ex);
        }
    }

    @Override
    public List<TFile> selectByFields(TFile param) {
        return mMapper.selectByFields(param);
    }

    @Override
    public Long addOne(TFile param) {
        Long result = mMapper.addOne(param);
        return result;
    }

    @Override
    public int deleteByFields(TFile param) {
        return mMapper.deleteByFields(param);
    }

    @Override
    public int updateByFields(TFile param) {
        return mMapper.updateByFields(param);
    }

}