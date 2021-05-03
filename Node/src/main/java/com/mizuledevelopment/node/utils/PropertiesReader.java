package com.mizuledevelopment.node.utils;

import lombok.Data;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Properties;

@Data
public class PropertiesReader {

    //private String rconPassword;

    @SneakyThrows
    public void getServerData() {
        BufferedReader is = new BufferedReader(new FileReader("server.properties"));
        Properties props = new Properties();
        props.load(is);
        is.close();

        //this.rconPassword = props.getProperty("");
    }
}
