function aplicarMascara(valor) {
    let v = valor.replace(/\D/g, "");
    if (v.length > 11) v = v.substring(0, 11);

    v = v.replace(/(\d{3})(\d)/, "$1.$2");
    v = v.replace(/(\d{3})(\d)/, "$1.$2");
    v = v.replace(/(\d{3})(\d{1,2})$/, "$1-$2");

    return v;
}

document.addEventListener('DOMContentLoaded', () => {

    document.body.addEventListener('input', (event) => {
        if (event.target.name === 'cpf') {
            event.target.value = aplicarMascara(event.target.value);
        }
    });
});
