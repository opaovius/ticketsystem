async function closeTicket() {

    //id aus der url holen
    const id = new URLSearchParams(window.location.search).get("id");

    if (!id) {
        document.getElementById("ticketHeader").textContent = "Keine Ticket-ID angegeben.";
        return;
    }

    const response = await fetchWithAuth(`/tickets/close/${id}`, {
        method: "PUT"
    });

    if (!response.ok) {
        console.error("Fehler beim Erstellen", response.status);
        return;
    }
	
	window.location.href = "/html/ticketsTable.html";
}