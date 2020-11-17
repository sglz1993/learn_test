package org.py.test.error.test01.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;

@Slf4j
@Controller
public class ImageController {

    @GetMapping(value = "/image", produces = {MediaType.IMAGE_PNG_VALUE})
    public void image(HttpServletResponse response) throws Exception {
        IOUtils.copy(new FileInputStream(new File("/Users/pengyue.du/Downloads/timg.png")), response.getOutputStream());
    }

}
