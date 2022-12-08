package com.apis.okre.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.apis.okre.entity.*;
import com.apis.okre.exception.FileNotFoundException;
import com.apis.okre.service.*;
import com.apis.okre.util.*;
/**
 * table: tb_file
 * @author alex
 *
 */
@RestController
@SpringBootApplication
@CrossOrigin
@RequestMapping("/file")
public class FileController {

	@Autowired
	private FileService mService;
	
	@Value("${file.upload-dir}")
    private String uploadDir;
	

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> uploadFile(@RequestParam("files") MultipartFile[] files) {
		try {
			Path mPath = mService.createOperatorDir();
			List<TFile> ret = new ArrayList<TFile>();
			for (MultipartFile file : files) {
				String fileName = file.getOriginalFilename();
				ret.add(mService.storeFile(file, mPath));
			}
			return new ResponseEntity<String>(Utils.getResponseObject(Constants.SEARCH_SUCCESSFULLY, ret).toString(),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(Utils.getResponseObject(e.getMessage()).toString(),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<?> downloadFile(@PathVariable Long fileName, HttpServletRequest request) {
		
		Path fileStorageLocation;

        TFile sFile = new TFile();
        sFile.file_id = fileName;
        List<TFile> flist = mService.selectByFields(sFile);

        if (flist.size() == 0) {
        	return new ResponseEntity<String>("File not found",
					HttpStatus.BAD_REQUEST);
        }

        fileStorageLocation = Paths.get(uploadDir + "/" + flist.get(0).file_dir.replaceAll(".*:(.*)@.*", "$1"))
                .toAbsolutePath().normalize();
        
        // Load file as Resource
        Resource resource = mService.loadFileAsResource(flist.get(0).file_uri, fileStorageLocation);

        // Try to determine file's content type
        String contentType = flist.get(0).file_type;
//        try {
//            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
//        } catch (IOException ex) {
//            System.out.println("Could not determine file type.");
//        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + flist.get(0).file_name + "\"")
                .body(resource);
    }

	@RequestMapping(value = "/getByFields", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> selectByFields(@RequestBody TFile param) {
		try {
			List<TFile> ret = mService.selectByFields(param);
			return new ResponseEntity<String>(Utils.getResponseObject(Constants.SEARCH_SUCCESSFULLY, ret).toString(),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(Utils.getResponseObject(e.getMessage()).toString(),
					HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/addOne", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> addOne(@RequestBody TFile param) {
		try {
			Long ret = mService.addOne(param);
			if (ret == 0) {
				return new ResponseEntity<String>(Utils.getResponseObject(Constants.INSERT_FAILED).toString(),
						HttpStatus.BAD_REQUEST);
			} else {
				TFile newObj = new TFile();
				newObj.file_id = param.file_id;
				List<TFile> retObj = mService.selectByFields(newObj);
				return new ResponseEntity<String>(
						Utils.getResponseObject(Constants.INSERT_SUCCESSFULLY, retObj.get(0)).toString(),
						HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(Utils.getResponseObject(e.getMessage()).toString(),
					HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/deleteByFields", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> deleteByFields(@RequestBody TFile param) {
		try {
			int ret = mService.deleteByFields(param);
			if (ret == 0) {
				return new ResponseEntity<String>(Utils.getResponseObject(Constants.DELETE_FAILED).toString(),
						HttpStatus.BAD_REQUEST);
			} else {
				return new ResponseEntity<String>(
						Utils.getResponseObject(Constants.DELETE_SUCCESSFULLY, ret).toString(), HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(Utils.getResponseObject(e.getMessage()).toString(),
					HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/updateByFields", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> updateByFields(@RequestBody TFile param) {
		try {
			int ret = mService.updateByFields(param);
			if (ret == 0) {
				return new ResponseEntity<String>(Utils.getResponseObject(Constants.UPDATE_FAILED).toString(),
						HttpStatus.BAD_REQUEST);
			} else {
				return new ResponseEntity<String>(
						Utils.getResponseObject(Constants.UPDATE_SUCCESSFULLY, ret).toString(), HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(Utils.getResponseObject(e.getMessage()).toString(),
					HttpStatus.BAD_REQUEST);
		}
	}
}
