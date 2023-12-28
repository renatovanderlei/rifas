const divTabela = document.querySelector("[tabela]");
const urlAPI = divTabela.getAttribute("tabela");

function criarElemento(tag, conteudo) {
  const elemento = document.createElement(tag);
  elemento.innerHTML = conteudo;
  return elemento;
}

function criarBotaoVisualizar(codigo) {
  const button = document.createElement("button");
  button.appendChild(document.createTextNode("Visualizar"));
  button.setAttribute("data-bs-toggle", "modal");
  button.setAttribute("data-bs-target", "#modalVisual");
  button.setAttribute("class", "btn");
  button.addEventListener("click", () => prepararVisualizacao(codigo));
  return button;
}

function criarLinhaTabela(dados) {
  const tr = document.createElement("tr");

  Object.values(dados).forEach((valor) => {
    const td = document.createElement("td");
    td.innerHTML = valor;
    tr.appendChild(td);
  });

  const operacoesTd = document.createElement("td");
  operacoesTd.appendChild(criarBotaoVisualizar(dados.codigo));
  tr.appendChild(operacoesTd);

  return tr;
}

function montarTabela(dados) {
  const table = document.createElement("table");
  table.classList.add("table");

  const cabecalho = criarElemento("tr", "");
  const titulos = ["Código", "Nome", "Localidade", "Whatsapp", "E-mail", "Operações"];

  titulos.forEach((titulo) => {
    cabecalho.appendChild(criarElemento("th", titulo));
  });

  table.appendChild(cabecalho);

  dados.forEach((el) => {
    table.appendChild(criarLinhaTabela(el));
  });

  divTabela.appendChild(table);
}

fetch(urlAPI)
  .then((response) => {
    if (!response.ok) {
      throw new Error("Erro ao obter os dados da API");
    }
    return response.json();
  })
  .then((resposta) => {
    montarTabela(resposta);
  })
  .catch((error) => {
    console.error(error);
  });

document.querySelector("[submitter]").addEventListener("submit", async function (event) {
  event.preventDefault();

  const form = new FormData(document.forms[0]);
  const obj = Object.fromEntries(form);

  try {
    const response = await fetch("http://localhost:8080/apostador", {
      method: "POST",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
      },
      body: JSON.stringify(obj),
    });

    if (!response.ok) {
      throw new Error("Não foi possível realizar o cadastro");
    }

    location.reload();
  } catch (error) {
    console.error(error);
    alert("Não foi possível realizar o cadastro");
  }
});

async function prepararVisualizacao(codigo) {
  try {
    const response = await fetch(`http://localhost:8080/apostador/${codigo}`);

    if (!response.ok) {
      throw new Error("Não foi possível recuperar os dados");
    }

    const json = await response.json();

    document.getElementById("codigo").innerHTML = json.codigo;
    document.getElementById("nome").innerHTML = json.nome;
    document.getElementById("localidade").innerHTML = json.localidade;
    document.getElementById("whatsapp").innerHTML = json.whatsapp;
    document.getElementById("email").innerHTML = json.email;
  } catch (error) {
    console.error(error);
    alert("Não foi possível recuperar os dados");
  }
}
