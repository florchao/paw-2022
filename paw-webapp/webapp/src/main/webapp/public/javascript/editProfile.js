const buttons = ["cocinar", "planchar", "menores", "mayores", "especiales", "mascotas","media","completa","cama"]
window.onload = function() {
    buttons.forEach(function(word) {
        let property = document.getElementById(word + "-cb");
        let label = document.getElementById(word + "-label");
        if (property.checked === true) {
            label.style.backgroundColor = "#c4b5fd";
            window.sessionStorage.setItem(word, "#c4b5fd");
        }
    })
};

function setColor(btn) {
    let property = document.getElementById(btn + "-cb");
    let label = document.getElementById(btn + "-label")
    if (property.checked === false) {
        label.style.backgroundColor = "#c4b5fd";
        window.sessionStorage.setItem(btn, "#c4b5fd");
    }
    else {
        label.style.backgroundColor = "#ffffff";
    }
}

var loadFile = function(event) {
    var image = document.getElementById('picture');
    image.src = URL.createObjectURL(event.target.files[0]);
};