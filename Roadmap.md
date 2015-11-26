= Feuille de route =

Repérer les logiciels pour développer sur la plateforme

Liste des outils candidats:

 - LeJOS > http://www.lejos.org/
 - BricxCC > http://bricxcc.sourceforge.net/


Outil sélectionné: LeJOS


== DONE ==

  * Hello World
  * Robot monté
  * Déplacement basique + rotation approximative sur lui-même
  * Programme capteur couleur + lumière

== DOING ==

  * Etalonnage (définir l'epsilon)
  * Spécification


== TODO ==

  * Programme qui fait avancer le robot sur une ligne/courbe
  * Test sur une ligne

  
Mise à jour : 19 novembre
 -l'étalonnage ne marche pas, des difficultés à comprendre et à le programmer.
 -le robot perçois qu'une "trame de couleur" mais pas totalement, il ne s'adapte pas à 
la lumibnosité

Mise à jour : 20 novembre 2015
- des difficultés dans la detection des couleurs plus précisement faire l'étalonnage.
- des difficultés dans le codage de la couleur noir ,blanc et vert,
plus pécisement arriver à faire en sorte de respecter la tanche des couleurs
-l'étalonnage est fait en parti.

Mise à jour : 21 novembre 2015
  - ajout du fichier detection.java
  - toujours autant de difficultés a faire fonctionner l'étalonnage, 
cela ne fait pas encore ce que l'on veut

Mise à jour : 24 novembre 2015
  - Etalonnage presque terminé, il faut juste définir un epsilon qui sera l'écart-type
  - Le programme de parcours prendra les valeurs mesurées comme valeurs
de référence et utilisera l'epsilon pour déterminer si la couleur capté 
(modulo l'epsilon) est bien celle désiré pour effectuer les opérations adéquates
  - Il sera peut-être nécessaire d'utiliser deux capteurs pour notamment savoir
vers où dévie le robot afin d'effectuer une correction de trajectoire adéquate


Mise à jour : 26 novembre 2015 
  - Garder les moyennes. Lors du suivi de ligne, prendre les mesures et  
calculer dynamiquement l'ecart-type pour chaque canal.  
On calcule une moyenne *meta_moy* des trois canaux de couleur (*moy_r*,*moy_g*,*moy_b*)  
et aussi une moyenne *sigma_moy* des trois écart-types (*sigma_r*,*sigma_g*,*sigma_b*).
Si *sigma_moy* < *meta_moy* alors la couleur est bonne. (A tester)

== Documentations ==

 - [Connexion vers le cerveau] (https://github.com/ev3dev/ev3dev/wiki/Setting-Up-Windows-USB-Ethernet-Networking)
 - [API EV3] (http://www.lejos.org/ev3/docs/)
 - [Connexion EV3 obtenir adresse] (http://knowledge.autodesk.com/customer-service/network-license-administration/install-and-configure-network-license/requesting-license-file/finding-your-host-name-and-id)


Creation de plusiseurs classe pour l'etalonage et la detection dans un dosssier capteur couleur
  Detecction.java
  CouleurGestion.java
  CouleurInterpreter.java
  CouleurListener.java

pour plus tard pour faire propre un dossier principal qui contiendra le main et potentiellement autre chose
un dossier deplacement pour les deplacements du robot
un dossier comportement pour gerer le comportement du robot pendant son 
déplacement potentiellement d'autre dossier si besoin

épurations du depot git:
- dans le dossier testes tout ce qu'on code et qu'on teste qui compile et fonctionnent plus ou moins correctement
- dans le dossier final, on mettera dedans tout ce qui fonctionne parfaitement par exemple le dossier capteur de couleur quand l'étalonnage sera fait parfaitement.

Modifications probables des fichiers du dossier capteurcouleur et des autres aussi.


