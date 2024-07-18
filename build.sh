set -ex

javac src/analise/*.java
cd src
jar cvfm ../build/Fiscal.jar manifest.txt analise/GUIAnalise.class analise/Fiscal.class
chmod 755 ../build/Fiscal.jar