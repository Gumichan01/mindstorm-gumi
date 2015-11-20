= Feuille de route =

Repérer les logiciels pour développer sur la plateforme

Liste des outils candidats:

 - LeJOS > http://www.lejos.org/
 - BricxCC > http://bricxcc.sourceforge.net/


Outil sélectionné: LeJOS


Programme Hello Wolrd fait !

== DONE ==

  * Robot monté
  * Déplacement basique + rotation approximative sur lui-même
  * Programme capteur couleur + lumière
  * Etalonnage

== DOING ==

  * Spécification


== TODO ==

  * Programme qui fait avancer le robot sur une ligne/courbe
  * Test sur une ligne


== Documentations ==

 - [Connexion vers le cerveau] (https://github.com/ev3dev/ev3dev/wiki/Setting-Up-Windows-USB-Ethernet-Networking)
 - [API EV3] (http://www.lejos.org/ev3/docs/)
 - [Connexion EV3 obtenir adreese] (http://knowledge.autodesk.com/customer-service/network-license-administration/install-and-configure-network-license/requesting-license-file/finding-your-host-name-and-id)


creation de plusiseurs classe pour l'etalonage et la detection dans un dosssier capteur couleur
  Detecction.java
  CouleurGestion.java
  CouleurInterpreter.java
  CouleurListener.java

pour plus tard pour faire propre un dossier principal qui contiendra le main et potentiellement autre chose
un dossier deplacement pour les deplacements du robot
un dossier comportement pour gerer le comportement du robot pendant son deplacement potentiellement d'autre dossier si besoin
