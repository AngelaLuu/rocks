package co.com.rockstar.center.servlet;

import co.com.rockstar.center.bean.User;
import co.com.rockstar.center.repository.Repository;
import co.com.rockstar.center.repository.UserRepositoryImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
@WebServlet ("RegisterUserxd")
public class UserRegisterServlet extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //  collect all form data}
        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");
        String precio = request.getParameter("precio");
        String cantidad = request.getParameter("cantidad");
        String image = request.getParameter("image");


        //  fill it up in a User Bean
        User user = new User();
        user.setNombre(nombre);
        user.setDescripcion(descripcion);
        user.setPrecio(Integer.valueOf(precio));
        user.setCantidad(Integer.valueOf(cantidad));
        user.setImage(image);

        // call repository layer and save the user object to DB
        String opcion = request.getParameter("opcion");
        Repository<User> repository = new UserRepositoryImpl();
        switch (opcion) {
            // call repository loyer and save the user object to DB
            case "modificardatos": {
                int rows = 0;

                try {
                    rows = repository.saveObj(user);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                //  prepare information message for user about sucess or fallure of the console
                if (rows == 0) {
                    //infoMessage = "Ocurrio un error";
                    System.out.println("Ocurrio un error");
                } else {
                    //infoMessage = "Registro exitoso";
                    System.out.println("registro existoso");
                }
                break;
            }
            case "eliminardatos": {

                try {
                    repository.deleteObj(User.getId());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            }
            case "abrirdatos": {

                try {
                    User datos = repository.byIdOBj(User.getId());
                    request.getSession().setAttribute("datos", datos);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;


                // todo write the message back to the page in client browser
            }
        }
        response.sendRedirect("/autowashcenter-1/User/registro.jsp");

    }
}
