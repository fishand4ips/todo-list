const checkboxes = document.querySelectorAll('input[type="checkbox"]')

let lastChecked;

function handleCheck(e) {
    let inBetween = false;
    if (e.shiftKey === true) {
        checkboxes.forEach(item => {
            if (item === this || item === lastChecked) {
                inBetween = !inBetween;
            }
            if (inBetween) {
                item.checked = true;
            }
        })
    }
    lastChecked = this
}
checkboxes.forEach(item => item.addEventListener('click', handleCheck));