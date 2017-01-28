# mini Projet Android

en continuité avec les TPs réalisés jusque là, application de ce qui a été vu en cours :

- Listview et Adapter
- DOM/SAX (surtout SAX) lecteurs de XML
- chargements et affichage d'images depuis url web
- gestion de gestes sur les images
- Archi MVC
- etc. 

### [Le sujet](https://drive.google.com/file/d/0B6atA2y3UObIUmxXY0RHTWdRRUE/view?usp=sharing)

<img align="center" src="http://crackberry.com/sites/crackberry.com/files/styles/large/public/topic_images/2013/ANDROID.png?itok=xhm7jaxS" height="150" />

### TODO
- revoir l'archi du projet (orga. en MVC & suppr. des trucs inutiles)
- personnaliser le style (ex. ajouter/modif' effets sur focus/appui/lâcher d'item de liste/de boutons)
- gérer images en fonct. de catégorie(?s |done, mais par une seule, pas de choix multiple)  (bonus) -> (todo?) transf. dialogue "Catégories" en activité (+choix multiple?)
- revoir limites du scroll (utile? le longPress permet de recentrer l'image si jamais elle se barre on sait pas où)
- gestion en orientation paysage (done: ajout du scroll pour les dialogues si trop gros pour écran + redimensionnement d'image en plein écran + espacements dans dialogue de filtreParCatégorie. Autre chose?)
- ajout animation onFling dans le "plein écran" (bonus) (done: mais image découpée sur l'animation. Ajouter un traitement asynchrone?)