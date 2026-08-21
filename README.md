# Motorcycle-maintenance-tracker
JAVA app that logs oil changes, chain lube, tire wear, mileage intervals for motorcycle.

How to run
1. Clone the repo

2. Download the required libraries into a 'lib\' folder:
mkdir lib

curl -L -o lib/sqlite-jdbc.jar "https://github.com/xerial/sqlite-jdbc/releases/download/3.46.1.0/sqlite-jdbc-3.46.1.0.jar"

curl -L -o lib/slf4j-api.jar "https://repo1.maven.org/maven2/org/slf4j/slf4j-api/2.0.13/slf4j-api-2.0.13.jar"

curl -L -o lib/slf4j-nop.jar "https://repo1.maven.org/maven2/org/slf4j/slf4j-nop/2.0.13/slf4j-nop-2.0.13.jar"

3. Compile and run:

javac -cp .:lib/sqlite-jdbc.jar:lib/slf4j-api.jar:lib/slf4j-nop.jar *.java

java -cp .:lib/sqlite-jdbc.jar:lib/slf4j-api.jar:lib/slf4j-nop.jar Main
