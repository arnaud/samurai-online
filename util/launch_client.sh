#!/bin/sh
# +++++++++++++++++++++++++++++++++++++++++++++++++++++++++\
# + Ce fichier permet d'exécuter l'application cliente sans +
# + aucune configuration manuelle. Il ne peut cependant pas +
# + être exécuté directement depuis Eclipse. Il faut l'exé- +
# + cuter depuis l'explorateur Windows.                     +
# \+++++++++++++++++++++++++++++++++++++++++++++++++++++++++/

OPTION='-Djava.library.path=libs/lwjgl/native/linux'
JARFILE='client.jar'
java $OPTION -jar $JARFILE $*
