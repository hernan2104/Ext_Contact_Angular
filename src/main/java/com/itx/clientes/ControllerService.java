package com.itx.clientes;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.interaxa.contactos.BulkContacts;
import com.interaxa.contactos.ClienteExport;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

import jakarta.servlet.http.HttpServletResponse;
@RestController
@CrossOrigin(origins = "*")
public class ControllerService {

	String clienteID = "3490ed10-c18e-4219-ae3d-ae64af5fa9d9";
	String clienteSecret = "6XfsLyXiXDnCPpGOR2dgBeQnjwEOY4GYbjYrfhoC3bA";

    
    @RequestMapping(value = "/api/getcontacto", method = RequestMethod.GET)
    private Test testAngular1(){
    	Test tst = new Test();
    	tst.setLocalidad("Matanza");
    	tst.setRazonSocial("Pepita la Pistolera");
    	return tst;
    }

    
    @RequestMapping(value = "/api/download-csv", method = RequestMethod.GET)
    public void exportCSV(HttpServletResponse response,
       		@RequestParam(value = "type", required = false) String typeContact) 
          throws Exception {
    	 String filename;
    	 System.out.println("Valor " + typeContact);
    //	if (typeContact.equalsIgnoreCase("puri"))
    //		filename = "purina_EXTContact.csv";
   // 	else
    		filename = "professional_EXTContact.csv";
       // set file name and content type
    
       response.setContentType("text/csv");
       response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
             "attachment; filename=\"" + filename + "\"");
      System.out.println("Empezamos aqui ");
 	  BulkContacts cont = new BulkContacts(clienteID, clienteSecret);
 	 System.out.println("Empezamos aqui 9");
 	  ArrayList<ClienteExport> clientes = cont.scanContacts(typeContact, 50);
      // create a csv writer/*
 	 HeaderColumnNameMappingStrategy<ClienteExport> strategy = new HeaderColumnNameMappingStrategy<>();
     strategy.setType(ClienteExport.class);
     String headerLine = Arrays.stream(ClienteExport.class.getDeclaredFields())
    	        .map(field -> field.getAnnotation(CsvBindByName.class))
    	        .filter(Objects::nonNull)
    	        .map(CsvBindByName::column)
    	        .collect(Collectors.joining(","));
     System.out.println("Empezamos aqui 2");
     try (StringReader reader = new StringReader(headerLine)) {
         CsvToBean<ClienteExport> csv = new CsvToBeanBuilder<ClienteExport>(reader)
                 .withType(ClienteExport.class)
                 .withMappingStrategy(strategy)
                 .build();
         for (ClienteExport csvCli : csv) {}
     }
     System.out.println("Empezamos aqui 3");
 	  StatefulBeanToCsv<ClienteExport> writer = 
            new StatefulBeanToCsvBuilder<ClienteExport>
                 (response.getWriter())
                 .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                   .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                   .withMappingStrategy(strategy) 
            .withOrderedResults(false).build();
      
 	 System.out.println("Empezamos aqui 4");
      if ((clientes != null) && (clientes.size() > 0)){
    	  System.out.println(clientes.get(0).getIDGenesys());
    	 // for (int i= 0; i < clientes.size(); i++) {
    		//  writer.write(clientes.get(i));
    	//  }
    	  writer.write(clientes);
    		  
      } 
      System.out.println("Empezamos aqui 5");
    } 
    
    @RequestMapping(value = "/api1/upload-csv", method = RequestMethod.POST)
    public void upload(@RequestParam("file") MultipartFile file,  
    		@RequestParam(value = "typeContact", required = false) String typeContact) {
    	
        try {
            if (file.isEmpty()) {            	
                  System.out.println("Seleccione el archivo para importar los contacto.");
            }
            else {
            	System.out.println("Hay archivo");
            	System.out.println(file.getName());
            	System.out.println("Valor " + typeContact);
            }
/*
        	  BulkContacts cont = new BulkContacts(file.getInputStream(), clienteID, clienteSecret);
        	  cont.setEsquema(typeContact);
        	  cont.setMaxContactosToProcess(99999);
        	  cont.createContact();
    		  model.addAttribute("status", true);
    		 // Resultado res = new Resultado(1,1, 0, 1,0);
    		  Resultado res = new Resultado(cont.getResults().getTotalContactos(),
    				  						cont.getResults().getTotalContactosOk(),
    				  						cont.getResults().getTotalContactosError(), 
    				  						cont.getResults().getTotalContactosOk_API(), 
    				  						cont.getResults().getTotalContactosError_API() );
    		  model.addAttribute("resultado", res);
    //		  logger.info("Resultado de la operacion");
    //		  logger.info("Total Contactos: " + cont.getResults().getTotalContactos());
    //		  logger.info("Total Contactos OK (csv): " + cont.getResults().getTotalContactosOk());
    //		  logger.info("Total Contactos Error(csv): " + cont.getResults().getTotalContactosError());
    //		  logger.info("Total Contactos OK(API): " + cont.getResults().getTotalContactosOk_API());
    //		  logger.info("Total Contactos Error(API): " + cont.getResults().getTotalContactosError_API());
            }*/
        }
        catch (Exception  e) {
           	 System.out.println(e.getMessage());
        }
        //return "file-upload-status";        
    }   


 
    @RequestMapping(value = "/api/upload-csv", method = RequestMethod.POST)
    public void update1(@RequestParam("file") MultipartFile file, 
    		@RequestParam(value = "typeContact", required = false) String typeContact,
    		@RequestParam(value = "operacion", required = false) String operacion) {
    	if (file.isEmpty()) {            	
    		System.out.println("Seleccione el archivo para importar los contacto.");
    	}
    	else {
    		System.out.println("Tipo de contacto: " + typeContact);
    		System.out.println("Operacion: " + operacion);
    		try {
    			BulkContacts cont = new BulkContacts(file.getInputStream(), clienteID, clienteSecret);
    			cont.setMaxContactosToProcess(99999);
    			if (operacion.equalsIgnoreCase("add")) {
    				cont.setEsquema(typeContact);
    				cont.createContact();
    			}
    			else if (operacion.equalsIgnoreCase("mod")) {
    				cont.setEsquema(typeContact);
    				cont.updateContact();
    			}
    			else {
    				cont.setMaxContactosToProcess(48);
    				cont.deleteContact();
    			}
    		}
            catch (Exception  e) {
              	 System.out.println(e.getMessage());
           }    		
    	}
    } 
}
