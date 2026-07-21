var numMessages;
var messageJSON;

$(document).ready(
    function() {

        //Web Sockets
        var socket = new SockJS('/gs-guide-websocket');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);

        stompClient.subscribe('/topic/tweets', function (tweetSentimentResponse) {

        	 var tweetSentiment = JSON.parse(tweetSentimentResponse.body);

                if (numMessages > 10) {
                    document.getElementById("output").deleteRow(-1);
                }

                $('#output').prepend('<tr id="tweet-row"> ' +
                '<td id="tweet-cell" class="col-sm-10">&nbsp<div class="verticalLine">'+ urlify(tweetSentiment.tweet.text) + '</div></td>' +
                '<td id="sentiment-cell" class="col-sm-2">' + polarityToLabel(tweetSentiment.polarity) + '</td> </tr>');
        });

    });

    }
);
function polarityToLabel(p) {
    if (p > 0) {
        if( p > 1 )
        {
            return "<div style=\"color:green\"> very pos </div> "
        }
        return "<div style=\"color:green\"> pos </div> "
    } else if (p < 0) {
        if(p < -1)
        {
            return "<div style=\"color:red\"> very neg </div> "
        }
        return "<div style=\"color:red\"> neg </div> "
    }
    return "neu"
}


function urlify(text) {
    // source: http://stackoverflow.com/questions/1500260/detect-urls-in-text-with-javascript
    var urlRegex = /(https?:\/\/[^\s]+)/g;
    return text.replace(urlRegex, function(url) {
        return '<a href="' + url + '">' + url + '</a>';
    })
}

