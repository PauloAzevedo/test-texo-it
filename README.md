# test-texo-it

Projeto implementado para a atender o teste para desenvolvedor Java da empresa Texo It.

O objetivo do projeto é carregar uma lista de filmes a partir de um arquivo .csv e seu endpoint principal deve atender ao requisito abaixo:
"Obter o produtor com maior intervalo entre dois prêmios consecutivos, e o que
obteve dois prêmios mais rápido, seguindo a especificação de formato definida na
página 2;"

Foi utilizado no projeto o spring boot na versão 2.7.18 e o Java 17 como JVM base.

O desenvolvimento foi feito com a IDE Intellij na versão community.

Para rodar o projeto basta clona-lo e abrir em sua IDE de preferência.

Com o terminal disponibilizado pela IDE rode os comandos para build:
 - mvn clean install
Esse comando baixará as dependências e fará o build do projeto.
Para rodar o projeto:
 - mvn spring-boot:run
O projeto vai subir e o endereço para visualizar o resultado do endpoint principal será esse:
http://localhost:8081/movies/winners-min-max

Para rodar os testes de integração, segue o comando:
 -  mvn clean test

   
