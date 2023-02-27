package com.example.demofss;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//C.Pruvost
@RestController  
public class HelloWorldController   {
	
	//private String directoryPathString = "C:/Temp/Fss";
	private String directoryPathString = "/mnt/MyFss";
	
	@Autowired
	private Environment environment;
	
	private String host; 
	
	public HelloWorldController() {
		super();
	}

	@GetMapping("/hello")  
	public String hello()   
	{  
	return "Hello World";  
	}  
	
	@GetMapping("/createfile")  
	public String createFile()   
	{  
		Random r = new Random();
		int low = 1;
		int high = 1000000;
		int result = r.nextInt(high-low) + low;
		
		File file = new File(directoryPathString + "/JavaFile" + result + ".java");

	    try {

	      // create a new file with name specified
	      // by the file object
	      boolean value = file.createNewFile();
	      if (value) {
	        System.out.println("New Java File is created.");
	        return (host + " : OK");
	      }
	      else {
	        System.out.println("The file already exists.");
	        return (host + " : NOK");
	      }
	    }
	    catch(Exception e) {
	      e.getStackTrace();
	      return (host + " : NOK");
	    }
	} 
	
	@GetMapping("/totalfiles")  
	public int getnumberOfFiles() {
		int result = 0;
		
		File file = new File (directoryPathString);
	    
	    if (!file.exists ()) {
	    	result = -1;
	    }    
	    else  {  
	        result = file.list ().length;
	    }
		
		return result;
	}
	
	@GetMapping("/listfiles") 
	public String showFiles() {
		Set<String> resultSet = null;
		
		try {
			resultSet = this.listFilesUsingFilesList(directoryPathString);
			if (host == null) return(Arrays.toString(resultSet.toArray()));
			else return(host.concat(Arrays.toString(resultSet.toArray())));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ("Error : " + e.getLocalizedMessage());
		}
	}
	
	private Set<String> listFilesUsingFilesList(String dir) throws IOException {
	    try (Stream<Path> stream = Files.list(Paths.get(dir))) {
	        return stream
	          .filter(file -> !Files.isDirectory(file))
	          .map(Path::getFileName)
	          .map(Path::toString)
	          .collect(Collectors.toSet());
	    }
	}
	
	@PostConstruct
	private void postConstruct() {
	    this.host = environment.getProperty("HOSTNAME");
	}
}  
