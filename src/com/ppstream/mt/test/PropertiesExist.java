package com.ppstream.mt.test;

import java.io.File;
import java.net.URL;

import org.junit.Test;

public class PropertiesExist extends BaseTest{
	
	@Test
	public void propertiesExist(){
		URL url = ClassLoader.getSystemResource("email.properties");
		final File file = new File(url.getFile());
		System.out.println(file.exists());
		System.out.println(file.getAbsolutePath()); 
	}

}
