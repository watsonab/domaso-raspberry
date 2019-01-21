document.addEventListener("DOMContentLoaded", function(event) { 

var request = new XMLHttpRequest();

// Open a new connection, using the GET request on the URL endpoint
request.open('GET', '/livingRoomTemperature/', true);

request.onload = function () {
  // Begin accessing JSON data here
  }
}

// Send request
request.send();

var data = this.response;

alert("I'm active");
    
document.getElementById("TheBody").innerHTML = data;});