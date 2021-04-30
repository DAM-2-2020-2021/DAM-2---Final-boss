# Projecte final de grau - DAM 2 (2020-2021)

## Descripció

## Requisits
- Java 1.8

## Instal·lació i ús

## Dependències
Aquest projecte utilitza el projecte [netlib](https://github.com/DAM-2-2020-2021/netlib) per a poder comunicar dispositius a dins d'una xarxa Peer to Peer. Netlib es pot utilitzar com a dependència de Maven. Per mes informació sobre la instal·lació de netlib es pot trobar al RAEDME.md del projecte.

## Com contribuir
Per tal de mantenir un projecte ben organitzat cal establir unes normes:
- Les braques `master` i `dev` estàn protegides. Significa que no es pot pujar codi a elles directament.
- Per tal d'afegir codi al projecte, un contribuidor ha de fer el seu desenvolupament a una branca específica de la característica a desenvolupar.
- Cada desenvolupador tindra les seves tasques, per lo tant, les branques han de tenir correlació amb la tasca realitzada (ex: logs-comunicacions).
- Una vegada acabat el desenvolupament d'una característica, es realitza una [Pull Request](https://docs.github.com/en/github/collaborating-with-issues-and-pull-requests/about-pull-requests) de la branca corresponent a la branca `dev`. Un administrador revisara el codi i realitzarà el *merge*.
- Cada vegada que es pugi codi al repositori o es faci una *Pull Request*, s'executaràn tots els mètodes de Unit Testing de Maven a través de GitHub actions.
