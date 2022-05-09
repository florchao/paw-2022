function validateExpYears() {
    console.log("algo")
    var el = document.getElementById('expYears');
    if (el.value ==="") {
        el.value=0;
    }
}

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

const buttons = ["cocinar", "planchar", "menores", "mayores", "especiales", "mascotas"]
window.onload = function() {
    console.log("Entre");
    buttons.forEach(function(word) {
        console.log(word)

        let property = document.getElementById(word + "-cb");
        let label = document.getElementById(word + "-label");
        console.log(property.checked)
        if (property.checked === true) {
            label.style.backgroundColor = "#c4b5fd";
            window.sessionStorage.setItem(word, "#c4b5fd");
        }
    })
};