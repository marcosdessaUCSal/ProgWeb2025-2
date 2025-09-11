// =================================================
//      DEFINIÇÕES DE CLASSES E VARIÁVEIS GLOBAIS
// =================================================

var idCount = 0;

class Tarefa {
	constructor(id, txt, sel) {
		this.id = id;
		this.marcado = sel;
		this.texto = txt;
	}
}

var arrayTarefas = new Array();


// =================================================
//      EXIBIÇÃO DOS DADOS
// =================================================

function exibeTarefas() {
	fetch('http://localhost:8080/todo_jsp/tarefas', {
		method: 'GET'
	})
		.then(response => response.json())
		.then(data => {
			arrayTarefas.length = 0;
			data.forEach(item => {
				arrayTarefas.push(new Tarefa(item.id, item.txt, item.marcado));
			});
			mostraNaTela();
		})
		.catch(
			e => {
				// TODO: DECIDIR O QUE FAZER
			}
		);
}


// =================================================
//      CONSTRUÇÃO DE ELEMENTOS GRÁFICOS
// =================================================

function mostraNaTela() {
    let area = document.getElementById('tarefas');
    let conteudo = '';
    let registroAtual = '';
    let txt = '';
    let selecionado = false;
    let id = 0;
    for (let t of arrayTarefas) {
        id = t.id;
        txt = t.texto;
        selecionado = t.marcado;
        registroAtual = `<div class="registro">`;
        if (!selecionado) {
            registroAtual += `<input type="checkbox" onchange="mude(${id})">`;
            registroAtual += `<div class="texto">${txt}</div>`;
        } else {
            registroAtual += `<input type="checkbox" checked onchange="mude(${id})">`;
            registroAtual += `<div class="texto-riscado">${txt}</div>`;
        }
        registroAtual += `<div><img class="icone" src="assets/delete-icon.svg" onclick="remova(${id})"></div>`;
        registroAtual += `</div>`;
        conteudo += registroAtual;
    }
    area.innerHTML = conteudo;
}



// =================================================
//      ALTERAÇÕES NOS DADOS
// =================================================


function mude(id) {
    fetch(`http://localhost:8080/todo_jsp/inverteMarcado/${id}`, {
        method: 'PUT'
    })
        .then(() => {
            exibeTarefas();
        })
        .catch(
            e => {
                console.log(e);
            }
        );
}

function remova(id) {
    fetch(`http://localhost:8080/todo_jsp/delete/${id}`, {
        method: 'DELETE'
    })
        .then(() => {
            exibeTarefas();
        })
        .catch(
            e => {
                console.log(e);
            }
        );
}

function adicionarTarefa() {
    let txt = document.getElementById('entrada').value;
    document.getElementById('entrada').value = '';
    if (!validaTarefa(txt)) return;
    fetch('http://localhost:8080/todo_jsp/add', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            id: null,
            txt: txt,
            marcado: false
        })
    })
        .then(() => {
            exibeTarefas();
        })
        .catch(
            e => {
                // TODO: DECIDIR O QUE FAZER
            }
        );
}

function marcarTudo() {
    fetch('http://localhost:8080/todo_jsp/marcaTudo', {
        method: 'PUT'
    })
        .then(() => exibeTarefas())
        .catch(
            e => {
                console.log(e);
            }
        );
}

function desmarcarTudo() {
    fetch('http://localhost:8080/todo_jsp/desmarcaTudo', {
        method: 'PUT'
    })
        .then(() => exibeTarefas())
        .catch(
            e => {
                console.log(e);
            }
        );
}

function removerMarcados() {
    fetch('http://localhost:8080/todo_jsp/removeMarcados', {
        method: 'DELETE'
    })
        .then(() => exibeTarefas())
        .catch(
            e => {
                console.log(e);
            }
        );
}

function reset() {
    fetch('http://localhost:8080/todo_jsp/reset', {
        method: 'PUT'
    })
        .then(() => exibeTarefas())
        .catch(
            e => {
                console.log(e);
            }
        );
}


// Verifica se a tarefa não é vazia
function validaTarefa(txt) {
    if (txt.trim() == '') {
        return false;
    }
    return true;
}


// =================================================
//      INICIANDO TUDO
// =================================================

exibeTarefas();














