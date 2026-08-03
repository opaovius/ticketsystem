async function loadSingleTicket() {


	const role = getRoleFromToken();
	
	//Kunde soll ticket nicht selber schliessen koennen
    if (role == "CUSTOMER") {
        const button = document.getElementById("closeBtn");
        button.style.display = "none";
    }

    //id aus der url holen
    const id = new URLSearchParams(window.location.search).get("id");

    if (!id) {
        document.getElementById("ticketHeader").textContent = "Keine Ticket-ID angegeben.";
        return;
    }

    try {
        //ticket aus datenbank holen
        const response = await fetchWithAuth(`/tickets/${id}`);
        const ticket = await response.json();

        if (!response.ok) {
            throw new Error("Fehler beim Laden: " + response.status);
        }

        //ticket rendern
        document.getElementById("Header").textContent = ticket.header;
        document.getElementById("Text").textContent = ticket.text;

    } catch (err) {
        console.error(err);
        document.getElementById("Header").textContent = "Ticket konnte nicht geladen werden.";
    }

    const repliesResponse = await fetchWithAuth(`/reply/getAll/${id}`)
    const replies = await repliesResponse.json();

    renderReplies(replies);

}

function renderReplies(replies) {
    const container = document.getElementById("replyList");
    container.innerHTML = "";

    if (replies.length === 0) {
        container.innerHTML = '<p class="text-muted">Noch keine Antworten vorhanden.</p>';
        return;
    }

    replies.forEach(reply => {
        const card = document.createElement("div");
        card.className = "card mb-2";

        const cardBody = document.createElement("div");
        cardBody.className = "card-body";

        const author = document.createElement("h6");
        author.className = "card-subtitle mb-2 text-muted";
        author.textContent = reply.author;

        const text = document.createElement("p");
        text.className = "card-text";
        text.style.whiteSpace = "pre-wrap";
        text.textContent = reply.text;

        cardBody.appendChild(author);
        cardBody.appendChild(text);
        card.appendChild(cardBody);
        container.appendChild(card);
    });
}

loadSingleTicket();