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
		
		showPassword(tempPass, name);

    } catch (err) {
        console.error("Nutzerregistrierung fehlgeschlagen", err);
    }
}

function showPassword(password, name) {

    const passwordDialog = document.getElementById("passwordDialog");
    const passwordText = document.getElementById("passwordText");
	const diaHeader = document.getElementById("diaHeader");
	
	diaHeader.textContent = "Nutzer " + name + " erstellt!"
    passwordText.textContent = password;
    passwordDialog.showModal();

}

function copyPassword() {
	
    const password = document.getElementById("passwordText").textContent;
    navigator.clipboard.writeText(password);
	
	const copyBtn = document.getElementById("copyBtn");
	copyBtn.textContent = "kopiert!";
}