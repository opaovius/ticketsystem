const formPass = document.getElementById("setPasswordForm");
if (formPass) {
    formPass.addEventListener("submit", (e) => {
        e.preventDefault();
        setPassword();
    });
}

async function setPassword() {

    const newPassword = document.getElementById("newPassword").value;
    const newPasswordConfirm = document.getElementById("newPasswordConfirm").value;

    console.log(newPassword + "|" + newPasswordConfirm);

    if (newPassword != newPasswordConfirm) {
        throw new Error("Passwörter stimmen nicht überein");
    }
    else {
        const response = await fetchWithAuth("/users/setPassword", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                newPassword: newPassword
            })
        })
		
		if (!response.ok) {
		    throw new Error("Neues Passwort konnte nicht gesetzt werden!")
		}
    }
	
	logout();
}