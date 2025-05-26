package servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dao.ClienteJpaController;
import dto.Cliente;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;
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
                jsonResponse.add("data", gson.toJsonTree(clientes));
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
        response.setContentType("application/json;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            BufferedReader reader = request.getReader();
            String json = reader.lines().collect(Collectors.joining());
            JsonObject obj = JsonParser.parseString(json).getAsJsonObject();

            String codigo = obj.get("codigo").getAsString();
            String nombre = obj.get("nombre").getAsString();
            String apPaterno = obj.get("appater").getAsString();
            String apMaterno = obj.get("apmater").getAsString();
            ClienteJpaController cjc = new ClienteJpaController();
            Gson gson = new Gson();
            JsonObject jsonObject = new JsonObject();
            try {
                Cliente cliente = new Cliente(codigo, apPaterno, apMaterno, nombre);
                cjc.create(cliente);
                jsonObject.addProperty("result", "created");
            } catch (Exception e) {
                jsonObject.addProperty("result", "error" + e);
                e.printStackTrace();
            }
            out.print(gson.toJson(jsonObject));
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            BufferedReader reader = request.getReader();
            String json = reader.lines().collect(Collectors.joining());
            JsonObject obj = JsonParser.parseString(json).getAsJsonObject();

            String codigo = obj.get("codigo").getAsString();
            String nombre = obj.get("nombre").getAsString();
            String apPaterno = obj.get("appater").getAsString();
            String apMaterno = obj.get("apmater").getAsString();
            ClienteJpaController cjc = new ClienteJpaController();
            Cliente cliente = cjc.findCliente(codigo);
            Gson gson = new Gson();
            JsonObject jsonObject = new JsonObject();
            try {
                if (cliente != null) {
                    cliente.setNombClie(nombre);
                    cliente.setAppaClie(apPaterno);
                    cliente.setApmaClie(apMaterno);
                    cjc.edit(cliente);
                    jsonObject.addProperty("result", "updated");
                }
            } catch (Exception e) {
                jsonObject.addProperty("result", "error" + e);
                e.printStackTrace();
            }
            out.print(gson.toJson(jsonObject));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try ( PrintWriter out = response.getWriter()) {
            BufferedReader reader = request.getReader();
            String json = reader.lines().collect(Collectors.joining());
            JsonObject obj = JsonParser.parseString(json).getAsJsonObject();

            String codigo = obj.get("codigo").getAsString();
            ClienteJpaController cjc = new ClienteJpaController();
            Cliente cliente = cjc.findCliente(codigo);
            Gson gson = new Gson();
            JsonObject jsonObject = new JsonObject();
            try {
                if (cliente != null) {
                    cjc.destroy(codigo);
                    jsonObject.addProperty("result", "deleted");
                }
            } catch (Exception e) {
                jsonObject.addProperty("result", "error" + e);
                e.printStackTrace();
            }
            out.print(gson.toJson(jsonObject));
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
