let sortKey = null;
let sortAsc = true;
let tickets = [];
let allTickets = [];
const unsolvedTickets = [];
const notSolvedTickets = [];

async function loadTickets() {
    try {
        const role = getRoleFromToken();
        let endpoint;

        if (role == "ADMIN") {
            endpoint = "/tickets/admin";
        }
        else if (role == "SUPPORT") {
            endpoint = "/tickets/support";
        }
        else {
            endpoint = "/tickets/customer";
        }

        //Support soll keine tickets erstellen
        if (role == "SUPPORT") {
            const button = document.getElementById("topButton");
            button.style.display = "none";
        }

        //Admin soll Users verwalten koennen
        if (role == "ADMIN") {
            const button = document.getElementById("topButton");
            button.textContent = "Nutzer Verwalten";
            button.onclick = () => redirectToManageUsers();
        }

        const response = await fetchWithAuth(endpoint);

        if (!response.ok) {
            throw new Error("Fehler beim Laden der Tickets: " + response.status);
        }

        allTickets = await response.json();
        splitUnsolvedTickets();
        renderTickets();

    } catch (err) {
        console.error(err);
        document.getElementById("errorMsg").textContent = "Tickets konnten nicht geladen werden.";
    }
}

function renderTickets() {
    const tbody = document.getElementById("ticketTableBody");
    tbody.innerHTML = "";

    if (tickets.length === 0) {
        tbody.innerHTML = '<tr><td colspan="3">Keine Tickets vorhanden.</td></tr>';
        return;
    }

    tickets.forEach(ticket => {
        const row = document.createElement("tr");

        //Anklickbarer Header hinzufuegen
        row.appendChild(createHeaderLinkCell(ticket));

        const textCell = createCell(ticket.text);
        //Begrenzen, damit Text nicht zu lang in Tabelle
        textCell.classList.add("text-truncate-cell");
        row.appendChild(textCell);

        row.appendChild(createStatusCell(ticket.status));

        tbody.appendChild(row);
    });
}

function createStatusCell(status) {

    //lookUpTable erzeugen
    const statusMapCustomer = {
        OPEN: { label: "Offen", css: "status-open" },
        WAITING_FOR_SUPPORT: { label: "In Bearbeitung", css: "status-progress" },
        WAITING_FOR_CUSTOMER: { label: "Antwort erforderlich", css: "status-waiting" },
        SOLVED: { label: "Gelöst", css: "status-closed" }
    };

    const statusMapSupport = {
        OPEN: { label: "Neu", css: "status-open" },
        WAITING_FOR_SUPPORT: { label: "Antwort erforderlich", css: "status-waiting" },
        WAITING_FOR_CUSTOMER: { label: "Wartet auf Kunde", css: "status-progress" },
        SOLVED: { label: "Gelöst", css: "status-closed" }
    };

    const role = getRoleFromToken();

    //lookUp mit Fallback kombinieren
    const statusMap = role === "SUPPORT" ? statusMapSupport : statusMapCustomer;

    const info = statusMap[status] || { label: status, css: "status-open" };

    const td = document.createElement("td");
    const span = document.createElement("span");
    span.className = "badge-status " + info.css;
    span.textContent = info.label;

    td.appendChild(span);
    return td;
}

function createCell(text) {
    const td = document.createElement("td");
    td.textContent = text;
    return td;
}

function createHeaderLinkCell(ticket) {
    const td = document.createElement("td");

    const link = document.createElement("a");
    link.href = `/html/ticketHTML/ticketDetails.html?id=${ticket.id}`;
    link.textContent = ticket.header;

    td.appendChild(link);
    return td;
}

function redirectToManageUsers() {
    window.location.href = "/html/userHTML/manageUsers.html";
}

function sortBy(key) {
    // gleiche Spalte nochmal geklickt -> Richtung umdrehen
    sortAsc = (sortKey === key) ? !sortAsc : true;
    sortKey = key;

    tickets.sort((a, b) => {
        const x = a[key];
        const y = b[key];
        const statusOrder = { OPEN: 0, WAITING_FOR_SUPPORT: 1, WAITING_FOR_CUSTOMER: 2, SOLVED: 3 };

        let result;
        if (typeof x === 'number' && typeof y === 'number') {
            result = x - y;
        } else if (key === 'status') {
            result = statusOrder[x] - statusOrder[y];
        }
        else {
            result = String(x).localeCompare(String(y), 'de', { sensitivity: 'base' });
        }
        return sortAsc ? result : -result;
    });

    //Pfeile aktualisieren
    document.querySelectorAll('#ticketTable thead th').forEach(th => {
        if (th.dataset.key === key) {
            th.dataset.sort = sortAsc ? 'asc' : 'desc';
            th.setAttribute('aria-sort', sortAsc ? 'ascending' : 'descending');
        } else {
            th.removeAttribute('data-sort');
            th.removeAttribute('aria-sort');
        }
    });

    renderTickets();
}

//Erzeugt eine Liste mit ungelösten tickets
function splitUnsolvedTickets() {

    allTickets.forEach(ticket => {

        if (!(ticket.status === "SOLVED")) {
            unsolvedTickets.push(ticket);
        }
    })
    tickets = unsolvedTickets
}


function showSolved() {

    const solvedBtn = document.getElementById("hideSolvedButton");
    solvedBtn.textContent = "Versteck Gelöste";
    solvedBtn.onclick = hideSolved;
    solvedBtn.className = "btn btn-warning btn-hover btn-border-wa";


    tickets = allTickets;
    renderTickets();
}

function hideSolved() {

    const solvedBtn = document.getElementById("hideSolvedButton");
    solvedBtn.textContent = "Zeig Gelöste";
    solvedBtn.onclick = showSolved;
    solvedBtn.className = "btn btn-warning btn-hover btn-border-wa";

    tickets = unsolvedTickets;
    renderTickets();
}

document.querySelectorAll('#ticketTable th').forEach(th => {
    th.addEventListener('click', () => sortBy(th.dataset.key));
});


loadTickets();