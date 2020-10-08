let id = id => document.getElementById(id);

//Establish the WebSocket connection and set up event handlers
let ws = new WebSocket("ws://localhost:" + location.port + "/chat");
ws.onmessage = msg => updateChat(msg);
ws.onclose = () => alert("WebSocket connection closed");

// Add event listeners to button and input field
id("sendBtn").addEventListener("click", () => sendAndClear(id("userInput").value));
id("userInput").addEventListener("keypress", function (e) {
    if (e.keyCode === 13) { // Send message if enter is pressed in input field
        sendAndClear(e.target.value);
    }
});

function sendAndClear(message) {
    if (message !== "") {
        ws.send(message);
        id("userInput").value = "";
    }
}

function updateChat(msg) { // Update chat-panel and list of connected users
    let data = JSON.parse(msg.data);
    console.log(data);
    if (data.answer) {
        var load = document.getElementsByClassName("load_container");
        for(i = 0; i < load.length; i++) {
            load[i].style.display = 'none';
        }
        id("chat").insertAdjacentHTML("beforeend", data.userMessage);
    } else {
        id("chat").insertAdjacentHTML("beforeend", data.userMessage);
    }
    // id("chat").insertAdjacentHTML("beforeend", data.userMessage);

}