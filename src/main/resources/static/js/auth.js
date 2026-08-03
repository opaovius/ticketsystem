function login() {

    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    fetch("/auth/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            email: email,
            password: password
        })
    })
    .then(res => res.json())
    .then(data => {

        // JWT speichern
        localStorage.setItem("token", data.token);

        // weiterleiten
        window.location.href = "/html/ticketsTable.html";
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