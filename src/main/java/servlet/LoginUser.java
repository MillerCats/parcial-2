package servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dao.UsuarioJpaController;
import dto.Usuario;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import util.JwtUtil;

@WebServlet(name = "LoginUser", urlPatterns = {"/login-normal"})
public class LoginUser extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            JsonObject jsonObject = new JsonObject();
            BufferedReader reader = request.getReader();
            JsonObject json = gson.fromJson(reader, JsonObject.class);
            String log = json.get("log").getAsString();
            String pass = json.get("pass").getAsString();
            UsuarioJpaController ujc = new UsuarioJpaController();
            Usuario usuario = ujc.validarCredenciales(log, pass);
            if (usuario != null) {
                String token = JwtUtil.generarToken(log);
                jsonObject.addProperty("result", "ok");
                jsonObject.addProperty("token", token);
            } else {
                jsonObject.addProperty("result", "not");
            }
            out.print(gson.toJson(jsonObject));
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
