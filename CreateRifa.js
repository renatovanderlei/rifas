let premios = []
let pIndex = 0

const bPremio = document.querySelector("[premio]")

document.forms["premio"].addEventListener("submit",function(event){

    event.preventDefault()

    const desc = document.getElementById("txtPremio").value

    premios[pIndex++] = {"descricao": desc}

    document.getElementById("txtPremio").value = ""

    const table = document.getElementById("tabelaPremio")
    const tr = document.createElement("tr")
    const td = document.createElement("td")
    td.appendChild(document.createTextNode(desc))
    tr.appendChild(td)
    table.appendChild(tr)

    
})

fetch("http://localhost:8080/apostador").then(res => res.json())
.then(json => {

    const combo = document.createElement("select")
    combo.setAttribute("class","form-control")
    combo.setAttribute("name","apostador")

    json.forEach(el => {
        
        const opt = document.createElement("option")

        opt.setAttribute("value",el.codigo)
        opt.appendChild(document.createTextNode(el.nome))

        combo.appendChild(opt)

    });

    document.getElementById("divApostador").appendChild(combo)

    document.querySelector("[submitter]").addEventListener("submit", function(event){

        event.preventDefault()

        const obj = {}

        const form = new FormData(document.forms["rifa"])

		form.forEach((value, key) => {
			obj[key] = value
		})

        obj["premios"] = premios

        const res = fetch("http://localhost:8080/rifa",{
            method: "post",
				headers: {
					'Accept': 'application/json',
					'Content-Type': 'application/json'
				},
				body: JSON.stringify(obj)
        })

    location.href = "rifas.html"

    })

})