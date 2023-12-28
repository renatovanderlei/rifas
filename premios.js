const divTabela = document.querySelector("[tabela]");
const urlAPI = divTabela.getAttribute("tabela");

// Função para criar uma célula de cabeçalho
const createHeaderCell = (text) => {
    const th = document.createElement("th");
    th.appendChild(document.createTextNode(text));
    return th;
};

// Função para criar uma célula de dados
const createDataCell = (text) => {
    const td = document.createElement("td");
    td.appendChild(document.createTextNode(text));
    return td;
};

// Função para criar uma linha da tabela
const createRow = (rowData) => {
    const tr = document.createElement("tr");
    rowData.forEach(data => {
        tr.appendChild(createDataCell(data));
    });
    return tr;
};


// Função para criar a tabela com base nos dados JSON
const createTable = (jsonData) => {
    const table = document.createElement("table");
    table.classList.add("table");

    // Cabeçalho da tabela
    const headerRow = createRow(["Código Premio", "Descrição", "Código Rifa", "Status"]);
    table.appendChild(headerRow);

    // Linhas de dados
    jsonData.forEach(el => {
        const dataRow = createRow([el.codigo, el.descricao, el.codigo_rifa, el.status]);
        table.appendChild(dataRow);
    });

    return table;
};

// Função para preparar a visualização com base no código
const prepararVisualizacao = function (codigo) {
    fetch(`http://localhost:8080/premios/${codigo}`)
        .then(response => {
            if (response.status === 500) {
                alert("Não foi possível recuperar os dados");
            }
            return response.json();
        })
        .then(json => {
            // Atualizar elementos HTML com os dados recuperados
            document.getElementById("codigo_premio").innerHTML = json.codigo_premio;
            document.getElementById("nome").innerHTML = json.descricao;
            document.getElementById("codigo_fk_Rifa").innerHTML = json.codigo_rifa;
            document.getElementById("status").innerHTML = json.status;
        })
        .catch(erro => {
            console.error(erro);
        });
};

// Realizar a requisição e criar a tabela ao receber os dados
fetch(urlAPI)
    .then(response => response.json())
    .then(jsonData => {
        const table = createTable(jsonData);
        divTabela.appendChild(table);
    })
    .catch(error => {
        console.error(error);
    });
