async function loadUsers() {

    try {

        const sort = new URLSearchParams(window.location.search).get("sort") || "";

        const response = await fetchWithAuth(("/users?sort=") + encodeURIComponent(sort));

        if (!response.ok) {
            throw new Error("Fehler beim Laden der Nutzer: " + response.status);
        }

        const userList = await response.json();

        renderUserTable(userList);
    } catch (err) {
        console.error(err);
        document.getElementById("errorMsg").textContent = "Nutzer konnten nicht geladen werden.";
    }
}

function renderUserTable(userList) {

    const tbody = document.getElementById("userTableBody");
    tbody.innerHTML = "";

    if (userList.length === 0) {
        tbody.innerHTML = '<tr><td colspan="3">Keine Nutzer vorhanden.</td></tr>';
        return;
    }

    userList.forEach(user => {
        const row = document.createElement("tr");

        const idCell = document.createElement("td");
        idCell.textContent = user.id;

        const nameCell = document.createElement("td");
        nameCell.textContent = user.name;

        const emailCell = document.createElement("td");
        emailCell.textContent = user.email;

        const roleCell = document.createElement("td");
        roleCell.textContent = user.role;

        const delBtnCell = createDeleteButtonCell(user.id);

        row.append(idCell, nameCell, emailCell, roleCell, delBtnCell);
        tbody.appendChild(row);
    });

}

function createDeleteButtonCell(id) {

    const btn = document.createElement("button");
    const td = document.createElement("td");

    btn.innerText = "Nutzer löschen";
    btn.className = "btn btn-danger btn-hover btn-border-da";
    btn.onclick = function() {
        if (confirm("Möchtest du diesen Nutzer wirklich löschen?")) {
            deleteUser(id);
        }
    };



    td.append(btn);

    return td;
}

function redirectAddUser() {
    window.location.href = "/html/userHTML/addUser.html";
}

function deleteUser(id) {

    fetchWithAuth(`/users/${id}`,
        { method: "DELETE" })
        .then(response => {
            if (!response.ok) {
                throw new Error("Nutzer konnte nicht gelöscht werden");
            }
            loadUsers();
        })

}

loadUsers();