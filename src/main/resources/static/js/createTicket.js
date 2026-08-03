function redirectCreateTicket() {
    window.location.href = "/html/createTicket.html";
}

async function createTicket() {


    const header = document.getElementById("header").value;
    const text = document.getElementById("text").value;

   const respone = await fetchWithAuth("/tickets/customer/create", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            header: header,
            text: text
        })
    })
	
	if(!respone.ok){
		console.error("Fehler beim Erstellen", respone.status);
		return;
	}
	
	window.location.href = "/html/ticketsTable.html";
}

function cancel(){
	window.location.href = "/html/ticketsTable.html";
}