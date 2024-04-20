
# Bonken 

Implementation of the game of Bonken in Java.

Credit program for the Programming in Java course at Faculty of Mathematics and Physics at Charles University.

Read more about the game of [Bonken on Wikipedia](https://en.wikipedia.org/wiki/Bonken).

> The rest of the readme is in Czech for purposes of the course.

## Zadání 

Zadání implementace dle odsouhlaseného emailu:

Chtěl bych implementovat hru Bonken. 
Jedná se zdvihovou karetní hru s původem v Nizozemí, pravidla hry se dají nalézt [například na Wikipedii](https://en.wikipedia.org/wiki/Bonken), implementoval bych variantu,
kde se místo minihry Domino hraje tzv. Beer card, kde hráč, který sebere károvou sedmu, ztratí 50 bodů.
(A dle tradice pocházející částečně také z bridže ostatním hráčům dluží pivo - to ale nemá vliv na implementaci). 
Jednalo by se o hru jednoho hráče, kde by protihráči byli řízeni počítačem, součástí aplikace by byl žebříček nejlepších výsledků.

Chtěl bych hru implementovat jako aplikaci s grafickým rozhraním.
Knihovny, které bych chtěl použít, by byly hlavně následující:

- JavaFx 
- JUnit 5
- log4j

Jako build systém bych chtěl použít Maven.

## Spuštění

Aplikace se spustí přes Maven

```sh
mvn javafx:run
```

Lze také spustit konzolovou verzi aplikace:

```sh
mvn clean compile exec:java -P console
```

## Uživatelská dokumentace

## Vývojářská dokumentace 


