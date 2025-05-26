document.addEventListener("DOMContentLoaded", async () => {
    let token = getCookie("token");
    const modalEditar = new bootstrap.Modal(document.getElementById("modalEditar"));
    const modalCrear = new bootstrap.Modal(document.getElementById("modalCrear"));
    const modalEliminar = new bootstrap.Modal(document.getElementById("modalEliminar"));
    const showModalCrear = document.getElementById("showModalCrear");
    const btnEditar = document.getElementById("btnEditar");
    const btnEliminar = document.getElementById("btnEliminar");
    if (token) {
        await fetchData();
    } else {
        alert("Debe iniciar sesión.");
        window.location.href = "../../../index.html";
    }
    async function fetchData() {
        const response = await fetch("/Examen2Prac/crud-clientes", {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + token
            }
        }).then(response => response.json());

        if (response.action === "show") {
            console.log(response);
            const data = response.data;
            const tbody = document.getElementById("dataTable");
            tbody.innerHTML = ""; // Clear existing rows before rendering new ones
            data.forEach(cliente => {
                const row = renderFila(cliente);
                tbody.appendChild(row);
            });
        } else {
            alert("Debe iniciar sesión");
            window.location.href = "../../../index.html";
        }
    }

    function renderFila(p) {
        const fila = document.createElement("tr");
        fila.innerHTML = `
        <td>${p.codiClie}</td>
        <td>${p.nombClie}</td>
        <td>${p.appaClie}</td>
        <td>${p.apmaClie}</td>
        <td class="text-center">
            <button class="btn btn-sm btn-primary editar-btn">Editar</button>
        </td>
        <td class="text-center">
            <button class="btn btn-sm btn-danger eliminar-btn">Eliminar</button>
        </td>
    `;
        fila.querySelector(".editar-btn").addEventListener("click", () => {
            document.getElementById("upCodigo").value = p.codiClie;
            document.getElementById("upAppaterno").value = p.appaClie;
            document.getElementById("upApmaterno").value = p.apmaClie;
            document.getElementById("upNombre").value = p.nombClie;
            modalEditar.show();
        });
        fila.querySelector(".eliminar-btn").addEventListener("click", () => {
            document.getElementById("delCodigo").value = p.codiClie;
            document.getElementById("delData").value = p.nombClie + " " + p.appaClie;
            modalEliminar.show();
        });
        return fila;
    }

    showModalCrear.addEventListener("click", () => {
        modalCrear.show();
        const btnCrear = document.getElementById("btnCrear");
        btnCrear.addEventListener("click", async () => {
            const codigo = document.getElementById("newCodigo").value;
            const appater = document.getElementById("newAppater").value;
            const apmater = document.getElementById("newApmater").value;
            const nombre = document.getElementById("newNombre").value;
            const data = {codigo: codigo, appater: appater, apmater: apmater, nombre: nombre};
            const response = await fetch("/Examen2Prac/crud-clientes", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": "Bearer " + token
                },
                body: JSON.stringify(data)
            }).then(response => response.json());
            if (response.result === "created") {
                await fetchData();
                modalCrear.hide();
            } else {
                alter("Error al actualizar");
                console.log(response.result);
            }
        });
    });

    btnEditar.addEventListener("click", async () => {
        const codigo = document.getElementById("upCodigo").value;
        const appater = document.getElementById("upAppaterno").value;
        const apmater = document.getElementById("upApmaterno").value;
        const  nombre = document.getElementById("upNombre").value;
        const data = {codigo: codigo, appater: appater, apmater: apmater, nombre: nombre};
        const response = await fetch("/Examen2Prac/crud-clientes", {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + token
            },
            body: JSON.stringify(data)
        }).then(response => response.json());
        if (response.result === "updated") {
            await fetchData();
            modalEditar.hide();
        } else {
            alter("Error al actualizar");
            console.log(response.result);
        }
    });

    btnEliminar.addEventListener("click", async () => {
        const codigo = document.getElementById("delCodigo").value;
        const response = await fetch("/Examen2Prac/crud-clientes", {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + token
            },
            body: JSON.stringify({codigo: codigo})
        }).then(response => response.json());
        if (response.result === "deleted") {
            await fetchData();
            modalEliminar.hide();
        } else {
            alter("Error al eliminar");
            console.log(response.result);
        }
    });

    function getCookie(nombre) {
        const valor = `; ${document.cookie}`;
        const partes = valor.split(`; ${nombre}=`);
        if (partes.length === 2) {
            return partes.pop().split(';').shift();
        }
    }
});