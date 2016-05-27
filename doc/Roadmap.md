# Feuille de route #

## Sélection des outils de développement ##

Repérer les logiciels pour développer sur la plateforme

Liste des outils candidats:

 - [LeJOS](http://www.lejos.org/)
 - [BricxCC](http://bricxcc.sourceforge.net/)


Outil sélectionné: LeJOS

## Journal ##

**Mise à jour : 19 novembre**
 - l'étalonnage ne marche pas, des difficultés à comprendre et à le programmer.
 - le robot perçoit qu'une "trame de couleur" mais pas totalement,
 il ne s'adapte pas à la luminosité

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

> Calcul de la distance parcouruee par le robot.

Pour cd calcul on mesure la distance parcouruee par le robot pendant 10 secondes, puis on la divise par 10 pour avoir la distance paroucure par le robot en 1 seconde.

- Distance (par seconde) : 17,165 cm.

Soient *Vg* et *Vr* les vitesses respectives du moteur gauche et du moteur droit,
et *t* le temps.

La distance parcourue par le robot, noté *D*, s'exprime de la manière suivante :

    D = ((Vg + Vr)/2) * t;

**Mise à jour : 14 avril 2016**

> Mesure de la vitesse des roues

  La mesure de la vitesse de chaque roue (vitesse angulaire) a été faite via 2 méthodes:

- Solution 1 : Mesure continue active (dans un thread à part).
- Solution 2 : Mesure passive en se basant sur les mises à jour.

 La première méthode, permet d'avoir un "bon" suivi
dans la mesure où on connait les instants dans
lesquels les mesures sont effectuées étaient connues.
Sachant que l'on définit un certain nombre de mesures par seconde,
on peut aisément déterminer à quel moment le temps de parcours dépasse une seconde.
 Cependant, cette méthode présente un inconvénient. En effet, cela
 engendre beaucoup de mesures identiques inutiles
 ainsi qu'une consommation plus importante des ressources.

  La deuxième méthode, quant à elle, se base juste sur la mise
à jour des vitesses. Dès un changement a lieu. une notification
se fait sur l'outils de mesure qui va récupérer les mise à jour.
Pour autant, on perd l'information sur le moment où le robot
effectue les changement de viteses sur les roues. Il faudra donc à
chaque notification enregistrer l'instant où les nouvelles vitesses
sont appliquées.

La solution retenue est la solution 2.

> Sémantique

Soit l'outils de mesure que l'on nommera *RStat*

   *RStat* enregistre les données dans une liste.
Cette liste est mise à jour à chaque fois qu'une nouvelle entrée est capté par *RStat*.
Chaque donnée est défini par le  triplet :
- ***(vg,vd,t)***

où ***vg*** et ***vd*** sont respectivement les vitesses angulaires des roues gauche et droite, et ***t*** l'instant à partir duquel les vitesses sont appliquées.

Lorsque le robot termine sont parcours, *RStat* enregistre les données captées dans un fichier *statO.gumi*.


**Mise à jour : 1er mai 2016  (oui oui c'était aussi long)**

Récupération des données effectuée.

Exemple de données lues :

    0 0 0
    360 360 30
    360 240 2035
    180 360 3537
    360 360 6535
    -360 360 8539
    0 0 10449

Chaque ligne correspond eu triplet ***(vg,vd,t)*** spécifié plus haut

Le but est d'avoir les coordonnées du robot à partir de ces données et d'effectuer
un calcul de trajectoire.

> Ces données sont-elles suffisantes ?

Peut-être pas, il est possible de connaitre les coordonnées du robot à tout moment,
mais cela n'a pas encore été testé (à faire).

Il sera peut-être nécessaire de modifier les données pour avoir quelque chose
comme ***(x,y,vg,vd,t)***. *x* et *y* ssont les cordonnées du robot.

***TODO***

- Tenter de récupérer les coordonnées.
- Calcul de trajectoire.


**Mise à jour : 11 mai 2016**

> Petite mise au point (pour ne pas être perdu) :

- distance *dr* parcouruee par le robot (par seconde) : 17,165 cm.

  Cette distance a été obtenue à partir d'une vitese angulaire de 360°.s-¹.
Méthode de mesure : on fait avancer le robot pendant 10 secondes, on mesure
la distance parcourue et on divise la valeur par 10.

Soient *Vg* et *Vd* les vitesses respectives du moteur gauche et du moteur droit,
et *t* le temps.

Soient *VRg* et *VRd* les vitesses angulaires respectives du moteur gauche et
du moteur droit.

Sachant qu'avec une vitesse angulaire *Va* de 360°.s-¹, on a la distance *dr*,
à partir du tableau suivant :

|  Va   |   dr  |
|-------|-------|
|  360  | 17,165|
|   v   |   d   |

Il est possible, en connaissant la vitesse angulaire *v* de déterminer
la distance *d*.

Dans le cas où on a les données suivantes :

    360 240 2035
    180 360 3537


On a :

 - VRg = 360°.s-¹.
 - VRd = 240°.s-¹.

On obtient donc :

 - Vg = 0.17165 m.s-¹.
 - Vd = 240 * 0.17165 / 360 = 0.11443 m.s-¹.


La vitesse *Vr* suivante du robot, s'exprime ainsi :

    Vr = (Vg + Vd)/2


Avec les valeurs calculée de *Vg* et *Vd*, on a donc :

    Vr = (Vg + Vd)/2
    Vr = (0.17165 + 0.11443)/2
    Vr = 0.14299 m.s-¹


*t* correspond au temps durant lequel le robot a roulé à ces vitesses.
En l'occurrence, on a :

    t = 3537 - 2035
    t = 1502 ms
    t = 1,502 s


La distance parcourue par le robot, noté *D*, s'exprime de la manière suivante :

    D = Vr * t;

On a donc :

    D = .014299 * 1.502
    D = 0.21477 m

Le robot a donc parcouru 21.477 cm.


> Calcul de trajectoire :

Soient *T*, l'angle du robot lorsqu'il tourne, *Dg* la distance parcourue par
la roue gauche, *Dd* la distance parcourue par la roue droite. et *L* la largeur
du robot. On a cette formule :

    L = 0.1438 m
    Θ = (Dg - Dd) / L ①     // Θ est en radian

Cette formule semble juste, mais le raisonnement est trop bancal pour en attester.


> Raisonnement

Dans le cas présent, on peut mesurer la distance parcourue par chaque roue.
Soient *Dg* la distance parcourue la la roue gauche
et *Dd* la distance parcourue par la roue droite.

    Dg = 0.17165 m
    Dd = 0.11443 m

*Dg > Dd*, donc on peut donc dire, au vu du virage que prend le robot (à droite),
on a :

    Dg = r.Θ    // r est le rayon du point de référence
    Dd = (r - L).Θ

Donc on a :

    r - L = Dd / Θ
    r     = (Dd / Θ)  + L

Donc, d'après la formule ①, on a:

    ① <=> (r.Θ - (r - L).Θ) / L
    ① <=> Θ.(r - (r - L)) / L
    ① <=> Θ.(r - r + L)) / L
    ① <=> Θ.L / L
    ① <=> Θ

On retrouve bien *Θ* a droite. Oui, ce raisonnement est "bizarre",
mais le résultat ne contredit pas la formule à prouver.

**Mise à jour : 27 mai 2016**

 Info sur les codes couleur:
 - noir : 3 (droite)
 - noir et bleu : 2 (millieu)
 - bleu : 1 (gauche)


## Documentations ##

 - [Connexion vers le cerveau](https://github.com/ev3dev/ev3dev/wiki/Setting-Up-Windows-USB-Ethernet-Networking)
 - [API EV3](http://www.lejos.org/ev3/docs/)
 - [Connexion EV3 : obtenir adresse](http://knowledge.autodesk.com/customer-service/network-license-administration/install-and-configure-network-license/requesting-license-file/finding-your-host-name-and-id)
