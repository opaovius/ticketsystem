async function loadTickets() {
    try {
        const role = getRoleFromToken();
        const endpoint = role === "SUPPORT" ? "/tickets/support" : "/tickets/customer";
		
		//Support soll keine tickets erstellen
		if(role == "SUPPORT"){
			const button = document.getElementById("createTicketBtn");
			button.style.display = "none";
		}

        const response = await fetchWithAuth(endpoint);

        if (!response.ok) {
            throw new Error("Fehler beim Laden der Tickets: " + response.status);
        }

        const tickets = await response.json();
        renderTickets(tickets);

    } catch (err) {
        console.error(err);
        document.getElementById("errorMsg").textContent = "Tickets konnten nicht geladen werden.";
    }
}

function renderTickets(tickets) {
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
    link.href = `/html/ticketDetails.html?id=${ticket.id}`;
    link.textContent = ticket.header;

    td.appendChild(link);
    return td;
}

loadTickets();