package com.tebeshir.admin.app.servlet;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import com.tebeshir.admin.app.dao.NodeCreatorDAO;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author fatih.turkeri
 */
@WebServlet(name = "NewBoardServlet", urlPatterns = {"/NewBoardServlet"})
public class NewBoardServlet extends HttpServlet {

    private final String UPLOAD_DIRECTORY = "/images/boardImages";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        
        String directoryPath = getServletContext().getRealPath(UPLOAD_DIRECTORY + "/");

        response.setContentType("text/html;charset=UTF-8");

        Vector<String> imageDirectories = new Vector<String>();
        Vector<String> nodeNames = new Vector<String>();

        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        try {
            List<FileItem> multiparts = upload.parseRequest(request);

            for (FileItem item : multiparts) {
                if (!item.isFormField()) {
                    String name = new File(item.getName()).getName();
                    //item.write(new File(directoryPath + File.separator + name));
                    imageDirectories.add(request.getContextPath() + directoryPath + File.separator + name);
                } else {
                    String fieldname = item.getString();
                    response.getWriter().write(fieldname);
                    nodeNames.add(fieldname);
                }
            }

        } catch (FileUploadException ex) {
            ex.printStackTrace();
        }

        for (int i = 0; i < imageDirectories.size(); i++) {
            System.out.println(imageDirectories.get(i));
        }

        for (int j = 0; j < nodeNames.size(); j++) {
            System.out.println(nodeNames.get(j));
        }

        NodeCreatorDAO nodeCreator = new NodeCreatorDAO();
        int result = nodeCreator.createNodes(nodeNames, imageDirectories);

        System.out.println("result: " + result);
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
            Logger.getLogger(NewBoardServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(NewBoardServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "New Board Servlet";
    }// </editor-fold>
}
