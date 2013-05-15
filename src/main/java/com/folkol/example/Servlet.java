package com.folkol.example;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet(urlPatterns = "/fileUpload")
@MultipartConfig
public class Servlet extends HttpServlet {

    private static final long serialVersionUID = 1597448164850678162L;

    private String getFileName(Part part) {
        String partHeader = part.getHeader("content-disposition");
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                return cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        for (Part part : request.getParts()) {
            InputStream is = request.getPart(part.getName()).getInputStream();
            int i = is.available();
            byte[] b = new byte[i];
            is.read(b);
            String fileName = getFileName(part);
            FileOutputStream os = new FileOutputStream(fileName);
            os.write(b);
            is.close();
        }
    }

}