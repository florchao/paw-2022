function validateExpYears() {
    console.log("algo")
    var el = document.getElementById('expYears');
    if (el.value ==="") {
        el.value=0;
    }
}

function validateEmptyNumberForm(id) {
    if (id === 'expYears') {
        var el = document.getElementById('expYears');
        if (el.value ==="") {
            el.value=0;
        }
    }
}
function previousPage(current) {
    var el = document.getElementById('pageNumber');
    el.value = (current - 1);
}
function nextPage(current) {
    var el = document.getElementById('pageNumber');
    el.value = (current + 1);
}
function prevPageValidation(current) {
    console.log(current);
    if (current === 0) {
        document.getElementById('prevPageButton').disabled = true;
    }
}

function setColor(btn) {
    console.log('algo')
    let property = document.getElementById(btn);
    if (property.style.backgroundColor === 'rgb(255, 255, 255)' || property.style.backgroundColor === '') {
        property.style.backgroundColor = "#c4b5fd";
        window.sessionStorage.setItem(btn, "#c4b5fd");
    }
    else {
        property.style.backgroundColor = "#ffffff";
    }
}

var loadFile = function(event) {
    var image = document.getElementById('picture');
    image.src = URL.createObjectURL(event.target.files[0]);
};