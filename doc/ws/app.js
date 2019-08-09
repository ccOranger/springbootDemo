var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('http://127.0.0.1:8099/gs-guide-websocket');
    stompClient = Stomp.over(socket);
	console.log('stompClient: ' + stompClient);
    stompClient.connect({}, function (frame) {
		var userId=1;
		console.log('222222: ');
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (greeting) {
			console.log('greeting:=======w============ ' + greeting.body);
            showGreeting(greeting.body);
        });
		
		//通过stompClient.subscribe订阅/topic/getResponse 目标(destination)发送的消息
            stompClient.subscribe('/user/' + userId + '/queue/getResponse',function(response){        	            		
            	showGreeting(response.body)              	
            });


    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
	console.log("stompClient===="+stompClient)
    stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});