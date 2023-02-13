# Projeto-Cataovo

<p>Projeto desenvolvido como parte do Trabalho de Conclusão de Curso da Instiruição IFPE campus recife do curso de TADS.</p>

<p>O objetivo geral deste trabalho é desenvolver uma aplicação monolítica de contagem e identificação de ovos do Aedes aegypti contidos em uma imagem, utilizando técnicas
de visão computacional.</p>

<p>O projeto possui os seguintes objetivos específicos:</p>
<ul>
<li>Desenvolver um módulo para a contagem dos ovos dos mosquitos Aedes pela ferramenta</li>
<li>Servir como parâmetro medidor da eficiência de políticas públicas de combate ao mosquito</li>
<li>Disponibilizar um banco de dados contendo as planilhas e as imagens resultantes do processamento</li>
</ul>

<p>A arquitetura monolítica foi escolhida porque um dos critérios deste projeto é ser uma aplicação do menor custo possível.</p>
<p>Outro critétio é ser uma aplicação simples de desenvolver e que seja possível executar localmente na máquina do usuário operador.</p>
<p>Entre as vantagens da arquitetura, há:
<ul>
<li>Mais simples de desenvolver: a organização fica concentrada em um único sistema;</li>
<li>Simples de testar: é possível testar a aplicação de ponta a ponta em um único lugar;</li>
<li>Simples de escalar: como é apenas uma aplicação, se necessário adicionar mais itens, simplesmente se adiciona o que for necessário.</li>
</ul>
</p>

<img src="/images/arquitetura_aplicacao_monolitica.png" alt="Imagem da arquitetura monolítica adotada no projeto" style="width:245px;height:367px"/>
<p>Fonte: https://learn.microsoft.com/pt-br/azure/architecture/microservices/migrate-monolith</p>

<h4>Estruturação dos Módulos da Aplicação</h4>
<p>O software completo é composto por três módulos:</p>
<ul>
<li>Módulo de Contagem Manual</li>
<li>Módulo de Contagem Automática</li>
<li>Módulo de Avaliação</li>
</ul>

<div>
<h4>Módulo de Contagem Manual</h4>
<section>
<p>Este é o módulo em que este trabalho irá se concentrar em desenvolver.</p>
<p>O módulo de Contagem Manual é o primeiro dos três módulos que formam a aplicação. Ele possibilita realizar a contagem e identificação dos ovos manualmente por uma pessoa operadora. A aplicação, como mostra a figura abaixo, apresenta a vantagem de abstrair a manipulação de microscópios para a contagem dos ovos, eliminando as falhas causadas pela falta de destreza na manipulação deste equipamento.</p>
<p>Ele também será usado como padrão ouro para todo o projeto, já que fica a critério do operador definir o que é ovo ou não.</p>
</section>
<img src="/images/mod_manual_1.png" alt="Imagem do Módulo de Contagem Manual" style="width:800px;height:480px"/>
</div>

<div>
<h4>Módulo de Contagem Automática</h4>
<section>
<p>No Módulo de Contagem Automática, a paleta selecionada é processada automaticamente pelo software.</p>
</section>
<img src="/images/mod_auto_1.png" alt="Imagem do Módulo de Contagem Automática" style="width:800px;height:480px"/>
</div>

<div>
<h4>Módulo de Avaliação</h4>
<section>
<p>O Módulo de Avaliação, será o responsável por realizar o cruzamento dos outros módulos e compará-los para obter uma métrica de acerto.</p>
<p>Cada um dos módulos de contagem produz um relatório final contendo os dados dos ovos encontrados. O Módulo de Avaliação faz o uso destes artefatos e, 
utilizando a contagem manual como padrão ouro, confronta os dados com a contagem automática a fim de extrair algumas métricas de acerto.</p>
</section>
<img src="/images/mod_result_1.png" alt="Imagem do Módulo de Avaliação" style="width:800px;height:480px"/>
</div>