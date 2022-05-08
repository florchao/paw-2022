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