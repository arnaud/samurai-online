rem /+++++++++++++++++++++++++++++++++++++++++++++++++++++++++\
rem + Ce fichier permet d'exécuter l'application cliente sans +
rem + aucune configuration manuelle. Il ne peut cependant pas +
rem + être exécuté directement depuis Eclipse. Il faut l'exé- +
rem + cuter depuis l'explorateur Windows.                     +
rem \+++++++++++++++++++++++++++++++++++++++++++++++++++++++++/

@echo off
cls
echo *******************************
echo *       SAMOURAI ONLINE       *
echo *******************************
echo *
echo * Execution du jeu...
echo
java -jar serveur.jar
echo
echo * Jeu termine !
echo *******************************
pause
