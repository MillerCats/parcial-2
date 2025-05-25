document.addEventListener("DOMContentLoaded", async () => {
    let token = getCookie("token");
    const response = await fetch("/Examen2Prac/crud-clientes", {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + token
        }
    }).then(response => response.json());

    if (response.action === "show") {
        console.log(response);
        const data = response.clientes;
        const tbody = document.getElementById("dataTable");
        data.forEach(p => {
            const fila = `
                <tr class="align-middle">
                    <td>${p.codiClie}</td>
                    <td>${p.appaClie}</td>
                    <td>${p.apmaClie}</td>
                    <td>${p.nombClie}</td>
                </tr>`;
            tbody.innerHTML += fila;
        });
    } else {
        alert("Debe iniciar sesión");
        window.location.href = "../../../index.html";
    }

    function getCookie(nombre) {
        const valor = `; ${document.cookie}`;
        const partes = valor.split(`; ${nombre}=`);
        if (partes.length === 2) {
            return partes.pop().split(';').shift();
        }
    }
});