/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tebeshir.admin.app.servlet;

import com.tebeshir.admin.app.dao.SocialNetworkCreatorDAO;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author yetkin.timocin
 */
@WebServlet(name = "NewSocialNetworkServlet", urlPatterns = {"/NewSocialNetworkServlet"})
@MultipartConfig
public class NewSocialNetworkServlet extends HttpServlet {

    private final String UPLOAD_DIRECTORY = "/images/socialNetworkLogos";
    private final static Logger LOGGER =
            Logger.getLogger(NewSocialNetworkServlet.class.getCanonicalName());

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int result = -1;

        // Create path components to save the file
        final String soNetName = request.getParameter("socialNetworkName");
        System.out.println("soNetName: " + soNetName);
        final String soNetStatus = request.getParameter("socialNetworkStatus");
        System.out.println("soNetStatus: " + soNetStatus);
        final Part filePart = request.getPart("socialNetworkLogo");
        final String fileName = getFileName(filePart);
        System.out.println("fileName: " + fileName);

        String directoryPath = getServletContext().getRealPath(UPLOAD_DIRECTORY + "/");

        OutputStream out = null;
        InputStream filecontent = null;
        final PrintWriter writer = response.getWriter();

        try {
            out = new FileOutputStream(new File(directoryPath + File.separator
                    + fileName));
            filecontent = filePart.getInputStream();

            int read = 0;
            final byte[] bytes = new byte[1024];

            while ((read = filecontent.read(bytes)) != -1) {
                System.out.println("yekokokoko");
                out.write(bytes, 0, read);
            }
            writer.println("New file " + fileName + " created at " + directoryPath);
            LOGGER.log(Level.INFO, "File{0}being uploaded to {1}", new Object[]{fileName, directoryPath});
            
            

            SocialNetworkCreatorDAO soNetDAO = new SocialNetworkCreatorDAO();
            result = soNetDAO.addSocialNetwork(soNetName, 
                    Integer.valueOf(soNetStatus), 
                    UPLOAD_DIRECTORY + File.separator + fileName,
                    "images/socialNetworkLogos" + File.separator + fileName);
            
            //filePart.write(fileName);

            if (result == 0) {
                System.out.println("yebaaa mathafacka");
            }

        } catch (FileNotFoundException fne) {
            writer.println("You either did not specify a file to upload or are "
                    + "trying to upload a file to a protected or nonexistent "
                    + "location.");
            writer.println("<br/> ERROR: " + fne.getMessage());

            LOGGER.log(Level.SEVERE, "Problems during file upload. Error: {0}", new Object[]{fne.getMessage()});
        } finally {
            if (out != null) {
                out.close();
            }
            if (filecontent != null) {
                filecontent.close();
            }
            if (writer != null) {
                writer.close();
            }
        }
    }

    private String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");
        //LOGGER.log(Level.INFO, "Part Header = {0}", partHeader);
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);


        } catch (Exception ex) {
            Logger.getLogger(NewSocialNetworkServlet.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);


        } catch (Exception ex) {
            Logger.getLogger(NewSocialNetworkServlet.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "New Social Network Servlet";
    }// </editor-fold>
}
