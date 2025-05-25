package servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dao.ClienteJpaController;
import dto.Cliente;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import util.JwtUtil;

@WebServlet(name = "CrudCliente", urlPatterns = {"/crud-clientes"})
public class CrudCliente extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        Gson gson = new Gson();
        JsonObject jsonResponse = new JsonObject();
        String authHeader = request.getHeader("Authorization");
        try ( PrintWriter out = response.getWriter()) {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            String token = authHeader.substring(7);
            if (JwtUtil.validarToken(token)) {
                ClienteJpaController cjc = new ClienteJpaController();
                List<Cliente> clientes = cjc.findClienteEntities();
                jsonResponse.addProperty("action", "show");
                jsonResponse.add("clientes", gson.toJsonTree(clientes));
            } else {
                jsonResponse.addProperty("message", "El token expiró o es invalido");
                jsonResponse.addProperty("action", "update");
            }
            out.print(gson.toJson(jsonResponse));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
