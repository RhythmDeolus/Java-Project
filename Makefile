compile:
	dir /s /B *.java > source.txt
	javac -cp "libs\\mysql-connector-j-8.0.32.jar" @source.txt

run: 
	java -cp ".\\;libs\\mysql-connector-j-8.0.32.jar" $(filename)