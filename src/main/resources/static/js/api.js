async function fetchWithAuth(url, options = {}) {
    let token = localStorage.getItem("token");

	//neues Header-Objekt erstellen mit Authhorization:Bearer + token
    options.headers = {
        ...(options.headers || {}),
        "Authorization": "Bearer " + token
    };

    let response = await fetch(url, options);

    if (response.status === 401) {
        //AccessToken abgelaufen -> mit RefreshToken neues holen
        const refreshed = await tryRefreshToken();

        if (refreshed) {
            token = localStorage.getItem("token");
            options.headers["Authorization"] = "Bearer " + token;
            response = await fetch(url, options);
        } else {
            window.location.href = "/html/login.html";
            throw new Error("Session abgelaufen");
        }
    }

    return response;
}

//erstellt neues AccessToken
async function tryRefreshToken() {
    try {
        const res = await fetch("/auth/refresh", {
            method: "POST",
            credentials: "include" //sendet RefreshCookie an AuthController
        });

        if (!res.ok) return false;

        const data = await res.json();
        localStorage.setItem("token", data.token);
        return true;

    } catch (err) {
        return false;
    }
}

//Rolle aus token extrahieren
function getRoleFromToken() {
    const token = localStorage.getItem("token");
    if (!token) return null;

    try {
        const payload = JSON.parse(atob(token.split(".")[1]));
        return payload.role;
    } catch (err) {
        return null;
    }
}