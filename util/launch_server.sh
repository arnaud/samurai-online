#!/bin/sh
# +++++++++++++++++++++++++++++++++++++++++++++++++++++++++\
# + Ce fichier permet d'exécuter l'application serveur sans +
# + aucune configuration manuelle. Il ne peut cependant pas +
# + être exécuté directement depuis Eclipse. Il faut l'exé- +
# + cuter depuis l'explorateur Windows.                     +
# \+++++++++++++++++++++++++++++++++++++++++++++++++++++++++/

JARFILE='serveur.jar'
java -jar $JARFILE $*
