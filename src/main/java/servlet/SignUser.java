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
import util.HashPass;
import util.JwtUtil;

@WebServlet(name = "SignUser", urlPatterns = {"/sign-hash"})
public class SignUser extends HttpServlet {

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
            String hashPass = HashPass.hashPassword(pass);
            Usuario usuario = new Usuario(null,log, hashPass);
            try {
                String token = JwtUtil.generarToken(log);
                ujc.create(usuario);
                jsonObject.addProperty("result", "ok");
                jsonObject.addProperty("token", token);
            } catch (Exception e) {
                jsonObject.addProperty("token", e.getMessage());
            }
            out.print(gson.toJson(jsonObject));
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
