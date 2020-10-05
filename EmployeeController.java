package com.JpaEx1_example.demo.Controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.JpaEx1_example.demo.model.EmployeeRyth;
import com.JpaEx1_example.demo.model.Image;
import com.JpaEx1_example.demo.repository.EmployeeRepository;
import com.JpaEx1_example.demo.repository.ImageRepository;

@RestController
public class EmployeeController {
	
	@Autowired
	private EmployeeRepository emprepo;
	@Autowired
	private ImageRepository imgrepo;
	@PostMapping(value="/save")
	private String saveEmp(@RequestBody EmployeeRyth emp)
	{
		emprepo.save(emp);
		return "Employer added";
	}
	@GetMapping(value="/getAllEmp")
	private List<EmployeeRyth> getemp() {
	return emprepo.findAll();
	
	}
	@GetMapping(value="/getAllEmpById/{id}")
	private Optional<EmployeeRyth> getemp(@PathVariable Integer id) {
		
	return emprepo.findById(id);
	
	}
	@DeleteMapping(value="/delete/{id}")
	private String deleteEmp(@PathVariable Integer id) {
		
	 emprepo.deleteById(id);
	 return"deleted ";
	
	
	}
	@GetMapping(value="getEmp/{firstName}/{lastName}")
	private List<EmployeeRyth> getEmpByfirstNameAndlastName(@PathVariable String firstName,@PathVariable String lastName)
	{
		return emprepo.findByFirstNameAndLastName(firstName, lastName);
		
	}
	
	
	@PutMapping(value="/updateEmp")
	 public String updateEmp(@RequestBody EmployeeRyth emp)
	 {
		 Optional<EmployeeRyth> emp1=emprepo.findById(emp.getId());
		 if(emp1.isPresent())
		 {
			 emp1.get().setCity(emp.getCity());
			 emp1.get().setSalary(emp.getSalary());
		 }
		 emprepo.save(emp1.get());
		return "updated successfully";
	 }
	
	@PostMapping("/insertImage")
    public String insertImage(@RequestParam("file") MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Image image = new Image();
        try {
            image.setData(file.getBytes());
            image.setFileName(fileName);
            image.setFileType(file.getContentType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        imgrepo.save(image);
        return "Image inserted";
    }
   
     @GetMapping("/retrive/{fileId}")
        public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable Integer fileId) {
            // Load file from database
             Optional<Image> image;
            image = imgrepo.findById(fileId);
            Image ima=null;
            if(image.isPresent()) {
                ima = image.get();
            }
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(ima.getFileType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + ima.getFileName() + "\"")
                    .body(new ByteArrayResource(ima.getData()));
	
	
     }
}
