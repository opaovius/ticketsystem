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

        row.append(idCell, nameCell, emailCell, roleCell);
        tbody.appendChild(row);
    });

}

function redirectAddUser(){
		window.location.href = "/html/addUser.html";
}

loadUsers();