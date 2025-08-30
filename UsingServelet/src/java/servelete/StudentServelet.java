/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servelete;

import dao.StudentDao;
import entity.Student;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
@WebServlet("/studentservlet")
public class StudentServelet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(StudentServelet.class.getName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action");

        try {
            if ("add".equals(action)) {
                Student s = new Student();
                s.setName(req.getParameter("name"));
                s.setEmail(req.getParameter("email"));
                s.setFee(Float.parseFloat(req.getParameter("fee")));

                StudentDao.saveStudent(s);
                resp.sendRedirect("index.jsp");

            } else if ("update".equals(action)) {
                Student s = new Student();
                s.setId(Integer.parseInt(req.getParameter("id")));
                s.setName(req.getParameter("name"));
                s.setEmail(req.getParameter("email"));
                s.setFee(Float.parseFloat(req.getParameter("fee")));

                StudentDao.updateStudent(s);
                resp.sendRedirect("index.jsp");
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error in doPost", e);
            resp.sendRedirect("error.jsp"); // Optional: error page
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action");

        try {
            if ("delete".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                StudentDao.deleteStudent(id);
                resp.sendRedirect("index.jsp");

            } else if ("edit".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                Student s = StudentDao.getStudentById(id);

                req.setAttribute("student", s);
                req.getRequestDispatcher("editStudent.jsp").forward(req, resp);
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error in doGet", e);
            resp.sendRedirect("error.jsp"); // Optional: error page
        }
    }

}
