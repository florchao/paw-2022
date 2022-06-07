$(function (){
    setTimeout(function (){
        $("#sent").fadeOut('slow');
    }, 2000)
});

$(function (){
    setTimeout(function (){
        $("#error").fadeOut('slow');
    }, 2500)
});

$(function (){
    setTimeout(function (){
        $("#error").fadeOut('slow');
    }, 2500)
});

function hoverOnRatings(number) {
    // let element = document.getElementById("star"+number);
    // element.classList.remove("fa-star-o");
    // element.classList.add("fa-star");
    let element;
    for (let i = 1; i <= number; i++) {
        element = document.getElementById("star"+i);
        element.classList.remove("fa-star-o");
        element.classList.add("fa-star");
    }
}

function leaveHoverOnRating(number) {
    let element;
    for (let i = 1; i <= number; i++) {
        element = document.getElementById("star"+i);
        element.classList.remove("fa-star");
        element.classList.add("fa-star-o");
    }
}

function setRatingAndSend(number) {
    let el = document.getElementById('ratingInput');
    el.value = number;
    let button = document.getElementById("sendRatingButton");
    button.click();
}