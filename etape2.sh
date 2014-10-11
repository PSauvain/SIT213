#!/bin/bash
# Script Bash permettant de compiler et générer la javadoc de notre programme simulant une chaine de transmission


javac programme/*.java -d bin -encoding ISO-8859-1

if [ $? -eq 0 ]; then
    echo "Compilation effectuée avec succès"
else
    echo "Echec de la compilation ... Appuyez sur Entrée pour quitter"
    read
    exit
fi

javadoc -d doc/ -encoding ISO-8859-1 programme/*.java

if [ $? -eq 0 ]; then
    echo "Génération de la javadoc effectuée avec succès"
    echo "Pour lancer le programme, positionnez vous dans le dossier bin et tapez :"
    echo "java programme.Simulateur [-etape n] [...]"
    echo "Pour plus de détails concernant l'usage du Simulateur, tapez :"
    echo "java programme.Simulateur -help"
else
    echo "Echec de la génération ... fermeture du programme"
fi
