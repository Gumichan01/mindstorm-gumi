# Feuille de route #

## Sélection des outils de développement ##

Repérer les logiciels pour développer sur la plateforme

Liste des outils candidats:

 - [LeJOS](http://www.lejos.org/)
 - [BricxCC](http://bricxcc.sourceforge.net/)


Outil sélectionné: LeJOS

## Journal ##

**Mise à jour : 19 novembre**
 -l'étalonnage ne marche pas, des difficultés à comprendre et à le programmer.
 -le robot perçois qu'une "trame de couleur" mais pas totalement, il ne s'adapte pas à
la lumibnosité

**Mise à jour : 20 novembre 2015**
- des difficultés dans la detection des couleurs plus précisement faire l'étalonnage.
- des difficultés dans le codage de la couleur noir ,blanc et vert,
plus pécisement arriver à faire en sorte de respecter la tanche des couleurs
-l'étalonnage est fait en parti.

**Mise à jour : 21 novembre 2015**
  - ajout du fichier detection.java
  - toujours autant de difficultés a faire fonctionner l'étalonnage,
cela ne fait pas encore ce que l'on veut.


**Mise à jour : 24 novembre 2015**
  - Etalonnage presque terminé, il faut juste définir un epsilon qui sera l'écart-type
  - Le programme de parcours prendra les valeurs mesurées comme valeurs
de référence et utilisera l'epsilon pour déterminer si la couleur capté
(modulo l'epsilon) est bien celle désiré pour effectuer les opérations adéquates
  - Il sera peut-être nécessaire d'utiliser deux capteurs pour notamment savoir
vers où dévie le robot afin d'effectuer une correction de trajectoire adéquate


**Mise à jour : 26 novembre 2015**
  - Garder les moyennes. Lors du suivi de ligne, prendre les mesures et
calculer dynamiquement l'ecart-type pour chaque canal.
On calcule une moyenne *meta_moy* des trois canaux de couleur (*moy_r*,*moy_g*,*moy_b*)
et aussi une moyenne *sigma_moy* des trois écart-types (*sigma_r*,*sigma_g*,*sigma_b*).
Si *sigma_moy* < *meta_moy* alors la couleur est bonne. (A tester)


**Mise à jour : 11 décembre 2015**

  - Pour l'étalonnage, travailler en terme de coordonnées.
  - Etalonnage de la couleur de la ligne + couleur du fond.

Avancer bêtement selon la couleur, on prend un certain nombre de mesures,
on prend la médiane.
  - Test couleur : Plusieurs couleurs (~5-8 mesures) dans un tableau
Calcul coordonnées rgb + distance euclidiene dans l'espace.
Si la position est plus proche de la couleur de la ligne que du fond -> OK,
sinon KO.

  - Suivi de ligne : aller vers le bord.
Si on est sur le bord -> avancer
Sinon -> continuer à chercher

Lorsqu'on est sur le bord: Avancer...
Si la couleur capter vire vers le fond -> revenir vers la ligne
Si la couleur capter vire vers la ligne -> revenir vers le bord

**Mise à jour : 22 décembre 2015**
- étalonnage en cours, nouvelle version adaptée des remaques des profs


**Mise à jour : 24 Janvier 2016**
- Définition d'une heuristique de suivi de ligne.

**Mise à jour : 28 Janvier 2016**
- Correction au niveau de la detection de couleur durant le parcours.

**Mise à jour : 20 mars 2016  (oui oui c'était long)**

Calcul de la distance parcourue par le robot.

- Diamètre de chaque roue : 5,5 cm.
- Rayon de chaque roue    : 2,75 cm.
- Périmètre de la roue    : 17,28 cm.

Soit *Vg* et *Vr* les vitesses respectives du moteur gauche et du moteur droit,
et *t* le temps.

La distance parcouru par le robot, noté *D*, s'exprime de la manière suivante :

    D = ((Vg + Vr)/2) * t;

***TODO***

- Calcul de la distance parcourue par le robot.
- Calcul angle pour un moteur qui ne tourne pas et l'autre si.
- Calcul des coordonnées (dans quel repère ?).
- Calcul de trajectoire.


## Documentations ##

 - [Connexion vers le cerveau](https://github.com/ev3dev/ev3dev/wiki/Setting-Up-Windows-USB-Ethernet-Networking)
 - [API EV3](http://www.lejos.org/ev3/docs/)
 - [Connexion EV3 : obtenir adresse](http://knowledge.autodesk.com/customer-service/network-license-administration/install-and-configure-network-license/requesting-license-file/finding-your-host-name-and-id)
