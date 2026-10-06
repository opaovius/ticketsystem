const form = document.getElementById("addUserForm");
if (form) {
    form.addEventListener("submit", (e) => {
        e.preventDefault();
        addUser();
    });
}


async function addUser() {

    const name = document.getElementById("name").value;
    const email = document.getElementById("email").value;
    const role = document.querySelector('select[name="role"]').value;

    try {

        const response = await fetchWithAuth("/users/register", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
				name: name,
                email: email,
				role: role
            })
        })
		
		if (!response.ok) {
		    console.error("Nutzerregistrierung fehlgeschlagen:", response.status);
		    return;
		}
		
		const data = await response.json();
		const tempPass = data.tempPassword;

    } catch (err) {
        console.error("Nutzerregistrierung fehlgeschlagen", err);
    }
}