package com.example.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import generated.Medicine;
import generated.MedicineType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.net.URL;

import static java.util.Objects.requireNonNull;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);

	private final Jaxb2Marshaller marshaller;
	private final ObjectMapper mapper;
	private final String xmlFileName;

	public DemoApplication(Jaxb2Marshaller marshaller, ObjectMapper mapper,
						   @Value("${xml.fileName}") String xmlFileName) {
		this.marshaller = marshaller;
		this.mapper = mapper;
		this.xmlFileName = xmlFileName;
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws JsonProcessingException {
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		URL resource = classLoader.getResource(xmlFileName);
		File file = new File(requireNonNull(resource).getFile());

		Medicine medicine = (Medicine) marshaller.unmarshal(new StreamSource(file));
		logger.info("XML: {}", medicine);

		String json = mapper.writeValueAsString(medicine);
		logger.info("JSON: {}", json);
	}
}
