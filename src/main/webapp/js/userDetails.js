function enableInput() {
    var elements = document.getElementsByTagName('input');
    var button = document.getElementById("submit-button");

    for (var i = 0, element; element = elements[i++];) {
            element.disabled = false;
    }

    // set save button enable
    button.disabled = false;
}
