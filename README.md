# Projeto-Cataovo

<span lang="en">
<p>Project developed as part of the Course Completion Work of the IFPE campus recife institution of the TADS course.</p>
<p>The general objective of this work is to develop a monolithic application for counting and identifying Aedes aegypti eggs contained in an image, using techniques
computer vision.</p>

<p>The project has the following specific objectives:</p>
<ul>
<li>Develop a module for counting Aedes mosquito eggs using the tool</li>
<li>Serve as a measuring parameter of the efficiency of public policies to combat the mosquito</li>
<li>Provide a database containing the worksheets and images resulting from the processing</li>
</ul>

<p>To use this project you will need to install OpenCV locally</p>
<h5>Instructions:</h5>
<ul>
<li>Access the <a href="https://opencv.org/releases/">OpenCV website</a> in the required release (4.7.0 in this project).</li>
<li>Download and install the option corresponding to your operating system (Windows in this project)</li>
<li>Once installed, include the OpenCV library in the project in this path: (installation location)/opencv/build/java/opencv-470.jar</li>
<li>Change the "run.jvmargs" property in project.properties to identify locally installed OpenCV: (installation path)/opencv/build/java/x64 </li>
<li>The previous property can also be changed within Netbeans by following the path:
     <ul>
         <li>Right click on the project</li>
         <li>Access "Properties"</li>
         <li>When Opening, Access "Execute"</li>
         <li>Opening the option, you must change the VM Options field to: -Djava.library.path="(installation path)opencv\build\java\x64" --enable-preview</li>
         <li>Note: The OpenCV library is not prepared for Portuguese accents. Therefore, care should be taken when choosing where to save palette images.</li>
     </ul>
</li>
</ul>

<p>The monolithic architecture was chosen because one of the criteria of this project is to be an application with the lowest possible cost.</p>
<p>Another criterion is to be an application that is simple to develop and that can be run locally on the operator user's machine.</p>
<p>Among the advantages of architecture there are:
<ul>
<li>Simpler to develop: the organization is concentrated in a single system;</li>
<li>Simple to test: you can test the application end-to-end in one place;</li>
<li>Simple to scale: as it is only one application, if you need to add more items, you simply add what is needed.</li>
</ul>
</p>

<img src="/images/arquitetura_aplicacao_monolitica.png" alt="Image of the monolithic architecture adopted in the project" style="width:245px;height:367px"/>
<p>Source: https://learn.microsoft.com/pt-br/azure/architecture/microservices/migrate-monolith</p>

<h4>Structuring the Application Modules</h4>
<p>The complete software consists of three modules:</p>
<ul>
<li>Manual Counting Module</li>
<li>Automatic Counting Module</li>
<li>Evaluation Module</li>
</ul>
 
<div>
<h4>Manual Counting Module</h4>
<section>
<p>This is the module that this work will focus on developing.</p>
<p>The Manual Counting module is the first of the three modules that make up the application. It makes it possible to carry out the counting and identification of eggs manually by an operator. The application, as shown in the figure below, has the advantage of abstracting the manipulation of microscopes for counting eggs, eliminating failures caused by lack of dexterity in handling this equipment.</p>
<p>It will also be used as the gold standard for the entire project, as it is up to the operator to define what is egg or not.</p>
</section>
<img src="/images/mod_manual_2.png" alt="Manual Counting Module Image" style="width:600px;height:400px"/>
</div>

<div>
<h4>Automatic Counting Module</h4>
<section>
<p>In the Automatic Counting Module, the selected palette is processed automatically by the software.</p>
</section>
<img src="/images/mod_auto_2.png" alt="Auto Counting Module Image" style="width:600px;height:400px"/>
</div>

<div>
<h4>Evaluation Module</h4>
<section>
<p>The Evaluation Module will be responsible for crossing the other modules and comparing them to obtain a success metric.</p>
<p>Each of the counting modules produces a final report containing data on eggs found. The Assessment Module makes use of these artifacts and,
using manual counting as the gold standard, it confronts the data with automatic counting in order to extract some hit metrics.</p>
</section>
<img src="/images/mod_result_2.png" alt="Assessment Module Image" style="width:600px;height:400px"/>
</div>
</span>
<br/> <br/>
<span lang="pt-BR">
<p>Projeto desenvolvido como parte do Trabalho de Conclusão de Curso da Instiruição IFPE campus recife do curso de TADS.</p>
<p>O objetivo geral deste trabalho é desenvolver uma aplicação monolítica de contagem e identificação de ovos do Aedes aegypti contidos em uma imagem, utilizando técnicas
de visão computacional.</p>

<p>O projeto possui os seguintes objetivos específicos:</p>
<ul>
<li>Desenvolver um módulo para a contagem dos ovos dos mosquitos Aedes pela ferramenta</li>
<li>Servir como parâmetro medidor da eficiência de políticas públicas de combate ao mosquito</li>
<li>Disponibilizar um banco de dados contendo as planilhas e as imagens resultantes do processamento</li>
</ul>

<p>Para utilizar este projeto será necessário instalar o OpenCV na localmente</p>
<h5>Instruções:</h5>
<ul>
<li>Acessar o <a href="https://opencv.org/releases/">site</a> do OpenCV na release necessária (4.7.0 neste projeto).</li>
<li>Baixar e instalar a opção correspondente ao seu sistema operacional (Windows neste projeto)</li>
<li>Após instalado, incluir a biblioteca do OpenCV no projeto neste caminho: (local de instalação)/opencv/build/java/opencv-470.jar</li>
<li>Alterar a propriedade "run.jvmargs" em project.properties para identificar o OpenCV instalado localmente: (caminho de instalação)/opencv/build/java/x64 </li>
<li>A propriedade anterior também pode ser alterada dentro do Netbeans seguindo o caminho: 
    <ul>
        <li>Clique no botão direito no projeto</li>
        <li>Acessar "Propriedades"</li>
        <li>Ao Abrir, Acessar "Executar"</li>
        <li>Abrindo a opção, deve-se alterar no campo VM Options para: -Djava.library.path="(caminho de instalação)opencv\build\java\x64" --enable-preview</li>
        <li>Obs: A biblioteca do OpenCV não está preparada para acentuação em portugês. Portanto, deve-se ter cuidado ao escolher onde salvar as imagens das paletas.</li>
    </ul>
</li>
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
<img src="/images/mod_manual_2.png" alt="Imagem do Módulo de Contagem Manual" style="width:600px;height:400px"/>
</div>

<div>
<h4>Módulo de Contagem Automática</h4>
<section>
<p>No Módulo de Contagem Automática, a paleta selecionada é processada automaticamente pelo software.</p>
</section>
<img src="/images/mod_auto_2.png" alt="Imagem do Módulo de Contagem Automática" style="width:600px;height:400px"/>
</div>

<div>
<h4>Módulo de Avaliação</h4>
<section>
<p>O Módulo de Avaliação, será o responsável por realizar o cruzamento dos outros módulos e compará-los para obter uma métrica de acerto.</p>
<p>Cada um dos módulos de contagem produz um relatório final contendo os dados dos ovos encontrados. O Módulo de Avaliação faz o uso destes artefatos e, 
utilizando a contagem manual como padrão ouro, confronta os dados com a contagem automática a fim de extrair algumas métricas de acerto.</p>
</section>
<img src="/images/mod_result_2.png" alt="Imagem do Módulo de Avaliação" style="width:600px;height:400px"/>
</div>
</span>
<br/> <br/>

<span lang="es">
<p>Proyecto desarrollado como parte del Trabajo de Finalización de Curso de la institución IFPE campus recife del curso TADS.</p>
<p>El objetivo general de este trabajo es desarrollar una aplicación monolítica para el conteo e identificación de huevos de Aedes aegypti contenidos en una imagen, utilizando técnicas
visión artificial.</p>

<p>El proyecto tiene los siguientes objetivos específicos:</p>
<ul>
<li>Desarrolle un módulo para contar huevos de mosquito Aedes usando la herramienta</li>
<li>Servir como parámetro de medición de la eficiencia de las políticas públicas para combatir el mosquito</li>
<li>Proporcionar una base de datos que contenga las hojas de trabajo y las imágenes resultantes del procesamiento</li>
</ul>

<p>Para usar este proyecto necesitarás instalar OpenCV localmente</p>
<h5>Instrucciones:</h5>
<ul>
<li>Acceda al <a href="https://opencv.org/releases/">sitio web de OpenCV</a> en la versión requerida (4.7.0 en este proyecto).</li>
<li>Descargue e instale la opción correspondiente a su sistema operativo (Windows en este proyecto)</li>
<li>Una vez instalada, incluya la biblioteca OpenCV en el proyecto en esta ruta: (ubicación de instalación)/opencv/build/java/opencv-470.jar</li>
<li>Cambie la propiedad "run.jvmargs" en project.properties para identificar OpenCV instalado localmente: (ruta de instalación)/opencv/build/java/x64 </li>
<li>La propiedad anterior también se puede cambiar dentro de Netbeans siguiendo la ruta:
     <ul>
         <li>Haga clic derecho en el proyecto</li>
         <li>Acceda a "Propiedades"</li>
         <li>Al abrir, acceda a "Ejecutar"</li>
         <li>Al abrir la opción, debe cambiar el campo Opciones de VM a: -Djava.library.path="(ruta de instalación)opencv\build\java\x64" --enable-preview</li>
         <li>Nota: La biblioteca OpenCV no está preparada para acentos portugueses. Por lo tanto, se debe tener cuidado al elegir dónde guardar las imágenes de la paleta.</li>
     </ul>
</li>
</ul>

<p>Se eligió la arquitectura monolítica porque uno de los criterios de este proyecto es ser una aplicación con el menor costo posible.</p>
<p>Otro criterio es ser una aplicación que sea simple de desarrollar y que pueda ejecutarse localmente en la máquina del usuario operador.</p>
<p>Entre las ventajas de la arquitectura se encuentran:
<ul>
<li>Más simple de desarrollar: la organización se concentra en un solo sistema;</li>
<li>Fácil de probar: puede probar la aplicación de principio a fin en un solo lugar;</li>
<li>Fácil de escalar: como es solo una aplicación, si necesita agregar más elementos, simplemente agregue lo que se necesita.</li>
</ul>
</p>

<img src="/images/arquitetura_aplicacao_monolitica.png" alt="Imagen de la arquitectura monolítica adoptada en el proyecto" style="width:245px;height:367px"/>
<p>Fuente: https://learn.microsoft.com/pt-br/azure/architecture/microservices/migrate-monolith</p>

<h4>Estructuración de los módulos de la aplicación</h4>
<p>El software completo consta de tres módulos:</p>
<ul>
<li>Módulo de conteo manual</li>
<li>Módulo de conteo automático</li>
<li>Módulo de Evaluación</li>
</ul>
 
<div>
<h4>Módulo de conteo manual</h4>
<sección>
<p>Este es el módulo en el que este trabajo se centrará en desarrollar.</p>
<p>El módulo de Conteo Manual es el primero de los tres módulos que componen la aplicación. Permite realizar el conteo e identificación de huevos manualmente por un operario. La aplicación, como se muestra en la siguiente figura, tiene la ventaja de abstraer la manipulación de microscopios para el conteo de huevos, eliminando fallas causadas por falta de destreza en el manejo de este equipo.</p>
<p>También se usará como el estándar de oro para todo el proyecto, ya que depende del operador definir qué es huevo o no.</p>
</sección>
<img src="/images/mod_manual_2.png" alt="Imagen del módulo de conteo manual" style="width:600px;height:400px"/>
</div>

<div>
<h4>Módulo de conteo automático</h4>
<sección>
<p>En el módulo de conteo automático, la paleta seleccionada es procesada automáticamente por el software.</p>
</sección>
<img src="/images/mod_auto_2.png" alt="Imagen del módulo de conteo automático" style="width:600px;height:400px"/>
</div>

<div>
<h4>Módulo de Evaluación</h4>
<sección>
<p>El Módulo de Evaluación se encargará de cruzar los demás módulos y compararlos para obtener una métrica de éxito.</p>
<p>Cada uno de los módulos de conteo produce un informe final que contiene datos sobre los huevos encontrados. El módulo de evaluación hace uso de estos artefactos y,
utilizando el conteo manual como estándar de oro, confronta los datos con el conteo automático para extraer algunas métricas de aciertos.</p>
</sección>
<img src="/images/mod_result_2.png" alt="Imagen del módulo de evaluación" style="width:600px;height:400px"/>
</div>
</span>