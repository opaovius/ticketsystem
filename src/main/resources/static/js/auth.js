const form = document.getElementById("loginForm");
if (form) {
    form.addEventListener("submit", (e) => {
        e.preventDefault();
        login();
    });
}


async function login() {

    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    response = await fetch("/auth/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            email: email,
            password: password
        })
    })

    if (!response.ok) {

        const errorPara = document.getElementById("errorParagraph")

        errorPara.textContent = "Email oder Passwort falsch!";
        errorPara.style.color = "red";

        console.error("Email oder Passwort falsch!")
        return;
    }

    response.json()
        .then(async data => {

            console.log(data);

            // JWT speichern
            localStorage.setItem("token", data.token);

            const response = await fetchWithAuth("/users/setPassword");

            if (!response.ok) {
                throw new Error("PasswordSet konnte nicht geladen werden");
            }

            const passwordSet = await response.json();

            console.log("PASSWORD_SET: " + passwordSet);

            if (passwordSet) {
                window.location.href = "/html/ticketHTML/ticketsTable.html";
            }
            else {
                window.location.href = "/html/userHTML/setPassword.html";
            }

        })
        .catch(err => {
            console.error("Login Fehler:", err);
        });
}

async function logout() {
    try {
        await fetch("/auth/logout", {
            method: "POST",
            credentials: "include"
        });
    } catch (err) {
        console.error("Logout-Request fehlgeschlagen:", err);
    } finally {
        localStorage.removeItem("token");
        window.location.href = "/html/login.html";
    }
}