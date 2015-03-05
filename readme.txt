Création d'un fichier de configuration :

C'est 7 zones et le dernier élément doivent s'enchaîner comme suit :

première zone
=============

	states:

		un_etat,
		un_etat_stoppant S,
		un_autre_etat_non_stopant,


Un état est définit par une suite de caractères. (hors \t \n \r \s)

deuxième zone
=============

	:machine_alphabet:

	 a b c 

	 d e f

Un alphabet est constitué de caractères, un symbol est un caractère.

troisième zone
==============

	:tape_alphabet:

	 a b c #

	 d e f @

 L'aphabet du ruban doit être constitué de l'aphabet machine

quatrième zone
==============

	:transitions:

		un_etat 					a un_autre_etat_non_stopant d >	,
		un_etat_stoppant 			b un_etat 					e <	,
		un_autre_etat_non_stopant 	c un_etat_stoppant 			f < ,


Une transition se compose comme suit : 
un état, doit être définit en zone 1
un symbol, doit être définit en zone 3
l'état suivant, doit être définit en zone 1, ou 6 ou 7
le nouveau symbol, doit être définit en zone 3 
la direction, doit être soit < soit >	

cinquième zone
==============

	:init_state:

	  etat_initial

Doit être présent dans la zone 1
Se définit comme un état  

sixième zone
============
  
	:accept_state:

	  etat_acceptant

Se définit comme un état
Doit être présent dans au moins une transition en tant qu'état suivant   

septième zone
=============

	:reject_state:

	  etat_rejetant  

Se définit comme un état  

dernier élément
===============
    
	:end

finit le fichier



Sont testés :

La forme des états, symbols

La présence de l'alphabet machine dans l'aphabet ruban

La présence des états actuels des transitions dans la zone d'états

La présence de l'état initial dans la zone d'états

La présence de l'état acceptant dans au moins une transition en tant qu'état suivant

La présence de l'état suivant dans les transitions, soit dans la zone d'états, soit dans la déclaration de l'état acceptant, ou l'état rejetant

La bonne disposition des zones

