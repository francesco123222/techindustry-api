function abrirModalEditar(id) {
    fetch('/api/main/editar/' + id)
        .then(response => response.text())
        .then(html => {
            document.getElementById('containerModal').innerHTML = html;
            const inputCpf = document.querySelector('input[name="cpf"]');
            if (inputCpf) inputCpf.value = aplicarMascara(inputCpf.value);
        })
        .catch(err => console.error('Erro ao abrir o modal de edição:', err));
}

function abrirModalDeletar(id) {
    fetch('/api/main/excluir/' + id)
    .then(response => response.text())
    .then(html => {
        document.getElementById('containerModalDeletar').innerHTML = html;
    })
    .catch(err => console.error('Erro ao abrir o modal de exclusão:', err));
}

function fecharModais() {
    document.getElementById('containerModal').innerHTML = '';
    document.getElementById('containerModalDeletar').innerHTML = '';
}

function aplicarMascara(valor) {
    let v = valor.replace(/\D/g, "");
    if (v.length > 11) v = v.substring(0, 11);

    v = v.replace(/(\d{3})(\d)/, "$1.$2");
    v = v.replace(/(\d{3})(\d)/, "$1.$2");
    v = v.replace(/(\d{3})(\d{1,2})$/, "$1-$2");

    return v;
}

document.addEventListener('DOMContentLoaded', () => {
    // 1. Monitoramento de Cliques Globais (Editar, Excluir e Cancelar)
    document.body.addEventListener('click', (event) => {
        if (event.target.classList.contains('link-editar')) {
            event.preventDefault();
            const id = event.target.getAttribute('data-id');
            abrirModalEditar(id);
        }

        if (event.target.classList.contains('link-excluir')) {
            event.preventDefault();
            const id = event.target.getAttribute('data-id');
            abrirModalDeletar(id);
        }

        if (event.target.classList.contains('btn-cancelar')) {
            event.preventDefault();
            fecharModais();
        }
    });

    // 2. Monitoramento de Digitação (Máscara de CPF em tempo real)
    document.body.addEventListener('input', (event) => {
        if (event.target.name === 'cpf') {
            event.target.value = aplicarMascara(event.target.value);
        }
    });
});
