async function createReply() {

    const text = document.getElementById("text").value;
	const id = new URLSearchParams(window.location.search).get("id");

    const response = await fetchWithAuth("/reply/create", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            text: text,
			id: id
        })
    })

    if (!response.ok) {
        console.error("Fehler beim Erstellen", response.status);
        return;
    }

	updateStatus();
    location.reload();
}

async function updateStatus() {

    //id aus der url holen
    const id = new URLSearchParams(window.location.search).get("id");

    if (!id) {
        document.getElementById("ticketHeader").textContent = "Keine Ticket-ID angegeben.";
        return;
    }


    const response = await fetchWithAuth(`/tickets/updateStatus/${id}`, {
		method: "PUT"
	});


    if (!response.ok) {
        console.error("Fehler beim Erstellen", response.status);
        return;
    }
}