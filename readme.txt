Création d'un fichier de configuration :

Tout ce qui se trouve entre " ======= " doit être écrit dans le fichier.

Ces 7 zones et le dernier élément doivent s'enchaîner comme suit :

Première zone
=============

	states:

		un_etat,
		un_etat_stoppant S,
		un_autre_etat_non_stoppant,

=============

Un état est définit par une suite de caractères. (hors \t \n \r \s)
Il suffit d'ajouter un 'S' derrière un état pour indiquer qu'il est stoppant.
Les états sont séparés par une virgule.


Deuxième zone
=============

	:machine_alphabet:

	 a b c 

	 d e
	 f

=============

Un alphabet est constitué de caractères, un symbole est un caractère.
Les caractères peuvent être écris en ligne, en colonne, ou les deux.
Tous les caractères sont acceptés, hormis l'espace et les caractères trop spéciaux (∞, ᴓ, etc.).


Troisième zone
==============

	:tape_alphabet:

	 a b c #

	 1 2 3 @
	 
==============

 L'aphabet du ruban doit être constitué de l'aphabet machine au minimum.
 Tout comme l'alphabet machine, les caractères peuvent être écrits en ligne, colonne, ou les deux.

 
Quatrième zone
==============

	:transitions:

		etat_1 		a etat_3 	d 	> ,
		etat_2		b etat_1	e 	< ,
		etat_3 		c etat_2 	f 	< ,

==============

Une transition se compose comme suit : 
	un état, doit être définit en zone 1
	un symbole, doit être définit en zone 3
	l'état suivant, doit être définit en zone 1, 6 ou 7
	le nouveau symbole, doit être définit en zone 3 
	la direction, doit être soit < (gauche) soit > (droite)

Une virgule sépare chaque transition.
	
	
Cinquième zone
==============

	:init_state:

	  etat_initial

==============
	  
Doit être présent dans la zone 1
Se définit comme un état, sans la virgule à la fin.


Sixième zone
============
  
	:accept_state:

	  etat_acceptant

============
	  
Se définit comme un état, sans la virgule à la fin.
Doit être présent dans au moins une transition en tant qu'état suivant   


Septième zone
=============

	:reject_state:

	  etat_rejetant  
	  
=============

Se définit comme un état, sans la virgule à la fin.
Cet état n'est pas obligatoire, dépent de l'automate définit.


Dernier élément
===============
    
	:end
	
===============

Indique que le fichier est terminé.



Sont testés :

La forme des états, symboles

La présence de l'alphabet machine dans l'aphabet ruban

La présence des états actuels des transitions dans la zone d'états

La présence de l'état initial dans la zone d'états

La présence de l'état acceptant dans au moins une transition en tant qu'état suivant

La présence de l'état suivant dans les transitions, soit dans la zone d'états, soit dans la déclaration de l'état acceptant, ou l'état rejetant

La bonne disposition des zones


