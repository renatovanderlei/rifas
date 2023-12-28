  const divTabela = document.querySelector("[tabela]");
  const urlAPI = divTabela.getAttribute("tabela");

  fetch(urlAPI)
    .then(response => {
      if (!response.ok) {
        throw new Error(`Erro na requisição: ${response.statusText}`);
      }
      return response.json();
    })
    .then(json => {
      const table = createTable(json);
      divTabela.appendChild(table);
    })
    .catch(error => {
      console.error(error);
      displayErrorMessage("Erro ao pegar os dados");
    });

function createTable(data) {
  const table = document.createElement("table");
  table.classList.add("table");

  const columnNames = ["Código", "Data de Criação", "Valor Aposta", "Número de Apostas", "Quantidade de Aposta", "Status"];
  const columnKeys = ["codigo", "dataCriacao", "valorAposta", "quantNumeros", "apostas.length", "status"];

  const headerRow = document.createElement("tr");
  columnNames.forEach(columnName => {
    const th = document.createElement("th");
    th.appendChild(document.createTextNode(columnName));
    headerRow.appendChild(th);
  });
  table.appendChild(headerRow);

  const createDataCell = (text) => {
    const td = document.createElement("td");
    td.appendChild(document.createTextNode(text));
    return td;
  };

  // Função para formatar uma data
  const formatarData = (data) => {
    const options = { year: 'numeric', month: 'numeric', day: 'numeric', hour: 'numeric', minute: 'numeric', second: 'numeric'};
    return new Intl.DateTimeFormat('pt-BR', options).format(data);
  };

  // Função para criar uma linha da tabela
  const createRow = (rowData) => {
    const tr = document.createElement("tr");

    // Itera sobre os dados
    rowData.forEach((data, index) => {
      // Verifica se o dado é um timestamp e está na coluna "Data de Criação"
      if (columnKeys[index] === "dataCriacao" && typeof data === 'number' && !isNaN(data)) {
        // Converte o timestamp para um objeto Date
        const date = new Date(data);
        // Formata a data como necessário
        const formattedDate = formatarData(date);
        tr.appendChild(createDataCell(formattedDate));
      } else {
        tr.appendChild(createDataCell(data));
      }
    });

    return tr;
  };

  data.forEach(item => {
    const dataRow = createRow(columnKeys.map(key => key.includes(".") ? key.split(".").reduce((obj, k) => obj[k], item) : item[key]));
    table.appendChild(dataRow);
  });

  return table;
}