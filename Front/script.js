function submitLogin() {
    $.ajax({
        url: 'http://127.0.0.1:7200/verificarConta',

        type: 'POST',

        data: ({
            user: $('#login').val(),
            senha: $('#senha').val()
        }),

        success: function (response) {
            if (response.status == 1) {
                switch (response.permissao) {
                    case 1:
                        window.location.href = 'garcom.html';
                        break;
                    case 2:
                        window.location.href = 'caixa.html';
                        break;
                    case 3:

                        window.location.href = "manutencao.html";
                        break;
                }
            }
            else if (response.status == 2)
                alert('Senha incorreta!');
            else if (response.status == 3)
                alert('Essa conta não consta nos nossos dados');
            else if (response.status == 0) {
                console.log(response.type);
                console.log(response.stackTrace);
            }
        },

        error: function (event) {
        }

    })
}

function cadastrarFuncionario() {

    if ($('#loginCadastro').val() != "" && $('#senhaCadastro').val() != "" && $('#tipoCadastro').val() != "")

        $.ajax({
            url: 'http://127.0.0.1:7200/criarConta',

            type: 'POST',

            data: ({
                user: $('#loginCadastro').val(),
                senha: $('#senhaCadastro').val(),
                permissao: $('#tipoCadastro').val()
            }),

            success: function (response) {
                if (response.status == 0) {
                    console.log(response.type);
                    console.log(response.stackTrace);
                    alert("Funcionário não foi cadastrado devido a um erro!");
                }
                else if (response.status == 1) {
                    console.log(response.message);
                    $('#loginCadastro').val('');
                    $('#senhaCadastro').val('');
                    $('#tipoCadastro').val('');
                    alert("Funcionário cadastrado!");
                }
            },

            error: function (event) {
                alert("Funcionário não foi cadastrado devido a um erro!");
            }

        })

    else
        alert("Preencha os dados corretamente!");
}


function submitNovoProduto() {

    console.log(calculaCategoria());

    if ($('#produto').val() != "" && $('#descricao').val() != "" && $('#codigo').val() != "" && $('#preco').val() != "")

        $.ajax({
            url: 'http://127.0.0.1:7200/adicionaProduto',

            type: 'POST',

            data: ({
                nome: $('#produto').val(),
                descricao: $('#descricao').val(),
                disponivel: true,
                codigo: $('#codigo').val(),
                preco: $('#preco').val(),
                categoria: calculaCategoria()
            }),

            success: function (response) {
                if (response.status == 0) {
                    console.log(response.type);
                    console.log(response.stackTrace);
                    alert('Erro ao tentar adicionar o produto');
                }
                else if (response.status == 1) {
                    $('#produto').val('');
                    $('#descricao').val('');
                    $('#codigo').val('');
                    $('#preco').val('');
                    alert("Produto adicionado com sucesso!");
                    console.log(response.message);
                }

            },

            error: function (event) {
            }
        });

    else
        alert('Preencha os dados corretamente');
}

function excluirProduto() {

    if ($('#produto').val() != "")

        $.ajax({
            url: 'http://127.0.0.1:7200/excluirProduto',

            type: 'POST',

            data: ({
                nome: $('#produto').val()
            }),

            success: function (response) {
                if (response.status == 0) {
                    console.log(response.type);
                    console.log(response.stackTrace);
                    alert('Houve um erro ao tentar remover o produto!');
                }
                else if (response.status == 1) {
                    $('#produto').val('');
                    alert('Produto removido com sucesso!');
                    console.log(response.message);
                }
            },

            error: function (event) {
            }
        });

    else
        alert('Preencha o campo corretamente!')
}

function alterarProduto() {

    if ($('#produto').val() != "" && $('#descricao').val() != "" && $('#codigo').val() != "" && $('#preco').val() != "")

        $.ajax({
            url: 'http://127.0.0.1:7200/alterarProduto',

            type: 'POST',

            data: ({
                nome: $('#produto').val(),
                descricao: $('#descricao').val(),
                codigo: $('#codigo').val(),
                preco: $('#preco').val(),
                categoria: calculaCategoria()
            }),

            success: function (response) {
                if (response.status == 0) {
                    console.log(response.type);
                    console.log(response.stackTrace);
                    alert('Houve um erro ao tentar alterar o produto!');
                }
                else if (response.status == 1) {
                    $('#produto').val('');
                    $('#descricao').val('');
                    $('#codigo').val('');
                    $('#preco').val('');
                    alert('Produto alterado com sucesso!');
                    console.log(response.message);
                }
            },

            error: function (event) {
            }
        });

    else
        alert('Preencha os campos corretamente!');
}

function imprimePraMim() {

    console.log('Yeah');

    $.ajax({
        url: 'http://127.0.0.1:7200/imprimeTudo',

        type: 'POST',

        data: ({
        }),

        success: function (response) {
            if (response.status == 0) {
                console.log('Status 0');
                console.log(response.type);
            }

            else if (response.status == 1) {
                console.log('Status 1');
                console.log(response.produtos[0]);
                var dados = '';
                response.produtos.forEach(element => {
                    dados += `<div class="produtoVisualizar"> Nome: ${element.nome} <br>
                    Descrição: ${element.descricao} <br> Codigo: ${element.codigo} <br>
                    Preço: R$${element.preco} <br> Categoria: ${element.categoria} </div>`
                });
                $('#dados').html(dados);
            }
            else if (response.status == 2) {
                console.log('Status 2');
                alert(response.message);
            }
        },

        error: function (event) {
        }
    })

}

function calculaCategoria() {

    var retorno;

    [].forEach.call(document.getElementsByClassName('categoriaRadio'), elemento => {
        if (elemento.checked)
            retorno = elemento.value;
    })

    return retorno;
}

function checaMesaCliente() {
    if ($('#mesa').val() != '') {
        $.ajax({
            url: 'http://127.0.0.1:7200/selecionaMesaCliente',

            type: 'POST',

            data: ({
                mesa: $('#mesa').val()
            }),

            success: function (response) {
                if (response.status == 0) {
                    console.log('Status 0');
                    console.log(response.type);
                    window.alert('Não foi possível encontrar a mesa!')
                }

                else if (response.status == 1) {
                    sessionStorage.setItem('mesa', $('#mesa').val());
                    window.location.href = 'clienteAcoes.html'
                }
            },

            error: function (event) {

            }

        })
    } else {
        window.alert('Preencha o campo corretamente!');
    }
}

function carregaComandas() {

    $.ajax({
        url: 'http://127.0.0.1:7200/pegaComandasAbertas',

        type: 'POST',

        data: ({
        }),

        success: function (response) {

            if (response.status == 0) {
                console.log('Status 0');
                console.log(response.type);
                window.alert('Não foi possível carregar as comandas já usadas');
            }

            else if (response.status == 1) {

                var dados = $('#comandas').html();

                if (response.comandas.length == 0)
                    dados += 'Não há comandas cadastradas ainda.';
                else {
                    response.comandas.forEach(element =>
                        dados += `<div class="comandaNumero"> Número: ${element.id} <br>
                        Mesa: ${element.mesa} </div>`)
                };
                document.getElementById('comandas').innerHTML = dados;
            }
        },

        error: function (event) {
            console.log('Deu erro');
        }
    })
}

function abrirComanda() {
    if ($('#mesaComanda').val() != '')
        $.ajax({
            url: 'http://127.0.0.1:7200/abreComanda',

            type: 'POST',

            data: ({
                mesa: $('#mesaComanda').val()
            }),

            success: function (response) {

                if (response.status == 1) {
                    $('#mesaComanda').val('');
                    alert('Comanda aberta com sucesso!');
                    window.location.href = 'garcom.html';
                }

                else if (response.status == 0) {
                    console.log('Status 0');
                    console.log(response.type);
                    window.alert('Não foi possível abrir a comanda!');
                }
            },

            error: function (event) {
                console.log('Deu erro');
            }
        })
    else
        window.alert('Preencha o campo corretamente!');
}

function fecharComanda() {
    if ($('#mesaComandaFechar').val() != '') {
        $.ajax({
            url: 'http://127.0.0.1:7200/fechaComanda',

            type: 'POST',

            data: ({
                mesa: $('#mesaComandaFechar').val()
            }),

            success: function (response) {

                if (response.status == 1) {
                    $('#mesaComandaFechar').val('');
                    alert('Comanda fechada com sucesso!');
                    window.location.href = 'garcom.html';
                }
                else if (response.status == 0) {
                    console.log('Status 0');
                    console.log(response.type);
                    window.alert('Não foi possível abrir a comanda!');
                }
            },

            error: function (event) {
                console.log('Deu erro');
            }
        })
    }
    else
        window.alert('Preencha o campo corretamente!');
}

function loadaPedido() {

    $('#pessoas').val('');
    $('#mesa').val('');
    $('#nmroProdutos').val('');

    var inputPessoas = document.getElementById('pessoas');

    inputPessoas.addEventListener('input', function () {
        var add = document.getElementById('add');

        var nPeassoas = parseInt(this.value);

        var htmlText = `<div class="input" id="input_nomePessoasAdicionarPedido">
                    <label>NOME:</label>
                        <input class="nomePessoas" type="text" name="nomePessoas"/>
                    </div>`;

        for (var i = 0; i < nPeassoas; i++) {
            add.innerHTML += htmlText;
        }
    })

    document.getElementById('nmroProdutos').addEventListener('input', function () {

        var add = document.getElementById('addProduto');

        var nProdutos = parseInt(this.value);

        var htmlText = `<div class="input" id="input_nomeProdutoAdicionarPedido">
        <label>ID:</label>
            <input class="idProduto" type="number" name="idProduto"/>
        </div>`;

        for (var i = 0; i < nProdutos; i++) {
            add.innerHTML += htmlText;
        }
    })
}

function adicionaPedido() {

    console.log('Chegou');

    var pessoasLista = [];

    [].forEach.call(document.getElementsByClassName('nomePessoas'), elemento => {
        pessoasLista.push(elemento.value);
    });

    var produtosListas = [];

    [].forEach.call(document.getElementsByClassName('idProduto'), elemento => {
        produtosListas.push(elemento.value);
    });

    var pessoasString = "";
    var produtosString = "";

    pessoasLista.forEach((pessoa) => {
        pessoasString += (pessoa + "/");
    });

    pessoasString = pessoasString.substring(0, pessoasString.length - 1);

    produtosListas.forEach((produto) => {
        produtosString += (produto + "/");
    });

    produtosString = produtosString.substring(0, produtosString.length - 1);

    if ($('#pessoas').val() != '' && $('#mesa').val() != '' && $('#nmroProdutos').val() != '') {
        $.ajax({
            url: 'http://127.0.0.1:7200/adicionarPedido',

            type: 'POST',

            data: ({
                mesa: $('#mesa').val(),
                pessoas: pessoasString,
                produtos: produtosString
            }),

            success: function (response) {
                if (response.status == 1) {
                    $('#pessoas').val('');
                    $('#mesa').val('');
                    $('#nmroProdutos').val('');
                    window.alert('Pedido adicionado com sucesso!');
                    location.href = 'garcom.html';
                }

                if (response.status == 0) {
                    console.log('Status 0');
                    console.log(response.type);
                    window.alert('Houve um erro ao tentar adicionar o pedido!');
                }
            },

            error: function (event) {
                console.log('Deu erro');
            }
        })
    } else {
        window.alert('Preencha os dados corretamente!');
    }
}

function adicionaSolicitante() {
    if ($('#mesaSolicitante').val() != "" && $('#nomeSolicitante').val() != "") {
        $.ajax({
            url: 'http://127.0.0.1:7200/adicionarSolicitante',

            type: 'POST',

            data: ({
                mesa: $('#mesaSolicitante').val(),
                solicitante: $('#nomeSolicitante').val()
            }),

            success: function (response) {
                if (response.status == 1) {
                    $('#mesaSolicitante').val('');
                    $('#nomeSolicitante').val('');
                    window.alert('Solicitante adicionado com sucesso!');
                }
                else if (response.status == 0) {
                    console.log('Status 0');
                    console.log(reponse.type);
                    window.alert('Houve um erro ao tentar adicionar o solicitante!');
                }
                else if (response.status == 2) {
                    window.alert('Essa mesa não consta nos nossos arquivos!');
                }

                else if (response.status == 3) {
                    window.alert('Algum dos produtos está com ID errado!')
                }
            },

            error: function (event) {
                console.log('Deu erro');
            }
        })


    } else {
        window.alert('Preencha os dados corretamente!');
    }

}

function loadaCaixa() {
    $.ajax({
        url: 'http://127.0.0.1:7200/pegaComandasFechadas',

        type: 'POST',

        data: ({
        }),

        success: function (response) {
            if (response.status == 1) {
                var dados = '';
                if (response.mesas.length == 0) {
                    dados += 'Não há comandas cadastradas!';
                }
                else {
                    response.mesas.forEach(element => {
                        dados += `<div class='comandaFechada'>
                        Mesa ${element}
                        </div>
                        <button class='botaoImprimir' onclick='imprimeComanda(${element})'> Imprimir </button>`
                    });
                };
                document.getElementById('listaComandasFechadas').innerHTML = dados;
            }
            else if (response.status == 0) {
                console.log('Status 0');
                console.log(reponse.type);
                window.alert('Houve um erro ao tentar pegar as comandas fechadas!');
            }
        },

        error: function (event) {
            console.log('Deu erro');
        }
    })
}

function imprimeComanda(mesa) {
    $.ajax({
        url: 'http://127.0.0.1:7200/imprimeComanda',

        type: 'POST',

        data: ({
            mesa: mesa
        }),

        success: function (response) {
            if (response.status == 1) {
                window.open(`../Back/${response.arquivo}`);
            }
            else if (response.status == 0) {
                console.log('Status 0');
                console.log(reponse.type);
                window.alert('Houve um erro ao tentar imprimir dados dessa comanda!');
            }
        },

        error: function (event) {
            console.log('Deu erro! A requisição Ajax não foi enviada! Imprimir comanda');
        }
    })
}